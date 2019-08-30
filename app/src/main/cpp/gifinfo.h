//
// Created by CoorChice on 2019-08-30.
//

#ifndef COORCHICELIBONE_GIFINFO_H
#define COORCHICELIBONE_GIFINFO_H

#include "common.h"

class GifInfo{
public:
    int width;
    int height;
    int curFrame;
    int totalFrame;
    int frameDuration;
    int totalDuration;

    GifInfo();
    ~GifInfo();

};

#endif //COORCHICELIBONE_GIFINFO_H
