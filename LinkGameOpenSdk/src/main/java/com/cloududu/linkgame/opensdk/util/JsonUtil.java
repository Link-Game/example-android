package com.cloududu.linkgame.opensdk.util;

/**
 * Author: Patter
 * Data:   2018/4/18
 * Email: 401219741@qq.com
 */

public class JsonUtil {

    public static String formatJson(int code, String message, String data) {
        return "{" +
                "\"code\"" + ":\"" +  + code + "\"," +
                "\"message\"" + ":\"" + message + "\"," +
                "\"data\"" + ":" + data +
                "}";
    }

    public static String formatJson(int type, String refreshToken) {
        return "{" +
                "\"type\"" + ":\"" + type + "\"," +
                "\"refreshToken\"" + ":\"" + refreshToken +
                "\"}";
    }
}
