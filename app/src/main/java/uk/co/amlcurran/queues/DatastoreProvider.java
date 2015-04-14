package uk.co.amlcurran.queues;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxDatastore;

public interface DatastoreProvider {
    DbxDatastore getDatastore();

    interface Delegate {
        void hasUserResolvableAction();
    }
}
