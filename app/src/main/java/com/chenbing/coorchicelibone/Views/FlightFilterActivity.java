package com.chenbing.coorchicelibone.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.chenbing.coorchicelibone.CustemViews.filter.FlightFilter;
import com.chenbing.iceweather.R;


public class FlightFilterActivity extends AppCompatActivity {

    private FlightFilter flightFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_filter);

        flightFilter = (FlightFilter) findViewById(R.id.flight_filter);

    }
}
