package com.zsgj.itil.project.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberUtil {
	/**
	 * 格式化double类型数据
	 * TODO
	 * Nov 28, 2008 By chuanyu ou
	 * @param num
	 * @return TODO
	 */
	public static Double numFormat(Double num){
		if(num==null){
			return 0.0;
		}
		return new BigDecimal(num).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 将数字格式字符串转换成字符全的百分式
	 * TODO
	 * Oct 29, 2008 By chuanyu ou
	 * @param num
	 * @return TODO
	 */
	@SuppressWarnings("unused")
	private static String numberToPercent(String num){
		String strNum = "";
		DecimalFormat format = new DecimalFormat("##.##%");
		strNum = format.format(new Double(num));
		return strNum;
	}
	
	/**
	 * 将字符串的百分数转换成数字格式字符串,如果格式不正确则返回null
	 * TODO
	 * Oct 29, 2008 By chuanyu ou
	 * @param strNum
	 * @return TODO
	 */
	@SuppressWarnings("unused")
	private static String percentToNumber(String strNum){
		if(strNum.indexOf("%")==-1){
			return null;
		}
		NumberFormat.getPercentInstance();
		NumberFormat format = NumberFormat.getNumberInstance();  
		strNum = strNum.substring(0, strNum.length()-1);
		strNum = format.format(new Double(strNum)/100);
		return strNum;
	}
	
	/**
	 * 将String转化成Double ，null和空串转化成0
	 * TODO
	 * Dec 3, 2008 By Administrator
	 * @param strNum
	 * @return TODO
	 */
	public static Double strToNumber(String strNum){
		if(strNum==null||strNum.equals("")){
			return 0.0;
		}else{
			return new Double(strNum);
		}
	}
}
