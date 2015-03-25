package uk.co.amlcurran.queues;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.co.amlcurran.queues.core.Queue;
import uk.co.amlcurran.queues.core.QueueList;

class QueueListAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final QueueList queueList;

    private LayoutInflater layoutInflater;

    public QueueListAdapter(QueueList queueList, LayoutInflater layoutInflater) {
        this.queueList = queueList;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Queue queue = queueList.all().get(i);
        ((TextView) viewHolder.itemView).setText("" + queue.getId());
    }

    @Override
    public int getItemCount() {
        return queueList.size();
    }

}
