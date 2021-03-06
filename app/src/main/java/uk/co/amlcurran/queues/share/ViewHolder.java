package uk.co.amlcurran.queues.share;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import uk.co.amlcurran.queues.R;
import uk.co.amlcurran.queues.core.Queue;

class ViewHolder extends RecyclerView.ViewHolder {

    private final TextView title;

    public ViewHolder(View itemView, final SelectionListener queueListSelectionListener) {
        super(itemView);
        title = ((TextView) itemView.findViewById(R.id.queue_title));
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queueListSelectionListener.selectedItem(getPosition());
            }
        });
    }

    void bind(Queue queue) {
        title.setText(queue.getTitle());
    }

    public interface SelectionListener {
        void selectedItem(int position);

    }
}
