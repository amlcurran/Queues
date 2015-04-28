package uk.co.amlcurran.queues;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxDatastore;
import com.dropbox.sync.android.DbxDatastoreManager;
import com.dropbox.sync.android.DbxException;

public class HotswappingDatastore implements DatastoreProvider {
    private final DbxAccountManager accountManager;
    private DbxDatastore datastore;

    public HotswappingDatastore(DbxAccountManager accountManager) {
        this.accountManager = accountManager;
    }

    @Override
    public DbxDatastore getDatastore() {
        if (datastore == null) {
            return createDatastore();
        } else {
            return datastore;
        }
    }

    private DbxDatastore createDatastore() {
        try {
            DbxDatastoreManager datastoreManager = null;
            if (isLinkedToAccount()) {
                datastoreManager = DbxDatastoreManager.forAccount(accountManager.getLinkedAccount());
            }
            if (datastoreManager == null) {
                datastoreManager = DbxDatastoreManager.localManager(accountManager);
            }
            datastore = datastoreManager.openDefaultDatastore();
            return datastore;
        } catch (DbxException e) {
            e.printStackTrace();
            throw new IllegalStateException("Couldn't open a Dropbox datastore");
        }
    }

    @Override
    public boolean isLinkedToAccount() {
        return accountManager.hasLinkedAccount();
    }

    @Override
    public void invalidate() {
        datastore.close();
        datastore = null;
    }

    @Override
    public void migrateToLinked() {
        datastore.close();
        try {
            DbxDatastoreManager.localManager(accountManager).migrateToAccount(accountManager.getLinkedAccount());
            invalidate();
        } catch (DbxException e) {
            e.printStackTrace();
        }
    }

}
