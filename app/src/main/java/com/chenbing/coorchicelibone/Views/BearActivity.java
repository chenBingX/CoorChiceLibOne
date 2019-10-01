package com.chenbing.coorchicelibone.Views;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.chenbing.coorchicelibone.Utils.LogUtils;
import com.chenbing.coorchicelibone.Utils.SocketUtils;
import com.chenbing.coorchicelibone.Utils.ToastUtil;
import com.chenbing.iceweather.R;
import com.coorchice.library.SuperTextView;
import com.coorchice.library.utils.ThreadPool;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static com.chenbing.coorchicelibone.Utils.SocketUtils.receiveData;
import static com.chenbing.coorchicelibone.Utils.SocketUtils.sendData;

public class BearActivity extends AppCompatActivity {

    private static final int TCP_PORT = 8837;
    // 19
    private static final String IP = "30.25.58.65";
    // 21
//    private static final String IP = "30.25.66.150";

    private static final int SEND_TOUCH_EVENT = 199;
    private static final int EXIT = 200;

    private EditText et;
    private SuperTextView stvCanvas;
    private SuperTextView stvStrat;
    private Handler socketHandler;
    private Bitmap bitmap;
    private boolean canReceive;
    private Looper socketLooper;
    private ImageView img;
    private float fixDx = 3f;

