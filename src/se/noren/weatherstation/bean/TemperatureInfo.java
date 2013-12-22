package se.noren.weatherstation.bean;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class TemperatureInfo {

	private double temp;
	private long time;
	
	public TemperatureInfo(double temp, long time) {
		super();
		this.temp = temp;
		this.time = time;
	}
	
	public double getTemp() {
		return temp;
	}
	public void setTemp(double temp) {
		this.temp = temp;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
