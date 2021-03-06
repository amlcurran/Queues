package uk.co.amlcurran.queues.list;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import java.util.List;

import uk.co.amlcurran.queues.ActivityNavigationController;
import uk.co.amlcurran.queues.QueuesApplication;
import uk.co.amlcurran.queues.R;
import uk.co.amlcurran.queues.core.Queue;
import uk.co.amlcurran.queues.core.QueueListPresenter;
import uk.co.amlcurran.queues.core.QueueListView;

public class QueueListFragment extends Fragment implements QueueListView {

    private QueueListPresenter queueListPresenter;
    private RecyclerView recyclerView;
    private QueueListAdapter adapter;
    private TextView newTitleEntry;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_queue_list, container, false);
        recyclerView = (RecyclerView) inflate.findViewById(R.id.queue_list);
        newTitleEntry = ((TextView) inflate.findViewById(R.id.queue_title_entry));
        return inflate;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        queueListPresenter = new QueueListPresenter(this, new ActivityNavigationController(getActivity()), QueuesApplication.queueList(getActivity()));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(getResources().getInteger(R.integer.list_queue_columns), StaggeredGridLayoutManager.VERTICAL));
        adapter = new QueueListAdapter(LayoutInflater.from(getActivity()), new QueueListAdapter.QueueListSelectionListener() {
            @Override
            public void selectedQueue(int position) {
                queueListPresenter.selectedQueue(position);
            }

            @Override
            public void secondarySelectedQueue(int position) {
                queueListPresenter.deleteQueue(position);
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
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        queueListPresenter.stop();
    }

    @Override
    public void queueAdded(Queue queue, int position) {
        adapter.add(queue, position);
    }

    @Override
    public void queueRemoved(Queue queue, int position) {
        adapter.remove(queue, position);
    }

    @Override
    public void show(List<Queue> queues) {
        adapter.addAll(queues);
    }

}
