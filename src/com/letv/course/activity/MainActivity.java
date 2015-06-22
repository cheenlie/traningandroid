package com.letv.course.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.course.timetable.DateAdapter;
import com.course.timetable.MyTimeUtil;
import com.course.timetable.SpecialCalendar;
import com.letv.course.R;

import android.R.color;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnShowListener;
import android.gesture.Gesture;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.widget.LinearLayout.LayoutParams;

public class MainActivity extends Activity implements OnGestureListener {

	private String TAG = MainActivity.class.getSimpleName();
	private ViewFlipper flipper1 = null;
	private TextView main_topbar_spinner;
	private GridView gridView = null;
	private String currentDate;
	private int week;
	private GridLayout grid;
	private int year_c = 0;
	private int month_c = 0;
	private int day_c = 0;
	private int week_c = 0;
	private int week_num = 0;

	// private TextView tvDate;
	private int currentYear;
	private int currentMonth;
	private int currentWeek;
	private int currentNum;

	private int daysOfMonth = 0; 
	private int dayOfWeek = 0; 
	private int weeksOfMonth = 0;
	private boolean isLeapyear = false; 
	private int selectPostion = 0;
	private String dayNumbers[] = new String[7];

	private SpecialCalendar sc = null;
	private DateAdapter dateAdapter; 
	private RelativeLayout relativelayout_main_all;
	private GestureDetector gestureDetector=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		relativelayout_main_all = (RelativeLayout) findViewById(R.id.relativelayout_main_all);
		
		
		gestureDetector=new GestureDetector(this); 
		flipper1=(ViewFlipper) findViewById(R.id.main_flipper_day);
		dateAdapter=new DateAdapter(this, getResources(), year_c, month_c, week_c, week_num, selectPostion, currentWeek==1?false:true);
		addGridView();
		dayNumbers=dateAdapter.getDayNumbers();
		gridView.setAdapter(dateAdapter);
		//
		gridView.setSelection(selectPostion);
		flipper1.addView(gridView,0);
		
		
		initTopBar();
	}

	private void addGridView() {
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		gridView=new GridView(this);
		gridView.setNumColumns(7);
		gridView.setGravity(Gravity.CENTER_VERTICAL);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gridView.setVerticalSpacing(1);
		gridView.setHorizontalSpacing(1);
		gridView.setLayoutParams(params);
		
	}

	private void initTopBar() {
		main_topbar_spinner = (TextView) findViewById(R.id.main_title_spinner);

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		String currentDate = sdf.format(date);
		week = MyTimeUtil.getWeeks(currentDate);
		System.out.println(week);
		main_topbar_spinner.setText(MyTimeUtil.getTheWeekStrMonthAndDay(week));

		main_topbar_spinner.setOnClickListener(new TextViewClickEvent());

	}

	public class TextViewClickEvent implements	android.view.View.OnClickListener {
		
		@Override
		public void onClick(View arg0) {
			final DatePickerDialog datePickerDialog;
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
			Date date = null;

			try {
				date = simpleDateFormat.parse(MyTimeUtil.getDateOfWeekAndDay(week, 3));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			calendar.setTime(date);
			datePickerDialog = new DatePickerDialog(MainActivity.this,	new DatePickerDialog.OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker arg0, int year,int month, int dayofMonth) {
							Date date = null;
							SimpleDateFormat format = new SimpleDateFormat(	"yyyy.MM.dd");
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
							month = month + 1;
							String monthStr = "" + month;
							String dayofMonthStr = "" + dayofMonth;
							if (month < 10) {
								monthStr = "0" + month;
							}
							if (dayofMonth < 10) {
								dayofMonthStr = "0" + dayofMonth;
							}
							String dateSelectStr = (year + "." + monthStr + "." + dayofMonthStr);
							
							week = MyTimeUtil.getWeeks(dateSelectStr);
							main_topbar_spinner.setText(MyTimeUtil.getTheWeekStrMonthAndDay(week));
//							updategvidlayout(week, grid);
							
							try {
								date = format.parse(dateSelectStr);
							} catch (ParseException e) {
								e.printStackTrace();
							}
							currentDate = sdf.format(date);
							year_c = Integer.parseInt(currentDate.split("-")[0]);
							month_c = Integer.parseInt(currentDate.split("-")[1]);
							day_c = Integer.parseInt(currentDate.split("-")[2]);
							currentYear = year_c;
							currentMonth = month_c;
							sc = new SpecialCalendar();
							getCalendar(year_c, month_c);
							week_num = getWeeksOfMonth();
							currentNum = week_num;
							if (dayOfWeek == 7) {
								week_c = day_c / 7 + 1;
							} else {
								if (day_c <= (7 - dayOfWeek)) {
									week_c = 1;
								} else {
									if ((day_c - (7 - dayOfWeek)) % 7 == 0) {
										week_c = (day_c - (7 - dayOfWeek)) / 7 + 1;
									} else {
										week_c = (day_c - (7 - dayOfWeek)) / 7 + 2;
									}
								}
							}
							currentWeek = week_c;
							getCurrent();
							dateAdapter = new DateAdapter(MainActivity.this,getResources(), currentYear, currentMonth,currentWeek, currentNum, selectPostion,currentWeek == 1 ? true : false);
							flipper1.removeViewAt(0);
						    addGridView();
							dayNumbers = dateAdapter.getDayNumbers();
							gridView.setAdapter(dateAdapter);
							Calendar c111 = Calendar.getInstance();
							if (c111.get(Calendar.YEAR) == dateAdapter.getCurrentYear(selectPostion)
									&& (c111.get(Calendar.MONTH) + 1) == dateAdapter.getCurrentMonth(selectPostion)
									&& c111.get(Calendar.DAY_OF_MONTH) == Integer.parseInt(dayNumbers[selectPostion])) 
							{
								dateAdapter.setSeclection(selectPostion); 
							}
							flipper1.addView(gridView, 0);
						}
					}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

			datePickerDialog.setOnShowListener(new OnShowListener() {

				public void onShow(DialogInterface arg0) {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(datePickerDialog.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
				}
			});
			datePickerDialog.show();
		}
	}
	
	public void getCalendar(int year, int month) {
		isLeapyear = sc.isLeapYear(year); // 是否为闰年
		daysOfMonth = sc.getDaysOfMonth(isLeapyear, month); // 某月的总天数
		dayOfWeek = sc.getWeekdayOfMonth(year, month); // 某月第一天为星期几
	}
	
	public int getWeeksOfMonth() {
		// getCalendar(year, month);
		int preMonthRelax = 0;
		if (dayOfWeek != 7) {
			preMonthRelax = dayOfWeek;
		}
		if ((daysOfMonth + preMonthRelax) % 7 == 0) {
			weeksOfMonth = (daysOfMonth + preMonthRelax) / 7;
		} else {
			weeksOfMonth = (daysOfMonth + preMonthRelax) / 7 + 1;
		}
		return weeksOfMonth;
	}
	
	/**
	 * 判断某年某月所有的星期数
	 * 
	 * @param year
	 * @param month
	 */
	public int getWeeksOfMonth(int year, int month) {
		// 先判断某月的第一天为星期几
		int preMonthRelax = 0;
		int dayFirst = getWhichDayOfWeek(year, month);
		int days = sc.getDaysOfMonth(sc.isLeapYear(year), month);
		if (dayFirst != 7) {
			preMonthRelax = dayFirst;
		}
		if ((days + preMonthRelax) % 7 == 0) {
			weeksOfMonth = (days + preMonthRelax) / 7;
		} else {
			weeksOfMonth = (days + preMonthRelax) / 7 + 1;
		}
		return weeksOfMonth;

	}
	
	/**
	 * 判断某年某月的第一天为星期几
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public int getWhichDayOfWeek(int year, int month) {
		return sc.getWeekdayOfMonth(year, month);
	}
	
	/**
	 * 重新计算当前的年月
	 */
	public void getCurrent() {
		if (currentWeek > currentNum) {
			if (currentMonth + 1 <= 12) {
				currentMonth++;
			} else {
				currentMonth = 1;
				currentYear++;
			}
			currentWeek = 1;
			currentNum = getWeeksOfMonth(currentYear, currentMonth);
		} else if (currentWeek == currentNum) {
			if (getLastDayOfWeek(currentYear, currentMonth) == 6) {
			} else {
				if (currentMonth + 1 <= 12) {
					currentMonth++;
				} else {
					currentMonth = 1;
					currentYear++;
				}
				currentWeek = 1;
				currentNum = getWeeksOfMonth(currentYear, currentMonth);
			}

		} else if (currentWeek < 1) {
			if (currentMonth - 1 >= 1) {
				currentMonth--;
			} else {
				currentMonth = 12;
				currentYear--;
			}
			currentNum = getWeeksOfMonth(currentYear, currentMonth);
			int firstDayOfWeek = sc.getWeekdayOfMonth(currentYear,
					currentMonth + 1);
			if (firstDayOfWeek == 0) {
				// 如果上月第一天是星期日:0,则当前周变成上个月最后一周 =currentNum
				currentWeek = currentNum;
			} else {
				currentWeek = currentNum - 1;
			}
		}

	}
	
	/**
	 * 
	 * @param year
	 * @param month
	 */
	public int getLastDayOfWeek(int year, int month) {
		return sc.getWeekDayOfLastMonth(year, month,
				sc.getDaysOfMonth(isLeapyear, month));
	}

	
	/**
	 * onDown -> onSingleTapUp 方法都是继承自OnGestureListener 
	 */
	
	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
