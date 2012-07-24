package com.oop.smining;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.oop.utils.TableAdapter;
import com.oop.utils.Utils;
import com.oop.utils.TableAdapter.TableCell;
import com.oop.utils.TableAdapter.TableRow;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AlarmListActivity extends Activity {

	ListView lv_alarm;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);
        
        lv_alarm = (ListView) this.findViewById(R.id.listView_alarmlist);
        
        ArrayList<TableRow> table = new ArrayList<TableRow>();

        //表头定义
        TableCell[] titles = new TableCell[4];
        
        titles[0] = new TableCell("序号",
        		80,
        		LayoutParams.FILL_PARENT,
        		TableCell.STRING);
        titles[1] = new TableCell("类型",
        		80,
        		LayoutParams.FILL_PARENT,
        		TableCell.STRING);
        titles[2] = new TableCell("时间",
        		240,
        		LayoutParams.FILL_PARENT,
        		TableCell.STRING);
        titles[3] = new TableCell("单位",
        		320,
        		LayoutParams.FILL_PARENT,
        		TableCell.STRING);
        table.add(new TableRow(titles));
        
        //获取Demo数据
        JSONArray arrData = null;
        JSONObject jsonObj = Utils.getDemoData(AlarmListActivity.this, "alarm");

        try {
			arrData = jsonObj.getJSONArray("data");
		} catch (JSONException e) {
			e.printStackTrace();
		}
        
        //每行数据定义
        for(int i=0;i<arrData.length();i++) {
        	TableCell[] cells = new TableCell[4];
        	cells[0] = new TableCell(String.valueOf(i+1),
        			titles[0].width,
        			LayoutParams.FILL_PARENT,
        			TableCell.STRING);
        	try {
				cells[1] = new TableCell(((JSONObject)arrData.get(i)).getString("monitor_type"),
						titles[1].width,
						LayoutParams.FILL_PARENT,
						TableCell.STRING);
			} catch (JSONException e) {
				cells[1] = new TableCell("err",
						titles[1].width,
						LayoutParams.FILL_PARENT,
						TableCell.STRING);
			}
        	try {
				cells[2] = new TableCell(((JSONObject)arrData.get(i)).getString("datetime"),
						titles[2].width,
						LayoutParams.FILL_PARENT,
						TableCell.STRING);
			} catch (JSONException e) {
				cells[2] = new TableCell("err",
						titles[2].width,
						LayoutParams.FILL_PARENT,
						TableCell.STRING);
			}
        	try {
				cells[3] = new TableCell(((JSONObject)arrData.get(i)).getString("mine_name"),
						titles[3].width,
						LayoutParams.FILL_PARENT,
						TableCell.STRING);
			} catch (JSONException e) {
				cells[3] = new TableCell("err",
						titles[3].width,
						LayoutParams.FILL_PARENT,
						TableCell.STRING);
			}
        	//把表格的行添加到表格
           	table.add(new TableRow(cells));
        }
        //显示数据
        TableAdapter tableAdapter = new TableAdapter(this, table);
        lv_alarm.setAdapter(tableAdapter);
        lv_alarm.setOnItemClickListener(new ItemClickEvent());
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
    
    class ItemClickEvent implements OnItemClickListener {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Toast.makeText(AlarmListActivity.this, "选中"+String.valueOf(arg2)+"行", 500).show();
		}
    }
}
