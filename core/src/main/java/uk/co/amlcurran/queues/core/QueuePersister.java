package uk.co.amlcurran.queues.core;

import java.util.List;

public interface QueuePersister {
    void addItemToQueue(long queueId, QueueItem queueItem);

    void removeItemFromQueue(long queueId, QueueItem queueItem);

    List<Queue> queues();

    void saveQueue(Queue queue, Callbacks callbacks);

    long uniqueId();

    void deleteQueue(Queue queue, Callbacks callbacks);

    interface Callbacks {

    }

}
