package uk.co.amlcurran.queues.core;

import java.util.List;

public interface QueueListView {
    void queueAdded(Queue queue, int position);

    void queueRemoved(Queue queue, int position);

    void show(List<Queue> queues);
}
