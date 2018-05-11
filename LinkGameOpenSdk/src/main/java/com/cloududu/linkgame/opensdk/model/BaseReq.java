package com.cloududu.linkgame.opensdk.model;

/**
 * Author: Patter
 * Data:   2018/3/4
 * Email: 401219741@qq.com
 */

public class BaseReq {
    public String openId;
    public String appKey;
    public String appSecret;
    public  String sign; //签名
    public BaseReq(){}
    public enum  RequsetType{
        SHARE , AUTHORIZEDLOGIN
    }
}
