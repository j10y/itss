package com.zsgj.itil.service.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.itil.require.entity.RequireApplyDefaultAudit;
import com.zsgj.itil.service.dao.RequireServiceDao;
import com.zsgj.itil.service.entity.ServiceItemApplyAuditHis;

public class RequireServiceDaoImpl extends BaseDao implements RequireServiceDao {

	public List<ServiceItemApplyAuditHis> findServiceItemApplyAuditHis(
			String dataId, String processId) {
		Criteria c = super.createCriteria(ServiceItemApplyAuditHis.class);
		c.add(Restrictions.eq("requirementId",Long.parseLong(dataId)));
		c.add(Restrictions.eq("processId", Long.parseLong(processId)));
		return c.list();
	}

	public boolean updateDeleteFlag(String id) {
		try{
			Criteria c = super.createCriteria(RequireApplyDefaultAudit.class);			
			c.add(Restrictions.eq("id",Long.parseLong(id)));			
			List<RequireApplyDefaultAudit> dataList = c.list();
			dataList.get(0).setDeleteFlag(1);			
			super.save(dataList.get(0));		
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}		
		return true;
	}
}
