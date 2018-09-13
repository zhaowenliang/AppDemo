package cc.buddies.app.appdemo.fragment_result;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class AvoidOnResultFragment extends Fragment {

    private Map<Integer, PublishSubject<ActivityResultInfo>> mSubjects = new HashMap<>();
    private Map<Integer, AvoidOnResult.CallBack> mCallBacks = new HashMap<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public Observable<ActivityResultInfo> startForResult(final Intent intent) {
        final PublishSubject<ActivityResultInfo> subject = PublishSubject.create();
        return subject.doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                mSubjects.put(subject.hashCode(), subject);
                startActivityForResult(intent, subject.hashCode());
            }
        });
    }

    public void startForResult(Intent intent, AvoidOnResult.CallBack callBack) {
        mCallBacks.put(callBack.hashCode(), callBack);
        startActivityForResult(intent, callBack.hashCode());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RxJava方式处理
        PublishSubject<ActivityResultInfo> subject = mSubjects.remove(requestCode);
        if (subject != null) {
            subject.onNext(new ActivityResultInfo(resultCode, data));
            subject.onComplete();
        }

        // Callback方式处理
        AvoidOnResult.CallBack callBack = mCallBacks.remove(requestCode);
        if (callBack != null) {
            callBack.onActivityResult(resultCode, data);
        }
    }
}
