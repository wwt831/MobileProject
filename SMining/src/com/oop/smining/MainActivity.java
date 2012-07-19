package com.oop.smining;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO:处理返回键及HOME键事件
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			ExitAlert();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.default_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_alarmlist:
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, AlarmListActivity.class);
			startActivity(intent);
			finish();
			return true;
		case R.id.menu_update:
			startActivity(new Intent(MainActivity.this, UpdateAppActivity.class));
			finish();
			return true;
		case R.id.menu_quit:
			finish();
			return true;
		}
		return false;
	}

	private void ExitAlert() {

	}
}
