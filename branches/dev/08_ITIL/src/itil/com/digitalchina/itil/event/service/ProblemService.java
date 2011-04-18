package com.digitalchina.itil.event.service;

import java.util.List;
import java.util.Map;

import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.exception.ServiceException;
import com.digitalchina.itil.event.entity.Event;
import com.digitalchina.itil.event.entity.EventRelation;
import com.digitalchina.itil.event.entity.Problem;

public interface ProblemService {

    /**
     * 通过userId获得所有已结束事件的所有未结束问题
     * @param userId
     * @return
     */
    public Page findAllNotEndProblemsOfAllEndEvents(Long userId, int start, int pageSize, String problemName);
    /**
     * 
     * @Methods Name findProblemsEvents
     * @Create In Nov 5, 2009 By duxh
     * @return Page
     */
    public Page findProblemsEvents(Event event,int start, int pageSize);
    /**
     * 更改问题状态。
     * @Methods Name modifyStatusOfProblem
     * @Create In Nov 5, 2009 By duxh
     * @return String
     */
    String modifyStatusOfProblem(Long problemId);
    
    /**
     * 通过事件A和B的父关系得到B和A的之关系
     * @Methods Name getEventRelationByEventRel
     * @Create In Sep 16, 2009 By guoxl
     * @param eventRel
     * @return EventRelation
     */
    EventRelation getEventRelationByEventRel(EventRelation eventRel);
    
       /**
     * 获取事件关联问题
     * @Methods Name getEventProblem
     * @Create In Sep 17, 2009 By lee
     * @param dataId	问题ID
     * @param eventId	关联事件ID
     * @param naem 		名称，模糊查询用 
     * @param pageNo 	
     * @param pageSize
     * @return Page
     */
    Page getEventProblem(String dataId,String eventId,String name, int pageNo, int pageSize,String status);
    /**
     * 新增问题关系
     * @Methods Name createEventRelation
     * @Create In Sep 17, 2009 By guoxl
     * @param currentEventId
     * @param parentEventId
     * @param currProblemId
     * @param parentProblemId
     * @param problemRelationTypeId void
     */
   void createEventRelation(Long currentEventId, Long parentEventId,Long currProblemId, Long parentProblemId,Long problemRelationTypeId);
   /**
    * 修改问题关系
    * @Methods Name modifyEventRelation
    * @Create In Sep 17, 2009 By guoxl
    * @param relId
    * @param currentEventId
    * @param parentEventId
    * @param currProblemId
    * @param parentProblemId
    * @param problemRelationTypeId void
    */
   void modifyEventRelation(String relId,Long currentEventId,Long parentEventId,Long currProblemId, Long parentProblemId,Long problemRelationTypeId);
  
   /**
    * 删除问题关系
    * @Methods Name removeProblemDoubleRel
    * @Create In Sep 17, 2009 By guoxl
    * @param relId void
    */
   void removeProblemDoubleRel(String relId);
   /**
    * 判断是否有已存在的问题关系
    * @Methods Name isExistProblem
    * @Create In Sep 17, 2009 By guoxl
    * @param currProblemId
    * @param parentProblemId
    * @return boolean
    */
   boolean isExistProblem(Long currProblemId, Long parentProblemId);
   /**
    * 
    * @Methods Name getEventRelByCurrEvent
    * @Create In Sep 17, 2009 By guoxl
    * @param event
    * @param pageNo
    * @param pageSize
    * @return Page
    */
   Page getProblemRelByCurrEvent(Problem problem,int pageNo, int pageSize);
   /**
    * 逻辑删除一组问题，将其状态置为已删除。如果问题已关联配置项，则不允许逻辑删除。
    * @Methods Name removeProblems
    * @Create In Nov 17, 2009 By duxh
    * @param problemsId
    * @return List<String> 关联配置项的问题编号,没有返回一个空集合。
    * @throws ServiceException
    */
   public List<String> removeProblems(Long[] problemsId) throws ServiceException;

}
