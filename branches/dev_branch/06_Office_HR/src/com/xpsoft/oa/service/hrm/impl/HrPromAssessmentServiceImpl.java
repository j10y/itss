package com.xpsoft.oa.service.hrm.impl;

import java.util.List;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.hrm.HrPromAssessmentDao;
import com.xpsoft.oa.model.hrm.HrPromApply;
import com.xpsoft.oa.model.hrm.HrPromAssessment;
import com.xpsoft.oa.service.hrm.HrPromAssessmentService;

public class HrPromAssessmentServiceImpl extends BaseServiceImpl<HrPromAssessment> implements
		HrPromAssessmentService{
	private HrPromAssessmentDao dao;
	public HrPromAssessmentServiceImpl(HrPromAssessmentDao dao) {
		super(dao);
		this.dao = dao;
	}
	
	/**
	 *查询applyId对应的未删除的评估表，只有一个或没有 
	 *没有则创建
	 */
	public HrPromAssessment getByApplyId(Long applyId) {
		// TODO Auto-generated method stub
		String HQL = "FROM HrPromAssessment v WHERE v.promApply.id=? AND v.publishStatus=?";
		Object[] paramArrayOfObject = new Object [] {applyId, HrPromApply.STATUS_ASSESS};
		List<HrPromAssessment> list = dao.findByHql(HQL, paramArrayOfObject);
		if(list.size()<=0){
			HrPromAssessment hrPromAssessment = new HrPromAssessment();
			HrPromApply hrPromApply = new HrPromApply();
			hrPromApply.setId(applyId);
			hrPromAssessment.setPromApply(hrPromApply);
			hrPromAssessment.setPublishStatus(HrPromApply.STATUS_ASSESS);//考核期评估
			dao.save(hrPromAssessment);
			return hrPromAssessment;
		}else{
			return list.get(0);
		}
	}
	
}
