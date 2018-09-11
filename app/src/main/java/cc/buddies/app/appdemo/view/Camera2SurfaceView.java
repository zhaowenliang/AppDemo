package cc.buddies.app.appdemo.view;

import android.content.Context;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class Camera2SurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder;

    private CameraManager mCameraManager;
    private CameraDevice mCameraDevice;

    private CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            // 打开摄像头
            mCameraDevice = camera;
            // TODO 开启预览
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                takePreview();
            }
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            // 关闭摄像头
            if (mCameraDevice != null) {
                mCameraDevice.close();
                mCameraDevice = null;
            }
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            // 发生错误
            Toast.makeText(getContext(), "开启摄像头失败", Toast.LENGTH_LONG).show();
        }
    };

    public Camera2SurfaceView(Context context) {
        super(context);
        init();
    }

    public Camera2SurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Camera2SurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Camera2SurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mHolder = getHolder();
        mHolder.setKeepScreenOn(true);
        mHolder.addCallback(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            initCamera2();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {



    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initCamera2() {
//        Context context = getContext();
//        mCameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
//
//        String cameraId = String.valueOf(CameraCharacteristics.LENS_FACING_FRONT);
//        Handler mainHandler = new Handler(context.getMainLooper());
//        try {
//            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                return;
//            }
//            mCameraManager.openCamera(cameraId, stateCallback, mainHandler);
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
    }

    private void takePreview() {
//        try {
//            CaptureRequest.Builder captureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
//            captureRequestBuilder.addTarget();
//
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
    }

}
