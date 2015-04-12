package uk.co.amlcurran.queues.queue;

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

import uk.co.amlcurran.queues.QueuesApplication;
import uk.co.amlcurran.queues.R;
import uk.co.amlcurran.queues.core.Queue;
import uk.co.amlcurran.queues.core.QueueItem;
import uk.co.amlcurran.queues.core.QueuePresenter;
import uk.co.amlcurran.queues.core.QueueView;

public class QueueFragment extends Fragment implements QueueView {

    private TextView newItemEntry;
    private RecyclerView items;
    private QueueAdapter adapter;
    private QueuePresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_queue, container, false);
        newItemEntry = (TextView) view.findViewById(R.id.queue_item_entry);
        items = (RecyclerView) view.findViewById(R.id.queue_items);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new QueuePresenter(getArguments().getLong("id"), this, QueuesApplication.queueList(getActivity()));
        adapter = new QueueAdapter(LayoutInflater.from(getActivity()), new QueueAdapter.QueueSelectionListener() {
            @Override
            public void itemSecondarySelected(int position) {
                presenter.removeItem(position);
            }
        });
        items.setLayoutManager(new LinearLayoutManager(getActivity()));
        items.setAdapter(adapter);
        presenter.load();
        newItemEntry.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String title = String.valueOf(newItemEntry.getText());
                    presenter.addItem(title);
                    newItemEntry.setText("");
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void show(Queue queue) {
        adapter.addAll(queue.all());
    }

    @Override
    public void itemAdded(QueueItem queueItem) {
        adapter.add(queueItem);
    }

    @Override
    public void itemRemoved(QueueItem item) {
        adapter.remove(item);
    }

    public static QueueFragment withId(long id) {
        QueueFragment queueFragment = new QueueFragment();
        Bundle args = new Bundle();
        args.putLong("id", id);
        queueFragment.setArguments(args);
        return queueFragment;
    }
}
