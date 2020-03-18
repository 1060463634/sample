package com.example.sample.application;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import okhttp3.OkHttpClient;


/**
 * For developer startup JPush SDK
 *
 * 一般建议在自定义 Application 类里初始化。也可以在主 Activity 里。
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initOkHttp();
    }


    private void initOkHttp(){
        CookieJarImpl cookieJar = new CookieJarImpl
                (new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();

        OkHttpUtils.initClient(okHttpClient);

    }
}
