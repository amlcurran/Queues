package uk.co.amlcurran.queues.core;

public class QueueListController {
    private final QueueListView queueListView;
    private final QueueList queueList;

    public QueueListController(QueueListView queueListView, QueueList queueList) {
        this.queueListView = queueListView;
        this.queueList = queueList;
    }

    public QueueList getQueueList() {
        return queueList;
    }

    public void start() {
        queueList.addCallbacks(updateSelfListener);
    }

    public void stop() {
        queueList.removeCallbacks(updateSelfListener);
    }

    private QueueList.ListListener updateSelfListener = new QueueList.ListListener() {
        @Override
        public void queueAdded(Queue queue) {
            int position = queueList.positionFromQueue(queue);
            queueListView.itemAdded(position);
        }
    };
}