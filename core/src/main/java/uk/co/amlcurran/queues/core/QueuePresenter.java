package uk.co.amlcurran.queues.core;

public class QueuePresenter {

    private final String queueId;
    private final QueueView queueView;
    private final QueueList queueList;
    private Queue queue;

    public QueuePresenter(String queueId, QueueView queueView, QueueList queueList) {
        this.queueId = queueId;
        this.queueView = queueView;
        this.queueList = queueList;
    }

    public void load() {
        if (!queueList.isLoaded()) {
            queueList.load();
        }
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

            @Override
            public void notEmpty() {
                queueView.notEmpty();
            }

            @Override
            public void empty() {
                queueView.empty();
            }
        });
        if (queue.size() == 0) {
            queueView.empty();
        } else {
            queueView.notEmpty();
        }
        queueView.show(queue);
    }

    public void addItem(String label) {
        queue.addItem(label);
    }

    public void removeItem(QueueItem queueItem) {
        queue.removeItem(queueItem);
    }

    public void removeItem(int position) {
        removeItem(queue.all().get(position));
    }
}
