package uk.co.amlcurran.queues.core;

public class Queue {
    private final QueuePersister queuePersister;
    private long id;

    public Queue(QueuePersister queuePersister) {

        this.queuePersister = queuePersister;
    }

    public void addItem(QueueItem queueItem) {
        queuePersister.addItemToQueue(id, queueItem);
    }

    public long getId() {
        return id;
    }
}
