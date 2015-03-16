package uk.co.amlcurran.queues.core;

import java.util.ArrayList;
import java.util.List;

public class Queue {
    private final QueuePersister queuePersister;
    private final List<QueueItem> queueItems;
    private long id;
    private int iteratorIndex = 0;

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
        QueueItem item = queueItems.get(iteratorIndex);
        iteratorIndex++;
        return item;
    }

    public void removeItem(QueueItem item) {
        queueItems.remove(item);
        queuePersister.removeItemFromQueue(id, item);
    }
}
