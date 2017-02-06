package com.JavaGuiDemo.Activities;

import java.util.List;

import com.CSDNUtil.ListLinks;
import com.CSDNUtil.UrlInfo;
import com.CSDNUtil.VisitThread;
import com.example.Activity;
import com.example.Views.ViewGroup;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/11/16
 * Notes:
 */

public class MainActivity extends Activity {
  private ViewGroup rootLayout;
  private final ObservableList<Node> rootContainer;

  private Button startButton;
  private TextArea showInfo, prefixEdit;

  private StringBuffer info = new StringBuffer();


  public MainActivity() {
    rootLayout = new ViewGroup();
    rootContainer = rootLayout.getChildren();
  }

  @Override
  public void onCreate() {
    setContentView(rootLayout);
    initView();
    addListener();
  }

  private void initView() {
    startButton = createStartButton();
    rootContainer.add(startButton);

    showInfo = createShowInfo();
    rootContainer.add(showInfo);

    prefixEdit = createPrefixEdit();
    rootContainer.add(prefixEdit);
  }

  private void addListener() {
    startButton.setOnMouseClicked(event -> {
      startShowInfo();
      // if (prefixEdit.getText().equals("")){
      // showInfo.setText("需要先设置URL");
      // return;
      // }
      if (!prefixEdit.getText().equals("")) {
        ListLinks.prefix = prefixEdit.getText();
      }
      info.append("目标地址前缀:").append(ListLinks.prefix).append("\n");
      showInfo.setText(info.toString());

      if (startButton.getText().equals("启动")) {
        changeStartButtonState();
        info.append("启动成功！");
        showInfo.setText(info.toString());
        visitList();
        System.out.println("启动成功！");
      } else {
        System.out.println("不要重复启动!");
      }
    });

    rootLayout.setOnMouseMoved(event -> {
      // System.out.println("触发鼠标移动");
    });
  }

  private void changeStartButtonState() {
    startButton.setText("正在运行中...");
    startButton.autosize();
    startButton.requestLayout();
    double width = startButton.getWidth();
    startButton.setLayoutX((getWindowWidth() - width) / 2);
    startButton.requestLayout();
  }

  private void visitList() {
    List<String> urls = ListLinks.getVisitUrls();
    info.append(String.format("共 %d 篇", urls.size())).append("\n");
    String format = "ThreadId = %d, url = %s, amount = %d";
    for (String url : urls) {
      info.append("链接: ").append(url).append("\n");
      createVisitThreadThenStart(format, url);
    }
    showInfo.setText(info.toString());
  }

  private void createVisitThreadThenStart(String format, String url) {
    if (url.contains("53220503")) {
      new VisitThread(new UrlInfo(url, 500))
          .setOnVisitListener(
              (thread, targetUrl, visitCount) -> updateInfo(format, thread, targetUrl, visitCount))
          .start();
    } else if (url.contains("53323203")) {
      new VisitThread(new UrlInfo(url, 600))
          .setOnVisitListener(
              (thread, targetUrl, visitCount) -> updateInfo(format, thread, targetUrl, visitCount))
          .start();
    } else if (url.contains("53323210")) {
      new VisitThread(new UrlInfo(url, 600))
          .setOnVisitListener(
              (thread, targetUrl, visitCount) -> updateInfo(format, thread, targetUrl, visitCount))
          .start();
    } else if (url.contains("53323212")) {
      new VisitThread(new UrlInfo(url, 600))
          .setOnVisitListener(
              (thread, targetUrl, visitCount) -> updateInfo(format, thread, targetUrl, visitCount))
          .start();
    } else if (url.contains("53339147")) {
      new VisitThread(new UrlInfo(url, 600))
          .setOnVisitListener(
              (thread, targetUrl, visitCount) -> updateInfo(format, thread, targetUrl, visitCount))
          .start();
    } else if (url.contains("53397689")) {
      new VisitThread(new UrlInfo(url, 600))
          .setOnVisitListener(
              (thread, targetUrl, visitCount) -> updateInfo(format, thread, targetUrl, visitCount))
          .start();
    } else if (url.contains("53406824")) {
      new VisitThread(new UrlInfo(url, 600))
          .setOnVisitListener(
              (thread, targetUrl, visitCount) -> updateInfo(format, thread, targetUrl, visitCount))
          .start();
    } else {
      new VisitThread(url)
          .setOnVisitListener(
              (thread, targetUrl, visitCount) -> updateInfo(format, thread, targetUrl, visitCount))
          .start();
    }
  }

  private void updateInfo(String format, Thread thread, String targetUrl, long visitCount) {
    String inputContent = String.format(format, thread.getId(), targetUrl, visitCount);
    info.append(inputContent).append("\n");
  }

  private void startShowInfo() {
    new Thread(() -> {
      while (true) {
        try {
          Thread.sleep(1000);
          showInfo.setText(info.toString());
          showInfo.setScrollTop(Integer.MAX_VALUE >> 4);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }

  private Button createStartButton() {
    Button button = new Button();
    button.setText("启动");
    button.setLayoutX((getWindowWidth() - 70) / 2);
    button.setLayoutY(100);
    button.setMinWidth(70);
    button.setMinHeight(35);
    button.requestLayout();
    return button;
  }

  private TextArea createShowInfo() {
    TextArea textArea = new TextArea();
    double y = (getWindowHeight() - 300);
    textArea.setLayoutX(10);
    textArea.setLayoutY(y - 10);
    textArea.setPrefWidth(getWindowWidth() - 20);
    textArea.setPrefHeight(300);
    textArea.requestLayout();
    return textArea;
  }


  private TextArea createPrefixEdit() {
    TextArea textArea = new TextArea();
    double width = getWindowWidth() * 0.6;
    textArea.setPrefWidth(width);
    textArea.setPrefHeight(20);
    double x = (getWindowWidth() - width) / 2;
    textArea.setLayoutX(x);
    textArea.setLayoutY(50);
    textArea.setPrefColumnCount(1);
    textArea.setPrefRowCount(1);
    textArea.setPromptText("输入URL前缀"); //设置提示
    textArea.requestLayout();
    return textArea;
  }

  private TextArea createFilterEdit() {
    TextArea textArea = new TextArea();
    double width = getWindowWidth() * 0.6;
    textArea.setPrefWidth(width);
    textArea.setPrefHeight(20);
    double x = (getWindowWidth() - width) / 2;
    textArea.setLayoutX(x);
    textArea.setLayoutY(50);
    textArea.setPrefColumnCount(1);
    textArea.setPrefRowCount(1);
    textArea.setPromptText("输入需要查询的信息"); // 设置提示
    textArea.requestLayout();
    return textArea;
  }



}
