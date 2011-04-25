package com.digitalchina.itil.knowledge.service.impl;

import java.util.List;

import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.exception.ServiceException;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.service.BaseService;
import com.digitalchina.itil.actor.entity.SupportGroupEngineer;
import com.digitalchina.itil.actor.entity.SupportGroupServiceItem;
import com.digitalchina.itil.event.entity.ProblemStatus;
import com.digitalchina.itil.knowledge.dao.KnowledgeDao;
import com.digitalchina.itil.knowledge.entity.KnowProblemType;
import com.digitalchina.itil.knowledge.entity.Knowledge;
import com.digitalchina.itil.knowledge.service.KnowledgeService;
import com.digitalchina.itil.service.entity.ServiceItem;

public class KnowledgeServiceImpl extends BaseService implements KnowledgeService {
	private KnowledgeDao knowledgeDao;

	public Long findProcessIdOfLatestProcess(Long kId, Long kType) {
		return knowledgeDao.selectProcessIdOfLatestProcess(kId, kType);
	}

	public void modifySolutionUseTime(Long id) throws ServiceException {
		try {
			knowledgeDao.updateSolutionUseTime(id);
		} catch (Exception e) {
			throw new ServiceException();
		}
	}

	public Knowledge findKnowledgeByEventId(Long eventId) {
		try {
			return knowledgeDao.selectKnowledgeByEventId(eventId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public void setKnowledgeDao(KnowledgeDao knowledgeDao) {
		this.knowledgeDao = knowledgeDao;
	}
	public Page findKnowledgeBySiId(String serviceItemId,String[] summary,int pageNo, int pageSize) {
		try {
			return knowledgeDao.selectKnowledgeBySiId(serviceItemId,summary,pageNo,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public void removeProblemType(Long[] problemTypesId)throws ServiceException {
		knowledgeDao.updateProblmeTypesStatus(problemTypesId, KnowProblemType.DELETE_FALSE);
	}

	public Page findKnowledgeByEventTypeId(String eventtypeId,int pageNo, int pageSize) {
		try {
			return knowledgeDao.selectKnowledgeByEventType(eventtypeId,pageNo,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public List<SupportGroupEngineer> findSupportGroupByEngineer(
			UserInfo userInfo) {
		try {
			return knowledgeDao.selectSupportGroupByEngineer(userInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public String confirmServiceItemInSupportGroup(Long serviceItemId,
			UserInfo userInfo) {
		try {
			List<SupportGroupServiceItem> siList=knowledgeDao.selectServiceItemInSupportGroup(serviceItemId,userInfo);
			if(siList!=null&&siList.size()>=1){
				return "yes";
			}else{
				return "no";
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public Page findAllProblemType(UserInfo userInfo, String adminFlag,
			String name, String serviceItem, int start, int pageSize) {
		try {
			return knowledgeDao.selectAllProblemType(userInfo,adminFlag,name,serviceItem,start,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

}
