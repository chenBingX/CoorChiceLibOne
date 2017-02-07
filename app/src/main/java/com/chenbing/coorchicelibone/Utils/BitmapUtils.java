package com.chenbing.coorchicelibone.Utils;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Project Name:IceWeather
 * Author:CoorChice
 * Date:2017/1/7
 * Notes:
 */

public class BitmapUtils {

  private static final int DEFAULT_QUALITY = 80;

  public static Bitmap changeBitmapColor(Bitmap bmp, int color) {
    Bitmap b = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
    Canvas canvas = new Canvas(b);
    Paint paint = new Paint();
    paint.setColor(color);
    paint.setAntiAlias(true);
    canvas.drawBitmap(bmp, bmp.getWidth(), bmp.getHeight(), paint);
    return b;
  }

  public static Bitmap changeBitmapSize(Bitmap source, int width, int height) {
    Bitmap sink = Bitmap.createScaledBitmap(source, width, height, true);
    return sink;
  }


  public static Bitmap scaleBitmap(Bitmap source, int width, int height) {
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    byte[] bitmapDatas = bitmapToBytes(source);
    BitmapFactory.decodeByteArray(bitmapDatas, 0, bitmapDatas.length, options);
    final int sourceWidth = options.outWidth;
    final int sourceHeight = options.outHeight;
    int inSampleSize = 1;
    if (sourceHeight > height || sourceWidth > width) {
      int halfHeight = sourceHeight / 2;
      int halfWidth = sourceWidth / 2;
      while ((halfHeight / inSampleSize) > height && (halfWidth / inSampleSize) > width) {
        inSampleSize *= 2;
      }
    }
    options.inJustDecodeBounds = false;
    Bitmap sink = BitmapFactory.decodeByteArray(bitmapDatas, 0, bitmapDatas.length, options);
    return sink;
  }


  /** 图片转化Bitmap → byte[] */
  public static byte[] bitmapToBytes(Bitmap source) {
    if (source == null) {
      return null;
    } else {
      ByteArrayOutputStream returnByte = new ByteArrayOutputStream();
      source.compress(Bitmap.CompressFormat.JPEG, DEFAULT_QUALITY, returnByte);
      return returnByte.toByteArray();
    }
  }

  /** 图片转化byte[] → Bitmap */
  public static Bitmap bytesToBimap(byte[] byteIn) {
    if (byteIn.length != 0) {
      return BitmapFactory.decodeByteArray(byteIn, 0, byteIn.length);
    } else {
      return null;
    }
  }


}
