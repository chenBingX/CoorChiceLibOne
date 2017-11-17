package com.chenbing.coorchicelibone.CustemViews.artist;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.Gravity;

import static android.graphics.Canvas.ALL_SAVE_FLAG;

/**
 * Created by coorchice on 2017/10/16.
 */

public class ArtText extends ArtView {

    public static final float DEFAULT_TEXT_PADDING = 3.5f;

    private boolean textPaddingEnable = true;
    private float textPadding;
    private String text = "";
    private int stringId = -1;
    private float textSize = 12f;
    private int textColor = Color.BLACK;
    private Typeface textStyle = Typeface.DEFAULT;
    private Paint paint;
    private int maxWidth = -1;

    private Paint.FontMetrics fontMetrics;
    private Rect textRect;

    private Resources resources;
    /**
     * 单位文字的高
     */
    private float textHeight;
    /**
     * 单位文字的宽
     */
    private float textWidth;
    private float textX;
    private float textY;

    ArtText() {
        super();
        init();
    }

    public ArtText(int width, int height) {
        super(width, height);
        init();
    }

    public ArtText(String text) {
        this();
        if (TextUtils.isEmpty(text)) {
            text = "";
        }
        this.text = text;
    }

    public ArtText(@IdRes int stringId) {
        this();
        this.stringId = stringId;
    }

    private void init() {
        checkTextPadding();
        if (paint == null) {
            paint = getPaint();
        }
        paint.setTextSize(textSize * getSp());
        paint.setColor(textColor);
        paint.setTypeface(textStyle);

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        this.fontMetrics = fontMetrics;
        textHeight = fontMetrics.descent - fontMetrics.ascent;

        if (textRect == null) {
            textRect = new Rect();
        }
        if (text != null) {
            paint.getTextBounds(text, 0, text.length(), textRect);
            textWidth = textRect.width();
        }
        right = left + textRect.width();
        bottom = top + textRect.height();
    }

    private void checkTextPadding() {
        if (textPaddingEnable) {
            textPadding = DEFAULT_TEXT_PADDING * getDensity();
        } else {
            textPadding = 0;
        }
    }


    @Override
    public void onDraw(Canvas canvas) {
        String text = autoLengthText(this.text);
        checkTextXY();
        Rect rect = getRect();
        canvas.saveLayer(rect.left, rect.top, rect.right, rect.bottom, paint, ALL_SAVE_FLAG);
        canvas.drawText(text, textX, textY, paint);
        canvas.restore();
    }

    private String autoLengthText(String text) {
        if (TextUtils.isEmpty(text)) {
            return "";
        }
        int length = text.length();
        int totalWidth = getWidth();
        if (maxWidth != -1 && getWidth() > maxWidth) {
            totalWidth = maxWidth;
        }
        if (length > 0 && textWidth > totalWidth) {
            float charWidth = textWidth / length;
            int usableLength = (int) (totalWidth / charWidth);
            int endIndex = (usableLength - 1) > 0 ? usableLength - 1 : 1;
            return text.substring(0, endIndex) + "...";
        }
        return text;
    }

    private void checkTextXY() {
        checkGravity();
    }

    public String getText() {
        return text;
    }

    @Override
    public void checkGravity() {
        float realTextX = this.x + textPadding * 0.4f;
        float realTextY = this.y - 2 * fontMetrics.ascent + fontMetrics.top + textPadding * 0.5f;
        final int majorGravity = gravity & Gravity.VERTICAL_GRAVITY_MASK;
        final int minorGravity = gravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK;

        if (getHeightSizeMode() == WRAP_CONTENT) {
            textY = realTextY + paddingTop;
        } else {
            switch (majorGravity) {
                case Gravity.BOTTOM:
                    realTextY = realTextY + getHeight() - textHeight + paddingTop - paddingBottom;
                    break;
                case Gravity.CENTER_VERTICAL:
                    realTextY = realTextY + getHeight() / 2 - textHeight / 2 + paddingTop - paddingBottom;
                    break;
                case Gravity.TOP:
                default:
                    break;
            }
            textY = realTextY;
        }

        if (getWidthSizeMode() == WRAP_CONTENT) {
            textX = realTextX + paddingLeft;
        } else {
            switch (minorGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
                case Gravity.CENTER_HORIZONTAL:
                    realTextX = realTextX + getWidth() / 2 - textWidth / 2 + paddingLeft - paddingRight;
                    break;
                case Gravity.RIGHT:
                    realTextX = realTextX + getWidth() - textWidth + paddingLeft - paddingRight;
                    break;

                case Gravity.LEFT:
                default:
                    break;
            }
            textX = realTextX;
        }
    }

