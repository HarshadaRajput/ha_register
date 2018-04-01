package com.example.lenovo.myapp2;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by lenovo on 14-Mar-18.
 */

class MakeCall {

    private static OkHttpClient client = new OkHttpClient();
    private static final String DOMAIN = "http://192.168.1.230/";

    static String post(String url, RequestBody formBody, String TAG) throws Exception {
        Request request = new Request.Builder()
                .url(DOMAIN + url)
                .post(formBody)
                .build();

        Response response = client.newCall(request).execute();
        String res = response.body().string();
        Log.e(TAG, "Url -> " + DOMAIN + url + "?" + bodyToString(request) + "\nResponse -> " + res);
        if (response.isSuccessful()) {
            return res;
        } else {
            return null;
        }
    }

    private static String bodyToString(final Request request) {
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
