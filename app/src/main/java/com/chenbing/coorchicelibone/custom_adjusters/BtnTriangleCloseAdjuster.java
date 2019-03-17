package com.chenbing.coorchicelibone.custom_adjusters;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Path;
import android.graphics.RectF;
import com.coorchice.library.SuperTextView;

/**
 * @author coorchice
 * @date 2018/08/09
 */

public class BtnTriangleCloseAdjuster extends SuperTextView.Adjuster {

    private int color = Color.TRANSPARENT;
    private int cacheW;
    private int cacheH;
    private Path trianglePath = new Path();
    private float[] xCoord= new float[4];
    private Paint paint;
    private float density = Resources.getSystem().getDisplayMetrics().density;

    /**
     * 三角颜色
     *
     * @param color
     */
    public BtnTriangleCloseAdjuster(int color) {
        super();
        this.color = color;
        initPaint();
    }

    private void initPaint() {
        if (paint == null) {
            paint = new Paint();
        }
        paint.reset();
        paint.setAntiAlias(true);
        paint.setDither(true);
    }

    @Override
    protected void adjust(SuperTextView stv, Canvas canvas) {
        if (trianglePath == null || cacheW != stv.getWidth() || cacheH != stv.getHeight()) {
            float corner = stv.getCorner();
            cacheW = stv.getWidth();
            cacheH = stv.getHeight();
            float length = cacheH <= cacheW ? cacheH * 0.47f : cacheW * 0.47f;
            // 三角
            trianglePath.reset();
            trianglePath.moveTo(cacheW, cacheH - length);
            trianglePath.lineTo(cacheW, cacheH - corner);
            trianglePath.arcTo(new RectF(cacheW - corner * 2, cacheH - corner * 2, cacheW, cacheH), 0, 90);
            trianglePath.lineTo(cacheW - length, cacheH);
            trianglePath.close();

            // x
            xCoord[0] = cacheW - (1f / 2f) * length + 1f * density;
            xCoord[1] = cacheH - (1f / 2f) * length + 1f * density;
            xCoord[2] = cacheW - (2f / 17f) * length;
            xCoord[3] = cacheH - (2f / 17f) * length;
        }
        // 三角
        initPaint();
        paint.setColor(color);
        canvas.drawPath(trianglePath, paint);
        // x
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(1.5f * density);
        paint.setStrokeCap(Cap.ROUND);
        canvas.drawLine(xCoord[0], xCoord[1], xCoord[2], xCoord[3], paint);
        canvas.drawLine(xCoord[2], xCoord[1], xCoord[0], xCoord[3], paint);
    }
}
