package uk.co.amlcurran.queues.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import uk.co.amlcurran.queues.R;
import uk.co.amlcurran.queues.core.Queue;

class ViewHolder extends RecyclerView.ViewHolder {

    private final QueueStackView stack;
    private final TextView title;

    public ViewHolder(View itemView, final QueueListAdapter.QueueListSelectionListener queueListSelectionListener) {
        super(itemView);
        stack = (QueueStackView) itemView.findViewById(R.id.queue_stack);
        title = ((TextView) itemView.findViewById(R.id.queue_title));
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queueListSelectionListener.selectedQueue(getPosition());
            }
        });
    }

    void bind(Queue queue) {
        title.setText(queue.getTitle());
        stack.setSize(queue);
    }

}
