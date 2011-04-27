package com.zsgj.itil.event.webservice;

import java.util.Map;

/**
 * CALLCENTER调用ITIL的WS接口
 * @Class Name CCLoginItilWs
 * @Author sa
 * @Create In Mar 31, 2009
 */
public interface CCLoginItilWs {
	
	/**
	 * CC远程调用登录itil的方法
	 * @Methods Name login
	 * @Create In Mar 31, 2009 By sa
	 * @param loginItcode 登陆ITIL系统账号, 
	 * @param submitUserItcode 坐席员工ITCODE, 也就是事件提交人
	 * @param customerItcode 客户员工ITCODE， 也就是来电的员工
	 * @param callId 话务ID
	 * @param callPhone 来电号码
	 * @return String
	 */
	Map login(String loginItcode, String submitUserItcode, String customerItcode,String callId,String callPhone);
	
}
