//
// Created by CoorChice on 2019-08-30.
//

#include "drawer.h"

void drawFrame(GifFileType *gifFileType, AndroidBitmapInfo bitmapInfo, void *pixels) {
    if (gifFileType->UserData == NULL)
        return;
    //获取当前帧
    GifInfo *gifInfo = (GifInfo *) (gifFileType->UserData);
    SavedImage savedImage = gifFileType->SavedImages[gifInfo->curFrame];
    GifImageDesc imageDesc = savedImage.ImageDesc;
    // 用于获取图形控制扩展信息
    GraphicsControlBlock GCB = gifInfo->graphicsControlBlock[gifInfo->curFrame];
    //像素位置
    int pointPixel;
    //全局像素数组
    int *px = (int *) pixels;
    int *line;
    // 一个颜色
    GifColorType gifColorType;
    // 获取局部颜色列表
    ColorMapObject *colorMapObject = imageDesc.ColorMap;
    if (colorMapObject == NULL) {
        // 没有设置为全局颜色列表
        colorMapObject = gifFileType->SColorMap;
    }
    GifByteType gifByteType;
    px = (int *) ((char *) px + bitmapInfo.stride * imageDesc.Top);
    for (int y = imageDesc.Top; y < imageDesc.Top + imageDesc.Height; y++) {
        // 一行像素数组
        line = px;
        for (int x = imageDesc.Left; x < imageDesc.Left + imageDesc.Width; x++) {
            // 单点像素数组
            pointPixel = (y - imageDesc.Top) * imageDesc.Width + (x - imageDesc.Left);
            // 一个像素点的值
            gifByteType = savedImage.RasterBits[pointPixel];
            // 如果是透明的，就需要跳过
            bool skip = gifByteType == GCB.TransparentColor;
            //需要给每个像素赋颜色
            if (colorMapObject != NULL && !skip) {
                // 通过索引，从颜色列表中获取颜色
                gifColorType = colorMapObject->Colors[gifByteType];
                // 合成一个像素点颜色，给它赋值
                line[x] = argb(255, gifColorType.Red, gifColorType.Green, gifColorType.Blue);
            }
        }
        // 处理完一行，指针移动到下一行
        px = (int *) ((char *) px + bitmapInfo.stride);
    }
}