package com.chenbing.coorchicelibone.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.chenbing.coorchicelibone.CustemViews.FliggySimpleTabLayout;
import com.chenbing.coorchicelibone.CustemViews.FliggySimpleTabLayout.Tab;
import com.chenbing.iceweather.R;

public class SimpleTabLayoutActivity extends AppCompatActivity {

    private FliggySimpleTabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_tab_layout);

        tabLayout = (FliggySimpleTabLayout)findViewById(R.id.tab_layout);

        tabLayout.addTab(new Tab("测试Tab1"));
        tabLayout.addTab(new Tab("测试Tab2"));
        tabLayout.addTab(new Tab("测试测试Tab2"));
        //tabLayout.setSelectedTextColor(Color.parseColor("#ffc900"));
    }
}
