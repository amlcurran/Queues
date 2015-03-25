package uk.co.amlcurran.queues;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import uk.co.amlcurran.queues.core.QueueList;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction()
                .replace(R.id.content, new QueueFragment())
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final boolean createOptionsMenu = super.onCreateOptionsMenu(menu);
        menu.add("Add");
        return createOptionsMenu;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if ("Add".equals(item.getTitle())) {
            addNewQueue();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addNewQueue() {
        QueueList queueList = QueuesApplication.queueList(this);
        queueList.add(queueList.newQueue());
        ((QueueFragment) getFragmentManager().findFragmentById(R.id.content)).poke();
    }
}
