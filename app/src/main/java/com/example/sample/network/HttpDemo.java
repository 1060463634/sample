package com.example.sample.network;

import android.content.Context;

import com.example.sample.application.Constants;
import com.example.sample.utils.LogUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by hello on 2017/6/15.
 */
public class HttpDemo {
    private Context mContext;
    private void httGet(){
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constants.HTTP_SERVER_DOMAIN + "/mob/login/login.jhtml")
                .addParams("account", "hyman")
                .addParams("password", "123456")
                .build()
                .execute(new MyStringCallback<Student>(mContext, Student.class,true,true){
                    @Override
                    public void onSuccess(CommonJsonList<Student> json) {
                        super.onSuccess(json);
                    }



                    @Override
                    public void onFailure(String message) {
                        super.onFailure(message);
                    }
                });


    }

    private void postString(){
        OkHttpUtils
                .postString()
                .tag(this)
                .url(Constants.HTTP_SERVER_DOMAIN + "/mob/login/login.jhtml")
                .content(new Gson().toJson(new CommonJson<>()))
                .build()
                .execute(new MyStringCallback<Student>(mContext, Student.class,true,true){


                    @Override
                    public void onSuccess(CommonJsonList<Student> json) {
                        super.onSuccess(json);
                    }



                    @Override
                    public void onFailure(String message) {
                        super.onFailure(message);
                    }


                });
    }

    private void postFile(){
        File file = null;
        File file2 = null;
        String url = "";
        OkHttpUtils.post()//
                .tag(this)
                .addFile("photo", "messenger_01.png", file)
                .addFile("licence", "test1.txt", file2)
                .url(url)
                .addParams("account", "hyman")
                .addParams("password", "123456")
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {

                    }

                    @Override
                    public void onResponse(String s, int i) {

                    }
                });
    }

    private void downloadFile(){
        String url = "http://www.kammall.com/upload/app/androidapk.apk";
        OkHttpUtils//
                .get()//
                .tag(this)
                .url(url)//
                .build()//
                .execute(new FileCallBack(Constants.FILE_DIR, "wangmingle.apk")
                {

                    @Override
                    public void onBefore(Request request, int id) {

                    }

                    @Override
                    public void inProgress(float progress, long total, int id)
                    {
                        LogUtil.d("进度"+ (100 * progress));
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        //Log.e(TAG, "onError :" + e.getMessage());
                    }

                    @Override
                    public void onResponse(File file, int id) {

                        //Log.e(TAG, "onResponse :" + file.getAbsolutePath());

                    }
                });
    }

}
