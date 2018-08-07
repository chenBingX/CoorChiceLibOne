package com.chenbing.coorchicelibone.ArrowView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.chenbing.iceweather.R;

/**
 * @author coorchice
 * @date 2018/06/19
 */

public class ArrowView extends ImageView {

    private float angle;
    private int arrowColor;
    private float rate;
    private boolean triangle;

    private float density;
    private Paint paint;
    private int width;
    private int height;
    private Rect arrowRect;
    private Path arrowPath;
    private float arrowWidth;
    private boolean asImageView;
    private float paddingBottom;
    private float paddingLeft;
    private float paddingRight;
    private float paddingTop;

    public ArrowView(Context context) {
        this(context, null);
    }

    public ArrowView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArrowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        density = getContext().getResources().getDisplayMetrics().density;
        initAttrs(attrs);
        initPaint();
    }

    private void initAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray =
                getContext().obtainStyledAttributes(attrs, R.styleable.ArrowView);
            angle = typedArray.getFloat(R.styleable.ArrowView_av_angle, 0);
            arrowColor = typedArray.getColor(R.styleable.ArrowView_av_arrowColor, Color.BLACK);
            rate = typedArray.getFloat(R.styleable.ArrowView_av_rate, 1);
            triangle = typedArray.getBoolean(R.styleable.ArrowView_av_triangle, false);
            arrowWidth = typedArray.getDimension(R.styleable.ArrowView_av_arrowWidth, 1 * density);
            paddingBottom = typedArray.getDimension(R.styleable.ArrowView_av_paddingBottom, 0);
            paddingLeft = typedArray.getDimension(R.styleable.ArrowView_av_paddingLeft, 0);
            paddingRight = typedArray.getDimension(R.styleable.ArrowView_av_paddingRight, 0);
            paddingTop = typedArray.getDimension(R.styleable.ArrowView_av_paddingTop, 0);
            asImageView = typedArray.getBoolean(R.styleable.ArrowView_av_asImageView, false);
            typedArray.recycle();
        }
        setRotation(angle);
    }

    private void initPaint() {
        if (paint == null) {
            paint = new Paint();
        }
        paint.reset();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!asImageView) {
            width = getWidth();
            height = getHeight();
            if (arrowRect == null) {
                arrowRect = new Rect();
            }
            arrowRect.set((int)(paddingLeft + arrowWidth / 2), (int)(paddingTop + arrowWidth / 2),
                (int)(width - paddingRight - arrowWidth / 2), (int)(height - paddingBottom - arrowWidth / 2));
            if (arrowPath == null) {
                arrowPath = new Path();
            }
            float leftRate = 1f;
            if (rate < 1) {
                leftRate = rate;
            }
            int arrowW = (arrowRect.left + arrowRect.right) / 2;
            float newLeftW = arrowW * (1 - leftRate);
            arrowPath.moveTo(arrowRect.left + newLeftW, arrowRect.bottom * leftRate);
            arrowPath.lineTo(arrowW, arrowRect.top);
            float rightRate = 1f;
            if (rate > 1) {
                rightRate = 1 / rate;
            }
            float newRightW = arrowW * (1 - rightRate);
            arrowPath.lineTo(arrowRect.right - newRightW, arrowRect.bottom * rightRate);
            if (triangle) {
                arrowPath.close();
                paint.setStyle(Style.FILL_AND_STROKE);
            } else {
                paint.setStyle(Style.STROKE);
            }
            paint.setStrokeWidth(arrowWidth);
            paint.setColor(arrowColor);
            paint.setStrokeJoin(Join.ROUND);
            paint.setStrokeCap(Cap.ROUND);
            canvas.save();
            canvas.translate(newRightW / 2 - newLeftW / 2, 0);
            canvas.drawPath(arrowPath, paint);
            canvas.restore();
        } else {
            super.onDraw(canvas);
        }
    }

    public ArrowView setAngle(float angle) {
        this.angle = angle;
        setRotation(angle);
        return this;
    }

    public ArrowView setArrowColor(int arrowColor) {
        this.arrowColor = arrowColor;
        invalidate();
        return this;
    }

    public ArrowView setTriangle(boolean triangle) {
        this.triangle = triangle;
        invalidate();
        return this;
    }

    public ArrowView asImageView(boolean asImageView) {
        this.asImageView = asImageView;
        invalidate();
        return this;
    }

    public ArrowView setArrowPadding(float paddingLeft, float paddingTop, float paddingRight, float paddingBottom) {
        this.paddingLeft = paddingLeft;
        this.paddingTop = paddingTop;
        this.paddingRight = paddingRight;
        this.paddingBottom = paddingBottom;
        invalidate();
        return this;
    }
}
