package com.CSDNUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/11/15
 * Notes:
 */

public class VisitThread extends Thread {
  private final UrlInfo urlInfo;
  private String userAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 6_1_3 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Mobile/10B329 MicroMessenger/5.0.1";

  private static final int DEFAULT_COUNT = 100;
  private long visitCount = 0;
  private OnVisitListener onVisitListener;

  public VisitThread(String url) {
    this.urlInfo = new UrlInfo(url, DEFAULT_COUNT);
  }

  public VisitThread(UrlInfo urlInfo) {
    this.urlInfo = urlInfo;
  }

  public void run() {
    for (int i = 0; i < urlInfo.getCount(); i++) {
      // while (true) {
      try {
        visit(urlInfo.getUrl());
        long sleepTime = (long) (Math.random() * 10 * 100 * 10 + 800);
//        long sleepTime = (long) (Math.random() * 10 * 10 + 50);
        sleep(sleepTime);
      } catch (IOException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private static Proxy proxy;
  static {
    SocketAddress sa = new InetSocketAddress("192.168.30.25", 3128);
    proxy = new Proxy(java.net.Proxy.Type.HTTP, sa);
  }

  public void visit(String url) throws Exception {
    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
    connection.setRequestProperty("User-Agent", userAgent);
    InputStream is = connection.getInputStream();
//    readPageContent(is);
    connection.disconnect();

    // System.out.println("ThreadId = " + Thread.currentThread().getId() + "进行了一次访问");

    visitCount++;
    if (onVisitListener != null) {
      onVisitListener.onVisit(Thread.currentThread(), url, visitCount);
    }

    // HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection(proxy);
    //
    // connection.setRequestProperty("Content-Type",
    // "application/x-www-form-urlencoded;charset=utf-8");
    // connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64)
    // AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36");
    // BufferedReader reader = new BufferedReader(new
    // InputStreamReader(connection.getInputStream()));
    // String s;
    // while ((s=reader.readLine())!= null){
    // System.out.println(s);
    // }
    // reader.close();
    // connection.disconnect();
  }

  public VisitThread setOnVisitListener(OnVisitListener onVisitListener) {
    this.onVisitListener = onVisitListener;
    return this;
  }


  public interface OnVisitListener {
    void onVisit(Thread currentThread, String url, long visitCount);
  }

  private void readPageContent(InputStream is) throws Exception {
    System.out.println("网页大小 = " + is.available());
    byte[] bytes = new byte[is.available()];
    String content = new String(bytes, 0, bytes.length - 1, "utf-8");
    System.out.println("网页内容 = " + content);
  }
}
