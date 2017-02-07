package com.chenbing.coorchicelibone.interfaces;

import android.support.v7.widget.RecyclerView;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/10/27
 * Notes:
 */
public interface OnItemTouchCallbackListener {
  boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
      RecyclerView.ViewHolder target);

  void onSwiped(RecyclerView.ViewHolder viewHolder, int direction);
}
