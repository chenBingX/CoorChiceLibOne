//
// Created by 陈冰 on 2019/8/30.
//

#include "decoder.h"

jlong openFile(JNIEnv *env, jclass clazz, jstring jpath) {
    char *fPath = jstring2string(env, jpath);
    int *Error = 0;
    GifFileType *gifFileType = DGifOpenFileName(fPath, Error);
    // 解析
    DGifSlurp(gifFileType);
    GifInfo *gifInfo = new GifInfo();
    int totalDuration = 0;
    SavedImage *frame;
    ExtensionBlock *extension;
    for (int i = 0; i < gifFileType->ImageCount; i++) {
        frame = &(gifFileType->SavedImages[i]);
        extension = NULL;
        for (int j = 0; j < frame->ExtensionBlockCount; j++) {
            ExtensionBlock block = frame->ExtensionBlocks[j];
            if (block.Function == GRAPHICS_EXT_FUNC_CODE) {
                extension = &block;
                break;
            }
        }
        if (extension) {
            totalDuration = 10 * (extension->Bytes[2] << 8 | extension->Bytes[1]);
            gifInfo->totalDuration += totalDuration;
        }
    }
    gifInfo->frameDuration = gifInfo->totalDuration / gifFileType->ImageCount;
    gifInfo->width = gifFileType->SWidth;
    gifInfo->height = gifFileType->SHeight;
    gifInfo->totalFrame = gifFileType->ImageCount;
    gifFileType->UserData = gifInfo;
    LOGE("帧数：%d", gifFileType->ImageCount);
    LOGE("每帧时长：%d", gifInfo->frameDuration);
    LOGE("总时长：%d", gifInfo->totalDuration);
    return (long long) gifFileType;
}

jlong openByte(JNIEnv *env, jclass clazz, jbyteArray bytes){

}
