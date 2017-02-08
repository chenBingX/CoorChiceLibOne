package com.chenbing.coorchicelibone.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chenbing.coorchicelibone.CustemViews.RoundCornerTextView;
import com.chenbing.iceweather.R;

public class RoundCornerTextViewActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_round_corner_text_view);

    RoundCornerTextView mRoundCornerTextView = new RoundCornerTextView(this);
    mRoundCornerTextView.setTextAdjuster(v->{
      // 在这里进行一些操作，它们将在文字开始被绘制的前一刻被执行
    });
  }
}
