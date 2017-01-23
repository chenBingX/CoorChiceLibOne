package com.chenbing.iceweather.AdaptersAndItemViews.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.chenbing.iceweather.AdaptersAndItemViews.BaseItemViewHolder;
import com.chenbing.iceweather.AdaptersAndItemViews.ItemViews.TextSetSelectItemView;
import com.chenbing.iceweather.AdaptersAndItemViews.ItemViews.TextTestItem;
import com.chenbing.iceweather.interfaces.ItemViewSetDataFunction;

import java.util.List;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/10/17
 * Notes:
 */

public class TextTestAdapter extends RecyclerView.Adapter {
  private Context context;
  private List<String> datas;

  public TextTestAdapter(Context context, List<String> datas) {
    this.context = context;
    this.datas = datas;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new BaseItemViewHolder(new TextTestItem(context));
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    ItemViewSetDataFunction itemView = (ItemViewSetDataFunction) holder.itemView;
    itemView.setData(datas.get(position));
  }

  @Override
  public int getItemCount() {
    return datas.size();
  }
}
