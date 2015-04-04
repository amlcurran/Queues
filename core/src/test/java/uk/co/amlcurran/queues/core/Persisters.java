package uk.co.amlcurran.queues.core;

import java.util.AbstractList;
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

    public static QueuePersister empty() {
        return new NullQueuePersister(Collections.<Queue>emptyList());
    }

    public static BasicQueuePersister withNumberOfItems(int numberOfQueues) {
        return new BasicQueuePersister(numberOfQueues);
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

    static class BasicQueuePersister implements QueuePersister {

        private final int numberOfQueues;
        private long nextId = 0;
        public Queue addedQueue;
        public Queue removedQueue;

        private BasicQueuePersister(int numberOfQueues) {
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
}
