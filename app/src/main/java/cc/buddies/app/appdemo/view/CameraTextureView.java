package cc.buddies.app.appdemo.view;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.TextureView;

import java.io.IOException;
import java.util.List;

import cc.buddies.app.treasury.utils.CameraUtils;
import cc.buddies.app.treasury.utils.LogUtils;

public class CameraTextureView extends TextureView implements TextureView.SurfaceTextureListener {

    private Camera mCamera;

    public CameraTextureView(Context context) {
        super(context);
        init();
    }

    public CameraTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CameraTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CameraTextureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setSurfaceTextureListener(this);
        setAlpha(1.0f);
        setRotation(90.0f);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        LogUtils.e("CameraTextureView----onSurfaceTextureAvailable() width: " + width + "   height: " + height);
        mCamera = CameraUtils.getCamera();
        Camera.Parameters parameters = mCamera.getParameters();

        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        Camera.Size previewSize = CameraUtils.getCloselyPreSize(width, height, supportedPreviewSizes, true);
//        Camera.Size previewSize = CameraUtils.getPreSizeByWidth(width, supportedPreviewSizes);
        LogUtils.e("最后选择的Camera.Size----width: " + previewSize.width + "     height: " + previewSize.height);
//        Camera.Size previewSize = mCamera.getParameters().getPreviewSize();
//        setLayoutParams(new FrameLayout.LayoutParams(previewSize.width, previewSize.height, Gravity.CENTER));

        parameters.setPreviewSize(previewSize.width, previewSize.height);
        mCamera.setParameters(parameters);
        try {
            mCamera.setPreviewTexture(surface);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.startPreview();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        LogUtils.e("CameraTextureView----onSurfaceTextureSizeChanged()");
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        LogUtils.e("CameraTextureView----onSurfaceTextureDestroyed()");
        mCamera.stopPreview();
        mCamera.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
//        LogUtils.e("CameraTextureView----onSurfaceTextureUpdated()");
    }

}
