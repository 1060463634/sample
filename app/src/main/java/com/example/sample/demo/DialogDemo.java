package com.example.sample.demo;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.sample.utils.DateUtil;


public class DialogDemo {

	private void showDatePickDlg(Context context,final TextView textView) {
		final Calendar calendar = Calendar.getInstance();
		if (!TextUtils.isEmpty(textView.getText().toString())) {
			calendar.setTime(DateUtil.string2date(textView.getText().toString()));
		}
		DatePickerDialog datePickerDialog = new DatePickerDialog(context,
				new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				calendar.set(year,monthOfYear,dayOfMonth);
				textView.setText(DateFormat.format("yyyy-MM-dd", calendar));
			}
		}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		datePickerDialog.show();

	}


}
