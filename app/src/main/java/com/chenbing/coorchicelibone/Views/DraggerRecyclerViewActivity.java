package com.chenbing.coorchicelibone.Views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.chenbing.coorchicelibone.Utils.DisplayUtils;
import com.chenbing.iceweather.R;
import com.chenbing.coorchicelibone.AdaptersAndItemViews.Adapters.TextTestAdapter;
import com.chenbing.coorchicelibone.Helpers.CustomItemTouchHelper;
import com.chenbing.coorchicelibone.Views.BaseView.BaseActivity;
import com.chenbing.coorchicelibone.interfaces.OnItemTouchCallbackListener;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DraggerRecyclerViewActivity extends BaseActivity {
  @BindView(R.id.rv_dragger_list)
  RecyclerView draggerList;

  private List<String> datas = new ArrayList<>();
  private TextTestAdapter adapter;
  private CustomItemTouchHelper itemTouchHelper;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dragger_recycler_view);
    ButterKnife.bind(this);
    initData();
    initView();
    addListener();
  }

  @Override
  protected void initData() {
    for (int i = 0; i < 100; i++) {
      datas.add("我是Item" + i);
    }
  }

  @Override
  protected void initView() {
    draggerList.setItemAnimator(new DefaultItemAnimator());
    draggerList.addItemDecoration(new RecyclerView.ItemDecoration() {
      @Override
      public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        if (itemPosition != parent.getChildCount() - 1) {
          outRect.bottom = (int) DisplayUtils.dipToPx(0.5f);
        }
      }
    });
    draggerList.setLayoutManager(new LinearLayoutManager(this));
    adapter = new TextTestAdapter(this, datas);
    //创建ItemTouchHelper
    itemTouchHelper = new CustomItemTouchHelper(new OnItemTouchCallbackListener() {
      @Override
      public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
          RecyclerView.ViewHolder target) {
        if (datas == null) {
          return false;
        }
        //处理拖动排序
        //使用Collection对数组进行重排序，目的是把我们拖动的Item换到下一个目标Item的位置
        Collections.swap(datas, viewHolder.getAdapterPosition(), target.getAdapterPosition());
        //通知Adapter它的Item发生了移动
        adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
      }

      @Override
      public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (datas == null) {
          return;
        }
        //处理滑动删除
        //直接从数据中删除该Item的数据
        datas.remove(viewHolder.getAdapterPosition());
        //通知Adapter有Item被移除了
        adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
      }
    });
    //并绑定RecyclerView
    itemTouchHelper.attachToRecyclerView(draggerList);
    draggerList.setAdapter(adapter);
  }

  @Override
  protected void addListener() {

  }
}
