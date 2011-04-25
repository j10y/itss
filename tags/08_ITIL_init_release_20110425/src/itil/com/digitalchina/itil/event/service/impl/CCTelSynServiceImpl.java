package com.digitalchina.itil.event.service.impl;

import com.digitalchina.info.framework.service.BaseService;
import com.digitalchina.itil.event.dao.CCTelSynDao;
import com.digitalchina.itil.event.service.CCTelSynService;

public class CCTelSynServiceImpl extends BaseService implements CCTelSynService {
	private  CCTelSynDao ccTelSynDao;
	
	public void setCcTelSynDao(CCTelSynDao ccTelSynDao) {
		this.ccTelSynDao = ccTelSynDao;
	}

	public void saveCCTel2Native(String dateString) {
		ccTelSynDao.insertCCTel2Native(dateString);
	}

}