    @SuppressLint({"ClickableViewAccessibility", "HandlerLeak"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bear);

        et = findViewById(R.id.et);
        stvCanvas = findViewById(R.id.stv_canvas);
        stvStrat = findViewById(R.id.stv_strat);
        img = findViewById(R.id.img);

        stvStrat.setOnClickListener(v -> {
            if (et.getEditableText() != null && !TextUtils.isEmpty(et.getEditableText().toString())) {
                stvStrat.setOnClickListener(null);
                stvStrat.setSolid(Color.parseColor("#B4B5B5"));
                ThreadPool.run(() -> {
                    //开始连接服务器
                    try {
                        Socket s = new Socket(IP, TCP_PORT); //尝试与服务器建立连接
                        //链接成功后开启子线程处理收发事物
                        final InputStream inputStream = s.getInputStream();
                        final OutputStream outputStream = s.getOutputStream();

                        //1:c-s:向服务发送用户名
                        String input = et.getEditableText().toString();
                        byte[] b = input.getBytes("UTF-8");
                        sendData(outputStream, b);
                        //2:s-c:接收服务器的登陆反馈信息
                        b = receiveData(inputStream);
                        String receiveStr = new String(b, "UTF-8");
                        if (TextUtils.equals("EXIST", receiveStr)) {
                            outputStream.close();
                            inputStream.close();
                            s.close();
                            toastLong("已经存在同名用户！");
                        } else if (TextUtils.equals("INEXISTANCE", receiveStr)) {
                            toastLong("欢迎使用！");
                        }
                        // 3:c-s:确认建立链接
                        sendData(outputStream, "OK".getBytes());

                        // 4:s-c:服务端同意建立链接
                        b = receiveData(inputStream);
                        receiveStr = new String(b, "UTF-8");
                        if (!"ACCEPT".equalsIgnoreCase(receiveStr)) {
                            outputStream.close();
                            inputStream.close();
                            s.close();
                            return;
                        }

                        // fixDx:c-s:初始化 云端 Surface
                        JSONObject sizeData = new JSONObject();
                        sizeData.put("type", "init");
                        sizeData.put("w", img.getWidth());
                        sizeData.put("h", img.getHeight());
                        sizeData.put("density", BearActivity.this.getResources().getDisplayMetrics().density);
                        sizeData.put("scaledDensity", BearActivity.this.getResources().getDisplayMetrics().scaledDensity);
                        sendData(outputStream, sizeData.toString().getBytes());

                        // doWork
                        Looper.prepare();
                        socketLooper = Looper.myLooper();
                        socketHandler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                int what = msg.what;
                                switch (what) {
                                    case SEND_TOUCH_EVENT:
                                        String touchEventString = ((String) msg.obj + "|");
                                        sendData(outputStream, touchEventString.getBytes());
                                        break;
                                    case EXIT:
                                        sendData(outputStream, "{\"type\":\"exit\"}".getBytes());
                                        break;
                                }
                            }
                        };
                        // 开启一个线程接收图像数据
                        beginReceiveData(socketHandler, inputStream, outputStream);
                        Looper.loop();
                        LogUtils.e("关闭");
                        if (inputStream != null) inputStream.close();
                        if (outputStream != null) outputStream.close();
                        s.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } else {
                ToastUtil.showShortToast("用户名不能为空！");
            }
        });

        img.setOnTouchListener(new View.OnTouchListener() {

            float[] lastXY = null;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (socketHandler != null) {
                    int action = event.getAction();
                    float x = event.getX();
                    float y = event.getY();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("type", "touch");
                        jsonObject.put("action", action);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            lastXY = new float[]{x, y};
                            break;
                        case MotionEvent.ACTION_MOVE:
                            tryToFixMissPoint(x, y, jsonObject);
                            lastXY = new float[]{x, y};
                            break;
                        case MotionEvent.ACTION_UP:
                            lastXY = null;
                            break;
                    }
                    try {
                        jsonObject.put("x", x);
                        jsonObject.put("y", y);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Message msg = Message.obtain();
                    msg.what = SEND_TOUCH_EVENT;
                    msg.obj = jsonObject.toString();
                    socketHandler.sendMessage(msg);
                    LogUtils.e("SEND_TOUCH_EVENT = " + jsonObject.toString());
                }
                return true;
            }

            private void tryToFixMissPoint(float x, float y, JSONObject jsonObject) {
                if (lastXY != null && (Math.abs(x - lastXY[0]) > fixDx || Math.abs(y - lastXY[1]) > fixDx)) {
                    float[] xArray = null;
                    float[] yArray = null;
                    if (Math.abs(x - lastXY[0]) > fixDx) {
                        int count = (int) (Math.abs(x - lastXY[0]) / fixDx);
                        xArray = new float[count];
                        for (int i = 0; i < xArray.length; i++) {
                            if (x > lastXY[0]) {
                                xArray[i] = lastXY[0] + fixDx * (i + 1);
                            } else {
                                xArray[i] = lastXY[0] - fixDx * (i + 1);
                            }
                        }
                    }
                    if (Math.abs(y - lastXY[1]) > fixDx) {
                        int count = (int) (Math.abs(y - lastXY[1]) / fixDx);
                        yArray = new float[count];
                        for (int i = 0; i < yArray.length; i++) {
                            if (y > lastXY[1]) {
                                yArray[i] = lastXY[1] + fixDx * (i + 1);
                            } else {
                                yArray[i] = lastXY[1] - fixDx * (i + 1);
                            }
                        }
                    }
                    int count = Math.max(xArray == null ? 0 : xArray.length, yArray == null ? 0 : yArray.length);
                    for (int i = 0; i < count; i++) {
                        try {
                            jsonObject.put("x", xArray == null ? x : (i < xArray.length ? xArray[i] : xArray[xArray.length - 1]));
                            jsonObject.put("y", yArray == null ? y : (i < yArray.length ? yArray[i] : yArray[yArray.length - 1]));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Message msg = Message.obtain();
                        msg.what = SEND_TOUCH_EVENT;
                        msg.obj = jsonObject.toString();
                        socketHandler.sendMessage(msg);
                    }
                }
            }
        });
    }

    private void beginReceiveData(Handler socketHandler, InputStream inputStream, OutputStream outputStream) {
        if (socketHandler == null || inputStream == null || outputStream == null) return;
        canReceive = true;
        ThreadPool.run(() -> {
            while (canReceive) {
                try {
                    synchronized (inputStream) {
//                        LogUtils.e(String.format("原始数据大小：%d k", (data.length / 1024)));
                        bitmap = BitmapFactory.decodeStream(inputStream);
                    }
                    if (bitmap != null) {
                        LogUtils.e(String.format("读取到一张bitmap，大小：%f k，w = %d，h = %d", bitmap.getByteCount() / 1024f, bitmap.getWidth(), bitmap.getHeight()));
                        img.post(() -> {
                            try {
                                img.setImageBitmap(bitmap);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void toastLong(String msg) {
        runOnUiThread(() -> ToastUtil.showLongToast(msg));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onDestroy() {
        if (socketHandler != null) socketHandler.sendEmptyMessage(EXIT);
        canReceive = false;
        if (socketLooper != null) {
            socketLooper.quitSafely();
        }
//        if (socketHandler != null) {
//            socketHandler.removeCallbacksAndMessages(null);
//        }
        if (bitmap != null) {
            bitmap = null;
        }
        super.onDestroy();
    }
}
