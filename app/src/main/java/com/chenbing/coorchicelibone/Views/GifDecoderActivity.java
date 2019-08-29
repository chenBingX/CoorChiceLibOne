package com.chenbing.coorchicelibone.Views;

import java.io.File;
import java.io.FileInputStream;

import com.chenbing.coorchicelibone.Utils.AppUtils;
import com.chenbing.coorchicelibone.Utils.LogUtils;
import com.chenbing.coorchicelibone.gifdecoder.GifDecoder;
import com.chenbing.iceweather.R;
import com.coorchice.library.SuperTextView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class GifDecoderActivity extends AppCompatActivity {

  private SuperTextView stvStart;
  private ImageView gif;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_gif_decoder);

    findView();
    stvStart.setOnClickListener((v) -> {
      String gifPath = AppUtils.getDrawablePath(R.drawable.gif_1);
      LogUtils.e(String.format("gifPath = %s", gifPath));
      GifDecoder gifDecoder = GifDecoder.openFile(gifPath);
      LogUtils.e(String.format("ptr = %s", String.valueOf(gifDecoder.getPtr())));

      File f = new File(gifPath);
      try {
        FileInputStream fis = new FileInputStream(f);
        LogUtils.e(String.format("gif.size = %s", String.valueOf(fis.available())));
      } catch (Exception e) {
        e.printStackTrace();
      }

    });
  }

  private void findView() {
    stvStart = findViewById(R.id.stv_start);
    gif = findViewById(R.id.gif);


  }
}
