package com.cloududu.linkgame.opensdk.api;

public interface CmApi {
    void register(String appKey,String appSecret);
    void unRegister();
    void shareText(String text);
    void shareImageWithFilePath(String filePath );
    void shareWebWithFileData(String title , String text , String url , byte[] data);
    void shareWebWithImageUrl(String title , String text , String url , String imageUrl);
    void authorization();
    boolean isAppInstalled();
}
