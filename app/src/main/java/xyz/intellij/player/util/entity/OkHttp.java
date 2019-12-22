package xyz.intellij.player.util.entity;

import android.util.Log;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.ConnectionSpec;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttp {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    //OkHttpClient client = new OkHttpClient();
    OkHttpClient client = new OkHttpClient.Builder()
            .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS))
            .build();
    public String post(String url, String json) throws IOException {//post请求，返回String类型
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .addHeader("Content-Type","application/json")//添加头部
                .url(url)
                .post(body)
                .build();
        String send = request.body().toString();
        try (Response response = client.newCall(request).execute()) {
            Log.e("OKHTTP_SEND",send);
            String res = response.body().string();
            Log.e("OKHTTP_RES",res);
            return res;
        }
    }
}
