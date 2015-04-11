package uk.co.amlcurran.queues.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import uk.co.amlcurran.queues.R;
import uk.co.amlcurran.queues.core.Queue;

class ViewHolder extends RecyclerView.ViewHolder {

    private final QueueStackView stack;
    private final TextView title;
    private final TextView firstItem;

    public ViewHolder(View itemView, final QueueListAdapter.QueueListSelectionListener queueListSelectionListener) {
        super(itemView);
        stack = (QueueStackView) itemView.findViewById(R.id.queue_stack);
        title = ((TextView) itemView.findViewById(R.id.queue_title));
        firstItem = ((TextView) itemView.findViewById(R.id.queue_first));
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queueListSelectionListener.selectedQueue(getPosition());
            }
        });
    }

    void bind(Queue queue) {
        title.setText(queue.getTitle());
        firstItem.setText(firstItemSummary(queue));
        stack.setFirstItem(queue.firstItem());
    }

    private CharSequence firstItemSummary(Queue queue) {
        if (queue.firstItem() == null) {
            return "No items";
        } else {
            return queue.firstItem().getLabel();
        }
    }

}
