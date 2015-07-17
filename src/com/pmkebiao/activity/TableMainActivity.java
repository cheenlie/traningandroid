package com.pmkebiao.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.letv.course.R;
import com.pmkebiao.dao.SingleClass;
import com.pmkebiao.db.*;
import com.pmkebiao.timetable.DateAdapter;
import com.pmkebiao.timetable.MyTimeUtil;
import com.pmkebiao.timetable.SpecialCalendar;
import com.pmkebiao.util.Constant;

import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnShowListener;
import android.gesture.Gesture;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.LinearLayout.LayoutParams;

public class TableMainActivity extends Activity implements OnGestureListener {

	private String TAG = TableMainActivity.class.getSimpleName();
	private ViewFlipper flipper_day = null;
	private TextView main_topbar_spinner;
	private GridView gridView = null;
	private String currentDate;
	private int weeks_nowBetween20140922;
	private GridLayout timeListGrid;
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
	private RelativeLayout relativelayout_main_contain_flipper_timelist;
	private GestureDetector gestureDetector=null;
	/*
	 * updateGridlayout方法专属变量
	 */
	View saveView;
	ArrayList<View> tv_id = new ArrayList<View>();
	int update_count = 0;   //更新网格的次数统计

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		relativelayout_main_contain_flipper_timelist = (RelativeLayout) findViewById(R.id.relativelayout_main_all_contain_flipper_timelist);
		timeListGrid = (GridLayout) findViewById(R.id.timeList_gridlayout);
		flipper_day=(ViewFlipper) findViewById(R.id.main_flipper_day);
		
		gestureDetector=new GestureDetector(this); 
		dateAdapter=new DateAdapter(this,  year_c, month_c, week_c, week_num, selectPostion, currentWeek==1?false:true);
//		dateAdapter=new DateAdapter(this, getResources(), year_c, month_c, week_c, week_num, selectPostion, currentWeek==1?false:true);
		addGridView();
		//向flipper中添加的数据
		dayNumbers=dateAdapter.getDayNumbers();
		gridView.setAdapter(dateAdapter);
		gridView.setSelection(selectPostion);
		//更改星期日上方的数据
//		flipper_day.addView(gridView,0);
		flipper_day.addView(gridView);
		
		//获取系统当前时间
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
		currentDate = simpleDateFormat.format(date);
		weeks_nowBetween20140922 = MyTimeUtil.getWeeks(currentDate);
		
		initTopBar();
		
