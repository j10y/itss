package com.xpsoft.oa.dao.hrm.impl;


import java.util.List;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.hrm.JobSalaryRelationDao;
import com.xpsoft.oa.model.hrm.Job;
import com.xpsoft.oa.model.hrm.JobSalaryRelation;


public class JobSalaryRelationDaoImpl extends BaseDaoImpl<JobSalaryRelation>
implements JobSalaryRelationDao
{
	public JobSalaryRelationDaoImpl()
	{
		/* 15 */super(JobSalaryRelation.class);
		}

	
	public List<JobSalaryRelation> findByDep(Long depId)
	{
		String hql = "from JobSalaryRelation vo where vo.department.depId=?";
		Object[] objs = { depId };
		return findByHql(hql, objs);
	}
	
}