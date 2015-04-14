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
    public void addItemToQueue(String queueId, QueueItem queueItem) {

    }

    @Override
    public void removeItemFromQueue(String queueId, QueueItem queueItem) {

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
    public String uniqueId() {
        return String.valueOf(nextId++);
    }

    @Override
    public String uniqueItemId() {
        return "0";
    }

    @Override
    public void deleteQueue(Queue queue, Callbacks callbacks) {
        removedQueue = queue;
    }

    @Override
    public boolean requiresUserIntervention() {
        return false;
    }

}
