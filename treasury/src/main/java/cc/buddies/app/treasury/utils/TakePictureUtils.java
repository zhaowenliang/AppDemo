package cc.buddies.app.treasury.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 拍照生成图片
 * Created by zhaowl on 2018/3/5.
 */
public class TakePictureUtils {

    public static String mCurrentPhotoPath;

    public static File createImageFile(Context context) throws IOException {
//        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File storageDir = new File(context.getFilesDir(), Environment.DIRECTORY_PICTURES);
//        Log.d("aaaa", "createImageFile  image存放目录=" + storageDir.getAbsolutePath());

        File image = new File(context.getFilesDir() + "/Pictures", "photo_"+ System.currentTimeMillis() +".jpg");

//        File image = File.createTempFile(
//                "photo_",  /* prefix */
//                ".jpg",   /* suffix */
//                storageDir      /* directory */
//        );
        Log.d("aaaa", "createImageFile  image=" + image.getAbsolutePath());
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.d("aaaa", "createImageFile  mCurrentPhotoPath=" + mCurrentPhotoPath);
        return image;
    }

}
