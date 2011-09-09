package com.xpsoft.oa.service.hrm;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.hrm.HrPromAssessment;

public interface HrPromAssessmentService extends BaseService<HrPromAssessment>{
	public HrPromAssessment getByApplyId(Long applyId);
	
	public HrPromAssessment saveViewByApplyId(Long applyId);
}
