package com.xpsoft.oa.util;

import com.xpsoft.oa.model.hrm.Budget;

public class RealExecutionUtil {
	/**
	 * <阀值 红灯
	 * >=阀值+阀值*50% 绿
	 * <阀值+阀值*50% &&>=阀值 黄
	 * @param value 预算金额
	 * @param threshold 阀值
	 * @param realValue 执行值
	 * @return 
	 */
	public static  String alarm(Double value, Double threshold, Double realValue){
		
		if((value-realValue) < value*threshold){
			return Budget.ALARM_RED;
		}else if((value-realValue) < ((value)*(threshold*1.5)) && (value-realValue) >= value*threshold){
			return Budget.ALARM_YELLOW;
		}else{
			return Budget.ALARM_GREEN;
		}
	}
}
