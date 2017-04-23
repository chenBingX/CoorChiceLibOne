package com.chenbing.iceweather.Utils;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.chenbing.coorchicelibone.Utils.FileUtils;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:16/8/27
 * Notes:
 */
public class FileUtilsTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testGetSDCard() throws Exception {
    assertEquals("", FileUtils.GetSDCard());
  }

  @Test
  public void testFor() throws Exception {
    List<String> data = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      data.add(i, "第" + i + "个");
    }

    for (String temp : data) {
      System.out.println(temp);
//      assertEquals("temp", temp);
    }
  }

}