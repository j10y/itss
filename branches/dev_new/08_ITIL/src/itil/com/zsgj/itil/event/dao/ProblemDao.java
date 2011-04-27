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
	 * 通过userId获得所有已结束事件的所有未结束问题
	 * @Methods Name selectAllNotEndProblemsOfAllEndEvents
	 * @Create In Nov 2, 2009 By duxh
	 * @return Map
	 */
	public Page selectAllNotEndProblemsOfAllEndEvents(Long userId, int start, int pageSize, String problemName) throws DaoException;
	
	public Page selectProblemsEvents(Event event,int start, int pageSize)throws DaoException;
	
	public List<Problem>  selectProblemByCurrProblem(Problem problem)throws DaoException;
	
	public Page selectEventProblem(String dataId, String eventId, String name, int pageNo,int pageSize,String status) throws DaoException;
	/**
	 * 删除问题关系.
	 * @Methods Name removeProblemDoubleRel
	 * @Create In Nov 2, 2009 By duxh
	 * @return void
	 */
	void deleteProblemDoubleRel(String relId) throws DaoException;
	
	Page selectProblemRelByCurrEvent(Problem problem,int pageNo, int pageSize) throws DaoException;
	/**
	 * 判断是否有已存在的问题关系.
	 * @Methods Name isExistProblem
	 * @Create In Nov 2, 2009 By duxh
	 * @return boolean
	 */
	boolean selectIsExistProblem(Long currProblemId, Long parentProblemId);
	
	/**
     * 通过事件A和B的父关系得到B和A的之关系
     * @Methods Name getEventRelationByEventRel
     * @Create In Sep 16, 2009 By guoxl
     * @param eventRel
     * @return EventRelation
     */
    EventRelation selectEventRelationByEventRel(EventRelation eventRel);
    /**
     * 更新问题的状态。
     * @Methods Name updateProblemsStatus
     * @Create In Nov 17, 2009 By duxh
     * @param problemsId
     * @param status_keyword ProblemStatus中的常量。
     */
    public void updateProblemsStatus(Long[] problemsId,String status_keyword);
    /**
     * 查看问题是否关联配置项。
     * @Methods Name selectWhetherHasConfigItem
     * @Create In Nov 17, 2009 By duxh
     * @param problemsId
     * @return List<String> 关联配置项的问题的编号。
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

