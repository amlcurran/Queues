package uk.co.amlcurran.queues.core;

import java.util.List;

public interface QueueView {
    QueueListView NULL_IMPL = new QueueListView() {
        @Override
        public void queueAdded(Queue queue, int position) {

        }

        @Override
        public void queueRemoved(Queue queue, int position) {

        }

        @Override
        public void show(List<Queue> queues) {

        }
    };

    void show(Queue queue);

    void itemAdded(QueueItem queueItem);

    void itemRemoved(QueueItem item);

    void notEmpty();

    void empty();
}
