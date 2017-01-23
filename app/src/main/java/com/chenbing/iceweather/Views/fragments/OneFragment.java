package com.chenbing.iceweather.Views.fragments;

import java.util.ArrayList;
import java.util.List;

import com.chenbing.iceweather.R;
import com.chenbing.iceweather.AdaptersAndItemViews.Adapters.TextTestAdapter;
import com.chenbing.iceweather.Utils.LogUtils;
import com.chenbing.iceweather.Views.BaseView.BaseFragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/11/19
 * Notes:
 */

public class OneFragment extends BaseFragment {
  @BindView(R.id.tv)
  TextView tv;
  @BindView(R.id.recycler_view)
  RecyclerView recyclerView;

  private List<String> datas = new ArrayList<>();

  public static BaseFragment getInstance() {
    BaseFragment oneFragment = new OneFragment();

    return oneFragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_one, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initData();
    initView();
    addListener();
    new Handler().postDelayed(()->{
      updateAllTextView(view);
    },3000L);
    LogUtils.e(getView().toString());
  }

  private void initData() {
    for (int i = 0; i < 10; i++) {
      datas.add(String.format("这是第%d条", i));
    }
  }

  private void initView() {
    recyclerView.setLayoutManager(
        new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    TextTestAdapter adapter = new TextTestAdapter(getActivity(), datas);
    recyclerView.setAdapter(adapter);
  }

  private void addListener() {
    tv.setOnClickListener(v -> {
      LogUtils.e("tv被电击！");
    });
  }

  private void updateAllTextView(View view) {
    if (view instanceof ViewGroup) {
      int childCount = ((ViewGroup) view).getChildCount();
      for (int i = 0; i < childCount; i++) {
        View childView = ((ViewGroup) view).getChildAt(i);
        if (childView instanceof ViewGroup) {
          updateAllTextView(childView);
        } else if (childView instanceof TextView) {
          updateTextViewAttribute((TextView) childView);
        }
      }
    } else if (view instanceof TextView) {
      updateTextViewAttribute((TextView) view);
    }

  }

  private void updateTextViewAttribute(TextView childView) {
    childView.setText("被更新了！");
    childView.setBackgroundColor(getActivity().getResources().getColor(R.color.opacity_7_yellow));
  }
}
