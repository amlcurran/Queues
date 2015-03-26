package uk.co.amlcurran.queues;

import uk.co.amlcurran.queues.core.Queue;
import uk.co.amlcurran.queues.core.QueueList;

public class QueueListController {
    private final QueueListView queueListView;
    private final QueueList queueList;

    public QueueListController(QueueListView queueListView, QueueList queueList) {
        this.queueListView = queueListView;
        this.queueList = queueList;
    }

    QueueList getQueueList() {
        return queueList;
    }

    void start() {
        queueList.addCallbacks(updateSelfListener);
    }

    void stop() {
        queueList.removeCallbacks(updateSelfListener);
    }

    QueueList.ListListener updateSelfListener = new QueueList.ListListener() {
        @Override
        public void queueAdded(Queue queue) {
            int position = queueList.positionFromQueue(queue);
            queueListView.itemAdded(position);
        }
    };
}