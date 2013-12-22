package se.noren.weatherstation.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateConverter {

	public Date dateFromYYYYMMDD(String yyyymmdd) throws ParseException {
		 DateFormat format = new SimpleDateFormat("yyyyMMdd");
		 
		 Date date = format.parse(yyyymmdd);
		 
		 return date;
	}
	
	public String yyyyMMddFromDate(Date d) throws ParseException {
		 DateFormat format = new SimpleDateFormat("yyyyMMdd");
		 
		 String string = format.format(d);
		 
		 return string;
	}


}
