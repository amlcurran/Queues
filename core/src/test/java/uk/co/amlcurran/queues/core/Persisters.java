package uk.co.amlcurran.queues.core;

import java.util.Collections;

public class Persisters {
    public static QueuePersister saveFailing() {
        return new NullQueuePersister(Collections.<Queue>emptyList()) {

            @Override
            public void saveQueue(Queue queue, Callbacks callbacks) {
                callbacks.failedToSave(queue);
            }
        };
    }
}
