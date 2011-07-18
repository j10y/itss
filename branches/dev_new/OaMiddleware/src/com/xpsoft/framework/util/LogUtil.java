package com.xpsoft.framework.util;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
/**
 * 自己try catch 保存错误日志
 * @Class Name LogUtil
 * @Author likang
 * @Create In Aug 15, 2010
 */
public class LogUtil {
	
	public static void info(String info) {
		Log LogUtil = LogFactory.getLog(LogUtil.class);
		LogUtil.info("=============================" + info);
	}
	
	/**
	 * 自己try catch 保存错误日志
	 * @Methods Name saveErrorInfoLog
	 * @Create In Aug 14, 2010 By likang
	 * @param e void
	 */
	public static void saveErrorInfoLog(Exception e,Log log) {
		String beginString = "\r\n*****************************************Begin*********************************************************\r\n";
		String endString = "*****************************************End*********************************************************\r\n";
		StringBuffer sBuffer = new StringBuffer();
		String msg = "";
		for (int i = 0; i < e.getStackTrace().length; i++) {
			sBuffer.append("      " + e.getStackTrace()[i] + "\r\n");
		}
		if (e instanceof DataAccessException) {
			msg = PropertiesUtil.getProperties("00000001");
		} else if (e instanceof NullPointerException) {
			msg = PropertiesUtil.getProperties("00000002");
		} else if (e instanceof IOException) {
			msg = PropertiesUtil.getProperties("00000003");
		} else if (e instanceof ClassNotFoundException) {
			msg = PropertiesUtil.getProperties("00000004");
		} else if (e instanceof ArithmeticException) {
			msg = PropertiesUtil.getProperties("00000005");
		} else if (e instanceof ArrayIndexOutOfBoundsException) {
			msg = PropertiesUtil.getProperties("00000006");
		} else if (e instanceof IllegalArgumentException) {
			msg = PropertiesUtil.getProperties("00000007");
		} else if (e instanceof ClassCastException) {
			msg = PropertiesUtil.getProperties("00000008");
		} else if (e instanceof SQLException) {
			msg = PropertiesUtil.getProperties("00000009");
		} else if (e instanceof Exception) {
			msg = PropertiesUtil.getProperties("00000010");
		} else if (e instanceof Throwable) {
			msg = PropertiesUtil.getProperties("00000011");
		} else {
			msg = PropertiesUtil.getProperties("00000000");
		}
		log.error(beginString + msg + "\r\n" + sBuffer + endString);
	}
	
}
