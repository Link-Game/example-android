package com.cloududu.linkgame.opensdk.api;

import android.content.Context;

/**
 * Author: Patter
 * Data:   2018/3/7
 * Email: 401219741@qq.com
 */

public class CmApiFactory {
    public static String APPKEY;
    public static String APPSECRET;
    public static CmApi createCmApi(Context var0) {
        return new CmApiRealization(var0);
    }

    private CmApiFactory() {
        throw new RuntimeException(this.getClass().getSimpleName() + " should not be instantiated");
    }
}
