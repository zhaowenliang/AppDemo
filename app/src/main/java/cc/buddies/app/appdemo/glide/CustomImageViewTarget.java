package cc.buddies.app.appdemo.glide;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;

import cc.buddies.app.treasury.utils.LogUtils;

public class CustomImageViewTarget extends ImageViewTarget<Bitmap> {

    private int width, height;

    public CustomImageViewTarget(ImageView view) {
        super(view);
    }

    public CustomImageViewTarget(ImageView view, int width, int height) {
        super(view);
        this.width = width;
        this.height = height;
    }

    @Override
    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
        super.onResourceReady(resource, transition);
        LogUtils.e("CustomImageViewTarget----onResourceReady()");
    }

    @Override
    protected void setResource(@Nullable Bitmap resource) {
        view.setImageBitmap(resource);
        LogUtils.e("CustomImageViewTarget----setResource()");
    }

    @Override
    public void getSize(@NonNull SizeReadyCallback cb) {
        LogUtils.e("CustomImageViewTarget----getSize()");
        if (width > 0 && height > 0) {
            cb.onSizeReady(width, height);
            return;
        }
        super.getSize(cb);
    }
}
