package com.example.sample.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

	/**
	 * 像素转换密度
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 像素转换密度
	 */
	public int px2dip(Context context,float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 判断是否安装APK的方法
	 */
	public static boolean isApkInstalled(Context context, String packageName) {
		try {
			context.getPackageManager().getApplicationInfo(packageName,
					PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (PackageManager.NameNotFoundException e) {
			return false;
		}
	}

	/**
	 * 获取当前程序的版本号
	 */
	public static int getVersionCode(Context context){
		int localVersion = 0;
		try {
			PackageInfo packageInfo = context.getApplicationContext()
					.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			localVersion = packageInfo.versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return localVersion;
	}
	/**
	 * 获取当前程序的版本号
	 */
	public static String getVersionName(Context context){
		String localVersionName = "";
		try {
			PackageInfo packageInfo = context.getApplicationContext()
					.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			localVersionName = packageInfo.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return localVersionName;
	}

	/**
	 * 手机号验证
	 *
	 * @param
	 * @return 验证通过返回true
	 */
	public static boolean checkMobile(String phone) {
		if (TextUtils.isEmpty(phone)) {
			return false;
		}
		if (phone.length() != 11) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");

		Matcher matcher = pattern.matcher(phone);

		if (matcher.matches()) {
			return true;
		}
		return false;
	}




	public static boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/*屏幕高度*/
	public static int getScreenWidth(final Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	/*屏幕宽度*/
	public static int getScreenHeight(final Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}



}