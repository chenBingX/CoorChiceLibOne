package com.chenbing.coorchicelibone.Views;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.chenbing.coorchicelibone.Utils.AppUtils;
import com.chenbing.coorchicelibone.Utils.LogUtils;
import com.chenbing.coorchicelibone.gifdecoder.GifDecoder;
import com.chenbing.iceweather.R;
import com.coorchice.library.SuperTextView;
import com.coorchice.library.utils.ThreadPool;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class GifDecoderActivity extends AppCompatActivity {

    private SuperTextView stvStart;
    private ImageView gif, gif_1, gif_2, gif_3;
    private List<GifDecoder> gifDecoders = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_decoder);

        AppUtils.checkPermission(this, 1, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        findView();
        stvStart.setOnClickListener((v) -> {
                ThreadPool.run(() -> {
                    createOne(gif, R.drawable.gif_4);
                    createOne(gif_1, R.drawable.gif_4);
                    createOne(gif_2, R.drawable.gif_4);
                    createOne(gif_3, R.drawable.gif_4);
                });
        });
    }

    private void createOne(ImageView v, @DrawableRes int resId){
        try {
//            String gifPath = Environment.getExternalStorageDirectory().getPath() + "/gif_4.gif";
//            FileInputStream is = new FileInputStream(new File(gifPath));
          InputStream is = getResources().openRawResource(resId);
          byte[] bytes = new byte[is.available()];
          is.read(bytes);
          LogUtils.e(String.format("bytes.length = %d", bytes.length));
            v.post(()->{
              GifDecoder gifDecoder = GifDecoder.openBytes(bytes);
//              GifDecoder gifDecoder = GifDecoder.openFile(gifPath);
              gifDecoders.add(gifDecoder);
              showGif(v, gifDecoder, 0);
          });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showGif(ImageView v, GifDecoder gifDecoder, int delay) {
        v.postDelayed(() -> {
            if (gifDecoder == null || gifDecoder.isDestroy()) return;
            int d = gifDecoder.updateFrame();
            v.setImageBitmap(gifDecoder.getBitmap());
            showGif(v, gifDecoder, d);
        }, delay);
    }

    private void findView() {
        stvStart = findViewById(R.id.stv_start);
        gif = findViewById(R.id.gif);
        gif_1 = findViewById(R.id.gif_1);
        gif_2 = findViewById(R.id.gif_2);
        gif_3 = findViewById(R.id.gif_3);
    }



    @Override
    protected void onDestroy() {
        if (!gifDecoders.isEmpty()){
            for (GifDecoder gifDecoder:gifDecoders){
                gifDecoder.destroy();
            }
            gifDecoders.clear();
        }
        super.onDestroy();
    }
}
