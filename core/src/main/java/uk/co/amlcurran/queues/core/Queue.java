package uk.co.amlcurran.queues.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Queue {
    private final QueuePersister queuePersister;
    private final List<QueueItem> queueItems;
    private long id;
    private QueueItem queueItem;
    private Iterator<QueueItem> iterator;

    public Queue(QueuePersister queuePersister) {
        this.queuePersister = queuePersister;
        this.queueItems = new ArrayList<>();
    }

    public void addItem(QueueItem queueItem) {
        queueItems.add(queueItem);
        queuePersister.addItemToQueue(id, queueItem);
    }

    public long getId() {
        return id;
    }

    public QueueItem next() {
        if (iterator == null) {
            iterator = queueItems.iterator();
        }
        return iterator.next();
    }
}
