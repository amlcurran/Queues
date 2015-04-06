package uk.co.amlcurran.queues.core;

import org.junit.Test;

import uk.co.amlcurran.queues.core.persisters.Persisters;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class QueueTest {

    @Test
    public void testAddingAnItemToAQueue_WillPersistIt() {
        AssertingQueuePersister persister = new AssertingQueuePersister();
        Queue queue = QueueFactory.withPersister(persister);

        QueueItem queueItem = new QueueItem("Hello");
        queue.addItem(queueItem);

        assertThat(persister.saveNewItem_item, is(queueItem));
        assertThat(persister.saveNewItem_queueId, is(queue.getId()));
    }

    @Test
    public void testAddingAnItemToAQueue_MeansItCanBeRetrieved() {
        Queue queue = QueueFactory.withPersister(UNUSED_PERSISTER);

        QueueItem queueItem = new QueueItem("Hello");
        queue.addItem(queueItem);

        assertThat(queue.next(), is(queueItem));
    }

    @Test
    public void testAddingTwoItemsToAQueue_RetrievesThemInTheCorrectOrder() {
        Queue queue = QueueFactory.withPersister(UNUSED_PERSISTER);

        QueueItem queueItem = new QueueItem("Hello");
        QueueItem queueItem2 = new QueueItem("How are you");
        queue.addItem(queueItem);
        queue.addItem(queueItem2);

        assertThat(queue.next(), is(queueItem));
        assertThat(queue.next(), is(queueItem2));
    }

    @Test
    public void testAddingAndIteratingWorks() {
        Queue queue = QueueFactory.withPersister(UNUSED_PERSISTER);

        QueueItem queueItem = new QueueItem("Hello");
        queue.addItem(queueItem);
        assertThat(queue.next(), is(queueItem));

        QueueItem queueItem2 = new QueueItem("How are you");
        queue.addItem(queueItem2);
        assertThat(queue.next(), is(queueItem2));
    }

    @Test
    public void testRemovingAnItem_RemovesIt() {
        Queue queue = QueueFactory.withPersister(UNUSED_PERSISTER);

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
        Queue queue = QueueFactory.withPersister(queuePersister);

        QueueItem queueItem = new QueueItem("Hello");
        QueueItem queueItem2 = new QueueItem("How are you");
        queue.addItem(queueItem);
        queue.addItem(queueItem2);

        queue.removeItem(new QueueItem("Hello"));

        assertThat(queuePersister.remove_Item, is(queueItem));
    }

    @Test
    public void addingAnItemNotifiesListener() {
        Queue queue = QueueFactory.withPersister(Persisters.empty());
        QueueItem item = new QueueItem("Woo");
        AssertingQueueListener listener = new AssertingQueueListener();

        queue.addListener(listener);
        queue.addItem(item);

        assertThat(listener.itemAdded, is(item));
    }

    private static final QueuePersister UNUSED_PERSISTER = new QueuePersister() {
        @Override
        public void addItemToQueue(long queueId, QueueItem queueItem) {

        }

        @Override
        public void removeItemFromQueue(long queueId, QueueItem queueItem) {

        }

        @Override
        public void queues(LoadCallbacks callbacks) {

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
        public void queues(LoadCallbacks callbacks) {

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

    private class AssertingQueueListener implements Queue.QueueListener {
        public QueueItem itemAdded;

        @Override
        public void itemAdded(QueueItem queueItem) {
            itemAdded = queueItem;
        }
    }
}