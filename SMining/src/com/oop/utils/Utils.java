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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import com.oop.smining.R;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.WindowManager;

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
	
	//获取屏幕宽度
	public static int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		return wm.getDefaultDisplay().getWidth();
	}
	
	//获取屏幕宽度
	public static int getScreenHeight(Context context) {
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		return wm.getDefaultDisplay().getHeight();
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
	
	//设置服务器地址
	public static void saveServerIP(Context context, String serverIP) {
		saveProperties(context, "ServerIP", serverIP);
	}
	
	//获取服务器地址
	public static String getServerIP(Context context) {
		return getProperties(context, "ServerIP");
	}

	//获取服务器端口
	public static String getServerPort(Context context) {
		return context.getString(R.string.serverport);
	}

	// 获取最近使用的用户名
	public static String getSavedUserName(Context context) {
		return getProperties(context, "UserName");
	}

	//保存使用的用户名
	public static void saveUserName(Context context, String userName) {
		saveProperties(context, "UserName", userName);
	}
	
	// 获取最近使用的密码
	public static String getSavedPassword(Context context) {
		return getProperties(context, "Password");
	}

	//保存使用的密码
	public static void savePassword(Context context, String password) {
		saveProperties(context, "Password", password);
	}
	
	public static boolean getEnabled(Context context, String function) {
		Properties properties = new Properties();
		try {
			properties.load(context.getAssets().open("deploy.prop"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(properties.get(function).equals("on"))
			return true;
		else
			return false;
	}
	
	//将配置信息保存到Properties文件中
	private static void saveProperties(Context context, String propName, String value) {
		OutputStream out = null;
		InputStream in = null;
		Properties properties = new Properties();
		
		try {
			in = context.openFileInput("smining.cfg");
			properties.load(in);
			out = context.openFileOutput("smining.cfg", Context.MODE_PRIVATE);
		} catch (FileNotFoundException e1) {
			try {
				out = context.openFileOutput("smining.cfg", Context.MODE_PRIVATE);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 

		properties.setProperty(propName, value);
		
		try {
			properties.store(out, "");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//将配置信息从Properties文件中读出来
	private static String getProperties(Context context, String propName) {
		InputStream in;
		try {
			in = context.openFileInput("smining.cfg");
		} catch (FileNotFoundException e1) {
			return "";
		}
		Properties properties = new Properties();
		try {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (String) properties.get(propName);
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

	public static boolean isValidServerIP(String serverIP) {
		Pattern pattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");

		Matcher matcher = pattern.matcher(serverIP);

		return matcher.matches();
	}

	public static JSONObject getDemoData(Context context, String type) {
		String line="",Result="";
		JSONObject jsonObj=null;
		try {
			//JSON数据必须采用 UTF-8编码
			InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(type+".json"), "UTF-8");
			BufferedReader bufReader = new BufferedReader(inputReader);
			while((line=bufReader.readLine())!=null)
				Result+=line;
			jsonObj=new JSONObject(Result);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObj;
	}
}
