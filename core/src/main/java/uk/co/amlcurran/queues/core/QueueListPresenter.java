package uk.co.amlcurran.queues.core;

public class QueueListPresenter {
    private final QueueListView queueListView;
    private final NavigationController navigationController;
    private final QueueList queueList;

    public QueueListPresenter(QueueListView queueListView, NavigationController navigationController, QueueList queueList) {
        this.queueListView = queueListView;
        this.navigationController = navigationController;
        this.queueList = queueList;
    }

    public void start() {
        queueList.load();
        queueList.addCallbacks(updateSelfListener);
        queueListView.show(queueList.all());
    }

    public void stop() {
        queueList.removeCallbacks(updateSelfListener);
    }

    public void selectedQueue(int position) {
        navigationController.viewQueue(queueList.all().get(position));
    }

    public Source<Queue> createQueueSource() {
        return new BasicQueueSource(queueList);
    }

    private final QueueList.ListListener updateSelfListener = new QueueList.ListListener() {
        @Override
        public void queueAdded(Queue queue) {
            int position = queueList.positionFromQueue(queue);
            queueListView.queueAdded(queue, position);
        }

        @Override
        public void queueRemoved(Queue queue, int removedPosition) {
            queueListView.queueRemoved(queue, removedPosition);
        }
    };

    public void deleteQueue(int position) {
        queueList.remove(queueList.all().get(position));
    }

    private static class BasicQueueSource implements Source<Queue> {
        private final QueueList queueList;

        public BasicQueueSource(QueueList queueList) {
            this.queueList = queueList;
        }

        @Override
        public Queue get(int position) {
            return queueList.all().get(position);
        }

        @Override
        public int size() {
            return queueList.size();
        }
    }
}