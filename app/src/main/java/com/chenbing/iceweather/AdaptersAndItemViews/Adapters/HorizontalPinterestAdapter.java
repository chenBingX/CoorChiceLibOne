package com.chenbing.iceweather.AdaptersAndItemViews.Adapters;

import java.util.ArrayList;
import java.util.List;

import com.chenbing.iceweather.AdaptersAndItemViews.BaseItemViewHolder;
import com.chenbing.iceweather.AdaptersAndItemViews.ItemViews.HorizontalPinterestItemView;
import com.chenbing.iceweather.Utils.DisplayUtils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/10/9
 * Notes:
 */
public class HorizontalPinterestAdapter extends RecyclerView.Adapter {
  private final Context context;
  private List<String> datas = new ArrayList<>();

  public HorizontalPinterestAdapter(Context context, List<String> datas) {
    this.context = context;
    this.datas.addAll(datas);
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new BaseItemViewHolder(new HorizontalPinterestItemView(context));
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    HorizontalPinterestItemView itemView = (HorizontalPinterestItemView) holder.itemView;
    itemView.setData(datas.get(position));
    int size = (int) (Math.random() * DisplayUtils.dipToPx(60) + DisplayUtils.dipToPx(60));
    ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(size, size);
//    lp.width = size;
//    lp.height = size;
    itemView.setLayoutParams(lp);
  }

  @Override
  public int getItemCount() {
    return datas.size();
  }
}
