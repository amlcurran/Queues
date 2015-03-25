package uk.co.amlcurran.queues;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.co.amlcurran.queues.core.Queue;
import uk.co.amlcurran.queues.core.QueueList;

public class QueueFragment extends Fragment {

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
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final QueueList queueList = QueuesApplication.queueList(getActivity());
        adapter = new RecyclerView.Adapter() {

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int i) {
                View view = LayoutInflater.from(getActivity()).inflate(android.R.layout.simple_list_item_1, viewGroup, false);
                return new RecyclerView.ViewHolder(view) { };
            }

            @Override
            public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int i) {
                Queue queue = queueList.all().get(i);
                ((TextView) viewHolder.itemView).setText("" + queue.getId());
            }

            @Override
            public int getItemCount() {
                return queueList.size();
            }
        };
        recyclerView.setAdapter(adapter);
    }

    public void poke() {
        adapter.notifyDataSetChanged();
    }
}
