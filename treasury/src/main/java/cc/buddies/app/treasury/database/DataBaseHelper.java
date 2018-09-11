package cc.buddies.app.treasury.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper implements DataBaseConfig {

    private static final String TAG = DataBaseHelper.class.getSimpleName();

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "数据库 --> " + DB_NAME + "创建，版本号: " + DB_VERSION);
        DataBaseUpgrade.createVersion2(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "数据库 --> " + DB_NAME + "版本由:" + oldVersion + "升级到:" + newVersion + "!");
        if (newVersion <= oldVersion) {
            return;
        }

        // 数据库升级应该根据版本变化，做出对应版本的表及数据操作。
        DataBaseUpgrade.upgrade(db, oldVersion, newVersion);
    }



}
