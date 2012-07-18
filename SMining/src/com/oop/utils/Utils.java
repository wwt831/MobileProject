package com.oop.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.oop.smining.R;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class Utils {

	//获得当前日期时间
	public static String getCurrentlyDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(new Date());
	}
	
	//获得明天的日期
	public static String getNextDate(String dateTime) {
		String[] date = dateTime.split("-");
		int day = Integer.valueOf(date[2]);
		int month = Integer.valueOf(date[1]);
		int year = Integer.valueOf(date[0]);
		StringBuffer buffer = new StringBuffer();
		if (Constant.MONTH_MAP.get(month)==(day)) {
			day = 1;
			month++;
			if (month == 13) {
				month = 1;
				year++;
			}
		} else {
			day++;
		}
		buffer.append(year).append("-");
		if (month < 10) {
			buffer.append("0");
		}
		buffer.append(month).append("-");
		if (day < 10) {
			buffer.append("0");
		}
		buffer.append(day);
		return buffer.toString();
	}

	//判断是否是晚上
	public static boolean isNight() {
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		if (hour >= 20 || hour <= 7) {
			return true;
		}
		return false;
	}
	
	//检查网络是否可用
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
	
	//编码转换UTF8
	public static String encodeUTF8(String str) {
		try {
			str = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	//设置首选服务器地址
	public static void setDefaultServerIP(Context context, String serverIP) {
		OutputStream out = null;
		try {
			out = context.openFileOutput("smining.cfg", Context.MODE_PRIVATE);
			Properties properties = new Properties();
			properties.setProperty("DefaultServerIP", encodeUTF8(serverIP));
			properties.store(out, "");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//获取首选服务器地址
	public static String getDefaultServerIP(Context context) {
		InputStream in;
		try {
			in = context.openFileInput("smining.cfg");
		} catch (FileNotFoundException e1) {
			return encodeUTF8(context.getString(R.string.default_serverip));
		}
		Properties properties = new Properties();
		try {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (String) properties.get("default_serverip");
	}
	
	//设置首选服务器端口
	public static void setDefaultServerPort(Context context, String port) {
		OutputStream out = null;
		try {
			out = context.openFileOutput("smining.cfg", Context.MODE_PRIVATE);
			Properties properties = new Properties();
			properties.setProperty("DefaultServerPort", encodeUTF8(port));
			properties.store(out, "");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//获取首选服务器端口
	public static String getDefaultServerPort(Context context) {
		InputStream in;
		try {
			in = context.openFileInput("smining.cfg");
		} catch (FileNotFoundException e1) {
			return encodeUTF8(context.getString(R.string.default_port));
		}
		Properties properties = new Properties();
		try {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (String) properties.get("default_port");
	}
	
	//获得当前版本代码
	public static int getCurrentVersionCode(Context context)throws NameNotFoundException{
		int verCode = -1;
		try{
			verCode = context.getPackageManager().getPackageInfo(
					context.getResources().getText(R.string.app_name).toString(),0).versionCode;	
		}catch(Exception e){
			Log.e("CurrentVersion", e.getMessage());
		}
		return verCode;
	}
	
	//获得当前版本名称
	public static String getCurrentVersionName(Context context){
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
	public static String getServerVersionJSON() throws Exception{
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
