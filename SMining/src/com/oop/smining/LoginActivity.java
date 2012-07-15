package com.oop.smining;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends Activity {

	private Button button;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        //TODO:��ʼ��Ӧ�ó���
        //TODO:��ȡ��������ַ
        //TODO:��ȡ������û���Ϣ
        button = (Button)this.findViewById(R.id.login_btn_login);
        button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
        		//TODO:��֤�û���Ч��
        		startActivity(new Intent(LoginActivity.this,MainActivity.class));
        		finish();
        	}
        });
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }

    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    	case R.id.login_menu_serversettings:
    		startActivity(new Intent(LoginActivity.this,ServerIPActivity.class));
    		finish();
    		return true;
    	case R.id.login_menu_quit:
    		finish();
    		return true;
    	}
    	return false;
    }
    
}
