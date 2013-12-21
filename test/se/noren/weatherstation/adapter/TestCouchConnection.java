package se.noren.weatherstation.adapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import se.noren.weatherstation.adapter.bean.CouchDBTemperatureDatabaseBean;
import se.noren.weatherstation.model.TemperatureReading;

public class TestCouchConnection {
	

	private static final String COUCHDB_SERVER = "http://localhost:5984";

	@Before
	public void setup() {
		CouchDBAdapter adapter = new CouchDBAdapter();
		String databases = adapter.get(COUCHDB_SERVER + "/_all_dbs", String.class, new HashMap<String, String>());
		if (databases.indexOf("testdb") != -1) {
			adapter.delete(COUCHDB_SERVER + "/testdb");
		}
	}
	
	@Test
	public void testConnection() {
		CouchDBAdapter adapter = new CouchDBAdapter();
		
		String string = adapter.get(COUCHDB_SERVER, String.class, new HashMap<String, String>());
		assertEquals(string, "{\"couchdb\":\"Welcome\",\"version\":\"1.2.0\"}\n");
		
	}

	@Test
	public void testPostTempReading() {
		CouchDBAdapter adapter = new CouchDBAdapter();
		
		/*
		 * Create DB
		 */
		adapter.put(COUCHDB_SERVER + "/testdb"); 
		
		/*
		 * Post a reading
		 */
		TemperatureReading reading = new TemperatureReading(34.6, new Date().getTime(), "test");
		String string = adapter.post(COUCHDB_SERVER + "/testdb", reading);
		
		String docs = adapter.get(COUCHDB_SERVER + "/testdb/_all_docs?include_docs=true", String.class, new HashMap<String, String>());
		
		assertNotEquals(docs.indexOf("rawDate"), -1);
		
	}

	@Test
	public void testMockedJacksonDeserialization1() throws JsonParseException, JsonMappingException, IOException {
		
		String docs = "{\"total_rows\":1,\"offset\":0,\"rows\":[]}";
		
		ObjectMapper mapper = new ObjectMapper();
		CouchDBTemperatureDatabaseBean database = mapper.readValue(docs, CouchDBTemperatureDatabaseBean.class);
		
		assertNotNull(database);
		
	}

	@Test
	public void testMockedJacksonDeserialization2() throws JsonParseException, JsonMappingException, IOException {
		
		String docs = "{\"total_rows\":1,\"offset\":0,\"rows\":[{\"id\":\"1d67133fad001d90126a9d32fd00327e\",\"key\":\"1d67133fad001d90126a9d32fd00327e\",\"value\":{\"rev\":\"1-cfeab093846278c4728ebebba4ad3588\"},\"doc\":{\"_id\":\"1d67133fad001d90126a9d32fd00327e\",\"_rev\":\"1-cfeab093846278c4728ebebba4ad3588\",\"temperature\":34.600000000000001421,\"rawDate\":1387550192894,\"key\":\"test\"}}]}";
		
		ObjectMapper mapper = new ObjectMapper();
		CouchDBTemperatureDatabaseBean database = mapper.readValue(docs, CouchDBTemperatureDatabaseBean.class);
		
		assertNotNull(database);
		
	}

	
	@Test
	public void testJacksonDeserialization() throws JsonParseException, JsonMappingException, IOException {
		CouchDBAdapter adapter = new CouchDBAdapter();
		
		/*
		 * Create DB
		 */
		adapter.put(COUCHDB_SERVER + "/testdb"); 
		
		/*
		 * Post a reading
		 */
		TemperatureReading reading = new TemperatureReading(34.6, new Date().getTime(), "test");
		String string = adapter.post(COUCHDB_SERVER + "/testdb", reading);
		
		String docs = adapter.get(COUCHDB_SERVER + "/testdb/_all_docs?include_docs=true", String.class, new HashMap<String, String>());
		
		ObjectMapper mapper = new ObjectMapper();
		CouchDBTemperatureDatabaseBean database = mapper.readValue(docs, CouchDBTemperatureDatabaseBean.class);
		
		
		assertNotEquals(docs.indexOf("rawDate"), -1);
		
	}

	

}
