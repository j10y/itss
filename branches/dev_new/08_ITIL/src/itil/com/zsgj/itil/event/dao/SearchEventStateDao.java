package com.zsgj.itil.event.dao;

import java.util.List;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.itil.event.entity.Event;

public interface SearchEventStateDao {
	
	public Page selectSearchEventStateGridInfo(String summary,
			String eventStatus, String supportGroup, String createUser,
			String dealuser ,int pageNo, int pageSize);

}
