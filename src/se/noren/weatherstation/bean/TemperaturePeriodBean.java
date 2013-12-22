package se.noren.weatherstation.bean;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class TemperaturePeriodBean {

	private TemperatureInfoFormatted lowestTemperature;
	private TemperatureInfoFormatted highestTemperature;
	private String averageTemperature;
	private long periodFromDate;
	private long periodToDate;
	private List<TemperatureInfo> temperatureInfo;
	
	public TemperaturePeriodBean(TemperatureInfoFormatted lowestTemperature, TemperatureInfoFormatted highestTemperature, String averageTemperature,
			List<TemperatureInfo> temperatureInfo, long periodFromDate, long periodToDate) {
		super();
		this.highestTemperature = highestTemperature;
		this.lowestTemperature = lowestTemperature;
		this.averageTemperature = averageTemperature;
		this.temperatureInfo = temperatureInfo;
		this.periodFromDate = periodFromDate;
		this.periodToDate = periodToDate;
	}
	
	public long getPeriodFromDate() {
		return periodFromDate;
	}

	public void setPeriodFromDate(long periodFromDate) {
		this.periodFromDate = periodFromDate;
	}

	public long getPeriodToDate() {
		return periodToDate;
	}

	public void setPeriodToDate(long periodToDate) {
		this.periodToDate = periodToDate;
	}

	public TemperatureInfoFormatted getLowestTemperature() {
		return lowestTemperature;
	}

	public void setLowestTemperature(TemperatureInfoFormatted lowestTemperature) {
		this.lowestTemperature = lowestTemperature;
	}

	public TemperatureInfoFormatted getHighestTemperature() {
		return highestTemperature;
	}

	public void setHighestTemperature(TemperatureInfoFormatted highestTemperature) {
		this.highestTemperature = highestTemperature;
	}

	public List<TemperatureInfo> getTemperatureInfo() {
		return temperatureInfo;
	}
	public void setTemperatureInfo(List<TemperatureInfo> temperatureInfo) {
		this.temperatureInfo = temperatureInfo;
	}
	public String getAverageTemperature() {
		return averageTemperature;
	}

	public void setAverageTemperature(String averageTemperature) {
		this.averageTemperature = averageTemperature;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
