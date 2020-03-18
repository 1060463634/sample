package com.example.sample.activity;

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
import com.example.sample.models.VerCodeBean;
import com.example.sample.network.MyStringCallback;
import com.example.sample.utils.CommonUtil;
import com.example.sample.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.ok_btn)
    Button mOkBtn;
    @BindView(R.id.phone_edt)
    EditText mAccountEdt;
    @BindView(R.id.password_edt)
    EditText mPwdEdt;
    @BindView(R.id.code_edt)
    EditText mCodeEdt;
    @BindView(R.id.code_btn)
    TextView mGetCodeBtn;


    String mVerCode = "";
    TimeCount mTimeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        ButterKnife.bind(this);
        setTitle(R.string.forget_pwd_title);
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
        mOkBtn.setOnClickListener(this);

        mAccountEdt.addTextChangedListener(new EditChangedListener());
        mPwdEdt.addTextChangedListener(new EditChangedListener());
        mCodeEdt.addTextChangedListener(new EditChangedListener());

    }


    private void checkPhone(final String phone){
        if (!CommonUtil.checkMobile(phone)) {
            ToastUtil.showToast(this, "手机号码格式不正确！");
            return;
        }

        sendCode(phone);

    }



    private void sendCode(String phone){
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constants.HTTP_SERVER_DOMAIN + "/mobile/register/getVerCode20171220.jhtml")
                .addParams("phone", phone)
                .addParams("type", "2")
                .build()
                .execute(new MyStringCallback<VerCodeBean>(this, VerCodeBean.class,true,false){
                    @Override
                    public void onSuccess(VerCodeBean data) {

                            mVerCode = data.getVerCode();
                            ToastUtil.showToast(ForgetPasswordActivity.this,"验证码已发送，请注意查看!");
                            mTimeCount = new TimeCount(100000, 1000);
                            mTimeCount.start();
                            mGetCodeBtn.setClickable(false);

                    }


                    @Override
                    public void onFailure(String message) {
                        ToastUtil.showToast(ForgetPasswordActivity.this,"该手机号尚未注册");
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
        if (!mCodeEdt.getText().toString().equalsIgnoreCase(mVerCode))
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

    private void changePwd(final String phone ,final String password){
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constants.HTTP_SERVER_DOMAIN + "/mobile/password/reset20171220.jhtml")
                .addParams("phone", phone)
                .addParams("newPassword", password)
                .build()
                .execute(new MyStringCallback<EmptyBean>(this, EmptyBean.class,true,false){
                    @Override
                    public void onSuccess(EmptyBean data) {

                        ToastUtil.showToast(ForgetPasswordActivity.this,"密码修改成功");
                        finish();

                    }

                    @Override
                    public void onFailure(String message) {
                        ToastUtil.showToast(ForgetPasswordActivity.this,message);
                    }
                });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.code_btn:
                checkPhone(mAccountEdt.getText().toString());
                break;
            case R.id.ok_btn:
                if(checkInfoOk()){
                    changePwd(mAccountEdt.getText().toString(),mPwdEdt.getText().toString());
                }
                break;
            case R.id.back_img:
                finish();
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


    class EditChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {

            if(!TextUtils.isEmpty(mAccountEdt.getText().toString()) &&
                    !TextUtils.isEmpty(mPwdEdt.getText().toString()) &&
                    !TextUtils.isEmpty(mCodeEdt.getText().toString())){
                mOkBtn.setEnabled(true);

            }else{
                mOkBtn.setEnabled(false);
            }
        }


    }

}
