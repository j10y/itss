package com.digitalchina.itil.event.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.exception.ServiceException;
import com.digitalchina.info.framework.service.BaseService;
import com.digitalchina.itil.config.entity.CIBatchModifyShip;
import com.digitalchina.itil.event.dao.ProblemDao;
import com.digitalchina.itil.event.entity.Event;
import com.digitalchina.itil.event.entity.EventRelation;
import com.digitalchina.itil.event.entity.Problem;
import com.digitalchina.itil.event.entity.ProblemRelation;
import com.digitalchina.itil.event.entity.ProblemRelationType;
import com.digitalchina.itil.event.entity.ProblemStatus;
import com.digitalchina.itil.event.service.ProblemService;


public class ProblemServiceImpl extends BaseService implements ProblemService {
	private ProblemDao problemDao;
	public void setProblemDao(ProblemDao problemDao) {
		this.problemDao = problemDao;
	}

	public Page findAllNotEndProblemsOfAllEndEvents(Long userId, int start, int pageSize, String problemName) {
		try {
			return problemDao.selectAllNotEndProblemsOfAllEndEvents(userId, start, pageSize, problemName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@SuppressWarnings("unchecked")
	public Page findProblemsEvents(Event event,int start, int pageSize) {
		try {
			return problemDao.selectProblemsEvents(event, start, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public String modifyStatusOfProblem(Long problemId) {
		ProblemStatus endProblemStatus = (ProblemStatus) findUnique(ProblemStatus.class,"keyword", "finish");
		Problem problem = (Problem) findUnique(Problem.class,"id",problemId);
		//add by huzh in 2010/03/11 ----- start 配置项批量变更审批未通过时，不能结束问题
    	List<CIBatchModifyShip> processinglist=problemDao.selectAllNoPassedCIBatchModifyShipByProblem(problemId);
    	if(processinglist!=null && processinglist.size()!=0){	
    		return "cibmNOPass";  
	    }
		//add by huzh in 2010/03/11 ----- end 
		List<Problem> problemList = new ArrayList<Problem>();
		//modified by huzh in 2010/02/04 start,递归找出与该problem相关的所有同一问题和子问题
		if(problem != null){
			//problemList = problemDao.selectProblemByCurrProblem(problem);
			problemList = findAllSameAndChildProblems(null,problem);
		}
		//modified by huzh in 2010/02/04 end
		if(problemList != null && problemList.size()>0){
			for(Problem problemObj:problemList){
				if(!"finish".equals(problemObj.getStatus().getKeyword())){
					problemObj.setClosedDate(new Date());
					problemObj.setStatus(endProblemStatus);
					super.save(problemObj);
				}
			}
		}
		problem.setClosedDate(new Date());
		problem.setStatus(endProblemStatus);
		super.save(problem);
		return "saved";
	}
	//add by huzh in 2010/02/04 --- start ,用于递归查找所有的同一问题和子问题
	private List<Problem> findAllSameAndChildProblems(Problem sameObj,Problem problem){
		List<Problem> problemList = new ArrayList<Problem>();
		List<Problem> problemList1 = new ArrayList<Problem>();
		problemList = problemDao.selectProblemByCurrProblem(problem);
        if(problemList != null && problemList.size() > 0){
    		for(int i=0;i<problemList.size();i++){
    			Problem pro=problemList.get(i);
			if(pro == sameObj){
				problemList.remove(i);  //注意：移除重复的,否则会出现死循环
				continue;
			}
			//add by huzh in 2010/03/11 ----- start 配置项批量变更审批未通过时，不能结束问题
			List<CIBatchModifyShip> ciBMShiplist= find(CIBatchModifyShip.class,"problem",pro);
		    if(ciBMShiplist != null && ciBMShiplist.size()!=0){
		    	List<CIBatchModifyShip> nopasslist=problemDao.selectAllNoPassedCIBatchModifyShipByProblem(pro.getId());
		    	if(nopasslist.size()!=0){
		    	  problemList.remove(i);  
		      }
		    }	
			//add by huzh in 2010/03/11 ----- end 
				
			}
        	problemList1.addAll(problemList);
        	for(int i =0;i<problemList.size();i++){
				Problem problemObj=problemList.get(i);
				List<Problem> subList = findAllSameAndChildProblems(problem,problemObj);
					problemList1.addAll(subList);
				}
			}
        return problemList1;
	}
	//add by huzh end

	public EventRelation getEventRelationByEventRel(EventRelation eventRel) {
		try {
			return problemDao.selectEventRelationByEventRel(eventRel);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
	
	public boolean isExistProblem(Long currProblemId, Long parentProblemId) {
		try {
			return problemDao.selectIsExistProblem(currProblemId, parentProblemId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public Page getProblemRelByCurrEvent(Problem problem, int pageNo,
			int pageSize) {
		try {
			return problemDao.selectProblemRelByCurrEvent(problem, pageNo, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
	public void createEventRelation(Long currentEventId, Long parentEventId,
			Long currProblemId, Long parentProblemId, Long problemRelationTypeId) {
		try {
			//当前问题
			Problem currProblem = new Problem();
			currProblem.setId(currProblemId);
			//关联问题
			Problem parentProblem =new Problem();
			parentProblem.setId(parentProblemId);
			//当前事件
			Event currentEvent = new Event();
			currentEvent.setId(currentEventId);
			//关联事件
			Event parentEvent =new Event();
			parentEvent.setId(parentEventId);
			//选择的事件关系类型
			ProblemRelationType problemRelationType = (ProblemRelationType) findUnique(ProblemRelationType.class, "id",problemRelationTypeId);
			
			ProblemRelation problemRelation  = new ProblemRelation();//新建
			problemRelation.setParentEvent(parentEvent);
			problemRelation.setEvent(currentEvent);
			problemRelation.setParentProblem(parentProblem);
			problemRelation.setProblem(currProblem);
			problemRelation.setProblemRelationType(problemRelationType);
			this.save(problemRelation);
			
			ProblemRelation relRelation  = new ProblemRelation();//新建
			relRelation.setEvent(parentEvent);
			relRelation.setParentEvent(currentEvent);
			relRelation.setProblem(parentProblem);
			relRelation.setParentProblem(currProblem);
			if(problemRelationType.getTypeFlag().equals(ProblemRelationType.SAME)||problemRelationType.getTypeFlag().equals(ProblemRelationType.RELATION)){
				relRelation.setProblemRelationType(problemRelationType);
			}else if(problemRelationType.getTypeFlag().equals(ProblemRelationType.PARENT)){
				relRelation.setProblemRelationType((ProblemRelationType) this.findUnique(ProblemRelationType.class, "typeFlag", ProblemRelationType.CHILD));
			}else if(problemRelationType.getTypeFlag().equals(ProblemRelationType.CHILD)){
				relRelation.setProblemRelationType((ProblemRelationType) findUnique(ProblemRelationType.class, "typeFlag", ProblemRelationType.PARENT));
			}
			this.save(relRelation);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
	public Page getEventProblem(String dataId, String eventId, String name, int pageNo,int pageSize,String status) {
		try {
			return problemDao.selectEventProblem(dataId, eventId, name, pageNo, pageSize,status);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
	
	public void removeProblemDoubleRel(String relId){
		try {
			problemDao.deleteProblemDoubleRel(relId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
	
	public void modifyEventRelation(String relId, Long currentEventId,
			Long parentEventId, Long currProblemId, Long parentProblemId,
			Long problemRelationTypeId) {
		problemDao.deleteProblemDoubleRel(relId);//删除原来的关系
		//当前问题
		Problem currProblem = new Problem();
		currProblem.setId(currProblemId);
		//关联问题
		Problem parentProblem =new Problem();
		parentProblem.setId(parentProblemId);
		//当前事件
		Event currentEvent = new Event();
		currentEvent.setId(currentEventId);
		//关联事件
		Event parentEvent =new Event();
		parentEvent.setId(parentEventId);
		//选择的事件关系类型
		ProblemRelationType problemRelationType = (ProblemRelationType) findUnique(ProblemRelationType.class, "id",problemRelationTypeId);
		
		ProblemRelation problemRelation  = new ProblemRelation();//新建
		problemRelation.setParentEvent(parentEvent);
		problemRelation.setEvent(currentEvent);
		problemRelation.setParentProblem(parentProblem);
		problemRelation.setProblem(currProblem);
		problemRelation.setProblemRelationType(problemRelationType);
		this.save(problemRelation);
		
		ProblemRelation relRelation  = new ProblemRelation();//新建
		relRelation.setEvent(parentEvent);
		relRelation.setParentEvent(currentEvent);
		relRelation.setProblem(parentProblem);
		relRelation.setParentProblem(currProblem);
		if(problemRelationType.getTypeFlag().equals(ProblemRelationType.SAME)||problemRelationType.getTypeFlag().equals(ProblemRelationType.RELATION)){
			relRelation.setProblemRelationType(problemRelationType);
		}else if(problemRelationType.getTypeFlag().equals(ProblemRelationType.PARENT)){
			relRelation.setProblemRelationType((ProblemRelationType) this.findUnique(ProblemRelationType.class, "typeFlag", ProblemRelationType.CHILD));
		}else if(problemRelationType.getTypeFlag().equals(ProblemRelationType.CHILD)){
			relRelation.setProblemRelationType((ProblemRelationType) findUnique(ProblemRelationType.class, "typeFlag", ProblemRelationType.PARENT));
		}
		this.save(relRelation);
	}

	public List<String> removeProblems(Long[] problemsId) throws ServiceException {
		List<String> problemCisns=problemDao.selectWhetherHasConfigItem(problemsId);
		if(problemCisns.isEmpty())
			problemDao.updateProblemsStatus(problemsId, ProblemStatus.KEYWORD_DELETE);
		return problemCisns;
	}
	
}
