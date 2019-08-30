package com.chenbing.coorchicelibone.Views;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.chenbing.coorchicelibone.Utils.AppUtils;
import com.chenbing.coorchicelibone.Utils.LogUtils;
import com.chenbing.coorchicelibone.gifdecoder.GifDecoder;
import com.chenbing.iceweather.R;
import com.coorchice.library.SuperTextView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class GifDecoderActivity extends AppCompatActivity {

    private SuperTextView stvStart;
    private ImageView gif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_decoder);

        AppUtils.checkPermission(this, 1, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        findView();
        stvStart.setOnClickListener((v) -> {
//      String gifPath = AppUtils.getDrawablePath(R.drawable.gif_1);
            String gifPath = Environment.getExternalStorageDirectory().getPath() + "/gif_1.gif";
            GifDecoder gifDecoder = GifDecoder.openFile(gifPath);
            LogUtils.e(String.format("ptr = %s", String.valueOf(gifDecoder.getPtr())));

            showGif(gifDecoder, 0);
        });
    }

    private void showGif(GifDecoder gifDecoder, int delay) {
        gif.postDelayed(() -> {
            int d = gifDecoder.updateFrame();
            gif.setImageBitmap(gifDecoder.getBitmap());
            showGif(gifDecoder, d);
        }, delay);
    }

    private void findView() {
        stvStart = findViewById(R.id.stv_start);
        gif = findViewById(R.id.gif);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                break;
        }
    }
}
