package se.noren.weatherstation.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class TemperatureReading implements Comparable<TemperatureReading> {

	private double temperature;
	private long rawDate;
	private String key;

	public TemperatureReading(double temperature, long rawDate, String key) {
		super();
		this.temperature = temperature;
		this.rawDate = rawDate;
		this.key = key;
	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public long getRawDate() {
		return rawDate;
	}
	public void setRawDate(long rawDate) {
		this.rawDate = rawDate;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int compareTo(TemperatureReading o) {
		long diff = this.rawDate - o.getRawDate();
		if (diff == 0) 
			return 0;
		if (diff > 0) 
			return 1;
		return -1;
	}
}
