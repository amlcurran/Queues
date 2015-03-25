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
        queuePersister.saveQueue(queue, null);
        notifyListeners(new ListenerAction() {
            @Override
            public void act(ListListener listListener) {
                listListener.queueAdded(queue);
            }
        });
    }

    public Queue newQueue() {
        return Queue.withPersister(queuePersister);
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

    public interface ListListener {

        void queueAdded(Queue queue);
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
