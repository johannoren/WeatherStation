package se.noren.weatherstation.bean;


public class TemperatureBean {

	private TemperatureInfoFormatted currentTemp;
	private boolean remoteSensorActive;
	private String upOrDownTime;
	private TemperaturePeriodBean todaysTemperature;
	private TemperaturePeriodBean weeksTemperature;
	private TemperaturePeriodBean monthsTemperature;
	
	
	
	public TemperatureBean(TemperatureInfoFormatted currentTemp, 
			boolean remoteSensorActive,
			String upOrDownTime,
			TemperaturePeriodBean todaysTemperature,
			TemperaturePeriodBean weeksTemperature,
			TemperaturePeriodBean monthsTemperature) {
		super();
		this.currentTemp = currentTemp;
		this.remoteSensorActive = remoteSensorActive;
		this.upOrDownTime = upOrDownTime;
		this.todaysTemperature = todaysTemperature;
		this.weeksTemperature = weeksTemperature;
		this.monthsTemperature = monthsTemperature;
	}
	
	public TemperatureInfoFormatted getCurrentTemp() {
		return currentTemp;
	}
	public void setCurrentTemp(TemperatureInfoFormatted currentTemp) {
		this.currentTemp = currentTemp;
	}
	public TemperaturePeriodBean getTodaysTemperature() {
		return todaysTemperature;
	}
	public void setTodaysTemperature(TemperaturePeriodBean todaysTemperature) {
		this.todaysTemperature = todaysTemperature;
	}
	public TemperaturePeriodBean getWeeksTemperature() {
		return weeksTemperature;
	}
	public void setWeeksTemperature(TemperaturePeriodBean weeksTemperature) {
		this.weeksTemperature = weeksTemperature;
	}
	public TemperaturePeriodBean getMonthsTemperature() {
		return monthsTemperature;
	}
	public void setMonthsTemperature(TemperaturePeriodBean monthsTemperature) {
		this.monthsTemperature = monthsTemperature;
	}
	public boolean isRemoteSensorActive() {
		return remoteSensorActive;
	}
	public void setRemoteSensorActive(boolean remoteSensorActive) {
		this.remoteSensorActive = remoteSensorActive;
	}

	public String getUpOrDownTime() {
		return upOrDownTime;
	}

	public void setUpOrDownTime(String upOrDownTime) {
		this.upOrDownTime = upOrDownTime;
	}
	
}
