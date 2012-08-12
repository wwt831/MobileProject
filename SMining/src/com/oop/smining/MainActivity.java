package com.oop.smining;

import java.util.ArrayList;
import java.util.HashMap;

import com.oop.utils.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {

	private GridView gridview; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		gridview = (GridView) findViewById(R.id.gridView9);

		// 生成动态数组，并且转入数据
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();

		if(Utils.getEnabled(this, "gas")) {
			HashMap<String, Object> map1 = new HashMap<String, Object>();
			map1.put("ItemImage", R.drawable.main_01);// 添加图像资源的ID
			map1.put("ItemText", "安全生产监督");
			lstImageItem.add(map1);
		}
		
		if(Utils.getEnabled(this, "project")) {
			HashMap<String, Object> map2 = new HashMap<String, Object>();
			map2.put("ItemImage", R.drawable.main_02);// 添加图像资源的ID
			map2.put("ItemText", "重点工程管理");
			lstImageItem.add(map2);
		}
		
		if(Utils.getEnabled(this, "rescue")) {
			HashMap<String, Object> map3 = new HashMap<String, Object>();
			map3.put("ItemImage", R.drawable.main_03);// 添加图像资源的ID
			map3.put("ItemText", "应急救援管理");
			lstImageItem.add(map3);
		}
		
		if(Utils.getEnabled(this, "hiddendanger")) {
			HashMap<String, Object> map4 = new HashMap<String, Object>();
			map4.put("ItemImage", R.drawable.main_04);// 添加图像资源的ID
			map4.put("ItemText", "隐患排查整改");
			lstImageItem.add(map4);
		}
		
		if(Utils.getEnabled(this, "alertlist")) {
			HashMap<String, Object> map5 = new HashMap<String, Object>();
			map5.put("ItemImage", R.drawable.main_05);// 添加图像资源的ID
			map5.put("ItemText", "综合报警管理");
			lstImageItem.add(map5);
		}
		
		if(Utils.getEnabled(this, "leader")) {
			HashMap<String, Object> map6 = new HashMap<String, Object>();
			map6.put("ItemImage", R.drawable.main_06);// 添加图像资源的ID
			map6.put("ItemText", "领导干部下井");
			lstImageItem.add(map6);
		}
		
		if(Utils.getEnabled(this, "accident")) {
			HashMap<String, Object> map7 = new HashMap<String, Object>();
			map7.put("ItemImage", R.drawable.main_07);// 添加图像资源的ID
			map7.put("ItemText", "事故汇报处理");
			lstImageItem.add(map7);
		}
		
		if(Utils.getEnabled(this, "management")) {
			HashMap<String, Object> map8 = new HashMap<String, Object>();
			map8.put("ItemImage", R.drawable.main_08);// 添加图像资源的ID
			map8.put("ItemText", "煤炭行业管理");
			lstImageItem.add(map8);
		}
		
		if(Utils.getEnabled(this, "field")) {
			HashMap<String, Object> map9 = new HashMap<String, Object>();
			map9.put("ItemImage", R.drawable.main_09);// 添加图像资源的ID
			map9.put("ItemText", "生产现场监控");
			lstImageItem.add(map9);
		}
		
		if(Utils.getEnabled(this, "notice")) {
			HashMap<String, Object> map10 = new HashMap<String, Object>();
			map10.put("ItemImage", R.drawable.main_10);// 添加图像资源的ID
			map10.put("ItemText", "通知公告");
			lstImageItem.add(map10);
		}
		
		if(Utils.getEnabled(this, "todo")) {
			HashMap<String, Object> map11 = new HashMap<String, Object>();
			map11.put("ItemImage", R.drawable.main_11);// 添加图像资源的ID
			map11.put("ItemText", "待办事项");
			lstImageItem.add(map11);
		}
		
		if(Utils.getEnabled(this, "contact")) {
			HashMap<String, Object> map12 = new HashMap<String, Object>();
			map12.put("ItemImage", R.drawable.main_12);// 添加图像资源的ID
			map12.put("ItemText", "通讯录");
			lstImageItem.add(map12);
		}
		
		// 生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
		SimpleAdapter saImageItems = new SimpleAdapter(this,
				lstImageItem,// 数据来源
				R.layout.gridview_item,// gridview_item的XML实现

				// 动态数组与ImageItem对应的子项
				new String[] { "ItemImage", "ItemText" },

				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.ItemImage, R.id.ItemText });
		// 添加并且显示
		gridview.setAdapter(saImageItems);
		// 添加消息处理
		gridview.setOnItemClickListener(new ItemClickListener());
	}

	// 当AdapterView被单击(触摸屏或者键盘)，则返回的Item单击事件
	class ItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0,// The AdapterView where the
													// click happened
				View arg1,// The view within the AdapterView that was clicked
				int arg2,// The position of the view in the adapter
				long arg3// The row id of the item that was clicked
		) {
			// 在这里中arg2=arg3
			HashMap<String, Object> item = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);
			// 定义按钮的动作
			Intent intent = new Intent();
			// 顺序从左上到右下(0..n)
			switch(arg2) {
			case 4: //综合报警管理
				intent.setClass(MainActivity.this, AlarmListActivity.class);
				startActivity(intent);
				break;
			case 9: //通知公告
				intent.setClass(MainActivity.this, cordovaExample.class);
				startActivity(intent);
				break;
			default:
					Toast.makeText(MainActivity.this, String.valueOf(arg2), 500).show();
					Toast.makeText(MainActivity.this, String.valueOf(item.get("ItemText")), 500).show();
			}
			
		}

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
