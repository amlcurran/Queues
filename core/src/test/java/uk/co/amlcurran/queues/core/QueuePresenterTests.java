package uk.co.amlcurran.queues.core;

import org.junit.Test;

import uk.co.amlcurran.queues.core.persisters.Persisters;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class QueuePresenterTests {

    @Test
    public void loadingWillRetrieveTheQueueFromThePersister() {
        long queueId = 212;
        QueuePersister queuePersister = Persisters.singleQueueWithId(queueId);
        AssertingQueueView queueView = new AssertingQueueView();
        QueueList queueList = new QueueList(queuePersister);
        queueList.load();

        QueuePresenter presenter = new QueuePresenter(queueId, queueView, queueList);
        presenter.load();

        assertThat(queueView.shownQueue.getId(), is(queueId));
    }

    @Test
    public void addingAnItemNotifiesTheView() {
        long queueId = 212;
        QueuePersister queuePersister = Persisters.singleQueueWithId(queueId);
        AssertingQueueView queueView = new AssertingQueueView();
        QueueList queueList = new QueueList(queuePersister);
        queueList.load();
        QueueItem queueItem = new QueueItem("hello");

        QueuePresenter presenter = new QueuePresenter(queueId, queueView, queueList);
        presenter.load();
        presenter.addItem(queueItem);

        assertThat(queueView.addedItem, is(queueItem));
    }

    private class AssertingQueueView implements QueueView {
        public Queue shownQueue;
        public QueueItem addedItem;

        @Override
        public void show(Queue queue) {
            shownQueue = queue;
        }

        @Override
        public void itemAdded(QueueItem queueItem) {
            addedItem = queueItem;
        }
    }
}
