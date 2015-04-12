package uk.co.amlcurran.queues.queue;

import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import uk.co.amlcurran.queues.QueuesApplication;
import uk.co.amlcurran.queues.R;
import uk.co.amlcurran.queues.core.Queue;
import uk.co.amlcurran.queues.core.QueueItem;
import uk.co.amlcurran.queues.core.QueuePresenter;
import uk.co.amlcurran.queues.core.QueueView;

public class QueueFragment extends Fragment implements QueueView {

    private TextView newItemEntry;
    private RecyclerView items;
    private QueueAdapter adapter;
    private QueuePresenter presenter;
    private View doneButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_queue, container, false);
        newItemEntry = (TextView) view.findViewById(R.id.queue_item_entry);
        items = (RecyclerView) view.findViewById(R.id.queue_items);
        doneButton = view.findViewById(R.id.queue_done_button);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new QueuePresenter(getArguments().getLong("id"), this, QueuesApplication.queueList(getActivity()));
        adapter = new QueueAdapter(LayoutInflater.from(getActivity()), new QueueAdapter.QueueSelectionListener() {
            @Override
            public void itemSecondarySelected(int position) {
                presenter.removeItem(position);
            }
        });
        items.setLayoutManager(new LinearLayoutManager(getActivity()));
        items.setAdapter(adapter);
        items.setItemAnimator(new CustomAnimator(getActivity()));
        presenter.load();
        newItemEntry.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String title = String.valueOf(newItemEntry.getText());
                    presenter.addItem(title);
                    newItemEntry.setText("");
                    return true;
                }
                return false;
            }
        });
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.removeItem(0);
            }
        });
    }

    @Override
    public void show(Queue queue) {
        adapter.addAll(queue.all());
    }

    @Override
    public void itemAdded(QueueItem queueItem) {
        adapter.add(queueItem);
    }

    @Override
    public void itemRemoved(QueueItem item) {
        adapter.remove(item);
    }

    public static QueueFragment withId(long id) {
        QueueFragment queueFragment = new QueueFragment();
        Bundle args = new Bundle();
        args.putLong("id", id);
        queueFragment.setArguments(args);
        return queueFragment;
    }

    private static class CustomAnimator extends DefaultItemAnimator {

        private final TimeInterpolator interpolator;
        private final float translationOut;

        public CustomAnimator(Activity activity) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                interpolator = lollipop(activity);
            }
            else {
                interpolator = other(activity);
            }
            translationOut = activity.getResources().getDimension(R.dimen.translation_out_length);
        }

        @Override
        public boolean animateRemove(final RecyclerView.ViewHolder holder) {
            holder.itemView.animate()
                    .translationXBy(-translationOut)
                    .alpha(0)
                    .withStartAction(new StartRunnable(holder))
                    .withEndAction(new EndRunnable(holder))
                    .setInterpolator(interpolator).start();
            return true;
        }

        private static Interpolator other(Activity activity) {
            return AnimationUtils.loadInterpolator(activity, android.R.interpolator.accelerate_cubic);
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        private static Interpolator lollipop(Activity activity) {
            return AnimationUtils.loadInterpolator(activity, android.R.interpolator.fast_out_slow_in);
        }

        private class StartRunnable implements Runnable {
            private final RecyclerView.ViewHolder holder;

            public StartRunnable(RecyclerView.ViewHolder holder) {
                this.holder = holder;
            }

            @Override
            public void run() {
                dispatchRemoveStarting(holder);
            }
        }

        private class EndRunnable implements Runnable {
            private final RecyclerView.ViewHolder holder;

            public EndRunnable(RecyclerView.ViewHolder holder) {
                this.holder = holder;
            }

            @Override
            public void run() {
                dispatchRemoveFinished(holder);
            }
        }
    }
}
