// SsoAuth.aidl
package com.chenbing.iceweather;

// Declare any non-default types here with import statements

interface SsoAuth {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    /**
     * 验证授权
     */
     void ssoAuth(String userName, String passWord);

}
