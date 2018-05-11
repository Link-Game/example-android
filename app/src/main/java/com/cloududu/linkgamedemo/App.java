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
