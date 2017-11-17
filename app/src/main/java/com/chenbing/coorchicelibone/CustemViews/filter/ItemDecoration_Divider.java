package com.chenbing.coorchicelibone.CustemViews.filter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by coorchice on 2017/11/14.
 */

public class ItemDecoration_Divider extends RecyclerView.ItemDecoration {


    private Paint dividerPaint = new Paint();
    private float dividerHeight = 0;
    private int dividerColor;

    public ItemDecoration_Divider(float dividerHeight, int dividerColor) {
        this.dividerHeight = dividerHeight;
        this.dividerColor = dividerColor;
        dividerPaint.setColor(dividerColor);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            float bottom = view.getTop();
            float top = bottom - dividerHeight;
            c.drawRect(left, top, right, bottom, dividerPaint);
            if (i == childCount - 1) {
                float top2 = view.getBottom();
                float bottom2 = top2 + dividerHeight;
                c.drawRect(left, top2, right, bottom2, dividerPaint);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top = (int) (dividerHeight);
        int position = parent.getChildLayoutPosition(view);
        if (position == parent.getChildCount() - 1) {
            outRect.bottom = (int) (dividerHeight);
        }

    }
}
