package com.chenbing.iceweather.Helpers;

import com.chenbing.iceweather.IceApplication;
import com.chenbing.iceweather.R;
import com.chenbing.iceweather.Utils.DisplayUtils;
import com.chenbing.iceweather.Utils.LogUtils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/10/9
 * Notes:
 */
public class DrawTextBmpHelper {
  private static final int DEFAULT_TEXT_SIZE = DisplayUtils.spToPx(10);

  private Paint paint;
  private Paint helpPaint;

  public DrawTextBmpHelper() {
    this.paint = new Paint();
    this.helpPaint = new Paint();
    initPaint();
  }

  private void initPaint() {
    paint.setAntiAlias(true);
    paint.setDither(true);
    paint.setFilterBitmap(true);
    paint.setSubpixelText(true);
    paint.setTextSize(DEFAULT_TEXT_SIZE);

    helpPaint.setAntiAlias(true);
    helpPaint.setDither(true);
    helpPaint.setFilterBitmap(true);
  }

  public Bitmap drawBitmap(String drawText) {
    Bitmap bmp = null;
    bmp = Bitmap.createBitmap(DisplayUtils.dipToPx(300), DisplayUtils.dipToPx(300),
        Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bmp);
    checkPaintValidate();
    float startX = bmp.getWidth() / 2 - paint.measureText(drawText) / 2;
    float startY = bmp.getHeight() / 2;
    int backgroundColor = IceApplication.getResColor(R.color.opacity_5_white);
    canvas.drawColor(backgroundColor);
    canvas.drawText(drawText, startX, startY, paint);

    helpPaint.setColor(IceApplication.getResColor(R.color.opacity_5_red));
    canvas.drawCircle(startX, startY, 2, helpPaint);


    Paint.FontMetrics fontMetrics = paint.getFontMetrics();
    float ascent = fontMetrics.ascent;
    float top = fontMetrics.top;
    float descent = fontMetrics.descent;
    float bottom = fontMetrics.bottom;
    // ascent
    helpPaint.setColor(Color.RED);
    canvas.drawLine(0, startY + ascent, bmp.getWidth(), startY + ascent, helpPaint);

    // top
    helpPaint.setColor(Color.YELLOW);
    canvas.drawLine(0, startY + top, bmp.getWidth(), startY + top, helpPaint);

    // descent
    helpPaint.setColor(Color.BLACK);
    canvas.drawLine(0, startY + descent, bmp.getWidth(), startY + descent, helpPaint);

    // bottom
    helpPaint.setColor(Color.BLUE);
    canvas.drawLine(0, startY + bottom, bmp.getWidth(), startY + bottom, helpPaint);

    // baseline
    helpPaint.setColor(IceApplication.getResColor(R.color.pink));
    canvas.drawLine(0, startY, bmp.getWidth(), startY, helpPaint);
    return bmp;
  }

  private void checkPaintValidate() {
    if (paint == null) {
      resetPaint();
      LogUtils.e("Paint为空，已重新创建");
      return;
    }

    if (paint.getTextSize() <= 0) {
      paint.setTextSize(DEFAULT_TEXT_SIZE);
    }
  }

  public Paint getPaint() {
    return paint;
  }

  public Paint resetPaint() {
    if (paint == null) {
      paint = new Paint();
    }
    initPaint();
    return paint;
  }
}
