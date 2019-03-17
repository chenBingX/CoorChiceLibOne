package com.chenbing.coorchicelibone.custom_adjusters;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Path.Op;
import android.graphics.RectF;
import android.text.TextPaint;
import android.view.Gravity;
import com.coorchice.library.SuperTextView;

/**
 * @author coorchice
 * @date 2018/08/09
 */

public class BtnAddPassengerAdjuster extends SuperTextView.Adjuster {

    private final float margin;
    private int color;
    private float radius;
    private float density = Resources.getSystem().getDisplayMetrics().density;
    private Paint paint;
    private int cacheH;
    private Path addIconPath;

    public BtnAddPassengerAdjuster(int color, float radius, float margin) {
        super();
        this.color = color;
        this.radius = radius * density;
        this.margin = margin * density;
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
        if (addIconPath == null || cacheH != stv.getHeight()) {
            cacheH = stv.getHeight();
            RectF addIconRect = new RectF(0, cacheH / 2 - radius, radius * 2, cacheH / 2 + radius);
            TextPaint stvPaint = stv.getPaint();
            float textW = stvPaint.measureText(stv.getText().toString());
            float totalW = radius * 2 + margin + textW;
            float padding = (stv.getMeasuredWidth() - totalW) / 2;
            addIconRect.offset(padding, 0);

            // circle
            Path circlePath = new Path();
            circlePath.addRoundRect(addIconRect, radius, radius, Direction.CW);
            // 加号
            Path xPath1 = new Path();
            float xPadding = 2 * density;
            xPath1.addRect((int)(addIconRect.centerX() - 1 * density), addIconRect.top + (int)(xPadding),
                (int)(addIconRect.centerX() + 1 * density),
                (int)(addIconRect.bottom - xPadding), Direction.CW);
            Path xPath2 = new Path();
            xPath2.addRect((int)(addIconRect.left + xPadding), (int)(addIconRect.centerY() - 1 * density),
                (int)(addIconRect.right - xPadding),
                (int)(addIconRect.centerY() + 1 * density), Direction.CW);
            Path xPath = new Path();
            xPath.op(xPath1, xPath2, Op.UNION);
            // 合成Path
            addIconPath = new Path();
            addIconPath.op(circlePath, xPath, Op.DIFFERENCE);

            int left = (int)(padding + addIconRect.width() + margin);
            if (stv.getPaddingLeft() != left) {
                stv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                stv.setPadding(left, 0, 0, 0);
            }
        }
        paint.setColor(color);
        canvas.drawPath(addIconPath, paint);
    }

    public void invalidate() {
        addIconPath = null;
    }
}
