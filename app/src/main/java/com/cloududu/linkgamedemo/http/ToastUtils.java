package com.cloududu.linkgamedemo.http;

/**
 * Created by MS on 2017/5/16.
 */


import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloududu.linkgamedemo.App;
import com.cloududu.linkgamedemo.R;


/**
 * 显示吐司的工具类
 */
public class ToastUtils {
    public static Toast mToast;
    private static Toast toast2;

    /**
     * 显示吐司
     *
     * @param message
     */
    public static void showToast(final String message) {
        if (mToast == null) {
            mToast = Toast.makeText(App.instance, message, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * 显示吐司
     */
    public static void showErrorToast() {
        if (mToast == null) {
            mToast = Toast.makeText(App.instance, R.string.http_error, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(R.string.http_error);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * 显示吐司
     *
     * @param context
     * @param message
     */
    public static void showToast(final Context context, final String message) {
        if (mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * 显示吐司
     *
     * @param context
     * @param messageResId
     */
    public static void showToast(final Context context, final int messageResId) {
        if (mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), messageResId, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(messageResId);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }


    /**
     * 显示有image的toast
     *
     * @param tvStr
     * @param imageResource
     * @return
     */
    public static Toast showToastWithImg(final String tvStr, final int imageResource) {
        if (toast2 == null) {
            toast2 = new Toast(App.instance);
        }
        View view = LayoutInflater.from(App.instance).inflate(R.layout.toast_custom, null);
        TextView tv = view.findViewById(R.id.toast_custom_tv);
        tv.setText(TextUtils.isEmpty(tvStr) ? "" : tvStr);
        ImageView iv = view.findViewById(R.id.toast_custom_iv);
        if (imageResource > 0) {
            iv.setVisibility(View.VISIBLE);
            iv.setImageResource(imageResource);
        } else {
            iv.setVisibility(View.GONE);
        }
        toast2.setView(view);
        toast2.setGravity(Gravity.CENTER, 0, 0);
        toast2.show();
        return toast2;

    }

    public static void showToastView(Context context, final int layout) {
        View v = LayoutInflater.from(context).inflate(layout, null);
        if (mToast == null) {
            mToast = new Toast(context);
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setView(v);
        } else {
            mToast.setView(v);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();

    }

}
