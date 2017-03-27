package com.chenbing.coorchicelibone.Views;

import com.chenbing.coorchicelibone.CustemViews.RoundCornerTextView;
import com.chenbing.coorchicelibone.Utils.DisplayUtils;
import com.chenbing.iceweather.R;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoundCornerTextViewActivity extends AppCompatActivity {
  @BindView(R.id.rctv_11)
  RoundCornerTextView tv11;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_round_corner_text_view);
    ButterKnife.bind(this);

    tv11.setAdjuster(new RoundCornerTextView.Adjuster() {
      float startLocation = -99999f;
      Paint paint = new Paint();

      @Override
      public void adjust(TextView v, Canvas canvas) {
        int width = v.getWidth();
        int height = v.getHeight();
        float density = DisplayUtils.getDensity();
        if (startLocation == -99999f) {
          startLocation = (float) (Math.random() * width);
        }
        if (startLocation < -(width / 3)) {
          startLocation = width;
        }
        int firstLineWidth = (int) (40 * density / 5 * 3);
        startLocation = ((float) (startLocation - 0.5 * density));
        Path firstLinePath = new Path();
        firstLinePath.moveTo(startLocation, height);
        firstLinePath.lineTo(startLocation + firstLineWidth, height);
        firstLinePath.lineTo((float) (startLocation + firstLineWidth + height * Math.tan(60)), 0);
        firstLinePath.lineTo((float) (startLocation + height * Math.tan(60)), 0);
        firstLinePath.close();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(getResources().getColor(R.color.white));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(firstLinePath, paint);
        int secondLineWidth = (int) (40 * density / 5);
        Path secondLinePath = new Path();
        secondLinePath.moveTo(startLocation + secondLineWidth * 4, height);
        secondLinePath.lineTo(startLocation + secondLineWidth * 4 + secondLineWidth, height);
        secondLinePath.lineTo(
            (float) (startLocation + secondLineWidth * 4 + secondLineWidth + height * Math.tan(60)),
            0);
        secondLinePath.lineTo((float) (startLocation + secondLineWidth * 4 + height * Math.tan(60)),
            0);
        secondLinePath.close();
        canvas.drawPath(secondLinePath, paint);
      }
    });
    tv11.setAutoAdjust(true);
    tv11.startAnim();

  }
}
