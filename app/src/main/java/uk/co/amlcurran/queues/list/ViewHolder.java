package uk.co.amlcurran.queues.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import uk.co.amlcurran.queues.core.Queue;

class ViewHolder extends RecyclerView.ViewHolder {

    public ViewHolder(View itemView, final QueueListAdapter.QueueListSelectionListener queueListSelectionListener) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queueListSelectionListener.selectedQueue(getPosition());
            }
        });
    }

    void bind(Queue queue) {
        ((TextView) itemView).setText(String.format("%1$s : %2$s", queue.getTitle(), firstItemSummary(queue)));
    }

    private CharSequence firstItemSummary(Queue queue) {
        if (queue.size() == 0) {
            return "No items";
        } else {
            return String.format("%1$d, %2$s", queue.size(), queue.firstItem().getLabel());
        }
    }
}
