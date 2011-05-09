package com.zsgj.itil.event.dao;

import java.util.List;
import java.util.Map;

import com.zsgj.info.framework.dao.Dao;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.DaoException;
import com.zsgj.itil.config.entity.CIBatchModifyShip;
import com.zsgj.itil.event.entity.Event;
import com.zsgj.itil.event.entity.EventRelation;
import com.zsgj.itil.event.entity.Problem;

public interface ProblemDao extends Dao<Problem> {
	/**
	 * ͨ��userId��������ѽ����¼�������δ��������
	 * @Methods Name selectAllNotEndProblemsOfAllEndEvents
	 * @Create In Nov 2, 2009 By duxh
	 * @return Map
	 */
	public Page selectAllNotEndProblemsOfAllEndEvents(Long userId, int start, int pageSize, String problemName) throws DaoException;
	
	public Page selectProblemsEvents(Event event,int start, int pageSize)throws DaoException;
	
	public List<Problem>  selectProblemByCurrProblem(Problem problem)throws DaoException;
	
	public Page selectEventProblem(String dataId, String eventId, String name, int pageNo,int pageSize,String status) throws DaoException;
	/**
	 * ɾ�������ϵ.
	 * @Methods Name removeProblemDoubleRel
	 * @Create In Nov 2, 2009 By duxh
	 * @return void
	 */
	void deleteProblemDoubleRel(String relId) throws DaoException;
	
	Page selectProblemRelByCurrEvent(Problem problem,int pageNo, int pageSize) throws DaoException;
	/**
	 * �ж��Ƿ����Ѵ��ڵ������ϵ.
	 * @Methods Name isExistProblem
	 * @Create In Nov 2, 2009 By duxh
	 * @return boolean
	 */
	boolean selectIsExistProblem(Long currProblemId, Long parentProblemId);
	
	/**
     * ͨ���¼�A��B�ĸ���ϵ�õ�B��A��֮��ϵ
     * @Methods Name getEventRelationByEventRel
     * @Create In Sep 16, 2009 By guoxl
     * @param eventRel
     * @return EventRelation
     */
    EventRelation selectEventRelationByEventRel(EventRelation eventRel);
    /**
     * ���������״̬��
     * @Methods Name updateProblemsStatus
     * @Create In Nov 17, 2009 By duxh
     * @param problemsId
     * @param status_keyword ProblemStatus�еĳ�����
     */
    public void updateProblemsStatus(Long[] problemsId,String status_keyword);
    /**
     * �鿴�����Ƿ���������
     * @Methods Name selectWhetherHasConfigItem
     * @Create In Nov 17, 2009 By duxh
     * @param problemsId
     * @return List<String> ���������������ı�š�
     */
    public List<String> selectWhetherHasConfigItem(Long[] problemsId);
    /**
     * 
     * @Methods Name selectAllNoPassedCIBatchModifyShipByProblem
     * @Create In Mar 11, 2010 By huzh
     * @param problemId
     * @return 
     * @Return List<CIBatchModifyShip>
     */
    public List<CIBatchModifyShip> selectAllNoPassedCIBatchModifyShipByProblem(Long problemId);
    	
    }

