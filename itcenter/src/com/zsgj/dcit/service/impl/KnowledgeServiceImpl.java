package com.zsgj.dcit.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.zsgj.dcit.dao.KnowledgeDao;
import com.zsgj.dcit.entity.Knowledge;
import com.zsgj.dcit.entity.Notice;
import com.zsgj.dcit.service.KnowledgeService;
import com.zsgj.dcit.util.Pagination;

public class KnowledgeServiceImpl implements KnowledgeService {
	
	private KnowledgeDao knowledgeDao;

	public List  getInfosService(int offset,int length,int infoLength ,Long columnType) {
		List list =  knowledgeDao.getInfos(offset, length, columnType);		
		String strSummary = "";		
		for(int i = 0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			Knowledge knowledge = (Knowledge) obj[0];
			if(knowledge.getSummary().length() > infoLength){//如果信息长度大于规定长度，则截取
				strSummary = knowledge.getSummary().substring(0,infoLength);
				knowledge.setSummary(strSummary);		
			}
		}		

		return list;
	}	


	public Knowledge  updateAndGetContentInfosService(Long id) {
		knowledgeDao.updateReadTimes(id);		//计数器加一
		Knowledge knowledge = knowledgeDao.getContentInfos(id);
		return knowledge;
	}

	public Pagination getListInfoService(Pagination  pagination,int infoLength,Long columnType) {
		List list =  knowledgeDao.getListInfo( pagination, columnType).getData();
		String strSummary = "";
		for(int i = 0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			Knowledge knowledge = (Knowledge) obj[0];
			if(knowledge.getSummary().length()>infoLength){//如果信息长度大于规定长度，则截取
				strSummary = knowledge.getSummary().substring(0,infoLength);
				knowledge.setSummary(strSummary);
			}
		}
		return pagination;
	}

	public KnowledgeDao getKnowledgeDao() {
		return knowledgeDao;
	}

	public void setKnowledgeDao(KnowledgeDao knowledgeDao) {
		this.knowledgeDao = knowledgeDao;
	}


	public Pagination getSearchInfoService(Pagination pagination,
			int infoLength, Long columnType, String keyValue) {
		
			List list =  knowledgeDao.getSearchInfo( pagination, columnType,keyValue).getData();
			String strSummary = "";
			for(int i = 0;i<list.size();i++){
				Object[] obj = (Object[]) list.get(i);
				Knowledge knowledge = (Knowledge) obj[0];
				if(knowledge.getSummary().length()>infoLength){//如果信息长度大于规定长度，则截取
					strSummary = knowledge.getSummary().substring(0,infoLength);
					knowledge.setSummary(strSummary);
				}
			}
			return pagination;
	}
	
	
	

}
