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

    private class QueuePresenter {

        private final long queueId;
        private final QueueView queueView;
        private final QueueList queueList;

        public QueuePresenter(long queueId, QueueView queueView, QueueList queueList) {
            this.queueId = queueId;
            this.queueView = queueView;
            this.queueList = queueList;
        }

        public void load() {
            Queue queue = queueList.queueById(queueId);
            queueView.show(queue);
        }
    }

    private class AssertingQueueView implements QueueView {
        public Queue shownQueue;

        @Override
        public void show(Queue queue) {
            shownQueue = queue;
        }
    }
}
