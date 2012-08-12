package com.oop.smining;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import com.oop.utils.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class UpdateAppActivity extends Activity {

	private Button btnUpdateApp;
	private ProgressDialog pBar;

	private Handler handler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);

		TextView tv = (TextView)findViewById(R.id.txt_CurrentVersion);
		if (!Utils.isNetworkAvailable(this)) {
			tv.append("\n\n请检查网络……");
			return;
		} else {
			tv.append("\n\n网络可用：\n");
			//TODO:显示当前版本信息
		}

		btnUpdateApp = (Button) findViewById(R.id.updateapp_btn_update);
		btnUpdateApp.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				try {
					checkToUpdate();
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//处理返回键事件
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			startActivity(new Intent(UpdateAppActivity.this, MainActivity.class));
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}



	// 检查新版本并更新
	private void checkToUpdate() throws NameNotFoundException {
		JSONArray serverVer;
		int currentVerCode = Utils.getCurrentVersionCode(this);
		String currentVerName = Utils.getCurrentVersionName(this);
		int serverVerCode = 0;
		String serverVerName = "";

		try {
			serverVer = new JSONArray(Utils.getServerVersionJSON());
			if (serverVer.length() > 0) {
				serverVerCode = serverVer.getJSONObject(0).getInt("VerCode");
				serverVerCode = serverVer.getJSONObject(0).getInt("VerName");
				if (serverVerCode > currentVerCode) {
					// 弹出更新对话框
					StringBuffer sb = new StringBuffer();
					sb.append("当前版本：");
					sb.append(currentVerName);
					sb.append("VerCode:");
					sb.append(Utils.getCurrentVersionCode(this));
					sb.append("\n");
					sb.append("发现新版本：");
					sb.append(serverVerName);
					sb.append("NewVerCode:");
					sb.append(serverVerCode);
					sb.append("\n");
					sb.append("是否更新？");
					Dialog dialog = new AlertDialog.Builder(
							UpdateAppActivity.this)
							.setTitle("软件更新")
							.setMessage(sb.toString())
							.setPositiveButton("更新",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											showProgressBar();// 更新当前版本
										}
									})
							.setNegativeButton("暂不更新",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
										}
									}).create();
					dialog.show();
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void showProgressBar() {
		pBar = new ProgressDialog(UpdateAppActivity.this);
		pBar.setTitle("正在下载");
		pBar.setMessage("请稍候……");
		pBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		downAppFile();
	}



	protected void downAppFile() {
		String serverIP = "";
		int serverPort = 0;
		String serverPath = "";
		final String url = serverIP + ":" + serverPort + "/" + serverPath;
		pBar.show();
		new Thread() {
			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(url);
				HttpResponse response;
				try {
					response = client.execute(get);
					HttpEntity entity = response.getEntity();
					long length = entity.getContentLength();
					Log.isLoggable("DownTag", (int) length);
					InputStream is = entity.getContent();
					FileOutputStream fileOutputStream = null;
					if (is == null) {
						throw new RuntimeException("isStream is null");
					}
					File file = new File(
							Environment.getExternalStorageDirectory(),
							getResources().getText(R.string.app_filename).toString());
					fileOutputStream = new FileOutputStream(file);
					byte[] buf = new byte[1024];
					int ch = -1;
					do {
						ch = is.read(buf);
						if (ch <= 0)
							break;
						fileOutputStream.write(buf, 0, ch);
					} while (true);
					is.close();
					fileOutputStream.close();
					haveDownLoad();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	//取消进度条，开始新应用
	protected void haveDownLoad() {
		handler.post(new Runnable() {
			public void run() {
				pBar.cancel();
				// 弹出警告框，提示是否安装新的版本
				Dialog installDialog = new AlertDialog.Builder(
						UpdateAppActivity.this)
						.setTitle("下载完成")
						.setMessage("是否安装新的应用")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										installNewApk();
										finish();
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										finish();
									}
								}).create();
				installDialog.show();
			}
		});
	}

	// 安装新的应用
	protected void installNewApk() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), 
				getResources().getText(R.string.app_filename).toString())),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}

}