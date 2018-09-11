package cc.buddies.app.treasury.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * 主题切换，使用Resource，切换Configuration.uiMode
 * 保存当前主题模式到SharedPreferences中。
 */
public class ThemeChange {

    private static final String SP_NAME = "theme";
    private static final String SP_NIGHT = "isNightTheme";

    private static SharedPreferences getSP(Context context) {
        return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public static boolean getAppTheme(Context context)  {
        return getSP(context).getBoolean(SP_NIGHT, false);
    }

    public static void putAppTheme(Context context, boolean isNightTheme) {
        getSP(context).edit().putBoolean(SP_NIGHT, isNightTheme).apply();
    }

    public static void toggle(Context context) {
        putAppTheme(context, !getAppTheme(context));
    }

    /**
     * 初始化当前主题(夜间模式)
     */
    public static void initTheme(Context context) {
        boolean isNightTheme = getAppTheme(context);
        updateNightMode(context.getResources(), isNightTheme);
    }

    /**
     * 更新主题
     * @param resources Resources
     * @param on true：夜间模式；false：白天模式；
     */
    private static void updateNightMode(Resources resources, boolean on) {
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.uiMode &= ~Configuration.UI_MODE_NIGHT_MASK;
        config.uiMode |= on ? Configuration.UI_MODE_NIGHT_YES : Configuration.UI_MODE_NIGHT_NO;
        resources.updateConfiguration(config, dm);
    }

}
