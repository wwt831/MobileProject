package com.oop.weather;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.content.Context;
import com.oop.smining.R;
import com.oop.utils.Constant;
import com.oop.utils.Utils;
 
public class WeatherUtil {

	public static void setDefaultCity(Context context, String city) {
		OutputStream out = null;
		try {
			out = context.openFileOutput("weather.cfg", Context.MODE_PRIVATE);
			Properties properties = new Properties();
			properties.setProperty("city", Utils.encodeUTF8(city));
			properties.store(out, "");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String getDefaultCity(Context context) {
		InputStream in;
		try {
			in = context.openFileInput("weather.cfg");
		} catch (FileNotFoundException e1) {
			return Utils.encodeUTF8(context.getString(R.string.default_city));
		}
		Properties properties = new Properties();
		try {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (String) properties.get("city");
	}
	
	 public static BaseWeather getWeatherByCity(String city) throws ParserConfigurationException, SAXException, IOException {
	        
         SAXParserFactory spf = SAXParserFactory.newInstance();
         SAXParser sp = spf.newSAXParser();
         XMLReader reader = sp.getXMLReader();
         XmlParse  handler = new XmlParse();
         reader.setContentHandler(handler);            
         URL url = new URL(Constant.GOOGLE_WEATHER_URL_CN+city);
         InputStream is = url.openStream();
         InputStreamReader isr = new InputStreamReader(is,"GB2312");
         InputSource source = new InputSource(isr);
         reader.parse(source);
         return handler.getBaseWeather();
  }
}
