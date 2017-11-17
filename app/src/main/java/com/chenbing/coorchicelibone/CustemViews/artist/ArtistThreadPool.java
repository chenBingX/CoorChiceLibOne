package com.chenbing.coorchicelibone.CustemViews.artist;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by coorchice on 2017/11/8.
 */

public class ArtistThreadPool {

    private ScheduledExecutorService threadPool;

    private ArtistThreadPool(){
//        threadPool = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        threadPool = Executors.newSingleThreadScheduledExecutor();
    }

    private static final class Holder{
        private static final ArtistThreadPool instance = new ArtistThreadPool();
    }

    static ArtistThreadPool get(){
        return Holder.instance;
    }

    public static void close(){
        get().threadPool.shutdownNow();
    }

    public static void run(Runnable r){
        runDelay(r, 0l);
    }

    public static void runDelay(Runnable r, long delay){
        get().threadPool.schedule(r, delay, TimeUnit.MILLISECONDS);
    }
}
