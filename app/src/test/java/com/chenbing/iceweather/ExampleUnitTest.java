package com.chenbing.iceweather;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
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
}