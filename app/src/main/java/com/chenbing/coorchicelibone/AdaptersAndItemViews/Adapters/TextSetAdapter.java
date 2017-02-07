package com.chenbing.coorchicelibone.AdaptersAndItemViews.Adapters;

import java.util.ArrayList;
import java.util.List;

import com.chenbing.coorchicelibone.AdaptersAndItemViews.ItemViews.TextSetInputItemView;
import com.chenbing.coorchicelibone.Datas.TextProperty;
import com.chenbing.coorchicelibone.AdaptersAndItemViews.BaseItemViewHolder;
import com.chenbing.coorchicelibone.AdaptersAndItemViews.ItemViews.TextSetSelectItemView;
import com.chenbing.coorchicelibone.interfaces.ItemViewSetDataFunction;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/10/10
 * Notes:
 */
public class TextSetAdapter extends RecyclerView.Adapter {
  private final Context context;
  private List<TextProperty> datas = new ArrayList<>();

  private OnDataChangedListener onDataChangedListener;

  public TextSetAdapter(Context context, List<TextProperty> datas) {
    this.context = context;
    this.datas.addAll(datas);
  }


  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == 0) {
      return new BaseItemViewHolder(new TextSetInputItemView(context));
    } else {
      return new BaseItemViewHolder(new TextSetSelectItemView(context));
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    ItemViewSetDataFunction itemView = (ItemViewSetDataFunction) (holder.itemView);
    itemView.setData(datas.get(position));
    if (holder.itemView instanceof TextSetInputItemView) {
      ((TextSetInputItemView) holder.itemView).setOnValueChangedListener(value -> {
        if (onDataChangedListener != null) {
          onDataChangedListener.onDataChanged(datas);
        }
      });
    } else if (holder.itemView instanceof  TextSetSelectItemView){
      ((TextSetSelectItemView)holder.itemView).setOnValueChangedListener(()->{
        if (onDataChangedListener != null){
          onDataChangedListener.onDataChanged(datas);
        }
      });
    }
  }

  @Override
  public int getItemCount() {
    return datas.size();
  }

  @Override
  public int getItemViewType(int position) {
    if (datas.get(position).getType() == TextProperty.TextPropertyType.INPUT) {
      return 0;
    } else {
      return 1;
    }
  }

  public void setOnDataChangedListener(OnDataChangedListener onDataChangedListener) {
    this.onDataChangedListener = onDataChangedListener;
  }

  public interface OnDataChangedListener {
    void onDataChanged(List<TextProperty> datas);
  }

}
