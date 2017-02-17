package com.chenbing.coorchicelibone.Views;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.chenbing.iceweather.R;
import com.sleepycat.asm.Handle;

public class ThreadActivity extends AppCompatActivity {
  static int count = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    count++;
    setContentView(R.layout.activity_thread);
    Log.e("ThreadActivity", " -> onCreate: count = " + count);
    Thread mainThread = Thread.currentThread();
    Thread newThread = new Thread(()->{
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      Looper.prepare();
      Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
          super.handleMessage(msg);
        }
      };
      Looper.myLooper().quitSafely();

      Looper.loop();
    });
    newThread.start();



  }
}
