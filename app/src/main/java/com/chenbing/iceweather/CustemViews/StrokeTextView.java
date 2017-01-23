package com.chenbing.iceweather.CustemViews;

import java.lang.reflect.Field;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.chenbing.iceweather.R;

public class StrokeTextView extends TextView {

  private TextPaint textPaint;
  private int textColor, strokeColor, startColor, endColor;
  private float strokeWidth = 5f;
  private boolean gradient;
  private LinearGradient linearGradient;
  private boolean isDrawStroke=true;

  public StrokeTextView(Context context) {
    super(context);
    init(context, null, 0);
  }

  public StrokeTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs, 0);
  }

  public StrokeTextView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init(context, attrs, defStyle);
  }

  public StrokeTextView(Context context, int textColor, int strokeColor) {
    super(context);
    this.textPaint = getPaint();
    this.textColor = textColor;
    this.strokeColor = strokeColor;
    linearGradient = new LinearGradient(0, 0, 0, 20, startColor, endColor, Shader.TileMode.CLAMP);
  }

  private void init(Context context, AttributeSet attrs, int defStyle) {
    textPaint = getPaint();
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StrokedTextView,
        defStyle, 0);
    textColor = typedArray.getColor(R.styleable.StrokedTextView_strokeTextColor, 0xFFFFFFFF);
    strokeColor = typedArray.getColor(R.styleable.StrokedTextView_strokeViewColor, 0xFF000000);
    strokeWidth = typedArray.getFloat(R.styleable.StrokedTextView_strokeOutlineWidth, 0.0f);
    gradient = typedArray.getBoolean(R.styleable.StrokedTextView_gradient, false);
    startColor = typedArray.getColor(R.styleable.StrokedTextView_startColor, 0xFFFFFFFF);
    endColor = typedArray.getColor(R.styleable.StrokedTextView_endColor, 0xFFFFFFFF);
    typedArray.recycle();
  }

  public void setCanDrawStroke(boolean drawStroke) {
    isDrawStroke = drawStroke;
  }

  private boolean m_bDrawSideLine = true; // 默认采用描边

  public void setStrokeTextColor(int textColor) {
    this.textColor = textColor;
    invalidate();
  }

  public void setStrokeViewColor(int strokeColor){
    this.strokeColor = strokeColor;
    invalidate();
  }

  public void setStartAndEndColor(int startColor, int endColor) {
    this.startColor = startColor;
    this.endColor = endColor;
    invalidate();
  }

  /**
  *  
  */
  @Override
  protected void onDraw(Canvas canvas) {
    if (isDrawStroke){
      if (m_bDrawSideLine) {
        // 描外层
        textPaint.setShader(null);
        setTextColorUseReflection(strokeColor);
        textPaint.setStrokeWidth(strokeWidth); // 描边宽度
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE); // 描边种类
        textPaint.setFakeBoldText(true); // 外层text采用粗体
        textPaint.setShadowLayer(1, 0, 0, 0); // 字体的阴影效果，可以忽略
        super.onDraw(canvas);
      }
      // 描内层，恢复原先的画笔
      if (gradient) {
        linearGradient =
            new LinearGradient(0, 0, 0, 20, startColor, endColor, Shader.TileMode.CLAMP);
        textPaint.setShader(linearGradient);
      } else {
        setTextColorUseReflection(textColor);
      }
      textPaint.setStrokeWidth(0);
      textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
      textPaint.setFakeBoldText(false);
      textPaint.setShadowLayer(0, 0, 0, 0);
    }
    super.onDraw(canvas);
  }

  /**
   * 使用反射的方法进行字体颜色的设置
   * 
   * @param color
   */
  private void setTextColorUseReflection(int color) {
    Field textColorField;
    try {
      textColorField = TextView.class.getDeclaredField("mCurTextColor");
      textColorField.setAccessible(true);
      textColorField.set(this, color);
      textColorField.setAccessible(false);
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    textPaint.setColor(color);
  }

}
