package com.letv.course.activity;

import com.letv.course.R;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MainActivity extends TabActivity implements	OnCheckedChangeListener {

//	int activitySelected;
	private TabHost mTabHost;
	private Intent tableMainActIntent;
	private Intent notebookActIntent;
	private Intent myInfActIntent;
	RadioButton timetable, notebook, myinfo;
	Drawable timetable_drawable_on, timetable_drawable_off, notebook_drawable_on, notebook_drawable_off, myinfo_drwable_on, myinfo_drwable_off;
	private boolean menuVisible = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tab_bottom);

		ActionBar myActionBar = getActionBar();
		myActionBar.hide();

		Intent intent = getIntent();
		tableMainActIntent = new Intent(this, TableMainActivity.class);
		notebookActIntent = new Intent(this, NotebookActivity.class);
		myInfActIntent = new Intent(this, MyinfoActivity.class);
		
//		if (intent.hasExtra("login")) {
//			String phone_no = intent.getStringExtra("login");
//			this.mainActIntent.putExtra("login", phone_no);
//		}

		timetable = (RadioButton) findViewById(R.id.radio_button_timetable);
		notebook = (RadioButton) findViewById(R.id.radio_button_notebook);
		myinfo = (RadioButton) findViewById(R.id.radio_button_myinfo);
		
		notebook.setOnCheckedChangeListener(this);
		timetable.setOnCheckedChangeListener(this);
		myinfo.setOnCheckedChangeListener(this);
		setupIntent();

		/*
		 * Intent gattServiceIntent = new Intent(this,
		 * BluetoothLeService.class);
		 */

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		if (menuVisible)
			return true;
		else
			return false;
	}

	/*
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { // TODO
	 * Auto-generated method stub if (item.isCheckable()) {
	 * item.setChecked(true); } switch (item.getItemId()) { case
	 * R.id.action_add: if (activitySelected == 0) { Intent intent = new
	 * Intent(this, DeviceAddActivity.class); DeviceAddActivity.startNumber = 0;
	 * startActivity(intent); break; } else if (activitySelected == 2) { Intent
	 * intent = new Intent(this, AddTimeWindow.class); startActivity(intent);
	 * break; } break; } return true; }
	 */

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			switch (buttonView.getId()) {
			case R.id.radio_button_timetable:
				this.mTabHost.setCurrentTabByTag("timetable_TAB");
				setTitle("课表");
				timetable_drawable_on = getResources().getDrawable(R.drawable.timetable_chose);
				notebook_drawable_off = getResources().getDrawable(R.drawable.notebook_unchose);
				myinfo_drwable_off = getResources().getDrawable(R.drawable.myinfo_unchose);
				
				timetable_drawable_on.setBounds(0, 0, timetable_drawable_on.getMinimumWidth(),timetable_drawable_on.getMinimumHeight());
				timetable_drawable_off.setBounds(0, 0, timetable_drawable_off.getMinimumWidth(),timetable_drawable_off.getMinimumHeight());
				myinfo_drwable_off.setBounds(0, 0, myinfo_drwable_off.getMinimumWidth(),myinfo_drwable_off.getMinimumHeight());
				
				timetable.setCompoundDrawables(null, timetable_drawable_on, null, null);
				timetable.setTextColor(0xff1683c1);
				
				notebook.setCompoundDrawables(null, timetable_drawable_off, null, null);
				notebook.setTextColor(0xff717171);
				
				myinfo.setCompoundDrawables(null, myinfo_drwable_off, null, null);
				myinfo.setTextColor(0xff717171);
				menuVisible = true;
				//Activity就会重新调用onCreateOptionMenu()方法重新生成ActionBar
				invalidateOptionsMenu();
				break;

			case R.id.radio_button_notebook:
				this.mTabHost.setCurrentTabByTag("notebook_TAB");
				setTitle("笔记");
				timetable_drawable_off = getResources().getDrawable(R.drawable.timetable_unchose);
				notebook_drawable_on = getResources().getDrawable(R.drawable.notebook_chose);
				myinfo_drwable_off = getResources().getDrawable(R.drawable.myinfo_unchose);
				timetable_drawable_off.setBounds(0, 0, timetable_drawable_off.getMinimumWidth(),timetable_drawable_off.getMinimumHeight());
				notebook_drawable_on.setBounds(0, 0, notebook_drawable_on.getMinimumWidth(),notebook_drawable_on.getMinimumHeight());
				myinfo_drwable_off.setBounds(0, 0, myinfo_drwable_off.getMinimumWidth(),myinfo_drwable_off.getMinimumHeight());
				timetable.setCompoundDrawables(null, timetable_drawable_off, null, null);
				timetable.setTextColor(0xff717171);
				notebook.setCompoundDrawables(null, notebook_drawable_on, null, null);
				notebook.setTextColor(0xff1683c1);
				myinfo.setCompoundDrawables(null, myinfo_drwable_off, null, null);
				myinfo.setTextColor(0xff717171);
				menuVisible = true;
				invalidateOptionsMenu();
				/*
				 * final Intent cintent = new Intent(OTHER_CHANGE_TO_CACTIVITY);
				 * sendBroadcast(cintent);
				 */
				break;

			case R.id.radio_button_myinfo:
				this.mTabHost.setCurrentTabByTag("myinfo_TAB");
				setTitle("我的");
				timetable_drawable_off = getResources().getDrawable(R.drawable.timetable_unchose);
				notebook_drawable_off = getResources().getDrawable(R.drawable.notebook_unchose);
				myinfo_drwable_on = getResources().getDrawable(R.drawable.myinfo_chose);
				timetable_drawable_off.setBounds(0, 0, timetable_drawable_off.getMinimumWidth(),timetable_drawable_off.getMinimumHeight());
				notebook_drawable_off.setBounds(0, 0, notebook_drawable_off.getMinimumWidth(),notebook_drawable_off.getMinimumHeight());
				myinfo_drwable_on.setBounds(0, 0, myinfo_drwable_on.getMinimumWidth(),myinfo_drwable_on.getMinimumHeight());
				timetable.setCompoundDrawables(null, timetable_drawable_off, null, null);
				timetable.setTextColor(0xff717171);
				notebook.setCompoundDrawables(null, notebook_drawable_off, null, null);
				notebook.setTextColor(0xff717171);
				myinfo.setCompoundDrawables(null, myinfo_drwable_on, null, null);
				myinfo.setTextColor(0xff1683c1);
				menuVisible = false;
				invalidateOptionsMenu();
				/*
				 * final Intent eintent = new Intent(OTHER_CHANGE_TO_EACTIVITY);
				 * sendBroadcast(eintent);
				 */
				break;
			}
		}
	}

	private void setupIntent() {
		this.mTabHost = getTabHost();
		TabHost localTabHost = this.mTabHost;
		localTabHost.addTab(
				buildTabSpec("timetable_TAB",
						R.string.main_tab_bottom_timetable,
						R.drawable.timetable_chose, 
						tableMainActIntent)
				);
		localTabHost.addTab(buildTabSpec("notebook_TAB", R.string.main_tab_bottom_notebook,R.drawable.timetable_chose, notebookActIntent));
		localTabHost.addTab(buildTabSpec("myinfo_TAB", R.string.main_tab_bottom_myinfo,R.drawable.timetable_chose, myInfActIntent));

	}

	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,	final Intent content) {
		TabHost.TabSpec tabSpec;
		tabSpec=this.mTabHost.newTabSpec(tag);  //实例化一个分页
		tabSpec.setIndicator(getString(resLabel), getResources().getDrawable(resIcon)); //设置此分页显示的标题  
		tabSpec.setContent(content); //设置此分页的资源
		return tabSpec;
		
//		return this.mTabHost.newTabSpec(tag).setIndicator(getString(resLabel),getResources().getDrawable(resIcon)).setContent(content);
	}
}
