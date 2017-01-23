package com.chenbing.iceweather.CustemViews;

import com.chenbing.iceweather.Utils.LogUtils;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Project Name:IceWeather
 * Author:CoorChice
 * Date:2017/1/7
 * Notes:
 */

public class CustomTextView extends TextView {

  private TextPaint paint;

  public CustomTextView(Context context) {
    super(context);
    init(null);

  }

  public CustomTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(attrs);

  }

  public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs);

  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(attrs);

  }

  private void init(AttributeSet attrs) {
    paint = getPaint();


  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    float ascent = paint.getFontMetrics().ascent;
    float descent = paint.getFontMetrics().descent;
    float bottom = paint.getFontMetrics().bottom;
    float top = paint.getFontMetrics().top;
    float leading = paint.getFontMetrics().leading;  //行间距
    LogUtils.e(String.format("ascent = %f,descent = %f,bottom = %f,top = %f, leading = %f", ascent,
        descent, bottom, top, leading));



  }
}
