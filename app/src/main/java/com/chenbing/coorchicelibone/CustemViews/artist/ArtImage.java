package com.chenbing.coorchicelibone.CustemViews.artist;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.DrawableRes;
import android.view.Gravity;


import com.chenbing.iceweather.R;

import java.lang.reflect.Field;

/**
 * Created by coorchice on 2017/10/17.
 */

public class ArtImage extends ArtView {

    public static final int AUTO_FIT = 1;
    public static final int FIT_XY = 2;

    private String src;
    private int drawableId = -1;
    private Bitmap bitmap;
    private float bmpX, bmpY;
    private Resources resources;
    private Paint paint;

    private int scaleType = AUTO_FIT;

    ArtImage() {
        super();
        init();
    }

    public ArtImage(int width, int height) {
        super(width, height);
        init();
    }

    public ArtImage(@DrawableRes int drawableId) {
        this();
        this.drawableId = drawableId;
    }

    public ArtImage(Bitmap bitmap) {
        this();
        this.bitmap = bitmap;
    }


    private void init() {
        paint = getPaint();
        gravity = Gravity.CENTER;
    }

    @SuppressLint("DrawAllocation")
    @Override
    public void onDraw(Canvas canvas) {
        if (bitmap != null) {
            checkGravity();
            canvas.drawBitmap(bitmap, bmpX, bmpY, paint);
        }
    }

    @Override
    public void checkGravity() {
        float realTextX = this.x;
        float realTextY = this.y;
        final int majorGravity = gravity & Gravity.VERTICAL_GRAVITY_MASK;
        final int minorGravity = gravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK;

        if (getHeightSizeMode() == WRAP_CONTENT) {
            bmpY = realTextY + paddingTop;
        } else {
            switch (majorGravity) {
                case Gravity.BOTTOM:
                    realTextY = realTextY + getHeight() - bitmap.getHeight() + paddingTop - paddingBottom;
                    break;
                case Gravity.CENTER_VERTICAL:
                    realTextY = realTextY + getHeight() / 2 - (float) bitmap.getHeight() / 2 + paddingTop - paddingBottom;
                    break;
                case Gravity.TOP:
                default:
                    break;
            }
            bmpY = realTextY;
        }

        if (getWidthSizeMode() == WRAP_CONTENT) {
            bmpX = realTextX + paddingLeft;
        } else {
            switch (minorGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
                case Gravity.CENTER_HORIZONTAL:
                    realTextX = realTextX + getWidth() / 2 - (float) bitmap.getWidth() / 2 + paddingLeft - paddingRight;
                    break;
                case Gravity.RIGHT:
                    realTextX = realTextX + getWidth() - bitmap.getWidth() + paddingLeft - paddingRight;
                    break;

                case Gravity.LEFT:
                default:
                    break;
            }
            bmpX = realTextX;
        }
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
        try {
            Field idField = R.drawable.class.getDeclaredField(src);
            setDrawableId(idField.getInt(idField));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(@DrawableRes int drawableId) {
        this.drawableId = drawableId;
        if (resources != null) {
            bitmap = BitmapFactory.decodeResource(resources, drawableId);
        }
        checkSize();
        invalidate();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        checkSize();
        invalidate();
    }

    @Override
    public void setWidth(int width) {
        if (bitmap != null && width > 0) {
            bitmap = Bitmap.createScaledBitmap(bitmap, width, bitmap.getHeight(), true);
        }
        super.setWidth(width);
    }

    @Override
    public void setHeight(int height) {
        if (bitmap != null && height > 0) {
            bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), height, true);
        }
        super.setHeight(height);
    }

    @Override
    public void setSize(int width, int height) {
        if (bitmap != null && width > 0 && height > 0) {
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        }
        super.setSize(width, height);
    }

    @Override
    public void onAttach() {
        resources = getResources();
        if (drawableId != -1) {
            bitmap = BitmapFactory.decodeResource(resources, drawableId);
        }
        checkSize();
    }

    private void checkSize() {
        if (bitmap != null) {
            if (getWidthSizeMode() == WRAP_CONTENT) {
                makeWidth(((bitmap.getWidth() & ~MODE_MASK) + paddingLeft + paddingRight) | (WRAP_CONTENT & MODE_MASK));
                right = left + getWidth();
            }

            if (getHeightSizeMode() == WRAP_CONTENT) {
                makeHeight(((bitmap.getHeight() & ~MODE_MASK) + paddingTop + paddingBottom) | (WRAP_CONTENT & MODE_MASK));
                bottom = top + getHeight();
            }

            if (getWidth() > 0 && getHeight() > 0 &&
                    (getWidthSizeMode() != WRAP_CONTENT || getHeightSizeMode() != WRAP_CONTENT)) {
                scaleImage();
            }
            computeXYWithLayoutGravity();
        }
    }

    private void scaleImage() {
        switch (scaleType) {
            case AUTO_FIT:
                if (getWidth() < getHeight()) {
                    bitmap = Bitmap.createScaledBitmap(bitmap, getWidth(), (int) (bitmap.getHeight() * ((float) getWidth() / (float) bitmap.getWidth())), true);
                } else {
                    bitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * ((float) getHeight() / (float) bitmap.getHeight())), getHeight(), true);
                }
                break;
            case FIT_XY:
                bitmap = Bitmap.createScaledBitmap(bitmap, getWidth(), getHeight(), true);
                break;
        }
    }
}
