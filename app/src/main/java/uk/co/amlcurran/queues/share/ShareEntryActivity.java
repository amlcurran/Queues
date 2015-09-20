package uk.co.amlcurran.queues.share;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

import uk.co.amlcurran.queues.QueuesApplication;
import uk.co.amlcurran.queues.R;
import uk.co.amlcurran.queues.core.Queue;

public class ShareEntryActivity extends ActionBarActivity {

    private RecyclerView recyclerView;
    private QueueListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        final String sharedText = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        ((TextView) findViewById(R.id.sharing)).setText(sharedText);

        recyclerView = (RecyclerView) findViewById(R.id.queues_list);
        adapter = new QueueListAdapter(getLayoutInflater(), new QueueListAdapter.QueueListSelectionListener() {

            @Override
            public void selectedQueue(Queue queue) {
                queue.addItem(sharedText);
                finish();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.clear();
        List<Queue> all = QueuesApplication.queueList(this).all();
        adapter.addAll(all);
    }
}
