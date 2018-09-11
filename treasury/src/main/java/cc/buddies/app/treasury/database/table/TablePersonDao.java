package cc.buddies.app.treasury.database.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cc.buddies.app.treasury.database.DataBaseConfig;
import cc.buddies.app.treasury.database.DataBaseManager;

public class TablePersonDao implements DataBaseConfig {

    private SQLiteDatabase db;

    public TablePersonDao(Context context) {
        db = DataBaseManager.initialize(context).getDatabase();
    }

    public Cursor query() {
        Cursor cursor = db.query(TABLE_PERSON_NAME, null, null, null, null, null, null);
        return cursor;
    }

    public Cursor queryByName(String name) {
        Cursor cursor = db.query(TABLE_PERSON_NAME, null, "name = ?", new String[] {name}, null, null, null);
        return cursor;
    }

    public long insert(ContentValues values) {
        db.beginTransaction();
        long insert = db.insert(TABLE_PERSON_NAME, null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
        return insert;
    }

    public long insert(String name, int age, String email) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("age", age);
        contentValues.put("email", email);

        return insert(contentValues);
    }

    public void delete(int _id) {
        db.beginTransaction();
        db.delete(TABLE_PERSON_NAME, "_id = ?", new String[]{String.valueOf(_id)});
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void update(int _id, String email) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);

        db.beginTransaction();
        db.update(TABLE_PERSON_NAME, contentValues, "_id = ?", new String[]{String.valueOf(_id)});
        db.setTransactionSuccessful();
        db.endTransaction();
    }

}
