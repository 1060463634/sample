package com.example.sample.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.sample.R;
import com.example.sample.application.Constants;
import com.example.sample.application.SP_Constants;
import com.example.sample.models.LoginBean;
import com.example.sample.network.MyStringCallback;
import com.example.sample.utils.AESUtils;
import com.example.sample.utils.CommonUtil;
import com.example.sample.utils.SharePreferenceUtil;
import com.example.sample.utils.ToastUtil;
import com.example.sample.widgets.WeiboDialogUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.login_btn)
    Button mLoginBtn;
    @BindView(R.id.register_btn)
    Button mRegisterBtn;
    @BindView(R.id.phone_edt)
    EditText mAccountEdt;
    @BindView(R.id.password_edt)
    EditText mPwdEdt;
    @BindView(R.id.forget_psw_tv)
    TextView mForgetPswTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setTitle(R.string.login_title);
        setBackButtonInVisible();
        initView();

    }


    private void initView(){
        String account = (String) SharePreferenceUtil.get(this, SP_Constants.LOGIN_ACCOUNT,"");

        mAccountEdt.setText(account);
        mLoginBtn.setOnClickListener(this);
        mRegisterBtn.setOnClickListener(this);
        mForgetPswTv.setOnClickListener(this);

    }


    private void login(final String account,final String password){


        if(!CommonUtil.checkMobile(account)){
            ToastUtil.showToast(this, "手机号码格式不正确！");
            return;
        }

        OkHttpUtils
                .get()
                .tag(this)
                .url(Constants.HTTP_SERVER_DOMAIN + "/mobile/login/login20171220.jhtml")
                .addParams("phone", account)
                .addParams("password", password)
                .build()
                .execute(new MyStringCallback<LoginBean>(this, LoginBean.class,true,false){
                    @Override
                    public void onSuccess(LoginBean data) {


                            ToastUtil.showToast(LoginActivity.this,"登录成功");
                            //给密码加密
                            String encryptPassword = AESUtils.encrypt(password,Constants.PASSWORD_ENCRYPT_SEED);
                            SharePreferenceUtil.put(LoginActivity.this, SP_Constants.LOGIN_ACCOUNT, account);

                            SharePreferenceUtil.put(LoginActivity.this,SP_Constants.LOGIN_PASSWORD, encryptPassword);
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();

                    }

                    @Override
                    public void onFailure(String message) {
                        ToastUtil.showToast(LoginActivity.this,"账号或密码错误！");
                    }
                });


    }



    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.login_btn:
                login(mAccountEdt.getText().toString(),mPwdEdt.getText().toString());
                break;
            case R.id.register_btn:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.forget_psw_tv:
                intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);

                break;


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK)
        {
            login(data.getStringExtra("account"), data.getStringExtra("password"));
        }

    }



}
