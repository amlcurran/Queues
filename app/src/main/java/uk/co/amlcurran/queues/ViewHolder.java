package uk.co.amlcurran.queues;

import android.support.v7.widget.RecyclerView;
import android.view.View;

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
}
