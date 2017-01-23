package com.chenbing.iceweather.CustemViews;

import com.chenbing.iceweather.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Project Name:kratos
 * Author:CoorChice
 * Date:2016/12/27
 * Notes:
 */

public class RoundCornerTextView extends TextView {
  private static final float DEFAULT_CORNER = 10f;
  private static final int DEFAULT_SOLID = Color.WHITE;
  private static final float DEFAULT_STROKE_WIDTH = 0f;
  private static final int DEFAULT_STROKE_COLOR = Color.BLACK;
  private float corner;
  private int solid;
  private float strokeWidth;
  private int strokeColor;

  private Paint paint;
  private int width;
  private int height;
  private Drawable drawable;
  private boolean isShowState;
  private float density;
  private boolean autoAdjustSize;
  private TextSizeAdjuster textSizeAdjuster;

  public RoundCornerTextView(Context context) {
    super(context);
    init(null);
  }

  public RoundCornerTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(attrs);
  }

  public RoundCornerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public RoundCornerTextView(Context context, AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(attrs);
  }

  private void init(AttributeSet attrs) {
    density = getContext().getResources().getDisplayMetrics().density;
    initAttrs(attrs);
    paint = new Paint();
    initPaint();
  }

  private void initAttrs(AttributeSet attrs) {
    if (attrs != null) {
      TypedArray typedArray =
          getContext().obtainStyledAttributes(attrs, R.styleable.RoundCornerTextView);
      corner = typedArray.getDimension(R.styleable.RoundCornerTextView_corner, DEFAULT_CORNER);
      solid = typedArray.getColor(R.styleable.RoundCornerTextView_solid, DEFAULT_SOLID);
      strokeWidth = typedArray.getFloat(R.styleable.RoundCornerTextView_stroke_width,
          DEFAULT_STROKE_WIDTH);
      strokeColor =
          typedArray.getColor(R.styleable.RoundCornerTextView_stroke_color, DEFAULT_STROKE_COLOR);
      drawable = typedArray.getDrawable(R.styleable.RoundCornerTextView_state_drawable);
      isShowState = typedArray.getBoolean(R.styleable.RoundCornerTextView_isShowState, false);
      autoAdjustSize = typedArray.getBoolean(R.styleable.RoundCornerTextView_autoAdjustSize, false);
      typedArray.recycle();
    }
  }

  private void initPaint() {
    paint.reset();
    paint.setAntiAlias(true);
    paint.setDither(true);
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    width = getWidth();
    height = getHeight();
    drawStrokeLine(canvas);
    drawSolid(canvas);
    drawStateDrawable(canvas);
    adjustTextSize();
    super.onDraw(canvas);
  }

  private void drawStrokeLine(Canvas canvas) {
    if (strokeWidth > 0) {
      initPaint();
      RectF rectF =
          new RectF(strokeWidth, strokeWidth, width - 2 * strokeWidth, height - 2 * strokeWidth);
      paint.setStyle(Paint.Style.STROKE);
      paint.setColor(strokeColor);
      paint.setStrokeWidth(strokeWidth);
      canvas.drawRoundRect(rectF, corner, corner, paint);
    }
  }

  private void drawSolid(Canvas canvas) {
    initPaint();
    RectF rectF = new RectF(strokeWidth * 1f, strokeWidth * 1f, width - 2 * strokeWidth,
        height - 2 * strokeWidth);
    paint.setStyle(Paint.Style.FILL);
    paint.setColor(solid);
    canvas.drawRoundRect(rectF, corner, corner, paint);
  }

  private void drawStateDrawable(Canvas canvas) {
    if (drawable != null && isShowState) {
      drawable.setBounds(0, 0, width, height);
      drawable.draw(canvas);
    }
  }

  private void adjustTextSize() {
    if (autoAdjustSize) {
      if (textSizeAdjuster == null) {
        adjustTextSizeByDefault();
      } else {
        textSizeAdjuster.adjustTextSize(this);
      }
    }
  }

  /**
   * 根据产品需求确定的值
   */
  private void adjustTextSizeByDefault() {
    int length = length();
    float scale = width / (116.28f * density);
    float[] textSizes = {
        37.21f, 37.21f, 24.81f, 27.9f, 24.81f,
        22.36f, 18.6f,
        18.6f
    };
    switch (length) {
      case 1:
        setTextSize(textSizes[0] * scale);
        break;
      case 2:
        setTextSize(textSizes[1] * scale);
        break;
      case 3:
        setTextSize(textSizes[2] * scale);
        break;
      case 4:
        setTextSize(textSizes[3] * scale);
        break;
      case 5:
      case 6:
        setTextSize(textSizes[4] * scale);
        break;
      case 7:
      case 8:
      case 9:
        setTextSize(textSizes[5] * scale);
        break;
      case 10:
      case 11:
      case 12:
        setTextSize(textSizes[6] * scale);
        break;
      case 13:
      case 14:
      case 15:
      case 16:
        setTextSize(textSizes[7] * scale);
        break;
    }
  }

  public float getCorner() {
    return corner;
  }

  public void setCorner(float corner) {
    this.corner = corner;
    postInvalidate();

  }

  public int getSolid() {
    return solid;
  }

  public void setSolid(int solid) {
    this.solid = solid;
    postInvalidate();
  }

  public float getStrokeWidth() {
    return strokeWidth;
  }

  public void setStrokeWidth(float strokeWidth) {
    this.strokeWidth = strokeWidth;
    postInvalidate();
  }

  public int getStrokeColor() {
    return strokeColor;
  }

  public void setStrokeColor(int strokeColor) {
    this.strokeColor = strokeColor;
    postInvalidate();
  }

  public Drawable getDrawable() {
    return drawable;
  }

  public void setDrawable(Drawable drawable) {
    this.drawable = drawable;
    postInvalidate();
  }

  public boolean isShowState() {
    return isShowState;
  }

  public void setShowState(boolean showState) {
    isShowState = showState;
    postInvalidate();
  }

  public void setTextSizeAdjuster(TextSizeAdjuster textSizeAdjuster) {
    this.textSizeAdjuster = textSizeAdjuster;
  }

  public interface TextSizeAdjuster {
    void adjustTextSize(TextView v);
  }
}
