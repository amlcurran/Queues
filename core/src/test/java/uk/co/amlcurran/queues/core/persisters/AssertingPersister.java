package uk.co.amlcurran.queues.core.persisters;

import java.util.List;

import uk.co.amlcurran.queues.core.Queue;
import uk.co.amlcurran.queues.core.QueueItem;
import uk.co.amlcurran.queues.core.QueuePersister;

public class AssertingPersister implements QueuePersister {
    public long queueById;

    @Override
    public void addItemToQueue(long queueId, QueueItem queueItem) {

    }

    @Override
    public void removeItemFromQueue(long queueId, QueueItem queueItem) {

    }

    @Override
    public List<Queue> queues() {
        return null;
    }

    @Override
    public void saveQueue(Queue queue, Callbacks callbacks) {

    }

    @Override
    public long uniqueId() {
        return 0;
    }

    @Override
    public void deleteQueue(Queue queue, Callbacks callbacks) {

    }
}
