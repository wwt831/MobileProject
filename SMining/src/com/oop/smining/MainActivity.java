package com.oop.smining;

import java.util.ArrayList;
import java.util.HashMap;

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

		// ���ɶ�̬���飬����ת������
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();

		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("ItemImage", R.drawable.h001);// ���ͼ����Դ��ID
		map1.put("ItemText", "��ȫ�����ල");
		lstImageItem.add(map1);
		
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("ItemImage", R.drawable.h001);// ���ͼ����Դ��ID
		map2.put("ItemText", "�ص㹤�̹���");
		lstImageItem.add(map2);
		
		HashMap<String, Object> map3 = new HashMap<String, Object>();
		map3.put("ItemImage", R.drawable.h001);// ���ͼ����Դ��ID
		map3.put("ItemText", "Ӧ����Ԯ����");
		lstImageItem.add(map3);
		
		HashMap<String, Object> map4 = new HashMap<String, Object>();
		map4.put("ItemImage", R.drawable.h001);// ���ͼ����Դ��ID
		map4.put("ItemText", "�����Ų�����");
		lstImageItem.add(map4);
		
		HashMap<String, Object> map5 = new HashMap<String, Object>();
		map5.put("ItemImage", R.drawable.h001);// ���ͼ����Դ��ID
		map5.put("ItemText", "�ۺϱ�������");
		lstImageItem.add(map5);
		
		HashMap<String, Object> map6 = new HashMap<String, Object>();
		map6.put("ItemImage", R.drawable.h001);// ���ͼ����Դ��ID
		map6.put("ItemText", "�쵼�ɲ��¾�");
		lstImageItem.add(map6);
		
		HashMap<String, Object> map7 = new HashMap<String, Object>();
		map7.put("ItemImage", R.drawable.h001);// ���ͼ����Դ��ID
		map7.put("ItemText", "�¹ʻ㱨����");
		lstImageItem.add(map7);
		
		HashMap<String, Object> map8 = new HashMap<String, Object>();
		map8.put("ItemImage", R.drawable.h001);// ���ͼ����Դ��ID
		map8.put("ItemText", "ú̿��ҵ����");
		lstImageItem.add(map8);
		
		HashMap<String, Object> map9 = new HashMap<String, Object>();
		map9.put("ItemImage", R.drawable.h001);// ���ͼ����Դ��ID
		map9.put("ItemText", "�����ֳ����");
		lstImageItem.add(map9);
		
		HashMap<String, Object> map10 = new HashMap<String, Object>();
		map10.put("ItemImage", R.drawable.h001);// ���ͼ����Դ��ID
		map10.put("ItemText", "֪ͨ����");
		lstImageItem.add(map10);
		
		HashMap<String, Object> map11 = new HashMap<String, Object>();
		map11.put("ItemImage", R.drawable.h001);// ���ͼ����Դ��ID
		map11.put("ItemText", "��������");
		lstImageItem.add(map11);
		
		HashMap<String, Object> map12 = new HashMap<String, Object>();
		map12.put("ItemImage", R.drawable.h001);// ���ͼ����Դ��ID
		map12.put("ItemText", "ͨѶ¼");
		lstImageItem.add(map12);
		
		// ������������ImageItem <====> ��̬�����Ԫ�أ�����һһ��Ӧ
		SimpleAdapter saImageItems = new SimpleAdapter(this,
				lstImageItem,// ������Դ
				R.layout.gridview_item,// gridview_item��XMLʵ��

				// ��̬������ImageItem��Ӧ������
				new String[] { "ItemImage", "ItemText" },

				// ImageItem��XML�ļ������һ��ImageView,����TextView ID
				new int[] { R.id.ItemImage, R.id.ItemText });
		// ��Ӳ�����ʾ
		gridview.setAdapter(saImageItems);
		// �����Ϣ����
		gridview.setOnItemClickListener(new ItemClickListener());
	}

	// ��AdapterView������(���������߼���)���򷵻ص�Item�����¼�
	class ItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0,// The AdapterView where the
													// click happened
				View arg1,// The view within the AdapterView that was clicked
				int arg2,// The position of the view in the adapter
				long arg3// The row id of the item that was clicked
		) {
			// ��������arg2=arg3
			HashMap<String, Object> item = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);
			// ���尴ť�Ķ���
			// ˳������ϵ�����(0..n)
			switch(arg2) {
			case 4: //�ۺϱ�������
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, AlarmListActivity.class);
				startActivity(intent);
				break;
			case 5: 
				startActivity(new Intent(MainActivity.this, LeaderActivity.class));
				break;
			case 9: 
				startActivity(new Intent(MainActivity.this, NoticeActivity.class));
				break;
			default:
					Toast.makeText(MainActivity.this, String.valueOf(arg2), 500).show();
					Toast.makeText(MainActivity.this, String.valueOf(item.get("ItemText")), 500).show();
			}
			
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO:�����ؼ���HOME���¼�
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
