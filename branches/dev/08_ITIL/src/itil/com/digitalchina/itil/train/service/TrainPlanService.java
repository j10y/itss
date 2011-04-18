package com.digitalchina.itil.train.service;

import java.util.Date;
import java.util.List;

import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.itil.train.entity.Quest;
import com.digitalchina.itil.train.entity.QuestOption;
import com.digitalchina.itil.train.entity.QuestResult;
import com.digitalchina.itil.train.entity.QuestType;
import com.digitalchina.itil.train.entity.Survey;
import com.digitalchina.itil.train.entity.SurveyType;
import com.digitalchina.itil.train.entity.TrainCourse;
import com.digitalchina.itil.train.entity.TrainCourseSurvey;

public interface TrainPlanService {
	/**
	 * ɾ������ ���ñ�ʶ
	 * @Methods Name removeTrain
	 * @Create In Feb 16, 2009 By A
	 * @param clazz
	 * @param scIds void
	 */
	public void removeTrain(String clazz,String scIds);
	/**
	 * ����id��ȡSurvey
	 * @Methods Name findSurveyById
	 * @Create In Feb 17, 2009 By A
	 * @param id
	 * @return Survey
	 */
	public Survey findSurveyById(String id);
	/**
	 * ͨ��ɾ����ʶλɸѡ���пγ�
	 * @Methods Name findAllCourse
	 * @Create In Feb 17, 2009 By A
	 * @return List<TrainCourse>
	 */
	public List<TrainCourse> findAllCourse();
	/**
	 * ����id���TrainCourse
	 * @Methods Name findTrainCourseById
	 * @Create In Feb 17, 2009 By A
	 * @param id
	 * @return TrainCourse
	 */
	public TrainCourse findTrainCourseById(String id);
	/**
	 * ����Survey�ҵ���TrainCourse��ϵ��
	 * @Methods Name findTrainCourseSurvey
	 * @Create In Feb 17, 2009 By Administrator
	 * @param survey
	 * @param trainCourse
	 * @return TrainCourseSurvey
	 */
	public TrainCourseSurvey findTrainCourseSurvey(Survey survey);
	/**
	 * �������������ҵ����ж�Ӧ��ѡ��
	 * @Methods Name findQuestOption
	 * @Create In Feb 24, 2009 By A
	 * @param quest
	 * @return List<QuestOption>
	 */
	public List<QuestOption> findQuestOption(Quest quest);
	/**
	 * ���������ʾ��ҵ����ж�Ӧ����
	 * @Methods Name findQuest
	 * @Create In Feb 24, 2009 By Administrator
	 * @param survey
	 * @return List<Quest>
	 */
	public List<Quest> findQuest(Survey survey);
	/**
	 * ���������ʾ��ҵ����ж�Ӧ���� ��ҳ
	 * @Methods Name findQuest
	 * @Create In Mar 12, 2009 By A
	 * @param survey
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	public Page findQuest(Survey survey,int pageNo, int pageSize);
	/**
	 * ���ݿγ����ʾ�
	 * @Methods Name findCourseSurveyByCourse
	 * @Create In Feb 27, 2009 By Administrator
	 * @param trainCourse
	 * @return List<TrainCourseSurvey>
	 */
	public List<TrainCourseSurvey> findCourseSurveyByCourse(TrainCourse trainCourse);
	/**
	 * 
	 * @Methods Name findAllNotice
	 * @Create In Mar 17, 2009 By Administrator
	 * @param auditflag
	 * @param date
	 * @return List
	 */
//	public List findAllNotice(int auditflag,Date date);
	
	public Page getSurveyAll(SurveyType surveyType,int pageNo, int pageSize);
	
	public TrainCourseSurvey findCourseSurveyByCourse2(TrainCourse trainCourse);
	/**
	 * ͨ���γ̲�ѯ�Ƿ��û���д�˵�ǰ�ʾ�
	 * @Methods Name findQuestByCourse
	 * @Create In May 5, 2009 By sujs
	 * @return List<QuestResult>
	 */
	List<QuestResult> findQuestByCourse(TrainCourse trainCourse);
	
	/**
	 * ģ����ѯ������ԱCOMBO
	 * @Methods Name findUserNameByParam
	 * @Create In Jun 25, 2009 By guoxl
	 * @param UName
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page findUserNameByParam(String UName,int pageNo,int pageSize);
	
	/**
	 * ģ����ѯ�γ�COMBO
	 * @Methods Name findCourseNameByParam
	 * @Create In Jun 25, 2009 By guoxl
	 * @param CName
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page findCourseNameByParam(String CName,int pageNo,int pageSize);
}
