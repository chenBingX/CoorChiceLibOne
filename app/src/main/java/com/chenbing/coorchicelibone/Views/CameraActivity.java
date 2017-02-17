package com.chenbing.coorchicelibone.Views;

import com.chenbing.coorchicelibone.CustemViews.CameraTest;
import com.chenbing.coorchicelibone.Views.BaseView.BaseActivity;
import com.chenbing.iceweather.R;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;



public class CameraActivity extends BaseActivity {
  @BindView(R.id.reset)
  Button btn_reset;

  @BindView(R.id.draw_3d)
  CameraTest draw3d;

  @BindView(R.id.x_add)
  Button btn_x_add;
  @BindView(R.id.x_subtract)
  Button btn_x_subtract;

  @BindView(R.id.y_add)
  Button btn_y_add;
  @BindView(R.id.y_subtract)
  Button btn_y_subtract;

  @BindView(R.id.z_add)
  Button btn_z_add;
  @BindView(R.id.z_subtract)
  Button btn_z_subtract;

  @BindView(R.id.z_translate_add)
  Button btn_z_translate_add;
  @BindView(R.id.z_translate_subtract)
  Button btn_z_translate_subtract;

  @BindView(R.id.bottom_btn)
  Button btn_bottom;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_camera);
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
    int addDegree = 10;

    btn_reset.setOnClickListener(v -> {
      draw3d.reset();
    });

    btn_x_add.setOnClickListener(v -> {
      draw3d.setRotateX(draw3d.getRotateX() + addDegree);

    });

    btn_x_subtract.setOnClickListener(v -> {
      draw3d.setRotateX(draw3d.getRotateX() - addDegree);

    });

    btn_y_add.setOnClickListener(v -> {
      draw3d.setRotateY(draw3d.getRotateY() + addDegree);

    });

    btn_y_subtract.setOnClickListener(v -> {
      draw3d.setRotateY(draw3d.getRotateY() - addDegree);

    });

    btn_z_add.setOnClickListener(v -> {
      draw3d.setRotateZ(draw3d.getRotateZ() + addDegree);

    });

    btn_z_subtract.setOnClickListener(v -> {
      draw3d.setRotateZ(draw3d.getRotateZ() - addDegree);

    });

    btn_z_translate_add.setOnClickListener(v -> {
      draw3d.setTranslateZ(draw3d.getTranslateZ() + 10);
    });

    btn_z_translate_subtract.setOnClickListener(v -> {
      draw3d.setTranslateZ(draw3d.getTranslateZ() - 10);
    });


    btn_bottom.setOnClickListener(v -> {
      startActivity(new Intent(this, SquareLayoutActivity.class));
    });

  }
}
