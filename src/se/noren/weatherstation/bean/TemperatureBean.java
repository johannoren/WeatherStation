package se.noren.weatherstation.bean;


public class TemperatureBean {

	private String currentTemp;
	private long currentTime;
	private TemperaturePeriodBean todaysTemperature;
	private TemperaturePeriodBean weeksTemperature;
	private TemperaturePeriodBean monthsTemperature;
	
	
	
	public TemperatureBean(String currentTemp, long currentTime,
			TemperaturePeriodBean todaysTemperature,
			TemperaturePeriodBean weeksTemperature,
			TemperaturePeriodBean monthsTemperature) {
		super();
		this.currentTemp = currentTemp;
		this.currentTime = currentTime;
		this.todaysTemperature = todaysTemperature;
		this.weeksTemperature = weeksTemperature;
		this.monthsTemperature = monthsTemperature;
	}
	
	public String getCurrentTemp() {
		return currentTemp;
	}
	public void setCurrentTemp(String currentTemp) {
		this.currentTemp = currentTemp;
	}
	public long getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(long currentTime) {
		this.currentTime = currentTime;
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
	
}
