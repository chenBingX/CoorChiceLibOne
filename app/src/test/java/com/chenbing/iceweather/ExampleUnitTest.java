package com.chenbing.iceweather;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.chenbing.coorchicelibone.Utils.CustomClassLoader;
import com.chenbing.coorchicelibone.Utils.GsonUtils;
import com.chenbing.coorchicelibone.Utils.ReflectUtils;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @org.junit.Test
    public void addition_isCorrect() throws Exception {
        //assertEquals(4, 2 + 2);
    }

    @org.junit.Test
    public void test() throws Exception {
        // com.blinnnk.kratos.view.activity.OtherUserProfileActivity?extra_key_id=10&calling_type=
        //        String url =
        //                "com.blinnnk.kratos.view.activity.OtherUserProfileActivity?extra_key_id=10&calling_type=1";
        //        URI uri = new URI(url);
        //        String query = uri.getQuery();
        //
        //        URL url1 = new URL(url);
        //        String query1 = url1.getQuery();
        //        assertEquals("2", query1);
        // assertEquals("1",query);
        testResolve();
    }

    @org.junit.Test
    public void testDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat chinaFormat = new SimpleDateFormat("yyyy.MM.dd EEEE", Locale.ENGLISH);
        SimpleDateFormat englishFormat = new SimpleDateFormat("yyyy.MM.dd EEEE", Locale.ENGLISH);
        //assertEquals(chinaFormat.format(date), englishFormat.format(date));
    }

    @org.junit.Test
    public void testClassLoader() {
        CustomClassLoader classLoader = new CustomClassLoader("");
        try {
            classLoader.setClassPath("/Users/chenbing/Documents/test/TestClass.class");
            Class clazz = classLoader.loadClass("TestClass");
            if (clazz != null) {
                Method method = ReflectUtils.getMethod(clazz, "doSomething");
                //assertEquals(999, method.invoke(clazz.newInstance()));
            } else {
                //assertEquals("", "路径不对啊！！！！");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void testVisitorTime() {
        String visitorTime = "";
        long currentTime = System.currentTimeMillis();
        long visitTime = 1490237079000l;
        long interval = currentTime - visitTime;
        Date visitDate = new Date(visitTime);
        Calendar visitCa = Calendar.getInstance();
        visitCa.setTime(visitDate);
        visitCa.set(Calendar.HOUR, 0);
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
            visitorTime = String.format("%d天前来过", interval % (24 * aHour) <= 12 * aHour
                ? interval / (24 * aHour)
                : interval / (24 * aHour) + 1);
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
        //assertEquals("" + (interval % (24 * aHour)), visitorTime);
    }

    public void testResolve() {
        String restJson
            = "{\"api\":\"mtop.trip.common.getcertscanresultbyimgbase64\",\"data\":{\"aliyunScanCost\":\"1722\","
            + "\"outputs\":[{\"outputLabel\":\"ocr_idcard\",\"outputMulti\":{},\"outputValue\":{\"dataType\":\"50\","
            + "\"dataValue\":{\"address\":\"北国市东城区景山前街4紫禁城敬事房\",\"birthday\":\"16541220\","
            + "\"cert_no\":\"11204416541220243X\",\"chinese_name\":\"韦小宝\","
            + "\"config_str\":\"{\\\"side\\\":\\\"face\\\"}\",\"face_rect\":{\"angle\":\"-90\","
            + "\"center\":{\"x\":\"750.49993896484375\",\"y\":\"250.99998474121094\"},\"size\":{\"height\":\"211\","
            + "\"width\":\"193.99996948242188\"}},\"name\":\"韦小宝\",\"nationality\":\"汉\","
            + "\"request_id\":\"20171122102207_69b822949a72e97ac599d04afbba15b5\",\"sex\":\"男\",\"success\":\"true\","
            + "\"upc_cert_type\":\"0\"}}}]},\"ret\":[\"SUCCESS::调用成功\"],\"v\":\"2.0\"}";

        int upc_cert_type_index = restJson.indexOf("upc_cert_type");
        int start = restJson.indexOf("\"", upc_cert_type_index);
        int end = restJson.indexOf("\"", start);
        String upc_cert_type = restJson.substring(start, end);

        System.out.println(upc_cert_type);
    }

    @org.junit.Test
    public void testString() {
        String s = "asd\n";
        if (s.lastIndexOf("\n") == s.length() - 1) {
            System.out.println(s.lastIndexOf("\n"));
            s = s.substring(0, s.length() - 1);
            System.out.println(s + "+asdfas");
        }
    }

    @org.junit.Test
    public void testIntegerNull() {
        Test test = new Test(199);
        System.out.println(GsonUtils.newInstance().toJson(test));
    }

    public static class Test {
        private Integer integer;
        private int mInt;

        public Test() {
        }

        public Test(int mInt) {
            this.mInt = mInt;
        }

        public Integer getInteger() {
            return integer;
        }

        public void setInteger(Integer integer) {
            this.integer = integer;
        }

        public int getmInt() {
            return mInt;
        }

        public void setmInt(int mInt) {
            this.mInt = mInt;
        }
    }

    @org.junit.Test
    public void testStringFormat() {
        StringBuffer sb = new StringBuffer();
        sb.append("https://h5.m.taobao.com/trip/rx-pay-success/index/index.html?_wx_tpl=https%3A%2F%2Fh5.m.taobao"
            + ".com%2Ftrip%2Frx-pay-success%2Findex%2Findex.weex.js&bizType=")
            .append("这是bizType位置")
            .append("&multiOrderIdList=")
            .append("这是订单详情位置");
        String format
            = "https://h5.m.taobao.com/trip/rx-pay-success/index/index.html?_wx_tpl=https%3A%2F%2Fh5.m.taobao"
            + ".com%2Ftrip%2Frx-pay-success%2Findex%2Findex.weex.js&bizType=%s&multiOrderIdList=%s";
        System.out.println(sb);
    }
}