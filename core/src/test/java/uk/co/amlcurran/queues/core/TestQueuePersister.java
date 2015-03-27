package uk.co.amlcurran.queues.core;

import java.util.ArrayList;
import java.util.List;

public class TestQueuePersister implements QueuePersister {

    private final List<Queue> queues;

    private TestQueuePersister() {
        queues = new ArrayList<>();
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
        queues.add(queue);
    }

    @Override
    public long uniqueId() {
        return queues.size();
    }

    @Override
    public void deleteQueue(Queue queue, Callbacks callbacks) {

    }

    public static QueuePersister withNumberOfQueues(int number) {
        TestQueuePersister queuePersister = new TestQueuePersister();
        for (int i = 0; i < number; i++) {
            queuePersister.saveQueue(Queue.withPersister("" + i, queuePersister), null);
        }
        return queuePersister;
    }
}
