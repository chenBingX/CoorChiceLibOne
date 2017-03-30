package com.chenbing.coorchicelibone.CustemViews;

import com.chenbing.iceweather.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Project Name:
 * Author:CoorChice
 * Date:2016/12/27
 * Notes:
 * V1.1.0 - 改变背景和边框的绘制方式，能够更佳精确的绘制。
 *        - 状态图支持设置位置。
 * V1.2.0 - 更改TextAdjuster为Adjuster，使它在绘制文字前夕的调整功能更加广泛。
 *        - 通过Adjuster可以便捷的实现动效。fps为16。
 * V1.2.1 - 优化动画执行效率.
 * V1.2.2 - 状态图支持设置大小和位置。
 */

public class RoundCornerTextView extends TextView {
  private static final float DEFAULT_CORNER = 0f;
  private static final int DEFAULT_SOLID = Color.WHITE;
  private static final float DEFAULT_STROKE_WIDTH = 0f;
  private static final int DEFAULT_STROKE_COLOR = Color.BLACK;
  private static final int DEFAULT_STATE_DRAWABLE_MODE = 4;
  private static final int DEFAULT_TEXT_STROKE_COLOR = Color.BLACK;
  private static final int DEFAULT_TEXT_FILL_COLOR = Color.BLACK;
  private static final float DEFAULT_TEXT_STROKE_WIDTH = 0f;

  private float corner;
  private boolean leftTopCornerEnable;
  private boolean rightTopCornerEnable;
  private boolean leftBottomCornerEnable;
  private boolean rightBottomCornerEnable;
  private int solid;
  private float strokeWidth;
  private int strokeColor;
  private int stateDrawableMode;
  private boolean isShowState;

