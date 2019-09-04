//
// Created by 陈冰 on 2019/8/30.
//

#include <asm/fcntl.h>
#include <fcntl.h>
#include "decoder.h"
#include "gif_lib_private.h"

void initGifInfo(GifFileType *gifFileType, GifInfo *gifInfo) {
    // 解析
    DGifSlurp(gifFileType);
    gifInfo->graphicsControlBlock = (GraphicsControlBlock *) (malloc(
            sizeof(GraphicsControlBlock) * gifFileType->ImageCount));
    SavedImage *frame;
    for (int i = 0; i < gifFileType->ImageCount; i++) {
        frame = &(gifFileType->SavedImages[i]);
        for (int j = 0; j < frame->ExtensionBlockCount; j++) {
            ExtensionBlock block = frame->ExtensionBlocks[j];
            if (block.Function == GRAPHICS_EXT_FUNC_CODE) {
                DGifExtensionToGCB(block.ByteCount, block.Bytes, &gifInfo->graphicsControlBlock[i]);
                gifInfo->totalDuration += (gifInfo->graphicsControlBlock[i].DelayTime * 10);
                break;
            }
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
}

jlong openFile(JNIEnv *env, jclass clazz, jstring jpath) {
    char *fPath = jstring2string(env, jpath);
    int *Error = 0;
    GifFileType *gifFileType = DGifOpenFileName(fPath, Error);
    if (gifFileType == nullptr) {
        free(fPath);
        return 0;
    }
    GifInfo *gifInfo = new GifInfo();
    initGifInfo(gifFileType, gifInfo);
    return (long long) gifFileType;
}


jlong openBytes(JNIEnv *env, jclass clazz, jbyteArray bytes) {
    if (bytes == NULL) {
        return 0;
    }
    GifInfo *gifInfo = new GifInfo();
    gifInfo->buffer = (jbyteArray) (env->NewGlobalRef(bytes));
    if (gifInfo->buffer == nullptr) {
        delete gifInfo;
        return 0;
    }
    gifInfo->length = (uint) (env->GetArrayLength(gifInfo->buffer));
    int *Error;
    GifFileType *gifFileType = DGifOpen(gifInfo, (InputFunc) (bytesRead), (int *) (&Error));
    if (gifFileType == NULL) {
        env->DeleteGlobalRef(gifInfo->buffer);
        delete gifInfo;
        return 0;
    }
    initGifInfo(gifFileType, gifInfo);
    return (long long) gifFileType;
}

bool bytesIsGif(JNIEnv *env, jclass clazz, jbyteArray bytes) {
    if (bytes != nullptr) {
        char Buf[GIF_STAMP_LEN + 1];
        env->GetByteArrayRegion(bytes, 0, GIF_STAMP_LEN, (jbyte *) Buf);
        Buf[GIF_STAMP_LEN] = '\0';
        bool r = strncmp(GIF_STAMP, Buf, GIF_VERSION_POS) == 0;
        return r;
    }
    return false;
}


bool fileIsGif(JNIEnv *env, jclass clazz, jstring filePath) {
    try {
        char *FileName = jstring2string(env, filePath);
        int FileHandle;
        if ((FileHandle = open(FileName, O_RDONLY)) == -1) {
            return (jboolean) (false);
        }
        FILE *f = fdopen(FileHandle, "rb");
        char Buf[GIF_STAMP_LEN + 1];
        fread(Buf, 1, GIF_STAMP_LEN, f);
        Buf[GIF_STAMP_LEN] = '\0';
        bool r = strncmp(GIF_STAMP, Buf, GIF_VERSION_POS) == 0;
        fclose(f);
        return r;
    } catch (const char* msg) {

    }
    return false;
}




