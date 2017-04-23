package com.chenbing.coorchicelibone.Views;

import com.chenbing.coorchicelibone.Utils.DisplayUtils;
import com.chenbing.iceweather.R;
import com.coorchice.library.SuperTextView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;



public class RoundCornerTextViewActivity extends AppCompatActivity {
  @BindView(R.id.rctv_11)
  SuperTextView tv11;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_round_corner_text_view);
    ButterKnife.bind(this);

    tv11.setAdjuster(new SuperTextView.Adjuster() {
      private PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
      private float totalWidth = 25f;
      private float startLocation = -99999f;
      private Paint paint = new Paint();
      private Path firstLinePath = new Path();
      private Path secondLinePath = new Path();
      private RectF rectF = new RectF();
      float density = DisplayUtils.getDensity();

      @Override
      public void adjust(SuperTextView v, Canvas canvas) {
        int width = v.getWidth();
        int height = v.getHeight();
        if (startLocation == -99999f) {
          startLocation = (float) (Math.random() * width);
        }
        if (startLocation < -(totalWidth * density + height * Math.tan(60))) {
          startLocation = width;
        }
        int firstLineWidth = (int) (totalWidth * density / 5 * 3);
        startLocation = ((float) (startLocation - 0.5 * density));
        firstLinePath.reset();
        firstLinePath.moveTo(startLocation, height);
        firstLinePath.lineTo(startLocation + firstLineWidth, height);
        firstLinePath.lineTo((float) (startLocation + firstLineWidth + height * Math.tan(60)), 0);
        firstLinePath.lineTo((float) (startLocation + height * Math.tan(60)), 0);
        firstLinePath.close();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(getResources().getColor(R.color.black));
        paint.setStyle(Paint.Style.FILL);
        int secondLineWidth = (int) (totalWidth * density / 5);
        secondLinePath.reset();
        secondLinePath.moveTo(startLocation + secondLineWidth * 4, height);
        secondLinePath.lineTo(startLocation + secondLineWidth * 4 + secondLineWidth, height);
        secondLinePath.lineTo(
            (float) (startLocation + secondLineWidth * 4 + secondLineWidth + height * Math.tan(60)),
            0);
        secondLinePath.lineTo((float) (startLocation + secondLineWidth * 4 + height * Math.tan(60)),
            0);
        secondLinePath.close();

        firstLinePath.addPath(secondLinePath);
        rectF.setEmpty();
        rectF.set(0, 0, width, height);
        // 创建一个图层，在图层上演示图形混合后的效果
        int sc = canvas.saveLayer(0, 0, width, height, null, Canvas.MATRIX_SAVE_FLAG |
            Canvas.CLIP_SAVE_FLAG |
            Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
            Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
            Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        paint.setColor(getResources().getColor(R.color.purple));
        canvas.drawRoundRect(rectF, height / 2, height / 2, paint);
        paint.setXfermode(xfermode);
        paint.setColor(getResources().getColor(R.color.opacity_3_white));
        canvas.drawPath(firstLinePath, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(sc);
      }
    });
    tv11.setAutoAdjust(true);
    tv11.startAnim();
  }
}
