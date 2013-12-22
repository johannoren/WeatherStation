package se.noren.weatherstation;

import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import se.noren.weatherstation.bean.TemperatureBean;
import se.noren.weatherstation.model.TemperatureReading;
import se.noren.weatherstation.util.DateConverter;

@Controller
@RequestMapping("/temperature")
public class WeatherStationController {
	private static final long serialVersionUID = 1L;

	@Autowired
	WeatherStationService weatherStationService;

	/**
	 * @return Fetch temperature data from database for the specific API key and the specified day.
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/{key}", method = RequestMethod.GET)
	public ModelAndView getTemperature(@PathVariable String key, @RequestParam String date) throws ParseException {
		if (date == null) {
			date = new DateConverter().yyyyMMddFromDate(new Date());
		}
		TemperatureBean temperatureBean = weatherStationService.getTemperatureReadings(key, date);
		return new ModelAndView("weatherStationView", "temperatureInfo", temperatureBean);
	}

	/**
	 * Add a new temperature reading to database
	 * @param key API key
	 * @param temperature New temperature value
	 * @return
	 */
	@RequestMapping(value = "/{key}", method = RequestMethod.POST)
	public void addTemperatureReading(@PathVariable String key,	@RequestParam String temperature, HttpServletResponse response) {
		long rawDate = new Date().getTime();
		TemperatureReading temperatureReading = new TemperatureReading(Double.valueOf(temperature), rawDate, key);
		weatherStationService.addTemperatureReading(temperatureReading);
	}
	
	/**
	 * Add a new temperature reading to database
	 * @param key API key
	 * @param time Time of reading
	 * @param temperature New temperature value
	 * @return
	 */
	@RequestMapping(value = "/{key}/test", method = RequestMethod.POST)
	public void addTemperatureReading(@PathVariable String key,	@RequestParam String temperature, @RequestParam long time, HttpServletResponse response) {
		TemperatureReading temperatureReading = new TemperatureReading(Double.valueOf(temperature), time, key);
		weatherStationService.addTemperatureReading(temperatureReading);
	}

}