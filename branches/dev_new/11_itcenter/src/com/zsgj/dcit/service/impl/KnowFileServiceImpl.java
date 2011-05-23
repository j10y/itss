package com.zsgj.dcit.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.zsgj.dcit.dao.KnowFileDao;
import com.zsgj.dcit.entity.KnowFile;
import com.zsgj.dcit.service.KnowFileService;
import com.zsgj.dcit.util.Pagination;

public class KnowFileServiceImpl  implements KnowFileService {
	
	private KnowFileDao knowFileDao;


	public List<KnowFile> getInfosService(int offset,int length,int infoLength ,Long columnType) {
		List<KnowFile> list = knowFileDao.getInfos(offset, length, columnType);
		String strName = "";
		for(KnowFile knowFile:list){
			if(knowFile.getName().length()>infoLength){//如果信息长度大于规定长度，则截取
				strName = knowFile.getName().substring(0,infoLength);
				knowFile.setName(strName);
			}
		}
		return list;
	}	


	public KnowFile updateAndGetContentInfosService(Long id) {
		knowFileDao.updateReadTimes(id);		//计数器加一
		KnowFile knowFile = knowFileDao.getContentInfos(id);
		return knowFile;
	}

	public Pagination<KnowFile> getListInfoService(Pagination<KnowFile> pagination,int infoLength,Long columnType) {
		List<KnowFile> list =  knowFileDao.getListInfo( pagination, columnType).getData();
		String strName = "";
		for(KnowFile knowFile:list){
			if(knowFile.getName().length()>infoLength){//如果信息长度大于规定长度，则截取
				strName = knowFile.getName().substring(0,infoLength);
				knowFile.setName(strName);
			}
		}
		return pagination;
	}
	public Pagination<KnowFile> getSearchInfoService(Pagination<KnowFile> pagination,int infoLength,Long columnType,String keyValue) {
		List<KnowFile> list =  knowFileDao.getSearchInfo( pagination, columnType,keyValue).getData();
		String strName = "";
		for(KnowFile knowFile:list){
			if(knowFile.getName().length()>infoLength){//如果信息长度大于规定长度，则截取
				strName = knowFile.getName().substring(0,infoLength);
				knowFile.setName(strName);
			}
		}
		return pagination;
	}


	public KnowFileDao getKnowFileDao() {
		return knowFileDao;
	}


	public void setKnowFileDao(KnowFileDao knowFileDao) {
		this.knowFileDao = knowFileDao;
	}
	


}
