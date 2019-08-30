//
// Created by 陈冰 on 2019/8/29.
//

#include "common.h"

char *jstring2string(JNIEnv *env, jstring jstr) {
    char *charString = NULL;
    jclass stringClass = env->FindClass("java/lang/String");
    jstring encode = env->NewStringUTF("utf-8");
    jmethodID mid = env->GetMethodID(stringClass, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray jStringArray = (jbyteArray) (env->CallObjectMethod(jstr, mid, encode));
    jsize len = env->GetArrayLength(jStringArray);
    jbyte *bytes = env->GetByteArrayElements(jStringArray, JNI_FALSE);
    if (len > 0) {
        charString = (char *) malloc((size_t) len + 1);
        memcpy(charString, bytes, (size_t) len);
        // 补位
        charString[len] = 0;
    }
    // 用完释放
    env->ReleaseByteArrayElements(jStringArray, bytes, 0);
    return charString;
}