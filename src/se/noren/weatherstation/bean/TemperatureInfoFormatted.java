package se.noren.weatherstation.bean;

import org.apache.commons.lang3.builder.ToStringBuilder;

import se.noren.weatherstation.FormatUtil;

public class TemperatureInfoFormatted {

	private String temp;
	private long time;
	
	public TemperatureInfoFormatted(String temp, long time) {
		super();
		this.temp = temp;
		this.time = time;
	}

	public TemperatureInfoFormatted(TemperatureInfo info) {
		super();
		this.temp = FormatUtil.round(info.getTemp(), 1);
		this.time = info.getTime();
	}

	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
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
