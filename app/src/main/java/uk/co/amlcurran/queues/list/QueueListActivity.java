package uk.co.amlcurran.queues.list;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import uk.co.amlcurran.queues.QueuesApplication;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_queue_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_dropbox_link).setVisible(QueuesApplication.userInterventionRequired(this));
        return super.onPrepareOptionsMenu(menu);
    }
}
