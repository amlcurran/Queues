package uk.co.amlcurran.queues.core;

public interface QueueView {
    void show(Queue queue);

    void itemAdded(QueueItem queueItem);
}
