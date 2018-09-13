package cc.buddies.app.treasury.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * SharedPreferences工具类
 */
public class SPUtils {

    private static final String CONFIG_FILE = SPConfig.APP_SP_NAME;

    private static SharedPreferences getSP(@NonNull Context context) {
        return context.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
    }

    public static void setBoolean(Context context, String key, boolean value) {
        getSP(context).edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return getSP(context).getBoolean(key, defValue);
    }

    public static void setString(Context context, String key, String value) {
        getSP(context).edit().putString(key, value).apply();
    }

    public static String getString(Context context, String key, String defValue) {
        return getSP(context).getString(key, defValue);
    }

    public static Long getLong(Context context, String key, Long defValue) {
        return getSP(context).getLong(key, defValue);
    }

    public static void setLong(Context context, String key, Long value) {
        getSP(context).edit().putLong(key, value).apply();
    }

    public static int getInt(Context context, String key, int defValue) {
        return getSP(context).getInt(key, defValue);
    }

    public static void setInt(Context context, String key, int value) {
        getSP(context).edit().putInt(key, value).apply();
    }

}
