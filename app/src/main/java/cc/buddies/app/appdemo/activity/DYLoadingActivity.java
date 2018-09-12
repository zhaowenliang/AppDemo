package cc.buddies.app.appdemo.activity;

import android.os.Bundle;
import android.view.View;

import cc.buddies.app.appdemo.BaseActivity;
import cc.buddies.app.appdemo.R;
import cc.buddies.app.appdemo.view.DYLoadingView;

public class DYLoadingActivity extends BaseActivity {

    DYLoadingView loadingView;
    DYLoadingView loadingView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dyloaing);

        loadingView = findViewById(R.id.loading_view);
        loadingView1 = findViewById(R.id.loading_view1);
    }

    public void onClickStartLoading(View view) {
        if (loadingView != null) {
            loadingView.start();
        }
        if (loadingView1 != null) {
            loadingView1.start();
        }
    }

    public void onClickEndLoading(View view) {
        if (loadingView != null) {
            loadingView.stop();
        }
        if (loadingView1 != null) {
            loadingView1.stop();
        }
    }
}
