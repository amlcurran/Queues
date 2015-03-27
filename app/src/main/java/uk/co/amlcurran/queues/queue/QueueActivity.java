package uk.co.amlcurran.queues.queue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import uk.co.amlcurran.queues.R;
import uk.co.amlcurran.queues.core.Queue;

public class QueueActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);
    }

    public static Intent viewQueue(Activity activity, Queue queue) {
        return new Intent(activity, QueueActivity.class);
    }
}
