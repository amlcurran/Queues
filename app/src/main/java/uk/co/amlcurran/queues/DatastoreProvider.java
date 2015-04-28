package uk.co.amlcurran.queues;

import com.dropbox.sync.android.DbxDatastore;

public interface DatastoreProvider {
    DbxDatastore getDatastore();

    boolean isLinkedToAccount();

    void invalidate();

    void migrateToLinked();

    interface Delegate {
        void hasUserResolvableAction();
    }
}
