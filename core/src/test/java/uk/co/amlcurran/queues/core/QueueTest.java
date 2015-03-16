package uk.co.amlcurran.queues.core;

import org.junit.Test;

import java.util.List;

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

    @Test
    public void testAddingTwoItemsToAQueue_RetrievesThemInTheCorrectOrder() {
        Queue queue = new Queue(UNUSED_PERSISTER);

        QueueItem queueItem = new QueueItem("Hello");
        QueueItem queueItem2 = new QueueItem("How are you");
        queue.addItem(queueItem);
        queue.addItem(queueItem2);

        assertThat(queue.next(), is(queueItem));
        assertThat(queue.next(), is(queueItem2));
    }

    @Test
    public void testAddingAndIteratingWorks() {
        Queue queue = new Queue(UNUSED_PERSISTER);

        QueueItem queueItem = new QueueItem("Hello");
        queue.addItem(queueItem);
        assertThat(queue.next(), is(queueItem));

        QueueItem queueItem2 = new QueueItem("How are you");
        queue.addItem(queueItem2);
        assertThat(queue.next(), is(queueItem2));
    }

    @Test
    public void testRemovingAnItem_RemovesIt() {
        Queue queue = new Queue(UNUSED_PERSISTER);

        QueueItem queueItem = new QueueItem("Hello");
        QueueItem queueItem2 = new QueueItem("How are you");
        queue.addItem(queueItem);
        queue.addItem(queueItem2);

        queue.removeItem(new QueueItem("Hello"));

        assertThat(queue.next(), is(queueItem2));
    }

    @Test
    public void testRemovingAnItem_RemovesItFromThePersister() {
        AssertingQueuePersister queuePersister = new AssertingQueuePersister();
        Queue queue = new Queue(queuePersister);

        QueueItem queueItem = new QueueItem("Hello");
        QueueItem queueItem2 = new QueueItem("How are you");
        queue.addItem(queueItem);
        queue.addItem(queueItem2);

        queue.removeItem(new QueueItem("Hello"));

        assertThat(queuePersister.remove_Item, is(queueItem));
    }

    private static final QueuePersister UNUSED_PERSISTER = new QueuePersister() {
        @Override
        public void addItemToQueue(long queueId, QueueItem queueItem) {

        }

        @Override
        public void removeItemFromQueue(long queueId, QueueItem queueItem) {

        }

        @Override
        public List<Queue> queues() {
            return null;
        }
    };


    private class AssertingQueuePersister implements QueuePersister {
        public QueueItem saveNewItem_item;
        public QueueItem remove_Item;
        public long saveNewItem_queueId;

        @Override
        public void addItemToQueue(long queueId, QueueItem queueItem) {
            saveNewItem_item = queueItem;
            saveNewItem_queueId = queueId;
        }

        @Override
        public void removeItemFromQueue(long queueId, QueueItem queueItem) {
            remove_Item = queueItem;
        }

        @Override
        public List<Queue> queues() {
            return null;
        }
    }
}