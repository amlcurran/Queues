package uk.co.amlcurran.queues.core;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class QueueListPresenterTests {

    private static final QueueListView UNUSED_VIEW = null;
    private static final NavigationController UNUSED_NAV = null;
    private String title = "hello";

    @Test
    public void whenAnItemIsAddedTheViewIsUpdated() {
        AssertingQueueListView queueListView = new AssertingQueueListView();
        QueueList queueList = new QueueList(NullQueuePersister.empty());
        QueueListPresenter queueListPresenter = new QueueListPresenter(queueListView, UNUSED_NAV, queueList);
        queueListPresenter.start();

        queueList.add(queueList.newQueue(title));

        assertThat(queueListView.addedItem, is(0));
    }

    @Test
    public void whenAnItemIsRemovedTheViewIsUpdated() {
        AssertingQueueListView queueListView = new AssertingQueueListView();
        QueueList queueList = new QueueList(NullQueuePersister.empty());
        QueueListPresenter queueListPresenter = new QueueListPresenter(queueListView, UNUSED_NAV, queueList);
        queueListPresenter.start();

        Queue queue = queueList.newQueue(title);
        queueList.add(queueList.newQueue(title));
        queueList.add(queue);
        queueList.remove(queue);

        assertThat(queueListView.removedItem, is(1));
    }

    @Test
    public void wheAnItemIsSelected_TheNavigationControllerMovesToQueueView() {
        AssertingNavigationController navigationController = new AssertingNavigationController();
        QueueList queueList = new QueueList(TestQueuePersister.withNumberOfQueues(2));
        QueueListPresenter queueListPresenter = new QueueListPresenter(UNUSED_VIEW, navigationController, queueList);
        queueListPresenter.start();

        queueListPresenter.selectedQueue(1);

        assertThat(navigationController.queueViewed, is(queueList.all().get(1).getId()));

    }

    private static class AssertingNavigationController implements NavigationController {
        public long queueViewed;

        @Override
        public void viewQueue(Queue queue) {
            queueViewed = queue.getId();
        }
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
