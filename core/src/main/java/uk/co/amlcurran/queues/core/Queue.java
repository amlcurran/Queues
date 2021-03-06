package uk.co.amlcurran.queues.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Queue {
    private final QueuePersister queuePersister;
    private final List<QueueItem> queueItems;
    private final String title;
    private final String id;
    private int iteratorIndex = 0;
    private QueueListener listener = QueueListener.NONE;

    public Queue(String title, String id, QueuePersister queuePersister, List<QueueItem> queueItems) {
        this.title = title;
        this.id = id;
        this.queuePersister = queuePersister;
        this.queueItems = queueItems;
    }

    public static Queue withPersister(String title, final QueuePersister queuePersister) {
        return new Queue(title, queuePersister.uniqueId(), queuePersister, new ArrayList<QueueItem>());
    }

    public void addItem(String label) {
        QueueItem queueItem = new QueueItem(queuePersister.uniqueItemId(), label);
        if (queueItems.size() == 0) {
            listener.notEmpty();
        }
        queueItems.add(queueItem);
        queuePersister.addItemToQueue(id, queueItem);
        listener.itemAdded(queueItem);
    }

    public List<QueueItem> all() {
        return Collections.unmodifiableList(queueItems);
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public int size() {
        return queueItems.size();
    }

    public QueueItem next() {
        QueueItem item = queueItems.get(iteratorIndex);
        iteratorIndex++;
        return item;
    }

    public void removeItem(QueueItem item) {
        queueItems.remove(item);
        queuePersister.removeItemFromQueue(id, item);
        if (queueItems.size() == 0) {
            listener.empty();
        }
        listener.itemRemoved(item);
    }

    public QueueItem firstItem() {
        return queueItems.get(0);
    }

    public void addListener(QueueListener listener) {
        this.listener = listener;
    }

    public void removeListener() {
        this.listener = QueueListener.NONE;
    }

    public CharSequence firstItemSummary() {
        if (size() == 0) {
            return "No items";
        } else {
            return firstItem().getLabel();
        }
    }

//    private void notifyListeners(ListenerAction action) {
//
//    }
//
//    private interface ListenerAction {
//        void act(QueueListener queueListener);
//    }

    public interface QueueListener {
        QueueListener NONE = new QueueListener() {

            @Override
            public void itemAdded(QueueItem queueItem) {

            }

            @Override
            public void itemRemoved(QueueItem item) {

            }

            @Override
            public void notEmpty() {

            }

            @Override
            public void empty() {

            }
        };

        void itemAdded(QueueItem queueItem);

        void itemRemoved(QueueItem item);

        void notEmpty();

        void empty();
    }
}
