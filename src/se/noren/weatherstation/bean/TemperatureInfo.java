package se.noren.weatherstation.bean;

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
}
