package uk.co.amlcurran.queues;

import uk.co.amlcurran.queues.core.Queue;
import uk.co.amlcurran.queues.core.QueueList;

public class BasicQueueSource {
    private final QueueList queueList;

    public BasicQueueSource(QueueList queueList) {
        this.queueList = queueList;
    }

    public Queue get(int position) {
        return queueList.all().get(position);
    }

    public int size() {
        return queueList.size();
    }
}