package com.chenbing.iceweather.Views;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;

import com.chenbing.iceweather.AdaptersAndItemViews.Adapters.TextTestAdapter;
import com.chenbing.iceweather.Managers.CustomLayoutManager;
import com.chenbing.iceweather.R;
import com.chenbing.iceweather.Utils.DisplayUtils;
import com.chenbing.iceweather.Views.BaseView.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomLayoutManagerActivity extends BaseActivity {
  @BindView(R.id.rv)
  RecyclerView recyclerView;

  private List<String> datas = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_custem_layout_manager);
    ButterKnife.bind(this);
    initData();
    initView();
  }

  @Override
  protected void initData() {
    for (int i = 0; i < 100; i++) {
      datas.add("我是Item" + i);
    }
  }

  @Override
  protected void initView() {
    TextTestAdapter adapter = new TextTestAdapter(this, datas);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setLayoutManager(new CustomLayoutManager());
    recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
      @Override
      public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        outRect.bottom = (int) DisplayUtils.dipToPx(0.5f);
//        if (itemPosition != parent.getChildCount() - 1){
//        }
      }
    });
    recyclerView.setAdapter(adapter);
  }

  @Override
  protected void addListener() {

  }
}
