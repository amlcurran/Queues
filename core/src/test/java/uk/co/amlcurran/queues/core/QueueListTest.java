package uk.co.amlcurran.queues.core;

import org.junit.Test;

import java.util.AbstractList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class QueueListTest {

    private static final QueuePersister UNUSED_PERSISTER = null;

    @Test
    public void returnsTheCorrectAmountOfQueues() {
        QueueList queueList = new QueueList(new BasicQueuePersister(3));

        assertThat(queueList.size(), is(3));
    }

    @Test
    public void addingAQueueIncrementsTheNumberOfQueues() {
        BasicQueuePersister queuePersister = new BasicQueuePersister(0);
        QueueList queueList = new QueueList(queuePersister);

        queueList.add(QueueFactory.withPersister(queuePersister));

        assertThat(queueList.size(), is(1));
    }

    @Test
    public void addingAQueuePersistsIt() {
        BasicQueuePersister queuePersister = new BasicQueuePersister(0);
        QueueList queueList = new QueueList(queuePersister);

        Queue queue = QueueFactory.withPersister(queuePersister);
        queueList.add(queue);

        assertThat(queuePersister.addedQueue, is(queue));
    }

    @Test
    public void removingAQueueUnpersistsIt() {
        BasicQueuePersister queuePersister = new BasicQueuePersister(0);
        QueueList queueList = new QueueList(queuePersister);

        Queue queue = QueueFactory.withPersister(queuePersister);
        queueList.add(queue);
        queueList.remove(queue);

        assertThat(queuePersister.removedQueue, is(queue));
    }

    @Test
    public void addingAQueueReturnsItFromTheList() {
        BasicQueuePersister queuePersister = new BasicQueuePersister(0);
        QueueList queueList = new QueueList(queuePersister);

        Queue queue = QueueFactory.withPersister(queuePersister);
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

    @Test
    public void addingAListNotifiesListeners() {
        QueueList queueList = new QueueList(new BasicQueuePersister(0));
        AssertingListListener listListener = new AssertingListListener();
        queueList.addCallbacks(listListener);

        Queue firstQueue = queueList.newQueue();
        queueList.add(firstQueue);

        assertThat(listListener.queueAdded, is(firstQueue));
    }

    @Test
    public void addingARemovedListenerDoesntGetNotified() {
        QueueList queueList = new QueueList(new BasicQueuePersister(0));
        AssertingListListener listListener = new AssertingListListener();
        queueList.addCallbacks(listListener);
        queueList.removeCallbacks(listListener);

        Queue firstQueue = queueList.newQueue();
        queueList.add(firstQueue);

        assertNull(listListener.queueAdded);
    }

    @Test
    public void gettingThePositionFromQueueReturnsTheCorrectPosition() {
        BasicQueuePersister queuePersister = new BasicQueuePersister(0);
        QueueList queueList = new QueueList(queuePersister);

        Queue firstQueue = queueList.newQueue();
        Queue secondQueue = queueList.newQueue();
        queueList.add(firstQueue);
        queueList.add(secondQueue);

        assertThat(queueList.positionFromQueue(firstQueue), is(0));
        assertThat(queueList.positionFromQueue(secondQueue), is(1));
    }

    private static class BasicQueuePersister implements QueuePersister {

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

    private class AssertingListListener implements QueueList.ListListener {
        public Queue queueAdded;

        @Override
        public void queueAdded(Queue queue) {
            queueAdded = queue;
        }

        @Override
        public void queueRemoved(Queue queue, int removedPosition) {

        }
    }
}
