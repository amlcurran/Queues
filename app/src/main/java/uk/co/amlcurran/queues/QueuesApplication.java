package uk.co.amlcurran.queues;

import android.app.Application;
import android.content.Context;

import uk.co.amlcurran.queues.core.QueueList;

public class QueuesApplication extends Application {

    private QueueList queueList;
    private DropboxPersister queuePersister;

    @Override
    public void onCreate() {
        super.onCreate();
        queuePersister = new DropboxPersister(this, new DatastoreProvider.Delegate() {
            @Override
            public void hasUserResolvableAction() {

            }
        });
        queueList = new QueueList(queuePersister);
    }

    public static QueueList queueList(Context context) {
        return ((QueuesApplication) context.getApplicationContext()).queueList;
    }

    public static boolean userInterventionRequired(Context context) {
        return ((QueuesApplication) context.getApplicationContext()).queuePersister.requiresUserIntervention();
    }
}
