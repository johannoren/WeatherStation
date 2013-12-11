package se.noren.weatherstation;

import se.noren.weatherstation.bean.TemperatureBean;
import se.noren.weatherstation.model.TemperatureReading;

public interface WeatherStationService {

	public TemperatureBean getTemperatureReadings(String key);
	
	public void addTemperatureReading(TemperatureReading reading);
}
