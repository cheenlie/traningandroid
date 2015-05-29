package com.letv.course.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.course.util.MyTimeUtil;
import com.letv.course.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
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

	}

}
