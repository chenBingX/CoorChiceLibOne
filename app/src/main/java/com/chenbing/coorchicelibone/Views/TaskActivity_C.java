package com.chenbing.coorchicelibone.Views;

import com.chenbing.iceweather.R;
import com.chenbing.coorchicelibone.Utils.LogUtils;
import com.chenbing.coorchicelibone.Views.BaseView.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskActivity_C extends BaseActivity {
  @BindView(R.id.btn_1)
  Button btn_1;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_task_c);
    ButterKnife.bind(this);
    initData();
    initView();
    addListener();
    LogUtils.e(String.format("TaskActivity_C: %s, TaskId = %s", this.toString(), this.getTaskId() + ""));
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
      Intent intent = new Intent(this, TaskActivity_A.class);
      startActivity(intent);
    });
  }
}
