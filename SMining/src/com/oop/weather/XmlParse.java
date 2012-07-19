package com.oop.weather;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.oop.utils.Constant;
import com.oop.utils.Utils;

public class XmlParse extends  DefaultHandler {
    private boolean inForcast;
    private boolean information;
    private BaseWeather baseWeather ;
    private Weather weather;
    private String dateTime;
    public XmlParse(){
    	information=false;
    	inForcast=false;
    	baseWeather = new BaseWeather();
    	ArrayList<Weather> weatherList=new ArrayList<Weather>();
    	baseWeather.setWeatherList(weatherList);
    }
	public BaseWeather getBaseWeather() {
		return baseWeather;
	}

    @Override
	public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException{
        
        String tagName = localName.length() != 0 ? localName : qName;
        tagName = tagName.toLowerCase();
		 if(tagName.equals(Constant.ERROR_PATH)) {
			 throw new IllegalArgumentException("the city is not existed!");
	       }
        if(tagName.equals(Constant.INFORMATION_PATH)) {
        	information=true;
        }
        if(information){
        	 if(tagName.equals(Constant.CITY_NAME_PATH)){
        		 baseWeather.setCityName(attributes.getValue(Constant.ATTRIBUTE_NAME));
        	 }
        	 if(tagName.equals(Constant.FIRST_DATE_PATH)){
        		 String[] date = attributes.getValue(Constant.ATTRIBUTE_NAME).split("-");
        		 int day = Integer.valueOf(date[2]);
        		 day--;
        		 date[2]=String.valueOf(day);
        		 this.dateTime=new StringBuilder().append(date[0]).append("-").append(date[1]).append("-").append(date[2]).toString();
        	 }
        }
        if(tagName.equals(Constant.FORECAST_CONDITION)) {
        	weather=new Weather();
            inForcast = true;
        }
        
        if(inForcast) {
            if(tagName.equals(Constant.DAY_WEEK_PATH)) {                
            	weather.setCurrently_week(attributes.getValue(Constant.ATTRIBUTE_NAME));
            }else if(tagName.equals(Constant.TEMPERATURE_MIN_PATH)) {
            	weather.setTemperature_min(attributes.getValue(Constant.ATTRIBUTE_NAME));
            }else if(tagName.equals(Constant.TEMPERATURE_MAX_PATH)) {
            	weather.setTemperature_max(attributes.getValue(Constant.ATTRIBUTE_NAME));
            }else if(tagName.equals(Constant.CONDITION_PATH)) {
            	weather.setCurrently_hint(attributes.getValue(Constant.ATTRIBUTE_NAME));
            }
        }
        
    }
    
    @Override
	public void endElement(String uri, String localName, String qName)
            throws SAXException {

        String tagName = localName.length() != 0 ? localName : qName;
        tagName = tagName.toLowerCase();
        
        if(tagName.equals(Constant.FORECAST_CONDITION)) {
            inForcast = false;
            weather.setCurrently_date(Utils.getNextDate(dateTime));
            dateTime=weather.getCurrently_date();
            baseWeather.getWeatherList().add(weather);
        }
    }
}
