package com.linxiao.executordemo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


  private static final String TAG = "test_test";

  private ThreadFactory threadFactory = new ThreadFactory() {
    @Override
    public Thread newThread(@NonNull final Runnable r) {
      return new Thread() {
        @Override
        public void run() {
          Log.d(TAG, "thread start");
          r.run();
          Log.d(TAG, "thread over");
        }
      };
    }
  };


  private ScheduledExecutorService mScheduledExecutorService = Executors.newScheduledThreadPool(4, new ThreadFactory() {
    @Override
    public Thread newThread(@NonNull Runnable r) {
      return new Thread(r) {
        @Override
        public void run() {
          Log.e(TAG, "newThread");
          super.run();
          Log.e(TAG, "newThread over");
        }
      };
    }
  });


  // 通过静态方法创建ScheduledExecutorService的实例
//  private ScheduledExecutorService mScheduledExecutorService = Executors.newScheduledThreadPool(4);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


//    mScheduledExecutorService.schedule(threadFactory.newThread(new Runnable() {
//      @Override
//      public void run() {
//        Log.e(TAG, "first task");
//      }
//    }), 1, TimeUnit.SECONDS);

    // 循环任务，以上一次任务的结束时间计算下一次任务的开始时间
    //TODO 为什么不执行 Log.e(TAG, "newThread over"); 可能是线程还没有执行结束，需要分析内部原理
    mScheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
      @Override
      public void run() {
        Log.e(TAG, "scheduleWithFixedDelay:" + System.currentTimeMillis() / 1000);
        try {
          Thread.sleep(3000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }, 1, 1, TimeUnit.SECONDS);
  }


}
