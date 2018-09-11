package cc.buddies.app.appdemo.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import cc.buddies.app.appdemo.BaseActivity;
import cc.buddies.app.appdemo.R;

public class SharedPreferenceActivity extends BaseActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference);

        init();
    }

    private void init() {
        // 使用PreferenceManager（存储文件名：packageName+"_"+preferencecs.xml）
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putBoolean("aa", true).apply();

        // 使用Context.getSharedPreferences()获取自定义名称的SharedPreferences。
        SharedPreferences msp = getSharedPreferences(this.getClass().getSimpleName(), Context.MODE_PRIVATE);
        msp.edit().putBoolean("aa", false).apply();

        msp.registerOnSharedPreferenceChangeListener(this);
        msp.edit().putBoolean("aa", true).apply();
        msp.unregisterOnSharedPreferenceChangeListener(this);

        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("11");
        treeSet.add("22");
        treeSet.add("33");
        msp.edit().putStringSet("bb", treeSet).apply();


        for (String str: treeSet) {
            Log.e("aaaa", str);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Log.e("aaaa", "aaaa----onSharedPreferenceChanged----s: " + s);
    }

}
