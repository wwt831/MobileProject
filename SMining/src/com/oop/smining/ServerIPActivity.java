package com.oop.smining;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ServerIPActivity extends Activity {

	private Button button;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serverip);
        
        //设置取消按钮动作
        button = (Button)this.findViewById(R.id.ServerIP_btn_cancel);
        button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
        		Intent intent = new Intent(ServerIPActivity.this,LoginActivity.class);
        		startActivity(intent);
        		finish();
        	}
        });
        
        //设置保存按钮动作
        button = (Button)this.findViewById(R.id.ServerIP_btn_save);
        button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
        		//TODO:校验输入，保存。
        		//如果校验成功，回到登录页面
				Intent intent = new Intent(ServerIPActivity.this,LoginActivity.class);
        		startActivity(intent);
        		finish();
        		//否则提示校验失败，重新输入
        	}
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
