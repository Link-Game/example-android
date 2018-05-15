package com.cloududu.linkgamedemo.lgapi;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cloududu.linkgame.opensdk.domain.Config;
import com.cloududu.linkgame.opensdk.lgapi.LGHandlerActivity;


/**
 * Author: Patter
 * Data:   2018/4/18
 * Email: 401219741@qq.com
 */

public class LGEntryActivity extends LGHandlerActivity {
    private static final String TAG = "LGEntryActivity" ;

    //如果授权成功，则返回有值的refresh_token，否则为空字符串
    @Override
    protected void onResp(int type, int code, String refresh_token)
    {
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
                    Toast.makeText(this, "refresh_token="+refresh_token, Toast.LENGTH_SHORT).show();
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
        //本Activity为中转Activity，获取完回调需要finish掉
        this.finish();
    }
}
