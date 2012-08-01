package com.oop.smining;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.oop.utils.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.support.v4.app.NavUtils;

public class NoticeActivity extends Activity {

	ListView lv_notice;
	Button but_return;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        but_return = (Button)this.findViewById(R.id.button1);
		class returnButtonListener implements OnClickListener {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(NoticeActivity.this, MainActivity.class);
				startActivity(intent);
				NoticeActivity.this.finish();
			}
		}
        but_return.setOnClickListener(new returnButtonListener());

        lv_notice = (ListView) this.findViewById(R.id.notice_list);
        JSONArray arrData = null;
        JSONObject jsonObj = Utils.getDemoData(NoticeActivity.this, "alarm");
        try {
			arrData = jsonObj.getJSONArray("data");
		} catch (JSONException e) {
			e.printStackTrace();
		}
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
        for(int i=0; i<arrData.length(); i++)
        {
        	HashMap<String, Object> item = new HashMap<String, Object>();
        	try {
				item.put("notice_title", ((JSONObject)arrData.get(i)).getString("mine_name"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	try {
				item.put("notice_time", ((JSONObject)arrData.get(i)).getString("datetime"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	try {
				item.put("notice_read_status",((JSONObject)arrData.get(i)).getString("monitor_type"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	data.add(item);
        }
        SimpleAdapter countAdapter = new SimpleAdapter(this, data, R.layout.notice_listview_item, new String[]{"notice_title","notice_time","notice_read_status"}, new int[]{R.id.notice_title, R.id.notice_time, R.id.notice_read_status});
        lv_notice.setAdapter(countAdapter);
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
			intent.setClass(NoticeActivity.this, AlarmListActivity.class);
			startActivity(intent);
			return true;
		case R.id.menu_update:
			startActivity(new Intent(NoticeActivity.this, UpdateAppActivity.class));
			finish();
			return true;
		case R.id.menu_quit:
			finish();
			return true;
		}
		return false;
	}
}
