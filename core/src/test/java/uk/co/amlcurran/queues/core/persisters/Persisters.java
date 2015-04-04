package uk.co.amlcurran.queues.core.persisters;

import uk.co.amlcurran.queues.core.Queue;
import uk.co.amlcurran.queues.core.QueuePersister;

public class Persisters {
    public static QueuePersister saveFailing() {
        return new BasicQueuePersister(0) {

            @Override
            public void saveQueue(Queue queue, Callbacks callbacks) {
                callbacks.failedToSave(queue);
            }
        };
    }

    public static QueuePersister empty() {
        return new BasicQueuePersister(0);
    }

    public static BasicQueuePersister withNumberOfQueues(int numberOfQueues) {
        return new BasicQueuePersister(numberOfQueues);
    }

    public static AssertingPersister asserter() {
        return new AssertingPersister();
    }

    public static QueuePersister singleQueueWithId(final long queueId) {
        return new BasicQueuePersister(1) {

            @Override
            public long uniqueId() {
                return queueId;
            }
        };
    }
}
