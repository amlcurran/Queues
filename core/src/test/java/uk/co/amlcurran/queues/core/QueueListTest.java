package uk.co.amlcurran.queues.core;

import org.junit.Test;

import java.util.AbstractList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class QueueListTest {

    @Test
    public void returnsTheCorrectAmountOfQueues() {
        QueueList queueList = new QueueList(new BasicQueuePersister(3));

        assertThat(queueList.size(), is(3));
    }

    @Test
    public void addingAQueueIncrementsTheNumberOfQueues() {
        BasicQueuePersister queuePersister = new BasicQueuePersister(0);
        QueueList queueList = new QueueList(queuePersister);

        queueList.add(Queue.withPersister(queuePersister));

        assertThat(queueList.size(), is(1));
    }

    @Test
    public void addingAQueuePersistsIt() {
        BasicQueuePersister queuePersister = new BasicQueuePersister(0);
        QueueList queueList = new QueueList(queuePersister);

        Queue queue = Queue.withPersister(queuePersister);
        queueList.add(queue);

        assertThat(queuePersister.addedQueue, is(queue));
    }

    @Test
    public void addingAQueueReturnsItFromTheList() {
        BasicQueuePersister queuePersister = new BasicQueuePersister(0);
        QueueList queueList = new QueueList(queuePersister);

        Queue queue = Queue.withPersister(queuePersister);
        queueList.add(queue);

        assertTrue(queueList.all().contains(queue));
    }

    @Test
    public void addingAQueueGivesItAUniqueId() {
        BasicQueuePersister queuePersister = new BasicQueuePersister(0);
        QueueList queueList = new QueueList(queuePersister);

        Queue firstQueue = queueList.newQueue();
        Queue secondQueue = queueList.newQueue();

        assertNotEquals(secondQueue.getId(), firstQueue.getId());
    }

    private static class BasicQueuePersister implements QueuePersister {

        private final int numberOfQueues;
        private long nextId = 0;
        public Queue addedQueue;

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

    }

}
