package uk.co.amlcurran.queues.core.persisters;

import uk.co.amlcurran.queues.core.Queue;
import uk.co.amlcurran.queues.core.QueueItem;
import uk.co.amlcurran.queues.core.QueuePersister;

public class AssertingPersister implements QueuePersister {
    public String queueById;

    @Override
    public void addItemToQueue(String queueId, QueueItem queueItem) {

    }

    @Override
    public void removeItemFromQueue(String queueId, QueueItem queueItem) {

    }

    @Override
    public void queues(LoadCallbacks callbacks) {

    }

    @Override
    public void saveQueue(Queue queue, Callbacks callbacks) {

    }

    @Override
    public String uniqueId() {
        return "0";
    }

    @Override
    public long uniqueItemId() {
        return 0;
    }

    @Override
    public void deleteQueue(Queue queue, Callbacks callbacks) {

    }
}
