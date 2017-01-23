package com.chenbing.iceweather.Datas;

import com.chenbing.iceweather.CustemViews.HSlideListView.SlideView;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:16/8/30
 * Notes:
 */
public class Message {
  private String content;
  private SlideView itemView;


  public Message(String content) {
    this.content = content;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public SlideView getItemView() {
    return itemView;
  }

  public void setItemView(SlideView itemView) {
    this.itemView = itemView;
  }
}
