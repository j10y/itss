package com.digitalchina.itil.event.service.impl;

import java.util.List;
import java.util.Map;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.exception.ServiceException;
import com.digitalchina.itil.event.dao.SearchEventStateDao;
import com.digitalchina.itil.event.service.SearchEventStateService;

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
