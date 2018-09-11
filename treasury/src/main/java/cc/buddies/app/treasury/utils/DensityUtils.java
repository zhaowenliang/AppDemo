package cc.buddies.app.treasury.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by zhaowl on 2017/5/17.
 */
public class DensityUtils {

    public static int dp2px(Context context, int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, i, context.getResources().getDisplayMetrics());
    }

}
