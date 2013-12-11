package se.noren.weatherstation.bean;

import java.util.List;

public class TemperaturePeriodBean {

	private String lowestTemperature;
	private String highestTemperature;
	private List<TemperatureInfo> temperatureInfo;
	
	public TemperaturePeriodBean(String lowestTemperature, String highestTemperature,
			List<TemperatureInfo> temperatureInfo) {
		super();
		this.highestTemperature = highestTemperature;
		this.lowestTemperature = lowestTemperature;
		this.temperatureInfo = temperatureInfo;
	}

	public String getLowestTemperature() {
		return lowestTemperature;
	}

	public void setLowestTemperature(String lowestTemperature) {
		this.lowestTemperature = lowestTemperature;
	}

	public String getHighestTemperature() {
		return highestTemperature;
	}

	public void setHighestTemperature(String highestTemperature) {
		this.highestTemperature = highestTemperature;
	}

	public List<TemperatureInfo> getTemperatureInfo() {
		return temperatureInfo;
	}
	public void setTemperatureInfo(List<TemperatureInfo> temperatureInfo) {
		this.temperatureInfo = temperatureInfo;
	}
	
}
