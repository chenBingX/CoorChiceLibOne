//
// Created by 陈冰 on 2019/8/29.
//

#include "common.h"
#include "gif_lib.h"
#include "gifinfo.h"

char *jstring2string(JNIEnv *env, jstring jstr) {
    char *charString = nullptr;
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
    env->DeleteLocalRef(stringClass);
    env->DeleteLocalRef(encode);
    env->ReleaseByteArrayElements(jStringArray, bytes, 0);
    return charString;
}

uint_fast8_t bytesRead(GifFileType *gif, GifByteType *bytes, uint_fast8_t size) {
    GifInfo *gifInfo = (GifInfo *) (gif->UserData);
    JNIEnv *env = getEnv();
    if (env == nullptr) {
        return 0;
    }
    if (gifInfo->curPosition + size > gifInfo->length) {
        size -= gifInfo->curPosition + size - gifInfo->length;
    }
    env->GetByteArrayRegion(gifInfo->buffer, (jsize) gifInfo->curPosition, size, (jbyte *) bytes);
    gifInfo->curPosition += size;
    return size;
}

