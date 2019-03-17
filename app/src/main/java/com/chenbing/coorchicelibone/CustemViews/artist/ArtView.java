package com.chenbing.coorchicelibone.CustemViews.artist;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by coorchice on 2017/10/13.
 * <p>
 * ArtView是重制的View，需要添加到ArtistLayout中才能显示。大多数使用方法和原生View保持一致。
 * <p>
 * 支持绝对布局和相对布局。
 * <p>
 * 可以单独处理触摸事件。
 * <p>
 * 现在已经对重绘操作进行了对齐，所以频繁的发起重绘不会带来性能影响。
 * <p>
 * ArtView由于不受系统管控，所以可以异步进行创建、更新UI等操作。但需要谨慎对待数据安全问题，特别是View相关的属性。
 * <p>
 * 支持圆角背景，并能单独指定圆角位置。{@link ArtView#corner}
 */

public class ArtView {

    public static final int VISIBLE = 0x00000000;
    public static final int INVISIBLE = 0x00000004;
    public static final int GONE = 0x00000008;

    public static final int MODE_SHIFT = 30;
    public static final int MODE_MASK = 0x3 << MODE_SHIFT;
    public static final int WRAP_CONTENT = (1 << MODE_SHIFT);
    public static final int EXACTLY = (2 << MODE_SHIFT);
    public static final int MATCH_PARENT = (3 << MODE_SHIFT);

    public static final String X = "x";
    public static final String Y = "y";
    public static final String TRANSLATION_X = X;
    public static final String TRANSLATION_Y = Y;
    public static final String ROTATE = "degrees";
    public static final String SCALE_X = "scaleX";
    public static final String SCALE_Y = "scaleY";

    private int visibility = VISIBLE;
    private int id = -1;

    /**
     * 表示View宽。默认情况下，数值为0，尺寸模式为{@link ArtView#WRAP_CONTENT}。
     * 需要注意，这个成员是始终包括了View的宽的数值和它的尺寸模式的int包，所以不要直接使用它。因该通过{@link ArtView#getWidth()}来
     * 获取一个View的宽。通过{@link ArtView#getWidthSizeMode()}可以获得宽的尺寸模式。
     * <p>
     * 在{@link ArtView#makeWidth(int)}可以看到这个int包如何打包的。
     */
    private int width;
    /**
     * 表示View高。默认情况下，数值为0，尺寸模式为{@link ArtView#WRAP_CONTENT}。
     * 需要注意，这个成员是始终包括了View的高的数值和它的尺寸模式的int包，所以不要直接使用它。因该通过{@link ArtView#getHeight()}来
     * 获取一个View的高。通过{@link ArtView#getHeightSizeMode()}可以获得高的尺寸模式。
     * <p>
     * 在{@link ArtView#makeHeight(int)}可以看到这个int包如何打包的。
     */
    private int height;
    protected float x;
    protected float y;
    protected int left;
    protected int top;
    protected int right;
    protected int bottom;
    private Rect rect;
    protected int marginLeft;
    protected int marginTop;
    protected int marginRight;
    protected int marginBottom;
    protected int padding;
    protected int paddingLeft;
    protected int paddingTop;
    protected int paddingRight;
    protected int paddingBottom;
    private Anchor anchor;
    private ArrayList<Anchor> anchorList;

    private Paint paint;
    private float density = 1.0f;
    private float sp = 1.0f;
    private Resources resources;

    protected float pivotX = -1;
    protected float pivotY = -1;
    private float degrees = 0;
    private float scaleX = 1f;
    private float scaleY = 1f;

    private ArtistLayout parent;
    private int parentWidth;
    private int parentHeight;

    private OnTouchListener onTouchListener;
    private OnClickListener onClickListener;
    private OnLongClickListener onLongClickListener;
    private Handler handler;
    private boolean isClick;
    private Runnable tapRunnable;
    private Runnable longClickRunnable;
    private boolean clickable = true;
    private boolean longClickable = true;

    protected int layoutGravity = Gravity.NO_GRAVITY;
    protected int gravity;
    private Drawable background;
    private int backgroundResId = -1;
    private int backgroundColor;
    private Path backgroundColorPath;
    private RectF backgroundColorRectF;
    private Path strokeWidthPath;
    private RectF strokeLineRectF;
    private float strokeWidth;
    private int strokeColor;
    private float corner;
    private float leftTopCorner[] = new float[2];
    private float rightTopCorner[] = new float[2];
    private float leftBottomCorner[] = new float[2];
    private float rightBottomCorner[] = new float[2];
    private float corners[] = new float[8];
    private boolean leftTopCornerEnable;
    private boolean rightTopCornerEnable;
    private boolean leftBottomCornerEnable;
    private boolean rightBottomCornerEnable;


    ArtView() {
        width |= WRAP_CONTENT;
        height |= WRAP_CONTENT;
        rect = new Rect();
        paint = getPaint();
    }

    public ArtView(int width, int height) {
        this();
        makeSize(width, height);
//        this.width = width;
//        this.height = height;
    }

    /**
     * 将View的width数值和模式打包成一个int
     * 将View的height数值和模式打包成一个int
     *
     * @param width
     * @param height
     */
    public void makeSize(int width, int height) {
        makeWidth(width);
        makeHeight(height);
    }

    /**
     * 将View的width数值和模式打包成一个int
     *
     * @param width
     */
    public void makeWidth(int width) {
        if (getSizeMode(width) == MATCH_PARENT) {
            this.width = (parentWidth & ~MODE_MASK) | (MATCH_PARENT & MODE_MASK);
        } else if (getSizeMode(width) == WRAP_CONTENT) {
            this.width = (width & ~MODE_MASK) | (WRAP_CONTENT & MODE_MASK);
        } else {
            this.width = (width & ~MODE_MASK) | (EXACTLY & MODE_MASK);
        }
    }

    /**
     * 将View的height数值和模式打包成一个int
     *
     * @param height
     */
    public void makeHeight(int height) {
        if (getSizeMode(height) == MATCH_PARENT) {
            this.height = (parentHeight & ~MODE_MASK) | (MATCH_PARENT & MODE_MASK);
        } else if (getSizeMode(height) == WRAP_CONTENT) {
            this.height = (height & ~MODE_MASK) | (WRAP_CONTENT & MODE_MASK);
        } else {
            this.height = (height & ~MODE_MASK) | (EXACTLY & MODE_MASK);
        }
    }

    final void draw(Canvas canvas) {
        if (visibility == VISIBLE) {
            canvas.save();
            canvas.rotate(degrees, pivotX, pivotY);
            canvas.scale(scaleX, scaleY, pivotX, pivotY);
            drawStrokeLine(canvas);
            drawBackgroundColor(canvas);
            drawBackground(canvas);
            onDraw(canvas);
            if (ArtistLayout.DEBUG) {
                if (paint == null) {
                    paint = getPaint();
                }
                int color = paint.getColor();
                paint.setColor(Color.parseColor("#4CFF7A95"));
                canvas.drawRect(getRect(), paint);
                paint.setColor(color);
            }
            canvas.restore();
        }
    }

    private void drawStrokeLine(Canvas canvas) {
        if (strokeWidth > 0) {
            if (strokeWidthPath == null) {
                strokeWidthPath = new Path();
            } else {
                strokeWidthPath.reset();
            }
            if (strokeLineRectF == null) {
                strokeLineRectF = new RectF();
            } else {
                strokeLineRectF.setEmpty();
            }
            strokeLineRectF.set(x + strokeWidth / 2, y + strokeWidth / 2, x + getWidth() - strokeWidth / 2,
                    y + getHeight() - strokeWidth / 2);
            getCorners(corner);
            strokeWidthPath.addRoundRect(strokeLineRectF, corners, Path.Direction.CW);
            Paint.Style style = paint.getStyle();
            int color = paint.getColor();
            float strokeWidth = paint.getStrokeWidth();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            paint.setStrokeWidth(this.strokeWidth);
            canvas.drawPath(strokeWidthPath, paint);
            paint.setStyle(style);
            paint.setColor(color);
            paint.setStrokeWidth(strokeWidth);
        }
    }

    private void drawBackgroundColor(Canvas canvas) {
        if (backgroundColor == 0) return;

        if (backgroundColorPath == null) {
            backgroundColorPath = new Path();
        } else {
            backgroundColorPath.reset();
        }

        if (backgroundColorRectF == null) {
            backgroundColorRectF = new RectF();
        } else {
            backgroundColorRectF.setEmpty();
        }

        backgroundColorRectF.set(x + strokeWidth, y + strokeWidth, x + getWidth() - strokeWidth, y + getHeight() - strokeWidth);
        getCorners(corner - strokeWidth / 2);
        backgroundColorPath.addRoundRect(backgroundColorRectF, corners, Path.Direction.CW);

        Paint.Style style = paint.getStyle();
        int color = paint.getColor();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(backgroundColor);
        canvas.drawPath(backgroundColorPath, paint);
        paint.setStyle(style);
        paint.setColor(color);
    }

    private float[] getCorners(float corner) {
        leftTopCorner[0] = 0;
        leftTopCorner[1] = 0;
        rightTopCorner[0] = 0;
        rightTopCorner[1] = 0;
        leftBottomCorner[0] = 0;
        leftBottomCorner[1] = 0;
        rightBottomCorner[0] = 0;
        rightBottomCorner[1] = 0;
        if (this.leftTopCornerEnable || this.rightTopCornerEnable || this.leftBottomCornerEnable
                || this.rightBottomCornerEnable) {
            if (this.leftTopCornerEnable) {
                leftTopCorner[0] = corner;
                leftTopCorner[1] = corner;
            }
            if (this.rightTopCornerEnable) {
                rightTopCorner[0] = corner;
                rightTopCorner[1] = corner;
            }
            if (this.leftBottomCornerEnable) {
                leftBottomCorner[0] = corner;
                leftBottomCorner[1] = corner;
            }
            if (this.rightBottomCornerEnable) {
                rightBottomCorner[0] = corner;
                rightBottomCorner[1] = corner;
            }
        } else {
            leftTopCorner[0] = corner;
            leftTopCorner[1] = corner;
            rightTopCorner[0] = corner;
            rightTopCorner[1] = corner;
            leftBottomCorner[0] = corner;
            leftBottomCorner[1] = corner;
            rightBottomCorner[0] = corner;
            rightBottomCorner[1] = corner;
        }
        corners[0] = leftTopCorner[0];
        corners[1] = leftTopCorner[1];
        corners[2] = rightTopCorner[0];
        corners[3] = rightTopCorner[1];
        corners[4] = rightBottomCorner[0];
        corners[5] = rightBottomCorner[1];
        corners[6] = leftBottomCorner[0];
        corners[7] = leftBottomCorner[1];
        return corners;
    }

    private void drawBackground(Canvas canvas) {
        if (resources != null && backgroundResId != -1) {
            this.background = resources.getDrawable(backgroundResId);
        }
        if (background != null) {
            background.setBounds(getRect());
            background.draw(canvas);
        }
    }

    public void onDraw(Canvas canvas) {
    }

    final boolean onTouch(MotionEvent event) {
        if (onTouchListener != null) {
            return onTouchListener.onTouch(this, event);
        } else {
            return onTouchEvent(event);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (clickable || longClickable) {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                isClick = true;
                startTrackClick();
                startTrackLongClick();
            } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                if (onClickListener != null && isClick) {
                    onClickListener.onClick(ArtView.this);
                }
                isClick = false;
                cancelRunnable(tapRunnable);
                cancelRunnable(longClickRunnable);
            }
            return true;
        }
        return false;
    }


    private void startTrackClick() {
        if (tapRunnable == null) {
            tapRunnable = new Runnable() {
                @Override
                public void run() {
                    isClick = false;
                }
            };
        }
        postDelay(tapRunnable, ViewConfiguration.getTapTimeout());
    }

    private void startTrackLongClick() {
        if (longClickRunnable == null) {
            longClickRunnable = new Runnable() {
                @Override
                public void run() {
                    if (onLongClickListener != null) {
                        onLongClickListener.onLongClick(ArtView.this);
                    }
                }
            };
        }
        postDelay(longClickRunnable, ViewConfiguration.getLongPressTimeout());
    }

    public Drawable getBackground() {
        return background;
    }

    public void setBackground(Drawable background) {
        this.background = background;
        backgroundResId = -1;
        invalidate();
    }

    public void setBackground(Bitmap background) {
        this.background = new BitmapDrawable(background);
        backgroundResId = -1;
        invalidate();
    }

    public void setBackground(@DrawableRes int background) {
        if (this.resources == null) {
            this.backgroundResId = background;
        } else {
            this.background = resources.getDrawable(background);
        }
        invalidate();
    }

    public void setBackgroundColor(int color) {
        this.backgroundColor = color;
        invalidate();
    }

    public void setBackgroundColor(String color) {
        setBackgroundColor(Color.parseColor(color));
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
        if (this.visibility == GONE) {

        }
//        notifyAllAnchor();
        invalidate();
    }

    public void getId() {
        id = hashCode();
    }

    public int getSizeMode(int size) {
        return (size & MODE_MASK);
    }

    public int getWidthSizeMode() {
        return getSizeMode(this.width);
    }

    public int getHeightSizeMode() {
        return getSizeMode(this.height);
    }

    public int getWidth() {
        return (width & ~MODE_MASK);
    }

    public void setWidth(int width) {
        if (getSizeMode(width) == 0) {
            width = (width & ~MODE_MASK) | (EXACTLY & MODE_MASK);
        }
        makeWidth(width);
        right = left + getWidth();
        invalidate();
    }

    public int getHeight() {
        return (height & ~MODE_MASK);
    }

    public void setHeight(int height) {
        if (getSizeMode(height) == 0) {
            height = (height & ~MODE_MASK) | (EXACTLY & MODE_MASK);
        }
        makeHeight(height);
        bottom = top + getHeight();
        invalidate();
    }

    public void setSize(int width, int height) {
        if (getSizeMode(width) == 0) {
            width = (width & ~MODE_MASK) | (EXACTLY & MODE_MASK);
        }
        if (getSizeMode(height) == 0) {
            height = (height & ~MODE_MASK) | (EXACTLY & MODE_MASK);
        }
        makeSize(width, height);
        right = left + getWidth();
        bottom = top + getHeight();
        invalidate();
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        if (isAttach() && layoutGravity != Gravity.NO_GRAVITY){
            layoutGravity &= ~Gravity.LEFT;
            layoutGravity &= ~Gravity.RIGHT;
            layoutGravity &= ~Gravity.CENTER_HORIZONTAL;
            layoutGravity &= ~Gravity.CENTER;
        }
        this.x = x;
        this.left = (int) x;
        this.right = this.left + getWidth();
//        notifyAllAnchor();
        invalidate();
    }

    private void setXWithoutInvalidate(float x) {
        this.x = x;
        this.left = (int) x;
        this.right = this.left + getWidth();
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        if (isAttach() && layoutGravity != Gravity.NO_GRAVITY){
            layoutGravity &= ~Gravity.TOP;
            layoutGravity &= ~Gravity.BOTTOM;
            layoutGravity &= ~Gravity.CENTER_VERTICAL;
            layoutGravity &= ~Gravity.CENTER;
        }
        this.y = y;
        this.top = (int) y;
        this.bottom = this.top + getHeight();
//        notifyAllAnchor();
        invalidate();
    }

    public void setYWithoutInvalidate(float y) {
        this.y = y;
        this.top = (int) y;
        this.bottom = this.top + getHeight();
    }

    public void setXY(float x, float y) {
        if (isAttach() && layoutGravity != Gravity.NO_GRAVITY){
            layoutGravity &= ~Gravity.RIGHT;
            layoutGravity &= ~Gravity.LEFT;
            layoutGravity &= ~Gravity.CENTER_HORIZONTAL;
            layoutGravity &= ~Gravity.TOP;
            layoutGravity &= ~Gravity.BOTTOM;
            layoutGravity &= ~Gravity.CENTER_VERTICAL;
            layoutGravity &= ~Gravity.CENTER;
        }
        this.x = x;
        this.y = y;
        this.left = (int) x;
        this.top = (int) y;
        this.right = this.left + getWidth();
        this.bottom = this.top + getHeight();
        invalidate();
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        if (isAttach() && layoutGravity != Gravity.NO_GRAVITY){
            layoutGravity &= ~Gravity.LEFT;
            layoutGravity &= ~Gravity.RIGHT;
            layoutGravity &= ~Gravity.CENTER_HORIZONTAL;
            layoutGravity &= ~Gravity.CENTER;
        }
        this.left = left;
        this.x = left;
        this.right = this.left + getWidth();
//        notifyAllAnchor();
        invalidate();
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        if (isAttach() && layoutGravity != Gravity.NO_GRAVITY){
            layoutGravity &= ~Gravity.TOP;
            layoutGravity &= ~Gravity.BOTTOM;
            layoutGravity &= ~Gravity.CENTER_VERTICAL;
            layoutGravity &= ~Gravity.CENTER;
        }
        this.top = top;
        this.y = top;
        this.bottom = this.top + getHeight();
//        notifyAllAnchor();
        invalidate();
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        if (isAttach() && layoutGravity != Gravity.NO_GRAVITY){
            layoutGravity &= ~Gravity.LEFT;
            layoutGravity &= ~Gravity.RIGHT;
            layoutGravity &= ~Gravity.CENTER_HORIZONTAL;
            layoutGravity &= ~Gravity.CENTER;
        }
        this.x = right - getWidth();
        this.left = (int) this.x;
        this.right = right;
//        notifyAllAnchor();
        invalidate();
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        if (isAttach() && layoutGravity != Gravity.NO_GRAVITY){
            layoutGravity &= ~Gravity.TOP;
            layoutGravity &= ~Gravity.BOTTOM;
            layoutGravity &= ~Gravity.CENTER_VERTICAL;
            layoutGravity &= ~Gravity.CENTER;
        }
        this.y = bottom - getHeight();
        this.top = (int) this.y;
        this.bottom = bottom;
//        notifyAllAnchor();
        invalidate();
    }

    public float getDegrees() {
        return degrees;
    }

    public void setDegrees(float degrees) {
        this.degrees = degrees;
//        notifyAllAnchor();
        invalidate();
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
//        notifyAllAnchor();
        invalidate();
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
//        notifyAllAnchor();
        invalidate();
    }

    public float getPivotX() {
        return pivotX;
    }

    /**
     * 设置View动画锚点的x坐标。会影响缩放、旋转等动画。
     *
     * @param pivotX
     */
    public void setPivotX(float pivotX) {
        this.pivotX = pivotX;
    }

    public float getPivotY() {
        return pivotY;
    }

    /**
     * 设置View动画锚点的y坐标。会影响缩放、旋转等动画。
     *
     * @param pivotY
     */
    public void setPivotY(float pivotY) {
        this.pivotY = pivotY;
    }

    /**
     * 获取View所在位置的矩形区域。
     *
     * @return
     */
    public Rect getRect() {
        left = (int) this.x;
        top = (int) y;
        right = (int) (this.x + getWidth());
        bottom = (int) (y + getHeight());
        rect.set(left, top, right, bottom);
        return rect;
    }

    public int getParentWidth() {
        return parentWidth;
    }

    public int getParentHeight() {
        return parentHeight;
    }

    public Paint getPaint() {
        resetPaint();
        return paint;
    }

    public void resetPaint() {
        if (paint == null) {
            paint = new Paint();
        }
        paint.reset();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);
    }

    public void clickable(boolean clickable) {
        this.clickable = clickable;
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public boolean isLongClickable() {
        return longClickable;
    }

    public void setLongClickable(boolean longClickable) {
        this.longClickable = longClickable;
    }

    protected void refreshPSize(int width, int height) {
        boolean changed = parentWidth != width || parentHeight != height;
        parentWidth = width;
        parentHeight = height;
        if (getSizeMode(this.width) == MATCH_PARENT) {
            makeWidth(MATCH_PARENT);
        }
        if (getSizeMode(this.height) == MATCH_PARENT) {
            makeHeight(MATCH_PARENT);
        }
        if (changed) {
            computeXYWithLayoutGravity();
            notifyAllAnchor();
        }
    }

    protected void computeXYWithLayoutGravity() {
        final int majorGravity = layoutGravity & Gravity.VERTICAL_GRAVITY_MASK;
        final int minorGravity = layoutGravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK;

        switch (majorGravity) {
            case Gravity.BOTTOM:
                setYWithoutInvalidate(parentHeight - getHeight() + marginTop - marginBottom);
                break;

            case Gravity.CENTER_VERTICAL:
                setYWithoutInvalidate(parentHeight / 2 - getHeight() / 2 + marginTop - marginBottom);
                break;

            case Gravity.TOP:
            default:
                break;
        }

        switch (minorGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
            case Gravity.CENTER_HORIZONTAL:
                setXWithoutInvalidate(parentWidth / 2 - getWidth() / 2 + +marginLeft - marginRight);
                break;

            case Gravity.RIGHT:
                setXWithoutInvalidate(parentWidth - getWidth() + marginLeft - marginRight);
                break;

            case Gravity.LEFT:
            default:
                break;
        }
    }

    public void checkGravity() {
    }

    /**
     * 设置View在ArtistLayout中的相对位置。
     *
     * @param layoutGravity
     */
    public void setLayoutGravity(int layoutGravity) {
        this.layoutGravity = layoutGravity;
        computeXYWithLayoutGravity();
        invalidate();

    }

    public int getLayoutGravity() {
        return this.layoutGravity;
    }

    public int getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
        invalidate();
    }

    public int getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
        invalidate();

    }

    public int getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(int marginRight) {
        this.marginRight = marginRight;
        invalidate();

    }

    public int getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
        invalidate();

    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
        this.paddingLeft = padding;
        this.paddingTop = padding;
        this.paddingRight = padding;
        this.paddingBottom = padding;
        invalidate();
    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
        invalidate();
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
        invalidate();
    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
        invalidate();

    }

    public int getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
        invalidate();

    }

    public int getGravity() {
        return gravity;
    }

    /**
     * 设置View内部内容的相对位置。并不是对所有View有用。视View各自实现而定。
     * 比如对ArtText是有用的。
     *
     * @param gravity
     */
    public void setGravity(int gravity) {
        this.gravity = gravity;
        invalidate();

    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        invalidate();

    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        invalidate();

    }

    public float getCorner() {
        return corner;
    }

    public void setCorner(float corner) {
        this.corner = corner;
        invalidate();
    }

    public boolean isLeftTopCornerEnable() {
        return leftTopCornerEnable;
    }

    public void setLeftTopCornerEnable(boolean leftTopCornerEnable) {
        this.leftTopCornerEnable = leftTopCornerEnable;
        invalidate();

    }

    public boolean isRightTopCornerEnable() {
        return rightTopCornerEnable;
    }

    public void setRightTopCornerEnable(boolean rightTopCornerEnable) {
        this.rightTopCornerEnable = rightTopCornerEnable;
        invalidate();

    }

    public boolean isLeftBottomCornerEnable() {
        return leftBottomCornerEnable;
    }

    public void setLeftBottomCornerEnable(boolean leftBottomCornerEnable) {
        this.leftBottomCornerEnable = leftBottomCornerEnable;
        invalidate();

    }

    public boolean isRightBottomCornerEnable() {
        return rightBottomCornerEnable;
    }

    public void setRightBottomCornerEnable(boolean rightBottomCornerEnable) {
        this.rightBottomCornerEnable = rightBottomCornerEnable;
        invalidate();
    }

    /**
     * 将View添加到一个父View中，见{@link ArtistLayout#addView(ArtView)}。
     * 注意不要传null，会抛 NullPointException 。
     *
     * @param parent ArtistLayout
     * @throws NullPointException
     */
    public void addTo(ArtistLayout parent) {
        parent.addView(this);
    }


    protected void attach(ArtistLayout parent) {
        if (this.parent != null) {
            throw new IllegalStateException(
                    "The ArtView has attached to a parent, you have to remove first!");
        } else {
            this.parent = parent;
            density = parent.getDp();
            sp = parent.getSp();
            resources = parent.getResources();

            onAttach();
            if (pivotX == -1) {
                pivotX = x;
            }
            if (pivotY == -1) {
                pivotY = y;
            }
        }
        parentWidth = parent.getWidth();
        parentHeight = parent.getHeight();
        if (parent.getWidth() != 0 && getSizeMode(this.width) == MATCH_PARENT) {
            makeWidth(MATCH_PARENT);
        }
        if (parent.getHeight() != 0 && getSizeMode(this.height) == MATCH_PARENT) {
            makeHeight(MATCH_PARENT);
        }
        computeXYWithLayoutGravity();
    }

    protected void detach() {
        if (parent != null) {
            parent = null;
        }
        cancelRunnable(tapRunnable);
        cancelRunnable(longClickRunnable);
        onDetach();
    }

    /**
     * 检测该View是否被添加到了一个ArtistLayout中。
     *
     * @return
     */
    public boolean isAttach() {
        return parent != null;
    }

    public ArtistLayout getParent() {
        return parent;
    }

    /**
     * 当View被添加到一个ArtistLayout的时候会被调用。
     */
    public void onAttach() {

    }

    /**
     * 当View被从一个ArtistLayout移除的时候会被调用。
     */
    public void onDetach() {

    }

    /**
     * 要求View发起一次重绘请求。
     */
    public void invalidate() {
        if (anchor != null && !anchor.isAttach()){
            anchor.notifyChanged();
        } else {
            notifyAllAnchor();
        }
        if (parent != null) {
            parent.postInvalidate();
        }
    }

    private void postDelay(Runnable runnable, long delayMillis) {
        if (handler == null && parent != null) {
            handler = parent.getHandler();
        }
        handler.postDelayed(runnable, delayMillis);
    }

    private void cancelRunnable(Runnable runnable) {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

    /**
     * 设置触摸监听。和原生使用一致。
     *
     * @param onTouchListener
     */
    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    /**
     * 获得设备dp。只有将View添加到一个ArtistLayout后才能获取到正确的值。
     * 否则，默认为1.0。
     *
     * @return
     */
    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    /**
     * 获得设备sp。只有将View添加到一个ArtistLayout后才能获取到正确的值。
     * 否则，默认为1.0。
     *
     * @return
     */
    public float getSp() {
        return sp;
    }

    public void setSp(float sp) {
        this.sp = sp;
    }

    public Resources getResources() {
        return resources;
    }

    /**
     * 设置左边缘与target右边缘保持leftMargin的单位的距离。
     *
     * @param target
     * @param leftMargin
     */
    public void leftMarginTo(ArtView target, float leftMargin) {
        anchor(target, target.getWidth() + leftMargin, -1, true, false);
        if (anchor != null) {
            anchor.xMarginType = Anchor.MARGIN_TYPE_F;
        }
    }

    /**
     * 设置上边缘与target底边缘保持topMargin的单位的距离。
     *
     * @param target
     * @param topMargin
     */
    public void topMarginTo(ArtView target, float topMargin) {
        anchor(target, -1, target.getHeight() + topMargin, true, false);
        if (anchor != null) {
            anchor.yMarginType = Anchor.MARGIN_TYPE_F;
        }
    }

    /**
     * 设置右边缘与target左边缘保持rightMargin的单位的距离。
     * <p>
     * 还有问题，暂时不要使用。
     *
     * @param target
     * @param rightMargin
     */
    public void rightMarginTo(ArtView target, float rightMargin) {
        detachAnchor();
        anchor = new Anchor(this, target, 0, 0, 0, 0, -(getWidth() + rightMargin), -1, false);
        target.addAnchor(anchor);
        anchor.xMarginType = Anchor.MARGIN_TYPE_R;
        anchor.changed();
        invalidate();
    }

    /**
     * 设置下边缘与target上边缘保持bottomMargin的单位的距离。
     * <p>
     * 还有问题，暂时不要使用。
     *
     * @param target
     * @param bottomMargin
     */
    public void bottomMarginTo(ArtView target, float bottomMargin) {
        detachAnchor();
        anchor = new Anchor(this, target, 0, 0, 0, 0, -1, -(getHeight() + bottomMargin), false);
        target.addAnchor(anchor);
        anchor.yMarginType = Anchor.MARGIN_TYPE_R;
        anchor.changed();
        invalidate();
    }

    /**
     * 自己左上角坐标相对于target左上角坐标偏移(offsetX, offsetY)。详情见
     * {@link ArtView#anchor(ArtView target, float offsetX, float offsetY, boolean attach, boolean followVisible)}
     * <p>
     * 当attach为true时，会跟随target的可见性。
     *
     * @param target  目标（宿主）View。
     * @param offsetX 在目标坐标系（以目标的左上角为坐标原点的坐标系）中，相对于x点向右偏移offsetX个单位长度。
     * @param offsetY 在目标坐标系（以目标的左上角为坐标原点的坐标系）中，相对于y点向右偏移offsetY个单位长度。
     * @param attach  是否建立依附关系。如果是true表示希望和目标锚点建立依附关系，那么当目标ArtView坐标发生改变时，
     *                本ArtView的位置也会跟着改变。如果是false表示不和目标锚点建立依附关系，也就不会随目标的坐标变化而变
     *                化，通常可以用来首次确定自己在总坐标系中的位置。
     */
    public void anchor(ArtView target, float offsetX, float offsetY, boolean attach) {
        anchor(target, 0, 0, 0, 0, offsetX, offsetY, attach, true);
    }

    /**
     * 自己左上角坐标相对于target左上角坐标偏移(offsetX, offsetY)。详情见
     * {@link ArtView#anchor(ArtView target, float mX, float mY, float tX, float tY, float offsetX, float offsetY, boolean attach, boolean followVisible)}
     *
     * @param target        目标（宿主）View。
     * @param offsetX       在目标坐标系（以目标的左上角为坐标原点的坐标系）中，相对于x点向右偏移offsetX个单位长度。
     * @param offsetY       在目标坐标系（以目标的左上角为坐标原点的坐标系）中，相对于y点向右偏移offsetY个单位长度。
     * @param attach        是否建立依附关系。如果是true表示希望和目标锚点建立依附关系，那么当目标ArtView坐标发生改变时，
     *                      本ArtView的位置也会跟着改变。如果是false表示不和目标锚点建立依附关系，也就不会随目标的坐标变化而变
     *                      化，通常可以用来首次确定自己在总坐标系中的位置。
     * @param followVisible 是否跟随target的可见性。只有attach为true时，该项才有效。
     */
    public void anchor(ArtView target, float offsetX, float offsetY, boolean attach, boolean followVisible) {
        anchor(target, 0, 0, 0, 0, offsetX, offsetY, attach, followVisible);
    }

    /**
     * 将自己的一个和目标target中的一个锚点建立联系，以此来确定自己位置。注意看看参数attch的解释哦！
     *
     * @param target        目标（宿主）View。
     * @param mX            自己坐标系（以自己的左上角为坐标原点的坐标系）中的x点。
     * @param mY            自己坐标系（以自己的左上角为坐标原点的坐标系）中的y点。
     * @param tX            目标坐标系（以目标的左上角为坐标原点的坐标系）中的x点。
     * @param tY            目标坐标系（以目标的左上角为坐标原点的坐标系）中的y点。
     * @param offsetX       在目标坐标系（以目标的左上角为坐标原点的坐标系）中，相对于x点向右偏移offsetX个单位长度。
     * @param offsetY       在目标坐标系（以目标的左上角为坐标原点的坐标系）中，相对于y点向右偏移offsetY个单位长度。
     * @param attach        是否建立依附关系。如果是true表示希望和目标锚点建立依附关系，那么当目标ArtView坐标发生改变时，
     *                      本ArtView的位置也会跟着改变。如果是false表示不和目标锚点建立依附关系，也就不会随目标的坐标变化而变
     *                      化，通常可以用来首次确定自己在总坐标系中的位置。
     * @param followVisible 是否跟随target的可见性。只有attach为true时，该项才有效。
     */
    public final void anchor(ArtView target, float mX, float mY, float tX, float tY, float offsetX,
                             float offsetY, boolean attach, boolean followVisible) {
        if (attach) {
            detachAnchor();
            anchor = new Anchor(this, target, mX, mY, tX, tY, offsetX, offsetY, followVisible);
            anchor.setAttach(attach);
            target.addAnchor(anchor);
            anchor.changed();
            invalidate();
        } else {
            setXY(tX + offsetX - mX + target.x, tY + offsetY - mY + target.y);
        }
    }

    /**
     * 接触Anchor所建立的联系。
     */
    public void detachAnchor() {
        if (anchor != null && anchor.getT() != null) {
            anchor.getT().removeAnchor(anchor);
            anchor = null;
        }
    }

    protected void addAnchor(Anchor anchor) {
        if (anchorList == null) {
            this.anchorList = new ArrayList<Anchor>();
        }
        this.anchorList.add(anchor);
    }

    protected void removeAnchor(Anchor anchor) {
        if (anchorList != null) {
            this.anchorList.remove(anchor);
        }
    }

    protected void clearAnchor() {
        if (anchorList != null) {
            this.anchorList.clear();
        }
    }

    private void notifyAllAnchor() {
        if (anchorList != null) {
            Iterator<Anchor> iterator = anchorList.iterator();
            while (iterator.hasNext()) {
                iterator.next().notifyChanged();
            }
        }
    }

    public static interface OnTouchListener {
        boolean onTouch(ArtView artView, MotionEvent event);
    }

    public static interface OnClickListener {
        void onClick(ArtView artView);
    }

    public static interface OnLongClickListener {
        void onLongClick(ArtView artView);
    }

    protected static class Anchor {
        /**
         * leftMargin、topMargin
         */
        private static final int MARGIN_TYPE_F = 1;
        /**
         * rightMargin、bottomMargin
         */
        private static final int MARGIN_TYPE_R = 2;

        private float mX;
        private float mY;
        private float tX;
        private float tY;
        private float offsetX;
        private float offsetY;
        private boolean followVisible;

        private ArtView m;
        private ArtView t;
        private float tOldWidth;
        private float tOldHeight;
        private float mOldWidth;
        private float mOldHeight;

        private int xMarginType = -1;
        private int yMarginType = -1;
        private boolean attach = false;

        public Anchor(ArtView m, ArtView t, float mX, float mY, float tX, float tY, float offsetX,
                      float offsetY, boolean followVisible) {
            this.m = m;
            this.t = t;
            this.mX = mX;
            this.mY = mY;
            this.tX = tX;
            this.tY = tY;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.followVisible = followVisible;
            mOldWidth = m.getWidth();
            mOldHeight = m.getHeight();
            tOldWidth = t.getWidth();
            tOldHeight = t.getHeight();
        }

        public float getmX() {
            return mX;
        }

        public void setmX(float mX) {
            this.mX = mX;
        }

        public float getmY() {
            return mY;
        }

        public void setmY(float mY) {
            this.mY = mY;
        }

        public float gettX() {
            return tX;
        }

        public void settX(float tX) {
            this.tX = tX;
        }

        public float gettY() {
            return tY;
        }

        public void settY(float tY) {
            this.tY = tY;
        }

        public float getOffsetX() {
            return offsetX;
        }

        public void setOffsetX(float offsetX) {
            this.offsetX = offsetX;
        }

        public float getOffsetY() {
            return offsetY;
        }

        public void setOffsetY(float offsetY) {
            this.offsetY = offsetY;
        }

        public ArtView getM() {
            return m;
        }

        public ArtView getT() {
            return t;
        }

        public void notifyChanged() {
            if (m != null && t != null) {
                changed();
                m.notifyAllAnchor();
            }
        }

        public void changed() {
            if (m != null && t != null) {
                m.degrees = t.degrees;
                m.scaleX = t.scaleX;
                m.scaleY = t.scaleY;
                if (followVisible) {
                    m.visibility = t.visibility;
                }
                int tWidth = t.getWidth();
                int tHeight = t.getHeight();
                if (t.visibility == GONE) {
                    tWidth = 0 - t.getLeft();
                    tHeight = 0 - t.getTop();
                }
                float tOffsetWidth = tWidth - tOldWidth;
                float tOffsetHeight = tHeight - tOldHeight;
                if (offsetX != -1) {
                    if (xMarginType == MARGIN_TYPE_R) {
                        float mOffsetWidth = m.getWidth() - mOldWidth;
                        m.x = tX - tOffsetWidth / 2 + offsetX - (mX) + t.x - mOffsetWidth;
                    } else {
                        m.x = tX + offsetX - (mX) + t.x + tOffsetWidth;
                    }
                }
                if (offsetY != -1) {
                    if (yMarginType == MARGIN_TYPE_R) {
                        float mOffsetHeight = m.getHeight() - mOldHeight;
                        m.y = tY - tOffsetHeight / 2 + offsetY - (mY) + t.y - mOffsetHeight;
                    } else {
                        m.y = tY + offsetY - (mY) + t.y + tOffsetHeight;
                    }
                }
                m.left = (int) m.x;
                m.top = (int) m.y;
                m.right = m.left + m.getWidth();
                m.bottom = m.top + m.getHeight();
            }
        }

        public void setAttach(boolean attach) {
            this.attach = attach;
        }

        public boolean isAttach() {
            return attach;
        }
    }


}
