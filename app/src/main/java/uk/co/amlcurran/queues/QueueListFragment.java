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
import android.widget.Toast;

import uk.co.amlcurran.queues.core.NavigationController;
import uk.co.amlcurran.queues.core.Queue;
import uk.co.amlcurran.queues.core.QueueListPresenter;
import uk.co.amlcurran.queues.core.QueueListView;

public class QueueListFragment extends Fragment implements QueueListView {

    private QueueListPresenter queueListPresenter;
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
        queueListPresenter = new QueueListPresenter(this, new NavigationController() {
            @Override
            public void viewQueue(Queue queue) {
                Toast.makeText(getActivity(), "View me this queue: " + queue.getTitle(), Toast.LENGTH_SHORT).show();
            }
        }, QueuesApplication.queueList(getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new QueueListAdapter(queueListPresenter.createQueueSource(), LayoutInflater.from(getActivity()), new QueueListAdapter.QueueListSelectionListener() {
            @Override
            public void selectedQueue(int position) {
                queueListPresenter.selectedQueue(position);
            }
        });
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
        queueListPresenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        queueListPresenter.stop();
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
