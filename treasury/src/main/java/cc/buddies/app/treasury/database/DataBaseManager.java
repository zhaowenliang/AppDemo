package cc.buddies.app.treasury.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseManager {

    private static DataBaseManager instance;
    private static SQLiteOpenHelper mDatabaseHelper;

    public static synchronized DataBaseManager initialize(Context context) {
        if (instance == null) {
            instance = new DataBaseManager();
        }
        if (mDatabaseHelper == null) {
            mDatabaseHelper = new DataBaseHelper(context);
        }

        return instance;
    }

    public synchronized SQLiteDatabase getDatabase() {
        return mDatabaseHelper.getWritableDatabase();
    }

}
