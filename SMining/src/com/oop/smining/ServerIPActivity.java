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
import android.widget.EditText;

public class ServerIPActivity extends Activity {

	private Button cancelButton;
	private Button saveButton;
	private EditText ETserverIP;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serverip);
        setTitle("��������ַ����");
        
        cancelButton = (Button)this.findViewById(R.id.ServerIP_btn_cancel);
        saveButton = (Button)this.findViewById(R.id.ServerIP_btn_save);
        ETserverIP = (EditText)this.findViewById(R.id.ServerIP_edit_server);

        //��ȡ�ϴε�¼��������ַ
        ETserverIP.setText(Utils.getLastServerIP(this));
        
        //����ȡ����ť����
        class cancelButtonListener implements OnClickListener {
			public void onClick(View v) {
        		Intent intent = new Intent(ServerIPActivity.this,LoginActivity.class);
        		startActivity(intent);
        		finish();
        	}
        }
        
      //���ñ��水ť����
        class saveButtonListener implements OnClickListener {
			public void onClick(View v) {
				String serverIP = ETserverIP.getText().toString();
				if(Utils.isValidServerIP(serverIP)) {
					Utils.saveServerIP(ServerIPActivity.this, serverIP);
					Intent intent = new Intent(ServerIPActivity.this,LoginActivity.class);
        			startActivity(intent);
        			finish();
				} else {
	        		//������ʾУ��ʧ�ܣ���������					
				}
        	}
        }
        
        cancelButton.setOnClickListener(new cancelButtonListener());
        saveButton.setOnClickListener(new saveButtonListener());
	}
}
