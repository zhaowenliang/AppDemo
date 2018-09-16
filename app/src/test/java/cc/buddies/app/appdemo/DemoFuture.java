package cc.buddies.app.appdemo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class DemoFuture {

    public void demo() {

        MyTask myTask = new MyTask();

        // 1. 第一种方式(不使用线程池，使用Thread)
//        FutureTask<Integer> future = new FutureTask<>(myTask);
//        Thread thread = new Thread(future);
//        thread.start();

        ExecutorService executorService = Executors.newCachedThreadPool();
        // 2. 第二种方式(使用线程池)
//        final Future<Integer> future = executorService.submit(myTask);
        // 3. 第三种方式(使用线程池)
        final FutureTask<Integer> future = new FutureTask<>(myTask);
        executorService.submit(future);

        int result = -1;
        try {
            result = future.get();

            if (result > 0) {
                FutureTask<Integer> futureTask2 = new FutureTask<>(new MyTask2(result));
                new Thread(futureTask2).start();
                int result2 = futureTask2.get();
                System.out.println("最终结果: " + result2);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    class MyTask implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            System.out.println("Thread [" + Thread.currentThread().getName() + "]");

            int result = 100;
            Thread.sleep(3000);
            return result;
        }
    }

    class MyTask2 implements Callable<Integer> {

        private int params;

        public MyTask2(int params) {
            this.params = params;
        }

        @Override
        public Integer call() throws Exception {
            params += 1;
            return params;
        }
    }

}
