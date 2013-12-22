package se.noren.weatherstation;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import se.noren.weatherstation.util.TestDateConverter;


	@RunWith(value = Suite.class)
	@SuiteClasses(value = { 
		TestDateConverter.class 
	})
	public class WeatherStationUnitTestSuite {}
