package com.letv.course.activity;
import com.letv.course.R;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MyMainActivity extends TabActivity implements	OnCheckedChangeListener {
	/*
	 * public final static String OTHER_CHANGE_TO_CACTIVITY =
	 * "wangyuepeng.changetocactivity"; public final static String
	 * OTHER_CHANGE_TO_AACTIVITY = "wangyuepeng.changetoaactivity"; public final
	 * static String OTHER_CHANGE_TO_EACTIVITY =
	 * "wangyuepeng.changetoeactivity";
	 */
	int activitySelected;
	private TabHost mTabHost;
	private Intent tableMainActIntent;
	private Intent noteActIntent;
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
		this.tableMainActIntent = new Intent(this, TableMainActivity.class);
//		this.mCIntent = new Intent(this, NoteActivity.class);
//		this.mEIntent = new Intent(this, MyMessageActivity.class);
		
//		if (intent.hasExtra("login")) {
//			String phone_no = intent.getStringExtra("login");
//			this.mainActIntent.putExtra("login", phone_no);
//		}

		(timetable = (RadioButton) findViewById(R.id.radio_button_timetable)).setOnCheckedChangeListener(this);
		(notebook = (RadioButton) findViewById(R.id.radio_button_notebook)).setOnCheckedChangeListener(this);
		(myinfo = (RadioButton) findViewById(R.id.radio_button_myinfo)).setOnCheckedChangeListener(this);

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
				activitySelected = 0;
				setTitle("课表");
				Resources contral_res = getResources();
				timetable_drawable_on = contral_res.getDrawable(R.drawable.timetable_chose);
				notebook_drawable_off = contral_res.getDrawable(R.drawable.notebook_unchose);
				myinfo_drwable_off = contral_res.getDrawable(R.drawable.myinfo_unchose);
				
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
				invalidateOptionsMenu();
				/*
				 * final Intent aintent = new Intent(OTHER_CHANGE_TO_AACTIVITY);
				 * sendBroadcast(aintent);
				 */
				break;

			case R.id.radio_button_notebook:
				this.mTabHost.setCurrentTabByTag("notebook_TAB");
				activitySelected = 2;
				setTitle("笔记");
				Resources contral_res2 = getResources();
				timetable_drawable_off = contral_res2.getDrawable(R.drawable.timetable_unchose);
				notebook_drawable_on = contral_res2.getDrawable(R.drawable.notebook_chose);
				myinfo_drwable_off = contral_res2.getDrawable(R.drawable.myinfo_unchose);
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
				activitySelected = 4;
				setTitle("我的");
				Resources contral_res4 = getResources();
				timetable_drawable_off = contral_res4.getDrawable(R.drawable.timetable_unchose);
				notebook_drawable_off = contral_res4.getDrawable(R.drawable.notebook_unchose);
				myinfo_drwable_on = contral_res4.getDrawable(R.drawable.myinfo_chose);
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
		localTabHost.addTab(buildTabSpec("timetable_TAB", R.string.main_tab_bottom_timetable,R.drawable.timetable_chose, this.tableMainActIntent));
		localTabHost.addTab(buildTabSpec("notebook_TAB", R.string.main_tab_bottom_notebook,R.drawable.timetable_chose, this.noteActIntent));
		localTabHost.addTab(buildTabSpec("myinfo_TAB", R.string.main_tab_bottom_myinfo,R.drawable.timetable_chose, this.myInfActIntent));

	}

	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,	final Intent content) {
		return this.mTabHost.newTabSpec(tag).setIndicator(getString(resLabel),getResources().getDrawable(resIcon)).setContent(content);
	}
}
