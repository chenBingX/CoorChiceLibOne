package com.chenbing.coorchicelibone.Views.BaseView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.View;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/11/19
 * Notes:
 */

public abstract class BaseFragment extends Fragment {

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    view.setClickable(true);
  }

  abstract protected void initData();

  abstract protected void initView();

  abstract protected void addListener();

}
