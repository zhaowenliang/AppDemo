package cc.buddies.app.treasury.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by zhaowl on 2017/5/17.
 */
public class DensityUtils {

    public static int dp2px(Context context, int value) {
        return applyDimension(context, TypedValue.COMPLEX_UNIT_DIP, value);
    }

    public static int sp2px(Context context, int value) {
        return applyDimension(context, TypedValue.COMPLEX_UNIT_SP, value);
    }

    /**
     * 单位转换，加0.5是为了四舍五入。
     * @param context 上下文
     * @param unit 单位
     * @param value 值
     * @return 转化后的值
     */
    private static int applyDimension(Context context, int unit, float value) {
        return (int) (TypedValue.applyDimension(unit, value, context.getResources().getDisplayMetrics()) + 0.5f);
    }

}
