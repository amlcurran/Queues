package uk.co.amlcurran.queues;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.amlcurran.queues.core.QueueList;

public class QueueListFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_queue, container, false);
        recyclerView = (RecyclerView) inflate.findViewById(R.id.queue_list);
        return inflate;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        QueueList queueList = QueuesApplication.queueList(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new QueueListAdapter(queueList, LayoutInflater.from(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    public void poke() {
        adapter.notifyDataSetChanged();
    }

}
