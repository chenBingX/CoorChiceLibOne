package com.chenbing.coorchicelibone.Views;

import com.chenbing.coorchicelibone.Utils.LogUtils;
import com.chenbing.coorchicelibone.Views.BaseView.BaseActivity;
import com.chenbing.iceweather.R;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestTouchEventActivity extends BaseActivity {

  @BindView(R.id.v1)
  View v1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test_touch_event);
    ButterKnife.bind(this);
    initData();
    initView();
  }

  @Override
  protected void initData() {

  }

  @Override
  protected void initView() {
    v1.setOnClickListener(v -> {
      LogUtils.e("onClick");
    });

    // 如果处理了longClick事件，那么onClick就不会触发
    // 如果没有处理longClick事件，onClick才会触发
    v1.setOnLongClickListener(v -> {
      LogUtils.e("onLongClick");
      return false;
    });

    new Handler().postDelayed(() -> {
      new AlertDialog.Builder(TestTouchEventActivity.this).setTitle("I'm the top window!").create()
          .show();
    }, 3000);
  }

  @Override
  protected void addListener() {

  }
}
