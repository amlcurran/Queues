package uk.co.amlcurran.queues;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import uk.co.amlcurran.queues.core.QueueListController;
import uk.co.amlcurran.queues.core.QueueListView;

public class QueueListFragment extends Fragment implements QueueListView {

    private QueueListController queueListController;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private TextView newTitleEntry;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_queue, container, false);
        recyclerView = (RecyclerView) inflate.findViewById(R.id.queue_list);
        newTitleEntry = ((TextView) inflate.findViewById(R.id.queue_title_entry));
        return inflate;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        queueListController = new QueueListController(this, QueuesApplication.queueList(getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new QueueListAdapter(queueListController.createQueueSource(), LayoutInflater.from(getActivity()));
        recyclerView.setAdapter(adapter);
        newTitleEntry.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String title = String.valueOf(newTitleEntry.getText());
                    QueuesApplication.queueList(getActivity()).addNewQueue(title);
                    newTitleEntry.setText("");
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        queueListController.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        queueListController.stop();
    }

    @Override
    public void itemAdded(int position) {
        adapter.notifyItemInserted(position);
    }

    @Override
    public void itemRemoved(int position) {
        adapter.notifyItemRemoved(position);
    }

}
