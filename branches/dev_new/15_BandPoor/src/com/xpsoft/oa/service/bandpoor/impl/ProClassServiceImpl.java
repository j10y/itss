package com.xpsoft.oa.service.bandpoor.impl;

import java.util.List;
import java.util.Map;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.bandpoor.ProClassDao;
import com.xpsoft.oa.model.bandpoor.ProClass;
import com.xpsoft.oa.service.bandpoor.ProClassService;

public class ProClassServiceImpl extends BaseServiceImpl<ProClass> implements ProClassService{
	private ProClassDao dao;
	
	public ProClassServiceImpl(ProClassDao dao) {
		super(dao);
		this.dao = dao;
	}

	public boolean validateUnique(Map params) {
		QueryFilter filter = new QueryFilter(params);
		List<ProClass> list = this.getAll(filter);
		if(list.size() > 0) {
			return false;
		}
		return true;
	}
	
	public boolean multiSave(List<ProClass> list) {
		try {
			for(ProClass pc : list) {
				this.save(pc);
			}
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
