package se.noren.weatherstation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import se.noren.weatherstation.adapter.CouchDBAdapter;
import se.noren.weatherstation.adapter.bean.CouchDBTemperatureDatabaseBean;
import se.noren.weatherstation.adapter.bean.CouchDBTemperatureDatabaseBean.DBRow;
import se.noren.weatherstation.adapter.bean.CouchDBTemperatureDatabaseBean.DBRow.DBDoc;
import se.noren.weatherstation.bean.TemperatureBean;
import se.noren.weatherstation.bean.TemperatureInfo;
import se.noren.weatherstation.bean.TemperatureInfoFormatted;
import se.noren.weatherstation.bean.TemperaturePeriodBean;
import se.noren.weatherstation.model.TemperatureReading;
import se.noren.weatherstation.util.DateConverter;

@Service
public class WeatherStationServiceImpl implements WeatherStationService {

//	private static final String COUCHDB_SERVER = "http://localhost:5984";  // Proxy mode
	private static final String COUCHDB_SERVER = "http://johanhtpc:5984";
	
	private static final long DEFINITION_OF_INACTIVE_SENSOR= 1000 * 60 * 15;
	
	
	private static final long TIME_BETWEEN_DATAPOINTS_HOURLY = 1000 * 60 * 59;
	private static final long TIME_BETWEEN_DATAPOINTS_FIVE_HOURS = 1000 * 60 * 60 * 5;

	@Override
	public TemperatureBean getTemperatureReadings(String key, String date) throws ParseException {
		/*
		 * Read from database
		 */
		List<TemperatureReading> unfilteredList = readTemperatureDataFromDatastore(key);
		Collections.sort(unfilteredList);
		TemperatureReading currentReading = null;
		
		if (unfilteredList.size() != 0) {
			currentReading = unfilteredList.get(unfilteredList.size() - 1);
		} else {
			currentReading = new TemperatureReading(0, 0, "");
		}

		DateConverter converter = new DateConverter();
		String lastDayReading = converter.yyyyMMddFromDate(new Date(currentReading.getRawDate()));
		
		/*
		 * Handle that sensor has been down since the day we are askin for
		 */
		if (lastDayReading.compareTo(date) < 0) {
			date = lastDayReading;
		}
		
		Date currentDate = converter.dateFromYYYYMMDD(date);
		Date furthestTimePossible = new Date(Math.min(DateUtils.addDays(currentDate, 1).getTime(), new Date().getTime()));
		
		
		/*
		 * TODO: This filtering could be moved to database!
		 */
		List<TemperatureReading> list = filterReadingsForTimeInterval(unfilteredList, new Date(0), furthestTimePossible);
		
		boolean hasRegisteredReadings = list.size() > 0; 


		
		TemperaturePeriodBean currentDaysTemp = createTemperaturePeriodBean(list, TIME_BETWEEN_DATAPOINTS_HOURLY, 24L * 60 * 60 * 1000, furthestTimePossible);
		TemperaturePeriodBean weeksTemp = createTemperaturePeriodBean(list, TIME_BETWEEN_DATAPOINTS_FIVE_HOURS, 24L * 60 * 60 * 1000 * 7, new Date());
		TemperaturePeriodBean monthsTemp = createTemperaturePeriodBean(list, TIME_BETWEEN_DATAPOINTS_FIVE_HOURS, 24L * 60 * 60 * 1000 * 30, new Date());
		
		long now = new Date().getTime();
		
		/*
		 * Are we looking for another day than the current day?
		 */
//		if (!new DateConverter().yyyyMMddFromDate(new Date()).equals(date)) {
//			now = currentDate.getTime();
//		}
		
		long timeSinceLastReading = 0;
		if (unfilteredList.size() != 0) {
			timeSinceLastReading = now - currentReading.getRawDate();
		}
		boolean sensorActive = timeSinceLastReading < DEFINITION_OF_INACTIVE_SENSOR;
		
		long onlineOfflineTime = sensorActive ? findActiveTimePeriod(unfilteredList, now) : timeSinceLastReading;
		
		TemperatureBean temperatureBean = new TemperatureBean(hasRegisteredReadings, new TemperatureInfoFormatted(FormatUtil.round(currentReading.getTemperature(), 1), 
															  								currentReading.getRawDate()),
															  sensorActive,
															  String.valueOf(onlineOfflineTime), 
															  currentDaysTemp, weeksTemp, monthsTemp);
		
		return temperatureBean;
	}
	
	private List<TemperatureReading> filterReadingsForTimeInterval(
			List<TemperatureReading> list, Date from, Date to) {
		ArrayList<TemperatureReading> filteredList = new ArrayList<TemperatureReading>();
		
		long fromTime = from.getTime();
		long toTime = to.getTime();
		
		for (TemperatureReading t : list) {
			if (t.getRawDate() >= fromTime && t.getRawDate() <= toTime) {
				filteredList.add(t);
			} else {
				System.out.println("skipping " + t);
			}
		}
		
		return filteredList;
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

	private TemperaturePeriodBean createTemperaturePeriodBean(List<TemperatureReading> list, long minTimeBetweenDataPoints, long backtrackingTime, Date toDate) {
		TemperatureInfo coldest = new TemperatureInfo(Double.MAX_VALUE, 0L);
		TemperatureInfo hottest = new TemperatureInfo(-Double.MAX_VALUE, 0L);
		List<TemperatureInfo> infoList = new ArrayList<TemperatureInfo>();
		
		long lastEntry = 0;
		long toTime = toDate.getTime();
		double sum = 0;
		int count = 0;
		long earliest = Long.MAX_VALUE;
		long latest = Long.MIN_VALUE;
		
		for (TemperatureReading t : list) {
			if (t.getRawDate() + backtrackingTime > toTime) {
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
				
				if (t.getRawDate() < earliest) {
					earliest = t.getRawDate();
				}

				if (t.getRawDate() > latest) {
					latest = t.getRawDate();
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
																	FormatUtil.round(average, 1), infoList, earliest, latest);
		
		return tempBean;
	}

	private ArrayList<TemperatureReading> readTemperatureDataFromDatastore(String key) {
		ArrayList<TemperatureReading> list = new ArrayList<TemperatureReading>();
	
		CouchDBAdapter adapter = new CouchDBAdapter();
		CouchDBTemperatureDatabaseBean databaseBean = adapter.get(COUCHDB_SERVER + "/" + key + "/_all_docs?include_docs=true", CouchDBTemperatureDatabaseBean.class, new HashMap<String, String>());
		for (DBRow dbRow : databaseBean.getRows()) {
			DBDoc doc = dbRow.getDoc();
			TemperatureReading reading = new TemperatureReading(doc.getTemperature(), doc.getRawDate(), doc.getKey());
			list.add(reading);
		}

		return list;
	}

	@Override
	public void addTemperatureReading(TemperatureReading reading) {
		CouchDBAdapter adapter = new CouchDBAdapter();
		adapter.post(COUCHDB_SERVER + "/" + reading.getKey(), reading);
	}

}
