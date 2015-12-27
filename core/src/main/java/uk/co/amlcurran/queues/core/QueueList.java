package uk.co.amlcurran.queues.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QueueList {
    private final QueuePersister queuePersister;
    private final List<Queue> queues;
    private final List<ListListener> listeners;
    private boolean loaded;

    public QueueList(QueuePersister queuePersister) {
        this.queuePersister = queuePersister;
        this.queues = new ArrayList<>();
        this.listeners = new ArrayList<>();
    }

    public void load() {
        this.queues.clear();
        queuePersister.queues(new QueuePersister.LoadCallbacks() {
            @Override
            public void loaded(final List<Queue> queues) {
                loaded = true;
                QueueList.this.queues.addAll(queues);
                notifyListeners(new ListenerAction() {
                    @Override
                    public void act(ListListener listListener) {
                        listListener.queuesLoaded(new ArrayList<>(queues));
                    }
                });
            }
        });
    }

    public int size() {
        return queues.size();
    }

    public void add(final Queue queue) {
        final Queue resultQueue = queuePersister.saveQueue(queue, new QueuePersister.Callbacks() {
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
        if (resultQueue != null) {
            queues.add(resultQueue);
            notifyListeners(new ListenerAction() {
                @Override
                public void act(ListListener listListener) {
                    listListener.queueAdded(resultQueue);
                }
            });
        }
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

    public Queue queueById(String queueId) {
        for (Queue queue : queues) {
            if (queue.getId().equals(queueId)) {
                return queue;
            }
        }
        return null;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public interface ListListener {

        void queueAdded(Queue queue);

        void queueRemoved(Queue queue, int removedPosition);

        void queuesLoaded(List<Queue> queues);
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
