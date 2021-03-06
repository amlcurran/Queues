package uk.co.amlcurran.queues.queue;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import uk.co.amlcurran.queues.core.QueueItem;

class ViewHolder extends RecyclerView.ViewHolder {

    public ViewHolder(View itemView, final QueueAdapter.QueueSelectionListener queueSelectionListener) {
        super(itemView);
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                queueSelectionListener.itemSecondarySelected(getPosition());
                return true;
            }

        });
    }

    void bind(QueueItem queueItem) {
        ((TextView) itemView).setText(queueItem.getLabel());
    }

}
