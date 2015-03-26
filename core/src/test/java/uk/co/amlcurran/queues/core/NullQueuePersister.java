package uk.co.amlcurran.queues.core;

import java.util.Collections;
import java.util.List;

class NullQueuePersister implements QueuePersister {
    @Override
    public void addItemToQueue(long queueId, QueueItem queueItem) {

    }

    @Override
    public void removeItemFromQueue(long queueId, QueueItem queueItem) {

    }

    @Override
    public List<Queue> queues() {
        return Collections.emptyList();
    }

    @Override
    public void saveQueue(Queue queue, Callbacks callbacks) {

    }

    @Override
    public long uniqueId() {
        return 0;
    }
}
