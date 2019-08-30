//
// Created by 陈冰 on 2019/8/29.
//

#include "decoder.h"
#include "drawer.h"

jint updateFrame(JNIEnv *env, jclass clazz, jlong ptr, jobject bitmap) {
    GifFileType *gifFileType = (GifFileType *) ptr;
    GifInfo *gifInfo = (GifInfo *) (gifFileType->UserData);

    AndroidBitmapInfo info;

    // 一张图片的数组
    void *pixels;
    AndroidBitmap_getInfo(env, bitmap, &info);
    // 锁定画布
    AndroidBitmap_lockPixels(env, bitmap, &pixels);
    drawFrame(gifFileType, gifInfo, info, pixels);
    gifInfo->curFrame += 1;
    if (gifInfo->curFrame >= gifInfo->totalFrame - 1) {
        gifInfo->curFrame = 0;
    }
    // 解锁画布
    AndroidBitmap_unlockPixels(env, bitmap);
    return gifInfo->frameDuration;
}

jint getWidth(JNIEnv *env, jclass clazz, jlong ptr) {
    GifFileType *gifFileType = (GifFileType *) ptr;
    GifInfo *gifInfo = (GifInfo *) (gifFileType->UserData);
    return gifInfo->width;
}

jint getHeight(JNIEnv *env, jclass clazz, jlong ptr) {
    GifFileType *gifFileType = (GifFileType *) ptr;
    GifInfo *gifInfo = (GifInfo *) (gifFileType->UserData);
    return gifInfo->height;
}


// 提供一个函数映射表，注册给JVM，这样JVM就可以通过函数映射表来调用相应的函数
// 这样的效率比静态注册的效率高
/**
 * @param1 Java中的native方法名。可以自定义。
 * @param2 函数签名，描述函数的返回值和参数
 * @param3 函数指针，指向被调用的c++函数。名车需要和函数名一样。
 */
static JNINativeMethod nativeMethod[] = {
        {"openFile",    "(Ljava/lang/String;)J",         (void *) openFile},
        {"updateFrame", "(JLandroid/graphics/Bitmap;)I", (void *) updateFrame},
        {"getWidth",    "(J)I",                          (void *) getWidth},
        {"getHeight",   "(J)I",                          (void *) getHeight},
};


JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *jvm, void *reserved) {
    JNIEnv *env;
    if (jvm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }

    jclass clz = env->FindClass(JAVA_JNI_CLASS);
    if (clz == NULL) {
        LOGE("类名不对");
    } else {
        LOGE("类加载成功");
    }

    jint method_size = sizeof(nativeMethod) / sizeof(nativeMethod[0]);
    /**
      * 注册函数表
      * @param1 需要关联到那个【Java】类，Kotlin类不行
      * @param2 方法数组
      * @param3 方法数
      */
    env->RegisterNatives(clz, nativeMethod, method_size);
    //返回使用的JNI版本
    return JNI_VERSION_1_6;
}

JNIEXPORT void JNICALL JNI_OnUnload(JavaVM *jvm, void *reserved) {
    JNIEnv *env;
    if (jvm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return;
    }
    jclass clz = env->FindClass(JAVA_JNI_CLASS);
    // 解注册函数表
    env->UnregisterNatives(clz);
}