package com.chenbing.coorchicelibone.CustemViews.artist;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.compat.BuildConfig;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.chenbing.coorchicelibone.Utils.LogUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by coorchice on 2017/10/13.
 */

public class ArtistLayout extends View {

    public static final boolean CONFIG_ASYNC_DRAW = true;
    public static boolean DEBUG = false ? BuildConfig.DEBUG : false;
//    public static boolean DEBUG = true;

    private List<ArtView> childList = new ArrayList();
    private List<ArtView> consumingList = new ArrayList<>();

    private int width;
    private int height;
    protected float dp;
    protected float sp;

    private volatile long vsync = 16L;
    private AtomicBoolean vsyncDone = new AtomicBoolean(true);
    private volatile boolean needInvalidate = true;
    private Runnable handleInvalidate;
    private volatile boolean isAttachedToWindow;
    private Canvas artCanvas;
    private Bitmap artBitmap;
    private Bitmap cacheBitmap;
    private volatile long t;
    private boolean asyncDraw = CONFIG_ASYNC_DRAW;
    private Runnable handleAsyncDraw;

    private Handler handler;
    private Paint paint;

    private Point touchPoint = new Point();
    private boolean hasOnClickListener = false;

    private long lastStartDrawTime;
//    final ReentrantLock mSurfaceLock = new ReentrantLock();

    public ArtistLayout(Context context) {
        super(context);
        init(null);
    }

