package com.chenbing.coorchicelibone.CustemViews;

import com.chenbing.coorchicelibone.Utils.LogUtils;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Project Name:CoorChiceLibOne
 * Author:CoorChice
 * Date:2017/4/20
 * Notes:
 */

public class TouchEventTextView extends TextView {


  public TouchEventTextView(Context context) {
    super(context);
  }

  public TouchEventTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public TouchEventTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public TouchEventTextView(Context context, AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    setText("onTouchEvent");
    LogUtils.e("onTouchEvent");

    return true;
  }
}
