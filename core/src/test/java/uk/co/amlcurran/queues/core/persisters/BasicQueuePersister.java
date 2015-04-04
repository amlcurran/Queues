package uk.co.amlcurran.queues.core.persisters;

import java.util.ArrayList;
import java.util.List;

import uk.co.amlcurran.queues.core.Queue;
import uk.co.amlcurran.queues.core.QueueItem;
import uk.co.amlcurran.queues.core.QueuePersister;

public class BasicQueuePersister implements QueuePersister {

    private final List<Queue> queues;
    private long nextId = 0;
    public Queue addedQueue;
    public Queue removedQueue;

    BasicQueuePersister(final int numberOfQueues) {
        this.queues = new ArrayList<>();
        for (int i = 0; i < numberOfQueues; i++) {
            queues.add(Queue.withPersister("hello", this));
        }
    }

    @Override
    public void addItemToQueue(long queueId, QueueItem queueItem) {

    }

    @Override
    public void removeItemFromQueue(long queueId, QueueItem queueItem) {

    }

    @Override
    public void queues(LoadCallbacks callbacks) {
        callbacks.loaded(queues);;
    }

    @Override
    public void saveQueue(Queue queue, Callbacks callbacks) {
        addedQueue = queue;
    }

    @Override
    public long uniqueId() {
        return nextId++;
    }

    @Override
    public void deleteQueue(Queue queue, Callbacks callbacks) {
        removedQueue = queue;
    }

}
