package com.chenbing.coorchicelibone.Views;

import com.chenbing.iceweather.R;
import com.chenbing.coorchicelibone.Views.BaseView.BaseActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TableActivity extends BaseActivity {
  @BindView(R.id.table_layout)
  TableLayout tableLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_draw);
    ButterKnife.bind(this);
    initData();
    initView();
    addListener();
  }

  @Override
  protected void initData() {
    for (int i = 0; i < 40; i++) {
      TableRow tableRow = new TableRow(getBaseContext());
      tableRow.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
          LinearLayout.LayoutParams.WRAP_CONTENT));

      for (int j = 0; j < 10; j++) {
        TextView textView1 = new TextView(getBaseContext());
        textView1.setText("textA:" + i + j);
        textView1.setPadding(0, 0, 12, 0);
        tableRow.addView(textView1);

        TextView textView2 = new TextView(getBaseContext());
        textView2.setText("textB:" + i + j);
        textView2.setPadding(0, 0, 12, 0);
        tableRow.addView(textView2);

        TextView textView3 = new TextView(getBaseContext());
        textView3.setText("textC:" + i + j);
        textView3.setPadding(0, 0, 12, 0);
        tableRow.addView(textView3);
      }

      tableLayout.addView(tableRow);
    }
  }

  @Override
  protected void initView() {


  }

  @Override
  protected void addListener() {

  }
}
