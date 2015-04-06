package uk.co.amlcurran.queues.queue;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.co.amlcurran.queues.R;

public class QueueFragment extends Fragment {

    private TextView newItemEntry;
    private RecyclerView items;
    private RecyclerView.Adapter adapter;

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
        items.setLayoutManager(new LinearLayoutManager(getActivity()));

    }
}
