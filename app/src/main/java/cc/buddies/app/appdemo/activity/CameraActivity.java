package cc.buddies.app.appdemo.activity;

import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cc.buddies.app.appdemo.BaseActivity;
import cc.buddies.app.appdemo.R;
import cc.buddies.app.appdemo.view.CameraPreview;
import cc.buddies.app.treasury.utils.CameraUtils;

public class CameraActivity extends BaseActivity {

    private CameraPreview cameraPreview;
    private Camera mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!CameraUtils.checkCameraHardware(this)) {
            Toast.makeText(this, "设备没有相机!", Toast.LENGTH_SHORT).show();
            finish();
        }
        setContentView(R.layout.activity_camera);

        cameraPreview = findViewById(R.id.surface_view);
        mCamera = cameraPreview.getCamera();
    }

    public void onClickTakePicture(View view) {
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                mCamera.takePicture(null, null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        File pictureFile = new File("/sdcard/" + System.currentTimeMillis() + ".jpg");

                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(pictureFile);
                            fos.write(data);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }

}
