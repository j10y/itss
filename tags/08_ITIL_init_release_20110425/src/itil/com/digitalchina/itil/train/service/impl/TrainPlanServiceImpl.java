package com.digitalchina.itil.train.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.train.entity.Quest;
import com.digitalchina.itil.train.entity.QuestOption;
import com.digitalchina.itil.train.entity.QuestResult;
import com.digitalchina.itil.train.entity.Survey;
import com.digitalchina.itil.train.entity.SurveyType;
import com.digitalchina.itil.train.entity.TrainCourse;
import com.digitalchina.itil.train.entity.TrainCourseGrade;
import com.digitalchina.itil.train.entity.TrainCourseSignup;
import com.digitalchina.itil.train.entity.TrainCourseSurvey;
import com.digitalchina.itil.train.entity.TrainInstructor;
import com.digitalchina.itil.train.entity.TrainPlan;
import com.digitalchina.itil.train.service.TrainPlanService;


public class TrainPlanServiceImpl extends BaseDao implements TrainPlanService{
	public void removeTrain(String clazz,String scIds) {
		Class cla = null;
		try {
			cla = Class.forName(clazz);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(clazz.equals("com.digitalchina.itil.train.entity.TrainInstructor")){
			TrainInstructor t = (TrainInstructor) super.get(cla, Long.valueOf(scIds));
			t.setDeleteFlag(1);
			super.save(t);//.removeById(cla, Long.valueOf(scIds));
		}
		if(clazz.equals("com.digitalchina.itil.train.entity.TrainCourse")){
			TrainCourse t = (TrainCourse) super.get(cla, Long.valueOf(scIds));
			t.setDeleteFlag(Integer.valueOf(1));
			super.save(t);//.removeById(cla, Long.valueOf(scIds));
		}
		if(clazz.equals("com.digitalchina.itil.train.entity.TrainCourseGrade")){
			TrainCourseGrade t = (TrainCourseGrade) super.get(cla, Long.valueOf(scIds));
			t.setDeleteFlag(Integer.valueOf(1));
			super.save(t);//.removeById(cla, Long.valueOf(scIds));
		}
		if(clazz.equals("com.digitalchina.itil.train.entity.TrainCourseSignup")){
			TrainCourseSignup t = (TrainCourseSignup) super.get(cla, Long.valueOf(scIds));
			t.setDeleteFlag(Integer.valueOf(1));
			super.save(t);//.removeById(cla, Long.valueOf(scIds));
		}
		if(clazz.equals("com.digitalchina.itil.train.entity.TrainPlan")){
			TrainPlan t = (TrainPlan) super.get(cla, Long.valueOf(scIds));
			t.setDeleteFlag(Integer.valueOf(1));
			super.save(t);//.removeById(cla, Long.valueOf(scIds));
		}
	}

	public Survey findSurveyById(String id) {
		// TODO Auto-generated method stub
		Survey survey = null;
		Criteria c = super.getCriteria(Survey.class);
		c.add(Restrictions.eq("id", Long.valueOf(id)));
		survey = (Survey) c.uniqueResult();
		return survey;
	}

	public List<TrainCourse> findAllCourse() {
		// TODO Auto-generated method stub
		TrainCourse trainCourse = null;
		Criteria c = super.getCriteria(TrainCourse.class);
		c.add(Restrictions.eq("deleteFlag", 0));
		c.list();
		return c.list();
	}

	public TrainCourse findTrainCourseById(String id) {
		TrainCourse trainCourse = null;
		Criteria c = super.getCriteria(TrainCourse.class);
		c.add(Restrictions.eq("id", Long.valueOf(id)));
		trainCourse = (TrainCourse) c.uniqueResult();
		return trainCourse;
	}

	public TrainCourseSurvey findTrainCourseSurvey(Survey survey) {
		// TODO Auto-generated method stub
		TrainCourseSurvey trainCourseSurvey = null;
		Criteria c = super.getCriteria(TrainCourseSurvey.class);
		c.add(Restrictions.eq("survey", survey));
		trainCourseSurvey = (TrainCourseSurvey) c.uniqueResult();
		return trainCourseSurvey;
	}

	public List<QuestOption> findQuestOption(Quest quest) {
		// TODO Auto-generated method stub
//		this.findBy(QuestOption.class, "quest", quest);
//		Criteria c = super.getCriteria(QuestOption.class);
//		c.add(Restrictions.eq("quest", quest));
		return this.findBy(QuestOption.class, "quest", quest);
	}

	public List<Quest> findQuest(Survey survey) {
		// TODO Auto-generated method stub
		Criteria c = super.getCriteria(Quest.class);
		c.add(Restrictions.eq("survey", survey));
		c.addOrder(Property.forName("questType").asc());
//		return this.findBy(Quest.class, "survey", survey);
		return c.list();
	}
	public Page findQuest(Survey survey,int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		Page page = null;
		Criteria c = super.getCriteria(Quest.class);
		c.add(Restrictions.eq("survey", survey));
		c.addOrder(Property.forName("questType").asc());
		page = super.pagedQuery(c, pageNo, pageSize);
//		return this.findBy(Quest.class, "survey", survey);
		return page;
	}
	public List<TrainCourseSurvey> findCourseSurveyByCourse(
			TrainCourse trainCourse) {
		// TODO Auto-generated method stub
		TrainCourseSurvey trainCourseSurvey = null;
		Criteria c = super.getCriteria(TrainCourseSurvey.class);
		c.add(Restrictions.eq("trainCourse", trainCourse));
		return c.list();
	}
	
	public TrainCourseSurvey findCourseSurveyByCourse2(
			TrainCourse trainCourse) {
		// TODO Auto-generated method stub
		TrainCourseSurvey trainCourseSurvey = null;
		Criteria c = super.getCriteria(TrainCourseSurvey.class);
		c.add(Restrictions.eq("trainCourse", trainCourse));
		trainCourseSurvey=(TrainCourseSurvey) c.uniqueResult();
		return trainCourseSurvey;
	}
	public Page getSurveyAll(SurveyType surveyType,int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		Page page = null;
		Criteria c = super.getCriteria(Survey.class);
//		c.setFetchMode("survey", FetchMode.JOIN);
		c.add(Restrictions.eq("deployFlag", 1));
		c.add(Restrictions.eq("surveyType", surveyType));
		page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	}

	/* (non-Javadoc)
	 * @see com.digitalchina.itil.train.service.TrainPlanService#findQuestByCourse(com.digitalchina.itil.train.entity.TrainCourse)
	 */
	public List<QuestResult> findQuestByCourse(TrainCourse trainCourse) {
		Criteria c = super.getCriteria(QuestResult.class);
		c.add(Restrictions.eq("trainCourse", trainCourse));
		c.add(Restrictions.eq("userId", UserContext.getUserInfo()));
		List<QuestResult>questResult=c.list();
		return questResult;
	}

	public Page findUserNameByParam(String UName, int pageNo, int pageSize) {
		Criteria criteria = super.getCriteria(UserInfo.class);
		criteria.add(Restrictions.like ("realName",UName,MatchMode.START));
		Page page = super.pagedQuery(criteria, pageNo, pageSize);
		
		return page;
	}

	public Page findCourseNameByParam(String CName, int pageNo, int pageSize) {
		Criteria criteria = super.getCriteria(TrainCourse.class);
		criteria.add(Restrictions.like ("courseName",CName,MatchMode.START));
		criteria.add(Restrictions.eq("deleteFlag", Integer.parseInt("0")));
		Page page = super.pagedQuery(criteria, pageNo, pageSize);
		
		return page;
	}

//	public List findAllNotice(int auditflag, Date date) {
//		// TODO Auto-generated method stub
//		NewNotice newNotice = null;
//		Criteria c = super.getCriteria(NewNotice.class);
//		c.add(Restrictions.eq("auditflag", auditflag));
//		c.add(Restrictions.lt("endDate", date));
//		//c.add(Restrictions.);
//		return c.list();
//	}
	
	
}
