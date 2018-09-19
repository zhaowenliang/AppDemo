package cc.buddies.app.appdemo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import cc.buddies.app.appdemo.BaseActivity;
import cc.buddies.app.appdemo.R;
import cc.buddies.app.appdemo.service.FloatButtonService;
import cc.buddies.app.treasury.permission.CheckPermissionUtils;

public class FloatWindowActivity extends BaseActivity {

    private static final int REQUEST_CODE_MANAGE_OVERLAY = 0x100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_window);
    }

    public void onClickButton(View view) {
        // 判断悬浮窗是否已经开启
        if (FloatButtonService.isStarted) {
            return;
        }

        // 如果没有悬浮窗权限，则请求权限
        if (!checkManageOverlayPermission()){
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, REQUEST_CODE_MANAGE_OVERLAY);
            return;
        }

        // 如果有权限则开启悬浮窗
        startFloatButtonService();
    }

    /** 检查悬浮窗权限 */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean checkManageOverlayPermission() {
        return CheckPermissionUtils.checkManageOverlayPermission(this);
    }

    private void startFloatButtonService() {
        startService(new Intent(this, FloatButtonService.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUEST_CODE_MANAGE_OVERLAY:
                if (!checkManageOverlayPermission()) {
                    Toast.makeText(this, "获取授权失败，请在权限设置中开启悬浮窗权限！", Toast.LENGTH_LONG).show();
                } else {
                    startFloatButtonService();
                }
                break;
        }
    }
}
