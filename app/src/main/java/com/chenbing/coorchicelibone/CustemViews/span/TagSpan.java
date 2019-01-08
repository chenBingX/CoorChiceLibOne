package com.chenbing.coorchicelibone.CustemViews.span;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.style.ReplacementSpan;

/**
 * @author coorchice
 * @date 2019/01/03
 */
public class TagSpan extends ReplacementSpan {

    private final int corner;
    private int strokeColor = Color.BLACK;
    private int textColor = Color.BLACK;
    private int fillColor = Color.TRANSPARENT;
    private int lrPadding;
    private int lrMargin;
    private int w;
    private RectF rectF;
    private Paint mPaint;


    public TagSpan(int strokeColor, int textColor) {
        this(strokeColor, textColor, Color.TRANSPARENT
                , (int) Resources.getSystem().getDisplayMetrics().density * 2
                , (int) Resources.getSystem().getDisplayMetrics().density * 2
                , (int) Resources.getSystem().getDisplayMetrics().density * 2);
    }

    public TagSpan(int strokeColor, int textColor, int fillColor) {
        this(strokeColor, textColor, fillColor
                , (int) Resources.getSystem().getDisplayMetrics().density * 2
                , (int) Resources.getSystem().getDisplayMetrics().density * 2
                , (int) Resources.getSystem().getDisplayMetrics().density * 2);
    }

    public TagSpan(int strokeColor, int textColor, int lrPadding, int lrMargin) {
        this(strokeColor, textColor, Color.TRANSPARENT
                , lrPadding
                , lrMargin
                , (int) Resources.getSystem().getDisplayMetrics().density * 2);
    }

    public TagSpan(int strokeColor, int textColor, int fillColor, int lrPadding, int lrMargin, int corner) {
        this.strokeColor = strokeColor;
        this.textColor = textColor;
        this.fillColor = fillColor;
        this.lrPadding = lrPadding;
        this.lrMargin = lrMargin;
        this.corner = corner;
        initPaint();
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable Paint.FontMetricsInt fm) {
        w = (int) paint.measureText(text, start, end);
        return w + lrPadding * 2 + lrMargin * 2;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        if (rectF == null) {
            rectF = new RectF();
        }
        rectF.set(x + lrMargin, top, x + lrMargin + w + lrPadding * 2, bottom);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(fillColor);
        canvas.drawRoundRect(rectF, corner, corner, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(strokeColor);
        canvas.drawRoundRect(rectF, corner, corner, mPaint);
        int tempColor = paint.getColor();
        paint.setColor(textColor);
        canvas.drawText(text, start, end, rectF.left + lrPadding, y, paint);
        paint.setColor(tempColor);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }
}
