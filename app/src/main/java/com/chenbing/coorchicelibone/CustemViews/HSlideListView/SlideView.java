package com.chenbing.coorchicelibone.CustemViews.HSlideListView;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:16/8/30
 * Notes:
 */
public class SlideView extends LinearLayout {

  private Scroller mScroller;
  private int lastX;
  private int lastY;

  public SlideView(Context context) {
    super(context);
    init();
  }

  public SlideView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();

  }

  public SlideView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public SlideView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  {
    setOrientation(HORIZONTAL);
  }

  private void init() {
    mScroller = new Scroller(getContext());
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }

  public void onRequireTouchEvent(MotionEvent event) {
    int x = (int) event.getX();
    int y = (int) event.getY();
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        abortScrollAnimation();
        break;
      case MotionEvent.ACTION_MOVE:
        onActionMove(x, y);
        break;
      case MotionEvent.ACTION_UP:
        onActionUp();
        break;
    }
    lastX = x;
    lastY = y;
  }

  private void abortScrollAnimation(){
    if (!mScroller.isFinished()) {
      mScroller.abortAnimation();
    }
  }

  private void onActionMove(int x, int y){
    int deltX = x - lastX;
    int deltY = y - lastY;
    if (Math.abs(deltX) < Math.abs(deltY))
      return;

    int scrollX = getScrollX();
    int newScrollX = scrollX - deltX;

    int hidViewWidth = (int) (getWidth() * 0.3);
    if (newScrollX > 0) {
      if (newScrollX <= hidViewWidth) {
        scrollTo(newScrollX, 0);
      }
    }
  }

  private void onActionUp(){
    int hidViewWidth = (int) (getWidth() * 0.3);
    int borderLine = (int) (hidViewWidth * 0.4);

    int scrollX = getScrollX();
    if (scrollX < borderLine) {
      int delta = 0 - scrollX;
      mScroller.startScroll(scrollX, 0, delta, 0, 1000);
    } else {
      int delta = hidViewWidth - scrollX;
      mScroller.startScroll(scrollX, 0, delta, 0, 1000);
    }

    invalidate(); // 刷新,否则不能马上回去
  }

  @Override
  public void computeScroll() {
    super.computeScroll();
    if (mScroller.computeScrollOffset()) {
      scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
      postInvalidate();
    }
  }


  public void shrink() {
    if (getScrollX() != 0) {
      mScroller.startScroll(0, 0, 0, 0);
    }
  }
}
