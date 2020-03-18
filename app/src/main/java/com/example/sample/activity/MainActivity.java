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
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sample.R;
import com.example.sample.application.Constants;
import com.example.sample.fragment.MallFragment;
import com.example.sample.fragment.MineFragment;
import com.example.sample.fragment.StudioFragment;
import com.example.sample.principle.PrincipleActivity;
import com.example.sample.utils.CommonUtil;
import com.example.sample.utils.ToastUtil;
import com.example.sample.widgets.MyAlertDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Request;

public class MainActivity extends BaseActivity {


    @BindView(android.R.id.tabhost)
    FragmentTabHost mFragmentTabHost;

    private long startTime;
    private String mTexts[] = { "商城",  "工作台", "我的" };
    private Class mFragments[] = { MallFragment.class,StudioFragment.class,MineFragment.class};
    private int mImages[] = {R.drawable.ic_back,R.drawable.ic_back,R.drawable.ic_back};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initTabHost();

    }

    private void initTabHost(){
        mFragmentTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mFragmentTabHost.getTabWidget().setDividerDrawable(null);
        for (int i = 0; i < mTexts.length; i++) {
            TabHost.TabSpec spec = mFragmentTabHost.newTabSpec(mTexts[i]).setIndicator(getView(i));
            mFragmentTabHost.addTab(spec, mFragments[i], null);

        }
        mFragmentTabHost.setCurrentTab(0);

    }

    private View getView(int i) {
        View view = View.inflate(this, R.layout.item_tab_host, null);
        ImageView img = (ImageView) view.findViewById(R.id.tab_img);
        TextView tv = (TextView) view.findViewById(R.id.tab_tv);
        img.setImageResource(mImages[i]);
        tv.setText(mTexts[i]);
        return view;
    }








    @Override
    public void onBackPressed() {

        long currentTime = System.currentTimeMillis();
        if ((currentTime - startTime) >= 2000) {
            ToastUtil.showToast(MainActivity.this, "再按一次退出");
            startTime = currentTime;
        } else {
            moveTaskToBack(true);
        }
    }






}
