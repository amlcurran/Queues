package uk.co.amlcurran.queues.share;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import uk.co.amlcurran.queues.R;
import uk.co.amlcurran.queues.core.Queue;

class QueueListAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final List<Queue> queueSource = new ArrayList<>();
    private final LayoutInflater layoutInflater;
    private final QueueListSelectionListener queueListSelectionListener;

    public QueueListAdapter(LayoutInflater layoutInflater, QueueListSelectionListener queueListSelectionListener) {
        this.layoutInflater = layoutInflater;
        this.queueListSelectionListener = queueListSelectionListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.item_queue_list_small, viewGroup, false);
        return new ViewHolder(view, new ViewHolder.SelectionListener() {

            @Override
            public void selectedItem(int position) {
                queueListSelectionListener.selectedQueue(queueSource.get(position));
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bind(queueSource.get(i));
    }

    public void addAll(List<Queue> queues) {
        queueSource.clear();
        queueSource.addAll(queues);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return queueSource.size();
    }

    public void clear() {
        queueSource.clear();
    }

    public interface QueueListSelectionListener {
        void selectedQueue(Queue queue);

    }

}
