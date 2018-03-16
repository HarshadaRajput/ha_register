package com.example.lenovo.myapp2;

import android.util.Log;

import java.io.IOException;
import java.nio.Buffer;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by lenovo on 14-Mar-18.
 */

public class MakeCall {

    private static OkHttpClient client = new OkHttpClient();

    public static String post(String url,RequestBody formBody,String TAG) throws Exception {
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Response response = client.newCall(request).execute();
        String res = response.body().string();
        Log.e(TAG,"Url -> "+url+"?"+bodyToString(request)+"\nResponse -> "+res);
        if (response.isSuccessful()){
            return res;
        }else {
            return null;
        }
    }
    public static String dummyPost(String url,RequestBody formBody,String TAG) throws Exception {
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Log.e(TAG,"dummyPost() Url -> "+url+"?"+bodyToString(request));
        return null;
    }
    private static String bodyToString(final Request request){
        try {
            final Request copy = request.newBuilder().build();
            final okio.Buffer buffer = new okio.Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
