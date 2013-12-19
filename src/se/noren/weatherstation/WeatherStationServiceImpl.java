package se.noren.weatherstation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import se.noren.weatherstation.bean.TemperatureBean;
import se.noren.weatherstation.bean.TemperatureInfo;
import se.noren.weatherstation.bean.TemperatureInfoFormatted;
import se.noren.weatherstation.bean.TemperaturePeriodBean;
import se.noren.weatherstation.model.TemperatureReading;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

@Service
public class WeatherStationServiceImpl implements WeatherStationService {

	private static final long DEFINITION_OF_INACTIVE_SENSOR= 1000 * 60 * 15;
	
	
	private static final long TIME_BETWEEN_DATAPOINTS_HOURLY = 1000 * 60 * 59;
	private static final long TIME_BETWEEN_DATAPOINTS_FIVE_HOURS = 1000 * 60 * 60 * 5;

	@Override
	public TemperatureBean getTemperatureReadings(String key) {

		List<TemperatureReading> list = readTemperatureDataFromDatastore(key);

		TemperatureReading currentReading = list.get(list.size() - 1);
		
		TemperaturePeriodBean todaysTemp = createTemperaturePeriodBean(list, TIME_BETWEEN_DATAPOINTS_HOURLY, 24L * 60 * 60 * 1000);
		TemperaturePeriodBean weeksTemp = createTemperaturePeriodBean(list, TIME_BETWEEN_DATAPOINTS_FIVE_HOURS, 24L * 60 * 60 * 1000 * 7);
		TemperaturePeriodBean monthsTemp = createTemperaturePeriodBean(list, TIME_BETWEEN_DATAPOINTS_FIVE_HOURS, 24L * 60 * 60 * 1000 * 30);
		
		long now = new Date().getTime();
		long timeSinceLastReading = now - currentReading.getRawDate();
		boolean sensorActive = timeSinceLastReading < DEFINITION_OF_INACTIVE_SENSOR;
		
		long onlineOfflineTime = sensorActive ? findActiveTimePeriod(list, now) : timeSinceLastReading;
		
		TemperatureBean temperatureBean = new TemperatureBean(new TemperatureInfoFormatted(FormatUtil.round(currentReading.getTemperature(), 1), 
															  								currentReading.getRawDate()),
															  sensorActive,
															  String.valueOf(onlineOfflineTime), 
															  todaysTemp, weeksTemp, monthsTemp);
		
		return temperatureBean;
	}
	
	private long findActiveTimePeriod(List<TemperatureReading> list, long now) {
		long t = now;
		for (int i = list.size() - 1; i >= 0; i--) {
			TemperatureReading reading = list.get(i);
			if (t - reading.getRawDate() > DEFINITION_OF_INACTIVE_SENSOR) {
				break;
			}
			t = reading.getRawDate();
		} 
		
		return now - t;
	}

	private TemperaturePeriodBean createTemperaturePeriodBean(List<TemperatureReading> list, long minTimeBetweenDataPoints, long oldestData) {
		TemperatureInfo coldest = new TemperatureInfo(Double.MAX_VALUE, 0L);
		TemperatureInfo hottest = new TemperatureInfo(-Double.MAX_VALUE, 0L);
		List<TemperatureInfo> infoList = new ArrayList<TemperatureInfo>();
		
		long lastEntry = 0;
		long now = new Date().getTime();
		double sum = 0;
		int count = 0;
		
		for (TemperatureReading t : list) {
			if (t.getRawDate() + oldestData > now) {
				if (t.getTemperature() < coldest.getTemp()) {
					coldest = new TemperatureInfo(t.getTemperature(), t.getRawDate());
				}
	
				if (t.getTemperature() > hottest.getTemp()) {
					hottest = new TemperatureInfo(t.getTemperature(), t.getRawDate());
				}
				
				sum += t.getTemperature();
				count++;
				
				if (t.getRawDate() - lastEntry > minTimeBetweenDataPoints) {
					lastEntry = t.getRawDate();
					infoList.add(new TemperatureInfo(t.getTemperature(), t.getRawDate()));
				}
			}
		}
		
		/*
		 * Always have the last entry in diagram 
		 */
		if (list.size() > 0 && list.get(list.size() - 1).getRawDate() != lastEntry) {
			TemperatureReading t = list.get(list.size() - 1);
			infoList.add(new TemperatureInfo(t.getTemperature(), t.getRawDate()));
		}

		double average = sum / count;
		
		TemperaturePeriodBean tempBean = new TemperaturePeriodBean(new TemperatureInfoFormatted(coldest), new TemperatureInfoFormatted(hottest), 
																	FormatUtil.round(average, 1), infoList);
		
		return tempBean;
	}

	private ArrayList<TemperatureReading> readTemperatureDataFromDatastore(String key) {
		ArrayList<TemperatureReading> list = new ArrayList<TemperatureReading>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		// The Query interface assembles a query
		Query q = new Query("TemperatureReading");
		q.addFilter("key", Query.FilterOperator.EQUAL, key);
		q.addSort("rawDate", SortDirection.ASCENDING);

		// PreparedQuery contains the methods for fetching query results
		// from the datastore
		PreparedQuery pq = datastore.prepare(q);

		for (Entity result : pq.asIterable()) {
			double temp = (Double) result.getProperty("temperature");
			long rawDate = (Long) result.getProperty("rawDate");
			list.add(new TemperatureReading(temp, rawDate, key));
		}
		return list;
	}

	@Override
	public void addTemperatureReading(TemperatureReading reading) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Entity hs = new Entity("TemperatureReading");
		hs.setProperty("temperature", reading.getTemperature());
		hs.setProperty("rawDate", reading.getRawDate());
		hs.setProperty("key", reading.getKey());
		datastore.put(hs);
	}

}
