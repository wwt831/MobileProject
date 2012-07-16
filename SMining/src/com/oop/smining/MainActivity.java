package com.oop.smining;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

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
			startActivity(new Intent(MainActivity.this, AlarmListActivity.class));
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
