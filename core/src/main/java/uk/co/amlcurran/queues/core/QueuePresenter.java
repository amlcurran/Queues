package uk.co.amlcurran.queues.core;

public class QueuePresenter {

    private final long queueId;
    private final QueueView queueView;
    private final QueueList queueList;
    private Queue queue;

    public QueuePresenter(long queueId, QueueView queueView, QueueList queueList) {
        this.queueId = queueId;
        this.queueView = queueView;
        this.queueList = queueList;
    }

    public void load() {
        queue = queueList.queueById(queueId);
        queue.addListener(new Queue.QueueListener() {
            @Override
            public void itemAdded(QueueItem queueItem) {
                queueView.itemAdded(queueItem);
            }

            @Override
            public void itemRemoved(QueueItem item) {
                queueView.itemRemoved(item);
            }
        });
        queueView.show(queue);
    }

    public void addItem(QueueItem queueItem) {
        queue.addItem(queueItem);
    }

    public void removeItem(QueueItem queueItem) {
        queue.removeItem(queueItem);
    }

}
