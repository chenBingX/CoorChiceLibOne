package com.chenbing.coorchicelibone.Views;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chenbing.coorchicelibone.Utils.LogUtils;
import com.chenbing.iceweather.R;

public class AnalyzeViewFrameworkActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_analyze_view_framwork);
    boolean a = true;
    boolean b = true;
    boolean c = a^b;
    TextView viewById = (TextView) findViewById(R.id.tv);
    viewById.setOnTouchListener((v, event) -> {
      viewById.setText("onTouch");
      LogUtils.e("onTouch");
      return false;
    });


  }

  public int add(){
    return -1;
  }
}
