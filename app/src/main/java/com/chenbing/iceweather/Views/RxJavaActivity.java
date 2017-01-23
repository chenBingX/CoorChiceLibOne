package com.chenbing.iceweather.Views;

import com.chenbing.iceweather.R;
import com.chenbing.iceweather.Views.BaseView.BaseActivity;

import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RxJavaActivity extends BaseActivity {

  @BindView(R.id.btn_start)
  Button btnStart;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_rx_java);
    ButterKnife.bind(this);
    initData();
    initView();
    addListener();
  }

  @Override
  protected void initData() {

  }

  @Override
  protected void initView() {

  }

  @Override
  protected void addListener() {


  }
}
