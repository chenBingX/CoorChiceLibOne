package com.CSDNUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/11/15
 * Notes:
 */

public class ListLinks {
//  public static final String prefix = "http://blog.csdn.net/qq_31370269/article/details/";
  public static String prefix = "http://blog.csdn.net/qq_31370269/article/details/";
  public static long[] codes = {
//      53171658, 53162291, 53117369, 53115879, 53115851,
      53115829, 52980953, 52932775, 52932315, 52818277,
      52818271, 52818265, 52328822, 52328802, 52317081,
//      52317061, 52314361, 52314236, 52314172, 52314125,
      // 52314015, 52313935, 51481938, 51480728, 51419826,
      // 51419075, 51418741, 51412638, 51348195, 51195244,
      // 51189090,
      // 50752445,
      // 50752211,
      // 50731915,
      // 50725701,
      53185428,
      53185422,
      53220503,
      53261816,
      53261969,
      53323181,

      // 天气App系列
      53323203, 53323210, 53323212, 53339147, 53397689, 53406824
  };

  public static void main(String[] args) throws IOException {
    getVisitUrls("http://blog.csdn.net/qq_31370269?viewmode=contents");
  }

  public static List<String> getVisitUrls(String url) {
    Document doc = null;
    try {
      doc = Jsoup.connect(url).data("query", "Java") // 请求参数
          .userAgent(
              "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0; BIDUBrowser 2.x)")
          .timeout(3000000) // 设置连接超时时间
          .post(); // 使用post方法访问 URL
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    Elements links = doc.select("span").attr("class", "link_title").select("a");
    List<String> urls = new ArrayList<String>();
    for (int i = 0; i < links.size() - 1; i++) {
      if (i % 3 == 0) {
        urls.add(links.get(i).attr("abs:href"));
        System.out.println(links.get(i).attr("abs:href"));
      }
    }
    return urls;
  }

  public static List<String> getVisitUrls() {
    List<String> urls = new ArrayList<String>();
    String url;
    for (long code : codes) {
      url = prefix + code;
      urls.add(url);
    }
    return urls;
  }


}
