package com.chenbing.coorchicelibone.CustemViews;

import java.util.ArrayList;
import java.util.List;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chenbing.iceweather.R;

/**
 * @author coorchice
 * @date 2018/08/03
 */

public class SimpleTabLayout extends LinearLayout {

    private LinearLayout tabContainer;
    private View indicator;

    private int selectedColor = Color.parseColor("#3d3d3d");
    private int unselectedColor = Color.parseColor("#3d3d3d");
    private float tabTextSize = 14f;
    /**
     * 当前选中的Tab
     */
    private Tab curTab;
    private List<Tab> tabList = new ArrayList<>();
    private OnTabSelectedListener onTabSelectedListener;
    private OnTabReleasedListener onTabReleasedListener;
    private static float density = Resources.getSystem().getDisplayMetrics().density;
    private boolean isFirst = true;

    public SimpleTabLayout(Context context) {
        super(context);
        init(null);
    }

    public SimpleTabLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public SimpleTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        setOrientation(VERTICAL);
        tabContainer = new LinearLayout(getContext());
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tabContainer.setOrientation(HORIZONTAL);
        addView(tabContainer, lp);

        indicator = new View(getContext());
        indicator.setBackgroundColor(Color.parseColor("#ffc900"));
        lp = new LayoutParams(0, dipToPx(3));
        addView(indicator, lp);

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SimpleTabLayout);
            selectedColor = typedArray.getColor(R.styleable.SimpleTabLayout_tabSelectedColor,
                Color.parseColor("#3d3d3d"));
            unselectedColor = typedArray.getColor(R.styleable.SimpleTabLayout_tabUnselectedColor,
                Color.parseColor("#3d3d3d"));
            Drawable drawable = typedArray.getDrawable(R.styleable.SimpleTabLayout_indicator);
            if (drawable != null) {
                indicator.setBackground(drawable);
            }
            tabTextSize = typedArray.getDimension(R.styleable.SimpleTabLayout_tabTextSize, 14);
            typedArray.recycle();
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isInEditMode() && isFirst) {
            isFirst = false;
            tabList.clear();
            addTab(new Tab("测试Tab1"));
            addTab(new Tab("测试Tab2"));
            addTab(new Tab("测试测试Tab2"));
        }
    }

    /**
     * 设置指示器颜色
     *
     * @param color 颜色
     */
    public void setIndicatorColor(int color) {
        indicator.setBackgroundColor(color);
    }

    /**
     * 设置指示器背景图
     *
     * @param drawableId drawable资源
     */
    public void setIndicatorDrawable(@DrawableRes int drawableId) {
        indicator.setBackgroundResource(drawableId);
    }

    /**
     * 设置被选中项的颜色
     *
     * @param color
     */
    public void setSelectedTextColor(int color) {
        selectedColor = color;
        update();
    }

    /**
     * 设置tab的字体大小
     *
     * @param textSize
     */
    public void setTabTextSize(float textSize) {
        this.tabTextSize = textSize;
        update();
    }

    /**
     * 设置一个Tab数组。会清空之前的Tab
     *
     * @param tabs
     */
    public void setTabs(List<Tab> tabs) {
        tabList.clear();
        if (tabs != null && !tabs.isEmpty()) {
            tabList.addAll(tabs);
        }
        update();
    }

    /**
     * 添加一个Tab
     *
     * @param tab
     */
    public void addTab(Tab tab) {
        if (tab != null) {
            tabList.add(tab);
            update();
        }
    }

    private void update() {
        tabContainer.removeAllViews();
        curTab = null;
        indicator.setTranslationX(0);
        initTabs();
    }

    private void initTabs() {
        int tvHeight = getMeasuredHeight() - dipToPx(3);
        if (getMeasuredHeight() == 0) {
            getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    int tvHeight = getMeasuredHeight() - dipToPx(3);
                    for (int i = 0; i < tabList.size(); i++) {
                        View tabView = tabList.get(i).getTabView();
                        if (tabView != null && tabView.getMeasuredHeight() != getMeasuredHeight()) {
                            LayoutParams lp = new LayoutParams(0, tvHeight);
                            lp.weight = 1;
                            tabView.setLayoutParams(lp);
                        }
                    }
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            });
        }
        for (int i = 0; i < tabList.size(); i++) {
            final Tab tab = tabList.get(i);
            final TextView tv = new TextView(getContext());
            LayoutParams lp = new LayoutParams(0, tvHeight);
            lp.weight = 1;
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(tabTextSize);
            tv.setTextColor(unselectedColor);
            tv.setText(tab.tabText);
            tv.setLines(1);
            tv.setPadding(dipToPx(2), 0, dipToPx(2), 0);
            tv.setEllipsize(TruncateAt.END);
            TextPaint paint = tv.getPaint();
            final int indicatorWidth = (int)(paint.measureText((String)tv.getText()) + dipToPx(4));
            final int index = i;
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (curTab != tab) {
                        resetTabState();
                        if (onTabReleasedListener != null && curTab != null) {
                            onTabReleasedListener.onTabReleased(curTab.getTabView(), curTab.getIndex());
                        }

                        curTab = tab;
                        if (tab.getPageView() != null) {
                            tab.getPageView().setVisibility(VISIBLE);
                        }
                        TextView tv = (TextView)v;
                        tv.setTextColor(selectedColor);
                        int tvWidth = tv.getMeasuredWidth();
                        int tvLeft = tv.getLeft();
                        int indicatorX = tvLeft + tvWidth / 2 - indicatorWidth / 2;
                        playIndicatorAnim(indicatorWidth, (int)(indicatorX - indicator.getTranslationX()));

                        if (onTabSelectedListener != null) {
                            onTabSelectedListener.onTabSelected(v, index);
                        }
                    }
                }
            });
            tabContainer.addView(tv, lp);
            tab.setTabView(tv);
            tab.setIndex(i);
            if (tab.getPageView() != null) {
                tab.getPageView().setVisibility(GONE);
            }
            // 初始化选中第0项
            if (i == 0 && indicator.getLayoutParams() != null) {
                curTab = tab;
                tv.setTextColor(selectedColor);
                ViewGroup.LayoutParams indicatorLp = indicator.getLayoutParams();
                indicatorLp.width = indicatorWidth;
                indicator.setLayoutParams(indicatorLp);
                tv.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int tvWidth = tv.getMeasuredWidth();
                        int tvLeft = tv.getLeft();
                        int indicatorX = tvLeft + tvWidth / 2 - indicatorWidth / 2;
                        indicator.setTranslationX(indicatorX);
                        if (tab.getPageView() != null && tab.getPageView().getVisibility() == GONE) {
                            tab.getPageView().setVisibility(VISIBLE);
                        }
                        if (onTabSelectedListener != null) {
                            onTabSelectedListener.onTabSelected(tv, 0);
                        }
                        tv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });

                if (isInEditMode()) {
                    tv.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft,
                                                   int oldTop, int oldRight,
                                                   int oldBottom) {
                            int tvWidth = tv.getMeasuredWidth();
                            int tvLeft = tv.getLeft();
                            int indicatorX = tvLeft + tvWidth / 2 - indicatorWidth / 2;
                            indicator.setTranslationX(indicatorX);
                            tv.removeOnLayoutChangeListener(this);
                        }
                    });
                }
            }

        }
    }

    private void playIndicatorAnim(final int indicatorWidth, final int moveDistance) {
        ValueAnimator anim1 = ValueAnimator.ofInt(indicator.getMeasuredWidth(), indicatorWidth);
        anim1.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams lp = indicator.getLayoutParams();
                lp.width = (int)animation.getAnimatedValue();
                indicator.requestLayout();
            }
        });
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(indicator, View.TRANSLATION_X, indicator.getTranslationX(),
            indicator.getTranslationX() + moveDistance);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(500);
        set.playTogether(anim1, anim2);
        set.start();
    }

    private void resetTabState() {
        for (int i = 0; i < tabList.size(); i++) {
            Tab tab = tabList.get(i);
            View tabView = tab.getTabView();
            if (tabView != null) {
                ((TextView)tabView).setTextColor(unselectedColor);
            }
            View pageView = tab.getPageView();
            if (pageView != null) {
                pageView.setVisibility(GONE);
            }
        }
    }

    /**
     * 选中指定位置的Tab，如果index不在tabList范围内，什么也不会发生
     *
     * @param index
     */
    public void selectTab(final int index) {
        post(new Runnable() {
            @Override
            public void run() {
                if (!tabList.isEmpty() && index < tabList.size()) {
                    View tabView = tabList.get(index).getTabView();
                    if (tabView != null) {
                        tabView.performClick();
                    }
                }
            }
        });

    }

    private int dipToPx(float dip) {
        return (int)(dip * density + 0.5f);
    }

    /**
     * 设置Tab被选中的监听
     *
     * @param onTabSelectedListener
     */
    public void setOnTabSelectedListener(
        OnTabSelectedListener onTabSelectedListener) {
        this.onTabSelectedListener = onTabSelectedListener;
    }

    /**
     * 设置Tab由选中变为未选中的监听
     *
     * @param onTabReleasedListener
     */
    public void setOnTabReleasedListener(
        OnTabReleasedListener onTabReleasedListener) {
        this.onTabReleasedListener = onTabReleasedListener;
    }

    /**
     * Tab对象，必须包含Tab标题，而且可以直接绑定关联的View
     */
    public static class Tab {
        private String tabText;
        private View tabView;
        private View pageView;
        private int index;

        public Tab(String tabText) {
            this.tabText = tabText;
        }

        public String getTabText() {
            return tabText;
        }

        /**
         * 设置Tab标题
         *
         * @param tabText
         * @return
         */
        public Tab setTabText(String tabText) {
            this.tabText = tabText;
            return this;
        }

        private View getTabView() {
            return tabView;
        }

        private Tab setTabView(View tabView) {
            this.tabView = tabView;
            return this;
        }

        public View getPageView() {
            return pageView;
        }

        /**
         * 设置该Tab关联的View页面
         *
         * @param pageView
         * @return
         */
        public Tab setPageView(View pageView) {
            this.pageView = pageView;
            return this;
        }

        private int getIndex() {
            return index;
        }

        private void setIndex(int index) {
            this.index = index;
        }
    }

    public static interface OnTabSelectedListener {
        /**
         * 当一个Tab被选中时会回调
         *
         * @param tab   被选中的TabView
         * @param index tab的位置
         */
        void onTabSelected(View tab, int index);
    }

    public static interface OnTabReleasedListener {
        /**
         * 当一个Tab由选中变为未选中时会回调
         *
         * @param tab   被选中的TabView
         * @param index tab的位置
         */
        void onTabReleased(View tab, int index);
    }
}
