package uk.co.amlcurran.queues.queue;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import uk.co.amlcurran.queues.core.QueueItem;

class ViewHolder extends RecyclerView.ViewHolder {

    public ViewHolder(View itemView) {
        super(itemView);
    }

    void bind(QueueItem queueItem) {
        ((TextView) itemView).setText(queueItem.getLabel());
    }

}
