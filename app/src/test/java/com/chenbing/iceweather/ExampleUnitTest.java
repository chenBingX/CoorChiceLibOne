package com.chenbing.iceweather;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

import com.chenbing.coorchicelibone.Utils.CustomClassLoader;
import com.chenbing.coorchicelibone.Utils.ReflectUtils;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
  @Test
  public void addition_isCorrect() throws Exception {
    assertEquals(4, 2 + 2);
  }

  @Test
  public void test() throws Exception {
    // com.blinnnk.kratos.view.activity.OtherUserProfileActivity?extra_key_id=10&calling_type=
    String url =
        "com.blinnnk.kratos.view.activity.OtherUserProfileActivity?extra_key_id=10&calling_type=1";
    URI uri = new URI(url);
    String query = uri.getQuery();

    URL url1 = new URL(url);
    String query1 = url1.getQuery();
    assertEquals("2", query1);
    // assertEquals("1",query);

  }

  @Test
  public void testDate() {
    Date date = new Date(System.currentTimeMillis());
    SimpleDateFormat chinaFormat = new SimpleDateFormat("yyyy.MM.dd EEEE", Locale.ENGLISH);
    SimpleDateFormat englishFormat = new SimpleDateFormat("yyyy.MM.dd EEEE", Locale.ENGLISH);
    assertEquals(chinaFormat.format(date), englishFormat.format(date));
  }

  @Test
  public void testClassLoader() {
    CustomClassLoader classLoader = new CustomClassLoader("");
    try {
      classLoader.setClassPath("/Users/chenbing/Documents/test/TestClass.class");
      Class clazz =  classLoader.loadClass("TestClass");
      if (clazz != null) {
        Method method = ReflectUtils.getMethod(clazz, "doSomething");
        try {
          assertEquals(999, method.invoke(clazz.newInstance()));
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          e.printStackTrace();
        } catch (InstantiationException e) {
          e.printStackTrace();
        }
      } else {
        assertEquals("", "路径不对啊！！！！");
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testVisitorTime() {
    String visitorTime = "";
    long currentTime = System.currentTimeMillis();
    long visitTime = 1489939210000l;
    long interval = currentTime - visitTime;
    Date visitDate = new Date(visitTime);
    Calendar visitCa = Calendar.getInstance();
    visitCa.setTime(visitDate);
    visitCa.set(Calendar.HOUR, 12);
    visitCa.set(Calendar.MINUTE, 0);
    visitCa.set(Calendar.SECOND, 0);
    // 表示访问时间与访问当天0点的时差
    long intervalVisit = visitTime - visitCa.getTime().getTime();
    int offsetDay = 0;
    if (intervalVisit > 0) {
      offsetDay = 1;
    }

    Calendar ca = Calendar.getInstance();
    ca.set(Calendar.HOUR_OF_DAY, 0);
    ca.set(Calendar.MINUTE, 0);
    ca.set(Calendar.SECOND, 0);
    Date today = ca.getTime();

    ca.add(Calendar.DAY_OF_YEAR, -1);
    Date yesterday = ca.getTime();

    ca.setTime(today);
    ca.add(Calendar.DAY_OF_YEAR, -2);
    Date theDayBeforeYesterday = ca.getTime();

    ca.setTime(today);
    ca.add(Calendar.DAY_OF_YEAR, -3);
    Date threeDaysAgo = ca.getTime();

    ca.setTime(today);
    ca.add(Calendar.DAY_OF_YEAR, -7);
    Date sevenDaysAgo = ca.getTime();

    ca.setTime(today);
    ca.add(Calendar.DAY_OF_YEAR, -14);
    Date fourteenDaysAgo = ca.getTime();

    ca.setTime(today);
    ca.add(Calendar.DAY_OF_YEAR, -21);
    Date twenty_oneDaysAgo = ca.getTime();

    ca.setTime(today);
    ca.add(Calendar.DAY_OF_YEAR, -30);
    Date thirtyDaysAgo = ca.getTime();

    ca.setTime(today);
    ca.add(Calendar.DAY_OF_YEAR, -60);
    Date sixtyDaysAgo = ca.getTime();

    int aHour = 60 * 60 * 1000;
    if (interval < aHour) {
      visitorTime = "刚刚来过";
    } else if (visitTime > today.getTime() && visitTime <= (today.getTime() + 24 * aHour)) {
      long intervalHour = interval / aHour;
      visitorTime =
          String.format("%d小时前来过", intervalHour);
    } else if (visitTime >= yesterday.getTime()
        && visitTime < (yesterday.getTime() + 24 * aHour)) {
      visitorTime = "昨天来过";
    } else if (visitTime >= theDayBeforeYesterday.getTime()
        && visitTime < (theDayBeforeYesterday.getTime() + 24 * aHour)) {
      visitorTime = "前天来过";
    } else if (visitTime >= sevenDaysAgo.getTime() && visitTime < threeDaysAgo.getTime()) {
      visitorTime = String.format("%d天前来过", interval / (24 * aHour) + offsetDay);
    } else if (visitTime >= fourteenDaysAgo.getTime() && visitTime < sevenDaysAgo.getTime()) {
      visitorTime = "上周来过";
    } else if (visitTime >= twenty_oneDaysAgo.getTime()
        && visitTime < fourteenDaysAgo.getTime()) {
      visitorTime = "20天前来过";
    } else if (visitTime >= thirtyDaysAgo.getTime() && visitTime < twenty_oneDaysAgo.getTime()) {
      visitorTime = "30天前来过";
    } else if (visitTime > sixtyDaysAgo.getTime() && visitTime <= thirtyDaysAgo.getTime()) {
      visitorTime = "60天前来过";
    } else if (visitTime < sixtyDaysAgo.getTime()) {
      visitorTime = "很久以前来过";
    }
    assertEquals("", visitorTime);
  }
}