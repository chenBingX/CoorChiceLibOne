package com.chenbing.coorchicelibone.Views;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.chenbing.coorchicelibone.Utils.BindView;
import com.chenbing.coorchicelibone.Utils.ButterKnife;
import com.chenbing.iceweather.R;

public class AnnotationActivity extends AppCompatActivity {

    /**
     * 注解
     */
    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
        /**
         * 一定要在 setContentView() 后调用
         */
        ButterKnife.bind(this);

        /**
         * 此时，tv已经被赋值了
         */
        tv.setOnClickListener(v -> tv.setTextColor(Color.RED));
    }
}
