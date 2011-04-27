package com.zsgj.itil.event.service;

import java.util.List;
import java.util.Map;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.itil.event.entity.Event;

public interface SearchEventStateService {
	
	
	/**
	 * 返回事件状态查询的grid数据信息
	 * @param  
	 * @Methods Name getSearchEventStateGridInfo
	 * @Create In 2010-6-24 By zhangzy
	 * @return Page
	 */
	public Page getSearchEventStateGridInfo(String summary,String eventStatus, 
																			String supportGroup , String createUser,
																			String dealuser, int pageNo, int pageSize);

}
