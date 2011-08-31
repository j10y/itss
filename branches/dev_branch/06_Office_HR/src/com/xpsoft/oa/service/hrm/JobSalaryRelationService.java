package com.xpsoft.oa.service.hrm;

import java.util.List;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.hrm.JobSalaryRelation;

public abstract interface JobSalaryRelationService extends BaseService<JobSalaryRelation> {
	public abstract List<JobSalaryRelation> findByDep(Long paramLong);
}