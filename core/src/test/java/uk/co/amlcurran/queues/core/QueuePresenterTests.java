package uk.co.amlcurran.queues.core;

import org.junit.Before;
import org.junit.Test;

import uk.co.amlcurran.queues.core.persisters.Persisters;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class QueuePresenterTests {

    private AssertingQueueView queueView;
    private QueuePresenter presenter;
    public static final long QUEUE_ID = 212;

    @Before
    public void setUp() throws Exception {
        QueuePersister queuePersister = Persisters.singleQueueWithId(QUEUE_ID);
        QueueList queueList = new QueueList(queuePersister);
        queueList.load();
        queueView = new AssertingQueueView();
        presenter = new QueuePresenter(QUEUE_ID, queueView, queueList);
    }

    @Test
    public void loadingWillRetrieveTheQueueFromThePersister() {
        presenter.load();

        assertThat(queueView.shownQueue.getId(), is(QUEUE_ID));
    }

    @Test
    public void addingAnItemNotifiesTheView() {
        QueueItem queueItem = new QueueItem("hello");
        presenter.load();

        presenter.addItem(queueItem);

        assertThat(queueView.addedItem, is(queueItem));
    }

    @Test
    public void removingAnItemNotifiesTheView() {
        QueueItem queueItem = new QueueItem("hello");
        presenter.load();

        presenter.removeItem(queueItem);

        assertThat(queueView.removedItem, is(queueItem));
    }

    private class AssertingQueueView implements QueueView {
        public Queue shownQueue;
        public QueueItem addedItem;
        public QueueItem removedItem;

        @Override
        public void show(Queue queue) {
            shownQueue = queue;
        }

        @Override
        public void itemAdded(QueueItem queueItem) {
            addedItem = queueItem;
        }

        @Override
        public void itemRemoved(QueueItem item) {
            removedItem = item;
        }
    }
}
