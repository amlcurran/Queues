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

            @Override
            public String uniqueItemId() {
                return "0";
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

    public static QueuePersister singleQueueWithId(final String queueId) {
        return new BasicQueuePersister(1) {

            @Override
            public String uniqueId() {
                return queueId;
            }

            @Override
            public String uniqueItemId() {
                return String.valueOf(0);
            }
        };
    }
}
