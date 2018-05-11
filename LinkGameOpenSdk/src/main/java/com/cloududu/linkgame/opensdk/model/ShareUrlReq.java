package com.cloududu.linkgame.opensdk.model;

/**
 * Author: Patter
 * Data:   2018/3/15
 * Email: 401219741@qq.com
 */

public class ShareUrlReq extends BaseReq {
    private String url;
    private String title;
    private String content;
    private String imageUrl;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
