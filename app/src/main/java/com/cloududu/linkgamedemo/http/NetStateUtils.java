package com.cloududu.linkgamedemo.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 这是一个工具类，用于判断当前网络。
 */
public class NetStateUtils {


    /**
     * @return 是否是wifi状态
     */
    public static boolean isWifi(Context context) {
        return isTypeConnected(context, ConnectivityManager.TYPE_WIFI);
    }

    /**
     * @return 是否是手机网络
     */
    public static boolean isPhone(Context context) {
        return isTypeConnected(context, ConnectivityManager.TYPE_MOBILE);
    }


    private static boolean isTypeConnected(Context context, int typeMobile) {
        if (!isConnected(context)) {
            return false;
        }

        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.
                getNetworkInfo(typeMobile);

        if (networkInfo == null) {
            return false;
        }

        return networkInfo.isAvailable() &
                networkInfo.isConnected();
    }

    /**
     * @return 是否连接上
     */
    public static boolean isConnected(Context context) {

        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // manager.getActiveNetwork()这个方法，是sdk23 才添加的。所以，不建议使用

        //获取到当前 可用的网络的信息
        NetworkInfo networkInfo =
                manager.getActiveNetworkInfo();//这个方法在 sdk 1 就有了，

        //如果没有，那么直接返回false ， 表示没有可用的网络连接
        if (networkInfo == null) {
            return false;
        }

        //判断，当两个都满足的时候，才返回true，其他的，返回false.
        return networkInfo.isConnected() &
                networkInfo.isAvailable();

    }

}
