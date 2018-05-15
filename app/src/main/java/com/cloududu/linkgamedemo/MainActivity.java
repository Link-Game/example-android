package com.cloududu.linkgamedemo;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloududu.linkgamedemo.listener.AuthorizationListener;
import com.cloududu.linkgame.opensdk.api.CmApi;
import com.cloududu.linkgame.opensdk.api.CmApiFactory;


public class MainActivity extends PermissionActivity{
    public static final int REQUEST_IMAGE = 1;
    private CmApi cmApi;
    private ImageView face;
    private TextView name;
    private AuthorizationListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button shareText = (Button) findViewById(R.id.share_text);
        Button shareImg = (Button) findViewById(R.id.share_img);
        final Button shareUrl = (Button) findViewById(R.id.share_url);
        Button login = (Button) findViewById(R.id.login);
        face = (ImageView) findViewById(R.id.face);
        name = (TextView) findViewById(R.id.name);
        cmApi = CmApiFactory.createCmApi(this);
        cmApi.register(App.APPID, App.APP_SECRET);
        shareText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cmApi.shareText("测试分享");
            }
        });
        shareImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission(new CheckPermListener() {
                    @Override
                    public void superPermission() {
                        selectImg();
                    }
                }, "读取相册", Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        });
        shareUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cmApi.shareWebWithImageUrl("测试" , "内容" , "http://waimai.meituan.com/?utm_campaign=baidu&utm_source=1522" ,
                        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1521202494197&di=7ee356766bf05179342a1c5087cb863d&imgtype=0&src=http%3A%2F%2Fp19.qhimg.com%2Ft01cd25856cc2f4520d.png");

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cmApi.authorization();
            }
        });

    }

    private void selectImg() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE:
                    String path = "file://" + getRealPathFromURI(data.getData());
                    cmApi.shareImageWithFilePath(path);
                    break;
            }
        }
    }


    private String getRealPathFromURI(Uri uri) { //传入图片uri地址
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = MainActivity.this.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

//    @Override
//    public void authReq(String refresh_token) {
//        //返回值
//        HashMap<String,String> map = new HashMap<String, String>();
//        map.put("refresh_token",refresh_token);
//        map.put("app_id","1234569bcegijmnoyz");
//        HttpHelper.post("http://api.zhuanxinyu.com/v1/user/get-info", map, new OkHttpClientManager.OnResultListener() {
//            @Override
//            public void onSuccess(String result) {
//                Gson gson = new Gson();
//                UserModel userModel = gson.fromJson(result,UserModel.class);
//                Glide.with(MainActivity.this).load(userModel.getData().getFace()).into(face);
//                name.setText(userModel.getData().getNickname());
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//
//            }
//        });
//    }
}
