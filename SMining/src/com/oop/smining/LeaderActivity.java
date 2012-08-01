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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class LeaderActivity extends Activity {
	private Button mineButton;
	private Button dateButton;
	TabHost tab;
	ListView lv_count;
	ListAdapter countAdapter = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader);
        
        mineButton = (Button)this.findViewById(R.id.coal);
        mineButton.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent = new Intent(LeaderActivity.this,MineListActivity.class);
        		startActivityForResult(intent, 0);//能获取返回值的跳转,0(任意值)在onActivityResult对应requestCode
        	}
        });
        
        dateButton = (Button)this.findViewById(R.id.btnTab1);
        dateButton.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent = new Intent(LeaderActivity.this,TimeActivity.class);
        		startActivity(intent);
        		finish();
        	}
        });

        TabHost tabs = (TabHost)findViewById(R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec("Tag1");
        spec.setContent(R.id.Tab1);//点击Tab要显示的内容
        spec.setIndicator("下井次数统计");
//        spec.setIndicator("下井统计", getResources().getDrawable(R.drawable.ic_launcher));
        tabs.addTab(spec);
        spec = tabs.newTabSpec("Tag2");
        spec.setContent(R.id.Tab2);
        spec.setIndicator("跟班下井情况");
//        spec.setIndicator("跟班下井情况", getResources().getDrawable(R.drawable.smining_launcher));
        tabs.addTab(spec);
        tabs.setCurrentTab(0);
//        tabs.setOnTabChangedListener(new OnTabChangeListener() {
//        	public void onTabChanged(String tabId) {
//        		
//        	}
//        }); 

        lv_count = (ListView) this.findViewById(R.id.count_list);
        
        JSONArray arrData = null;
        JSONObject jsonObj = Utils.getDemoData(LeaderActivity.this, "alarm");

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
				item.put("leader_name", ((JSONObject)arrData.get(i)).getString("monitor_type"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	try {
				item.put("leader_duty", ((JSONObject)arrData.get(i)).getString("mine_name"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	try {
				item.put("leader_count",((JSONObject)arrData.get(i)).getString("datetime"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	data.add(item);
        }
        SimpleAdapter countAdapter = new SimpleAdapter(this, data, R.layout.listview_item, new String[]{"leader_name","leader_duty","leader_count"}, new int[]{R.id.leader_name, R.id.leader_duty, R.id.leader_count});
        lv_count.setAdapter(countAdapter);
        lv_count.setOnItemClickListener(new ItemClickEvent());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	if(resultCode==1){
    		Bundle bundle = data.getExtras();
    		mineButton.setText(bundle.getString("result").toString());
    	}
    }
    
    class ItemClickEvent implements OnItemClickListener {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Toast.makeText(LeaderActivity.this, "选中"+String.valueOf(arg2)+"行", 500).show();
		}
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
			intent.setClass(LeaderActivity.this, AlarmListActivity.class);
			startActivity(intent);
			return true;
		case R.id.menu_update:
			startActivity(new Intent(LeaderActivity.this, UpdateAppActivity.class));
			finish();
			return true;
		case R.id.menu_quit:
			finish();
			return true;
		}
		return false;
	}
    
}
