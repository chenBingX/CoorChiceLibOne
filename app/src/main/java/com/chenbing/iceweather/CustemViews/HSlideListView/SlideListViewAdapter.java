package com.chenbing.iceweather.CustemViews.HSlideListView;

import java.util.List;

import com.chenbing.iceweather.R;
import com.chenbing.iceweather.Datas.Message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:16/8/30
 * Notes:
 */
public class SlideListViewAdapter extends BaseAdapter {
  private List<Message> data;
  LayoutInflater inflate;

  public SlideListViewAdapter(Context context, List<Message> data) {
    this.data = data;
    inflate = LayoutInflater.from(context);
  }

  @Override
  public int getCount() {
    return data.size();
  }

  @Override
  public Object getItem(int i) {
    return data.get(i);
  }

  @Override
  public long getItemId(int i) {
    return i;
  }

  @Override
  public View getView(int i, View view, ViewGroup viewGroup) {
    ViewHolder viewHolder;
    if (view == null) {
      view = inflate.inflate(R.layout.item_test, null);
      viewHolder = new ViewHolder(view);
      view.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) view.getTag();
    }
    bindData(i, viewHolder);
    return view;
  }

  public static class ViewHolder {
    SlideView slideView;
    TextView tv;

    ViewHolder(View view) {
      slideView = (SlideView) view.findViewById(R.id.slide_view);
      tv = (TextView) view.findViewById(R.id.tv);
    }
  }

  private void bindData(int position, ViewHolder holder) {
    holder.tv.setText(data.get(position).getContent());
    data.get(position).setItemView(holder.slideView);
    holder.slideView.shrink();
  }


}
