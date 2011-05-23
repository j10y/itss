package com.zsgj.dcit.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.zsgj.dcit.dao.NoticeDao;
import com.zsgj.dcit.entity.Notice;
import com.zsgj.dcit.service.NoticeService;
import com.zsgj.dcit.util.Pagination;

public class NoticeServiceImpl  implements NoticeService {
	
	private NoticeDao noticeDao;


	public List<Notice> getInfosService(int offset,int length,int infoLength ,Long columnType) {
		List<Notice> list =  noticeDao.getInfos(offset, length, columnType);
		String strTitle = "";
		for(Notice notice:list){
			if(notice.getTitle().length()>infoLength){//如果信息长度大于规定长度，则截取
				if("1".equals(columnType.toString())){
					strTitle = notice.getTitle().substring(0,infoLength)+"．．．";
				}else{
					strTitle = notice.getTitle().substring(0,infoLength);
				}
				notice.setTitle(strTitle);
			}
		}
		return list;
	}	

	public Notice updateAndGetContentInfosService(Long id) {
		noticeDao.updateReadTimes(id);		//计数器加一
		Notice notice = noticeDao.getContentInfos(id);
		return notice;
	}
	
	public Pagination<Notice> getListInfoService(Pagination<Notice> pagination,int infoLength,Long columnType) {
		List<Notice> list =  noticeDao.getListInfo( pagination, columnType).getData();
		String strTitle = "";
		for(Notice notice:list){
			if(notice.getTitle().length()>infoLength){//如果信息长度大于规定长度，则截取
				strTitle = notice.getTitle().substring(0,infoLength);
				notice.setTitle(strTitle);
			}
		}
		return pagination;
	}
	
	public Pagination<Notice> getSearchInfoService(
			Pagination<Notice> pagination, int infoLength, Long columnType,
			String keyValue) {
		
		List<Notice> list =  noticeDao.getSearchInfo(pagination, columnType, keyValue).getData();
		String strTitle = "";
		for(Notice notice:list){
			if(notice.getTitle().length()>infoLength){//如果信息长度大于规定长度，则截取
				strTitle = notice.getTitle().substring(0,infoLength);
				notice.setTitle(strTitle);
			}
		}
		return pagination;
	}

	
	public NoticeDao getNoticeDao() {
		return noticeDao;
	}

	public void setNoticeDao(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}

}
