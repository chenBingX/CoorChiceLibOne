package com.CSDNUtil;

import java.util.List;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/11/15
 * Notes:
 */

public class Main {
  // 53115879
  public static boolean visitList = true;
  public static long SLEEP_TIME = 800L;

  public static void main(String[] args) {
    if (visitList){
      visitList();
    } else {
      visit();
    }
  }

  private static void visitList() {
    List<String> urls = ListLinks.getVisitUrls();
    System.out.println(String.format("共 %d 篇", urls.size()));
    for (String url : urls) {
      System.out.println("链接" + url);
      new VisitThread(url).start();
    }
  }

  private static void visit(){
    String url = "http://www.jianshu.com/p/cbb563529e01?utm_campaign=maleskine&utm_content=note&utm_medium=mobile_all_hots&utm_source=recommendation";
    new VisitThread(url).start();
  }
}
