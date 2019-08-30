//
// Created by CoorChice on 2019-08-30.
//

#include "drawer.h"

void drawFrame(GifFileType *gifFileType, GifInfo *gifInfo, AndroidBitmapInfo info, void *pixels) {
    //获取当前帧
    SavedImage savedImage = gifFileType->SavedImages[gifInfo->curFrame];
    GifImageDesc imageDesc = savedImage.ImageDesc;

    //像素位置
    int pointPixel;
    int *px = (int *) pixels;
    int *line;
    GifColorType gifColorType;
    ColorMapObject *colorMapObject = imageDesc.ColorMap;
    if (colorMapObject == NULL) {
        colorMapObject = gifFileType->SColorMap;
    }
    GifByteType gifByteType;
    px = (int *) ((char *) px + info.stride * imageDesc.Top);
    for (int y = imageDesc.Top; y < imageDesc.Top + imageDesc.Height; y++) {
        line = px;
        for (int x = imageDesc.Left; x < imageDesc.Left + imageDesc.Width; x++) {
            pointPixel = (y - imageDesc.Top) * imageDesc.Width + (x - imageDesc.Left);
            gifByteType = savedImage.RasterBits[pointPixel];
            //需要给每个像素赋颜色
            if (colorMapObject != NULL) {

                gifColorType = colorMapObject->Colors[gifByteType];
                line[x] = argb(255, gifColorType.Red, gifColorType.Green, gifColorType.Blue);
            }
        }
        px = (int *) ((char *) px + info.stride);
    }
}