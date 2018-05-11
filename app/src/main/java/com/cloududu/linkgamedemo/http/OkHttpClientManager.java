package com.cloududu.linkgamedemo.http;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by SongJun on 2017/2/28.
 */
public class OkHttpClientManager {
//    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static OkHttpClientManager mInstance;

    private Handler mHandler;

    private OkHttpClient mOkHttpClient;

    private OkHttpClientManager(Application application) {

        X509TrustManager xtm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }
        };
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        File sdcache = application.getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .sslSocketFactory(sslContext.getSocketFactory())
                .hostnameVerifier(DO_NOT_VERIFY)
//                .sslSocketFactory(createSSLSocketFactory())
//                .hostnameVerifier(new TrustAllHostnameVerifier())
                .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
        mOkHttpClient = builder.build();

        mHandler = new Handler(Looper.getMainLooper());
    }

    public static OkHttpClientManager initOkHttpManager(Application application) {
        synchronized (OkHttpClientManager.class) {
            if (mInstance == null) {
                mInstance = new OkHttpClientManager(application);
            }
        }
        return mInstance;
    }

    public static OkHttpClientManager getInstance() {
        return mInstance;
    }

    //////////////////////////////////////////////////DELETE请求START////////////////////////////////////////////
    public void httpDelete(String url, ArrayList<Param> params, final OnResultListener listener) {
        Request request = buildDeleteFormRequest(url, params);
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                listener.onFailure(e);

//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                listener.onSuccess(str);
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                });
            }
        });
    }

    private Request buildDeleteFormRequest(String url, ArrayList<Param> params) {
        if (params == null) {
            params = new ArrayList<>();
        }
        FormBody.Builder builder = new FormBody.Builder();
        for (Param param : params) {
            if (param != null)
                builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .delete(requestBody)
                .build();
    }

    //////////////////////////////////////////////////DELETE请求END////////////////////////////////////////////


    //////////////////////////////////////////////////PUT请求START////////////////////////////////////////////
    public void httpPut(String url, ArrayList<Param> params, final OnResultListener listener) {
        Request request = buildPutFormRequest(url, params);
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onSuccess(str);
                    }
                });
            }
        });
    }

    private Request buildPutFormRequest(String url, ArrayList<Param> params) {
        if (params == null) {
            params = new ArrayList<>();
        }
        FormBody.Builder builder = new FormBody.Builder();
        for (Param param : params) {
            if (param != null)
                builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .put(requestBody)
                .build();
    }

    //////////////////////////////////////////////////PUT请求END////////////////////////////////////////////


    //////////////////////////////////////////////////上传文件START////////////////////////////////////////////

    /**
     * 上传多个文件
     *
     * @param url
     * @param files
     * @param fileKeys
     * @param params
     * @param listener
     */
    public void postFiles(String url, File[] files,
                          String[] fileKeys, ArrayList<Param> params, final OnResultListener listener) {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onSuccess(str);
                    }
                });
            }
        });
    }

    public void postFile(String url, File file,
                         String fileKey, ArrayList<Param> params, final OnResultListener listener) {
        postFiles(url, new File[]{file}, new String[]{fileKey}, params, listener);
    }

    public void postQQFile(String url, File files, String fileKeys, ArrayList<Param> heders, ArrayList<Param> params, final OkHttpClientManager.OnResultListener listener) {
        Request request = buildMultipartFormRequest(url, new File[]{files}, new String[]{fileKeys}, heders, params);
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onSuccess(str);
                    }
                });
            }
        });
    }

    private Request buildMultipartFormRequest(String url, File[] files,
                                              String[] fileKeys, ArrayList<Param> heders, ArrayList<Param> params) {
        if (params == null) {
            params = new ArrayList<>();
        }

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
//        MultipartBuilder builder = new MultipartBuilder()
//                .type(MultipartBuilder.FORM);

        for (OkHttpClientManager.Param param : params) {
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + param.key + "\""),
                    RequestBody.create(null, param.value));
        }
        if (files != null) {
            RequestBody fileBody = null;
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                //TODO 根据文件名设置contentType
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + fileName + "\""),
                        fileBody);
            }
        }
        RequestBody requestBody = builder.build();
        Request.Builder builder1 = new Request.Builder()
                .url(url)
                .post(requestBody);

        if (heders != null && heders.size() > 0) {
            for (int i = 0; i < heders.size(); i++) {
                builder1.addHeader(heders.get(i).key, heders.get(i).value);
            }
        }

        return builder1.build();
    }


    private Request buildMultipartFormRequest(String url, File[] files,
                                              String[] fileKeys, ArrayList<Param> params) {
        if (params == null) {
            params = new ArrayList<>();
        }

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
//        MultipartBuilder builder = new MultipartBuilder()
//                .type(MultipartBuilder.FORM);

        for (Param param : params) {
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + param.key + "\""),
                    RequestBody.create(null, param.value));
        }
        if (files != null) {
            RequestBody fileBody = null;
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                //TODO 根据文件名设置contentType
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + fileName + "\""),
                        fileBody);
            }
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    //////////////////////////////////////////////////上传文件END////////////////////////////////////////////


    //////////////////////////////////////////////////POST请求START////////////////////////////////////////////

    /**
     * POST
     *
     * @param url
     * @param params
     * @param listener
     */
    public void httpPost(String url, ArrayList<Param> params, final OnResultListener listener) {
        RequestBody formBody = makeRequestBody(params).build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .post(formBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onSuccess(str);
                    }
                });
            }
        });
    }

    private FormBody.Builder makeRequestBody(ArrayList<Param> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params == null || params.size() <= 0) {
            return builder;
        }
        for (int i = 0, len = params.size(); i < len; i++) {
            builder.add(params.get(i).key, params.get(i).value);
        }
        return builder;
    }

    //////////////////////////////////////////////////POST请求END////////////////////////////////////////////


    //////////////////////////////////////////////////GET请求START////////////////////////////////////////////

    public void httpGetNotAsync(String url, ArrayList<Param> params, final OnResultListener listener) {
        if (params != null) {
            url = makeGetUrl(url, params);
        }
        Request.Builder requestBuilder = new Request.Builder().url(url)
                .addHeader("Accept", "application/json");
        requestBuilder.method("GET", null);
        Request request = requestBuilder.build();
        Call mcall = mOkHttpClient.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                listener.onFailure(e);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                try {
                    listener.onSuccess(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * get
     *
     * @param url
     * @param params
     * @param listener
     */
    public void httpGet(String url, ArrayList<Param> params, final OnResultListener listener) {
        if (params != null) {
            url = makeGetUrl(url, params);
        }
        Request.Builder requestBuilder = new Request.Builder().url(url)
                .addHeader("Accept", "application/json");
        requestBuilder.method("GET", null);
        Request request = requestBuilder.build();
        Call mcall = mOkHttpClient.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            listener.onSuccess(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    /**
     * get
     *
     * @param url
     */
    public Response httpGetWithOthre(String url) {
        Request.Builder requestBuilder = new Request.Builder().url(url);
        requestBuilder.method("GET", null);
        Request request = requestBuilder.build();
        Call mcall = mOkHttpClient.newCall(request);
        try {
            return mcall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void httpGet(String url, final OnResultListener listener) {
        httpGet(url, null, listener);
    }

    private String makeGetUrl(String url, Param[] params) {
        url = url + "?";
        if (params == null) {
            params = new Param[0];
        }
        for (int i = 0, len = params.length; i < len; i++) {
            url = url + params[i].key + "=" + params[i].value;
            if (i != len - 1) {
                url = url + "&";
            }
        }
        return url;
    }

    private String makeGetUrl(String url, ArrayList<Param> params) {
        url = url + "?";
        if (params == null || params.size() == 0) {
            return url;
        }
        for (int i = 0, len = params.size(); i < len; i++) {
            url = url + params.get(i).key + "=" + params.get(i).value;
            if (i != len - 1) {
                url = url + "&";
            }
        }
        return url;
    }


    //////////////////////////////////////////////////GET请求END////////////////////////////////////////////

    /////////////////////////////////////////////////OTHER/////////////////////////////////////////////////
    public static class Param {
        public Param() {
        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String key;
        public String value;
    }

    public interface OnResultListener {
        void onSuccess(String result);

        void onFailure(Exception e);
    }

    public interface OnResultListenerOther {
        void onSuccess(Response result);

        void onFailure(Exception e);
    }

    public static ArrayList<Param> MapToList(HashMap<String, String> map) {
        ArrayList<Param> params = new ArrayList<>();
        Param param = null;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            param = new Param();
            param.key = entry.getKey();
            param.value = entry.getValue();
            params.add(param);
        }
        return params;
    }
}