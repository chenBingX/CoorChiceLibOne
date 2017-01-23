package com.chenbing.iceweather.Views;

import java.util.ArrayList;
import java.util.List;

import com.chenbing.iceweather.R;
import com.chenbing.iceweather.AdaptersAndItemViews.Adapters.HeaderFooterRvAdapterWrapper;
import com.chenbing.iceweather.AdaptersAndItemViews.Adapters.TextTestAdapter;
import com.chenbing.iceweather.Views.BaseView.BaseActivity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeaderFooterRecyclerViewActivity extends BaseActivity {
  @BindView(R.id.rv_header_footer)
  RecyclerView headerFooterListView;

  private List<String> datas = new ArrayList<>();
  private List<String> headerDatas = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_header_footer_recycler_view);
    ButterKnife.bind(this);
    initData();
    initView();
    addListener();
  }

  @Override
  protected void initData() {
    for (int i = 0; i < 50; i++) {
      datas.add(String.format("第%d个Item", i + 1));
    }

    for (int i = 0; i < 10; i++) {
      headerDatas.add(String.format("headerItem " + (i + 1)));
    }
  }

  @Override
  protected void initView() {
    // 初始化Header1
    TextTestAdapter headerAdapter = new TextTestAdapter(this, headerDatas);
    RecyclerView header = new RecyclerView(this);
    header.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    header.setItemAnimator(new DefaultItemAnimator());
    header.setAdapter(headerAdapter);
    ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
    header.setLayoutParams(lp);

    // 初始化Header2
    TextView textView = new TextView(this);
    textView.setText("-----我只是一条分割线而已-----");
    textView.setTextSize(12);
    textView.setGravity(Gravity.CENTER);
    ViewGroup.LayoutParams lp2 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
    textView.setLayoutParams(lp2);

    // 初始化Header3
    TextView textView2 = new TextView(this);
    textView2.setText("-----我只是另一条分割线而已-----");
    textView2.setTextSize(12);
    textView2.setGravity(Gravity.CENTER);
    ViewGroup.LayoutParams lp3 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
    textView2.setLayoutParams(lp3);


    TextTestAdapter targetAdapter = new TextTestAdapter(this, datas);
    /*多布局Adapter创建*/
    HeaderFooterRvAdapterWrapper adapterWrapper = new HeaderFooterRvAdapterWrapper(targetAdapter);
    adapterWrapper.addHeader(header); // 添加一个Header
    adapterWrapper.addHeader(textView); // 添加第二个Header

    headerFooterListView
        .setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    headerFooterListView
        .setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    headerFooterListView.setItemAnimator(new DefaultItemAnimator());
    headerFooterListView.setAdapter(adapterWrapper);
    adapterWrapper.addHeader(textView2); // 添加第三个Header

    new Handler().postDelayed(() -> {
      if (header == null) {
        return;
      }

      for (int i = 10; i < 20; i++) {
        headerDatas.add(String.format("headerItem " + (i + 1)));
      }
      headerAdapter.notifyDataSetChanged();
    }, 3000);

    new Handler().postDelayed(() -> {
      if (headerFooterListView == null) {
        return;
      }

      for (int i = 50; i < 100; i++) {
        datas.add(String.format("第%d个Item ", (i + 1)));
      }
      adapterWrapper.notifyDataSetChanged();
    }, 6000);

  }

  @Override
  protected void addListener() {

  }
}
