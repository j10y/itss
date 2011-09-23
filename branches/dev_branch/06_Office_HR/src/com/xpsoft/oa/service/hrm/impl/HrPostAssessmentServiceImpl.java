package com.xpsoft.oa.service.hrm.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.oa.dao.hrm.HrPostAssessmentDao;
import com.xpsoft.oa.model.hrm.EmpProfile;
import com.xpsoft.oa.model.hrm.HrPostApply;
import com.xpsoft.oa.model.hrm.HrPostAssessment;
import com.xpsoft.oa.model.hrm.HrPromApply;
import com.xpsoft.oa.model.hrm.HrPromAssessment;
import com.xpsoft.oa.service.hrm.EmpProfileService;
import com.xpsoft.oa.service.hrm.HrPostApplyService;
import com.xpsoft.oa.service.hrm.HrPostAssessmentService;

public class HrPostAssessmentServiceImpl extends BaseServiceImpl<HrPostAssessment>
		implements HrPostAssessmentService{
	private HrPostAssessmentDao dao;
	
	public HrPostAssessmentServiceImpl(HrPostAssessmentDao dao) {
		super(dao);
		this.dao = dao;
	}
	
	public HrPostAssessment getByApplyId(Long applyId) {
		String HQL = "FROM HrPostAssessment v WHERE v.postApply.id=? AND v.publishStatus!=?";
		Object[] paramArrayOfObject = new Object [] {applyId, HrPostApply.STATUS_DEL};
		List<HrPostAssessment> list = dao.findByHql(HQL, paramArrayOfObject);
		if(list.size()<=0){
			return null;
		}else{
			return list.get(0);
		}
	}
	
	public HrPostAssessment saveViewByApplyId(Long applyId) {
		String HQL = "FROM HrPostAssessment v WHERE v.postApply.id=? AND v.publishStatus!=?";
		Object[] paramArrayOfObject = new Object [] {applyId, HrPostApply.STATUS_DEL};
		List<HrPostAssessment> list = dao.findByHql(HQL, paramArrayOfObject);
		if(list.size()<=0){
			HrPostApplyService hrPostApplyService = (HrPostApplyService)AppUtil.getBean("hrPostApplyService");
			HrPostApply hrPostApply = hrPostApplyService.get(applyId);
			EmpProfileService empProfileService = (EmpProfileService)AppUtil.getBean("empProfileService");
			Map<String, String> map = new HashMap<String, String>();
			map.put("Q_userId_L_EQ", hrPostApply.getApplyUser().getUserId().toString());
			QueryFilter filter = new QueryFilter(map);
			List<EmpProfile> empProfileList = empProfileService.getAll(filter);
			HrPostAssessment hrPostAssessment = new HrPostAssessment();
			hrPostAssessment.setPostApply(hrPostApply);
			hrPostAssessment.setActualReportDate(empProfileList.get(0).getAccessionTime());
			hrPostAssessment.setApplyPostDate(empProfileList.get(0).getPositiveTime());
			hrPostAssessment.setStandardPostId(hrPostApply.getPostId());
			hrPostAssessment.setStandardPostName(hrPostApply.getPostName());
			hrPostAssessment.setPublishStatus(HrPostApply.STATUS_DRAFT);//待人力资源复核
			hrPostAssessment.setCreatePerson(ContextUtil.getCurrentUser());
			hrPostAssessment.setCreateDate(new Date());
			hrPostAssessment.setModifyDate(new Date());
			hrPostAssessment.setModifyPerson(ContextUtil.getCurrentUser());
			dao.save(hrPostAssessment);
			return dao.get(hrPostAssessment.getId());
		}else{
			return list.get(0);
		}
	}
}
