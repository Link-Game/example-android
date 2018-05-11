package com.cloududu.linkgame.opensdk.lgapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.cloududu.linkgame.opensdk.domain.Config;
import com.cloududu.linkgame.opensdk.domain.LinkGameSDK;
import com.cloududu.linkgame.opensdk.util.JsonUtil;


/**
 * Author: Patter
 * Data:   2018/4/18
 * Email: 401219741@qq.com
 */

public class LGHandlerActivity extends Activity {
    private static final String TAG = "LinkGame" ;
    public LGHandlerActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.onResp(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.setIntent(intent);
        this.onResp(intent);

    }
    void onResp(Intent intent) {
        if (intent == null) {
            return;
        }
        int type = intent.getIntExtra("type", 0);
        int code = intent.getIntExtra("code", 0);

        if(type == Config.LG_SHARE){
            switch (code){
                case Config.LG_SUCCESS:
                    Log.e(TAG , "LGHandlerActivity==" + "分享成功") ;
                    break;
                case Config.LG_CANCEL:
                    Log.e(TAG , "LGHandlerActivity==" + "分享取消") ;
                    break;
                case Config.LG_PARAMETER_ERROR:
                    Log.e(TAG , "LGHandlerActivity==" + "参数错误") ;
                    break;
                case Config.LG_OTHER_ERROR:
                    Log.e(TAG , "LGHandlerActivity==" + "其他错误") ;
                    break;
            }
        }else if(type == Config.LG_AUTHORIZATION){
            switch (code){
                case Config.LG_SUCCESS:
                    Log.e(TAG , "LGHandlerActivity==" + "授权成功") ;
                    break;
                case Config.LG_CANCEL:
                    Log.e(TAG , "LGHandlerActivity==" + "授权取消") ;
                    break;
                case Config.LG_REFUSED:
                    Log.e(TAG , "LGHandlerActivity==" + "拒绝授权") ;
                    break;
                case Config.LG_PARAMETER_ERROR:
                    Log.e(TAG , "LGHandlerActivity==" + "参数错误") ;
                    break;
                case Config.LG_TIME_OUT:
                    Log.e(TAG , "LGHandlerActivity==" + "授权超时") ;
                    break;
                case Config.LG_OTHER_ERROR:
                    Log.e(TAG , "LGHandlerActivity==" + "其他错误") ;
                    break;
            }
        }

        String message = intent.getStringExtra("message");
        String refresh_token = intent.getStringExtra("refresh_token");
        if(refresh_token == null){
            refresh_token = "" ;
        }
        LinkGameSDK.sendMsg(JsonUtil.formatJson(code, message,
                JsonUtil.formatJson(type, refresh_token)));
    }
}
