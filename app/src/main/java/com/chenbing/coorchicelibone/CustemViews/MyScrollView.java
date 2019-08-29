package com.chenbing.coorchicelibone.CustemViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.chenbing.coorchicelibone.Utils.LogUtils;

/**
 * Project Name:CoorChiceLibOne
 * Author:CoorChice
 * Date:2019/8/18
 * Notes:
 */
public class MyScrollView extends ScrollView {

  public MyScrollView(Context context) {
    super(context);
  }

  public MyScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }


  private int  mLastX;
  private int  mLastY;

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    int x = (int) ev.getX();
    int y = (int) ev.getY();
    int action = ev.getAction();
    boolean intercept = false;
    LogUtils.e("ACTION = " + action);
    switch (action) {
      case MotionEvent.ACTION_DOWN:
        // 不触发Down，后面就无法滑动了
        onTouchEvent(ev);
        break;
      case MotionEvent.ACTION_MOVE:
        int gapX = x - mLastX;
        int gapY = y - mLastY;
        if (Math.abs(gapX) > Math.abs(gapY)) {
          intercept = false;
        } else {
          intercept = true;
        }
        LogUtils.e("ACTION_MOVE，intercept = " + intercept);
        LogUtils.e("ACTION_MOVE，gapX = " + Math.abs(gapX));
        LogUtils.e("ACTION_MOVE，gapY = " + Math.abs(gapY));
    }
    mLastX = x;
    mLastY = y;
    return intercept;
  }

  @Override
  public boolean onTouchEvent(MotionEvent ev) {
    LogUtils.e("onTouchEvent action = " + ev.getAction());
    return super.onTouchEvent(ev);
  }
}
