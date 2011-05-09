package com.zsgj.itil.event.webservice.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.PropertiesUtil;
import com.zsgj.itil.event.entity.CCCallInfo;
import com.zsgj.itil.event.webservice.CCLoginItilWs;
public class CCLoginItilWsImpl extends BaseDao implements CCLoginItilWs {
	public Map login(String loginItCode, String submitUserItcode, String customerItcode,
			String callId, String callPhone) {
		Map map = new HashMap();
		String message = "";
		Boolean flag = Boolean.TRUE;
		try {
			
			if(StringUtils.isBlank(submitUserItcode)){
				flag = Boolean.FALSE;
				message = "坐席员工Itcode不能为空";
			}else if(StringUtils.isBlank(callId)){
				flag = Boolean.FALSE;
				message = "话务ID不能为空";
			}else{
				String patam =loginItCode+"*"+submitUserItcode+"*"+customerItcode+"*"+callId+"*"+callPhone;
				String url = PropertiesUtil.getProperties("webccurl", "http://10.1.120.55:8080/user/event/ccEntry/CCCallInfo.jsp?CCCallParam=");
				message = url + patam;
			}
			map.put("message", message);
			map.put("flag", flag);
			
		} catch (Exception e) {
			message = e.getMessage();
			flag = Boolean.FALSE;
			map.put("message", message);
			map.put("flag", flag);
		}
		return map;
	}   
}
