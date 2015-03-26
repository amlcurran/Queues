package uk.co.amlcurran.queues.core;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class QueueListViewTests {

    @Test
    public void whenAnItemIsAddedTheViewIsUpdated() {
        AssertingQueueListView queueListView = new AssertingQueueListView();
        QueueList queueList = new QueueList(new NullQueuePersister());
        QueueListController queueListController = new QueueListController(queueListView, queueList);
        queueListController.start();

        queueList.add(queueList.newQueue());

        assertThat(queueListView.addedItem, is(true));
    }

    private class AssertingQueueListView implements QueueListView {

        public boolean addedItem;

        @Override
        public void itemAdded(int position) {
            addedItem = true;
        }
    }

}
