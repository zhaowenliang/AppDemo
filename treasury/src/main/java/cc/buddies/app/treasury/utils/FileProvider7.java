package cc.buddies.app.treasury.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Android7.0后Uri.fromFile(file)报错，需要FileProvider7。
 * Created by zhaowl on 2018/3/6.
 */
public class FileProvider7 {

    public static Uri getUriForFile(Context context, File file) {
        return Build.VERSION.SDK_INT >= 24 ? getUriForFile24(context, file) : Uri.fromFile(file);
    }

    private static Uri getUriForFile24(Context context, File file) {
        return FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
    }

    public static void setIntentDataAndType(Context context, Intent intent, String type, File file, boolean writeAble) {
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setDataAndType(getUriForFile(context, file), type);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (writeAble) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        } else {
            intent.setDataAndType(Uri.fromFile(file), type);
        }
    }

}
