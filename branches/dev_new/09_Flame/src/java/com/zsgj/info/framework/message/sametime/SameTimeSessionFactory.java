package com.zsgj.info.framework.message.sametime;

import java.util.HashMap;

import com.zsgj.info.framework.message.sametime.impl.SameTimeServiceImpl;

public class SameTimeSessionFactory {

	private static HashMap entryList = new HashMap();
	
	public static final int SYSTEM_ALERT_EVENT_TYPE = 145700;
	public static final int SYSTEM_REQUEST_EVENT_TYPE = 145700;
	public final static long ResolutionTimeOut = 10000L;
	public final static long ResolutionPeriod = 25L;
	
	/**
	 * 获取SameTime服务，如果队列中存在则返回，否则新建
	 * @Methods Name getInstance
	 * @Create In Feb 1, 2008 By Iceman
	 * @param loginName 用户登陆的ITCode
	 * @return SameTimeFactory
	 */
	public static SameTimeService getInstance(String loginName){
		if(entryList.get(loginName) != null){
			return (SameTimeService)entryList.get(loginName);
		}else{
			SameTimeServiceImpl stf = new SameTimeServiceImpl();
			entryList.put(loginName, stf);
			return (SameTimeService)stf;
		}
	}
	
	public static void destroy(String loginName){
		if(entryList.get(loginName) != null){
			SameTimeService stf = (SameTimeService)entryList.get(loginName);
			stf.logout();
			entryList.remove(loginName);
			
		}
	}
}
