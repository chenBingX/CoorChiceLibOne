# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/chenbing/Downloads/android_sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
# 将你不需要混淆的部分申明进来，因为有些类经过混淆会导致程序编译不通过，如下
-printseeds

-keepattributes *Annotation*
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable

-assumenosideeffects class android.util.Log{
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
    public static boolean isLoggable(java.lang.String, int);
}

-assumenosideeffects class * extends java.lang.Throwable {
    public void printStackTrace();
}

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class * extends android.os.IInterface
-keep class android.media.* { *; }
-keep public class com.android.internal.telephony.* { *; }
-keep public class android.os.storage.* { *; }
-keep public class android.content.pm.* { *; }
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment

-keepclasseswithmembers class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}


-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

-keep class * implements java.io.Serializable {
    *;
}

-keep class *.R

-keepclasseswithmembers class **.R$* {
    public static <fields>;
}

-keep class android.support.v4.** { *; }

-keep class com.blinnnk.kratos.animation.** {*;}
-keep class com.blinnnk.kratos.datas.api.request.** {*;}
-keep class com.blinnnk.kratos.datas.api.response.** {*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-keep class **.R

# LeakCanary
-keep class org.eclipse.mat.** { *; }
-keep class com.squareup.leakcanary.** { *; }
#retorfit
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepattributes Signature
-keepattributes Exceptions
#fresco
# Keep our interfaces so they can be used by other ProGuard rules.
# See http://sourceforge.net/p/proguard/bugs/466/
-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip

# Do not strip any method/class that is annotated with @DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}

# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**
#butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
# EventBus
-keep class com.blinnnk.kratos.event.**{*;}
-keepclassmembers class ** {
    public void onEvent*(**);
}
# EventBus 3.0 annotation
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
-keep class com.blinnnk.kratos.event.**{*;}
# stream
-dontwarn com.annimon.stream.**
#weChat
-keep class com.tencent.mm.sdk.** {*;}

-dontwarn com.igexin.**
-keep class com.igexin.**{*;}
#rx
-dontwarn sun.misc.**

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

#uemng
-keepclassmembers class * {
    public <init> (org.json.JSONObject);
}
-keep public class com.blinnnk.kratos.R$*{
    public static final int *;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements java.io.Serializable {
   *;
}
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

#share-sdk
-keep class cn.sharesdk.**{*;}
-keep class android.net.http.SslError
-keep class android.webkit.**{*;}
-keep class com.sina.**{*;}
-keep class m.framework.**{*;}

#Retrofit 2.1
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions


