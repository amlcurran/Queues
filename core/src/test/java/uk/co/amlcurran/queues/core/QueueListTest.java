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
        QueueList queueList = new QueueList(new QueuePersister(3));

        assertThat(queueList.size(), is(3));
    }

    @Test
    public void addingAQueueIncrementsTheNumberOfQueues() {
        QueuePersister queuePersister = new QueuePersister(0);
        QueueList queueList = new QueueList(queuePersister);

        queueList.add(new Queue(queuePersister));

        assertThat(queueList.size(), is(1));
    }

    private static class QueuePersister implements uk.co.amlcurran.queues.core.QueuePersister {

        private final int numberOfQueues;

        private QueuePersister(int numberOfQueues) {
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
    }

    private class QueueList {
        private final List<Queue> queues;

        public QueueList(QueuePersister queuePersister) {
            this.queues = new ArrayList<>(queuePersister.queues());
        }

        public int size() {
            return queues.size();
        }

        public void add(Queue queue) {
            queues.add(queue);
        }
    }
}
