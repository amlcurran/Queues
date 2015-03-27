package uk.co.amlcurran.queues.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.amlcurran.queues.core.Queue;
import uk.co.amlcurran.queues.core.Source;

class QueueListAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final Source<Queue> queueSource;
    private final LayoutInflater layoutInflater;
    private final QueueListSelectionListener queueListSelectionListener;

    public QueueListAdapter(Source<Queue> queueSource, LayoutInflater layoutInflater, QueueListSelectionListener queueListSelectionListener) {
        this.queueSource = queueSource;
        this.layoutInflater = layoutInflater;
        this.queueListSelectionListener = queueListSelectionListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
        return new ViewHolder(view, queueListSelectionListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bind(queueSource.get(i));
    }

    @Override
    public int getItemCount() {
        return queueSource.size();
    }

    public interface QueueListSelectionListener {
        void selectedQueue(int position);
    }

}
