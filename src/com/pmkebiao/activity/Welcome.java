package com.pmkebiao.activity;

import com.letv.course.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

public class Welcome extends Activity {

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
				loadMainActivity();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome_layout);

		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// }
		// }).start();

		new Thread() {

			@Override
			public void run() {
				try {
					sleep(1500);
					handler.sendEmptyMessage(0);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();

	}

	public void loadMainActivity() {
		Intent intent = new Intent(Welcome.this, MainActivity.class);
		startActivity(intent);
		finish();
	}

}
