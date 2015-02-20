package uk.co.amlcurran.queues.core;

public class Queue {
    private final QueuePersister queuePersister;
    private long id;
    private QueueItem queueItem;

    public Queue(QueuePersister queuePersister) {
        this.queuePersister = queuePersister;
    }

    public void addItem(QueueItem queueItem) {
        this.queueItem = queueItem;
        queuePersister.addItemToQueue(id, queueItem);
    }

    public long getId() {
        return id;
    }

    public QueueItem next() {
        return queueItem;
    }
}
