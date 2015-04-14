package uk.co.amlcurran.queues.core;

import java.util.ArrayList;

import uk.co.amlcurran.queues.core.persisters.Persisters;

public class QueueLists {
    static QueueList sameThreadQueueList(QueuePersister queuePersister) {
        return new QueueList(queuePersister);
    }

    static QueueList singleQueueListWithItems(final int numberItems, final String inputId) {
        final QueuePersister queuePersister = Persisters.singleQueueWithId(inputId);
        return new QueueList(queuePersister) {

            @Override
            public Queue queueById(String queueId) {
                if (queueId.equals(inputId)) {
                    ArrayList<QueueItem> queueItems = new ArrayList<>();
                    for (int i = 0; i < numberItems; i++) {
                        queueItems.add(new QueueItem(String.valueOf(i), "A label"));
                    }
                    return new Queue("hello", queueId, queuePersister, queueItems);
                }
                return null;
            }
        };
    }
}
