package com.example.sample.widgets;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sample.R;


public class VersionDownloadDialog extends Dialog {
    private TextView mTextTv;
    private ProgressBar mProgressBar;

    public VersionDownloadDialog(Context context) {
        super(context, R.style.alert_dialog);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_version_downloading_layout);
        mProgressBar = findViewById(R.id.progress_bar);
        mTextTv = findViewById(R.id.downloading_tv);

    }

    public void setText(String text ){
        mTextTv.setText(text);
    }

    public void setProgress(int progress){
        mProgressBar.setProgress(progress);
    }



}
