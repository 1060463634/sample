package com.example.sample.network;

import android.app.Dialog;
import android.content.Context;


import com.example.sample.R;
import com.example.sample.utils.LogUtil;
import com.example.sample.utils.ToastUtil;
import com.example.sample.widgets.WeiboDialogUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Request;

public abstract class MyStringCallback<T> extends StringCallback {

	private Context mContext;
	private Class mCls;
	private boolean mShowDialog;
	private boolean mIsList;
	private Dialog mDialog;


	public MyStringCallback(Context context, Class cls,
							boolean showDialog,boolean isList) {
		this.mContext = context;
		this.mCls = cls;
		this.mShowDialog = showDialog;
		this.mIsList = isList;
	}

	@Override
	public void onBefore(Request request, int id) {
		super.onBefore(request, id);
		if (mShowDialog) {
			showLoadingDialog();
		}
	}

	@Override
	public void onAfter(int id) {
		super.onAfter(id);
		if (mShowDialog) {
			dismissLoadingDialog();
		}
	}

	@Override
	public void onError(Call call, Exception e, int i) {
		ToastUtil.showToast(mContext, R.string.net_error_no_server);
		LogUtil.d("http onError  :" + e.getMessage());

	}

	@Override
	public void onResponse(String s, int i) {
		LogUtil.d("http onSuccess  :" + s);

			try {
				if(!mIsList){
					CommonJson<T> commonJson = GsonUtils.fromJson(s, mCls);
					if (commonJson.isRequest()) {
						onSuccess(commonJson.getData());
					} else {
						onFailure(commonJson.getErrorMessage());
						onFailure(commonJson.getData());
					}
				}else{
					CommonJsonList<T> commonJsonList = GsonUtils.fromJsonList(s, mCls);
					if (commonJsonList.getStatus() == 1) {
						onSuccess(commonJsonList);
					} else {
						onFailure(commonJsonList.getErrorMessage());
					}
				}

			} catch (Exception e1) {
				e1.printStackTrace();
				ToastUtil.showToast(mContext, "json 解析错误");
				LogUtil.d( "json_parse_error:" + e1.toString());
			}

		}

		/*try {
			JSONObject results = new JSONObject(s);
			if (results.getInt("status") == 1)
			{
				if(!mIsList){
					onSuccess(results);
				}else{
					CommonJsonList<T> commonJsonList = GsonUtils.fromJsonList(s, mCls);
					//onRequestTrue(commonJsonList);
				}
			}
			else
			{
				onFailure(results);
			}
		} catch (JSONException e) {
			ToastUtil.showToast(mContext, "json 解析错误");
			LogUtil.d("json_parse_error ");
			e.printStackTrace();
		}*/




	protected void showLoadingDialog() {
		if (mDialog == null) {
			mDialog = WeiboDialogUtils.createLoadingDialog(mContext, "加载中...");
		}else{
			mDialog.show();
		}

	}

	protected void dismissLoadingDialog() {
		WeiboDialogUtils.closeDialog(mDialog);

	}

	public void onSuccess(CommonJsonList<T> json) {

	}

	public void onSuccess(T data) {

	}

	public void onFailure(String message) {
	}

	public void onFailure(T data) {

	}


}
