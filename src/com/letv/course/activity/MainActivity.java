package com.letv.course.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.course.util.MyTimeUtil;
import com.letv.course.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView main_topbar_spinner;
	private String currentDate;
	private int week;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		initTopBar();
	}

	private void initTopBar() {
		main_topbar_spinner = (TextView) findViewById(R.id.main_title_spinner);

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		String currentDate = sdf.format(date);
		week = MyTimeUtil.getWeeks(currentDate);
System.out.println(week);
		main_topbar_spinner.setText(MyTimeUtil.getTheWeekStrMonthAndDay(week));
		
		main_topbar_spinner.setOnClickListener( (android.view.View.OnClickListener) new TextViewClickEvent());
		

	}

	
	public class TextViewClickEvent implements OnClickListener{

		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			
			Calendar calendar=Calendar.getInstance();
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy.MM.dd");
			Date date=null;
			
			try {
				date=simpleDateFormat.parse(MyTimeUtil.getDateOfWeekAndDay(week, 3));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			calendar.setTime(date);
			
			final DatePickerDialog datePickerDialog;
			datePickerDialog=new DatePickerDialog(getApplicationContext(), new DatePickerDialog.OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
					
				}
			}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			
			datePickerDialog.setOnShowListener(new OnShowListener() {
				
				public void onShow(DialogInterface arg0) {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(datePickerDialog.getCurrentFocus().getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);					
				}
			});
			
			datePickerDialog.show();
			
		}
		
	}
	
}
