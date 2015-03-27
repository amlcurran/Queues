package uk.co.amlcurran.queues.core;

public class QueueLists {
    static QueueList sameThreadQueueList(QueuePersister queuePersister) {
        return new QueueList(queuePersister);
    }
}
