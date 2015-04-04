package uk.co.amlcurran.queues.queue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import uk.co.amlcurran.queues.R;
import uk.co.amlcurran.queues.core.Queue;

public class QueueActivity extends ActionBarActivity {

    private static final String QUEUE_TITLE = "queue_title";
    private static final String QUEUE_ID = "queue_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);

        String title = getIntent().getStringExtra(QUEUE_TITLE);
        getSupportActionBar().setTitle(title);

//        long id = getIntent().getLongExtra(QUEUE_ID, -1);
//        QueuesApplication.queueList(this).
        getFragmentManager().beginTransaction()
                .replace(R.id.content, new QueueFragment())
                .commit();
    }

    public static Intent viewQueue(Activity activity, Queue queue) {
        Intent intent = new Intent(activity, QueueActivity.class);
        intent.putExtra(QUEUE_TITLE, queue.getTitle());
        intent.putExtra(QUEUE_ID, queue.getId());
        return intent;
    }
}
