package cc.buddies.app.appdemo.home.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class HomeViewPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles;
    private List<Fragment> mListFragment;
    private List<String> mTags = new ArrayList<>();

    public HomeViewPagerAdapter(FragmentManager fm, String[] mTitles, List<Fragment> listFragment) {
        super(fm);
        this.mTitles = mTitles;
        this.mListFragment = listFragment;
    }

    @Override
    public Fragment getItem(int position) {
        return mListFragment.get(position);
    }

    @Override
    public int getCount() {
        return mListFragment != null ? mListFragment.size() : 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles != null ? mTitles[position] : "";
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        String tag = fragment.getTag();
        if (!mTags.contains(tag)) {
            mTags.add(tag);
        }
        return fragment;
    }
}
