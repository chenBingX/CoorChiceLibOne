package com.chenbing.iceweather.Views;

import java.util.ArrayList;
import java.util.List;

import com.chenbing.iceweather.R;
import com.chenbing.iceweather.Utils.LogUtils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WebActivity extends AppCompatActivity {
  @BindView(R.id.web_view)
  WebView webView;
  @BindView(R.id.tv)
  TextView tv;

  private List<String> urls = new ArrayList<>();
  private boolean run = true;
  private int count;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_web);
    ButterKnife.bind(this);
    initData();
    initView();
    visit();
  }

  private void initData() {
//    urls.add("http://www.jianshu.com/p/715b59c46b74");
//    urls.add("http://www.jianshu.com/p/beddc8c8bbca");
//    urls.add("http://www.jianshu.com/p/d29274c10589");
//    urls.add("http://www.jianshu.com/p/577165985576");
//    urls.add("http://www.jianshu.com/p/34e517a5b0d0");
//    urls.add("http://www.jianshu.com/p/b845979271d3");
//    urls.add("http://www.jianshu.com/p/81e16ba2d037");
//    urls.add("http://www.jianshu.com/p/60c1859ca25a");
//    urls.add("http://www.jianshu.com/p/c255b1c24b9e");
//    urls.add("http://www.jianshu.com/p/b5b4f7fc5264");
//
//    urls.add("http://www.jianshu.com/p/c36a7fdf27cd");
//    urls.add("http://www.jianshu.com/p/f5b758711979");
//    urls.add("http://www.jianshu.com/p/4a4b64006cbf");
//    urls.add("http://www.jianshu.com/p/eb5ed8ebd7a0");
//    urls.add("http://www.jianshu.com/p/c96f54994d7c");
//    urls.add("http://www.jianshu.com/p/a0c87436914f");
//    urls.add("http://www.jianshu.com/p/54241ca3da5c");
//    urls.add("http://www.jianshu.com/p/b4325fecdcda");
//    urls.add("http://www.jianshu.com/p/69e6f894c698");
//    urls.add("http://www.jianshu.com/p/3366229650bc");
//
//    urls.add("http://www.jianshu.com/p/067a3b8b79ea");
//    urls.add("http://www.jianshu.com/p/2067750e9530");
//    urls.add("http://www.jianshu.com/p/a4b4035842fe");
//    urls.add("http://www.jianshu.com/p/81ef12627265");
//    urls.add("http://www.jianshu.com/p/76debc78ce6b");
//    urls.add("http://www.jianshu.com/p/98836802f51a");
//    urls.add("http://www.jianshu.com/p/14d6aa8c026d");
//    urls.add("http://www.jianshu.com/p/7b2fdb182eba");
//    urls.add("http://www.jianshu.com/p/2a17572cf2c4");
    urls.add("http://www.jianshu.com/p/beddc8c8bbca");

    urls.add("http://www.jianshu.com/p/dbc05cee9f87");
    urls.add("http://www.jianshu.com/p/c5708834b601");
    urls.add("http://www.jianshu.com/p/cbb563529e01");

    //app
    urls.add("http://www.jianshu.com/p/968b3aa57d4d");
    urls.add("http://www.jianshu.com/p/4170361c5a7e");
    urls.add("http://www.jianshu.com/p/d2f15c788aa6");
    urls.add("http://www.jianshu.com/p/dca0d01134fe");
    urls.add("http://www.jianshu.com/p/488d9674c8b4");
    urls.add("http://www.jianshu.com/p/06a28e86f073");


  }

  private void initView() {
    webView.getSettings().setJavaScriptEnabled(true);
    webView.setWebViewClient(new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
      }
    });

  }

  private void visit() {
    new Thread(() -> {
      while (run) {
        try {
          for (String url : urls) {
            runOnUiThread(() -> {
              webView.loadUrl(null);
              webView.loadUrl(url);
              count++;
              tv.setText("" + count);
              LogUtils.e("访问:" + url);
            });
            Thread.sleep(800);
          }

        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (!run) {
      run = true;
      visit();
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    run = false;
  }
}
