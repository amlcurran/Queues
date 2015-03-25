package uk.co.amlcurran.queues.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QueueList {
    private final QueuePersister queuePersister;
    private final List<Queue> queues;

    public QueueList(QueuePersister queuePersister) {
        this.queuePersister = queuePersister;
        this.queues = new ArrayList<>(queuePersister.queues());
    }

    public int size() {
        return queues.size();
    }

    public void add(Queue queue) {
        queues.add(queue);
        queuePersister.saveQueue(queue, null);
    }

    public Queue newQueue() {
        return Queue.withPersister(queuePersister);
    }

    public List<Queue> all() {
        return Collections.unmodifiableList(queues);
    }
}
