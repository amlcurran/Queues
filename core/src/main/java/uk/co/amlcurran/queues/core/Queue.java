package uk.co.amlcurran.queues.core;

import java.util.ArrayList;
import java.util.List;

public class Queue {
    private final QueuePersister queuePersister;
    private final List<QueueItem> queueItems;
    private final String title;
    private final long id;
    private int iteratorIndex = 0;

    private Queue(String title, long id, QueuePersister queuePersister) {
        this.title = title;
        this.id = id;
        this.queuePersister = queuePersister;
        this.queueItems = new ArrayList<>();
    }

    public static Queue withPersister(String title, final QueuePersister queuePersister) {
        return new Queue(title, queuePersister.uniqueId(), queuePersister);
    }

    public void addItem(QueueItem queueItem) {
        queueItems.add(queueItem);
        queuePersister.addItemToQueue(id, queueItem);
    }

    public String getTitle() {
        return title;
    }

    public long getId() {
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
    }

    public QueueItem firstItem() {
        return queueItems.get(0);
    }
}
