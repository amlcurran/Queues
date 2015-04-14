package uk.co.amlcurran.queues;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxDatastore;
import com.dropbox.sync.android.DbxDatastoreManager;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxRecord;
import com.dropbox.sync.android.DbxTable;

import java.util.ArrayList;
import java.util.List;

import uk.co.amlcurran.queues.core.Queue;
import uk.co.amlcurran.queues.core.QueueItem;
import uk.co.amlcurran.queues.core.QueuePersister;

public class DropboxPersister implements QueuePersister {
    private final DbxDatastore datastore;

    public DropboxPersister(QueuesApplication queuesApplication) {
        DbxAccountManager accountManager = DbxAccountManager.getInstance(queuesApplication, Secretz.APP_KEY, Secretz.APP_SECRET);
        DbxDatastoreManager datastoreManager = managerFromAccount(accountManager);
        try {
            datastore = datastoreManager.openDefaultDatastore();
        } catch (DbxException e) {
            e.printStackTrace();
            throw new IllegalStateException("Couldn't open a Dropbox datastore");
        }
    }

    private DbxDatastoreManager managerFromAccount(DbxAccountManager accountManager) {
        if (accountManager.hasLinkedAccount()) {
            try {
                return DbxDatastoreManager.forAccount(accountManager.getLinkedAccount());
            } catch (DbxException.Unauthorized unauthorized) {
                unauthorized.printStackTrace();
            }
        }
        return DbxDatastoreManager.localManager(accountManager);
    }

    @Override
    public void addItemToQueue(final String queueId, final QueueItem queueItem) {
        doChangeAction(new ChangeAction() {
            @Override
            public void run() throws DbxException {
                DbxRecord record = itemsTable().get(queueItem.getId());
                record.set("label", queueItem.getLabel());
                record.set("queue_id", queueId);
            }
        });
    }

    @Override
    public void removeItemFromQueue(String queueId, final QueueItem queueItem) {
        doChangeAction(new ChangeAction() {
            @Override
            public void run() throws DbxException {
                DbxRecord record = itemsTable().get(queueItem.getId());
                record.deleteRecord();
            }
        });
    }

    @Override
    public void queues(LoadCallbacks callbacks) {
        List<Queue> queues = new ArrayList<>();
        try {
            DbxTable.QueryResult dbQueues = queuesTable().query();
            for (DbxRecord record : dbQueues) {
                queues.add(queueFromRecord(record));
            }
        } catch (DbxException e) {
            e.printStackTrace();
        }
        callbacks.loaded(queues);
    }

    @Override
    public void saveQueue(final Queue queue, Callbacks callbacks) {
        doChangeAction(new ChangeAction() {
            @Override
            public void run() throws DbxException {
                queuesTable().get(queue.getId()).set("title", queue.getTitle());
            }
        });
    }

    @Override
    public String uniqueId() {
        return queuesTable().insert().getId();
    }

    @Override
    public String uniqueItemId() {
        return itemsTable().insert().getId();
    }

    @Override
    public void deleteQueue(final Queue queue, Callbacks callbacks) {
        doChangeAction(new ChangeAction() {
            @Override
            public void run() throws DbxException {
                queuesTable().get(queue.getId()).deleteRecord();
            }
        });
    }

    private void doChangeAction(ChangeAction action) {
        doChangeAction(action, null);
    }

    private void doChangeAction(ChangeAction action, FailAction failAction) {
        try {
            action.run();
            datastore.sync();
        } catch (DbxException e) {
            e.printStackTrace();
            if (failAction != null) {
                failAction.fail();
            }
        }
    }

    private interface ChangeAction {
        void run() throws DbxException;
    }

    private interface FailAction {
        void fail();
    }

    private DbxTable itemsTable() {
        return datastore.getTable("items");
    }

    private QueueItem itemFromRecord(DbxRecord record) {
        return new QueueItem(record.getId(), record.getString("label"));
    }

    private Queue queueFromRecord(DbxRecord record) {
        List<QueueItem> queueItems = itemsForQueue(record.getId());
        return new Queue(record.getString("title"), record.getId(), this, queueItems);
    }

    private List<QueueItem> itemsForQueue(String id) {
        List<QueueItem> items = new ArrayList<>();
        try {
            DbxTable.QueryResult dbItems = itemsTable().query();
            for (DbxRecord record : dbItems) {
                if (record.getString("queue_id").equals(id)) {
                    items.add(itemFromRecord(record));
                }
            }
        } catch (DbxException e) {
            e.printStackTrace();
        }
        return items;
    }

    private DbxTable queuesTable() {
        return datastore.getTable("queues");
    }
}