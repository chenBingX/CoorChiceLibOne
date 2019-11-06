package com.chenbing.coorchicelibone.Views;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.chenbing.coorchicelibone.Utils.AppUtils;
import com.chenbing.coorchicelibone.Utils.LogUtils;
import com.chenbing.coorchicelibone.Utils.ThreadPool;
import com.chenbing.coorchicelibone.gifdecoder.GifCache;
import com.chenbing.coorchicelibone.gifdecoder.GifDecoder;
import com.chenbing.coorchicelibone.gifdecoder.GifDrawable;
import com.chenbing.iceweather.R;
import com.coorchice.library.SuperTextView;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class GifDecoderActivity extends AppCompatActivity {

    private SuperTextView stv_start;
    private ImageView gif, gif_1, gif_2, gif_3;
    private List<GifDecoder> gifDecoders = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_decoder);

        AppUtils.checkPermission(this, 1, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        stv_start = (SuperTextView) findViewById(R.id.stv_start);
        gif = (ImageView) findViewById(R.id.gif);
        gif_1 = (ImageView) findViewById(R.id.gif_1);
        gif_2 = (ImageView) findViewById(R.id.gif_2);
        gif_3 = (ImageView) findViewById(R.id.gif_3);

        ThreadPool.run(new Runnable() {
            @Override
            public void run() {
                GifDrawable gifDrawable = GifCache.getInstance().get(R.drawable.gif_m_7);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gif.setImageDrawable(gifDrawable);
                    }
                });
            }
        });

        stv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gif_1.setImageDrawable(GifCache.getInstance().get(R.drawable.gif_m_7));
                gif_2.setImageDrawable(GifCache.getInstance().get(R.drawable.gif_m_7));
                gif_3.setImageDrawable(GifCache.getInstance().get(R.drawable.gif_m_7));
            }
        });
    }
}
