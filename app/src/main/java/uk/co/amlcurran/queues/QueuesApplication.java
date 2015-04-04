package uk.co.amlcurran.queues;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.co.amlcurran.queues.core.Queue;
import uk.co.amlcurran.queues.core.QueueItem;
import uk.co.amlcurran.queues.core.QueueList;
import uk.co.amlcurran.queues.core.QueuePersister;

public class QueuesApplication extends Application {

    private QueueList queueList;

    @Override
    public void onCreate() {
        super.onCreate();
        queueList = new QueueList(new SQLitePersister(this));
    }

    public static QueueList queueList(Context context) {
        return ((QueuesApplication) context.getApplicationContext()).queueList;
    }

    private static class InMemoryPersister implements QueuePersister {

        private final List<Queue> queues = new ArrayList<>();

        @Override
        public void addItemToQueue(final long queueId, final QueueItem queueItem) {
        }

        @Override
        public void removeItemFromQueue(final long queueId, final QueueItem queueItem) {

        }

        @Override
        public void queues(LoadCallbacks callbacks) {
            callbacks.loaded(Collections.unmodifiableList(queues));
        }

        @Override
        public void saveQueue(final Queue queue, final Callbacks callbacks) {
            queues.add(queue);
        }

        @Override
        public long uniqueId() {
            return queues.size();
        }

        @Override
        public void deleteQueue(Queue queue, Callbacks callbacks) {
            queues.remove(queue);
        }
    }
}
