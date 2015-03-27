package uk.co.amlcurran.queues;

import android.app.Activity;

import uk.co.amlcurran.queues.core.NavigationController;
import uk.co.amlcurran.queues.core.Queue;
import uk.co.amlcurran.queues.queue.QueueActivity;

public class ActivityNavigationController implements NavigationController {

    private Activity activity;

    public ActivityNavigationController(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void viewQueue(Queue queue) {
        activity.startActivity(QueueActivity.viewQueue(activity, queue));
    }
}
