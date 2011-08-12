package com.xpsoft.oa.service.kpi;

import java.util.Map;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.kpi.HrPaAssessmentcriteria;

public interface HrPaAssessmentcriteriaService extends BaseService<HrPaAssessmentcriteria>{
	public abstract Map<String, String> getKeyAndName();
}
