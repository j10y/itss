package com.xpsoft.oa.dao.hrm;

import java.util.List;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.hrm.RealExecution;

public abstract interface RealExecutionDao extends BaseDao<RealExecution> {
	
	/**
	 * 
	 * @param paramLong
	 * @return
	 */
	public List treeStatics(Long paramLong);
	
}
