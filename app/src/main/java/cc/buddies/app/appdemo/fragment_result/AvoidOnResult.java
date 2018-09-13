package cc.buddies.app.appdemo.fragment_result;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;

import io.reactivex.Observable;

public class AvoidOnResult {

    private AvoidOnResultFragment mAvoidOnResultFragment;

    private static final String TAG = "AvoidOnResult";

    public AvoidOnResult(Activity activity) {
        mAvoidOnResultFragment = getAvoidOnResultFragment(activity);
    }

    public AvoidOnResult(Fragment fragment) {
        this(fragment.getActivity());
    }

    private AvoidOnResultFragment getAvoidOnResultFragment(Activity activity) {
        AvoidOnResultFragment avoidOnResultFragment = findAvoidOnResultFragment(activity);
        if (avoidOnResultFragment == null) {
            avoidOnResultFragment = new AvoidOnResultFragment();
            FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(avoidOnResultFragment, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return avoidOnResultFragment;
    }

    private AvoidOnResultFragment findAvoidOnResultFragment(Activity activity) {
        return (AvoidOnResultFragment) activity.getFragmentManager().findFragmentByTag(TAG);
    }

    /**
     * 使用RxJava方式处理onActivityResult
     * @param intent 意图
     * @return Observable<ActivityResultInfo>
     */
    public Observable<ActivityResultInfo> startForResult(Intent intent) {
        return mAvoidOnResultFragment.startForResult(intent);
    }

    /**
     * 使用RxJava方式处理onActivityResult
     * @param clazz 请求目标
     * @return Observable<ActivityResultInfo>
     */
    public Observable<ActivityResultInfo> startForResult(Class<?> clazz) {
        Intent intent = new Intent(mAvoidOnResultFragment.getActivity(), clazz);
        return startForResult(intent);
    }

    /**
     * 使用CallBack方式处理onActivityResult
     * @param intent 意图
     * @param callBack onActivityResult回调接口
     */
    public void startForResult(Intent intent, CallBack callBack) {
        mAvoidOnResultFragment.startForResult(intent, callBack);
    }

    /**
     * 使用CallBack方式处理onActivityResult
     * @param clazz 请求目标
     * @param callBack onActivityResult回调接口
     */
    public void startForResult(Class<?> clazz, CallBack callBack) {
        Intent intent = new Intent(mAvoidOnResultFragment.getActivity(), clazz);
        mAvoidOnResultFragment.startForResult(intent, callBack);
    }

    /**
     * onActivityResult回调接口
     */
    public interface CallBack {
        void onActivityResult(int resultCode, Intent data);
    }

}
