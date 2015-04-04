package uk.co.amlcurran.queues.core;

import org.junit.Test;

import uk.co.amlcurran.queues.core.persisters.BasicQueuePersister;
import uk.co.amlcurran.queues.core.persisters.Persisters;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class QueueListTest {

    private static final QueuePersister UNUSED_PERSISTER = null;
    private static final String TITLE = "title";

    @Test
    public void returnsTheCorrectAmountOfQueues() {
        QueueList queueList = QueueLists.sameThreadQueueList(Persisters.withNumberOfItems(3));

        assertThat(queueList.size(), is(3));
    }

    @Test
    public void addingAQueueIncrementsTheNumberOfQueues() {
        BasicQueuePersister queuePersister = Persisters.withNumberOfItems(0);
        QueueList queueList = QueueLists.sameThreadQueueList(queuePersister);

        queueList.addNewQueue(TITLE);

        assertThat(queueList.size(), is(1));
    }

    @Test
    public void addingAQueuePersistsIt() {
        BasicQueuePersister queuePersister = Persisters.withNumberOfItems(0);
        QueueList queueList = QueueLists.sameThreadQueueList(queuePersister);

        Queue queue = queueList.addNewQueue(TITLE);

        assertThat(queuePersister.addedQueue, is(queue));
    }

    @Test
    public void addingAQueueRemovesItIfItFailedToSave() {
        QueuePersister queuePersister = Persisters.saveFailing();
        QueueList queueList = new QueueList(queuePersister);
        AssertingListListener listListener = new AssertingListListener();
        queueList.addCallbacks(listListener);

        Queue queue = queueList.addNewQueue(TITLE);

        assertThat(listListener.queueRemoved, is(queue));
        assertThat(queueList.size(), is(0));
    }

    @Test
    public void removingAQueueUnpersistsIt() {
        BasicQueuePersister queuePersister = Persisters.withNumberOfItems(0);
        QueueList queueList = QueueLists.sameThreadQueueList(queuePersister);

        Queue queue = queueList.addNewQueue(TITLE);
        queueList.remove(queue);

        assertThat(queuePersister.removedQueue, is(queue));
    }

    @Test
    public void addingAQueueReturnsItFromTheList() {
        int numberOfQueues = 0;
        BasicQueuePersister queuePersister = Persisters.withNumberOfItems(numberOfQueues);
        QueueList queueList = QueueLists.sameThreadQueueList(queuePersister);

        Queue newQueue = queueList.addNewQueue(TITLE);

        assertTrue(queueList.all().contains(newQueue));
    }

    @Test
    public void addingAQueueGivesItAUniqueId() {
        BasicQueuePersister queuePersister = Persisters.withNumberOfItems(0);
        QueueList queueList = QueueLists.sameThreadQueueList(queuePersister);

        Queue firstQueue = queueList.addNewQueue(TITLE);
        Queue secondQueue = queueList.addNewQueue(TITLE);

        assertNotEquals(secondQueue.getId(), firstQueue.getId());
    }

    @Test
    public void addingAListNotifiesListeners() {
        QueueList queueList = QueueLists.sameThreadQueueList(Persisters.withNumberOfItems(0));
        AssertingListListener listListener = new AssertingListListener();
        queueList.addCallbacks(listListener);

        Queue firstQueue = queueList.addNewQueue(TITLE);

        assertThat(listListener.queueAdded, is(firstQueue));
    }

    @Test
    public void addingARemovedListenerDoesntGetNotified() {
        QueueList queueList = QueueLists.sameThreadQueueList(Persisters.withNumberOfItems(0));
        AssertingListListener listListener = new AssertingListListener();
        queueList.addCallbacks(listListener);
        queueList.removeCallbacks(listListener);

        queueList.addNewQueue(TITLE);

        assertNull(listListener.queueAdded);
    }

    @Test
    public void gettingThePositionFromQueueReturnsTheCorrectPosition() {
        BasicQueuePersister queuePersister = Persisters.withNumberOfItems(0);
        QueueList queueList = QueueLists.sameThreadQueueList(queuePersister);

        Queue firstQueue = queueList.addNewQueue(TITLE);
        Queue secondQueue = queueList.addNewQueue(TITLE);

        assertThat(queueList.positionFromQueue(firstQueue), is(0));
        assertThat(queueList.positionFromQueue(secondQueue), is(1));
    }

    @Test
    public void persistingIsDoneOnTheBackgroundThread() {

    }

    private class AssertingListListener implements QueueList.ListListener {
        public Queue queueAdded;
        public Queue queueRemoved;

        @Override
        public void queueAdded(Queue queue) {
            queueAdded = queue;
        }

        @Override
        public void queueRemoved(Queue queue, int removedPosition) {
            queueRemoved = queue;
        }
    }
}
