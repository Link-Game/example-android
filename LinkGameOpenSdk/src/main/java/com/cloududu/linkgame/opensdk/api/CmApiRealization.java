package com.cloududu.linkgame.opensdk.api;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.cloududu.linkgame.opensdk.model.ShareType;
import com.cloududu.linkgame.opensdk.util.AppInfoUtils;
import com.cloududu.linkgame.opensdk.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Patter
 * Data:   2018/3/5
 * Email: 401219741@qq.com
 */

final class CmApiRealization implements CmApi {
    private Context context;
    private String checkCode;
    private String md5;  //签名信息
    private static final String TAG = "LinkGame" ;

    CmApiRealization(Context context) {
        this.context = context;
    }

    @Override
    public void register(String appKey, String appSecret) {
        CmApiFactory.APPKEY = appKey;
        CmApiFactory.APPSECRET = appSecret;
        md5 = AppInfoUtils.getSingInfo(context, context.getPackageName(), AppInfoUtils.MD5);
        checkCode = StringUtils.SHA1(StringUtils.MD5(appKey + appSecret));
    }

    @Override
    public void unRegister() {
        CmApiFactory.APPKEY = "";
        CmApiFactory.APPSECRET = "";
    }

    @Override
    public void shareText(String text) {
        if(!isAppInstalled()){
            Toast.makeText(context , "未安装游戏互联" , Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(CmApiFactory.APPKEY)) {
            Log.e(TAG , "shareText==" + "appKey为空") ;
            return;
        }
        if (TextUtils.isEmpty(CmApiFactory.APPSECRET)) {
            Log.e(TAG , "shareText==" + "appSecret为空") ;
            return;
        }
        if(TextUtils.isEmpty(text)){
            Log.e(TAG , "shareText==" + "text为空") ;
            return;
        }
        Log.e(TAG , "shareText()==" + "text=" + text) ;
        String tempStr = "text=" + text;
        String content = "yxhl://share?checkCode=" + checkCode + "&" + "key=" + md5 + "&" + "appId=" + CmApiFactory.APPKEY
                + "&" + "shareType=" + ShareType.TEXT + "&" + tempStr + "&" + "packageName=" + context.getPackageName();
        Uri uri = Uri.parse(content);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    @Override
    public void shareImageWithFilePath(String filePath) {
        if(!isAppInstalled()){
            Toast.makeText(context , "未安装游戏互联" , Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(CmApiFactory.APPKEY)) {
            Log.e(TAG , "shareImageWithFilePath==" + "appKey为空") ;
            return;
        }
        if (TextUtils.isEmpty(CmApiFactory.APPSECRET)) {
            Log.e(TAG , "shareImageWithFilePath==" + "appSecret为空") ;
            return;
        }
        if(TextUtils.isEmpty(filePath)){
            Log.e(TAG , "shareImageWithFilePath==" + "filePath为空") ;
            return;
        }
        Log.e(TAG , "shareImageWithFilePath()==" + "filePath=" + filePath + "," + "appKey=" + CmApiFactory.APPKEY + "," + "appSecret=" + CmApiFactory.APPSECRET) ;
        String tempStr = "image=" + Base64.encodeToString(filePath.getBytes(), Base64.NO_WRAP);
        String content = "yxhl://share?checkCode=" + checkCode + "&" + "key=" + md5 + "&" + "appId=" + CmApiFactory.APPKEY
                + "&" + "shareType=" + ShareType.IMAGE + "&" + tempStr + "&" + "packageName=" + context.getPackageName();
        Uri uri = Uri.parse(content);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    @Override
    public void shareWebWithFileData(String title, String text, String url, byte[] data) {
        if(!isAppInstalled()){
            Toast.makeText(context , "未安装游戏互联" , Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(CmApiFactory.APPKEY)) {
            Log.e(TAG , "shareWebWithFileData==" + "appKey为空") ;
            return;
        }
        if (TextUtils.isEmpty(CmApiFactory.APPSECRET)) {
            Log.e(TAG , "shareWebWithFileData==" + "appSecret为空") ;
            return;
        }
        if (TextUtils.isEmpty(title)) {
            Log.e(TAG , "shareWebWithFileData==" + "title为空") ;
            return;
        }
        if (TextUtils.isEmpty(text)) {
            Log.e(TAG , "shareWebWithFileData==" + "text为空") ;
            return;
        }
        if (TextUtils.isEmpty(url)) {
            Log.e(TAG , "shareWebWithFileData==" + "url为空") ;
            return;
        }
        if (data == null) {
            Log.e(TAG , "shareWebWithFileData==" + "图片文件为空") ;
            return;
        }
        Log.e(TAG , "shareWebWithFileData()==" + "title=" + title + "," + "text=" + text + ","
                + "url=" + url + "," + "appKey=" + CmApiFactory.APPKEY + "," + "appSecret=" + CmApiFactory.APPSECRET
        ) ;

    }

    @Override
    public void shareWebWithImageUrl(String title , String text , String url , String imageUrl) {
        if(!isAppInstalled()){
            Toast.makeText(context , "未安装游戏互联" , Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(CmApiFactory.APPKEY)) {
            Log.e(TAG , "shareWebWithImageUrl==" + "appKey为空") ;
            return;
        }
        if (TextUtils.isEmpty(CmApiFactory.APPSECRET)) {
            Log.e(TAG , "shareWebWithImageUrl==" + "appSecret为空") ;
            return;
        }
        Log.e(TAG , "shareWebWithImageUrl()==" + "title=" + title + "," + "text=" + text + ","
                + "url=" + url + "," + "imageUrl=" + imageUrl + "," + "appKey=" + CmApiFactory.APPKEY + "," + "appSecret=" + CmApiFactory.APPSECRET) ;
        String tempStr = "text=" + text + "&" + "title="
                + title + "&" + "imageUrl=" + Base64.encodeToString(imageUrl.getBytes(), Base64.NO_WRAP) + "&"
                + "url=" + Base64.encodeToString(url.getBytes(), Base64.NO_WRAP);
        String content = "yxhl://share?checkCode=" + checkCode + "&" + "key=" + md5 + "&" + "appId=" + CmApiFactory.APPKEY
                + "&" + "shareType=" + ShareType.URL + "&" + tempStr + "&" + "packageName=" + context.getPackageName();
        Uri uri = Uri.parse(content);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    @Override
    public void authorization() {
        if (TextUtils.isEmpty(CmApiFactory.APPKEY)) {
            Log.e(TAG , "authorization==" + "appKey为空") ;
            return;
        }
        if (TextUtils.isEmpty(CmApiFactory.APPSECRET)) {
            Log.e(TAG , "authorization==" + "appSecret为空") ;
            return;
        }
        if (TextUtils.isEmpty(md5)) {
            Log.e(TAG , "authorization==" + "该应用未签名") ;
            return;
        }
        Log.e(TAG , "authorization()=="  + "appKey=" + CmApiFactory.APPKEY + "," + "appSecret=" + CmApiFactory.APPSECRET);
        String tempStr = "MD5=" + md5;
        String content = "yxhl://auth?checkCode=" + checkCode + "&" + "key=" + md5 + "&" + "appId=" + CmApiFactory.APPKEY + "&" + tempStr  + "&" + "packageName=" + context.getPackageName();
        Uri uri = Uri.parse(content);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }


    @Override
    public boolean isAppInstalled() {
        Log.e(TAG , "isAppInstalled()=="  + "appKey=" + CmApiFactory.APPKEY + "," + "appSecret=" + CmApiFactory.APPSECRET);
        final PackageManager packageManager = context.getPackageManager();
        // 取得所有的PackageInfo
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        if(!pName.contains("com.cloududu.linkgame")){
            Log.e(TAG , "isAppInstalled==" + "未安装游戏互联") ;
        }
        //判断包名是否在系统包名列表中
        return pName.contains("com.cloududu.linkgame");
    }

}
