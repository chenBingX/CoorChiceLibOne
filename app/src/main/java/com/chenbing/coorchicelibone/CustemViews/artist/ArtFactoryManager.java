package com.taobao.trip.flight.widget.artist;

import com.chenbing.coorchicelibone.CustemViews.artist.ArtFactory;
import com.chenbing.coorchicelibone.CustemViews.artist.ArtView;
import com.chenbing.coorchicelibone.CustemViews.artist.ArtistLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by coorchice on 2017/10/19.
 */

public class ArtFactoryManager {

    private ExecutorService parserThread;

    private ArtFactoryManager() {

    }

    public static ArtFactoryManager get() {
        return ArtFactoryManagerHolder.instance;
    }

    private static final class ArtFactoryManagerHolder {
        private static final ArtFactoryManager instance = new ArtFactoryManager();
    }

    public List<ArtView> parser(String artViewJson) {
        List<ArtView> artViews = new ArrayList<>();
        List<ArtFactory> artFactories = new Gson().fromJson(artViewJson, new TypeToken<List<ArtFactory>>() {
        }.getType());
        for (ArtFactory factory : artFactories) {
            artViews.add(factory.build());
        }
        return artViews;
    }

    public void parser(final ArtistLayout container, final String artViewJson) {
        List<ArtView> parser = parser(artViewJson);
        for (ArtView view : parser) {
            container.addView(view);
        }
    }

    public void asyncParser(final ArtistLayout container, final String artViewJson) {
        if (parserThread == null) {
            parserThread = Executors.newSingleThreadExecutor();
        }
        parserThread.execute(new Runnable() {
            @Override
            public void run() {
                parser(container, artViewJson);
            }
        });
    }
}
