package com.example.sample.widgets;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.sample.R;

public class MyPopupWindow {


    private PopupWindow popupWindow;
    private Activity mActivity;
    private View view;

    private LinearLayout mQiangLayout;

    /**
     *
     * 构造函数
     * @param view 在这个控件下面
     */
    public MyPopupWindow(View view,Activity activity) {
        this.view = view;
        this.mActivity = activity;
        initPopupWindow();
    }

    public void show() {
        if (popupWindow == null) {
            return;
        }
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAsDropDown(view, 0, 0);

            //popupWindow.showAtLocation(view, Gravity.BOTTOM,0, 0);
            backgroundAlpha(0.5f);
        }

    }

    /**
     * 初始化
     */
    public void initPopupWindow() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.my_alert_dialog, null);
        mQiangLayout = view.findViewById(R.id.button1);


        mQiangLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });



        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.setAnimationStyle(R.style.my_popwindow_anim_style);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });

    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mActivity.getWindow().setAttributes(lp);
    }

}
