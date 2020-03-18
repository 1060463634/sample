package com.example.sample.application;

import android.os.Environment;

/**
 * Created by ameng on 2016/4/13.
 */
public class Constants {
    public static final String FILE_DIR = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/ttmarket/";
    public static final String CACHE_DIR = FILE_DIR + "/cache/";

    public static final String HTTP_SERVER_DOMAIN = "http://www.jsxyjh.com";
    public static final String PASSWORD_ENCRYPT_SEED = "0504888"; //密码加密种子


}

