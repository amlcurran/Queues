package uk.co.amlcurran.queues;

import android.app.Application;
import android.content.Context;

import uk.co.amlcurran.queues.core.QueueList;

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

}
