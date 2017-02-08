

![效果图](http://ogemdlrap.bkt.clouddn.com/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202017-02-08%2017.59.28.png)
# 简介
**RoundCornerTextView** 继承自TextView。它拥有TextView的方法，并且进行了功能扩展。**RoundCornerTextView** 能够轻松的实现原本需要编写**Shape** 设置背景等繁琐操作才能实现的控件效果。因此，使用了**RoundCornerTextView** 之后能够使你不用频繁的创建**Shape** 或导入图片，节省了大量内存。

# 主要功能
通过组合**RoundCornerTextView** 的功能，能够创造出上面的效果图中的几种基本效果。当然，当你脑洞大开时能够创造更多有趣而奇怪的效果。下面将介绍**RoundCornerTextView** 的一些特性。    
  
## 使用前准备
你可以在xml文件中直接设置**RoundCornerTextView** 的这些有趣的特有属性。当然，在此之前，你必须确保已经把**RoundCornerTextView**的`<declare-styleable>` 的自定义属性加入到你项目中的`attrs.xml`中([点击这里寻找RoundCornerTextView的<declare-styleable>](https://github.com/chenBingX/CoorChiceLibOne/blob/master/app/src/main/res/values/attrs.xml))。然后，你需要在布局文件的开头申明自定义命名空间`xmlns:app="http://schemas.android.com/apk/res-auto"`。现在，可以开始使用**RoundCornerTextView** 进行创作了。  

## 属性说明
```
<RoundCornerTextView
    android:layout_width="50dp"
    android:layout_height="50dp"
    
    app:corner="25dp"               //设置圆角。会同时作用于填充和边框(如果边框存在的话)。如果要设置为圆形，只需要把该值设置为宽或长的1/2即可。
    app:solid="@color/red"          //设置填充颜色
    app:stroke_color="@color/black"  //设置边框颜色
    app:stroke_width="2dp"          //设置边框的宽度。

    app:state_drawable="@drawable/vector_emoji_1"           //放置一个drawable在背景上。默认居中显示。
    app:state_drawable_mode="center"            //设置drawable的显示模式。可选值如下：
    // left、top、right、bottom、center(默认值)、leftTop、rightTop、leftBottom、rightBottom、fill(充满整个RoundCornerTextView)
    app:isShowState="true"          // boolean类型。是否显示drawable。这在控制状态时十分有用。

    app:text_stroke="true"          // boolean类型。是否启用文字描边模式。注意，启用这个模式之后通过setTextColor()设置的颜色将会被覆盖。
    app:text_stroke_color="@color/black"            // 文字的描边颜色。默认为Color.BLACK。
    app:text_stroke_width="1dp"             // 文字描边的宽度。
    app:text_fill_color="@color/blue"           // 文字填充的颜色。默认为Color.BLACK。

    app:autoAdjust="true"           // boolean类型。是否启用自动调整功能。具体调整什么，需要在Java中为RoundCornerTextView实现一个TextAdjuster。
    //当你启用这个功能而没有实现自己的TextAdjuster时，RoundCornerTextView会启用默认的TextAdjuster。它会按照一定的规则调整文字大小。
    />

```
以上这些属性，你均可以在Java中进行动态的设置。同时也能够获得它们的值。例如：
```
mRoundCornerTextView.setCorner(10);
mRoundCornerTextView.getCorner();
```

## TextAdjuster

如果你实现了**TextAdjuster** ，它将会在绘制文字之前被调用。因此，你可以根据需求在文字绘制前进行一些操作，例如默认的字体大小调整。如果你希望停止这种变化，只需要调用`setTextAdjuster(null)`。
```
mRoundCornerTextView.setTextAdjuster(v->{
      // 在这里进行一些操作，它们将在文字开始被绘制的前一刻被执行
    });
```

# 链接
[点击这里查看RoundCornerTextView的源码。](https://github.com/chenBingX/CoorChiceLibOne/blob/master/app/src/main/java/com/chenbing/coorchicelibone/CustemViews/RoundCornerTextView.java)  

如果你觉得还不错的话，那么感谢关注我，或者给我点个赞。