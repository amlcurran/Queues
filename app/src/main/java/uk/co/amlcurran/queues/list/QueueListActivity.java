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

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final boolean createOptionsMenu = super.onCreateOptionsMenu(menu);
        menu.add("Add");
        menu.add("Remove");
        return createOptionsMenu;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if ("Remove".equals(item.getTitle())) {
            removeQueue();
        }
        return super.onOptionsItemSelected(item);
    }

    private void removeQueue() {
        QueueList queueList = QueuesApplication.queueList(this);
        if (queueList.size() > 0) {
            Queue queue = queueList.all().get(0);
            queueList.remove(queue);
        }
    }

}
