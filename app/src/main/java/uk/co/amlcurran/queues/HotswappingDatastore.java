package uk.co.amlcurran.queues;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxDatastore;
import com.dropbox.sync.android.DbxDatastoreManager;
import com.dropbox.sync.android.DbxException;

public class HotswappingDatastore implements DatastoreProvider {
    private final DbxAccountManager accountManager;
    private final DatastoreProvider.Delegate delegate;

    public HotswappingDatastore(DbxAccountManager accountManager, Delegate delegate) {
        this.accountManager = accountManager;
        this.delegate = delegate;
    }

    @Override
    public DbxDatastore getDatastore() {
        try {
            DbxDatastoreManager datastoreManager = null;
            if (accountManager.hasLinkedAccount()) {
                datastoreManager = DbxDatastoreManager.forAccount(accountManager.getLinkedAccount());
            } else {
                delegate.hasUserResolvableAction();
            }
            if (datastoreManager == null) {
                datastoreManager = DbxDatastoreManager.localManager(accountManager);
            }
            return datastoreManager.openDefaultDatastore();
        } catch (DbxException e) {
            e.printStackTrace();
            throw new IllegalStateException("Couldn't open a Dropbox datastore");
        }
    }

}
