package cc.buddies.app.appdemo.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.concurrent.TimeUnit;

import cc.buddies.app.appdemo.BaseActivity;
import cc.buddies.app.appdemo.R;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RxJavaActivity extends BaseActivity {

    private static final String TAG = "RxJavaActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
    }

    public void onClickButton(View view) {
//        // 1. 创建时传入整型1、2、3、4
//        // 在创建后就会发送这些对象，相当于执行了onNext(1)、onNext(2)、onNext(3)、onNext(4)
//        Observable.just(1, 2, 3, 4)
//                // 至此，一个Observable对象创建完毕，以下步骤仅为展示一个完整demo，可以忽略
//                // 2. 通过通过订阅（subscribe）连接观察者和被观察者
//                // 3. 创建观察者 & 定义响应事件的行为
//                .subscribe(new Observer<Integer>() {
//
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "开始采用subscribe连接");
//                    }
//                    // 默认最先调用复写的 onSubscribe（）
//
//                    @Override
//                    public void onNext(Integer value) {
//                        Log.d(TAG, "接收到了事件" + value);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "对Error事件作出响应");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "对Complete事件作出响应");
//                    }
//
//                });

//        // 1. 设置需要传入的数组
//        Integer[] items = { 0, 1, 2, 3, 4 };
//
//        // 2. 创建被观察者对象（Observable）时传入数组
//        // 在创建后就会将该数组转换成Observable & 发送该对象中的所有数据
//        Observable.fromArray(items)
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "开始采用subscribe连接");
//                    }
//
//                    @Override
//                    public void onNext(Integer value) {
//                        Log.d(TAG, "接收到了事件"+ value  );
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "对Error事件作出响应");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "对Complete事件作出响应");
//                    }
//
//                });

        /*
         * 快速发送集合
         **/
//// 1. 设置一个集合
//        List<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.add(2);
//        list.add(3);
//
//// 2. 通过fromIterable()将集合中的对象 / 数据发送出去
//        Observable.fromIterable(list)
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "开始采用subscribe连接");
//                    }
//
//                    @Override
//                    public void onNext(Integer value) {
//                        Log.d(TAG, "接收到了事件"+ value  );
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "对Error事件作出响应");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "对Complete事件作出响应");
//                    }
//                });


//        final Integer i = 10;
//
//        // 2. 通过defer 定义被观察者对象
//        // 注：此时被观察者对象还没创建
//        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
//            @Override
//            public ObservableSource<? extends Integer> call() throws Exception {
//                return Observable.just(i);
//            }
//        });
//
//                // 注：此时，才会调用defer（）创建被观察者对象（Observable）
//                observable.subscribe(new Observer<Integer>() {
//
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "开始采用subscribe连接");
//                    }
//
//                    @Override
//                    public void onNext(Integer value) {
//                        Log.d(TAG, "接收到的整数是"+ value  );
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "对Error事件作出响应");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "对Complete事件作出响应");
//                    }
//                });

        // 该例子 = 延迟2s后，发送一个long类型数值
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Long value) {
                        Log.d(TAG, "接收到了事件"+ value  );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }

                });


    }
}
