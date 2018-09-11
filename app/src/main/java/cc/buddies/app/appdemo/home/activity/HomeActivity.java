package cc.buddies.app.appdemo.home.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cc.buddies.app.appdemo.BaseActivity;
import cc.buddies.app.appdemo.R;
import cc.buddies.app.appdemo.home.adapter.HomeViewPagerAdapter;
import cc.buddies.app.appdemo.home.fragment.HomeOneFragment;
import cc.buddies.app.treasury.utils.LogUtils;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    TabLayout tabLayout;
    ViewPager viewPager;

    private String[] tabs = {"标签1", "标签2", "标签3", "标签4", "标签5", "标签6", "标签7", "标签8"};
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initToolBar();
        initDrawerLayout();
        initViewPager();
        initTabLayout();
    }

    private void initToolBar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() == null) {
            return;
        }
        // 隐藏默认title
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // 左边返回按钮
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initDrawerLayout() {
        drawerLayout = findViewById(R.id.drawer_left);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initViewPager() {
        viewPager = findViewById(R.id.view_pager);

        fragments.clear();
        for (String tab : tabs) {
            fragments.add(new HomeOneFragment());
        }
        HomeViewPagerAdapter homeViewPagerAdapter = new HomeViewPagerAdapter(getSupportFragmentManager(), tabs, fragments);
        viewPager.setAdapter(homeViewPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                LogUtils.e("onPageSelected----" + fragments.get(position).getTag());
            }
        });
    }

    private void initTabLayout() {
        tabLayout = findViewById(R.id.tab_layout);
//        tabLayout.setTabMode(TabLayout.MODE_FIXED);
//        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this,R.color.colorAccent));
        for (String title: tabs) {
            tabLayout.addTab(tabLayout.newTab().setText(title));
        }
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LogUtils.e("onTabSelected----" + tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                LogUtils.e("onTabUnselected----" + tab.getText());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                LogUtils.e("onTabReselected----" + tab.getText());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case android.R.id.home:
//                Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.action_item1:
            case R.id.action_item2:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_camera:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
        }
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("state1", "111");
        LogUtils.e("onSaveInstanceState----save: state1");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String state1 = (String) savedInstanceState.get("state1");
        LogUtils.e("onRestoreInstanceState----get state1: " + state1);
    }
}
