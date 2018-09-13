package cc.buddies.app.appdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import cc.buddies.app.appdemo.BaseActivity;
import cc.buddies.app.appdemo.R;
import cc.buddies.app.appdemo.fragment_result.ActivityResultInfo;
import cc.buddies.app.appdemo.fragment_result.AvoidOnResult;
import cc.buddies.app.treasury.utils.LogUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class FragmentResultActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_result);
    }

    public void onClickButton(View view) {
        AvoidOnResult avoidOnResult = new AvoidOnResult(this);
        avoidOnResult.startForResult(DataResultActivity.class, new AvoidOnResult.CallBack() {
            @Override
            public void onActivityResult(int resultCode, Intent data) {
                if (resultCode == RESULT_OK) {
                    String stringExtra = data.getStringExtra("data");
                    String toastStr = stringExtra == null ? "没有返回数据" : stringExtra;
                    Toast.makeText(FragmentResultActivity.this, toastStr, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onClickButtonRx(View view) {
        AvoidOnResult avoidOnResult = new AvoidOnResult(this);
        avoidOnResult.startForResult(DataResultActivity.class)
                .filter(new Predicate<ActivityResultInfo>() {
                    @Override
                    public boolean test(ActivityResultInfo activityResultInfo) throws Exception {
                        return activityResultInfo != null && activityResultInfo.getResultCode() == RESULT_OK;
                    }
                })
                .flatMap(new Function<ActivityResultInfo, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(ActivityResultInfo activityResultInfo) throws Exception {
                        if (activityResultInfo.getData() != null) {
                            String stringExtra = activityResultInfo.getData().getStringExtra("data");
                            return Observable.just(stringExtra);
                        }
                        return Observable.just("没有返回数据");
                    }
                }).subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // Disposable可以切断订阅关系
                    }

                    @Override
                    public void onNext(String s) {
                        Toast.makeText(FragmentResultActivity.this, s, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("AvoidOnResult----Rx----onError");
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.e("AvoidOnResult----Rx----onComplete");
                    }
                });
    }
}
