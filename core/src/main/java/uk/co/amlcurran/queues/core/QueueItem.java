package uk.co.amlcurran.queues.core;

public class QueueItem {
    private final String label;

    public QueueItem(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof QueueItem && ((QueueItem) obj).label.equals(label);
    }

    @Override
    public int hashCode() {
        return label.hashCode();
    }
}