  private Paint paint;
  private int width;
  private int height;
  private Drawable drawable;
  private float density;
  private boolean autoAdjust;
  private Adjuster adjuster;
  private boolean textStroke;
  private int textStrokeColor;
  private int textFillColor;
  private float textStrokeWidth;
  private boolean runnable = false;
  private boolean needRun = false;
  private Thread animThread;
  private Path strokeWidthPath;
  private Path solidPath;
  private RectF strokeLineRectF;
  private RectF solidRectF;
  private float leftTopCorner[] = new float[2];
  private float rightTopCorner[] = new float[2];
  private float leftBottomCorner[] = new float[2];
  private float rightBottomCorner[] = new float[2];
  private float corners[] = new float[8];
  private float[] drawableBounds = new float[4];
  private float drawableWidth;
  private float drawableHeight;
  private float drawablePaddingLeft;
  private float drawablePaddingTop;


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
      leftTopCornerEnable =
          typedArray.getBoolean(R.styleable.RoundCornerTextView_left_top_corner, false);
      rightTopCornerEnable =
          typedArray.getBoolean(R.styleable.RoundCornerTextView_right_top_corner, false);
      leftBottomCornerEnable =
          typedArray.getBoolean(R.styleable.RoundCornerTextView_left_bottom_corner, false);
      rightBottomCornerEnable =
          typedArray.getBoolean(R.styleable.RoundCornerTextView_right_bottom_corner, false);
      solid = typedArray.getColor(R.styleable.RoundCornerTextView_solid, DEFAULT_SOLID);
      strokeWidth = typedArray.getDimension(R.styleable.RoundCornerTextView_stroke_width,
          DEFAULT_STROKE_WIDTH);
      strokeColor =
          typedArray.getColor(R.styleable.RoundCornerTextView_stroke_color, DEFAULT_STROKE_COLOR);
      drawable = typedArray.getDrawable(R.styleable.RoundCornerTextView_state_drawable);
      drawableWidth =
          typedArray.getDimension(R.styleable.RoundCornerTextView_state_drawable_width, 0);
      drawableHeight =
          typedArray.getDimension(R.styleable.RoundCornerTextView_state_drawable_height, 0);
      drawablePaddingLeft =
          typedArray.getDimension(R.styleable.RoundCornerTextView_state_drawable_padding_left, 0);
      drawablePaddingTop =
          typedArray.getDimension(R.styleable.RoundCornerTextView_state_drawable_padding_top, 0);
      isShowState = typedArray.getBoolean(R.styleable.RoundCornerTextView_isShowState, false);
      stateDrawableMode = typedArray.getInteger(R.styleable.RoundCornerTextView_state_drawable_mode,
          DEFAULT_STATE_DRAWABLE_MODE);
      textStroke = typedArray.getBoolean(R.styleable.RoundCornerTextView_text_stroke, false);
      textStrokeColor = typedArray.getColor(R.styleable.RoundCornerTextView_text_stroke_color,
          DEFAULT_TEXT_STROKE_COLOR);
      textFillColor = typedArray.getColor(R.styleable.RoundCornerTextView_text_fill_color,
          DEFAULT_TEXT_FILL_COLOR);
      textStrokeWidth = typedArray.getDimension(R.styleable.RoundCornerTextView_text_stroke_width,
          DEFAULT_TEXT_STROKE_WIDTH);
      autoAdjust = typedArray.getBoolean(R.styleable.RoundCornerTextView_autoAdjust, false);
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
    adjust(canvas);
    if (textStroke){
      getPaint().setStyle(Paint.Style.STROKE);
      setTextColor(textStrokeColor);
      getPaint().setFakeBoldText(true);
      getPaint().setStrokeWidth(textStrokeWidth);
      super.onDraw(canvas);
      getPaint().setStyle(Paint.Style.FILL);
      getPaint().setFakeBoldText(false);
      setTextColor(textFillColor);
    }
    super.onDraw(canvas);
  }

  private void drawStrokeLine(Canvas canvas) {
    if (strokeWidth > 0) {
      if (strokeWidthPath == null) {
        strokeWidthPath = new Path();
      } else {
        strokeWidthPath.reset();
      }
      if (strokeLineRectF == null) {
        strokeLineRectF = new RectF();
      } else {
        strokeLineRectF.setEmpty();
      }
      strokeLineRectF.set(strokeWidth / 2, strokeWidth / 2, width - strokeWidth / 2,
          height - strokeWidth / 2);
      getCorners(corner);
      strokeWidthPath.addRoundRect(strokeLineRectF, corners, Path.Direction.CW);
      initPaint();
      paint.setStyle(Paint.Style.STROKE);
      paint.setColor(strokeColor);
      paint.setStrokeWidth(strokeWidth);
      canvas.drawPath(strokeWidthPath, paint);
    }
  }

  private void drawSolid(Canvas canvas) {
    if (solidPath == null) {
      solidPath = new Path();
    } else {
      solidPath.reset();
    }
    if (solidRectF == null) {
      solidRectF = new RectF();
    } else {
      solidRectF.setEmpty();
    }
    solidRectF.set(strokeWidth, strokeWidth, width - strokeWidth, height - strokeWidth);
    getCorners(corner - strokeWidth / 2);
    solidPath.addRoundRect(solidRectF, corners, Path.Direction.CW);

    initPaint();
    paint.setStyle(Paint.Style.FILL);
    paint.setColor(solid);
    canvas.drawPath(solidPath, paint);
  }

  private float[] getCorners(float corner) {
    leftTopCorner[0] = 0;
    leftTopCorner[1] = 0;
    rightTopCorner[0] = 0;
    rightTopCorner[1] = 0;
    leftBottomCorner[0] = 0;
    leftBottomCorner[1] = 0;
    rightBottomCorner[0] = 0;
    rightBottomCorner[1] = 0;
    if (this.leftTopCornerEnable || this.rightTopCornerEnable || this.leftBottomCornerEnable
        || this.rightBottomCornerEnable) {
      if (this.leftTopCornerEnable) {
        leftTopCorner[0] = corner;
        leftTopCorner[1] = corner;
      }
      if (this.rightTopCornerEnable) {
        rightTopCorner[0] = corner;
        rightTopCorner[1] = corner;
      }
      if (this.leftBottomCornerEnable) {
        leftBottomCorner[0] = corner;
        leftBottomCorner[1] = corner;
      }
      if (this.rightBottomCornerEnable) {
        rightBottomCorner[0] = corner;
        rightBottomCorner[1] = corner;
      }
    } else {
      leftTopCorner[0] = corner;
      leftTopCorner[1] = corner;
      rightTopCorner[0] = corner;
      rightTopCorner[1] = corner;
      leftBottomCorner[0] = corner;
      leftBottomCorner[1] = corner;
      rightBottomCorner[0] = corner;
      rightBottomCorner[1] = corner;
    }
    corners[0] = leftTopCorner[0];
    corners[1] = leftTopCorner[1];
    corners[2] = rightTopCorner[0];
    corners[3] = rightTopCorner[1];
    corners[4] = rightBottomCorner[0];
    corners[5] = rightBottomCorner[1];
    corners[6] = leftBottomCorner[0];
    corners[7] = leftBottomCorner[1];
    return corners;
  }

  private void drawStateDrawable(Canvas canvas) {
    if (drawable != null && isShowState) {
      getDrawableBounds();
      drawable.setBounds((int) drawableBounds[0], (int) drawableBounds[1], (int) drawableBounds[2],
          (int) drawableBounds[3]);
      drawable.draw(canvas);
    }
  }

  private float[] getDrawableBounds() {
    for (int i = 0; i < drawableBounds.length; i++) {
      drawableBounds[i] = 0;
    }
    switch (stateDrawableMode) {
      case 0: // left
        drawableBounds[0] = 0;
        drawableBounds[1] = height * (1f / 4f);
        drawableBounds[2] = width * (1f / 2f);
        drawableBounds[3] = height * (3f / 4f);
        break;
      case 1: // top
        drawableBounds[0] = width * (1f / 4f);
        drawableBounds[1] = 0;
        drawableBounds[2] = width * (3f / 4f);
        drawableBounds[3] = height * (1f / 2f);
        break;
      case 2: // right
        drawableBounds[0] = width * (1f / 2f);
        drawableBounds[1] = height * (1f / 4f);
        drawableBounds[2] = width;
        drawableBounds[3] = height * (3f / 4f);
        break;
      case 3: // bottom
        drawableBounds[0] = width * (1f / 4f);
        drawableBounds[1] = height * (1f / 2f);
        drawableBounds[2] = width * (3f / 4f);
        drawableBounds[3] = height;
        break;
      case 4: // center
        drawableBounds[0] = width * (1f / 4f);
        drawableBounds[1] = height * (1f / 4f);
        drawableBounds[2] = width * (3f / 4f);
        drawableBounds[3] = height * (3f / 4f);
        break;
      case 5: // fill
        drawableBounds[0] = 0;
        drawableBounds[1] = 0;
        drawableBounds[2] = width;
        drawableBounds[3] = height;
        break;
      case 6: // leftTop
        drawableBounds[0] = 0;
        drawableBounds[1] = 0;
        drawableBounds[2] = width * (1f / 2f);
        drawableBounds[3] = height * (1f / 2f);
        break;
      case 7: // rightTop
        drawableBounds[0] = width * (1f / 2f);
        drawableBounds[1] = 0;
        drawableBounds[2] = width;
        drawableBounds[3] = height * (1f / 2f);
        break;
      case 8: // leftBottom
        drawableBounds[0] = 0;
        drawableBounds[1] = height * (1f / 2f);
        drawableBounds[2] = width * (1f / 2f);
        drawableBounds[3] = height;
        break;
      case 9: // rightBottom
        drawableBounds[0] = width * (1f / 2f);
        drawableBounds[1] = height * (1f / 2f);
        drawableBounds[2] = width;
        drawableBounds[3] = height;
        break;
    }

    return drawableBounds;
  }

  private void adjust(Canvas canvas) {
    if (autoAdjust) {
      if (adjuster == null) {
        adjuster = new DefaultAdjuster();
      }
      adjuster.adjust(this, canvas);
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

  public int getStateDrawableMode() {
    return stateDrawableMode;
  }

  public void setStateDrawableMode(int stateDrawableMode) {
    this.stateDrawableMode = stateDrawableMode;
    postInvalidate();
  }

  public void setAdjuster(Adjuster adjuster) {
    this.adjuster = adjuster;
    postInvalidate();
  }

  public Adjuster getAdjuster() {
    return adjuster;
  }

  public boolean isTextStroke() {
    return textStroke;
  }

  public void setTextStroke(boolean textStroke) {
    this.textStroke = textStroke;
    postInvalidate();

  }

  public int getTextStrokeColor() {
    return textStrokeColor;
  }

  public void setTextStrokeColor(int textStrokeColor) {
    this.textStrokeColor = textStrokeColor;
    postInvalidate();

  }

  public int getTextFillColor() {
    return textFillColor;
  }

  public void setTextFillColor(int textFillColor) {
    this.textFillColor = textFillColor;
    postInvalidate();

  }

  public float getTextStrokeWidth() {
    return textStrokeWidth;
  }

  public void setTextStrokeWidth(float textStrokeWidth) {
    this.textStrokeWidth = textStrokeWidth;
    postInvalidate();

  }

  public boolean isAutoAdjust() {
    return autoAdjust;
  }

  public void setAutoAdjust(boolean autoAdjust) {
    this.autoAdjust = autoAdjust;
  }

  public boolean isLeftTopCornerEnable() {

    return leftTopCornerEnable;
  }

  public void setLeftTopCornerEnable(boolean leftTopCornerEnable) {
    this.leftTopCornerEnable = leftTopCornerEnable;
  }

  public boolean isRightTopCornerEnable() {
    return rightTopCornerEnable;
  }

  public void setRightTopCornerEnable(boolean rightTopCornerEnable) {
    this.rightTopCornerEnable = rightTopCornerEnable;
  }

  public boolean isLeftBottomCornerEnable() {
    return leftBottomCornerEnable;
  }

  public void setLeftBottomCornerEnable(boolean leftBottomCornerEnable) {
    this.leftBottomCornerEnable = leftBottomCornerEnable;
  }

  public boolean isRightBottomCornerEnable() {
    return rightBottomCornerEnable;
  }

  public void setRightBottomCornerEnable(boolean rightBottomCornerEnable) {
    this.rightBottomCornerEnable = rightBottomCornerEnable;
  }


  public void startAnim() {
    needRun = true;
    runnable = false;
    if (animThread == null) {
      runnable = true;
      needRun = true;
      animThread = new Thread(() -> {
        while (runnable) {
          post(this::postInvalidate);
          try {
            Thread.sleep(16);
          } catch (InterruptedException e) {
            e.printStackTrace();
            runnable = false;
          }
        }
        animThread = null;
        if (needRun) {
          startAnim();
        }
      });
      animThread.start();
    }
  }

  public void stopAnim() {
    runnable = false;
    needRun = false;
  }

  @Override
  protected void finalize() throws Throwable {
    runnable = false;
    super.finalize();
  }

  public static interface Adjuster {
    void adjust(TextView v, Canvas canvas);
  }

  public static class DefaultAdjuster implements Adjuster {

    @Override
    public void adjust(TextView v, Canvas canvas) {
      int length = v.length();
      float scale = v.getWidth() / (116.28f * v.getResources().getDisplayMetrics().density);
      float[] textSizes = {
        37.21f, 37.21f, 24.81f, 27.9f, 24.81f,
        22.36f, 18.6f,
        18.6f
      };
      switch (length) {
        case 1:
          v.setTextSize(textSizes[0] * scale);
          break;
        case 2:
          v.setTextSize(textSizes[1] * scale);
          break;
        case 3:
          v.setTextSize(textSizes[2] * scale);
          break;
        case 4:
          v.setTextSize(textSizes[3] * scale);
          break;
        case 5:
        case 6:
          v.setTextSize(textSizes[4] * scale);
          break;
        case 7:
        case 8:
        case 9:
          v.setTextSize(textSizes[5] * scale);
          break;
        case 10:
        case 11:
        case 12:
          v.setTextSize(textSizes[6] * scale);
          break;
        case 13:
        case 14:
        case 15:
        case 16:
          v.setTextSize(textSizes[7] * scale);
          break;
      }
    }

  }
}
