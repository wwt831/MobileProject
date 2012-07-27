package com.oop.utils;

import java.util.HashMap;

public class Constant {
	/* Google天气 API地址 */
	public static final String GOOGLE_WEATHER_URL_CN = "http://www.google.com/ig/api?hl=zh-cn&weather=";
	/* 今天日期节点 */
	public static final String FIRST_DATE_PATH = "forecast_date";
	/* 最低温度节点 */
	public static final String TEMPERATURE_MIN_PATH = "low";
	/* 最高温度节点 */
	public static final String TEMPERATURE_MAX_PATH = "high";
	/* 星期节点 */
	public static final String DAY_WEEK_PATH = "day_of_week";
	/* 提示信息节点 */
	public static final String CONDITION_PATH = "condition";
	/* 错误信息节点 */
	public static final String ERROR_PATH = "problem_cause";
	/* 城市名称节点 */
	public static final String CITY_NAME_PATH = "postal_code";
	/* 节点属性名称 */
	public static final String ATTRIBUTE_NAME = "data";
	/* 信息节点 */
	public static final String INFORMATION_PATH = "forecast_information";
	/* 节点属性名称 */
	public static final String FORECAST_CONDITION = "forecast_conditions";
	/* 月份对应天数 */
	public static final HashMap<Integer, Integer> MONTH_MAP = new HashMap<Integer, Integer>(
			12);
	static {
		MONTH_MAP.put(1, 31);
		MONTH_MAP.put(2, 28);
		MONTH_MAP.put(3, 31);
		MONTH_MAP.put(4, 30);
		MONTH_MAP.put(5, 31);
		MONTH_MAP.put(6, 30);
		MONTH_MAP.put(7, 31);
		MONTH_MAP.put(8, 31);
		MONTH_MAP.put(9, 30);
		MONTH_MAP.put(10, 31);
		MONTH_MAP.put(11, 30);
		MONTH_MAP.put(12, 31);
	}

	public static final String T_SIGN = "°C";

	// 网络连接错误码
	public static final int NET_LINK_ERROR = 1;
	// 城市存不在错误码
	public static final int CITY_NOT_EXIST = 2;
	// 操作成功
	public static final int SUCCESS_FULL = 3;
}
