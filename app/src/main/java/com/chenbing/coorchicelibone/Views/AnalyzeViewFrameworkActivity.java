package com.chenbing.coorchicelibone.Views;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chenbing.coorchicelibone.Utils.LogUtils;
import com.chenbing.iceweather.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;

public class AnalyzeViewFrameworkActivity extends Activity {

  ViewGroup root;
  TextView tv;
  TextView tv2;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_analyze_view_framwork);
    boolean a = true;
    boolean b = true;
    boolean c = a^b;
    root = findViewById(R.id.activity_analyze_view_framwork);
    tv = (TextView) findViewById(R.id.tv);
    tv2 = (TextView) findViewById(R.id.tv_1);
    tv.setOnTouchListener((v, event) -> {
      tv.setText("onTouch");
      LogUtils.e("onTouch");
      return false;
    });


    tv.addOnLayoutChangeListener(new OnLayoutChangeListener() {
      @Override
      public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
                                 int oldRight,
                                 int oldBottom) {
        LogUtils.e("tv2 onLayoutChange");
        StringBuffer sb = new StringBuffer();
        sb.append("left:").append(left).append(", ");
        sb.append("top:").append(top).append(", ");
        sb.append("right:").append(right).append(", ");
        sb.append("bottom:").append(bottom).append(", ");
        sb.append("oldLeft:").append(oldLeft).append(", ");
        sb.append("oldTop:").append(oldTop).append(", ");
        sb.append("oldRight:").append(oldRight).append(", ");
        sb.append("oldBottom:").append(oldBottom).append(" ");
        LogUtils.e(sb.toString());

        //tv.setVisibility(View.GONE);


        tv2.setVisibility(View.VISIBLE);
        v.post(new Runnable() {
          @Override
          public void run() {
            //tv2.setVisibility(View.VISIBLE);
            tv2.setText("change text in onLayoutChange!");
          }
        });
      }
    });
    tv2.addOnLayoutChangeListener(new OnLayoutChangeListener() {
      @Override
      public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
                                 int oldRight,
                                 int oldBottom) {
        LogUtils.e("tv2 onLayoutChange");
      }
    });

    //tv.postDelayed(new Runnable() {
    //  @Override
    //  public void run() {
    //    TextView tv3 = new TextView(AnalyzeViewFrameworkActivity.this);
    //    root.addView(tv3);
    //    tv3.addOnLayoutChangeListener(new OnLayoutChangeListener() {
    //      @Override
    //      public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
    //                                 int oldRight,
    //                                 int oldBottom) {
    //        tv2.setVisibility(View.GONE);
    //      }
    //    });
    //  }
    //}, 3000);


    //    getResources().getLayout(R.layout.item_test);
//
//    ViewGroup container = (ViewGroup) getWindow().getDecorView();
//
//    LayoutInflater layoutInflater = LayoutInflater.from(this);
//    layoutInflater.setFactory2(new LayoutInflater.Factory2() {
//      @Override
//      public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
//        return null;
//      }
//
//      @Override
//      public View onCreateView(String name, Context context, AttributeSet attrs) {
//        return null;
//      }
//    });

    try {
      XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
      XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
      xmlPullParserFactory.setNamespaceAware(true);
      xmlPullParser.setInput(new StringReader( "<TextView android:layout_width=\"wrap_content\" android:layout_height=\"wrap_content\"></TextView>"));
      AttributeSet attributeSet = Xml.asAttributeSet(xmlPullParser);

    } catch (XmlPullParserException e) {
      e.printStackTrace();
    }

  }

  public int add(){
    return -1;
  }
}
