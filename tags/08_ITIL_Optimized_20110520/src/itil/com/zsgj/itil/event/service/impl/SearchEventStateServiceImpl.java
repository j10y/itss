package com.zsgj.itil.event.service.impl;

import java.util.List;
import java.util.Map;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.itil.event.dao.SearchEventStateDao;
import com.zsgj.itil.event.service.SearchEventStateService;

public class SearchEventStateServiceImpl implements SearchEventStateService {
	
	private SearchEventStateDao searchEventStateDao ; 
	
	

	public SearchEventStateDao getSearchEventStateDao() {
		return searchEventStateDao;
	}

	public void setSearchEventStateDao(SearchEventStateDao searchEventStateDao) {
		this.searchEventStateDao = searchEventStateDao;
	}



	public Page getSearchEventStateGridInfo(String summary,
			String eventStatus, String supportGroup, String createUser,
			String dealuser ,int pageNo, int pageSize) {	
		
		try {
			Page page =  searchEventStateDao.selectSearchEventStateGridInfo(summary, eventStatus, 
																					supportGroup, createUser, dealuser, pageNo, pageSize);
			return page;
		} catch( Exception e) {
				throw new ServiceException(e.getMessage());
		}
	}

}
