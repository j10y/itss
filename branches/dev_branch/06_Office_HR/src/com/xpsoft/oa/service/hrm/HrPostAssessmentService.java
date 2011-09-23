package com.xpsoft.oa.service.hrm;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.hrm.HrPostAssessment;
import com.xpsoft.oa.model.hrm.HrPromAssessment;

public interface HrPostAssessmentService extends BaseService<HrPostAssessment>{
	public HrPostAssessment getByApplyId(Long applyId);
	
	public HrPostAssessment saveViewByApplyId(Long applyId);
}
