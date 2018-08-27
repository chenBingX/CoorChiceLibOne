package com.chenbing.coorchicelibone.custom_adjusters;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

import com.coorchice.library.SuperTextView;

/**
 * @author coorchice
 * @date 2018/08/27
 */

public class SwitchAdjuster extends SuperTextView.Adjuster {

    private float location = 0;
    private Paint paint;
    private int color = Color.WHITE;
    private ValueAnimator va;
    private GestureDetector gestureDetector;
    private boolean autoSwitch;

    public SwitchAdjuster() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
    }

    @Override
    protected void adjust(SuperTextView v, Canvas canvas) {
        paint.setColor(color);
        float density = v.getContext().getResources().getDisplayMetrics().density;
        int width = v.getMeasuredWidth();
        int height = v.getMeasuredHeight();
        int padding = (int) (1 * density);
        int r = (height - 2 * padding) / 2;
        if (va == null || !va.isRunning()) {
            if (v.isSelected()) {
                location = v.getMeasuredWidth() - 2 * padding - 2 * r;
            } else {
                location = 0;
            }
        }
        int centerX = (int) (padding + r + location);
        int centerY = padding + r;
        canvas.drawCircle(centerX, centerY, r, paint);
    }

    @Override
    public boolean onTouch(SuperTextView v, MotionEvent event) {
        if (!autoSwitch) {
            return false;
        }
        if (gestureDetector == null) {
            gestureDetector = new GestureDetector(v.getContext(), new SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    v.setSelected(!v.isSelected());
                    open(v, v.isSelected());
                    return true;
                }
            });
        }
        gestureDetector.onTouchEvent(event);
        return true;
    }

    private void open(SuperTextView v, boolean open) {
        if (va != null && va.isRunning()) {
            va.cancel();
        }
        float density = v.getContext().getResources().getDisplayMetrics().density;
        int padding = (int) (1 * density);
        int height = v.getMeasuredHeight();
        int r = (height - 2 * padding) / 2;
        float start = location;
        float end = v.getMeasuredWidth() - 2 * padding - 2 * r;
        if (!open) {
            start = location;
            end = 0;
        }
        va = ValueAnimator.ofFloat(start, end);
        va.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                location = (float) animation.getAnimatedValue();
                v.invalidate();
            }
        });
        va.setDuration(500);
        va.start();
    }
}
