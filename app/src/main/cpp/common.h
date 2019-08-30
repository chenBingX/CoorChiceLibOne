//
// Created by 陈冰 on 2019/8/29.
//

#ifndef _COMMON_H
#define _COMMON_H

#include <jni.h>
#include <android/log.h>
#include <stdint.h>
#include <malloc.h>
#include <memory.h>
#include <iostream>
#include <string>
#include <stdio.h>
#include <stdlib.h>

using namespace std;

// Log
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, "Tag", __VA_ARGS__)
// Java 中链接 c 的类路径
#define JAVA_JNI_CLASS  "com/chenbing/coorchicelibone/gifdecoder/JNI"
// 32 位色 argb 合成
#define  argb(a,r,g,b) ( ((a) & 0xff) << 24 ) | ( ((b) & 0xff) << 16 ) | ( ((g) & 0xff) << 8 ) | ((r) & 0xff)

char* jstring2string(JNIEnv *env, jstring jstr);

#endif //_COMMON_H
