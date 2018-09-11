package cc.buddies.app.appdemo.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.RequestBuilder;

import org.greenrobot.eventbus.EventBus;

import cc.buddies.app.appdemo.BaseActivity;
import cc.buddies.app.appdemo.R;
import cc.buddies.app.appdemo.eventbus.UserEvent;
import cc.buddies.app.appdemo.glide.CustomImageViewTarget;
import cc.buddies.app.appdemo.glide.GlideApp;

public class ImageActivity extends BaseActivity {

    ImageView imageView;
    String imageUrl = "http://img1.dzwww.com:8080/tupian_pl/20150813/16/7858995348613407436.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        init();
    }

    private void init() {
        imageView = findViewById(R.id.image_view);

//        Glide.with(this)
//                .load(imageUrl)
//                .into(imageView);

        // 停止加载
//        Glide.with(this).clear(imageView);

//        GlideApp.with(this)
//                .load(imageUrl)
//                .placeholder(R.mipmap.ic_launcher)
//                .thumbnail(0.01f)
//                .fitCenter()
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .into(imageView);

//        GlideApp.with(this)
//                .load(imageUrl)
//                .override(500, 500)
//                .into(imageView);

        GlideApp.with(this)
                .asBitmap()
                .placeholder(R.mipmap.ic_launcher)
                .load(imageUrl)
                .into(new CustomImageViewTarget(imageView, 500, 500));

//        GlideApp.with(this)
//                .asBitmap()
//                .load(imageUrl)
//                .into(new CustomImageViewTarget(imageView, 500, 500));

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                // 在子线程获取加载图片
//                FutureTarget<Bitmap> futureTarget = GlideApp.with(ImageActivity.this)
//                        .asBitmap()
//                        .load(imageUrl)
//                        .submit();
//
//                try {
//                    final Bitmap bitmap = futureTarget.get();
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            imageView.setImageBitmap(bitmap);
//                        }
//                    });
//                } catch (InterruptedException | ExecutionException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();


    }


    private void loadThumbnail() {
        // 如果主请求在缩略图请求之前完成，则缩略图请求中的图像将不会被展示。
        // thumbnail() API 允许你简单快速地加载图像的低分辨率版本，并且同时加载图像的无损版本，这可以减少用户盯着加载指示器 【例如进度条–译者注】 的时间。

        // 设置加载图片，加载图片前先加载缩略0.1f的缩略图。
        GlideApp.with(this)
                .load(imageUrl)
                .thumbnail(0.1f)
                .into(imageView);

        // 这里可以加载其他链接的缩略图
        RequestBuilder<Drawable> loadThumbRequestBuilder = GlideApp.with(this).load(imageUrl);
        // 这设置加载大图，并设置加载大图前先加载缩略图。
        GlideApp.with(this)
                .load(imageUrl)
                .thumbnail(loadThumbRequestBuilder)
                .into(imageView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlideApp.get(this).clearMemory();

        new Thread(new Runnable() {
            @Override
            public void run() {
                GlideApp.get(ImageActivity.this).clearDiskCache();
            }
        }).start();
    }


    public void onClickButton(View view) {
        EventBus.getDefault().post(new UserEvent("AAA", 100));
        finish();
    }
}
