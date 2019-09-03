package com.chenbing.coorchicelibone.Views;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.chenbing.coorchicelibone.Utils.AppUtils;
import com.chenbing.coorchicelibone.Utils.LogUtils;
import com.chenbing.coorchicelibone.gifdecoder.GifDecoder;
import com.chenbing.iceweather.R;
import com.coorchice.library.SuperTextView;
import com.coorchice.library.utils.ThreadPool;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
                try {
                    InputStream is = getResources().openRawResource(R.drawable.gif_1);
                    byte[] bytes = new byte[is.available()];
                    is.read(bytes);
                    gif.post(() -> {
                        GifDecoder gifDecoder = GifDecoder.openBytes(bytes);
                        gifDecoders.add(gifDecoder);
                        gifDecoder.setOnFrameListener((gd, bitmap) -> {
                            if (bitmap != null)
                                gif.setImageBitmap(bitmap);
                        });
                        gifDecoder.play();
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    InputStream is = getResources().openRawResource(R.drawable.gif_3);
                    byte[] bytes = new byte[is.available()];
                    is.read(bytes);
                    gif_2.post(() -> {
                        final GifDecoder gifDecoder = GifDecoder.openBytes(bytes);
                        gifDecoders.add(gifDecoder);
                        gifDecoder.setOnFrameListener((gd, bitmap) -> {
                            if (bitmap != null)
                                gif_2.setImageBitmap(bitmap);
                        });
                        gifDecoder.play();
                        gif_2.setOnClickListener(v1 -> {
                            gifDecoder.gotoFrame(gifDecoder.getFrameCount() / 2);
                        });
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {
                    InputStream is = getResources().openRawResource(R.drawable.gif_2);
                    byte[] bytes = new byte[is.available()];
                    is.read(bytes);
                    gif_3.post(() -> {
                        final GifDecoder gifDecoder = GifDecoder.openBytes(bytes);
                        gifDecoders.add(gifDecoder);
                        gifDecoder.setOnFrameListener((gd, bitmap) -> {
                            if (bitmap != null)
                                gif_3.setImageBitmap(bitmap);
                        });
                        gifDecoder.play();
                        gif_3.setOnClickListener(v1 -> {
                            if (gifDecoder.isPlaying()) {
                                gifDecoder.stop();
                            } else {
                                gifDecoder.play();
                            }
                        });
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }


//                createOne(gif, R.drawable.gif_4);
//                createOne(gif_1, R.drawable.gif_4);
//                createOne(gif_2, R.drawable.gif_4);
//                createOne(gif_3, R.drawable.gif_4);
            });
        });
        final Random random = new Random();
        gif_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(v.getTag() instanceof GifDecoder)) {
                    try {
                        InputStream is = getResources().openRawResource(R.drawable.flag);
                        byte[] bytes = new byte[is.available()];
                        is.read(bytes);
                        LogUtils.e(String.format("bytes.length = %d", bytes.length));
                        v.post(() -> {
                            GifDecoder gifDecoder = GifDecoder.openBytes(bytes);
                            gifDecoders.add(gifDecoder);
                            v.setTag(gifDecoder);
                            Bitmap frame = gifDecoder.getFrame(0);
                            if (frame != null) {
                                gif_1.setImageBitmap(frame);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    GifDecoder gifDecoder = (GifDecoder) v.getTag();
                    Bitmap frame = gifDecoder.getFrame(random.nextInt(gifDecoder.getFrameCount()));
                    if (frame != null) {
                        gif_1.setImageBitmap(frame);
                    }
                }
            }
        });

    }

    private void createOne(ImageView v, @DrawableRes int resId) {
        try {
//            String gifPath = Environment.getExternalStorageDirectory().getPath() + "/gif_4.gif";
//            FileInputStream is = new FileInputStream(new File(gifPath));
            InputStream is = getResources().openRawResource(resId);
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            LogUtils.e(String.format("bytes.length = %d", bytes.length));
            v.post(() -> {
                GifDecoder gifDecoder = GifDecoder.openBytes(bytes);
//              GifDecoder gifDecoder = GifDecoder.openFile(gifPath);
                gifDecoders.add(gifDecoder);
                showGif(v, gifDecoder, 0);
            });
        } catch (Exception e) {
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
        if (!gifDecoders.isEmpty()) {
            for (GifDecoder gifDecoder : gifDecoders) {
                gifDecoder.destroy();
            }
            gifDecoders.clear();
        }
        super.onDestroy();
    }
}
