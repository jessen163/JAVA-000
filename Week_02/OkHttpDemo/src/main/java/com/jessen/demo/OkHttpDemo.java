package com.jessen.demo;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * okhttp 请求http 8801端口 demo
 */
public class OkHttpDemo {
    public static final String URL = "http://localhost:8801";

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(URL).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                System.out.println(response.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
