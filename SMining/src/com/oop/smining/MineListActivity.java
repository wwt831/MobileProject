package com.oop.smining;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.support.v4.app.NavUtils;

public class MineListActivity extends Activity {
	ListView lv_mine;
	Button but_return;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_list);
        
        but_return = (Button)this.findViewById(R.id.but_return);
		class returnButtonListener implements OnClickListener {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MineListActivity.this, LeaderActivity.class);
				startActivity(intent);
				MineListActivity.this.finish();
			}
		}
        but_return.setOnClickListener(new returnButtonListener());
        
        lv_mine = (ListView)this.findViewById(R.id.mine_list);
        JSONArray arrData = null;
        JSONObject jsonObj = Utils.getDemoData(MineListActivity.this, "mine");	
        try {
			arrData = jsonObj.getJSONArray("data");
		} catch (JSONException e) {
			e.printStackTrace();
		}
        int len = arrData.length();
        String []name = new String[len];
        for(int i=0;i<len;i++) {
        	try {
				name[i] = ((JSONObject)arrData.get(i)).getString("mine_name");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice, name);
        lv_mine.setAdapter(arrayadapter);
        lv_mine.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv_mine.setOnItemClickListener(mOnClickListener);
    }

	private AdapterView.OnItemClickListener mOnClickListener = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
			String select_mine = (String)lv_mine.getItemAtPosition(position);//获取选中煤矿名称
			Intent intent = new Intent();
			intent.putExtra("result", select_mine);//把返回数据存入Intent
			MineListActivity.this.setResult(1, intent);//设置回传数据。resultCode值是1(可以是任意值),在onActivityResult对应resultCode       
			MineListActivity.this.finish();//关闭子窗口ChildActivity 
		}
	};
	
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
			intent.setClass(MineListActivity.this, AlarmListActivity.class);
			startActivity(intent);
			return true;
		case R.id.menu_update:
			startActivity(new Intent(MineListActivity.this, UpdateAppActivity.class));
			finish();
			return true;
		case R.id.menu_quit:
			finish();
			return true;
		}
		return false;
	}    
}
