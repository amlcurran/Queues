package uk.co.amlcurran.queues.core;

import java.util.List;

public interface QueuePersister {
    void addItemToQueue(String queueId, QueueItem queueItem);

    void removeItemFromQueue(String queueId, QueueItem queueItem);

    void queues(LoadCallbacks callbacks);

    Queue saveQueue(Queue queue, Callbacks callbacks);

    String uniqueId();

    String uniqueItemId();

    void deleteQueue(Queue queue, Callbacks callbacks);

    boolean requiresUserIntervention();

    interface Callbacks {

        void failedToSave(Queue queue);
    }

    interface LoadCallbacks {

        void loaded(List<Queue> queues);
    }

}
