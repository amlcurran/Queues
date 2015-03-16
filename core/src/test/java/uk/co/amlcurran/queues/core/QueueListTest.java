package uk.co.amlcurran.queues.core;

import org.junit.Test;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

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

        queueList.add(new Queue(queuePersister));

        assertThat(queueList.size(), is(1));
    }

    @Test
    public void addingAQueuePersistsIt() {
        BasicQueuePersister queuePersister = new BasicQueuePersister(0);
        QueueList queueList = new QueueList(queuePersister);

        Queue queue = new Queue(queuePersister);
        queueList.add(queue);

        assertThat(queuePersister.addedQueue, is(queue));
    }

    private static class BasicQueuePersister implements QueuePersister {

        private final int numberOfQueues;
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

    }

    private class QueueList {
        private final List<Queue> queues;
        private final BasicQueuePersister queuePersister;

        public QueueList(BasicQueuePersister queuePersister) {
            this.queuePersister = queuePersister;
            this.queues = new ArrayList<>(queuePersister.queues());
        }

        public int size() {
            return queues.size();
        }

        public void add(Queue queue) {
            queues.add(queue);
            queuePersister.saveQueue(queue, null);
        }
    }
}
