package com.cloududu.linkgame.opensdk.model;

/**
 * Author: Patter
 * Data:   2018/3/21
 * Email: 401219741@qq.com
 */

public class AuthModel extends BaseReq {
    private String appKey;
    private String appSecret;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
