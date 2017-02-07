package com.chenbing.coorchicelibone.Managers;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/11/17
 * Notes:
 */

public class CustomLinearLayoutManager extends LinearLayoutManager{

  public CustomLinearLayoutManager(Context context) {
    super(context);
  }

  @Override
  protected int getExtraLayoutSpace(RecyclerView.State state) {
    return super.getExtraLayoutSpace(state);
  }
}
