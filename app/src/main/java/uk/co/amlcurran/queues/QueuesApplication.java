package uk.co.amlcurran.queues;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import uk.co.amlcurran.queues.core.QueueList;

public class QueuesApplication extends Application {

    private QueueList queueList;
    private DropboxPersister queuePersister;

    @Override
    public void onCreate() {
        super.onCreate();
        queuePersister = new DropboxPersister(this);
        queueList = new QueueList(queuePersister);
    }

    public static QueueList queueList(Context context) {
        return ((QueuesApplication) context.getApplicationContext()).queueList;
    }

    public static boolean userInterventionRequired(Context context) {
        return persister(context).requiresUserIntervention();
    }

    private static DropboxPersister persister(Context context) {
        return ((QueuesApplication) context.getApplicationContext()).queuePersister;
    }

    public static void resolveUserIntervention(Activity activity) {
        persister(activity).linkAccount(activity);
    }

    public static boolean handleUserIntervention(Activity activity, int requestCode, int resultCode) {
        return persister(activity).handleAccountLinkAttempt(requestCode, resultCode);
    }
}
