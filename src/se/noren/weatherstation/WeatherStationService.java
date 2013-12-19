package se.noren.weatherstation;

import se.noren.weatherstation.bean.TemperatureBean;
import se.noren.weatherstation.model.TemperatureReading;

/**
 * A Weather Station service handles storage and analysis of temperature
 * readings. It it the layer between the persistence layer and the distribution
 * layer. 
 */
public interface WeatherStationService {

	/**
	 * Fetch temperature readings for a specific client application
	 * @param key Unique key for the client application
	 * @return A bean containing temperature information about this client
	 */
	public TemperatureBean getTemperatureReadings(String key);
	
	/**
	 * Add a temperature reading to the data model
	 * @param reading Details about this reading
	 */
	public void addTemperatureReading(TemperatureReading reading);
}
