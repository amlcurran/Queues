package uk.co.amlcurran.queues.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QueueList {
    private final QueuePersister queuePersister;
    private final List<Queue> queues;
    private final List<ListListener> listeners;

    public QueueList(QueuePersister queuePersister) {
        this.queuePersister = queuePersister;
        this.queues = new ArrayList<>(queuePersister.queues());
        this.listeners = new ArrayList<>();
    }

    public int size() {
        return queues.size();
    }

    public void add(final Queue queue) {
        queues.add(queue);
        queuePersister.saveQueue(queue, new QueuePersister.Callbacks() {
            @Override
            public void failedToSave(final Queue queue) {
                notifyListeners(new ListenerAction() {
                    @Override
                    public void act(ListListener listListener) {
                        removeFromMemCache(queue);
                    }
                });
            }
        });
        notifyListeners(new ListenerAction() {
            @Override
            public void act(ListListener listListener) {
                listListener.queueAdded(queue);
            }
        });
    }

    private Queue newQueue(String title) {
        return Queue.withPersister(title, queuePersister);
    }

    public List<Queue> all() {
        return Collections.unmodifiableList(queues);
    }

    public void addCallbacks(ListListener listListener) {
        listeners.add(listListener);
    }

    public void removeCallbacks(ListListener listListener) {
        listeners.remove(listListener);
    }

    public int positionFromQueue(Queue queue) {
        return queues.indexOf(queue);
    }

    public void remove(final Queue queue) {
        removeFromMemCache(queue);
        queuePersister.deleteQueue(queue, null);
    }

    private void removeFromMemCache(final Queue queue) {
        final int removedPosition = positionFromQueue(queue);
        queues.remove(queue);
        notifyListeners(new ListenerAction() {
            @Override
            public void act(ListListener listListener) {
                listListener.queueRemoved(queue, removedPosition);
            }
        });
    }

    public Queue addNewQueue(String title) {
        Queue queue = newQueue(title);
        add(queue);
        return queue;
    }

    public Queue queueById(long queueId) {
        for (Queue queue : queues) {
            if (queue.getId() == queueId) {
                return queue;
            }
        }
        return null;
    }

    public interface ListListener {

        void queueAdded(Queue queue);

        void queueRemoved(Queue queue, int removedPosition);
    }

    private void notifyListeners(ListenerAction action) {
        List<ListListener> listenersCopy = new ArrayList<>(listeners);
        for (ListListener listener : listenersCopy) {
            action.act(listener);
        }
    }

    private interface ListenerAction {
        void act(ListListener listListener);
    }

}
