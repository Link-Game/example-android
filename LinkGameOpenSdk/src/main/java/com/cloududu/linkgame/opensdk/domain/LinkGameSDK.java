package com.cloududu.linkgame.opensdk.domain;

import android.text.TextUtils;
import android.util.Log;

import com.cloududu.linkgame.opensdk.api.CmApi;
import com.cloududu.linkgame.opensdk.api.CmApiFactory;
import com.cloududu.linkgame.opensdk.util.AppInfoUtils;
import com.cloududu.linkgame.opensdk.util.JsonUtil;
import com.cloududu.linkgame.opensdk.util.StringUtils;
import com.unity3d.player.UnityPlayer;

/**
 * User: Tan(hellotanm@gmail.com)
 * Date: 2018/4/18.
 * Time: 6:00 PM.
 * Version: V1.0
 */

public class LinkGameSDK {
    private static final String TAG = "LinkGame" ;
    public static String callBackObjectName;
    public static String callBackMethodName;
    private CmApi cmApi;

    public static void sendMsg(String msg) {
        UnityPlayer.UnitySendMessage(callBackObjectName, callBackMethodName, msg);
    }

    public void init(String appID, String appSecret) {
        Log.i(TAG , "init()") ;

        String md5 = AppInfoUtils.getSingInfo(UnityPlayer.currentActivity,
                UnityPlayer.currentActivity.getPackageName(), AppInfoUtils.MD5);

        String checkCode = StringUtils.SHA1(StringUtils.MD5(appID + appSecret));

        if (TextUtils.isEmpty(appID) || TextUtils.isEmpty(appSecret) || TextUtils.isEmpty(md5)) {
            UnityPlayer.UnitySendMessage(callBackObjectName, callBackMethodName, JsonUtil.formatJson(400, "init fil", ""));
            return;
        }
        cmApi = CmApiFactory.createCmApi(UnityPlayer.currentActivity) ;
        cmApi.register(appID , appSecret);
//        String tempStr = "MD5=" + md5;
//        String content = "yxhl://auth?checkCode=" + checkCode + "&" + "key=" + md5 + "&" + "appId=" + CmApiFactory.APPKEY + "&" + tempStr;
//
//        Uri uri = Uri.parse(content);
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//
//        UnityPlayer.currentActivity.startActivity(intent);
    }

    public void registerSyncCallBack(String callBackObjectName,String callBackMethodName) {
        Log.i(TAG , "registerSyncCallBack()") ;
        LinkGameSDK.callBackObjectName = callBackObjectName;
        LinkGameSDK.callBackMethodName = callBackMethodName;
    }

    //分享文字
    public void shareText(String text) {
        Log.i(TAG , "shareText()") ;
        if(cmApi == null){
            Log.e(TAG , "appId , appSecret未初始化") ;
            return;
        }
        cmApi.shareText(text);
    }

    //分享图片
    public void shareImageWithFilePath(String path) {
        Log.i(TAG , "shareImageWithFilePath()") ;
        if(cmApi == null){
            Log.e(TAG , "appId , appSecret未初始化") ;
            return;
        }
        cmApi.shareImageWithFilePath(path);
    }

    //分享链接
    public void shareWebPageWithUrl(String title, String text, String url, String imageUrl) {
        Log.i(TAG , "shareWebPageWithUrl()") ;
        if(cmApi == null){
            Log.e(TAG , "appId , appSecret未初始化") ;
            return;
        }
        cmApi.shareWebWithImageUrl(title , text , url , imageUrl);
    }

    //登录授权
    public void authorization() {
        Log.i(TAG , "authorization()") ;
        if(cmApi == null){
            Log.e(TAG , "appId , appSecret未初始化") ;
            return;
        }
        cmApi.authorization();
    }

    //是否安装游戏互联app
    public boolean isLinkGameInstalled() {
        Log.i(TAG , "isLinkGameInstalled()") ;
        if(cmApi == null){
            Log.e(TAG , "appId , appSecret未初始化") ;
            return false ;
        }
        return cmApi.isAppInstalled();
    }
}
