package com.chenbing.coorchicelibone.Views;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.chenbing.coorchicelibone.CustemViews.SimpleTabLayout;
import com.chenbing.coorchicelibone.CustemViews.SimpleTabLayout.Tab;
import com.chenbing.coorchicelibone.Utils.DensityBoss;
import com.chenbing.coorchicelibone.Utils.LogUtils;
import com.chenbing.coorchicelibone.custom_adjusters.BtnAddPassengerAdjuster;
import com.chenbing.coorchicelibone.custom_adjusters.SwitchAdjuster;
import com.chenbing.iceweather.R;
import com.coorchice.library.SuperTextView;
import com.coorchice.library.SuperTextView.ShaderMode;

public class SimpleTabLayoutActivity extends AppCompatActivity {

    private SimpleTabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_tab_layout);

        tabLayout = (SimpleTabLayout) findViewById(R.id.tab_layout);
        SuperTextView stv2 = (SuperTextView)findViewById(R.id.stv_2);

        tabLayout.addTab(new Tab("测试Tab1"));
        tabLayout.addTab(new Tab("测试Tab2"));
        tabLayout.addTab(new Tab("测试测试Tab2"));
        //tabLayout.setSelectedTextColor(Color.parseColor("#ffc900"));

        SuperTextView stv = (SuperTextView) findViewById(R.id.stv);
        //stv.addAdjuster(new BtnTriangleCloseAdjuster(Color.parseColor("#ffc900"))
        //    .setOpportunity(Opportunity.AT_LAST));
        stv.setStrokeWidth(0);
        stv.setShaderEnable(true);
        stv.setShaderStartColor(Color.parseColor("#ffdc00"));
        stv.setShaderEndColor(Color.parseColor("#ffc900"));
        stv.setShaderMode(ShaderMode.LEFT_TO_RIGHT);
        stv.addAdjuster(new BtnAddPassengerAdjuster(Color.BLACK, 8, 9));

        stv2.addAdjuster(new SwitchAdjuster());
        stv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                LogUtils.e("stv onclicked");
            }
        });

    }
}
