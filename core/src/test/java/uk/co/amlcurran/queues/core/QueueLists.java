package uk.co.amlcurran.queues.core;

import java.util.ArrayList;

import uk.co.amlcurran.queues.core.persisters.Persisters;

public class QueueLists {
    static QueueList sameThreadQueueList(QueuePersister queuePersister) {
        return new QueueList(queuePersister);
    }

    static QueueList singleQueueListWithItems(final int numberItems, final long queueId) {
        final QueuePersister queuePersister = Persisters.singleQueueWithId(queueId);
        return new QueueList(queuePersister) {

            @Override
            public Queue queueById(long queueId) {
                if (queueId == 212) {
                    ArrayList<QueueItem> queueItems = new ArrayList<>();
                    for (int i = 0; i < numberItems; i++) {
                        queueItems.add(new QueueItem(i, "A label"));
                    }
                    return new Queue("hello", queueId, queuePersister, queueItems);
                }
                return null;
            }
        };
    }
}
