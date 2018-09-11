package cc.buddies.app.appdemo.activity;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import cc.buddies.app.appdemo.BaseActivity;
import cc.buddies.app.appdemo.R;
import cc.buddies.app.treasury.database.table.TablePersonDao;

public class SQLiteActivity extends BaseActivity {

    private static final String TAG = SQLiteActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

    }

    private void insert() {
        TablePersonDao tablePersonDao = new TablePersonDao(getApplicationContext());
        long insert = tablePersonDao.insert("jack", 20, "jack@buddies.cc");
        Log.e(TAG, insert > 0 ? ("插入成功 i -> " + insert) : "插入失败");
    }

    public void onClickInsert(View view) {
        insert();
    }

    public void onClickQuery(View view) {
        TablePersonDao tablePersonDao = new TablePersonDao(getApplicationContext());
//        Cursor cursor = tablePersonDao.queryByName("jack");
        Cursor cursor = tablePersonDao.query();

        if (cursor == null) {
            Log.e(TAG, "查询失败");
        }
        if (cursor.getCount() == 0) {
            Log.e(TAG, "查询结果条数为0");
        }
        Log.e(TAG, "查询结果条数为: " + cursor.getCount());
        while (cursor.moveToNext()) {
            int columnCount = cursor.getColumnCount();

            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String email = cursor.getString(cursor.getColumnIndex("email"));

            Log.e(TAG, id + "   " + name + "    " + email);
        }
        cursor.close();
    }
}
