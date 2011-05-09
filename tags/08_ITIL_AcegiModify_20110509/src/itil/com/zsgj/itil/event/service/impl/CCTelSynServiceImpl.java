package com.zsgj.itil.event.service.impl;

import com.zsgj.info.framework.service.BaseService;
import com.zsgj.itil.event.dao.CCTelSynDao;
import com.zsgj.itil.event.service.CCTelSynService;

public class CCTelSynServiceImpl extends BaseService implements CCTelSynService {
	private  CCTelSynDao ccTelSynDao;
	
	public void setCcTelSynDao(CCTelSynDao ccTelSynDao) {
		this.ccTelSynDao = ccTelSynDao;
	}

	public void saveCCTel2Native(String dateString) {
		ccTelSynDao.insertCCTel2Native(dateString);
	}

}
