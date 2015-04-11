package uk.co.amlcurran.queues.list;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import uk.co.amlcurran.queues.QueuesApplication;
import uk.co.amlcurran.queues.R;
import uk.co.amlcurran.queues.core.Queue;
import uk.co.amlcurran.queues.core.QueueList;

public class QueueListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue_list);

        getFragmentManager().beginTransaction()
                .replace(R.id.content, new QueueListFragment())
                .commit();
    }

}
