package com.chenbing.iceweather.AdaptersAndItemViews.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.chenbing.iceweather.AdaptersAndItemViews.BaseItemViewHolder;
import com.chenbing.iceweather.AdaptersAndItemViews.ItemViews.NavigatorItemView;
import com.chenbing.iceweather.Datas.Navigator;

import java.util.ArrayList;
import java.util.List;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/10/9
 * Notes:
 */
public class NavigatorAdapter extends RecyclerView.Adapter {
  private final Context context;
  private List<Navigator> datas = new ArrayList<>();

  public NavigatorAdapter(Context context, List<Navigator> datas) {
    this.context = context;
    this.datas.addAll(datas);
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new BaseItemViewHolder(new NavigatorItemView(context));
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    NavigatorItemView itemView = (NavigatorItemView) holder.itemView;
    itemView.setData(datas.get(position));
  }

  @Override
  public int getItemCount() {
    return datas.size();
  }
}
