package com.chenbing.coorchicelibone.Utils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
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


    /**
     * 图片转化Bitmap → byte[]
     */
    public static byte[] bitmapToBytes(Bitmap source) {
        if (source == null) {
            return null;
        } else {
            ByteArrayOutputStream returnByte = new ByteArrayOutputStream();
            source.compress(Bitmap.CompressFormat.JPEG, DEFAULT_QUALITY, returnByte);
            return returnByte.toByteArray();
        }
    }

    /**
     * 图片转化byte[] → Bitmap
     */
    public static Bitmap bytesToBimap(byte[] byteIn) {
        if (byteIn.length != 0) {
            return BitmapFactory.decodeByteArray(byteIn, 0, byteIn.length);
        } else {
            return null;
        }
    }

    public static Bitmap bitmap2Gray(Bitmap bmSrc) {
        // 得到图片的长和宽
        int width = bmSrc.getWidth();
        int height = bmSrc.getHeight();
        // 创建目标灰度图像
        Bitmap bmpGray = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // 创建画布
        Canvas c = new Canvas(bmpGray);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmSrc, 0, 0, paint);
        return bmpGray;
    }

    // 该函数实现对图像进行二值化处理
    public static Bitmap bitmap2Gray2(Bitmap graymap) {
        //得到图形的宽度和长度
        int width = graymap.getWidth();
        int height = graymap.getHeight();
        //创建二值化图像
        Bitmap binarymap = null;
        binarymap = graymap.copy(Bitmap.Config.ARGB_8888, true);
        //依次循环，对图像的像素进行处理
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //得到当前像素的值
                int col = binarymap.getPixel(i, j);
                //得到alpha通道的值
                int alpha = Color.alpha(col);
                //得到图像的像素RGB的值
                int red = Color.red(col);
                int green = Color.green(col);
                int blue = Color.blue(col);
                //用公式X = 0.3×R+0.59×G+0.11×B计算出X代替原来的RGB
                int gray = (int) ((float) red * 77 + (float) green * 151 + (float) blue * 28) / 256;
//                int gray = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                //设置新图像的当前像素值
                binarymap.setPixel(i, j, gray);
            }
        }
        return binarymap;
    }

    public static Bitmap bitmap2Binary(Bitmap graymap) {
        //得到图形的宽度和长度
        int width = graymap.getWidth();
        int height = graymap.getHeight();
        //创建二值化图像
        Bitmap binarymap = null;
        binarymap = graymap.copy(Bitmap.Config.ARGB_8888, true);
        //依次循环，对图像的像素进行处理
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //得到当前像素的值
                int col = binarymap.getPixel(i, j);
                //得到alpha通道的值
                int alpha = Color.alpha(col);
                //得到图像的像素RGB的值
                int red = Color.red(col);
                int green = Color.green(col);
                int blue = Color.blue(col);
                //用公式X = 0.3×R+0.59×G+0.11×B计算出X代替原来的RGB
                int gray = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                //对图像进行二值化处理
                if (gray <= 180) {
                    gray = Color.BLACK;
                } else {
                    gray = Color.WHITE;
                }
                //设置新图像的当前像素值
                binarymap.setPixel(i, j, gray);
            }
        }
        return binarymap;
    }




    public static Bitmap projectionOntoX(Bitmap bmp) {
        //得到图形的宽度和长度
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int[] r = new int[width];
        boolean is_255;
        for (int i = 0; i < width; i++) {
            is_255 = true;
            for (int j = 0; j < height; j++) {
                int col = bmp.getPixel(i, j);
                int red = Color.red(col);
                if (red == 0) {
                    is_255 = false;
                    break;
                }
            }
            r[i] = is_255 ? Color.WHITE : Color.BLACK;
        }
        int h = DisplayUtils.dipToPx(10);
        int[] bmpColors = new int[width * h];
        for (int i = 0; i < h; i++) {
            System.arraycopy(r, 0, bmpColors, i * width, width);
        }
        LogUtils.e("bmpColors = " + bmpColors);
        return Bitmap.createBitmap(bmpColors, width, h, Bitmap.Config.ARGB_8888);
    }

    public static Bitmap[] splitVerCodeBmp(Bitmap src) {
        Bitmap bmp = bitmap2Binary(src);
        List<Integer> index = new ArrayList<>();
        //得到图形的宽度和长度
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int[] r = new int[width];
        boolean is_255;
        for (int i = 0; i < width; i++) {
            is_255 = true;
            for (int j = 0; j < height; j++) {
                int col = bmp.getPixel(i, j);
                int red = Color.red(col);
                if (red != 255) {
                    is_255 = false;
                    break;
                }
            }
            r[i] = is_255 ? Color.WHITE : Color.BLACK;
        }
        int last = -99;
        for (int i = 0; i < r.length; i++) {
            if (r[i] != last) {
                last = r[i];
                index.add(i);
            }
        }
        Bitmap[] bmps = new Bitmap[(index.size() - 1) / 2];
        index.add(width);
        for (int i = 0; i < bmps.length; i++) {
            int baseIndex = i * 2;
            int w1 = (index.get(baseIndex + 1) - index.get(baseIndex)) / 2 + index.get(baseIndex);
            int w2 = (index.get(baseIndex + 3) - index.get(baseIndex + 2)) / 2 + index.get(baseIndex + 2);
            bmps[i] = bitmap2Gray(Bitmap.createBitmap(bmp, w1, 0, w2 - w1, bmp.getHeight()));
        }
        LogUtils.e("index = " + index.toString());
        return bmps;
    }

}
