package com.chenbing.iceweather.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.chenbing.iceweather.R;
import com.chenbing.iceweather.Utils.LogUtils;
import com.chenbing.iceweather.Views.BaseView.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskActivity_B extends BaseActivity {
  @BindView(R.id.btn_1)
  Button btn_1;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_task__b);
    ButterKnife.bind(this);
    initData();
    initView();
    addListener();
    LogUtils.e(String.format("TaskActivity_B: %s, TaskId = %s", this.toString(), this.getTaskId() + ""));

  }

  @Override
  protected void initData() {

  }

  @Override
  protected void initView() {

  }

  @Override
  protected void addListener() {
    btn_1.setOnClickListener(v -> {
      Intent intent = new Intent(this,TaskActivity_C.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
    });
  }
}
