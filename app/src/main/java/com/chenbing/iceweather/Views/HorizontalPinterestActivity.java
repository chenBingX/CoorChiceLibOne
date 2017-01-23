package com.chenbing.iceweather.Views;

import java.util.ArrayList;
import java.util.List;

import com.chenbing.iceweather.R;
import com.chenbing.iceweather.AdaptersAndItemViews.Adapters.HorizontalPinterestAdapter;
import com.chenbing.iceweather.Views.BaseView.BaseActivity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Project Name:IceWeather
 * Author:CoorChice
 * Date:2016/12/23
 * Notes:
 */

public class HorizontalPinterestActivity extends BaseActivity {
  @BindView(R.id.recycler_view)
  RecyclerView recyclerView;

  private List<String> datas = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_horizontal_pinterest);
    ButterKnife.bind(this);
    initData();
    initView();
    addListener();
  }


  @Override
  protected void initData() {
    for (int i = 0; i < 100; i++) {
      datas.add("" + i);
    }

  }

  @Override
  protected void initView() {
    configRecyclerView();

  }

  private void configRecyclerView() {
    recyclerView
        .setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL));
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
    recyclerView.setAdapter(new HorizontalPinterestAdapter(this, datas));
  }

  @Override
  protected void addListener() {

  }
}
