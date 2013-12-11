package se.noren.weatherstation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import se.noren.weatherstation.bean.TemperatureBean;
import se.noren.weatherstation.bean.TemperatureInfo;
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

	private static final long TIME_BETWEEN_DATAPOINTS_HOURLY = 1000 * 60 * 59;
	private static final long TIME_BETWEEN_DATAPOINTS_FIVE_HOURS = 1000 * 60 * 60 * 5;

	@Override
	public TemperatureBean getTemperatureReadings(String key) {

		ArrayList<TemperatureReading> list = readTemperatureDataFromDatastore(key);

		TemperatureReading currentReading = list.get(list.size() - 1);
		
		TemperaturePeriodBean todaysTemp = createTemperaturePeriodBean(list, TIME_BETWEEN_DATAPOINTS_HOURLY, 24L * 60 * 60 * 1000);
		TemperaturePeriodBean weeksTemp = createTemperaturePeriodBean(list, TIME_BETWEEN_DATAPOINTS_FIVE_HOURS, 24L * 60 * 60 * 1000 * 7);
		TemperaturePeriodBean monthsTemp = createTemperaturePeriodBean(list, TIME_BETWEEN_DATAPOINTS_FIVE_HOURS, 24L * 60 * 60 * 1000 * 30);
		
		TemperatureBean temperatureBean = new TemperatureBean(FormatUtil.round(currentReading.getTemperature(), 1), 
															  currentReading.getRawDate(), 
															  todaysTemp, weeksTemp, monthsTemp);
		
		return temperatureBean;
	}

	private TemperaturePeriodBean createTemperaturePeriodBean(ArrayList<TemperatureReading> list, long minTimeBetweenDataPoints, long oldestData) {
		double coldest = Double.MAX_VALUE;
		double hottest = -Double.MAX_VALUE;
		List<TemperatureInfo> infoList = new ArrayList<TemperatureInfo>();
		
		long lastEntry = 0;
		long now = new Date().getTime();
		
		for (TemperatureReading t : list) {
			if (t.getRawDate() + oldestData > now) {
				if (t.getTemperature() < coldest) {
					coldest = t.getTemperature();
				}
	
				if (t.getTemperature() > hottest) {
					hottest = t.getTemperature();
				}
				
				if (t.getRawDate() - lastEntry > minTimeBetweenDataPoints) {
					lastEntry = t.getRawDate();
					infoList.add(new TemperatureInfo(t.getTemperature(), t.getRawDate()));
				}
			}
		}

		TemperaturePeriodBean tempBean = new TemperaturePeriodBean(FormatUtil.round(coldest, 1), FormatUtil.round(hottest, 1), infoList);
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
