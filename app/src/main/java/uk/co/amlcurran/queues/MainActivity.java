package uk.co.amlcurran.queues;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import uk.co.amlcurran.queues.core.Queue;
import uk.co.amlcurran.queues.core.QueueList;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        if ("Add".equals(item.getTitle())) {
            addNewQueue();
        } else if ("Remove".equals(item.getTitle())) {
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

    private void addNewQueue() {
        QueueList queueList = QueuesApplication.queueList(this);
        queueList.add(queueList.newQueue("Boo"));
    }
}
