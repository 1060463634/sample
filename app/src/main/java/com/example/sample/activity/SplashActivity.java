package com.example.sample.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.sample.R;
import com.example.sample.application.Constants;
import com.example.sample.application.SP_Constants;
import com.example.sample.models.LoginBean;
import com.example.sample.network.MyStringCallback;
import com.example.sample.utils.AESUtils;
import com.example.sample.utils.CommonUtil;
import com.example.sample.utils.LogUtil;
import com.example.sample.utils.SharePreferenceUtil;
import com.example.sample.utils.ToastUtil;
import com.example.sample.widgets.MyAlertDialog;
import com.example.sample.widgets.VersionDownloadDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Request;

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        checkPermisson();

    }

    private void checkPermisson(){
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
            } else {
                timeLogin();
            }
        } else {
            timeLogin();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                timeLogin();
            } else {
                finish();
                Toast.makeText(this, "请打开权限后重试", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void timeLogin(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                autoLogin();

            }
        },1000);

    }





    private void autoLogin(){
        String account = (String) SharePreferenceUtil.get(this, SP_Constants.LOGIN_ACCOUNT,"");
        String encryptPassword = (String) SharePreferenceUtil.get(this, SP_Constants.LOGIN_PASSWORD,"");
        String password = null;
        try {
            password = AESUtils.decrypt(encryptPassword, Constants.PASSWORD_ENCRYPT_SEED);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)) {

            login(account,password);

        }else{
            gotoLoginActivity();
        }

    }

    private void gotoLoginActivity(){
        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
        finish();

    }

    private void login(final String account,final String password){

        OkHttpUtils
                .get()
                .tag(this)
                .url(Constants.HTTP_SERVER_DOMAIN + "/mobile/login/login20171220.jhtml")
                .addParams("phone", account)
                .addParams("password", password)
                .build()
                .execute(new MyStringCallback<LoginBean>(this, LoginBean.class,false,false){
                    @Override
                    public void onSuccess(LoginBean data) {

                        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onFailure(String message) {
                        gotoLoginActivity();
                    }

                    @Override
                    public void onError(Call call, Exception e, int i) {
                        super.onError(call, e, i);
                        finish();
                    }
                });


    }

    public void createDialog() {
        final MyAlertDialog dialog = new MyAlertDialog(this);
        dialog.show();
        dialog.setText("有新版本可以更新");
        dialog.setPositiveText("更新");
        dialog.setNagtiveText("取消");
        dialog.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                download();
            }
        });
        dialog.setNegativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {

            }
        });

    }


    public void download() {
        final VersionDownloadDialog mVersionDialog = new VersionDownloadDialog(this);
        mVersionDialog.show();
        OkHttpUtils//
                .get()//
                .tag(this)
                .url("http://openbox.mobilem.360.cn/index/d/sid/3970928")
                //.url("http://www.kamdellar.com/upload/app/kamdellar.apk")
                .build()//

                .execute(new FileCallBack(Constants.FILE_DIR, "kamdellar.apk")
                {
                    @Override
                    public void onBefore(Request request, int id) {

                    }

                    @Override
                    public void inProgress(float progress, long total, int id)
                    {
                        mVersionDialog.setProgress((int)(progress * 100));
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast(SplashActivity.this, "下载出错");
                        mVersionDialog.dismiss();
                        finish();
                    }

                    @Override
                    public void onResponse(File file, int id) {

                        mVersionDialog.setText("下载完成");
                        mVersionDialog.dismiss();

                        if (Build.VERSION.SDK_INT >= 24){

                            Uri apkUri = FileProvider.getUriForFile(SplashActivity.this,
                                    SplashActivity.this.getPackageName(), file);
                            Intent install = new Intent(Intent.ACTION_VIEW);
                            install.addCategory(Intent.CATEGORY_DEFAULT);
                            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
                            startActivity(install);
                            finish();
                        } else {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setDataAndType(Uri.fromFile(file),
                                    "application/vnd.android.package-archive");
                            startActivity(intent);
                            finish();
                        }

                    }
                });


    }



}
