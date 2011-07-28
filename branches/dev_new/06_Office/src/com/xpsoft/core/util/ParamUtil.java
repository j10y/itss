package com.xpsoft.core.util;

import java.math.BigDecimal;
import java.util.Calendar;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ParamUtil {
	/* 16 */private static Log logger = LogFactory.getLog(ParamUtil.class);

	public static Object convertObject(String type, String paramValue) {
		/* 19 */if (StringUtils.isEmpty(paramValue))
			return null;
		/* 20 */Object value = null;
		try {
			/* 22 */if ("S".equals(type)) {
				/* 23 */value = paramValue;
				/* 24 */} else if ("L".equals(type)) {
				/* 25 */value = new Long(paramValue);
				/* 26 */} else if ("N".equals(type)) {
				/* 27 */value = new Integer(paramValue);
				/* 28 */} else if ("BD".equals(type)) {
				/* 29 */value = new BigDecimal(paramValue);
				/* 30 */} else if ("FT".equals(type)) {
				/* 31 */value = new Float(paramValue);
				/* 32 */} else if ("SN".equals(type)) {
				/* 33 */value = new Short(paramValue);
				/* 34 */} else if ("D".equals(type)) {
				/* 35 */value = DateUtils.parseDate(paramValue, new String[] {
						"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" });
				/* 36 */} else if ("DL".equals(type)) {
				/* 37 */Calendar cal = Calendar.getInstance();
				/* 38 */cal.setTime(DateUtils.parseDate(paramValue,
						new String[] { "yyyy-MM-dd" }));
				/* 39 */value = DateUtil.setStartDay(cal).getTime();
				/* 40 */} else if ("DG".equals(type)) {
				/* 41 */Calendar cal = Calendar.getInstance();
				/* 42 */cal.setTime(DateUtils.parseDate(paramValue,
						new String[] { "yyyy-MM-dd" }));
				/* 43 */value = DateUtil.setEndDay(cal).getTime();
			} else {
				/* 45 */value = paramValue;
			}
		} catch (Exception ex) {
			/* 48 */logger
					.error("the data value is not right for the query filed type:"
							+ ex.getMessage());
		}
		/* 50 */return value;
	}
}
