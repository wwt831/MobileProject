package com.oop.weather;

public class Weather {
	private String currently_date;
	private String currently_week;
	private String temperature_min;
	private String temperature_max;
	private String currently_hint;

	public String getCurrently_date() {
		return currently_date;
	}

	public void setCurrently_date(String currently_date) {
		this.currently_date = currently_date;
	}

	public String getCurrently_week() {
		return currently_week;
	}

	public void setCurrently_week(String currently_week) {
		this.currently_week = currently_week;
	}

	public String getTemperature_min() {
		return temperature_min;
	}

	public void setTemperature_min(String temperature_min) {
		this.temperature_min = temperature_min;
	}

	public String getTemperature_max() {
		return temperature_max;
	}

	public void setTemperature_max(String temperature_max) {
		this.temperature_max = temperature_max;
	}

	public String getCurrently_hint() {
		return currently_hint;
	}

	public void setCurrently_hint(String currently_hint) {
		this.currently_hint = currently_hint;
	}
}
