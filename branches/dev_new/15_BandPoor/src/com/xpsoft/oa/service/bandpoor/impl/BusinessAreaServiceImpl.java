package com.xpsoft.oa.service.bandpoor.impl;

import java.util.List;
import java.util.Map;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.bandpoor.BusinessAreaDao;
import com.xpsoft.oa.model.bandpoor.Band;
import com.xpsoft.oa.model.bandpoor.BusinessArea;
import com.xpsoft.oa.service.bandpoor.BusinessAreaService;

public class BusinessAreaServiceImpl extends BaseServiceImpl<BusinessArea> implements BusinessAreaService{
	private BusinessAreaDao dao;
	
	public BusinessAreaServiceImpl(BusinessAreaDao dao) {
		super(dao);
		this.dao = dao;
	}

	public boolean validateUnique(Map params) {
		QueryFilter filter = new QueryFilter(params);
		List<BusinessArea> list = this.getAll(filter);
		if(list.size() > 0) {
			return false;
		}
		return true;
	}
	public boolean multiSave(List<BusinessArea> list) {
		try {
			for(BusinessArea ba : list) {
				this.save(ba);
			}
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
