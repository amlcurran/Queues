package uk.co.amlcurran.queues.core;

public class QueueItem {
    private final String label;
    private final long id;

    public QueueItem(long id, String label) {
        this.id = id;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "Queue item: " + label;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof QueueItem && ((QueueItem) obj).label.equals(label);
    }

    @Override
    public int hashCode() {
        return label.hashCode();
    }

    public long getId() {
        return id;
    }
}
