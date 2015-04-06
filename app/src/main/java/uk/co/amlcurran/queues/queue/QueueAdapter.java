package uk.co.amlcurran.queues.queue;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import uk.co.amlcurran.queues.core.QueueItem;

class QueueAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final List<QueueItem> queueSource;
    private final LayoutInflater layoutInflater;

    public QueueAdapter(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
        this.queueSource = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bind(queueSource.get(i));
    }

    @Override
    public int getItemCount() {
        return queueSource.size();
    }

    public void addAll(List<QueueItem> queueItems) {
        queueSource.addAll(queueItems);
        notifyDataSetChanged();
    }

    public void add(QueueItem queueItem) {
        queueSource.add(queueItem);
        notifyItemInserted(queueSource.size() - 1);
    }

    public void remove(QueueItem item) {
        int index = queueSource.indexOf(item);
        queueSource.remove(index);
        notifyItemRemoved(index);
    }
}
