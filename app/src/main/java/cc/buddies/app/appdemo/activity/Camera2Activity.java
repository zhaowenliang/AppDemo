package cc.buddies.app.appdemo.activity;

import android.os.Bundle;

import cc.buddies.app.appdemo.BaseActivity;
import cc.buddies.app.appdemo.R;
import cc.buddies.app.appdemo.view.CameraPreview;

public class Camera2Activity extends BaseActivity {

    CameraPreview cameraPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);
        initSurfaceView();

    }

    private void initSurfaceView() {
        cameraPreview = findViewById(R.id.surface_view);

    }


}
