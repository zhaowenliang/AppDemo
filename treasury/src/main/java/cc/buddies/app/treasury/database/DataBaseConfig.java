package cc.buddies.app.treasury.database;

public interface DataBaseConfig {

    // 对应各个版本的数据库
    static final int DB_VERSION_1 = 1;
    static final int DB_VERSION_2 = 2;
    static final int DB_VERSION_3 = 3;

    static final String DB_NAME = "database.db";
    static final int DB_VERSION = DB_VERSION_3;

    static final String TABLE_PERSON_NAME = "person";

}
