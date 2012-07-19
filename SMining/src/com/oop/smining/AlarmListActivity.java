package com.oop.smining;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class AlarmListActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_alarmlist, menu);
        return true;
    }

    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    	case R.id.menu_quit:
    		finish();
    		return true;
    	case R.id.menu_main:
    		startActivity(new Intent(AlarmListActivity.this,MainActivity.class));
    		finish();
    		return true;
    	}
    	return false;
    }
}
