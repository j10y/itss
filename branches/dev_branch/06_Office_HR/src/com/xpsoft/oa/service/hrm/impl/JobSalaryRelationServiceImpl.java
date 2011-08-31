package com.xpsoft.oa.service.hrm.impl;

import java.util.List;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.hrm.JobSalaryRelationDao;
import com.xpsoft.oa.model.hrm.Job;
import com.xpsoft.oa.model.hrm.JobSalaryRelation;
import com.xpsoft.oa.service.hrm.JobSalaryRelationService;

public class JobSalaryRelationServiceImpl extends BaseServiceImpl<JobSalaryRelation>
		implements JobSalaryRelationService {
	private JobSalaryRelationDao dao;

	public JobSalaryRelationServiceImpl(JobSalaryRelationDao dao) {
		super(dao);
		this.dao = dao;
	}

	public List<JobSalaryRelation> findByDep(Long depId) {
		return this.dao.findByDep(depId);
	}
}