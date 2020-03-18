package com.example.sample.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sample.R;
import com.example.sample.application.Constants;
import com.example.sample.models.EmptyBean;
import com.example.sample.models.LoginBean;
import com.example.sample.models.VerCodeBean;
import com.example.sample.network.MyStringCallback;
import com.example.sample.utils.CommonUtil;
import com.example.sample.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.register_btn)
    Button mRegisterBtn;
    @BindView(R.id.phone_edt)
    EditText mAccountEdt;
    @BindView(R.id.password_edt)
    EditText mPwdEdt;
    @BindView(R.id.code_edt)
    EditText mCodeEdt;
    @BindView(R.id.code_btn)
    TextView mGetCodeBtn;


    private String mVerCode = "";
    private TimeCount mTimeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setTitle(R.string.register_title);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTimeCount != null){
            mTimeCount.cancel();
        }
    }

    private void initView(){
        mGetCodeBtn.setOnClickListener(this);
        mRegisterBtn.setOnClickListener(this);
    }


    private void checkPhone(final String phone){
        if (!CommonUtil.checkMobile(phone)) {
            ToastUtil.showToast(RegisterActivity.this, "手机号码格式不正确！");
            return;
        }
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constants.HTTP_SERVER_DOMAIN + "/mobile/register/check_tel.jhtml")
                .addParams("phone", phone)
                .addParams("type", "1")
                .build()
                .execute(new MyStringCallback<EmptyBean>(this, EmptyBean.class,true,false){
                    @Override
                    public void onSuccess(EmptyBean data) {
                        sendCode(phone);
                    }

                    @Override
                    public void onFailure(String message) {
                        ToastUtil.showToast(RegisterActivity.this, message);
                    }
                });

    }

    private void sendCode(String phone){
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constants.HTTP_SERVER_DOMAIN + "/mobile/register/getVerCode20171220.jhtml")
                .addParams("phone", phone)
                .addParams("type", "1")
                .build()
                .execute(new MyStringCallback<VerCodeBean>(this, VerCodeBean.class,true,false){
                    @Override
                    public void onSuccess(VerCodeBean data) {

                            mVerCode = data.getVerCode();
                            ToastUtil.showToast(RegisterActivity.this,"验证码已发送，请注意查看");
                            mTimeCount = new TimeCount(100000, 1000);
                            mTimeCount.start();
                            mGetCodeBtn.setClickable(false);

                    }

                    @Override
                    public void onFailure(String message) {
                        ToastUtil.showToast(RegisterActivity.this,"验证码已发送失败");
                    }
                });


    }

    private boolean checkInfoOk(){

        if (TextUtils.isEmpty(mAccountEdt.getText().toString()))
        {
            ToastUtil.showToast(this,"手机号不能为空");
            return false;
        }
        if (TextUtils.isEmpty(mCodeEdt.getText().toString()))
        {
            ToastUtil.showToast(this,"验证码不能为空");
            return false;
        }
        if (!mCodeEdt.getText().toString().equalsIgnoreCase(mVerCode) )
        {
            ToastUtil.showToast(this,"验证码不正确");
            return false;
        }
        if (mPwdEdt.getText().toString().length() < 6)
        {
            ToastUtil.showToast(this,"密码不能小于6位");
            return false;
        }


        return true;
    }

    private void register(final String phone ,final String password){
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constants.HTTP_SERVER_DOMAIN + "/mobile/register/register.jhtml")
                .addParams("phone", phone)
                .addParams("password", password)
                .build()
                .execute(new MyStringCallback<LoginBean>(this, LoginBean.class,true,false){
                    @Override
                    public void onSuccess(LoginBean data) {

                        ToastUtil.showToast(RegisterActivity.this,"注册成功");
                                Intent intent = new Intent();
                                intent.putExtra("account", phone);
                                intent.putExtra("password", password);
                                setResult(RESULT_OK, intent);
                                RegisterActivity.this.finish();


                    }

                    @Override
                    public void onFailure(String message) {
                        ToastUtil.showToast(RegisterActivity.this,message);
                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.code_btn:
                checkPhone(mAccountEdt.getText().toString());
                break;
            case R.id.register_btn:
                if(checkInfoOk()){
                    register(mAccountEdt.getText().toString(),
                            mPwdEdt.getText().toString());
                }
                break;

        }
    }

    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            mGetCodeBtn.setClickable(true);
            mGetCodeBtn.setText("获取验证码");
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            mGetCodeBtn.setText( millisUntilFinished / 1000 + "s");
        }
    }



}
