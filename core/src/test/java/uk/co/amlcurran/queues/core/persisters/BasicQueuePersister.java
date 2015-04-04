package uk.co.amlcurran.queues.core.persisters;

import java.util.AbstractList;
import java.util.List;

import uk.co.amlcurran.queues.core.Queue;
import uk.co.amlcurran.queues.core.QueueItem;
import uk.co.amlcurran.queues.core.QueuePersister;

public class BasicQueuePersister implements QueuePersister {

    private final int numberOfQueues;
    private long nextId = 0;
    public Queue addedQueue;
    public Queue removedQueue;

    BasicQueuePersister(int numberOfQueues) {
        this.numberOfQueues = numberOfQueues;
    }

    @Override
    public void addItemToQueue(long queueId, QueueItem queueItem) {

    }

    @Override
    public void removeItemFromQueue(long queueId, QueueItem queueItem) {

    }

    @Override
    public List<Queue> queues() {
        return new AbstractList<Queue>() {
            @Override
            public Queue get(int index) {
                return null;
            }

            @Override
            public int size() {
                return numberOfQueues;
            }
        };
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
