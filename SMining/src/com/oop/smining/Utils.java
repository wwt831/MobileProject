package com.oop.smining;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class Utils {

	// 检查网络是否可用
	public static boolean isNetworkAvailable(Context context) {
		try {

			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netWorkInfo = cm.getActiveNetworkInfo();
			return (netWorkInfo != null && netWorkInfo.isAvailable());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static int getCurrentVerCode(Context context)throws NameNotFoundException{
		int verCode = -1;
		try{
			verCode = context.getPackageManager().getPackageInfo(
					context.getResources().getText(R.string.app_name).toString(),0).versionCode;	
		}catch(Exception e){
			Log.e("CurrentVersion", e.getMessage());
		}
		return verCode;
	}
	
	public static String getCurrentVerName(Context context){
		String verName = "";
		try{
			verName = context.getPackageManager().getPackageInfo(
					context.getResources().getText(R.string.app_name).toString(), 0).versionName;
		}catch(Exception e){
			Log.e("CurrentVersion", e.getMessage());
		}
		return verName;
	}
	
	//获得服务器端的应用程序版本描述串
	//JSON格式定义见“SMining数据传输规范”
	public static String getServerVerJSON() throws Exception{
		//TODO:处理服务器地址的问题
		String serverIP = "";
		int serverPort = 0;
		String serverPath = "";
		
		StringBuilder newVerJSON = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpParams httpParams = client.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 3000);//设置连接超时范围
		HttpConnectionParams.setSoTimeout(httpParams, 5000);
		
		//serverPath是version.json的路径
		HttpResponse response = client.execute(new HttpGet(serverIP + ":" + serverPort + "/" + serverPath));
		HttpEntity entity = response.getEntity();
		if(entity != null){
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(entity.getContent(),"UTF-8"),8192);
			String line = null;
			while((line = reader.readLine()) != null){
				newVerJSON.append(line+"\n");//按行读取放入StringBuilder中
			}
			reader.close();
		}
		return newVerJSON.toString();
	}
}
