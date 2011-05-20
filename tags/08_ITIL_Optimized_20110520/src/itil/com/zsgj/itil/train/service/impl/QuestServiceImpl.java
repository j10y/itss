package com.zsgj.itil.train.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.train.entity.QuestOption;
import com.zsgj.itil.train.entity.QuestResult;
import com.zsgj.itil.train.service.QuestService;

public class QuestServiceImpl extends BaseDao implements QuestService{

	public List<QuestResult> getResult(UserInfo user, Long surveyId, Long objId) {
		Criteria c = super.getCriteria(QuestResult.class);
		c.add(Restrictions.eq("userId",user));
		c.add(Restrictions.eq("survey.id",surveyId));
		c.add(Restrictions.eq("objId",objId));
		return c.list();
	}
	public List<QuestResult> getResultBySurveyAndObjId(Long surveyId, Long objId) {
		Criteria c = super.getCriteria(QuestResult.class);
		c.add(Restrictions.eq("survey.id",surveyId));
		c.add(Restrictions.eq("objId",objId));
		return c.list();
	}
}
