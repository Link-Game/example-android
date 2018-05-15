package com.cloududu.linkgamedemo;

import android.app.Application;
import android.os.Handler;

import com.cloududu.linkgamedemo.http.OkHttpClientManager;


/**
 * Author: Patter
 * Data:   2018/4/4
 * Email: 401219741@qq.com
 */

public class App extends Application{
    public static App instance;
    private static Handler mWeakHandler;

    public static final String APPID = "2358aefijklnoprtuv";
    public static final String APP_SECRET = "6e883172e2b072604fed6269c4a0c461";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mWeakHandler = new Handler();
        OkHttpClientManager.initOkHttpManager(App.this);

    }

    public static Handler getHandler() {
        return mWeakHandler;
    }
}
