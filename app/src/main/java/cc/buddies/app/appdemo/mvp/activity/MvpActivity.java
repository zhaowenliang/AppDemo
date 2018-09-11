package cc.buddies.app.appdemo.mvp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cc.buddies.app.appdemo.R;
import cc.buddies.app.appdemo.mvp.presenter.MvpPresenter;
import cc.buddies.app.appdemo.mvp.view.MvpView;

public class MvpActivity extends BaseMVPActivity implements MvpView {

    TextView textView;
    private MvpPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);

        textView = findViewById(R.id.text_view);

        mPresenter = new MvpPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void showData(String data) {
        textView.setText(data);
    }

    public void onClickButton(View view) {
        mPresenter.getData("normal");
    }
}
