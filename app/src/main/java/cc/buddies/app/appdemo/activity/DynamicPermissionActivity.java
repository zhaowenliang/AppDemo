package cc.buddies.app.appdemo.activity;

import android.Manifest;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import cc.buddies.app.appdemo.BaseActivity;
import cc.buddies.app.appdemo.R;
import cc.buddies.app.treasury.permission.CheckPermissionUtils;
import cc.buddies.app.treasury.utils.FileProvider7;
import cc.buddies.app.treasury.utils.LogUtils;
import cc.buddies.app.treasury.utils.TakePictureUtils;

public class DynamicPermissionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_permission);

        initView();
    }

    private void initView() {
        TextView textView = (TextView) findViewById(R.id.text_view);
        textView.setText("动态权限适配\\n 正常权限在清单文件中申请\\n 危险权限动态申请。");
    }

    public void onClickApplyPermission(View view) {
        photograph();
    }

    private void photograph() {
        boolean hasCameraPermission = CheckPermissionUtils.hasPermission(this, Manifest.permission.CAMERA);
        LogUtils.e("是否具有相机权限 --> " + hasCameraPermission);

        if (!hasCameraPermission) {
            if (CheckPermissionUtils.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                // 上次请求权限被拒绝，在此应向用户解释需要此权限的原因。
                LogUtils.e("此权限之前被拒绝！");
                CheckPermissionUtils.dialogPermissionRationale(this, "此权限用于拍摄照片，请授予此权限。", 1);
            } else {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 1);
            }
            return;
        }

        goPhotograph();
    }

    private void goPhotograph() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                File imageFile = TakePictureUtils.createImageFile(this);
                Uri fileUri = FileProvider7.getUriForFile(this, imageFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(takePictureIntent, 2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                int isGranted = grantResults[0];
                if (isGranted == PackageManager.PERMISSION_DENIED) {
                    if (!CheckPermissionUtils.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                        CheckPermissionUtils.dialogPermissionRationale(this, "此权限用于拍摄照片，请授予此权限。", 1);
                    }
                } else if (isGranted == PackageManager.PERMISSION_GRANTED) {
                    LogUtils.e(isGranted + " 权限获取成功!");
                    goPhotograph();
                }

                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1:
                LogUtils.e("系统设置授权页面返回！");

                photograph();
                break;
            case 2:
                LogUtils.e("系统拍照页面返回！");
                break;
        }
    }
}