    public ArtistLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public ArtistLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ArtistLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
                        int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);

    }

    private void init(AttributeSet attrs) {
        if (isInEditMode()) {
            DEBUG = false;
            asyncDraw = false;
        }
        if (handler == null) {
            handler = new Handler(getContext().getMainLooper());
        }
        dp = getResources().getDisplayMetrics().density;
        sp = getResources().getDisplayMetrics().scaledDensity;
        if (handleInvalidate == null) {
            handleInvalidate = new Runnable() {
                @Override
                public void run() {
                    vsyncDone.set(true);
                    if (isAttachedToWindow) {
                        if (needInvalidate) {
                            needInvalidate = false;
                            if (asyncDraw) {
                                beginDispatchAsyncDraw();
                            } else {
                                ArtistLayout.super.postInvalidate();
                            }
                        }
                    }
                }
            };
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isAttachedToWindow = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isAttachedToWindow = false;
        if (handler != null) {
            handler.removeCallbacks(null);
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        initArtCanvas();
        if (asyncDraw) {
            initAsyncDraw();
        }
        for (int i = 0; i < childList.size(); i++) {
            childList.get(i).refreshPSize(width, height);
        }
    }

    private void initArtCanvas() {
        if (artBitmap == null) {
            artBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
        if (artCanvas == null) {
            artCanvas = new Canvas(artBitmap);
        } else {
            artCanvas.scale(width / artCanvas.getWidth(), height / artCanvas.getHeight());
        }
        if (paint == null) {
            paint = new Paint();
            paint.setFilterBitmap(true);
            paint.setAntiAlias(true);
            paint.setDither(true);
        }
    }

    private void initAsyncDraw() {
        if (handleAsyncDraw == null) {
            handleAsyncDraw = new Runnable() {
                @Override
                public void run() {
                    if (artCanvas != null) {
                        asyncDraw();
                    }
                }
            };
//            postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    postInvalidate();
//                }
//            }, vsync);
        }
    }

    private synchronized void asyncDraw() {
        drawToArtBitmap();

        if (cacheBitmap == null) {
            cacheBitmap = Bitmap.createBitmap(this.artBitmap);
            cacheBitmap.prepareToDraw();
            ArtistLayout.super.postInvalidate();
        } else if (!cacheBitmap.sameAs(artBitmap)) {
            int[] pixels = new int[artBitmap.getRowBytes() * artBitmap.getHeight()];
            artBitmap.getPixels(pixels, 0, artBitmap.getWidth(), 0, 0, artBitmap.getWidth(), artBitmap.getHeight());
            if (pixels.length > 0) {
                cacheBitmap.setPixels(pixels, 0, artBitmap.getWidth(), 0, 0, artBitmap.getWidth(), artBitmap.getHeight());
            }
            cacheBitmap.prepareToDraw();
            ArtistLayout.super.postInvalidate();
        }

    }

    private void drawToArtBitmap() {
        artCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        for (int i = 0; i < childList.size(); i++) {
            ArtView child = childList.get(i);
            child.draw(artCanvas);
        }
    }

    @Override
    protected final void onDraw(Canvas canvas) {

        if (DEBUG) {
            if (paint == null) {
                paint = new Paint();
                paint.setFilterBitmap(true);
                paint.setAntiAlias(true);
                paint.setDither(true);
            }
            int color = paint.getColor();
            paint.setColor(Color.RED);
            canvas.drawCircle(touchPoint.x, touchPoint.y, 2 * dp, paint);
            paint.setColor(color);
        }

        if (asyncDraw) {
            if (cacheBitmap != null) {
                canvas.drawBitmap(cacheBitmap, 0, 0, paint);
                LogUtils.e("id = " + hashCode() + "-> onDraw() -> cacheBitmap");
            } else if (artCanvas != null) {
                drawToArtBitmap();
                canvas.drawBitmap(artBitmap, 0, 0, paint);

            }
        } else if (artCanvas != null) {
            drawToArtBitmap();
            canvas.drawBitmap(artBitmap, 0, 0, paint);
            LogUtils.e("id = " + hashCode() + "-> onDraw() -> artCanvas");
        }
    }

    private void beginDispatchAsyncDraw() {
        long temp = System.currentTimeMillis();
        if (artCanvas != null && handleAsyncDraw != null && temp - t >= vsync) {
            t = temp;
            async(handleAsyncDraw);
            LogUtils.e("ThreadId = " + Thread.currentThread().getId());
        }
        LogUtils.e("被跳过绘制");
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
        if (l != null) {
            hasOnClickListener = true;
        } else {
            hasOnClickListener = false;
        }
    }

    @Override
    public final boolean onTouchEvent(MotionEvent event) {
        boolean hasConsume = false;
        int action = event.getAction();
        int actionMasked = action & MotionEvent.ACTION_MASK;
        if (actionMasked == MotionEvent.ACTION_DOWN && !hasOnClickListener) {
            for (int i = childList.size() - 1; i >= 0; i--) {
                ArtView child = childList.get(i);
                if (child.getVisibility() == ArtView.VISIBLE &&
                        child.getRect().contains((int) event.getX(), (int) event.getY())) {
                    hasConsume = child.onTouch(event);
                    if (hasConsume) {
                        consumingList.add(child);
                        break;
                    }
                }
            }
        } else {
            Iterator<ArtView> iterator = consumingList.iterator();
            while (iterator.hasNext()) {
                hasConsume = true;
                ArtView child = iterator.next();
                if (child.getVisibility() == ArtView.VISIBLE) {
                    child.onTouch(event);
                } else {
                    int realAction = event.getAction();
                    if (realAction != MotionEvent.ACTION_UP && realAction != MotionEvent.ACTION_CANCEL) {
                        event.setAction(MotionEvent.ACTION_CANCEL);
                        child.onTouch(event);
                        event.setAction(realAction);
                        iterator.remove();
                    }
                }
            }
            if (actionMasked == MotionEvent.ACTION_UP || actionMasked == MotionEvent.ACTION_CANCEL) {
                consumingList.clear();
            }
        }
        if (DEBUG) {
            touchPoint.set((int) event.getX(), (int) event.getY());
            super.postInvalidate();
        }
        if (hasConsume) {
            return true;
        }
        return super.onTouchEvent(event);
    }


    /**
     * 返回设备dp。
     *
     * @return
     */
    public float getDp() {
        return dp;
    }

    /**
     * 返回设备sp。
     *
     * @return
     */
    public float getSp() {
        return sp;
    }

    public boolean isAsyncDraw() {
        return asyncDraw;
    }

    public void setAsyncDraw(boolean asyncDraw) {
        this.asyncDraw = asyncDraw;
        invalidate();
    }

    int maxWidth = 0;
    int maxHeight = 0;

    /**
     * 同一个View只能添加到一个ArtistLayout中。否则抛出异常。见{@link ArtView#attach()}。
     *
     * @param view
     */
    public final void addView(ArtView view) {
        view.attach(this);
        childList.add(view);
        postInvalidate();
    }

    /**
     * 同一个View只能添加到一个ArtistLayout中。否则抛出异常。见{@link ArtView#attach()}。
     *
     * @param views
     */
    public final void addViews(List<ArtView> views) {
        childList.addAll(views);
        for (int i = 0; i < childList.size(); i++) {
            childList.get(i).attach(this);
        }
        postInvalidate();
    }

    public final void removeView(ArtView view) {
        view.detach();
        childList.remove(view);
        postInvalidate();
    }

    public final void clearViews() {
        for (int i = 0; i < childList.size(); i++) {
            childList.get(i).detach();
        }
        childList.clear();
        postInvalidate();
    }


    /**
     * 使用ArtistLayout内部的线程池来异步处理一个任务。比如初始化View。
     * 这个线程池会自动释放任务和销毁。
     *
     * @param task
     */
    public void async(Runnable task) {
        ArtistThreadPool.run(task);
//        asyncHandler.post(task);
    }

    public void asyncDelay(Runnable task, long delay) {
        ArtistThreadPool.runDelay(task, delay);
//        asyncHandler.postDelayed(task, delay);
    }

    /**
     * 检测ArtistLayout是否出现在一个窗口中。
     *
     * @return
     */
    public boolean isAttached() {
        return isAttachedToWindow;
    }

    @Override
    public Handler getHandler() {
        if (handler != null) {
            return handler;
        } else if (super.getHandler() != null) {
            return (handler = super.getHandler());
        }
        return null;
    }

    @Override
    public void postInvalidate() {
        needInvalidate = true;
        long tempTime = System.currentTimeMillis();
        if (tempTime - lastStartDrawTime >= vsync * 3) {
            vsyncDone.set(true);
        }
        if (vsyncDone.get()) {
            lastStartDrawTime = tempTime;
            vsyncDone.set(false);
            needInvalidate = false;
            if (asyncDraw) {
                LogUtils.e("id = " + hashCode() + "-> postInvalidate()");
                beginDispatchAsyncDraw();
            } else {
                invalidate();
            }
            getHandler().postDelayed(handleInvalidate, vsync);
        }
    }
}