    public void setText(String text) {
        if (TextUtils.isEmpty(text)) {
            text = "";
        }
        this.text = text;
        setTextSize(textSize);
//        invalidate();
    }

    public void setText(@StringRes int resId) {
        this.stringId = resId;
        if (resources != null) {
            text = resources.getString(resId);
        } else {
            text = "";
        }
        setTextSize(textSize);
//        invalidate();
    }

    public float getTextSize() {
        return textSize;
    }

    /**
     * 设置文字大小
     *
     * @param textSize 文字大小，单位sp
     */
    public void setTextSize(float textSize) {
        checkTextPadding();
        this.textSize = textSize;
        paint.setTextSize(textSize * getSp());
        computeViewSize(paint);
        invalidate();
    }

    private void computeViewSize(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        this.fontMetrics = fontMetrics;
        textHeight = fontMetrics.descent - fontMetrics.ascent;

        if (text != null) {
            paint.getTextBounds(text, 0, text.length(), textRect);
            textWidth = textRect.width();
        }
        right = (int) (left + textRect.width() + textPadding * 0.6f);
        bottom = (int) (top + textRect.height() + textPadding * 0.5f);
        if (getWidthSizeMode() == WRAP_CONTENT) {
            float width = textWidth + textPadding + paddingRight + paddingLeft;
            if (maxWidth != -1 && width > maxWidth) {
                width = maxWidth;
            }
            right = left + (int) width;
            makeWidth(((int) width & ~MODE_MASK) | (WRAP_CONTENT & MODE_MASK));
        }
        if (getHeightSizeMode() == WRAP_CONTENT) {
            bottom += paddingTop + paddingBottom;
            makeHeight(((int) (textRect.height() + textPadding + paddingTop + paddingBottom) & ~MODE_MASK) | (WRAP_CONTENT & MODE_MASK));
        }
        computeXYWithLayoutGravity();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        paint.setColor(textColor);
        invalidate();
    }

    public void setTextColor(String color) {
        setTextColor(Color.parseColor(color));
    }

    public Typeface getTextStyle() {
        return textStyle;
    }

    /**
     * 设置字体。
     *
     * @param textStyle
     */
    public void setTextStyle(Typeface textStyle) {
        this.textStyle = textStyle;
        paint.setTypeface(textStyle);
        invalidate();
    }


    /**
     * 获取文字的高。
     *
     * @return
     */
    public float getTextHeight() {
        return textHeight;
    }

    /**
     * 获取文字的宽。
     *
     * @return
     */
    public float getTextWidth() {
        return textWidth;
    }

    public Rect getTextRect() {
        return textRect;
    }

    @Override
    public void setPadding(int padding) {
        super.setPadding(padding);
        setTextSize(textSize);
    }

    @Override
    public void setPaddingLeft(int paddingLeft) {
        super.setPaddingLeft(paddingLeft);
        setTextSize(textSize);
    }

    @Override
    public void setPaddingTop(int paddingTop) {
        super.setPaddingTop(paddingTop);
        setTextSize(textSize);
    }

    @Override
    public void setPaddingRight(int paddingRight) {
        super.setPaddingRight(paddingRight);
        setTextSize(textSize);
    }

    @Override
    public void setPaddingBottom(int paddingBottom) {
        super.setPaddingBottom(paddingBottom);
        setTextSize(textSize);
    }

    /**
     * 获取字体信息。
     *
     * @return
     */
    public Paint.FontMetrics getFontMetrics() {
        return fontMetrics;
    }

    public boolean isTextPaddingEnable() {
        return textPaddingEnable;
    }

    /**
     * 设置是否开启文字默认边距。在文字测量模式为{@link ArtView#WRAP_CONTENT}时，会有一个默认边距。
     *
     * @param textPaddingEnable
     */
    public void setTextPaddingEnable(boolean textPaddingEnable) {
        this.textPaddingEnable = textPaddingEnable;
        checkTextPadding();
        invalidate();
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        setTextSize(textSize);
    }

    @Override
    public void onAttach() {
        resources = getResources();
        if (stringId != -1) {
            text = resources.getString(stringId);
        }
        setTextSize(textSize);
    }
}
