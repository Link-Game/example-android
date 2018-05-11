package com.cloududu.linkgamedemo.lgapi;
import android.content.Intent;
import android.os.Bundle;
import com.cloududu.linkgame.opensdk.lgapi.LGHandlerActivity;


/**
 * Author: Patter
 * Data:   2018/4/18
 * Email: 401219741@qq.com
 */

public class LGEntryActivity extends LGHandlerActivity {
    private static final String TAG = "LGEntryActivity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.onRep(getIntent());
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.onRep(intent);
    }
    void onRep(Intent intent) {
        finish();
    }
}
