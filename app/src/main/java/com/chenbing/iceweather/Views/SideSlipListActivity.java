package com.chenbing.iceweather.Views;

import java.util.ArrayList;
import java.util.List;

import com.chenbing.iceweather.R;
import com.chenbing.iceweather.CustemViews.HSlideListView.SlideListViewAdapter;
import com.chenbing.iceweather.Datas.Message;
import com.chenbing.iceweather.Views.BaseView.BaseActivity;

import android.os.Bundle;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SideSlipListActivity extends BaseActivity {
  @BindView(R.id.lv)
  ListView lv;
  private List<Message> messages = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_side_slip_list);
    ButterKnife.bind(this);
    initData();
    initView();
  }

  @Override
  protected void initData() {
    for (int i = 0; i < 100; i++) {
      messages.add(new Message(getString(R.string.side_slip_list_test_string)));
    }
  }

  @Override
  protected void initView() {
    lv.setAdapter(new SlideListViewAdapter(this, messages));
  }

  @Override
  protected void addListener() {

  }
}
