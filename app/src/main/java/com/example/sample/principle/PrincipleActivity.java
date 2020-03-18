package com.example.sample.principle;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.sample.R;
import com.example.sample.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hello on 2017/9/8.
 */
public class PrincipleActivity extends BaseActivity {

    private ViewPager mViewPager;
    private List<String> mWebLists = new ArrayList<>();
    private PagerAdapter mPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principle);
        mViewPager = (ViewPager) findViewById(R.id.content_viewpager);
        initViewPager();
    }

    private void initViewPager(){

        mWebLists.add("file:///android_asset/principle/life.html");
        mWebLists.add("file:///android_asset/principle/time.html");
        mWebLists.add("file:///android_asset/principle/world.html");
        mPageAdapter = new MyViewPagerAdapter(this,mWebLists);
        mViewPager.setAdapter(mPageAdapter);
    }



}
