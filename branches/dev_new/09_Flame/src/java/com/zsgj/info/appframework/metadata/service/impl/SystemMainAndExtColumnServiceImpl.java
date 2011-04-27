package com.zsgj.info.appframework.metadata.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.zsgj.info.appframework.metadata.entity.ExtData;
import com.zsgj.info.appframework.metadata.entity.ExtOptionData;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.service.SystemMainAndExtColumnService;
import com.zsgj.info.framework.dao.BaseDao;

public class SystemMainAndExtColumnServiceImpl extends BaseDao implements
		SystemMainAndExtColumnService {

	public List findAllColumnBySysMainTable(SystemMainTable smt) {
		// TODO Auto-generated method stub
		Criteria c = this.createCriteria(SystemMainTableColumn.class);
		c.add(Restrictions.eq("systemMainTable", smt));
		List list=c.list();
		return list;
	}

	public List findColumnByIsExtAndSysMainTable(Integer isExtend,
			SystemMainTable smt) {
		// TODO Auto-generated method stub
		Criteria c = this.createCriteria(SystemMainTableColumn.class);
		c.add(Restrictions.eq("isExtColumn", isExtend));
		c.add(Restrictions.eq("systemMainTable", smt));
		List list=c.list();
		return list;
	}
	
	public List findExtOptionDataByExtColId(String ectColId){
		Criteria c = this.createCriteria(ExtOptionData.class);
		c.add(Restrictions.eq("extColumnId", Integer.parseInt(ectColId)));
		List list=c.list();
		return list;
	}

	public Object findObjectByMainRowIdAndExtColId(Integer mainRowId,
			Integer extid) {
		// TODO Auto-generated method stub
		Criteria c = this.createCriteria(ExtData.class);
		c.add(Restrictions.eq("extendTableId", extid));
		c.add(Restrictions.eq("mainTableRowID", mainRowId));
		Object ob=c.uniqueResult();
		return ob;
	}

	public Object findOptionById(Long id) {
		// TODO Auto-generated method stub
		Criteria c = this.createCriteria(ExtOptionData.class);
		c.add(Restrictions.eq("id", id));
		Object ob=c.uniqueResult();
		return ob;
	}

	public Object saveExtOption(Object object) {
		// TODO Auto-generated method stub
		
		return this.save(object);
	}

	public void removeOptionById(Long id) {
		// TODO Auto-generated method stub
		this.removeObject(ExtOptionData.class, id);
	}

}
