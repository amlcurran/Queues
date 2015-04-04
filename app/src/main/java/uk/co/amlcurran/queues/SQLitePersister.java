package uk.co.amlcurran.queues;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    public void addItemToQueue(long queueId, QueueItem queueItem) {

    }

    @Override
    public void removeItemFromQueue(long queueId, QueueItem queueItem) {

    }

    @Override
    public void queues(LoadCallbacks callbacks) {
        Cursor cursor = db.getReadableDatabase().query("queuelist", null, null, null, null, null, null);
        List<Queue> queues = new ArrayList<>();
        while (cursor.moveToNext()) {
            queues.add(fromCursor(cursor));
        }
        cursor.close();
        callbacks.loaded(queues);
    }

    private Queue fromCursor(Cursor cursor) {
        String title = cursor.getString(cursor.getColumnIndex("title"));
        long id = cursor.getLong(cursor.getColumnIndex("_id"));
        return new Queue(title, id, this);
    }

    @Override
    public void saveQueue(Queue queue, Callbacks callbacks) {
        ContentValues queueValues = fromQueue(queue);
        int update = db.getWritableDatabase().update("queuelist", queueValues, "_id=?", new String[]{String.valueOf(queue.getId())});
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
    public long uniqueId() {
        long inserted = db.getWritableDatabase().insert("queuelist", "title", null);
        if (inserted == -1) {
            throw new IllegalArgumentException("Failed to insert");
        }
        return inserted;
    }

    @Override
    public void deleteQueue(Queue queue, Callbacks callbacks) {
        db.getWritableDatabase().delete("queuelist", "_id=?", new String[] { String.valueOf(queue.getId()) });
    }

    private class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, "queues.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE queuelist (_id INTEGER PRIMARY KEY, title TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }


}
