package com.cloududu.linkgamedemo.model;

/**
 * Author: Patter
 * Data:   2018/4/8
 * Email: 401219741@qq.com
 */

public class UserModel {

    /**
     * code : 200
     * message : success
     * data : {"nickname":"费费","face":"http://miliao-1255542050.cosgz.myqcloud.com/face/51-jnKRTW1522807190.jpg","open_id":"4181ec731737e2f8b581e48b96f6e081dadf4861"}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * nickname : 费费
         * face : http://miliao-1255542050.cosgz.myqcloud.com/face/51-jnKRTW1522807190.jpg
         * open_id : 4181ec731737e2f8b581e48b96f6e081dadf4861
         */

        private String nickname;
        private String face;
        private String open_id;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getOpen_id() {
            return open_id;
        }

        public void setOpen_id(String open_id) {
            this.open_id = open_id;
        }
    }
}
