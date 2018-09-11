package cc.buddies.app.appdemo.activity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import cc.buddies.app.appdemo.BaseActivity;
import cc.buddies.app.appdemo.R;
import cc.buddies.app.appdemo.view.CameraTextureView;
import cc.buddies.app.treasury.utils.CameraUtils;
import cc.buddies.app.treasury.utils.LogUtils;

public class CameraNewActivity extends BaseActivity {

    CameraTextureView textureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        LogUtils.e("CameraNewActivity----onCreate()");
        if (!CameraUtils.checkCameraHardware(this)) {
            Toast.makeText(this, "设备没有相机!", Toast.LENGTH_SHORT).show();
            finish();
        }
        setContentView(R.layout.activity_camera_new);

        textureView = findViewById(R.id.texture_view);
    }
}
