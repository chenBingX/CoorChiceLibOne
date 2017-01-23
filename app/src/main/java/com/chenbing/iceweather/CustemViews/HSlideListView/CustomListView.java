package com.chenbing.iceweather.CustemViews.HSlideListView;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ListView;

import com.chenbing.iceweather.Datas.Message;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:16/8/30
 * Notes:
 */
public class CustomListView extends ListView {

  private SlideView currentView;
  int touchSlop;

  public CustomListView(Context context) {
    super(context);
    init();
  }

  public CustomListView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public CustomListView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public CustomListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private void init() {
    touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
  }

  @Override
  public boolean onInterceptHoverEvent(MotionEvent event) {
    return super.onInterceptHoverEvent(event);
  }


  @Override
  public boolean onTouchEvent(MotionEvent ev) {
    switch (ev.getAction()) {
      case MotionEvent.ACTION_DOWN:
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        int position = pointToPosition(x, y);
        if (position != INVALID_POSITION){
          Message itemData = (Message) getItemAtPosition(position);
          currentView = itemData.getItemView();
        }
        default:
        break;
    }
    if (currentView != null){
      currentView.onRequireTouchEvent(ev);
    }
    return super.onTouchEvent(ev);
  }
}
