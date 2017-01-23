package com.chenbing.iceweather.Views;

import com.chenbing.iceweather.R;
import com.chenbing.iceweather.Utils.DataCache;
import com.chenbing.iceweather.Utils.LogUtils;
import com.chenbing.iceweather.Views.BaseView.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskActivity_A extends BaseActivity {
  @BindView(R.id.btn_1)
  Button btn_1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_task_a);
    ButterKnife.bind(this);
    initData();
    initView();
    addListener();
    if (DataCache.getInstance().getActivity() == null){
      DataCache.getInstance().setActivity(this);
    } else {
      LogUtils.e(String.format("DataCache: TaskActivity_A: %s, TaskId = %s", DataCache.getInstance().getActivity().toString(), this.getTaskId() + ""));

    }
    LogUtils.e(String.format("TaskActivity_A: %s, TaskId = %s", this.toString(), this.getTaskId() + ""));
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
      Intent intent = new Intent(this,TaskActivity_B.class);
      startActivity(intent);
    });

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    LogUtils.e("TaskActivity_A - > onDestroy");
    DataCache.getInstance().setActivity(null);
  }

  @Override
  public void finish() {
    super.finish();
    LogUtils.e("TaskActivity_A - > finish");
  }
}
