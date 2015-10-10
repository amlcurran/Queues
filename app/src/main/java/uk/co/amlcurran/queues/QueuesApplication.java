package uk.co.amlcurran.queues;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.Collections;

import uk.co.amlcurran.queues.core.Queue;
import uk.co.amlcurran.queues.core.QueueItem;
import uk.co.amlcurran.queues.core.QueueList;
import uk.co.amlcurran.queues.core.QueuePersister;

public class QueuesApplication extends Application {

    private QueueList queueList;
    private QueuePersister queuePersister;

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "bI8ntsSfiXUQ7smRDZDFKmOqKyTmaM8NVwPsMjfU", "9lGVfr2BgmSxgiLbt4Jqq4onZu76S0Vms7HLM0cI");
        queuePersister = new QueuePersister() {

            @Override
            public void addItemToQueue(String queueId, QueueItem queueItem) {

            }

            @Override
            public void removeItemFromQueue(String queueId, QueueItem queueItem) {

            }

            @Override
            public void queues(LoadCallbacks callbacks) {
                callbacks.loaded(Collections.<Queue>emptyList());
            }

            @Override
            public void saveQueue(Queue queue, Callbacks callbacks) {
                ParseObject parseObject = new ParseObject("Queue");
                parseObject.put("id", queue.getId());
                parseObject.put("title", queue.getTitle());
                try {
                    parseObject.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                    callbacks.failedToSave(queue);
                }
            }

            @Override
            public String uniqueId() {
                return ParseObject.c;
            }

            @Override
            public String uniqueItemId() {
                return null;
            }

            @Override
            public void deleteQueue(Queue queue, Callbacks callbacks) {

            }

            @Override
            public boolean requiresUserIntervention() {
                return false;
            }
        };
        queueList = new QueueList(queuePersister);
    }

    public static QueueList queueList(Context context) {
        return ((QueuesApplication) context.getApplicationContext()).queueList;
    }

    public static boolean userInterventionRequired(Context context) {
        return persister(context).requiresUserIntervention();
    }

    private static QueuePersister persister(Context context) {
        return ((QueuesApplication) context.getApplicationContext()).queuePersister;
    }

    public static void resolveUserIntervention(Activity activity) {
        ((DropboxPersister) persister(activity)).linkAccount(activity);
    }

    public static boolean handleUserIntervention(Activity activity, int requestCode, int resultCode) {
        return ((DropboxPersister) persister(activity)).handleAccountLinkAttempt(requestCode, resultCode);
    }
}
