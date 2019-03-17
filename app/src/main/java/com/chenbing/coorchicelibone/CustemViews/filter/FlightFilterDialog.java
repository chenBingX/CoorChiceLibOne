package com.chenbing.coorchicelibone.CustemViews.filter;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenbing.iceweather.R;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by coorchice on 2017/11/16.
 */

public class FlightFilterDialog extends DialogFragment {

    private Context context;
    private Map<String, List<MagicData<FilterDetailData>>> datas = new LinkedHashMap<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_flight_filter, container);
        return view;
    }
}
