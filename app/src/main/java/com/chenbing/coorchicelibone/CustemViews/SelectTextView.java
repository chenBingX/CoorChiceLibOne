package com.chenbing.coorchicelibone.CustemViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.chenbing.iceweather.R;

/**
 * @author coorchice
 * @date 2018/08/07
 */

public class SelectTextView extends TextView {

    private static final PorterDuffXfermode xfermode = new PorterDuffXfermode(Mode.SRC_IN);

    private Path solidPath;
    private RectF solidRectF;
    private float width;
    private float height;
    private float corner;
    private Paint paint;
    private float density;
    private Bitmap selectedBmp;
    private Bitmap unselectedBmp;

    private int unselectedColor = Color.parseColor("#F7F7F7");
    private int selectedColor = Color.parseColor("#FFF7D4");
    private int triangleColor = Color.parseColor("#fca500");
    private OnClickListener onClickListener;
    private OnSelectStateChangedListener onSelectStateChangedListener;

    public SelectTextView(Context context) {
        super(context);
        init(null);
    }

    public SelectTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SelectTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (width != getMeasuredWidth() || height != getMeasuredHeight()) {
            width = getMeasuredWidth();
            height = getMeasuredHeight();
            updateBackground();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (width != getMeasuredWidth() || height != getMeasuredHeight()) {
            width = getMeasuredWidth();
            height = getMeasuredHeight();
            updateBackground();
        }
    }

    private void init(AttributeSet attrs) {
        density = getContext().getResources().getDisplayMetrics().density;
        paint = new Paint();
        initPaint();

        initAttrs(attrs);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(v);
                }
                setSelected(!isSelected());
                if (onSelectStateChangedListener != null) {
                    onSelectStateChangedListener.onChanged(isSelected());
                }
            }
        });
    }

    public void initAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray =
                getContext().obtainStyledAttributes(attrs, R.styleable.SelectTextView);
            selectedColor = typedArray.getColor(R.styleable.SelectTextView_sltv_selectedColor, selectedColor);
            unselectedColor = typedArray.getColor(R.styleable.SelectTextView_sltv_unselectedColor, unselectedColor);
            triangleColor = typedArray.getColor(R.styleable.SelectTextView_sltv_triangleColor, triangleColor);
            setSelected(typedArray.getBoolean(R.styleable.SelectTextView_sltv_isSelected, false));
            corner = typedArray.getDimension(R.styleable.SelectTextView_sltv_corner, 0);
            typedArray.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (selectedBmp == null || unselectedBmp == null) {
            width = getMeasuredWidth();
            height = getMeasuredHeight();
            updateBackground();
        }
        initPaint();
        if (isSelected()) {
            canvas.drawBitmap(selectedBmp, 0, 0, paint);
        } else {
            canvas.drawBitmap(unselectedBmp, 0, 0, paint);
        }
        super.onDraw(canvas);
    }

    private void updateBackground() {
        if (width == 0 || height == 0) {
            return;
        }
        initPaint();

        // unselectedBmp
        unselectedBmp = Bitmap.createBitmap((int)width, (int)height, Config.ARGB_8888);
        Canvas canvas = new Canvas(unselectedBmp);
        if (solidPath == null) {
            solidPath = new Path();
        } else {
            solidPath.reset();
        }
        if (solidRectF == null) {
            solidRectF = new RectF();
        } else {
            solidRectF.setEmpty();
        }
        solidRectF.set(0, 0, width, height);
        solidPath.addRoundRect(solidRectF, corner, corner, Path.Direction.CW);
        paint.setColor(unselectedColor);
        canvas.drawPath(solidPath, paint);

        // selectedSrc
        Bitmap selectedSrc = Bitmap.createBitmap((int)width, (int)height, Config.ARGB_8888);
        canvas = new Canvas(selectedSrc);
        paint.setColor(selectedColor);
        // 背景
        canvas.drawRect(solidRectF, paint);
        Path trianglePath = new Path();
        float length = height <= width ? height * 0.47f : width * 0.47f;
        trianglePath.moveTo(width, height - length);
        trianglePath.lineTo(width, height);
        trianglePath.lineTo(width - length, height);
        trianglePath.close();
        paint.setColor(triangleColor);
        // 三角
        canvas.drawPath(trianglePath, paint);
        float left = width - (1f / 2f) * length + 1f * density;
        float top = height - (1f / 2f) * length + 1f * density;
        float right = width - (2f / 17f) * length;
        float bottom = height - (2f / 17f) * length;
        // x
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(1.5f * density);
        paint.setStrokeCap(Cap.ROUND);
        canvas.drawLine(left, top, right, bottom, paint);
        canvas.drawLine(right, top, left, bottom, paint);

        // selected
        selectedBmp = Bitmap.createBitmap((int)width, (int)height, Config.ARGB_8888);
        canvas = new Canvas(selectedBmp);
        canvas.drawBitmap(unselectedBmp, 0, 0, paint);
        paint.setXfermode(xfermode);
        canvas.drawBitmap(selectedSrc, 0, 0, paint);
    }

    private void initPaint() {
        paint.reset();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);
    }

    /**
     * 设置圆角大小
     *
     * @param corner
     */
    public void setCorner(float corner) {
        this.corner = corner;
        updateBackground();
        invalidate();
    }

    /**
     * 设置未被选中时的背景色
     *
     * @param unselectedColor
     */
    public void setUnselectedColor(int unselectedColor) {
        this.unselectedColor = unselectedColor;
        updateBackground();
        invalidate();
    }

    /**
     * 设置选中时的背景色
     *
     * @param selectedColor
     */
    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
        updateBackground();
        invalidate();
    }

    /**
     * 设置三角的颜色
     *
     * @param triangleColor
     */
    public void setTriangleColor(int triangleColor) {
        this.triangleColor = triangleColor;
        updateBackground();
        invalidate();
    }

    /**
     * 监听选中状态变化
     *
     * @param onSelectStateChangedListener
     */
    public void setOnSelectStateChangedListener(
        OnSelectStateChangedListener onSelectStateChangedListener) {
        this.onSelectStateChangedListener = onSelectStateChangedListener;
    }

    /**
     * 选中状态变化监听器
     */
    public static interface OnSelectStateChangedListener {
        /**
         * 选中状态变化时会回调
         *
         * @param isSelected
         */
        void onChanged(boolean isSelected);
    }

}
