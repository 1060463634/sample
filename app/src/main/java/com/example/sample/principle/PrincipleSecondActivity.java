package com.example.sample.principle;

import android.os.Bundle;
import android.webkit.WebView;

import com.example.sample.R;
import com.example.sample.activity.BaseActivity;
import com.example.sample.widgets.MyWebView;

/**
 * Created by hello on 2017/9/8.
 */
public class PrincipleSecondActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url = getIntent().getStringExtra("url");

        setContentView(R.layout.web_layout);
        MyWebView web = findViewById(R.id.webView);
        web.loadUrl(url);
        setTitle("");
    }



}
