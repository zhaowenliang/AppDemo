package cc.buddies.app.treasury.database;

import android.database.sqlite.SQLiteDatabase;

public class DataBaseUpgrade implements DataBaseConfig {

    /**
     * 数据库升级
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    public static void upgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int curVersion = oldVersion;

        // 数据库逐个版本升级
        while (curVersion < newVersion) {
            switch (oldVersion) {
                case DB_VERSION_1:
                    curVersion = update1_2(db);
                    break;
                case DB_VERSION_2:
                    curVersion = update2_3(db);
                    break;
                case DB_VERSION_3:
                    break;
            }
        }
    }

    /**
     * 升级版本1的数据库到版本2的数据库
     * <p>升级内容:</p>
     *     <li>表person，增加一个字段</li>
     * @param db 数据库
     * @return 返回升级后的数据库版本
     */
    private static int update1_2(SQLiteDatabase db) {
        // 创建备份表
        String temp_table_person = "alter table " + TABLE_PERSON_NAME + " rename to temp_" + TABLE_PERSON_NAME;
        db.execSQL(temp_table_person);
        // 创建新表
        createVersion2(db);
        // 导入备份表数据到新表
        String import_table_person = "insert into " + TABLE_PERSON_NAME + " select *,'' from temp_" + TABLE_PERSON_NAME;
        db.execSQL(import_table_person);
        // 删除备份表
        String drop_temp_table_person = "drop table if exists temp_" + TABLE_PERSON_NAME;
        db.execSQL(drop_temp_table_person);

        // 返回处理后数据库版本
        return DataBaseConfig.DB_VERSION_2;
    }

    /**
     * 升级版本2的数据库到版本3的数据库
     * <p>升级内容:</p>
     *     <li></li>
     * @param db
     * @return 返回升级后的版本数据库
     */
    private static int update2_3(SQLiteDatabase db) {
        // TODO 数据库升级

        return DataBaseConfig.DB_VERSION_3;
    }

    public static void createVersion1(SQLiteDatabase db) {
        String sql = "create table if not exists " + TABLE_PERSON_NAME + " (_id integer primary key AUTOINCREMENT, name text, age integer, email text)";
        db.execSQL(sql);
    }

    public static void createVersion2(SQLiteDatabase db) {
        String sql = "create table if not exists " + TABLE_PERSON_NAME + " (_id integer primary key AUTOINCREMENT, name text, age integer, email text, phone text)";
        db.execSQL(sql);
    }

}
