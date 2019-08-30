cmake_minimum_required(VERSION 3.4.1)

# 配置编译设置
set(CMAKE_CXX_FLAGS "${CMAKE_C_FLAGS} -D__ANDROID_ -g -std=gnu++11 -fexceptions -frtti -lstdc++ -Wundefined-internal")
# 配置 .so 输出路径
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/../jniLibs/${ANDROID_ABI})

# 添加源文件
set(SOURCE)
set(SOURCE2)
file(GLOB_RECURSE SOURCE "${PROJECT_SOURCE_DIR}/*.cpp")
file(GLOB_RECURSE SOURCE2 "${PROJECT_SOURCE_DIR}/*.c")
add_library(GifLib SHARED ${SOURCE}
        ${SOURCE2}
        )

# 添加头文件
include_directories(${PROJECT_SOURCE_DIR})


# 在ndk的lib-log文件夹下查找log库
find_library(lib-log log)

# 依赖库
set(LIBS)
list(APPEND LIBS
        jnigraphics
#        android
#        GLESv2
        log
        )

# 关联依赖库
target_link_libraries(GifLib
        ${LIBS}
        )



