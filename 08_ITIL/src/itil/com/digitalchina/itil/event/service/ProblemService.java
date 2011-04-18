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
     * ͨ��userId��������ѽ����¼�������δ��������
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
     * ��������״̬��
     * @Methods Name modifyStatusOfProblem
     * @Create In Nov 5, 2009 By duxh
     * @return String
     */
    String modifyStatusOfProblem(Long problemId);
    
    /**
     * ͨ���¼�A��B�ĸ���ϵ�õ�B��A��֮��ϵ
     * @Methods Name getEventRelationByEventRel
     * @Create In Sep 16, 2009 By guoxl
     * @param eventRel
     * @return EventRelation
     */
    EventRelation getEventRelationByEventRel(EventRelation eventRel);
    
       /**
     * ��ȡ�¼���������
     * @Methods Name getEventProblem
     * @Create In Sep 17, 2009 By lee
     * @param dataId	����ID
     * @param eventId	�����¼�ID
     * @param naem 		���ƣ�ģ����ѯ�� 
     * @param pageNo 	
     * @param pageSize
     * @return Page
     */
    Page getEventProblem(String dataId,String eventId,String name, int pageNo, int pageSize,String status);
    /**
     * ���������ϵ
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
    * �޸������ϵ
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
    * ɾ�������ϵ
    * @Methods Name removeProblemDoubleRel
    * @Create In Sep 17, 2009 By guoxl
    * @param relId void
    */
   void removeProblemDoubleRel(String relId);
   /**
    * �ж��Ƿ����Ѵ��ڵ������ϵ
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
    * �߼�ɾ��һ�����⣬����״̬��Ϊ��ɾ������������ѹ���������������߼�ɾ����
    * @Methods Name removeProblems
    * @Create In Nov 17, 2009 By duxh
    * @param problemsId
    * @return List<String> �����������������,û�з���һ���ռ��ϡ�
    * @throws ServiceException
    */
   public List<String> removeProblems(Long[] problemsId) throws ServiceException;

}
