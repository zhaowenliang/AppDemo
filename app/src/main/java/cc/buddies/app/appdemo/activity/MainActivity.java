 package cc.buddies.app.appdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cc.buddies.app.appdemo.BaseActivity;
import cc.buddies.app.appdemo.MyApplication;
import cc.buddies.app.appdemo.R;
import cc.buddies.app.appdemo.mvp.activity.MvpActivity;
import cc.buddies.app.appdemo.service.FloatButtonService;


 /**
  * ThisTime: 229
  * TotalTime: 229
  * WaitTime: 238
  */
 public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

     public void onClickCloseApp(View view) {
         MyApplication application = (MyApplication) getApplication();
         application.exit();
     }

     public void onClickHandler(View view) {
        startActivity(new Intent(this, HandlerActivity.class));
     }

     public void onClickSharedPreference(View view) {
         startActivity(new Intent(this, SharedPreferenceActivity.class));
     }

     public void onClickDataBase(View view) {
        startActivity(new Intent(this, SQLiteActivity.class));
     }

     public void onClickDynamicPermission(View view) {
         startActivity(new Intent(this, DynamicPermissionActivity.class));
     }

     public void onClickMessenger(View view) {
         startActivity(new Intent(this, MessengerActivity.class));
     }

     public void onClickMyService(View view) {
         startActivity(new Intent(this, ServiceActivity.class));
     }

     public void onClickAIDL(View view) {
         startActivity(new Intent(this, AIDLActivity.class));
     }

     public void onClickDispatchTouch(View view) {
         startActivity(new Intent(this, DispatchTouchActivity.class));
     }

     public void onClickImage(View view) {
         startActivity(new Intent(this, ImageActivity.class));
     }

     public void onClickRetrofit(View view) {
         startActivity(new Intent(this, RetrofitActivity.class));
     }

     public void onClickRxJava(View view) {
         startActivity(new Intent(this, RxJavaActivity.class));
     }

     public void onClickEventBus(View view) {
         startActivity(new Intent(this, EventBusActivity.class));
     }

     public void onClickCamera(View view) {
        startActivity(new Intent(this, CameraActivity.class));
     }

     public void onClickCameraNew(View view) {
        startActivity(new Intent(this, CameraNewActivity.class));
        // enterAnim: 新Activity进入动画；exitAnim: 旧Activity离开动画。
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
     }

     public void onClickMVP(View view) {
        startActivity(new Intent(this, MvpActivity.class));
     }

     public void onClickHome(View view) {
        startActivity(new Intent(this, NavigationActivity.class));
     }

     public void onClickDYLoading(View view) {
        startActivity(new Intent(this, DYLoadingActivity.class));
     }

     public void onClickRVLayoutManager(View view) {
         startActivity(new Intent(this, RVLayoutManagerActivity.class));
     }

     public void onClickFragmentResult(View view) {
         startActivity(new Intent(this, FragmentResultActivity.class));
     }

     public void onClickFloatWindow(View view) {
         startActivity(new Intent(this, FloatWindowActivity.class));
     }

     @Override
     public void onBackPressed() {
         super.onBackPressed();
         // 完全退出，启动应用重新初始化Application。
         MyApplication application = (MyApplication) getApplication();
         application.exit();
     }

 }
