package se.noren.weatherstation.bean;

import java.util.List;

public class TemperaturePeriodBean {

	private TemperatureInfoFormatted lowestTemperature;
	private TemperatureInfoFormatted highestTemperature;
	private String averageTemperature;
	private List<TemperatureInfo> temperatureInfo;
	
	public TemperaturePeriodBean(TemperatureInfoFormatted lowestTemperature, TemperatureInfoFormatted highestTemperature, String averageTemperature,
			List<TemperatureInfo> temperatureInfo) {
		super();
		this.highestTemperature = highestTemperature;
		this.lowestTemperature = lowestTemperature;
		this.averageTemperature = averageTemperature;
		this.temperatureInfo = temperatureInfo;
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
	
}
