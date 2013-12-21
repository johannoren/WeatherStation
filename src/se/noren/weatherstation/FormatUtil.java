package se.noren.weatherstation;

import java.math.BigDecimal;

public class FormatUtil {

	public static String round(double d, int decimals) {
		if (Double.isNaN(d)) {
			return "N/A";
		} else {
			return new BigDecimal(String.valueOf(d)).setScale(1, BigDecimal.ROUND_HALF_UP).toPlainString();
		}
	}
}
