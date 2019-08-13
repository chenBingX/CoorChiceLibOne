package com.chenbing.coorchicelibone.Views;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.chenbing.coorchicelibone.Utils.LogUtils;
import com.chenbing.iceweather.R;
import com.coorchice.library.ImageEngine;
import com.coorchice.library.SuperTextView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.text.Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE;

public class TextViewActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);

        tv = findViewById(R.id.tv);

        lazyRender();

    }

    private void lazyRender() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv.setText(Html.fromHtml("<p>Allspark 图片交互化的主要目标是为了改变现在呆板的banner、列表区块或者任何静态图片展示现状，创造一种更加灵活和生动的图片交互能力。  </p>" +
                                "" +
                                "<p>1.Allspark 提供了最基础的图片多区域（精确的不规则区域）的触摸事件。这种能力用到图片上，可以让原本只能设置一个点击事件的图片，能够根据不同位置触发不同的事件。比如在 banner 或者列表区块图片中，可以一张图片跳转多个商品的购买链接。</p>" +
                                "" +
                                "<p><img src=\"https://gw.alicdn.com/tfs/TB1njoVX21G3KVjSZFkXXaK4XXa-464-753.gif\"/> </p>" +
                                "" +
                                "<p>2.当然 Allspark 不止于此。它具有一整套的构建丰富交互的能力。如嵌套、滑动、丰富的可交互动画支持、区域深度扩展。  </p>" +
                                "" +
                                "<p><font style=\"line-height:250%;\" color=\"#000000\" font-size=\"50px\">上图的浴缸场景，演示了 Allspark 的简单嵌套和可交互动画能力。点击指定区域，在该区块中出现内浮层，再次点击指定区域，浮层播放位移动画。如此一来，原本单调的一张图片活了起来，生动而有趣。</font></p>" +
                                "" +
                                "<p>当然，这只是一种应用场景，Allspark 所提供的这种能力，可以做到的更多。比如在一个宝贵的营销位置，可以通过 多点击区 域或者 内联嵌套 让它产生更多的价值。  </p>" +
                                "" +
                                "<p><img src=\"https://gw.alicdn.com/tfs/TB1FdMYX9SD3KVjSZFKXXb10VXa-464-753.gif\" alt=\"\"/></p>" +
                                "" +
                                "<p>如图是 banner 中的一张图片，通过点击指定位置，能够触发一个商品弹性位移的动画。这种场景可以提高该 banner 广告位趣味性，吸引更多的用户注意力以达成转化。</p>" +
                                "" +
                                "<p>3.Allspark 天然提供了现今比较流行的页面浮层支持。  </p>" +
                                "" +
                                "<p><img src=\"https://gw.alicdn.com/tfs/TB1eg.5XYus3KVjSZKbXXXqkFXa-464-753.gif\" alt=\"\"/>  </p>" +
                                "" +
                                "<p>多区域点击可以让这种浮层产生更多的价值，不受限制的嵌套组合能够提供随心所欲的快速的发布一个全新的浮层。对于开发同学来说，起码可以让大家从编写这种浮层代码中解放出来（设计师、产品或者运营同学就能搞定）。可交互化的动画支持也可以让你的浮层更加生动。或者，你的浮层也能 内联嵌套 出一些列的类似页面流的操作。  </p>" +
                                "" +
                                "<p>4.Allspark 支持自定义事件扩展。  </p>" +
                                "" +
                                "<p><img src=\"https://gw.alicdn.com/tfs/TB15J7VX25G3KVjSZPxXXbI3XXa-464-753.gif\" alt=\"\"/>  </p>" +
                                "" +
                                "<p>如上图 点击出现标签 的事件就是在标准以外扩展出来的事件。</p>" +
                                "" +
                                "<p>5.Allspark 通过约定一套统一的协议，使其天然具备了跨端、跨域的可能性。任何接入 Allspark 引擎的应用的中，都能运行同一张可交互化图片。比如，在淘宝的 banner （或者一个列表区块）中投放飞猪的一块可交互内容，或者在飞猪的一个区块上投放了河马的一块可交互内容。在移动端上，在获得这种能力的同时，伴随的是甚至由于 naitve 的性能体验（同等复杂的交互场景下）。  </p>" +
                                "" +
                                "<p><img src=\"https://gw.alicdn.com/tfs/TB1LLA1X3aH3KVjSZFpXXbhKpXa-1730-1302.png\" alt=\"\"/></p>" +
                                "" +
                                "<p>相比于目前一些仅支持简单的图片规则区域的点击热区方案而言，Allspark 提供的更多，可以具有更多的想象力。</p>" +
                                "" +
                                "<p>后续可以提供使用者类似 Axure 等工具，可以方便的编辑和快速发布、投放到线上，而无需开发资源的再投入。</p>"

                        , TO_HTML_PARAGRAPH_LINES_CONSECUTIVE
                        , new Html.ImageGetter() {
                            @Override
                            public Drawable getDrawable(String s) {
                                Drawable drawable = null;
                                InputStream is = null;
                                try {
                                    is = (InputStream) new URL(s).getContent();
                                    drawable = Drawable.createFromStream(is, "src");
                                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                                    is.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    return null;
                                }

                                return drawable;
                            }
                        }, null));
//                tv.setText("测试数据");
            }
        }, 1000);
    }

    private static byte[] getBytesArrayFromNet(String path) throws Exception {
        // 1.实例化URL对象并指定网址
        URL url = new URL(Uri.encode(path, ":/-![].,%?&="));
        // 2.打开连接并返回HttpURLConnection对象
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        // 3.设置相关参数
        httpURLConnection.setConnectTimeout(5000);// 设置连接超时时间
        httpURLConnection.setRequestMethod("GET");// 设置请求方法
        // 4.将请求提交到服务器并得到服务器端的响应结果
        int responseCode = httpURLConnection.getResponseCode();// 得到了响应码,只有响应码等于200表示OK
        if (responseCode == 200) {
            InputStream inputStream = null;
            try {
                inputStream = httpURLConnection.getInputStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, len);
                }

                return byteArrayOutputStream.toByteArray();
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                    inputStream = null;
                }
            }

        }
        return null;
    }
}
