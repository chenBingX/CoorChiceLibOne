//
// Created by 陈冰 on 2019/8/29.
//

#ifndef _COMMON_H
#define _COMMON_H

#include <jni.h>
#include <android/log.h>

// Log
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, "Tag", __VA_ARGS__)
// Java 中链接 c 的类路径
#define JAVA_JNI_CLASS  "com/chenbing/coorchicelibone/gifdecoder/JNI"

#endif //_COMMON_H
