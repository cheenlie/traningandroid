package com.pmkebiao.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import com.pmkebiao.course.R;
import com.pmkebiao.dao.ChildInfo;
import com.pmkebiao.dao.ClassInfo;
import com.pmkebiao.dao.SingleClass;
import com.pmkebiao.db.DBOperation;
import com.pmkebiao.timetable.MyTimeUtil;
import com.pmkebiao.util.Constant;
import com.pmkebiao.util.NetworkState;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class AddClassXiaoneiActivity extends Activity implements OnTouchListener {
	private String location_tem;
	private int isFirst = 0;
	private boolean isFirstSet = true;
	private int addWeekNo;
	private int addDay;
	private int addStartTime;
	private int term;// ��ǵڼ�ѧ�� 1-��һѧ�ڣ�9��1��-1��15�գ� 2-�ڶ�ѧ�ڣ�3��1��-6��30�գ�
	/**
	 * ��¼��ӿγ���ʼ��Ϊ�ڼ���
	 */
	private int week;

	private boolean addFinished = false;

	/*
	 * ���ܴ��ݵĿγ���Ϣ��
	 */
	private SingleClass scShow;

	private ClassInfo cf;

	private final int BEFORE_NUMBER = 8;
	private final int BEHIND_NUMBER = 10;
	private String TAG = AddCourseActivity.class.getSimpleName();
	private String class_name; // �γ��� ��ѡ
	private String class_type; // �γ���� ��ѡ
	private int class_start_week = 1; // ��ʼ�� ��ѡ
	private int class_stop_week = 1;// ������
	private int timers_total; // �ܴ��� ��ѡ
	private int timers_oneweek = 1; // ÿ�ܴ��� ��ѡ
	private String[] Class_each_week; // ÿ���ܼ���
	private String[] Class_starttime; // ��ʼʱ��
	private String[] Class_finnishtime; // �¿�ʱ��
	// private int class_cut; // ���ڿ۳����� ��ѡ
	private String address; // �Ͽεص� ��ѡ ͨ���ٶȵ�ͼѡ��
	private int reminder; // ��ǰ���� ��ѡ ȱʡ�ǲ��򹴵ģ���������
	private String moneycost = "100"; // �γ��ܷ��� ��ѡ
	private String others; // ע�Ϳ�ѡ
	private Handler handler;
	private LinearLayout layout;
	/**
	 * �γ����༭��
	 */
	private EditText class_name_edit;

	/**
	 * �γ������ʾ
	 */
	private TextView class_type_textview;

	/**
	 * ��ʼ����ʾ
	 */
	private TextView class_start_week_textview;

	/**
	 * ��������ʾ
	 */
	private TextView stopweek_textview;

	/**
	 * �ܴ����༭��
	 */
	private EditText timers_total_edit;

	/**
	 * ÿ�ܴ��������б�
	 */
	private Spinner timers_oneweek_spinner;

	/**
	 * ÿ���Ͽ�ʱ���б�
	 */
	private ListView Class_each_weektime_listview;

	/**
	 * ��ǰ���������б�
	 */
	private Spinner reminder_spinner;

	/**
	 * �γ��ܷ��ñ༭��
	 */
	private EditText moneycost_edit;

	/**
	 * �����Ҳ����TextView
	 */
	private TextView right_textview;

	/**
	 * �Ͽεص�textview
	 */
	private TextView Textview_loction;
	/**
	 * ������෵��ImageView
	 */
	private ImageView left_imageview;

	/**
	 * �����м����textView
	 */
	TextView title_textView;

	private LayoutInflater mInflator;

	private TimeStartStopAdapter myTimeStartStopAdapter;

	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.add_class_xiaonei_layout);
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		/*
		 * ����actionbar
		 */
		ActionBar maActionBar = getActionBar();
		maActionBar.hide();

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		if (Constant.START_ADDCOURSEACTIVITY == Constant.EDIT_START) {
			/**
			 * ���Ϊ�༭�γ���Ϣ����ʼ�����ݴ���
			 */
			getIntentData();
		} else {
			addGetIntentData();
		}

		initView();

		initAdapter();

		if (Constant.START_ADDCOURSEACTIVITY == Constant.EDIT_START) {
			/**
			 * ���Ϊ�༭�γ���Ϣ
			 */
			updataIntent();
		} else {
			addUpDateIntent();
		}

		handler = new Handler() {
			public void handleMessage(Message msg) {
				String http_code = (String) msg.obj;
				int what = msg.what;
				if (what == Constant.ADD_START) {
					if (http_code.equals("0")) {
						Toast.makeText(AddClassXiaoneiActivity.this, "����", 800);
					} else {
						Constant.noteChanged = true;
						AddClassXiaoneiActivity.this.finish();
					}
				}
				if (what == Constant.EDIT_START) {
					if (http_code.equals("0")) {
						Toast.makeText(AddClassXiaoneiActivity.this, "�༭ʧ��",800);
					} else {
						Constant.noteChanged = true;
						AddClassXiaoneiActivity.this.finish();
					}
				}
			}
		};

	}

	/**
	 * ��ʼ����õ��޸�֮ǰ����Ϣ
	 */
	private void updataIntent() {

		String[] every_week_number_array = getResources().getStringArray(
				R.array.every_week_number);

		/*
		 * ÿ�ܿ�������ֵ
		 */

		for (int i = 0; i < every_week_number_array.length; i++) {
			if (every_week_number_array[i].equals(String.valueOf(cf
					.getTimers_oneweek()))) {

				timers_oneweek_spinner.setSelection(i);

			}
		}

		/*
		 * ���ָ���ֵ
		 */
		class_name_edit.setText(scShow.getClass_name());
		/*
		 * �ܴ�������ֵ
		 */
		timers_total_edit.setText(String.valueOf(cf.getTimers_total()));

		/*
		 * �γ̻��Ѹ���ֵ
		 */
		moneycost_edit.setText(cf.getMoneycost());

		/*
		 * ��ʼ�ܸ���ֵ
		 */
		String weekStr;
		class_start_week = cf.getClass_start_week();
		weekStr = MyTimeUtil.getTheWeekStr(class_start_week);
		class_start_week_textview.setText(weekStr);

		/*
		 * �����ܸ�ֵ
		 */
		int every = cf.getTimers_oneweek();
		class_stop_week = class_start_week + cf.getTimers_total() / every - 1;
		if (cf.getTimers_total() % every != 0) {
			class_stop_week = class_stop_week + 1;
		}
		weekStr = MyTimeUtil.getTheWeekStr(class_stop_week);
		stopweek_textview.setText(weekStr);
		/*
		 * ÿ���Ͽ�ʱ���б�ֱ𸳳�ֵ
		 */

		Thread ted = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {

					if (addFinished) {

						runOnUiThread(new Runnable() {

							@Override
							public void run() {

								for (int i = 0; i < (cf.getClass_starttime().length - 1); i++) {

									/* ������Ҫת�� */

									Resources res = getResources();
									String[] dayOfWeekArray = res
											.getStringArray(R.array.oneDayOfWeek);
									int[] dayInt = { 1, 2, 3, 4, 5, 6, 0 };
									for (int j = 0; j < 7; j++) {
										if (Integer.parseInt(cf
												.getClass_each_week()[i + 1]) == dayInt[j]) {

											myTimeStartStopAdapter.dayOfWeek
													.set(i, dayOfWeekArray[j]);

										}
									}

									String starttime[] = cf
											.getClass_starttime()[i + 1]
											.split(":");
									String stoptime[] = cf
											.getClass_finnishtime()[i + 1]
											.split(":");

									myTimeStartStopAdapter.timeHourStart.set(i,
											Integer.parseInt(starttime[0]));
									myTimeStartStopAdapter.timeHourStop.set(i,
											Integer.parseInt(stoptime[0]));
									if (starttime.length > 1
											&& stoptime.length > 1) {
										myTimeStartStopAdapter.timeMinStart.set(
												i,
												Integer.parseInt(starttime[1]));
										myTimeStartStopAdapter.timeMinStop.set(
												i,
												Integer.parseInt(stoptime[1]));
									} else {
										myTimeStartStopAdapter.timeMinStart
												.set(i, 0);
										myTimeStartStopAdapter.timeMinStop.set(
												i, 0);
									}
									String timeStartHourStr = ""
											+ myTimeStartStopAdapter.timeHourStart
													.get(i);
									if (myTimeStartStopAdapter.timeHourStart
											.get(i) < 10) {
										timeStartHourStr = "0"
												+ timeStartHourStr;
									}

									String timeStopHourStr = ""
											+ myTimeStartStopAdapter.timeHourStop
													.get(i);
									if (myTimeStartStopAdapter.timeHourStop
											.get(i) < 10) {
										timeStopHourStr = "0" + timeStopHourStr;
									}
									if (starttime.length > 1
											&& stoptime.length > 1) {
										String timeStartMinStr = ""
												+ myTimeStartStopAdapter.timeMinStart
														.get(i);
										if (myTimeStartStopAdapter.timeMinStart
												.get(i) < 10) {
											timeStartMinStr = "0"
													+ timeStartMinStr;
										}

										String timeStopMinStr = ""
												+ myTimeStartStopAdapter.timeMinStop
														.get(i);
										if (myTimeStartStopAdapter.timeMinStop
												.get(i) < 10) {
											timeStopMinStr = "0"
													+ timeStopMinStr;
										}
										myTimeStartStopAdapter.timeStart.set(i,
												timeStartHourStr + ":"
														+ timeStartMinStr);
										myTimeStartStopAdapter.timeStop.set(i,
												timeStopHourStr + ":"
														+ timeStopMinStr);
									} else {
										myTimeStartStopAdapter.timeStart.set(i,
												timeStartHourStr + ":" + "00");
										myTimeStartStopAdapter.timeStop.set(i,
												timeStopHourStr + ":" + "00");
									}

								}
								myTimeStartStopAdapter.notifyDataSetChanged();
							}
						});

						break;
					}
				}
			}
		});
		ted.start();

		/**
		 * �Ͽεص㸳��ֵ
		 */
		if (scShow.getLocation() == null || scShow.getLocation().equals("null")) {
			String input_location = getString(R.string.input_location);
			Textview_loction.setText(input_location);
		} else {
			String s = new String(scShow.getLocation());
			String a[] = s.split("@");
			String address = a[0];
			Textview_loction.setText(address);
			location_tem = s;

		}

	}

	/**
	 * ������޸Ŀγ̵���Ϣ
	 */
	private void getIntentData() {
		Intent intent = this.getIntent();
		scShow = (SingleClass) intent.getSerializableExtra("scc");
		cf = scShow.getclassinfo(AddClassXiaoneiActivity.this,
				scShow.getClass_id());
		others = scShow.getOthers();
	}

	/**
	 * ���ʱ ��ó�ʼֵ
	 */
	private void addGetIntentData() {
		Intent intent = this.getIntent();
		addWeekNo = intent.getIntExtra("weekno", 28);
		addDay = intent.getIntExtra("weekday", 0);
		addStartTime = intent.getIntExtra("starttime", 12);
	}

	/**
	 * ���ʱ��ʼ��
	 */
	private void addUpDateIntent() {

		Resources res = getResources();
		String[] dayOfWeekArray = res.getStringArray(R.array.oneDayOfWeek);
		int[] dayInt = { 1, 2, 3, 4, 5, 6, 0 };
		for (int j = 0; j < 7; j++) {
			if (addDay == dayInt[j]) {
				myTimeStartStopAdapter.dayOfWeek.set(0, dayOfWeekArray[j]);
			}
		}

		myTimeStartStopAdapter.timeHourStart.set(0, addStartTime);
		myTimeStartStopAdapter.timeHourStop.set(0, addStartTime + 1);

		myTimeStartStopAdapter.timeMinStart.set(0, 0);
		myTimeStartStopAdapter.timeMinStop.set(0, 0);

		String timeStartHourStr = ""
				+ myTimeStartStopAdapter.timeHourStart.get(0);
		if (myTimeStartStopAdapter.timeHourStart.get(0) < 10) {
			timeStartHourStr = "0" + timeStartHourStr;
		}

		String timeStopHourStr = ""
				+ myTimeStartStopAdapter.timeHourStop.get(0);
		if (myTimeStartStopAdapter.timeHourStop.get(0) < 10) {
			timeStopHourStr = "0" + timeStopHourStr;
		}
		myTimeStartStopAdapter.timeStart.set(0, timeStartHourStr + ":" + "00");
		myTimeStartStopAdapter.timeStop.set(0, timeStopHourStr + ":" + "00");

		/**
		 * ��ʼ�ܸ���ֵ
		 */

		String dateStr;
		dateStr = MyTimeUtil.getDateOfWeekAndDay(addWeekNo, addDay);
//		term = MyTimeUtil.getTerm(dateStr);

		String[] date = dateStr.split("\\.");
		String startDateStr;
		String stopDateStr;
		if (term == 1) {
			/*
			 * ��һѧ��
			 */
			startDateStr = date[0] + "." + "09" + "." + "01";
			stopDateStr = (Integer.parseInt(date[0]) + 1) + "." + "01" + "."
					+ "15";
		} else {
			/*
			 * �ڶ�ѧ��
			 */

			startDateStr = date[0] + "." + "03" + "." + "01";
			stopDateStr = date[0] + "." + "06" + "." + "30";
		}

		class_start_week_textview.setText(MyTimeUtil.getWeekFirstAndLastDay(startDateStr));
		class_start_week = MyTimeUtil.getWeeks(startDateStr);

		/**
		 * �����ܸ�ֵ
		 */
		stopweek_textview.setText(MyTimeUtil.getWeekFirstAndLastDay(stopDateStr));
		class_stop_week = MyTimeUtil.getWeeks(stopDateStr);

		/*
		 * �ܴ�����ֵ
		 */
		timers_total = (1 + class_stop_week - class_start_week)
				* timers_oneweek;
		timers_total_edit.setText(timers_total + "");

	}

	/**
	 * ��ʼ��������
	 */
	private void initAdapter() {
		mInflator = getLayoutInflater();
		myTimeStartStopAdapter = new TimeStartStopAdapter();
		Class_each_weektime_listview.setAdapter(myTimeStartStopAdapter);
		timers_oneweek = Integer.parseInt(timers_oneweek_spinner.getSelectedItem().toString());
		loadDataForTimeList(timers_oneweek);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
		Date nowDate = new Date(System.currentTimeMillis());// ��ȡ��ǰʱ��
		String dateStr = simpleDateFormat.format(nowDate);

		class_start_week_textview.setText(MyTimeUtil.getWeekFirstAndLastDay(dateStr));
		class_start_week = MyTimeUtil.getWeeks(dateStr);

	}

	int startNumber = 0;

	/**
	 * ������ѡÿ���Ͽδ�����ӿγ��Ͽ�ʱ���б�
	 */
	private void loadDataForTimeList(int number) {

		if (number > myTimeStartStopAdapter.getCount()) {
			/**
			 * ѡ��ÿ�ܴ���������
			 */
			int count = myTimeStartStopAdapter.getCount();

			if (Constant.START_ADDCOURSEACTIVITY == Constant.ADD_START) {
				/**
				 * ��ӿγ�
				 */

				Resources res = getResources();
				String[] dayOfWeekArray = res.getStringArray(R.array.oneDayOfWeek);
				if (isFirst > 1) {

					for (int j = 0; j < 7; j++) {
						/**
						 * ȷ��ǰ��Ŀγ�Ϊ���ڼ����������γ���������˳��
						 */
						if (myTimeStartStopAdapter.getDayOfWeekStr(count - 1)
								.equals(dayOfWeekArray[j])) {
							startNumber = j;
						}
					}

				}
				for (int i = 0; i < (number - count); i++) {
					++startNumber;
					if (startNumber == 7) {
						startNumber = 0;
					}
					if (isFirst > 1) {
						myTimeStartStopAdapter.addItem(myTimeStartStopAdapter
								.getTimeStartHour(count - 1),
								myTimeStartStopAdapter
										.getTimeMinStart(count - 1),
								myTimeStartStopAdapter
										.getTimeStopHour(count - 1),
								myTimeStartStopAdapter
										.getTimeMinStop(count - 1),
								dayOfWeekArray[startNumber]);
					} else {
						myTimeStartStopAdapter.addItem(13, 0, 14, 0, "������");
					}

				}
			} else {
				/**
				 * �༭�γ�
				 */
				Resources res = getResources();
				String[] dayOfWeekArray = res.getStringArray(R.array.oneDayOfWeek);
				if (isFirst > 1) {
					for (int j = 0; j < 7; j++) {
						/**
						 * ȷ��ǰ��Ŀγ�Ϊ���ڼ����������γ���������˳��
						 */
						if (myTimeStartStopAdapter.getDayOfWeekStr(count - 1)
								.equals(dayOfWeekArray[j])) {
							startNumber = j;
						}
					}
				}

				for (int i = 0; i < (number - count); i++) {
					++startNumber;
					if (startNumber == 7) {
						startNumber = 0;
					}
					if (isFirst > 1) {
						myTimeStartStopAdapter.addItem(myTimeStartStopAdapter
								.getTimeStartHour(count - 1),
								myTimeStartStopAdapter
										.getTimeMinStart(count - 1),
								myTimeStartStopAdapter
										.getTimeStopHour(count - 1),
								myTimeStartStopAdapter
										.getTimeMinStop(count - 1),
								dayOfWeekArray[startNumber]);
					} else {
						myTimeStartStopAdapter.addItem(13, 0, 14, 0, "������");
					}
				}

			}
		} else if (number <= myTimeStartStopAdapter.getCount()) {
			/**
			 * ѡ��ÿ�ܴ���������
			 */
			int count = myTimeStartStopAdapter.getCount();
			for (int i = 0; i < (count - number); i++) {

				myTimeStartStopAdapter.deleteLast();

			}
		}

		if (isFirst == 1) {

			if (Constant.START_ADDCOURSEACTIVITY == Constant.EDIT_START) {

				for (int i = 0; i < myTimeStartStopAdapter.timeHourStart.size(); i++) {
					Resources res = getResources();
					String[] dayOfWeekArray = res
							.getStringArray(R.array.oneDayOfWeek);
					int[] dayInt = { 1, 2, 3, 4, 5, 6, 0 };
					for (int j = 0; j < 7; j++) {
						if (Integer.parseInt(cf.getClass_each_week()[i + 1]) == dayInt[j]) {

							myTimeStartStopAdapter.dayOfWeek.set(i,
									dayOfWeekArray[j]);

						}
					}

					String starttime[] = cf.getClass_starttime()[i + 1]	.split(":");
					String stoptime[] = cf.getClass_finnishtime()[i + 1].split(":");

					myTimeStartStopAdapter.timeHourStart.set(i,
							Integer.parseInt(starttime[0]));
					myTimeStartStopAdapter.timeHourStop.set(i,
							Integer.parseInt(stoptime[0]));
					if (starttime.length > 1 && stoptime.length > 1) {
						myTimeStartStopAdapter.timeMinStart.set(i,
								Integer.parseInt(starttime[1]));
						myTimeStartStopAdapter.timeMinStop.set(i,
								Integer.parseInt(stoptime[1]));
					} else {
						myTimeStartStopAdapter.timeMinStart.set(i, 0);
						myTimeStartStopAdapter.timeMinStop.set(i, 0);
					}
					String timeStartHourStr = ""
							+ myTimeStartStopAdapter.timeHourStart.get(i);
					if (myTimeStartStopAdapter.timeHourStart.get(i) < 10) {
						timeStartHourStr = "0" + timeStartHourStr;
					}

					String timeStopHourStr = ""
							+ myTimeStartStopAdapter.timeHourStop.get(i);
					if (myTimeStartStopAdapter.timeHourStop.get(i) < 10) {
						timeStopHourStr = "0" + timeStopHourStr;
					}
					if (starttime.length > 1 && stoptime.length > 1) {
						String timeStartMinStr = ""
								+ myTimeStartStopAdapter.timeMinStart.get(i);
						if (myTimeStartStopAdapter.timeMinStart.get(i) < 10) {
							timeStartMinStr = "0" + timeStartMinStr;
						}

						String timeStopMinStr = ""
								+ myTimeStartStopAdapter.timeMinStop.get(i);
						if (myTimeStartStopAdapter.timeMinStop.get(i) < 10) {
							timeStopMinStr = "0" + timeStopMinStr;
						}

						myTimeStartStopAdapter.timeStart.set(i,
								timeStartHourStr + ":" + timeStartMinStr);
						myTimeStartStopAdapter.timeStop.set(i, timeStopHourStr
								+ ":" + timeStopMinStr);
					} else {
						myTimeStartStopAdapter.timeStart.set(i,
								timeStartHourStr + ":" + "00");
						myTimeStartStopAdapter.timeStop.set(i, timeStopHourStr
								+ ":" + "00");
					}

				}
				myTimeStartStopAdapter.notifyDataSetChanged();

			} else {
				Resources res = getResources();
				String[] dayOfWeekArray = res.getStringArray(R.array.oneDayOfWeek);
				int[] dayInt = { 1, 2, 3, 4, 5, 6, 0 };
				for (int j = 0; j < 7; j++) {
					if (addDay == dayInt[j]) {
						myTimeStartStopAdapter.dayOfWeek.set(0,
								dayOfWeekArray[j]);
					}
				}

				myTimeStartStopAdapter.timeHourStart.set(0, addStartTime);
				myTimeStartStopAdapter.timeHourStop.set(0, addStartTime + 1);

				String timeStartHourStr = ""
						+ myTimeStartStopAdapter.timeHourStart.get(0);
				if (myTimeStartStopAdapter.timeHourStart.get(0) < 10) {
					timeStartHourStr = "0" + timeStartHourStr;
				}

				String timeStopHourStr = ""
						+ myTimeStartStopAdapter.timeHourStop.get(0);
				if (myTimeStartStopAdapter.timeHourStop.get(0) < 10) {
					timeStopHourStr = "0" + timeStopHourStr;
				}
				myTimeStartStopAdapter.timeStart.set(0, timeStartHourStr + ":"
						+ "00");
				myTimeStartStopAdapter.timeStop.set(0, timeStopHourStr + ":"
						+ "00");
				myTimeStartStopAdapter.timeMinStart.set(0, 0);
				myTimeStartStopAdapter.timeMinStop.set(0, 0);

			}
		}
//		ListResetUtil.reSetListViewHeight(Class_each_weektime_listview);
		isFirst++;
	}

	/**
	 * ��ʼ������
	 */
	private void initView() {
		class_name_edit = (EditText) this.findViewById(R.id.coursename_edittext);
//		class_type_textview = (TextView) findViewById(R.id.class_type_textview);
		class_type_textview.setText("У�ڿ�");
		class_start_week_textview = (TextView) this.findViewById(R.id.startweek_textview);
		stopweek_textview = (TextView) findViewById(R.id.stopweek_textview);
		timers_total_edit = (EditText) this.findViewById(R.id.total_number_edittext);
		timers_oneweek_spinner = (Spinner) this.findViewById(R.id.every_week_number_spinner);
		timers_oneweek_spinner.setOnItemSelectedListener(new SpinnerItemSelectedListener());
		Class_each_weektime_listview = (ListView) this.findViewById(R.id.every_week_number_listview);
		moneycost_edit = (EditText) this.findViewById(R.id.course_cost_edittext);
		Textview_loction = (TextView) this.findViewById(R.id.where_conrse);
		Textview_loction.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (location_tem == null) {
//					Intent intent = new Intent(AddClassXiaoneiActivity.this,Setlocationactivity.class);
//					startActivityForResult(intent, 0);
				} else {
					String s = new String(location_tem);
					String a[] = s.split("@");
					String location = a[1];
					String y_location = location.substring(
							location.indexOf(":") + 1, location.indexOf(","));
					String x_location = location.substring(31,
							location.length());
//					Intent intent = new Intent(AddClassXiaoneiActivity.this,Setlocationactivity.class);
//					intent.putExtra("location", a[0]);
//					intent.putExtra("y", Double.valueOf(y_location));
//					intent.putExtra("x", Double.valueOf(x_location));
//					startActivityForResult(intent, 0);
				}
			}
		});
		layout = (LinearLayout) this.findViewById(R.id.add_linearlayout);
		layout.setOnTouchListener(this);

		/*
		 * ���������
		 */
		InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(class_name_edit.getWindowToken(), 0);

		initTopBar();
	}

	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		switch (resultCode) {
		// resultCodeΪ�ش��ı�ǣ�����B�лش�����RESULT_OK
		case RESULT_OK:
			location_tem = intent.getStringExtra("location");
			String s = new String(location_tem);
			if (s.contains("@")) {
				String a[] = s.split("@");
				address = a[0];
				Textview_loction.setText(address);
			} else {
				Textview_loction.setText(s);
			}
			break;
		case 2:

			int year = intent.getIntExtra("year", 0);
			int month = intent.getIntExtra("month", 0);
			int dayofMonth = intent.getIntExtra("dayOfMonth", 0);

			month = month + 1;

			String yearSrt = year + "";
			String monthStr = "" + month;
			String dayofMonthStr = "" + dayofMonth;

			if (month < 10) {
				monthStr = "0" + month;
			}
			if (dayofMonth < 10) {
				dayofMonthStr = "0" + dayofMonth;
			}
			String date = yearSrt + "." + monthStr + "." + dayofMonthStr;
			if (requestCode == 1) {
				/**
				 * ��ʼ��ѡ���¼�
				 */
				class_start_week_textview.setText(MyTimeUtil
						.getWeekFirstAndLastDay(date));
				class_start_week = MyTimeUtil.getWeeks(date);

				if (class_start_week > class_stop_week) {
					/**
					 * ѡ��Ľ�����������ʼ��
					 */
					class_stop_week = class_start_week;
					stopweek_textview.setText(MyTimeUtil
							.getTheWeekStr(class_stop_week));

				}
			} else {
				/**
				 * ������ѡ���¼�
				 */
				stopweek_textview.setText(MyTimeUtil
						.getWeekFirstAndLastDay(date));
				class_stop_week = MyTimeUtil.getWeeks(date);

				timers_total = (class_stop_week - class_start_week + 1)
						* timers_oneweek;
				if (timers_total <= 0) {
					/**
					 * ѡ��Ľ�����������ʼ��
					 */
					class_start_week = class_stop_week;
					class_start_week_textview.setText(MyTimeUtil
							.getTheWeekStr(class_start_week));
					timers_total = (class_stop_week - class_start_week + 1)
							* timers_oneweek;
				}
				timers_total_edit.setText(timers_total + "");

			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		/*
		 * ���������
		 */
		InputMethodManager imm = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(class_name_edit.getWindowToken(), 0);
		super.onResume();
	}

	/**
	 * ��ʼ������TopBar
	 */
	public void initTopBar() {
//		right_textview = (TextView) this.findViewById(R.id.right_tx);
		right_textview.setOnClickListener(new ClickListener());
//		left_imageview = (ImageView) this.findViewById(R.id.left_ic);
		left_imageview.setOnClickListener(new ClickListener());
//		title_textView = (TextView) this.findViewById(R.id.title);
		if (Constant.START_ADDCOURSEACTIVITY == Constant.EDIT_START) {
			title_textView.setText(R.string.editclass);
		} else {
			title_textView.setText(R.string.addclass);
		}
	}

	public class SpinnerItemSelectedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			if (arg0 == timers_oneweek_spinner) {

				timers_oneweek = Integer.parseInt(timers_oneweek_spinner
						.getSelectedItem().toString());

				/*
				 * ����ܴ����Ѿ���д�����������޸ģ�������ʼ�ܺͽ����ܲ���
				 */
				if (!timers_total_edit.getText().toString().equals("")) {
					timers_total = (class_stop_week - class_start_week + 1)
							* timers_oneweek;
					timers_total_edit.setText(timers_total + "");
				}
				loadDataForTimeList(timers_oneweek);
				addFinished = true;

			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}

	}

	/*
	 * ���ѡ���ʱ���Ƿ�С��8��;
	 */
	private boolean timeLess6 = false;

	/**
	 * ����û���ӵ���Ϣ
	 */
	public ClassInfo getDataAdd() {

		/*
		 * ��ȡ�γ���
		 */
		class_name = class_name_edit.getText().toString();

		/*
		 * ��ȡ�γ�����
		 */
		class_type = "У�ڿ�";

		/*
		 * ��ȡ��ʼ��
		 */
		// class_start_week =
		// MyTimeUtil.getWeeks(class_start_week_textview.getText().toString());
		/*
		 * ��ȡ�ܴ���
		 */
		timers_total = Integer.parseInt(timers_total_edit.getText().toString());
		/*
		 * ��ȡÿ�ܴ���
		 */
		timers_oneweek = Integer.parseInt(timers_oneweek_spinner
				.getSelectedItem().toString());

		/*
		 * ��ȡ�Ͽ�ʱ������
		 */
		Class_each_week = new String[myTimeStartStopAdapter.getCount()];// ÿ���ܼ���
		// Сʱ:����
		Class_starttime = new String[myTimeStartStopAdapter.getCount()];// ��ʼʱ��
		// Сʱ:����
		Class_finnishtime = new String[myTimeStartStopAdapter.getCount()];// ����ʱ��
		for (int i = 0; i < myTimeStartStopAdapter.getCount(); i++) {

			/*
			 * ÿ�ܵ����ڼ��ϿΣ���Ҫת�� ����һ��Ӧ��1�������ڶ���Ӧ��2���������ն�Ӧ��0����
			 */
			String dayString = myTimeStartStopAdapter.getDayOfWeekStr(i);
			Resources res = getResources();
			String[] dayOfWeekArray = res.getStringArray(R.array.oneDayOfWeek);
			int[] dayInt = { 1, 2, 3, 4, 5, 6, 0 };
			for (int j = 0; j < 7; j++) {
				if (dayString.equals(dayOfWeekArray[j])) {
					Class_each_week[i] = "" + dayInt[j];
					break;
				}
			}

			/*
			 * �Ͽο�ʼʱ��
			 */
			Class_starttime[i] = myTimeStartStopAdapter.getTimeStart(i);

			/*
			 * �Ͽν���ʱ��
			 */
			Class_finnishtime[i] = myTimeStartStopAdapter.geTimeStop(i);

			if (myTimeStartStopAdapter.getTimeStopHour(i) < 8
					|| myTimeStartStopAdapter.getTimeStartHour(i) < 8) {
				timeLess6 = true;
			}

		}

		classTimeSort(Class_each_week, Class_starttime, Class_finnishtime);

		/**
		 * ��õ�ַ
		 */
		if (location_tem != null) {
			String s = new String(location_tem);
			String a[] = s.split("@");
			address = a[0];
		} else {
			String input_location = getString(R.string.input_location);
			address = input_location;
		}

		/*
		 * ��ÿ�ǰ����ʱ��
		 */

		reminder = 0;

		/*
		 * ��ȡ�ܷ���
		 */
		moneycost = moneycost_edit.getText().toString();
		if (moneycost.isEmpty()) {
			moneycost = "0";
		}

		/*
		 * ��ȡ��ע
		 */

		// others = others_edit.getText().toString();
		if (others == null) {
			others = "0";
		}
		/*
		 * if (others.isEmpty()) { others = "��"; }
		 */
		ClassInfo addClass_info = new ClassInfo(class_name, class_type,
				class_start_week, timers_total, timers_oneweek,
				Class_each_week, Class_starttime, Class_finnishtime,
				location_tem, reminder, moneycost, others, MainActivity.childid);
		return addClass_info;

	}

	/**
	 * ��һ�ܵ��Ͽ�ʱ���������ʹ�䰴����ʾ��Gridlayout�д����ң����ϵ�������
	 * 
	 * @param class_each_week2
	 *            ���ڼ��Ͽε����� (����һΪ��1����������Ϊ��0��)
	 * @param class_starttime2
	 *            �γ̿�ʼʱ�������
	 * @param class_finnishtime2
	 *            �γ̽���ʱ�������
	 */
	private void classTimeSort(String[] class_each_week2,
			String[] class_starttime2, String[] class_finnishtime2) {

		for (int i = 0; i < class_each_week2.length; i++) {
			for (int j = i; j < class_each_week2.length; j++) {
				if (Integer.parseInt(class_each_week2[i]) > Integer
						.parseInt(class_each_week2[j])) {
					classTimeExchange(class_each_week2, class_starttime2,
							class_finnishtime2, i, j);
				} else if (Integer.parseInt(class_each_week2[i]) == Integer
						.parseInt(class_each_week2[j])) {
					String ai[] = class_starttime2[i].split(":");
					String aj[] = class_starttime2[j].split(":");

					String starti = ai[0];
					String startj = aj[0];

					if (Integer.parseInt(starti) > Integer.parseInt(startj)) {
						classTimeExchange(class_each_week2, class_starttime2,
								class_finnishtime2, i, j);
					}
				}
			}
		}
	}

	/**
	 * ������������ �е�i ��jԪ��
	 * 
	 * @param class_each_week2
	 * @param class_starttime2
	 * @param class_finnishtime2
	 * @param i
	 * @param j
	 */
	private void classTimeExchange(String[] class_each_week2,
			String[] class_starttime2, String[] class_finnishtime2, int i, int j) {
		String weekday;
		String starttime;
		String stoptime;
		weekday = class_each_week2[i];
		class_each_week2[i] = class_each_week2[j];
		class_each_week2[j] = weekday;

		starttime = class_starttime2[i];
		class_starttime2[i] = class_starttime2[j];
		class_starttime2[j] = starttime;

		stoptime = class_finnishtime2[i];
		class_finnishtime2[i] = class_finnishtime2[j];
		class_finnishtime2[j] = stoptime;

	}

	ClassInfo aaaaa;

	public class ClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			if (Constant.START_ADDCOURSEACTIVITY == Constant.ADD_START) {
				if (arg0 == right_textview) {
					/**
					 * ��ӿγ�
					 */
					if (class_name_edit.getText().toString().isEmpty()) {
						Toast.makeText(AddClassXiaoneiActivity.this,
								"����ȷ��д�γ���", 1000).show();
					} else if (timers_total_edit.getText().toString().isEmpty()) {
						Toast.makeText(AddClassXiaoneiActivity.this,
								"����ȷ��д�γ��ܴ���", 1000).show();
					} else if (Integer.parseInt(timers_oneweek_spinner
							.getSelectedItem().toString()) > Integer
							.parseInt(timers_total_edit.getText().toString())) {
						Toast.makeText(AddClassXiaoneiActivity.this,
								"�Ͽ��ܴ�������С��ÿ���Ͽδ��������޸ģ�", 1500).show();
					} else if (NetworkState.isCon(AddClassXiaoneiActivity.this) == false) {
						Toast.makeText(AddClassXiaoneiActivity.this, "�������磡",
								1500).show();
					} else if (Integer.parseInt(timers_total_edit.getText()
							.toString()) > 366
							|| Integer.parseInt(timers_total_edit.getText()
									.toString()) < 1) {
						Toast.makeText(AddClassXiaoneiActivity.this,
								"�Ͽ��ܴ���Ӧ��1-366֮�䣬���޸ģ�", 1500).show();
					} else {
						timeLess6 = false;
						aaaaa = getDataAdd();
//						CheckClassTimeUtil check = new CheckClassTimeUtil(Class_each_week, Class_starttime,Class_finnishtime);
						if (timeLess6) {
							Toast.makeText(AddClassXiaoneiActivity.this,
									"Ϊ��֤������Ϣ���벻Ҫ���Ͽ�ʱ������8�㣡", 1500).show();
//						} else if (check.checkConflict()) {
							/**
							 * ʱ���ͻ
							 */
							Toast.makeText(AddClassXiaoneiActivity.this,
									"��ѡ���ʱ���г�ͻ,����", 1500).show();
						} else {
							ProgressDialog pp = new ProgressDialog(
									AddClassXiaoneiActivity.this);
							pp.setMessage("�����У����Ժ򡭡�");
							pp.show();
							right_textview.setClickable(false);

							new Thread(new Runnable() {

								public void run() {
									String errorCode;
									DBOperation dbo = new DBOperation();
									try {
										aaaaa = getDataAdd();
										int a = aaaaa.getChild_id();
										ChildInfo ci = dbo
												.getsingle_child_info(
														AddClassXiaoneiActivity.this,
														aaaaa.getChild_id());
										String user_number = dbo
												.get_user_info_1(
														AddClassXiaoneiActivity.this,
														ci.getParent_id())
												.getPhone_no();
										String cid = ci.getCid();
//										JSONObject addcourse = Http_post_json_AddCourse.getjson(aaaaa, user_number,cid);
//										String result = addcourse.getString("result");
//										JSONObject result_json = new JSONObject(result);
//										errorCode = result_json.getString("errorCode");
//										String errorMessage = result_json.getString("errorMessage");
//										if (errorCode.equals("1")) {
////											aaaaa.setOthers(errorMessage);
//											dbo.insert_entire_class(
//													AddClassXiaoneiActivity.this,
//													aaaaa);
//										} else {
//											errorCode = "0";
//										}

									} catch (Exception e) {
										e.toString();
										errorCode = "0";
									}
									Message msg = new Message();
									msg.what = Constant.ADD_START;
//									msg.obj = errorCode;
									handler.sendMessage(msg);

								}
							}).start();

						}
						aaaaa = null;
					}

				} else if (arg0 == left_imageview) {
					AddClassXiaoneiActivity.this.finish();
				}
			} else if (Constant.START_ADDCOURSEACTIVITY == Constant.EDIT_START) {
				if (arg0 == right_textview) {

					/**
					 * �༭�γ���ɰ�ť
					 */

					DBOperation dbo = new DBOperation();

					if (class_name_edit.getText().toString().isEmpty()) {
						Toast.makeText(AddClassXiaoneiActivity.this,
								"����ȷ��д�γ���", 1000).show();
					} else if (timers_total_edit.getText().toString().isEmpty()) {
						Toast.makeText(AddClassXiaoneiActivity.this,
								"����ȷ��д�γ��ܴ���", 1000).show();
					} else if (Integer.parseInt(timers_oneweek_spinner
							.getSelectedItem().toString()) > Integer
							.parseInt(timers_total_edit.getText().toString())) {
						Toast.makeText(AddClassXiaoneiActivity.this,
								"�Ͽ��ܴ�������С��ÿ���Ͽδ��������޸ģ�", 1500).show();
					} else if (NetworkState.isCon(AddClassXiaoneiActivity.this) == false) {
						Toast.makeText(AddClassXiaoneiActivity.this, "�������磡",
								1500).show();
					} else if (Integer.parseInt(timers_total_edit.getText()
							.toString()) > 366
							|| Integer.parseInt(timers_total_edit.getText()
									.toString()) < 1) {
						Toast.makeText(AddClassXiaoneiActivity.this,
								"�Ͽ��ܴ���Ӧ��1-366֮�䣬���޸ģ�", 1500).show();
					} else {

						timeLess6 = false;
						aaaaa = getDataAdd();
//						CheckClassTimeUtil check = new CheckClassTimeUtil(Class_each_week, Class_starttime,
//								Class_finnishtime);
						if (timeLess6) {
							Toast.makeText(AddClassXiaoneiActivity.this,
									"Ϊ��֤������Ϣ���벻Ҫ���Ͽ�ʱ������8�㣡", 1500).show();
//						} else if (check.checkConflict()) {
							/**
							 * ʱ���ͻ
							 */
							Toast.makeText(AddClassXiaoneiActivity.this,
									"��ѡ���ʱ���г�ͻ,����", 1500).show();
						}

						AlertDialog.Builder builder = new AlertDialog.Builder(
								AddClassXiaoneiActivity.this);
						builder.setMessage("������γ��бʼǼ�¼���༭�γ̻ᶪʧ�ʼǣ��Ƿ������")
								.setCancelable(true)
								.setPositiveButton("����",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface arg0,
													int arg1) {

												ProgressDialog pp = new ProgressDialog(
														AddClassXiaoneiActivity.this);
												pp.setMessage("�����У����Ժ򡭡�");
												pp.show();
												right_textview
														.setClickable(false);

												new Thread(new Runnable() {

													public void run() {
														String errorCode;
														DBOperation dbo = new DBOperation();
														try {
															aaaaa = getDataAdd();
															int a = aaaaa.getChild_id();
															ChildInfo ci = dbo.getsingle_child_info(AddClassXiaoneiActivity.this,
																			aaaaa.getChild_id());

															String user_number = dbo.get_user_info_1(AddClassXiaoneiActivity.this,
																			ci.getParent_id())
																	.getPhone_no();
															String cid = ci.getCid();
															String courseId = aaaaa.getOthers();
//															JSONObject addcourse = Http_post_json_UpdateCourse.getjson(
//																			aaaaa,
//																			user_number,
//																			cid,
//																			courseId);
//															String result = addcourse.getString("result");
//															JSONObject result_json = new JSONObject(result);
//															errorCode = result_json.getString("errorCode");
//															// String
//															// errorMessage=result_json.getString("errorMessage");
//															if (errorCode.equals("1")) {
//																dbo.delete_entire_class(AddClassXiaoneiActivity.this,
//																		scShow.getClass_id(),
//																		true);
//																dbo.insert_entire_class(AddClassXiaoneiActivity.this,
//																		aaaaa);
//
//															} else {
//																errorCode = "0";
//															}

														} catch (Exception e) {
															e.toString();
															errorCode = "0";
														}
														Message msg = new Message();
														msg.what = Constant.EDIT_START;
//														msg.obj = errorCode;
														handler.sendMessage(msg);

													}
												}).start();

											}
										});
						// �öԻ������������Ի���
						AlertDialog alert = builder.create();
						alert.show();
					}
					aaaaa = null;
				} else if (arg0 == left_imageview) {
					AddClassXiaoneiActivity.this.finish();
				}

			}
		}
	}

	/**
	 * ʱ��listView������
	 * 
	 * @author wangyuepeng
	 */

	public class TimeStartStopAdapter extends BaseAdapter {
		public int count = 0;

		public List<String> timeStart;
		public List<Integer> timeHourStart;
		public List<Integer> timeMinStart;
		public List<String> timeStop;
		public List<Integer> timeHourStop;
		public List<Integer> timeMinStop;
		public List<String> dayOfWeek;

		public TimeStartStopAdapter() {
			timeStart = new ArrayList<String>();
			timeStop = new ArrayList<String>();
			dayOfWeek = new ArrayList<String>();
			timeHourStart = new ArrayList<Integer>();
			timeHourStop = new ArrayList<Integer>();
			timeMinStart = new ArrayList<Integer>();
			timeMinStop = new ArrayList<Integer>();
		}

		/**
		 * ���б������һ��
		 */
		public void addItem(int HourStart, int MinStart, int HourStop,
				int MinStop, String oneDayOfWeek) {
			++count;

			String hourstartStr = "" + HourStart;
			if (HourStart < 10) {
				hourstartStr = "0" + hourstartStr;
			}

			String minstartStr = "" + MinStart;
			if (MinStart < 10) {
				minstartStr = "0" + MinStart;
			}
			String hourstopStr = "" + HourStop;
			if (HourStop < 10) {
				hourstopStr = "0" + hourstopStr;
			}
			String minstopStr = "" + MinStop;
			if (MinStop < 10) {
				minstopStr = "0" + minstopStr;
			}

			timeStart.add(hourstartStr + ":" + minstartStr);
			timeStop.add(hourstopStr + ":" + minstopStr);
			timeHourStart.add(HourStart);
			timeHourStop.add(HourStop);
			timeMinStart.add(MinStart);
			timeMinStop.add(MinStop);
			dayOfWeek.add(oneDayOfWeek);
			myTimeStartStopAdapter.notifyDataSetChanged();
		}

		/**
		 * ɾ�����һ��
		 */
		public void deleteLast() {

			timeStart.remove(count - 1);
			timeStop.remove(count - 1);
			dayOfWeek.remove(count - 1);
			timeHourStart.remove(count - 1);
			timeHourStop.remove(count - 1);
			timeMinStart.remove(count - 1);
			timeMinStop.remove(count - 1);
			count--;
		}

		/**
		 * ����б�
		 */
		public void clear() {
			count = 0;
			timeStart.clear();
			timeStop.clear();
			dayOfWeek.clear();
			timeHourStart.clear();
			timeHourStop.clear();
			timeMinStart.clear();
			timeMinStop.clear();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return count;
		}

		public String getDayOfWeekStr(int i) {
			return dayOfWeek.get(i);
		}

		public String geTimeStop(int i) {
			return timeStop.get(i);
		}

		public String getTimeStart(int i) {
			return timeStart.get(i);
		}

		public int getTimeStartHour(int i) {
			return timeHourStart.get(i);
		}

		public int getTimeStopHour(int i) {
			return timeHourStop.get(i);
		}

		public int getTimeMinStart(int i) {
			return timeMinStart.get(i);
		}

		public int getTimeMinStop(int i) {
			return timeMinStop.get(i);
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(final int arg0, View convertView,
				ViewGroup viewGroup) {
			// TODO Auto-generated method stub
			final ViewHolder holder;
			View itemView = (View) convertView;
			if (itemView == null) {
				itemView = mInflator.inflate(R.layout.time_list_item, null);
				holder = new ViewHolder(itemView);
				itemView.setTag(holder);
			} else {
				holder = (ViewHolder) itemView.getTag();
			}
			holder.dayOfWeekApinner
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> vv,
								View view, int i, long l) {

							dayOfWeek.set(arg0, holder.dayOfWeekApinner
									.getSelectedItem().toString());
						}

						@Override
						public void onNothingSelected(AdapterView<?> vv) {

						}
					});

			holder.timeStartTextView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {

					TimePickerDialog startTimePickerDialog = new TimePickerDialog(
							AddClassXiaoneiActivity.this,
							new TimePickerDialog.OnTimeSetListener() {

								@Override
								public void onTimeSet(TimePicker view,
										int hourOfDay, int minute) {
									// TODO Auto-generated method stub
									String hour = "" + hourOfDay;
									if (hourOfDay < 10) {
										hour = "0" + hourOfDay;
									}
									String min = "" + minute;
									if (minute < 10) {
										min = "0" + minute;
									}
									holder.timeStartTextView.setText(hour + ":"
											+ min);

									timeStart.set(arg0, hour + ":" + min);
									timeHourStart.set(arg0, hourOfDay);
									timeMinStart.set(arg0, minute);
									if (timeHourStop.get(arg0) < timeHourStart
											.get(arg0)
											|| (timeHourStop.get(arg0) == timeHourStart
													.get(arg0) && timeMinStop
													.get(arg0) / 10 <= timeMinStart
													.get(arg0) / 10)) {
										timeHourStop.set(arg0, hourOfDay + 1);
										timeMinStop.set(arg0, 0);
										String hourStop = "" + (hourOfDay + 1);
										if (hourOfDay == 23) {
											timeHourStop.set(arg0, 0);
											hourStop = ""
													+ timeHourStop.get(arg0);
										}
										if (timeHourStop.get(arg0) < 10) {
											hourStop = "0" + hourStop;
										}
										timeStop.set(arg0, hourStop + ":"
												+ "00");
										holder.timeStopTextView
												.setText(timeStop.get(arg0));
									}

								}
							}, timeHourStart.get(arg0), timeMinStart.get(arg0)

							/**
							 * true��ʾ24Сʱ��ʽ
							 */
							, true);
					startTimePickerDialog.show();
				}

			});

			holder.timeStopTextView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {

					new TimePickerDialog(AddClassXiaoneiActivity.this,
							new TimePickerDialog.OnTimeSetListener() {

								@Override
								public void onTimeSet(TimePicker view,
										int hourOfDay, int minute) {

									String hour = "" + hourOfDay;
									if (hourOfDay < 10) {
										hour = "0" + hourOfDay;
									}
									String min = "" + minute;
									if (minute < 10) {
										min = "0" + minute;
									}
									holder.timeStopTextView.setText(hour + ":"
											+ min);
									timeStop.set(arg0, hour + ":" + min);
									timeHourStop.set(arg0, hourOfDay);
									timeMinStop.set(arg0, minute);

									if (timeHourStop.get(arg0) < timeHourStart
											.get(arg0)
											|| (timeHourStop.get(arg0) == timeHourStart
													.get(arg0) && timeMinStop
													.get(arg0) / 10 <= timeMinStart
													.get(arg0) / 10)) {
										timeHourStart.set(arg0, hourOfDay - 1);
										timeMinStart.set(arg0, 0);
										String hourStart = "" + (hourOfDay - 1);
										if (hourOfDay == 0) {
											timeHourStart.set(arg0, 23);
											hourStart = ""
													+ timeHourStart.get(arg0);
										}
										if (timeHourStart.get(arg0) < 10) {
											hourStart = "0" + hourStart;
										}
										timeStart.set(arg0, hourStart + ":"
												+ "00");
										holder.timeStartTextView
												.setText(timeStart.get(arg0));
									}

								}
							}, timeHourStop.get(arg0), timeMinStop.get(arg0)

							/**
							 * true��ʾ24Сʱ��ʽ
							 */
							, true).show();
				}

			});

			holder.timeStartTextView.setText(timeStart.get(arg0));
			holder.timeStopTextView.setText(timeStop.get(arg0));

			/*
			 * ȷ����ʼ���ܼ�
			 */
			String[] dayweekStr = getResources().getStringArray(
					R.array.oneDayOfWeek);
			for (int j = 0; j < dayweekStr.length; j++) {
				if (dayweekStr[j].equals(dayOfWeek.get(arg0))) {

					holder.dayOfWeekApinner.setSelection(j);
					break;
				}
			}
			return itemView;
		}

	}

	/**
	 * ���ڰ�������
	 * 
	 * @author wangyuepeng
	 */
	private static class ViewHolder {
		public Spinner dayOfWeekApinner;
		public TextView timeStartTextView;
		public TextView timeStopTextView;

		ViewHolder(View view) {
			dayOfWeekApinner = (Spinner) view
					.findViewById(R.id.spinner_dayofweek);
			timeStartTextView = (TextView) view
					.findViewById(R.id.textview_time_start);
			timeStopTextView = (TextView) view
					.findViewById(R.id.textview_time_stop);
		}
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		InputMethodManager imm = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(class_name_edit.getWindowToken(), 0);
		return true;
	}

	InputMethodManager manager;

	@Override
	public boolean onTouchEvent(MotionEvent event) { // TODO Auto-generated
														// method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (getCurrentFocus() != null
					&& getCurrentFocus().getWindowToken() != null) {
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.onTouchEvent(event);
	}

}
