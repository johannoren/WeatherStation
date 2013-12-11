package se.noren.weatherstation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import se.noren.weatherstation.bean.TemperatureBean;
import se.noren.weatherstation.bean.TemperatureInfo;
import se.noren.weatherstation.model.TemperatureReading;

@Controller
@RequestMapping("/temperature")
public class WeatherStationController {
	private static final long serialVersionUID = 1L;

	@Autowired
	WeatherStationService weatherStationService;

	/**
	 * @return Fetch temperature data from database for the specific API key.
	 */
	@RequestMapping(value = "/{key}", method = RequestMethod.GET)
	public ModelAndView getTemperature(@PathVariable String key) {
		TemperatureBean temperatureBean = weatherStationService.getTemperatureReadings(key);
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
}