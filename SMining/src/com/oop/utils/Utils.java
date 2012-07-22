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

	//��õ�ǰ����ʱ��
	public static String getCurrentlyDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(new Date());
	}
	
	//������������
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

	//�ж��Ƿ�������
	public static boolean isNight() {
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		if (hour >= 20 || hour <= 7) {
			return true;
		}
		return false;
	}
	
	//��������Ƿ����
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
	
	//����ת��UTF8
	public static String encodeUTF8(String str) {
		try {
			str = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	//���÷�������ַ
	public static void saveServerIP(Context context, String serverIP) {
		saveProperties(context, "ServerIP", serverIP);
	}
	
	//��ȡ��������ַ
	public static String getServerIP(Context context) {
		return getProperties(context, "ServerIP");
	}

	//��ȡ�������˿�
	public static String getServerPort(Context context) {
		return context.getString(R.string.serverport);
	}

	// ��ȡ���ʹ�õ��û���
	public static String getSavedUserName(Context context) {
		return getProperties(context, "UserName");
	}

	//����ʹ�õ��û���
	public static void saveUserName(Context context, String userName) {
		saveProperties(context, "UserName", userName);
	}
	
	//��������Ϣ���浽Properties�ļ���
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
	
	//��������Ϣ��Properties�ļ��ж�����
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
	
	//�����û�����
	public static void saveUserPassword(Context context, String userName, String password) {
		OutputStream out = null;
		try {
			out = context.openFileOutput("smining.pass", Context.MODE_PRIVATE);
			Properties properties = new Properties();
			properties.setProperty(userName, encodeBase64(password));
			properties.store(out, "");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String encodeBase64(String password) {
		// TODO Auto-generated method stub
		return password;
	}

	// ��ȡ������û�����
	public static String getSavedPassword(Context context, String userName) {
		InputStream in;
		try {
			in = context.openFileInput("smining.pass");
		} catch (FileNotFoundException e1) {
			return null;
		}
		Properties properties = new Properties();
		try {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String code = (String) properties.get(userName);
		if(code.length()>0) 			
			return decodeBase64(code);
		else
			return code;
	}
	
	private static String decodeBase64(String code) {
		// TODO Auto-generated method stub
		return code;
	}

	//��õ�ǰ�汾����
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
	
	//��õ�ǰ�汾����
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
	
	//��÷������˵�Ӧ�ó���汾������
	//JSON��ʽ�������SMining���ݴ���淶��
	public static String getServerVersionJSON() throws Exception{
		//TODO:�����������ַ������
		String serverIP = "";
		int serverPort = 0;
		String serverPath = "";
		
		StringBuilder newVerJSON = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpParams httpParams = client.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 3000);//�������ӳ�ʱ��Χ
		HttpConnectionParams.setSoTimeout(httpParams, 5000);
		
		//serverPath��version.json��·��
		HttpResponse response = client.execute(new HttpGet(serverIP + ":" + serverPort + "/" + serverPath));
		HttpEntity entity = response.getEntity();
		if(entity != null){
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(entity.getContent(),"UTF-8"),8192);
			String line = null;
			while((line = reader.readLine()) != null){
				newVerJSON.append(line+"\n");//���ж�ȡ����StringBuilder��
			}
			reader.close();
		}
		return newVerJSON.toString();
	}

	public static boolean isValidServerIP(String serverIP) {
		// TODO Auto-generated method stub
		if(serverIP.length()>6)
			return true;
		else
			return false;
	}


}
