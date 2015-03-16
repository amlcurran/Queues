package uk.co.amlcurran.queues.core;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class QueueListTest {

    @Test
    public void returnsTheCorrectAmountOfQueues() {

        QueueList queueList = new QueueList(new QueuePersister() {

            @Override
            public void addItemToQueue(long queueId, QueueItem queueItem) {

            }

            @Override
            public void removeItemFromQueue(long queueId, QueueItem queueItem) {

            }

            @Override
            public int queueCount() {
                return 3;
            }
        });

        assertThat(queueList.size(), is(3));

    }

    private class QueueList {
        private final QueuePersister queuePersister;

        public QueueList(QueuePersister queuePersister) {
            this.queuePersister = queuePersister;
        }

        public int size() {
            return queuePersister.queueCount();
        }
    }
}
