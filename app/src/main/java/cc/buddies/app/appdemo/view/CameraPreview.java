package cc.buddies.app.appdemo.view;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

import cc.buddies.app.treasury.utils.CameraUtils;
import cc.buddies.app.treasury.utils.LogUtils;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraPreview(Context context) {
        this(context, null);
    }

    public CameraPreview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LogUtils.e("CameraPreview----init()");
        mHolder = getHolder();
        mHolder.setKeepScreenOn(true);
        mHolder.addCallback(this);

        // API16以后不需要
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//        }
    }

    // Surface创建时触发。
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        LogUtils.e("CameraPreview----surfaceCreated()");
        initCamera();
        preview(holder);
    }

    // Surface的大小发生改变的时候调用。
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        LogUtils.e("CameraPreview----surfaceChanged()");
        if (mHolder.getSurface() == null) {
            return;
        }
        initCamera();

        mCamera.stopPreview();
        preview(holder);
    }

    // Surface销毁的时候调用。
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        LogUtils.e("CameraPreview----surfaceDestroyed()");
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    private void preview(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.setDisplayOrientation(90);
            mCamera.startPreview();
        } catch (IOException e) {
            LogUtils.e("预览失败!");
            e.printStackTrace();
        }
    }

    private void initCamera() {
        if (mCamera == null) {
            mCamera = CameraUtils.getCamera();
        }
    }

    public Camera getCamera() {
        return mCamera;
    }
}
