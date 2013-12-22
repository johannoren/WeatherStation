package se.noren.weatherstation.util;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

public class TestDateConverter {
	
	@Test
	public void testStrToDate() throws ParseException {
		Date date = new DateConverter().dateFromYYYYMMDD("19810809");
		assertEquals(date.getYear(), 81);
		assertEquals(date.getMonth(), 7);
		assertEquals(date.getDate(), 9);
	}

	@Test
	public void testDateToStr() throws ParseException {
		Date date2 = new Date(1387661868000L);
		String str = new DateConverter().yyyyMMddFromDate(date2);
		assertEquals(str, "20131221");
	}

}
