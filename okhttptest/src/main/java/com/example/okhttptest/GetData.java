package com.example.okhttptest;
/**
 * 使用OKHttp省去了流操作，使用原生的httpURLconnection需要从conn中获取输入流，然后解析采用bufferedreader来解析。
 */

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 进行HTTP请求，返回数据
 */
public class GetData {
    public static byte[] getImage(String path) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(path)
                .build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            return response.body().bytes();
        }else{
            throw new RuntimeException("请求url失败");
        }
    }

    public static String getHtml(String path) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(path)
                .build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            return response.body().string();
        }else{
            throw new RuntimeException("请求url失败");
        }
    }
    /*
    采用回调接口的方式，就可以不用自己动手创建一个子线程来完成网络请求了，该接口已经自动创建了子线程
     */
    public static void getHtml(String path,okhttp3.Callback callback) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(path)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
