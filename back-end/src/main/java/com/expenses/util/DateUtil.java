package com.expenses.util;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtil {

	/**
	 * Transform a string into a date.
	 * 
	 * @param date
	 * @return
	 */
	public static Date stringToDate(String date) {
		Date result = null;
		if (date != null && !"".equals(date)) {
			DateTimeFormatter f = DateTimeFormat.forPattern(SmartMoneyConstants.DATE_FORMAT);
			DateTime dateTime = f.parseDateTime(date);
			result = dateTime.toDate();
		}

		return result;
	}

	/**
	 * Transform a date into a string.
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		DateTime dt = new DateTime(date.getTime());
		DateTimeFormatter fmt = DateTimeFormat.forPattern(SmartMoneyConstants.DATE_FORMAT);
		String dtStr = fmt.print(dt);

		return dtStr;
	}

}
