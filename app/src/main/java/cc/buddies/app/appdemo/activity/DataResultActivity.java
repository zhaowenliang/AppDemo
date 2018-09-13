package cc.buddies.app.appdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cc.buddies.app.appdemo.BaseActivity;
import cc.buddies.app.appdemo.R;

public class DataResultActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_result);
    }

    public void onClickButton(View view) {
        Intent data = new Intent();
        data.putExtra("data", "This is a secret!");
        setResult(RESULT_OK, data);
        finish();
    }
}
