package com.chenbing.iceweather.Utils;

import com.chenbing.coorchicelibone.Utils.FileUtils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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

}