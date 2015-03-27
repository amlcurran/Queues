package uk.co.amlcurran.queues;

import android.app.Activity;
import android.widget.Toast;

import uk.co.amlcurran.queues.core.NavigationController;
import uk.co.amlcurran.queues.core.Queue;

class ActivityNavigationController implements NavigationController {

    private Activity activity;

    public ActivityNavigationController(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void viewQueue(Queue queue) {
        Toast.makeText(activity, "View me this queue: " + queue.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
