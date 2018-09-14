package cc.buddies.app.appdemo.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import cc.buddies.app.appdemo.module.ImageDownloadServiceApi;
import cc.buddies.app.appdemo.retrofit.AppRetrofit;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 下载启动页图片IntentService
 */
public class SplashImageIntentService extends IntentService {

    private static final String ACTION_DOWNLOAD_SPLASH = "cc.buddies.app.appdemo.service.action.DOWNLOAD_SPLASH";

    private static final String EXTRA_DOWNLOAD_SPLASH = "cc.buddies.app.appdemo.service.extra.DOWNLOAD_SPLASH";

    private static final String APP_IMAGE_DIR = "/AppDemo/Image";
    private static final String APP_IMAGE_SPLASH_NAME = "splash.jpg";

    public SplashImageIntentService() {
        super("SplashImageIntentService");
    }

    /**
     * 启动下载启动页图片任务
     * @see IntentService
     */
    public static void startActionDownloadSplash(Context context, String imageUrl) {
        Intent intent = new Intent(context, SplashImageIntentService.class);
        intent.setAction(ACTION_DOWNLOAD_SPLASH);
        intent.putExtra(EXTRA_DOWNLOAD_SPLASH, imageUrl);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (EXTRA_DOWNLOAD_SPLASH.equals(action)) {
                final String param_splash = intent.getStringExtra(EXTRA_DOWNLOAD_SPLASH);
//                handleActionDownloadSplash(param_splash);
            }
        }
    }

    /**
     * 开始下载启动页图片任务
     */
    private void handleActionDownloadSplash(String imageUrl) {
        ImageDownloadServiceApi serviceApi = AppRetrofit.retrofit().create(ImageDownloadServiceApi.class);
        Call<ResponseBody> call = serviceApi.downloadImageFromNet(imageUrl);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    writeResponseBodyToDisk(APP_IMAGE_SPLASH_NAME, response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * 保存下载的图片流写入SD卡文件
     * @param imageName  xxx.jpg
     * @param body  image stream
     */
    private void writeResponseBodyToDisk(String imageName, ResponseBody body) {
        if(body==null){
            Toast.makeText(getApplicationContext(), "图片源错误", Toast.LENGTH_SHORT).show();
            return;
        }

        // 验证是否有访问外部存储权限

        // 通过接口获取最新文件的MD5值
        // 读取已经缓存的文件的MD5值，对比最新文件的MD5值，如果相同则不下载，否则下载新文件，删除旧文件。

        // 验证目录及文件是否存在

        // 将新下载的文件保存到目录

//        try {
//            InputStream is = body.byteStream();
//            File fileDr = new File(APP_IMAGE_DIR);
//            if (!fileDr.exists()) {
//                fileDr.mkdir();
//            }
//            File file = new File(APP_IMAGE_DIR, imageName);
//            if (file.exists()) {
//                file.delete();
//                file = new File(APP_IMAGE_DIR, imageName );
//            }
//            FileOutputStream fos = new FileOutputStream(file);
//            BufferedInputStream bis = new BufferedInputStream(is);
//            byte[] buffer = new byte[1024];
//            int len;
//            while ((len = bis.read(buffer)) != -1) {
//                fos.write(buffer, 0, len);
//            }
//            fos.flush();
//            fos.close();
//            bis.close();
//            is.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
