package com.digitalchina.info.appframework.metadata.service.impl;

import com.digitalchina.info.appframework.metadata.dao.IdGeneratorDao;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.service.IdGeneratorService;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.Department;
import com.digitalchina.info.framework.service.BaseService;

public class IdGeneratorServiceImp extends BaseService implements IdGeneratorService {

	private IdGeneratorDao idGeneratorDao;

	public IdGeneratorDao getIdGeneratorDao() {
		return idGeneratorDao;
	}

	public void setIdGeneratorDao(IdGeneratorDao idGeneratorDao) {
		this.idGeneratorDao = idGeneratorDao;
	}

	public Page findAllDepartment(String departName, int start, int pageSize) {

		Page page = idGeneratorDao.selectAllDepartNameInfo(departName, start, pageSize);
		return page;
	}

	public Page findAllSystemMainTable(String tableCnName, int start,
			int pageSize) {
		Page page = idGeneratorDao.selectAllSystemMainTableInfo(tableCnName,
				start, pageSize);
		return page;
	}
	public Page findSystemMainTableIdBuilder(String tableId, String departId,int pageNo,
			int pageSize){
		return idGeneratorDao.getSystemMainTableIdBuilder(tableId, departId, pageNo, pageSize);
	}
}