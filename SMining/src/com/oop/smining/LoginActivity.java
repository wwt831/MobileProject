package com.oop.smining;

import com.oop.utils.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private Button loginButton;
	private EditText loginETUserName;
	private EditText loginETPassword;
	private CheckBox loginCBsave;
	private String username;
	private String password;
	private String serverIP;
	private String serverPort;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		loginButton = (Button) this.findViewById(R.id.login_btn_login);
		loginETUserName = (EditText) this.findViewById(R.id.login_edit_account);
		loginETPassword = (EditText) this.findViewById(R.id.login_edit_pwd);
		loginCBsave = (CheckBox) this.findViewById(R.id.login_cb_savepwd);

		serverIP = Utils.getServerIP(this);
		serverPort = Utils.getServerPort(this);
		username = Utils.getSavedUserName(this);
		password = Utils.getSavedPassword(this, username);
		
		loginETUserName.setText(username);
		loginETPassword.setText(password);
		
		// 定义登录按钮动作
		class loginButtonListener implements OnClickListener {
			public void onClick(View v) {
				username = loginETUserName.getText().toString();
				password = loginETPassword.getText().toString();
				
				boolean isValidUser;
				// TODO:到服务器端验证用户有效性
				isValidUser = true;
				
				if(isValidUser) {
					if(loginCBsave.isChecked()) {
						Utils.saveUserName(LoginActivity.this, username);
						Utils.saveUserPassword(LoginActivity.this, username, password);
					}
					startActivity(new Intent(LoginActivity.this, MainActivity.class));
					finish();
				} else {
					
				}
			}
		}

		loginButton.setOnClickListener(new loginButtonListener());


	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.login_menu_serversettings:
			startActivity(new Intent(LoginActivity.this, ServerIPActivity.class));
			return true;
		case R.id.login_menu_quit:
			finish();
			return true;
		}
		return false;
	}

}
