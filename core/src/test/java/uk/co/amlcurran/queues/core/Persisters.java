package uk.co.amlcurran.queues.core;

import java.util.Collections;
import java.util.List;

public class Persisters {
    public static QueuePersister saveFailing() {
        return new NullQueuePersister(Collections.<Queue>emptyList()) {

            @Override
            public void saveQueue(Queue queue, Callbacks callbacks) {
                callbacks.failedToSave(queue);
            }
        };
    }

    public static NullQueuePersister empty() {
        return new NullQueuePersister(Collections.<Queue>emptyList());
    }

    static class NullQueuePersister implements QueuePersister {

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

        @Override
        public void deleteQueue(Queue queue, Callbacks callbacks) {

        }

    }
}
