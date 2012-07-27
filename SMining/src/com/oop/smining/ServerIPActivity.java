package com.oop.smining;

import com.oop.utils.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ServerIPActivity extends Activity {

	private Button saveButton;
	private EditText ETserverIP;
	private String serverIP;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_serverip);
		setTitle("服务器地址设置");

		saveButton = (Button) this.findViewById(R.id.ServerIP_btn_save);
		ETserverIP = (EditText) this.findViewById(R.id.ServerIP_edit_server);

		serverIP = Utils.getServerIP(this);
		ETserverIP.setText(serverIP);

		// 设置保存按钮动作
		class saveButtonListener implements OnClickListener {
			public void onClick(View v) {
				String serverIP = ETserverIP.getText().toString();
				if (Utils.isValidServerIP(serverIP)) {
					Utils.saveServerIP(ServerIPActivity.this, serverIP);
					Intent intent = new Intent(ServerIPActivity.this,
							LoginActivity.class);
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(ServerIPActivity.this, "IP地址无效！", 300)
							.show();
				}
			}
		}

		saveButton.setOnClickListener(new saveButtonListener());
	}
}
