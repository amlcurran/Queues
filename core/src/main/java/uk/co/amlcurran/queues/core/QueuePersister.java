package uk.co.amlcurran.queues.core;

public interface QueuePersister {
    void addItemToQueue(long queueId, QueueItem queueItem);

    void removeItemFromQueue(long queueId, QueueItem queueItem);

    int queueCount();
}
