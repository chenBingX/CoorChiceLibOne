package com.chenbing.coorchicelibone.Views;

import com.chenbing.coorchicelibone.Views.BaseView.BaseActivity;
import com.chenbing.iceweather.R;
import com.chenbing.coorchicelibone.Utils.LogUtils;

import android.app.Dialog;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class CrackPasswordActivity extends BaseActivity {
  @BindView(R.id.tv_display_progress)
  TextView displayProgress;

  @BindView(R.id.code_1)
  EditText code1;
  @BindView(R.id.code_2)
  EditText code2;
  @BindView(R.id.code_3)
  EditText code3;

  @BindView(R.id.btn_do)
  Button btnDo;

  private Dialog successDialog;
  private StringBuffer displayBuffer;
  private boolean runShowThread = true;
  private Keyboard keyboard;
  private CompositeDisposable disposable = new CompositeDisposable();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_crack_password);
    ButterKnife.bind(this);
    initData();
    initView();
    addListener();
  }

  @Override
  protected void initData() {
    displayBuffer = new StringBuffer();
  }

  @Override
  protected void initView() {
    displayProgress.setGravity(Gravity.BOTTOM);
    displayProgress.setMovementMethod(ScrollingMovementMethod.getInstance());
    displayProgress.setText("FBI");
    successDialog = new Dialog(this);
    successDialog.setTitle("破解成功！");
    successDialog.setCancelable(true);

  }

  @Override
  protected void addListener() {
    btnDo.setOnClickListener(v -> {
      StringBuffer buffer = new StringBuffer();
      if (!code1.getText().equals("")) {
        buffer.append(code1.getText());
      }
      if (!code2.getText().equals("")) {
        buffer.append(code2.getText());
      }
      if (!code3.getText().equals("")) {
        buffer.append(code3.getText());
      }
      LogUtils.e("code = " + buffer);
      crackPassword(buffer);
    });

  }

  private void crackPassword(StringBuffer buffer) {
    displayProgress.setText("");
    runShowThread = true;
    displayBuffer.delete(0, displayBuffer.length());
    displayBuffer.append("开始破解...\n");
    displayProgress.requestFocus();
    displayProgress.setText(displayBuffer.toString());
    long startTime = System.currentTimeMillis();
    Disposable d = io.reactivex.Observable.create((ObservableOnSubscribe<String>) e -> {
      for (int i = 0; i < 10; i++) {
        for (int j = 0; j < 10; j++) {
          for (int k = 0; k < 10; k++) {
            String tempCode = "" + i + j + k;
            if (tempCode.equals(buffer.toString())) {
              displayBuffer.append("找到密码：")
                  .append(tempCode)
                  .append("\n");
              e.onNext(tempCode);
              return;
            } else {
              displayBuffer.append(tempCode)
                  .append("\n");
            }
            LogUtils.e("tempCode = " + tempCode);
          }
        }
      }
    })
        .subscribeOn(io.reactivex.schedulers.Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableObserver<String>() {
          @Override
          public void onNext(String s) {
            displayBuffer.append("破解成功!\n");
            displayBuffer.append("耗时：")
                .append((System.currentTimeMillis() - startTime) / 1000)
                .append("s\n");
            successDialog.show();
            runShowThread = false;
            displayProgress.setText(displayBuffer.toString());
          }

          @Override
          public void onError(Throwable e) {}

          @Override
          public void onComplete() {}
        });
    disposable.add(d);

    new Thread(() -> {
      while (runShowThread) {
        try {
          Thread.sleep(50);
          if (displayProgress == null) {
            runShowThread = false;
          }
          displayProgress.post(() -> {
            if (runShowThread){
              displayProgress.setText(displayBuffer);
            }
          });
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

      }
    }).start();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (disposable != null) {
      disposable.dispose();
    }
  }
}
