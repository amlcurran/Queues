package uk.co.amlcurran.queues.core;

import java.util.Collections;
import java.util.List;

class NullQueuePersister implements QueuePersister {

    private final List<Queue> queues;

    public NullQueuePersister(List<Queue> queues) {
        this.queues = queues;
    }

    @Override
    public void addItemToQueue(long queueId, QueueItem queueItem) {

    }

    @Override
    public void removeItemFromQueue(long queueId, QueueItem queueItem) {

    }

    @Override
    public List<Queue> queues() {
        return queues;
    }

    @Override
    public void saveQueue(Queue queue, Callbacks callbacks) {

    }

    @Override
    public long uniqueId() {
        return 0;
    }

    public static QueuePersister empty() {
        return new NullQueuePersister(Collections.<Queue>emptyList());
    }
}
