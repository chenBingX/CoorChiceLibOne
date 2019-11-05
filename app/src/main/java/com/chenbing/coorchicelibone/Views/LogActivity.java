package com.chenbing.coorchicelibone.Views;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.chenbing.coorchicelibone.Utils.LogUtils;
import com.chenbing.coorchicelibone.Views.BaseView.BaseActivity;
import com.chenbing.iceweather.R;

public class LogActivity extends BaseActivity {

    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        initView();
        addListener();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        et = (EditText) findViewById(R.id.et);
    }

    @Override
    protected void addListener() {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    String content = s.toString();
                    int maxLength = 4000 - 5;
                    if (content.length() > maxLength) {
                        int size = content.length() / maxLength;
                        for (int i = 0; i <= size; i++) {
                            int beginIndex = i * maxLength;
                            int endIndex = beginIndex + maxLength;
                            endIndex = (endIndex > content.length()) ? content.length() : endIndex;
                            LogUtils.e("--\n" + content.substring(beginIndex, endIndex));
                        }
                    } else {
                        LogUtils.e(content);
                    }
                }
            }
        });
    }
}
