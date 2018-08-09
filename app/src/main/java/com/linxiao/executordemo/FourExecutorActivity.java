package com.linxiao.executordemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class FourExecutorActivity extends AppCompatActivity {


  private static final String TAG = "test_test";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  /**
   * 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程
   */
  public void cachedThreadPool(View view) {
    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    for (int i = 0; i < 10; i++) {
      final int index = i;
      cachedThreadPool.execute(new Runnable() {
        @Override
        public void run() {
          System.out.println(index + " cachedThreadPool current thread:  " + Thread.currentThread().getName());
          try {
            Thread.sleep(index * 1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      });
    }
  }

  /**
   * 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
   */
  public void fixedThreadPool(View view) {
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
    for (int i = 0; i < 10; i++) {
      final int index = i;

      fixedThreadPool.execute(new Runnable() {

        @Override
        public void run() {
          try {
            System.out.println(index + " fixedThreadPool current thread:  " + Thread.currentThread().getName());
            Thread.sleep(2000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      });
    }

  }

  /**
   * 创建一个定长线程池，支持定时及周期性任务执行。
   */
  public void schedulerThreadPool(View view) {
    ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
    scheduledThreadPool.schedule(new Runnable() {

      @Override
      public void run() {
        System.out.println("delay 3 seconds:  " + Thread.currentThread().getName());
      }
    }, 3, TimeUnit.SECONDS);
  }

  /**
   * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行
   */
  public void newSingleThreadExecutor(View view) {
    ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    for (int i = 0; i < 10; i++) {
      final int index = i;
      singleThreadExecutor.execute(new Runnable() {

        @Override
        public void run() {
          try {
            System.out.println(index + "index " + Thread.currentThread().getName());
            Thread.sleep(2000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      });
    }
  }

}
