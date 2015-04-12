package uk.co.amlcurran.queues.list;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import uk.co.amlcurran.queues.R;

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
