package com.digitalchina.itil.event.dao;

import java.util.List;

import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.itil.event.entity.Event;

public interface SearchEventStateDao {
	
	public Page selectSearchEventStateGridInfo(String summary,
			String eventStatus, String supportGroup, String createUser,
			String dealuser ,int pageNo, int pageSize);

}
