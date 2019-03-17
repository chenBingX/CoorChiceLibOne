package com.chenbing.coorchicelibone.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.chenbing.iceweather.R;

import io.flutter.facade.Flutter;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.view.FlutterView;

public class FlutterDemoActivity extends AppCompatActivity {

    public static final String FlutterChannel = "com.coorchice.flutter_app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FlutterView flutterView = Flutter.createView(
                this,
                getLifecycle(),
                "route0"
        );
        setContentView(R.layout.activity_flutter);
        FrameLayout root = (FrameLayout) findViewById(R.id.root);
        root.addView(flutterView);
        // 第一帧绘制好后在显示，避免黑屏
        ((FlutterView) flutterView).addFirstFrameListener(() -> root.setVisibility(View.VISIBLE));

        MethodChannel channel = new MethodChannel(flutterView, FlutterChannel);
        channel.setMethodCallHandler((methodCall, result) -> {
            if (methodCall.method.equals("bar")) {
                result.success("success, " + methodCall.arguments);
            }
        });
        channel.invokeMethod("update", "Hi, I'm Native!");
    }
}
