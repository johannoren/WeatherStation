package se.noren.weatherstation.model;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class TestAddTemperatureReadings extends BaseTest {
	
	private static final String APPKEY = "testkey";

	@Test
	public void testAddingTwoMonthsOfReadings() {
		long period = 1000L * 60 * 60 * 24; // 24 h
		long now = new Date().getTime();
		long twoMonthsAgo = now - 1000L * 60 * 60 * 24 * 30 * 2;
		long interval = 1000 * 60 * 5; // 5 min
		long time = twoMonthsAgo;
		DateFormat df = DateFormat.getDateInstance();
		Random random = new Random();
		 
		while (time < now) {
			double temp = 10.0 + Math.sin(time * 2.0 * Math.PI / period) * 2.5 + random.nextDouble() * 1;
			System.out.println("Posting for time " + df.format(new Date(time)) + "\t temp: " + temp);
			postReading(String.valueOf(temp), String.valueOf(time));
			
			time += interval;
		}
		
		
		//assertEquals(s, null);
	}

	private void postReading(String temp, String time) {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("temperature", temp);
		map.add("time", time);
		
		post("/api/temperature/" + APPKEY + "/test", map);
	}
}
