package uk.co.amlcurran.queues.core;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class QueueTest {

    @Test
    public void testAddingAnItemToAQueue_WillPersistIt() {
        AssertingQueuePersister persister = new AssertingQueuePersister();
        Queue queue = new Queue(persister);

        QueueItem queueItem = new QueueItem("Hello");
        queue.addItem(queueItem);

        assertThat(persister.saveNewItem_item, is(queueItem));
        assertThat(persister.saveNewItem_queueId, is(queue.getId()));
    }

    @Test
      public void testAddingAnItemToAQueue_MeansItCanBeRetrieved() {
        Queue queue = new Queue(UNUSED_PERSISTER);

        QueueItem queueItem = new QueueItem("Hello");
        queue.addItem(queueItem);

        assertThat(queue.next(), is(queueItem));
    }

    private static final QueuePersister UNUSED_PERSISTER = new QueuePersister() {
        @Override
        public void addItemToQueue(long queueId, QueueItem queueItem) {

        }
    };


    private class AssertingQueuePersister implements QueuePersister {
        public QueueItem saveNewItem_item;
        public long saveNewItem_queueId;

        @Override
        public void addItemToQueue(long queueId, QueueItem queueItem) {
            saveNewItem_item = queueItem;
            saveNewItem_queueId = queueId;
        }
    }
}