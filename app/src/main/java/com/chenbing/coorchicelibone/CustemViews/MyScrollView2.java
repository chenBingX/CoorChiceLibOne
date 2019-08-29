package com.chenbing.coorchicelibone.CustemViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

/**
 * Project Name:CoorChiceLibOne
 * Author:CoorChice
 * Date:2019/8/18
 * Notes:
 */
public class MyScrollView2 extends HorizontalScrollView {

  public MyScrollView2(Context context) {
    super(context);
  }

  public MyScrollView2(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public MyScrollView2(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }


  private int  mLastX;
  private int  mLastY;

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
//    int x = (int) ev.getX();
//    int y = (int) ev.getY();
//    int action = ev.getAction();
//    boolean intercept = false;
//    switch (action) {
//      case MotionEvent.ACTION_DOWN:
//        break;
//      case MotionEvent.ACTION_MOVE:
//        int gapX = x - mLastX;
//        int gapY = y - mLastY;
//        if (Math.abs(gapX) > Math.abs(gapY)) {
//          intercept = true;
//        } else {
//          intercept = false;
//        }
//    }
//    mLastX = x;
//    mLastY = y;
    getParent().requestDisallowInterceptTouchEvent(false);
    return true;
  }

  @Override
  public boolean onTouchEvent(MotionEvent ev) {
    super.onTouchEvent(ev);
    getParent().requestDisallowInterceptTouchEvent(false);
    return true;
  }
}
