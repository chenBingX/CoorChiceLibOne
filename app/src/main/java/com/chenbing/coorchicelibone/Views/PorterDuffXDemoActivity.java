package com.chenbing.coorchicelibone.Views;

import com.chenbing.coorchicelibone.Utils.DisplayUtils;
import com.chenbing.coorchicelibone.Views.BaseView.BaseActivity;
import com.chenbing.iceweather.R;
import com.coorchice.library.SuperTextView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PorterDuffXDemoActivity extends BaseActivity {

  private static final int TYPE_BITMAP = 1;
  private static final int TYPE_NATIVE = 2;
  private final static PorterDuffXfermode[][] xfermodes = {
      {getXfermode(PorterDuff.Mode.CLEAR), getXfermode(PorterDuff.Mode.SRC),
          getXfermode(PorterDuff.Mode.DST), getXfermode(PorterDuff.Mode.SRC_OVER)},
      {getXfermode(PorterDuff.Mode.DST_OVER), getXfermode(PorterDuff.Mode.SRC_IN),
          getXfermode(PorterDuff.Mode.DST_IN), getXfermode(PorterDuff.Mode.SRC_OUT)},
      {getXfermode(PorterDuff.Mode.DST_OUT), getXfermode(PorterDuff.Mode.SRC_ATOP),
          getXfermode(PorterDuff.Mode.DST_ATOP), getXfermode(PorterDuff.Mode.XOR)},
      {getXfermode(PorterDuff.Mode.DARKEN), getXfermode(PorterDuff.Mode.LIGHTEN),
          getXfermode(PorterDuff.Mode.MULTIPLY), getXfermode(PorterDuff.Mode.SCREEN)}};
  private final static String[][] xfermodeTitles = {
      {"CLEAR", "SRC", "DST", "SRC_OVER"},
      {"DST_OVER", "SRC_IN", "DST_IN", "SRC_OUT"},
      {"DST_OUT", "SRC_ATOP", "DST_ATOP", "XOR"},
      {"DARKEN", "LIGHTEN", "MULTIPLY", "SCREEN"}
  };

  @BindView(R.id.tv_bitmap)
  SuperTextView tvBitmap;
  @BindView(R.id.tv_native)
  SuperTextView tvNative;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_porter_duff_xdemo);
    ButterKnife.bind(this);
    initData();
    initView();
    addListener();
  }

  @Override
  protected void initData() {


  }

  @Override
  protected void initView() {
    ViewGroup.LayoutParams lpBitmap = tvBitmap.getLayoutParams();
    lpBitmap.height = DisplayUtils.getScreenWidth();
    ViewGroup.LayoutParams lpNative = tvNative.getLayoutParams();
    lpNative.height = DisplayUtils.getScreenWidth();

    tvBitmap.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    tvBitmap.addAdjuster(new PorterDuffAdjuster(TYPE_BITMAP));
    tvBitmap.setAutoAdjust(true);

    tvNative.addAdjuster(new PorterDuffAdjuster(TYPE_NATIVE));
    tvNative.setAutoAdjust(true);
  }

  @Override
  protected void addListener() {

  }

  private static class PorterDuffAdjuster extends SuperTextView.Adjuster {
    private Bitmap dst, src;
    private Paint paint;
    private float padding = DisplayUtils.dipToPx(10);
    private int width, height;
    private float bitmapSize;
    private int type;


    public PorterDuffAdjuster(int type) {
      this.type = type;
      initPaint();
    }

    private void initPaint() {
      paint = new Paint();
      paint.setAntiAlias(true);
      paint.setFilterBitmap(true);
    }

    @Override
    protected void adjust(SuperTextView v, Canvas canvas) {
      if (dst == null) {
        init(v);
      }
      for (int i = 0; i < 4; i++) {
        int y = (int) (bitmapSize + padding) * i + DisplayUtils.dipToPx(10);
        for (int j = 0; j < 4; j++) {
          String text = xfermodeTitles[i][j];
          int x = (int) (bitmapSize + padding) * j + DisplayUtils.dipToPx(10);
          drawTitle(canvas, x, y, text);
          if (type == TYPE_BITMAP) {
            drawWithBitmap(canvas, x, y, xfermodes[i][j]);
          } else if (type == TYPE_NATIVE) {
            drawWithNative(canvas, x, y, xfermodes[i][j]);
          }
        }
      }
    }

    private void init(SuperTextView v) {
      width = v.getWidth() - DisplayUtils.dipToPx(10) * 2;
      height = v.getHeight();
      bitmapSize = (width - padding * 3) / 4;
      // int shapeWidth = (int) bitmapSize;
      int shapeWidth = (int) (bitmapSize / 3 * 2);
      int shapeHeight = shapeWidth;
      RectF dstRectF = new RectF(0, 0, shapeWidth, shapeHeight);
      dst = Bitmap.createBitmap((int) bitmapSize, (int) bitmapSize, Bitmap.Config.ARGB_8888);
      // dst = Bitmap.createBitmap(shapeWidth, shapeHeight, Bitmap.Config.ARGB_8888);
      Canvas dstCanvas = new Canvas(dst);
      paint.setColor(Color.parseColor("#F2BB36"));
      dstCanvas.drawRoundRect(dstRectF, shapeWidth / 2, shapeHeight / 2, paint);

      RectF srcRectF = new RectF(shapeWidth / 2, shapeHeight / 2, bitmapSize, bitmapSize);
      // src = Bitmap.createBitmap(shapeWidth, shapeHeight, Bitmap.Config.ARGB_8888);
      src = Bitmap.createBitmap((int) bitmapSize, (int) bitmapSize, Bitmap.Config.ARGB_8888);
      Canvas srcCanvas = new Canvas(src);
      paint.setColor(Color.parseColor("#5291F4"));
      srcCanvas.drawRect(srcRectF, paint);
    }

    private void drawTitle(Canvas canvas, int x, int y, String text) {
      float textSize = DisplayUtils.dipToPx(10) / DisplayUtils.getScaledDensity();
      textSize = DisplayUtils.spToPx((int) textSize);
      paint.setTextSize(textSize);
      Rect textRect = new Rect();
      paint.getTextBounds(text, 0, text.length(), textRect);
      float textWidth = textRect.width();
      int textX = (int) (x + (bitmapSize - textWidth) / 2);
      Paint.FontMetrics fontMetrics = paint.getFontMetrics();
      float textHeight = fontMetrics.bottom - fontMetrics.top;
      int textY = (int) (y + (DisplayUtils.dipToPx(10) - textHeight) / 2);
      paint.setColor(Color.BLACK);
      canvas.drawText(text, textX, textY, paint);
    }

    private void drawWithBitmap(Canvas canvas, int x, int y, PorterDuffXfermode xfermode) {
      paint.setColor(Color.BLACK);
      paint.setStyle(Paint.Style.STROKE);
      paint.setStrokeWidth(DisplayUtils.dipToPx(1));
      canvas.drawRect(x, y, x + bitmapSize, y + bitmapSize, paint);

      paint.setStyle(Paint.Style.FILL);
      int layer = canvas.saveLayer(x, y, x + bitmapSize, y + bitmapSize, null,
          Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
              | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG);

      canvas.drawBitmap(dst, x, y, paint);
      paint.setXfermode(xfermode);

      // canvas.drawBitmap(src, x + dst.getWidth() / 2, y + dst.getHeight() / 2, paint);
      canvas.drawBitmap(src, x, y, paint);
      paint.setXfermode(null);

      canvas.restoreToCount(layer);
    }

    private void drawWithNative(Canvas canvas, int x, int y, PorterDuffXfermode xfermode) {
      paint.setColor(Color.BLACK);
      paint.setStyle(Paint.Style.STROKE);
      paint.setStrokeWidth(DisplayUtils.dipToPx(1));
      canvas.drawRect(x, y, x + bitmapSize, y + bitmapSize, paint);

      paint.setStyle(Paint.Style.FILL);
      int layer = canvas.saveLayer(x, y, x + bitmapSize, y + bitmapSize, null,
          Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
              | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
      paint.setColor(Color.parseColor("#F2BB36"));
      canvas.drawCircle(x + bitmapSize / 3, y + bitmapSize / 3, bitmapSize / 3, paint);
      paint.setXfermode(xfermode);

      paint.setColor(Color.parseColor("#5291F4"));
      canvas.drawRect(x + bitmapSize / 3, y + bitmapSize / 3,
          x + bitmapSize / 3 + bitmapSize / 3 * 2, y + bitmapSize / 3 + bitmapSize / 3 * 2, paint);
      paint.setXfermode(null);

      canvas.restoreToCount(layer);
    }
  }

  public static PorterDuffXfermode getXfermode(PorterDuff.Mode mode) {
    return new PorterDuffXfermode(mode);
  }

}
