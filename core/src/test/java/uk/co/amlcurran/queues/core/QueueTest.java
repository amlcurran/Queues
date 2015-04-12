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

        queue.addItem("Hello");

        assertThat(persister.saveNewItem_item.getLabel(), is("Hello"));
        assertThat(persister.saveNewItem_queueId, is(queue.getId()));
    }

    @Test
    public void testAddingAnItemToAQueue_MeansItCanBeRetrieved() {
        Queue queue = QueueFactory.withPersister(UNUSED_PERSISTER);

        queue.addItem("Hello");

        assertThat(queue.next().getLabel(), is("Hello"));
    }

    @Test
    public void testAddingTwoItemsToAQueue_RetrievesThemInTheCorrectOrder() {
        Queue queue = QueueFactory.withPersister(UNUSED_PERSISTER);

        queue.addItem("Hello");
        queue.addItem("How are you");

        assertThat(queue.next().getLabel(), is("Hello"));
        assertThat(queue.next().getLabel(), is("How are you"));
    }

    @Test
    public void testAddingAndIteratingWorks() {
        Queue queue = QueueFactory.withPersister(UNUSED_PERSISTER);

        queue.addItem("Hello");
        assertThat(queue.next().getLabel(), is("Hello"));

        queue.addItem("How are you");
        assertThat(queue.next().getLabel(), is("How are you"));
    }

    @Test
    public void testRemovingAnItem_RemovesIt() {
        Queue queue = QueueFactory.withPersister(UNUSED_PERSISTER);

        String label1 = "Hello";
        String label2 = "How are you";
        queue.addItem(label1);
        queue.addItem(label2);

        queue.removeItem(new QueueItem(0, "Hello"));

        assertThat(queue.next().getLabel(), is(label2));
    }

    @Test
    public void testRemovingAnItem_RemovesItFromThePersister() {
        AssertingQueuePersister queuePersister = new AssertingQueuePersister();
        Queue queue = QueueFactory.withPersister(queuePersister);

        String label1 = "Hello";
        String label2 = "How are you";
        queue.addItem(label1);
        queue.addItem(label2);

        queue.removeItem(new QueueItem(queuePersister.uniqueItemId(), label1));

        assertThat(queuePersister.remove_Item.getLabel(), is(label1));
    }

    @Test
    public void addingAnItemNotifiesListener() {
        Queue queue = QueueFactory.withPersister(Persisters.empty());
        AssertingQueueListener listener = new AssertingQueueListener();
        String label = "Label";

        queue.addListener(listener);
        queue.addItem(label);

        assertThat(listener.itemAdded.getLabel(), is(label));
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
        public long uniqueItemId() {
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
        public long uniqueItemId() {
            return 0;
        }

        @Override
        public void deleteQueue(Queue queue, Callbacks callbacks) {

        }

    }

    private class AssertingQueueListener implements Queue.QueueListener {
        private QueueItem itemAdded;
        private QueueItem itemRemoved;

        @Override
        public void itemAdded(QueueItem queueItem) {
            itemAdded = queueItem;
        }

        @Override
        public void itemRemoved(QueueItem item) {
            itemRemoved = item;
        }
    }
}