		new Thread(new Runnable() {

			public void run() {
				
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						try {
							updateGridlayout(weeks_nowBetween20140922, timeListGrid);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		}).start();
	}
	
	private boolean setbackground = true;
	public static int childid;   //
	public static int weekAdd;
	View vSave;    //
	int height = 0;
	int heightTotal = 0;
	int scrollHeight;
	int scrollHeightOne;
	int widPx;  //
	
	ScrollView scrollView1;  //
	
	 /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     * 
     * dp=dip,不同手机显示效果不同，不依赖像素，
     * px不同设备显示效果一样
     * sp: scaled pixels(放大像素). 主要用于字体显示best for textsize
     */
	public int dipTopx(Context context, float pxValue) {

		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue * scale + 0.5f);
	}
	
	
	private void updateGridlayout(int week, GridLayout grid) {
		
		scrollHeight = 0;
		heightTotal = (dipTopx(TableMainActivity.this, 56));
		scrollHeightOne = (dipTopx(TableMainActivity.this, 60));
		weekAdd = week;
		height = (dipTopx(TableMainActivity.this, 60)) / 6 - 4;
		
		try {
			if (tv_id.size() == 0) {
			} else {
				for (int i = 0; i < tv_id.size(); i++) {
					grid.removeView(tv_id.get(i));
				}
				tv_id.clear();
			}
			
			if (update_count == 0) {
				for (int i = 1; i < grid.getColumnCount(); i++) {
					for (int j = 0; j < (grid.getRowCount()); j++) {

						/**
						 * 显示每个小单元格
						 */
						TextView btn = new TextView(this);
						btn.setWidth(widPx - 4);
						btn.setHeight(height);
						btn.setText("  ");
						btn.setGravity(Gravity.CENTER);
						btn.setTextColor(Color.DKGRAY);

						GridLayout.Spec rowSpec = GridLayout.spec(j); // 设置它的行和列
						GridLayout.Spec columnSpec = GridLayout.spec(i);
						GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
						params.setGravity(Gravity.FILL_VERTICAL);
						RelativeLayout mylayout = new RelativeLayout(this);
						RelativeLayout.LayoutParams s = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
						s.addRule(RelativeLayout.CENTER_IN_PARENT, -1);
						mylayout.setPadding(2, 2, 2, 2);
						mylayout.addView(btn, s);
						grid.addView(mylayout, params);

					}
				}
			} else {

			}
			if (update_count == 0) {
				/**
				 * 6个小格合并为一个大格，用于处理点击添加课程事件，显示加号
				 */
				for (int i = 1; i < grid.getColumnCount(); i++) {
					for (int j = 0; j < (grid.getRowCount() / 6); j++) {
						TextView btn = new TextView(this);
						btn.setWidth(widPx - 4);
						btn.setHeight(heightTotal);
						btn.setText("  ");
						btn.setGravity(Gravity.CENTER);
						btn.setTextColor(Color.DKGRAY);
						final int week1 = week;
						final int day = i - 1;
						final int starttime = j + 8;

						btn.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								if (vSave != null) {
									if (vSave == v) {
										vSave.setBackground(null);
										vSave = null;
										AlertDialog.Builder builder = new AlertDialog.Builder(TableMainActivity.this);
										builder.setMessage("添加课程")
												.setCancelable(true)
												.setPositiveButton(
														"课外班",
														new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int id) {
																setbackground = false;
																Intent intent = new Intent(TableMainActivity.this,
																		AddCourseActivity.class);
																intent.putExtra(
																		"weekno",
																		weekAdd);
																intent.putExtra(
																		"weekday",
																		day);
																intent.putExtra(
																		"starttime",
																		starttime);
																Constant.START_ADDCOURSEACTIVITY = Constant.ADD_START;
																startActivity(intent);
															}
														})
												.setNegativeButton(
														"校内课",
														new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int id) {
																setbackground = false;
//																Intent intent = new Intent(TableMainActivity.this,	AddClassXiaoneiActivity.class);
//																intent.putExtra(
//																		"weekno",
//																		weekAdd);
//																intent.putExtra(
//																		"weekday",
//																		day);
//																intent.putExtra(
//																		"starttime",
//																		starttime);
//																Constant.START_ADDCOURSEACTIVITY = Constant.ADD_START;
//																startActivity(intent);
															}
														});
										// 用对话框构造器创建对话框
										AlertDialog alert = builder.create();
										alert.show();

									} else {
										vSave.setBackground(null);
										v.setBackgroundResource(R.drawable.jiahao_1);
										v.getBackground().setAlpha(70);
										vSave = v;
									}
								} else {
									vSave = v;
									vSave.setBackgroundResource(R.drawable.jiahao_1);
									vSave.getBackground().setAlpha(70);
								}
							}
						
						});
						
						GridLayout.Spec rowSpec = GridLayout.spec(j * 6, 6); // 设置它的行和列
						GridLayout.Spec columnSpec = GridLayout.spec(i);
						GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
						params.setGravity(Gravity.FILL_VERTICAL);
						// grid.addView(btn, params);
						RelativeLayout mylayout = new RelativeLayout(this);
						RelativeLayout.LayoutParams s = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
						s.addRule(RelativeLayout.CENTER_IN_PARENT, -1);
						mylayout.setPadding(2, 2, 2, 2);
						mylayout.addView(btn, s);
						grid.addView(mylayout, params);

					}
				}
			} else {

			}

			DBOperation dbo = new DBOperation();

			ArrayList<SingleClass> al_sc = dbo.return_week_total_class(this,week, childid);
			if (al_sc.size() > 0) {
				scrollHeight = Integer.parseInt(al_sc.get(0).getClass_starttime().split(":")[0]) - 8;
			}
			
			for (int i = 0; i < al_sc.size(); i++) {

				SingleClass sc = al_sc.get(i);
				TextView tv_sc = new TextView(this);

				tv_sc.setWidth(widPx - 4);
				tv_sc.setGravity(Gravity.CENTER);
				tv_sc.setTextColor(Color.WHITE);
				tv_sc.setMaxLines(2);
				tv_sc.setEllipsize(TruncateAt.END);
				if (sc.getClass_type().equals("文化课")) {
					tv_sc.setBackgroundResource(R.drawable.corner_view_wh);
				} else if (sc.getClass_type().equals("艺术课")) {
					tv_sc.setBackgroundResource(R.drawable.corner_view_ys);
				} else if (sc.getClass_type().equals("体育课")) {
					tv_sc.setBackgroundResource(R.drawable.corner_view_ty);
				} else if (sc.getClass_type().equals("其他")) {
					tv_sc.setBackgroundResource(R.drawable.corner_view_qt);
				} else {
					tv_sc.setBackgroundResource(R.drawable.corner_view_1);
				}

				tv_sc.setText(sc.getClass_name());
				final SingleClass sc2 = sc;
				tv_sc.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						/**
						 * 汪修改 点击启动展示界面
						 */
						setbackground = false;
//						Intent intent = new Intent(TableMainActivity.this,ShowClassActivity.class);
//						Bundle data = new Bundle();
//						data.putSerializable("sc", sc2);
//						intent.putExtras(data);
//						startActivity(intent);
						Log.d("ccccc", "danjikecheng");
					}
				});

				String starttimearr[] = sc.getClass_starttime().split(":");
				String stoptimearr[] = sc.getClass_finishtime().split(":");

				int start_time = Integer.parseInt(starttimearr[0]);
				int finish_time = Integer.parseInt(stoptimearr[0]);
				int start_min;
				int finish_min;
				if (starttimearr.length > 1 && stoptimearr.length > 1) {
					start_min = Integer.parseInt(starttimearr[1]);
					finish_min = Integer.parseInt(stoptimearr[1]);
				} else {
					start_min = 0;
					finish_min = 0;
				}

				int space = 1;
				space = (finish_time - start_time) * 6
						+ (finish_min / 10 - start_min / 10);
				if (space <= 4) {
					tv_sc.setMaxLines(1);
					tv_sc.setEllipsize(TruncateAt.END);
				}
				if (space <= 2) {
					tv_sc.setText("");
					tv_sc.setTextSize(3);
				}
				int weekday = (sc.getWeek_day() + 1);
				int starttime = (start_time - 8);
				if (starttime < scrollHeight) {
					scrollHeight = starttime;
				}
				GridLayout.Spec rowSpec = GridLayout.spec(
						(starttime * 6 + (start_min / 10)), space);
				GridLayout.Spec columnSpec = GridLayout.spec(weekday);
				GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
				params.setGravity(Gravity.FILL_VERTICAL);
				RelativeLayout mylayout = new RelativeLayout(this);
				RelativeLayout.LayoutParams s = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
				s.addRule(RelativeLayout.CENTER_IN_PARENT, -1);
				mylayout.setPadding(2, 2, 2, 2);
				mylayout.addView(tv_sc, s);
				grid.addView(mylayout, params);
				tv_id.add(mylayout);
			}
			update_count++;

			if (scrollHeight != 0) {
				Handler mHandler = new Handler();
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						scrollView1.scrollTo(0,	(scrollHeight * scrollHeightOne));
						Log.e("height", scrollHeight + "");
					}
				});
			} else {
				Handler mHandler = new Handler();
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						/**
						 * 滑动到顶部
						 */
						scrollView1.scrollTo(0, 0);

					}
				});
			}
		} catch (Exception e) {
			Log.d("111", e.toString());
		}

		/*
		try {
			if (tv_id.size() == 0) {
			} else {
				for (int i = 0; i < tv_id.size(); i++) {
					grid.removeView(tv_id.get(i));
				}
				tv_id.clear();

			}
			
			if (update_count == 0) {
				int m=grid.getColumnCount();  //8
				int n=grid.getRowCount();     //18
				for (int i = 1; i < grid.getColumnCount(); i++)
					for (int j = 0; j < grid.getRowCount(); j++) {
						TextView btTextView = new TextView(this);
						btTextView.setWidth(139);
						btTextView.setHeight(100);
						btTextView.setText("  ");
						btTextView.setGravity(Gravity.CENTER);

						btTextView.setTextColor(Color.DKGRAY);
						final int week1 = week;
						final int day = i - 1;
						final int starttime = j + 6;

						btTextView.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								
								//saveView保存某日某时课程的view
								if (saveView != null) {
									if (saveView == v) {
										saveView.setBackground(null);
										saveView = null;
										Intent intent = new Intent(TableMainActivity.this,AddCourseActivity.class);
										intent.putExtra("weekno", week1);
										intent.putExtra("weekday", day);
										intent.putExtra("starttime", starttime);
										Constant.START_ADDCOURSEACTIVITY = Constant.ADD_START;
										startActivity(intent);
									} else {
										saveView.setBackground(null);
										v.setBackgroundResource(R.drawable.jiahao_1);
										v.getBackground().setAlpha(70);
										saveView = v;
									}
								} else {
									saveView = v;
									saveView.setBackgroundResource(R.drawable.jiahao_1);
									saveView.getBackground().setAlpha(70);
								}
							}

							
						});
						//确定行号和列号
						GridLayout.Spec rowSpec = GridLayout.spec(j); 
						GridLayout.Spec columnSpec = GridLayout.spec(i);
						//Constructs a new LayoutParams instance for this rowSpec and columnSpec
						GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
						params.setGravity(Gravity.FILL_VERTICAL);
						
						RelativeLayout mylayout = new RelativeLayout(this);
						RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
						layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, -1);
						//setPadding(int left, int top, int right, int bottom)
						mylayout.setPadding(26, 16,26, 16);
						mylayout.addView(btTextView, layoutParams);
						
						//在网格指定列和行号中添加textview控件
						grid.addView(mylayout, params);

					}
			} 
			
		} catch (Exception e) {
			
		}*/

	}

	/**
	 * 绘制一周7天的网格，既星期上面一行表示天的数字
	 */
	private void addGridView() {
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		gridView=new GridView(this);
		gridView.setNumColumns(7);
		gridView.setGravity(Gravity.CENTER_VERTICAL);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gridView.setVerticalSpacing(1);   //定义两列之间的默认的水平距离
		gridView.setHorizontalSpacing(1);  //Defines the default vertical spacing between rows. 
		
		gridView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return TableMainActivity.this.gestureDetector.onTouchEvent(event);
			}
		});
		
		gridView.setLayoutParams(params);
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		return this.gestureDetector.onTouchEvent(event);
	}
	
	private void initTopBar() {
		main_topbar_spinner = (TextView) findViewById(R.id.main_title_spinner);

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		String currentDate = sdf.format(date);
		weeks_nowBetween20140922 = MyTimeUtil.getWeeks(currentDate);
		System.out.println(weeks_nowBetween20140922);
		main_topbar_spinner.setText(MyTimeUtil.getTheWeekStrMonthAndDay(weeks_nowBetween20140922));

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
				date = simpleDateFormat.parse(MyTimeUtil.getDateOfWeekAndDay(weeks_nowBetween20140922, 3));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			calendar.setTime(date);
			datePickerDialog = new DatePickerDialog(TableMainActivity.this,	new DatePickerDialog.OnDateSetListener() {

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
							
							weeks_nowBetween20140922 = MyTimeUtil.getWeeks(dateSelectStr);
							main_topbar_spinner.setText(MyTimeUtil.getTheWeekStrMonthAndDay(weeks_nowBetween20140922));
							updateGridlayout(weeks_nowBetween20140922, timeListGrid);
							
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
							dateAdapter = new DateAdapter(TableMainActivity.this,getResources(), currentYear, currentMonth,currentWeek, currentNum, selectPostion,currentWeek == 1 ? true : false);
							flipper_day.removeViewAt(0);
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
							Log.i(TAG,"③ onDateSet");
							flipper_day.addView(gridView, 0);
						}
					}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

			datePickerDialog.setOnShowListener(new OnShowListener() {

				public void onShow(DialogInterface arg0) {
					Log.i(TAG,"② onShow");
//					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//					imm.hideSoftInputFromWindow(datePickerDialog.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
				}
			});

			Log.i(TAG,"① show datePickerDialog");
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
		return sc.getWeekDayOfLastMonth(year, month,sc.getDaysOfMonth(isLeapyear, month));
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
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,	float arg3) {
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
