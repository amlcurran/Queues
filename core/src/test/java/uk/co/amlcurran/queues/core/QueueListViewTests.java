package uk.co.amlcurran.queues.core;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class QueueListViewTests {

    private String title = "hello";

    @Test
    public void whenAnItemIsAddedTheViewIsUpdated() {
        AssertingQueueListView queueListView = new AssertingQueueListView();
        QueueList queueList = new QueueList(NullQueuePersister.empty());
        QueueListPresenter queueListPresenter = new QueueListPresenter(queueListView, queueList);
        queueListPresenter.start();

        queueList.add(queueList.newQueue(title));

        assertThat(queueListView.addedItem, is(0));
    }

    @Test
    public void whenAnItemIsRemovedTheViewIsUpdated() {
        AssertingQueueListView queueListView = new AssertingQueueListView();
        QueueList queueList = new QueueList(NullQueuePersister.empty());
        QueueListPresenter queueListPresenter = new QueueListPresenter(queueListView, queueList);
        queueListPresenter.start();

        Queue queue = queueList.newQueue(title);
        queueList.add(queueList.newQueue(title));
        queueList.add(queue);
        queueList.remove(queue);

        assertThat(queueListView.removedItem, is(1));
    }

    private class AssertingQueueListView implements QueueListView {

        public int addedItem;
        public int removedItem;

        @Override
        public void itemAdded(int position) {
            addedItem = position;
        }

        @Override
        public void itemRemoved(int position) {
            removedItem = position;
        }
    }

}
