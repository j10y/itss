package com.zsgj.itil.project.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.itil.config.extlist.entity.SRProjectPlan;
import com.zsgj.itil.project.service.SRProjectPlanService;
import com.zsgj.itil.require.entity.SpecialRequirement;

public class SRProjectPlanServiceImpl extends BaseDao implements SRProjectPlanService {
	BaseAction ba=new BaseAction();

	public List<SRProjectPlan> findChildPlans(SRProjectPlan projectPlan) {
		Criteria criteria = super.getCriteria(SRProjectPlan.class);
		criteria.add(Restrictions.eq("parentPlan", projectPlan));
		List<SRProjectPlan> result = criteria.list();
		return result;
	}

	public SRProjectPlan findProjectPlanById(String id) {
		if(id==null||"".equals(id))return null;
		Criteria criteria = super.getCriteria(SRProjectPlan.class);
		criteria.add(Restrictions.eq("id", Long.valueOf(id)));
		SRProjectPlan projectPlan = (SRProjectPlan) criteria.uniqueResult();
		return projectPlan;
	}

	public SRProjectPlan findRootProjectPlanByReq(String requireId) {
		
		SpecialRequirement sr = (SpecialRequirement) ba.getService().find(SpecialRequirement.class, requireId);
		Criteria criteria = super.createCriteria(SRProjectPlan.class);
		criteria.add(Restrictions.eq("specialRequire", sr));
		criteria.add(Restrictions.isNull("parentPlan"));
		SRProjectPlan projectPlan = (SRProjectPlan) criteria.uniqueResult();
		return projectPlan;
	}

	public List<SRProjectPlan> findAllProjectPlanByReq(String requireId) {
		SpecialRequirement sr = (SpecialRequirement) ba.getService().find(SpecialRequirement.class, requireId);
		Criteria criteria = super.createCriteria(SRProjectPlan.class);
		criteria.add(Restrictions.eq("specialRequire", sr));
		List<SRProjectPlan> projectPlans = criteria.list();
		return projectPlans;
	}
}
