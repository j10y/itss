package com.xpsoft.oa.dao.hrm;

import java.util.List;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.hrm.JobSalaryRelation;

public abstract interface JobSalaryRelationDao extends BaseDao<JobSalaryRelation>
{
  public abstract List<JobSalaryRelation> findByDep(Long paramLong);
}