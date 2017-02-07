package com.chenbing.coorchicelibone.Managers;

import com.chenbing.coorchicelibone.Utils.DisplayUtils;
import com.chenbing.coorchicelibone.Utils.LogUtils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/10/18
 * Notes:
 */

public class CustomLayoutManager extends RecyclerView.LayoutManager {
  /** 用于保存item的位置信息 */
  private SparseArray<Rect> allItemRects = new SparseArray<>();
  /** 用于保存item是否处于可见状态的信息 */
  private SparseBooleanArray itemStates = new SparseBooleanArray();

  public int totalHeight = 0;
  private int verticalScrollOffset;

  @Override
  public RecyclerView.LayoutParams generateDefaultLayoutParams() {
    return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
  }

  @Override
  public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
    if (getItemCount() <= 0 || state.isPreLayout()) {
      return;
    }
    super.onLayoutChildren(recycler, state);
    detachAndScrapAttachedViews(recycler);
    /* 这个方法主要用于计算并保存每个ItemView的位置 */
    calculateChildrenSite(recycler);
    recycleAndFillView(recycler, state);
  }

  private void calculateChildrenSite(RecyclerView.Recycler recycler) {
    totalHeight = 0;
    for (int i = 0; i < getItemCount(); i++) {
      View view = recycler.getViewForPosition(i);
      addView(view);
      // 我们自己指定ItemView的尺寸。
      measureChildWithMargins(view, DisplayUtils.getScreenWidth() / 2, 0);
      calculateItemDecorationsForChild(view, new Rect());
      int width = getDecoratedMeasuredWidth(view);
      int height = getDecoratedMeasuredHeight(view);

      Rect mTmpRect = allItemRects.get(i);
      if (mTmpRect == null) {
        mTmpRect = new Rect();
      }

      if (i % 2 == 0) { // 当i能被2整除时，是左，否则是右。
        // 左
        mTmpRect.set(0, totalHeight, DisplayUtils.getScreenWidth() / 2, totalHeight + height);
      } else {
        // 右，需要换行
        mTmpRect.set(DisplayUtils.getScreenWidth() / 2, totalHeight, DisplayUtils.getScreenWidth(),
            totalHeight + height);
        totalHeight = totalHeight + height;
      }

      // 保存ItemView的位置信息
      allItemRects.put(i, mTmpRect);
      // 由于之前调用过detachAndScrapAttachedViews(recycler)，所以此时item都是不可见的
      itemStates.put(i, false);
    }
  }


  private void recycleAndFillView(RecyclerView.Recycler recycler, RecyclerView.State state) {
    if (getItemCount() <= 0 || state.isPreLayout()) {
      return;
    }

    // 当前scroll offset状态下的显示区域
    Rect displayRect= new Rect(0, verticalScrollOffset, getHorizontalSpace(),
        verticalScrollOffset + getVerticalSpace());

    /**
     * 将滑出屏幕的Items回收到Recycle缓存中
     */
    Rect childRect = new Rect();
    for (int i = 0; i < getChildCount(); i++) {
      //这个方法获取的是RecyclerView中的View，注意区别Recycler中的View
      //这获取的是实际的View
      View child = getChildAt(i);
      //下面几个方法能够获取每个View占用的空间的位置信息，包括ItemDecorator
      childRect.left = getDecoratedLeft(child);
      childRect.top = getDecoratedTop(child);
      childRect.right = getDecoratedRight(child);
      childRect.bottom = getDecoratedBottom(child);
      //如果Item没有在显示区域，就说明需要回收
      if (!Rect.intersects(displayRect, childRect)) {
        //移除并回收掉滑出屏幕的View
        removeAndRecycleView(child, recycler);
        itemStates.put(i, false); //更新该View的状态为未依附
      }
    }

    //重新显示需要出现在屏幕的子View
    for (int i = 0; i < getItemCount(); i++) {
      //判断ItemView的位置和当前显示区域是否重合
      if (Rect.intersects(displayRect, allItemRects.get(i))) {
        //获得Recycler中缓存的View
        View itemView = recycler.getViewForPosition(i);
        measureChildWithMargins(itemView, DisplayUtils.getScreenWidth() / 2, 0);
        //添加View到RecyclerView上
        addView(itemView);
        //取出先前存好的ItemView的位置矩形
        Rect rect = allItemRects.get(i);
        //将这个item布局出来
        layoutDecoratedWithMargins(itemView,
          rect.left,
          rect.top - verticalScrollOffset,  //因为现在是复用View，所以想要显示在
          rect.right,
          rect.bottom - verticalScrollOffset);
        itemStates.put(i, true); //更新该View的状态为依附
      }
    }
    LogUtils.e("itemCount = " + getChildCount());
  }


  @Override
  public boolean canScrollVertically() {
    // 返回true表示可以纵向滑动
    return true;
  }

  @Override
  public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
    //每次滑动时先释放掉所有的View，因为后面调用recycleAndFillView()时会重新addView()。
    detachAndScrapAttachedViews(recycler);
    // 列表向下滚动dy为正，列表向上滚动dy为负，这点与Android坐标系保持一致。
    // 实际要滑动的距离
    int travel = dy;

    LogUtils.e("dy = " + dy);
    // 如果滑动到最顶部
    if (verticalScrollOffset + dy < 0) {
      travel = -verticalScrollOffset;
    } else if (verticalScrollOffset + dy > totalHeight - getVerticalSpace()) {// 如果滑动到最底部
      travel = totalHeight - getVerticalSpace() - verticalScrollOffset;
    }
    // 调用该方法通知view在y方向上移动指定距离
    offsetChildrenVertical(-travel);
    recycleAndFillView(recycler, state); //回收并显示View
    // 将竖直方向的偏移量+travel
    verticalScrollOffset += travel;
    return travel;
  }

  private int getVerticalSpace() {
    // 计算RecyclerView的可用高度，除去上下Padding值
    return getHeight() - getPaddingBottom() - getPaddingTop();
  }

  @Override
  public boolean canScrollHorizontally() {
    // 返回true表示可以横向滑动
    return super.canScrollHorizontally();
  }

  @Override
  public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler,
      RecyclerView.State state) {
    // 在这个方法中处理水平滑动
    return super.scrollHorizontallyBy(dx, recycler, state);
  }

  public int getHorizontalSpace() {
    return getWidth() - getPaddingLeft() - getPaddingRight();
  }
}
