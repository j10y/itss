package net.shopin.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AppUtil {
	private static Log logger = LogFactory.getLog(AppUtil.class);

	@SuppressWarnings("rawtypes")
	private static Map configMap = new HashMap();

	@SuppressWarnings("unchecked")
	public static void init(ServletContext in_servletContext) {

		reloadResouse();
	}

	public static String getProperties(String key, String defValue) {
		if (configMap.isEmpty() || configMap == null) {
			reloadResouse();
		}
		try {
			return (String) configMap.get(key) == null ? defValue
					: (String) configMap.get(key);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static void reloadResouse() {
		Properties props = new Properties();
		try {
			ClassLoader cl = Thread.currentThread().getClass().getClassLoader();
			InputStream is = cl.getResourceAsStream("application.properties");
			props.load(is);
			for (Object key : props.keySet()) {
				configMap.put(key, props.get(key));
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}
}
