package uk.co.amlcurran.queues.core;

public class QueueFactory {
    static Queue withPersister(QueuePersister persister) {
        return Queue.withPersister("ANY_TITLE", persister);
    }
}
