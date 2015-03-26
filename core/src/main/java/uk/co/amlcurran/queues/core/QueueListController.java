package uk.co.amlcurran.queues.core;

public class QueueListController {
    private final QueueListView queueListView;
    private final QueueList queueList;

    public QueueListController(QueueListView queueListView, QueueList queueList) {
        this.queueListView = queueListView;
        this.queueList = queueList;
    }

    public void start() {
        queueList.addCallbacks(updateSelfListener);
    }

    public void stop() {
        queueList.removeCallbacks(updateSelfListener);
    }

    public Source<Queue> createQueueSource() {
        return new BasicQueueSource(queueList);
    }

    private QueueList.ListListener updateSelfListener = new QueueList.ListListener() {
        @Override
        public void queueAdded(Queue queue) {
            int position = queueList.positionFromQueue(queue);
            queueListView.itemAdded(position);
        }
    };

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