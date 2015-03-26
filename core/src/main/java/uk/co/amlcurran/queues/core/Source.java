package uk.co.amlcurran.queues.core;

public interface Source<T> {
    T get(int position);

    int size();
}
