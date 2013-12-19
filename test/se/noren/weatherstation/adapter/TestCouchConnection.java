package se.noren.weatherstation.adapter;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class TestCouchConnection {
	

	@Test
	public void testConnection() {
		CouchDBAdapter adapter = new CouchDBAdapter();
		
		String string = adapter.get("http://johanhtpc:5984", String.class, new HashMap<String, String>());
		assertEquals(string, "{\"couchdb\":\"Welcome\",\"version\":\"1.2.0\"}\n");
		
	}

	private void postReading(String temp, String time) {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("temperature", temp);
		map.add("time", time);
		
	}
}
