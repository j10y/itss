package com.digitalchina.info.appframework.metadata.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.digitalchina.info.appframework.metadata.dao.IdGeneratorDao;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableIdBuilder;
import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.exception.DaoException;
import com.digitalchina.info.framework.security.entity.Department;

public class IdGeneratorDaoImp extends BaseDao implements IdGeneratorDao {


	public Page selectAllDepartNameInfo(String departName, int pageNo, int pageSize) {
		try{
			
			Criteria c = super.createCriteria(Department.class);
			 if(departName.trim().length()!=0){
				 c.add(Restrictions.like("departName", departName, MatchMode.ANYWHERE));
			 }
			return pagedQuery(c, pageNo, pageSize);
		}catch(Exception e){
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}		
	}

	public Page selectAllSystemMainTableInfo(String tableCnName, int pageNo,
			int pageSize) {
		try{
			
			Criteria c = super.createCriteria(SystemMainTable.class);
			 if(tableCnName!=null&&tableCnName.trim().length()!=0){
				 c.add(Restrictions.like("tableCnName", tableCnName, MatchMode.ANYWHERE));
			 }
			 return pagedQuery(c, pageNo, pageSize);
		}catch(Exception e){
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public Page getSystemMainTableIdBuilder(String tableId, String departId,int pageNo,
			int pageSize){
		Criteria c = super.createCriteria(SystemMainTableIdBuilder.class);
		if(StringUtils.isNotBlank(tableId)){
			c.add(Restrictions.eq("systemMainTable.id", Long.valueOf(tableId)));
		}
		if(StringUtils.isNotBlank(departId)){
			c.add(Restrictions.eq("department.id", Long.valueOf(departId)));
		}
		return this.pagedQuery(c, pageNo, pageSize);
	}
}
