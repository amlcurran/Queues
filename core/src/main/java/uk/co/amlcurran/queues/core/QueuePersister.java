package uk.co.amlcurran.queues.core;

import java.util.List;

public interface QueuePersister {
    void addItemToQueue(long queueId, QueueItem queueItem);

    void removeItemFromQueue(long queueId, QueueItem queueItem);

    void queues(LoadCallbacks callbacks);

    void saveQueue(Queue queue, Callbacks callbacks);

    long uniqueId();

    void deleteQueue(Queue queue, Callbacks callbacks);

    interface Callbacks {

        void failedToSave(Queue queue);
    }

    interface LoadCallbacks {

        void loaded(List<Queue> queues);
    }

}
