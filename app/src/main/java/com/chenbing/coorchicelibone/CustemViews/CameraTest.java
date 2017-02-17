package com.chenbing.coorchicelibone.CustemViews;

import com.chenbing.coorchicelibone.Utils.BitmapUtils;
import com.chenbing.iceweather.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Project Name:CoorChiceLibOne
 * Author:CoorChice
 * Date:2017/2/14
 * Notes:
 */

public class CameraTest extends View {
  private Paint paint;
  private Bitmap bmp;
  private float desity;

  private float rotateY = 0;
  private float rotateX = 0;
  private float rotateZ = 0;
  private Camera camera;
  private Matrix matrix;
  private float translateZ;

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public CameraTest(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  public CameraTest(Context context) {
    super(context);
    init();

  }

  public CameraTest(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();

  }

  public CameraTest(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();

  }

  private void init() {
    desity = getResources().getDisplayMetrics().density;

    paint = new Paint();
    paint.setAntiAlias(true);
    paint.setDither(true);

    bmp = BitmapFactory.decodeResource(getResources(), R.drawable.test);
    bmp = BitmapUtils.changeBitmapSize(bmp, (int) (300 * desity), (int) (300 * desity));

    camera = new Camera();
    matrix = new Matrix();
  }


  @Override
  protected void onDraw(Canvas canvas) {
    canvas.save();

    camera.save();
    camera.rotateX(rotateX);
    camera.rotateY(rotateY);
    camera.rotateZ(rotateZ);
    camera.translate(0, 0, translateZ);
    camera.getMatrix(matrix);
    camera.restore();

    matrix.preTranslate(-bmp.getWidth() / 2, -bmp.getHeight() / 2);
    matrix.postTranslate(bmp.getWidth() / 2, bmp.getHeight() / 2);
    float[] matrixValue = new float[9];
    matrix.getValues(matrixValue);
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("[(");
    for (int i = 0; i < matrixValue.length; i++) {
      stringBuffer.append(matrixValue[i]);
      if (i != matrixValue.length - 1) {
        if ((i + 1) % 3 == 0) {
          stringBuffer.append("), (");
        } else {
          stringBuffer.append(", ");
        }
      } else {
        stringBuffer.append(")]");
      }
    }
    Log.e("CameraTest", " -> onDraw: " + stringBuffer.toString());

    canvas.concat(matrix);
    canvas.drawBitmap(bmp, 0, 0, paint);
    canvas.restore();

    super.onDraw(canvas);
  }

  public float getRotateY() {
    return rotateY;
  }

  public void setRotateY(float rotateY) {
    this.rotateY = rotateY;
    postInvalidate();
  }

  public float getRotateX() {
    return rotateX;
  }

  public void setRotateX(float rotateX) {
    this.rotateX = rotateX;
    postInvalidate();

  }

  public float getRotateZ() {
    return rotateZ;
  }

  public void setRotateZ(float rotateZ) {
    this.rotateZ = rotateZ;
    postInvalidate();

  }

  public void setTranslateZ(float translateZ) {
    this.translateZ = translateZ;
    postInvalidate();
  }

  public float getTranslateZ(){
    return translateZ;
  }

  public void reset() {
    rotateX = 0;
    rotateY = 0;
    rotateZ = 0;
    postInvalidate();
  }
}
