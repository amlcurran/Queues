package uk.co.amlcurran.queues;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import uk.co.amlcurran.queues.core.Queue;
import uk.co.amlcurran.queues.core.QueueItem;
import uk.co.amlcurran.queues.core.QueuePersister;

public class SQLitePersister implements QueuePersister {

    private final DbHelper db;

    public SQLitePersister(Context context) {
        this.db = new DbHelper(context);
    }

    @Override
    public void addItemToQueue(String queueId, QueueItem queueItem) {
        SQLiteDatabase database = db.getWritableDatabase();
        long update = database.update(QueueItems.TABLE_NAME, fromQueueItem(queueItem, queueId),
                whereIdClause(), asArgs(queueItem.getId()));
        database.close();
    }

    private ContentValues fromQueueItem(QueueItem queueItem, String queueId) {
        ContentValues values = new ContentValues();
        values.put(QueueItems.QUEUE_ID, queueId);
        values.put(QueueItems.LABEL, queueItem.getLabel());
        return values;
    }

    @Override
    public void removeItemFromQueue(String queueId, QueueItem queueItem) {
        SQLiteDatabase database = db.getWritableDatabase();
        database.delete(QueueItems.TABLE_NAME, whereIdClause(), asArgs(queueItem.getId()));
    }

    @Override
    public void queues(LoadCallbacks callbacks) {
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.query(QueueList.TABLE_NAME, null, null, null, null, null, null);
        List<Queue> queues = new ArrayList<>();
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(QueueList._ID));
            String title = cursor.getString(cursor.getColumnIndex(QueueList.TITLE));
            Cursor itemsCursor = database.query(QueueItems.TABLE_NAME, null, whereItemBelongsTo(), asArgs(id), null, null, null);
            List<QueueItem> queueItems = fromItemsCursor(itemsCursor);
            queues.add(new Queue(title, id, this, queueItems));
            itemsCursor.close();
        }
        database.close();
        cursor.close();
        callbacks.loaded(queues);
    }

    private List<QueueItem> fromItemsCursor(Cursor itemsCursor) {
        List<QueueItem> items = new ArrayList<>();
        while (itemsCursor.moveToNext()) {
            items.add(new QueueItem(itemsCursor.getString(itemsCursor.getColumnIndex(QueueItems._ID)),
                    itemsCursor.getString(itemsCursor.getColumnIndex(QueueItems.LABEL))));
        }
        return items;
    }

    private String[] asArgs(long id) {
        return new String[] { String.valueOf(id) };
    }
    private String[] asArgs(String id) {
        return new String[] { id };
    }

    private String whereItemBelongsTo() {
        return QueueItems.QUEUE_ID + "=?";
    }

    @Override
    public void saveQueue(Queue queue, Callbacks callbacks) {
        ContentValues queueValues = fromQueue(queue);
        SQLiteDatabase database = db.getWritableDatabase();
        int update = database.update(QueueList.TABLE_NAME, queueValues, whereIdClause(), whereIdArgs(queue));
        database.close();
        if (update == 0) {
            callbacks.failedToSave(queue);
        }
    }

    private ContentValues fromQueue(Queue queue) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", queue.getTitle());
        return contentValues;
    }

    @Override
    public String uniqueId() {
        long inserted = db.getWritableDatabase().insert(QueueList.TABLE_NAME, QueueList.TITLE, null);
        if (inserted == -1) {
            throw new IllegalArgumentException("Failed to insert");
        }
        return String.valueOf(inserted);
    }

    @Override
    public String uniqueItemId() {
        long inserted = db.getWritableDatabase().insert(QueueItems.TABLE_NAME, QueueItems.LABEL, null);
        if (inserted == -1) {
            throw new IllegalArgumentException("Failed to insert");
        }
        return String.valueOf(inserted);
    }

    @Override
    public void deleteQueue(Queue queue, Callbacks callbacks) {
        db.getWritableDatabase().delete(QueueList.TABLE_NAME, whereIdClause(), whereIdArgs(queue));
        db.getWritableDatabase().delete(QueueItems.TABLE_NAME, whereQueueIdClause(), asArgs(queue.getId()));
    }

    @Override
    public boolean requiresUserIntervention() {
        return false;
    }

    private String whereQueueIdClause() {
        return QueueItems.QUEUE_ID + "=?";
    }

    private static String[] whereIdArgs(Queue queue) {
        return new String[]{String.valueOf(queue.getId())};
    }

    private static String whereIdClause() {
        return QueueList._ID + "=?";
    }

    private class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, "queues.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE queuelist (_id INTEGER PRIMARY KEY, title TEXT);");
            sqLiteDatabase.execSQL("CREATE TABLE queueItems (_id INTEGER PRIMARY KEY, label TEXT, queueId INTEGER);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }

    public interface QueueList extends BaseColumns {

        String TABLE_NAME = "queuelist";
        String TITLE = "title";

    }

    public interface QueueItems extends BaseColumns {

        String TABLE_NAME = "queueItems";
        String LABEL = "label";
        String QUEUE_ID = "queueId";

    }

}
