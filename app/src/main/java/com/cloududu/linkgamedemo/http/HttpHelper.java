package com.cloududu.linkgamedemo.http;

import android.util.Log;

import com.cloududu.linkgamedemo.App;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


/**
 * Created by SongJun on 2017/3/20.
 */
public class HttpHelper {
    private static final String base = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final int len = 36;
    private static final Random random = new Random();



    private static boolean check(String result) {
        try {
            JSONObject object = new JSONObject(result);
            int code = object.optInt("code");
            if (code == 200) {
                return true;
            }

            String message = object.optString("message");
            ToastUtils.showToast(message);
            Log.i("======checkToke=====>", message);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static void post(String url, HashMap<String, String> map, final OkHttpClientManager.OnResultListener listener) {

        ArrayList<OkHttpClientManager.Param> params = OkHttpClientManager.MapToList(map);
        OkHttpClientManager.getInstance().httpPost(url, params, new OkHttpClientManager.OnResultListener() {
            @Override
            public void onSuccess(final String result) {
                App.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (check(result)) {
                            listener.onSuccess(result);
                        } else {
                            listener.onFailure(null);
                        }
                    }
                });

            }

            @Override
            public void onFailure(final Exception e) {
                App.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFailure(e);
                    }
                });

            }
        });
    }

    public interface OnPostFilesListener {
        void onSuccess(String result);

        void onFailure();
    }
//

}
