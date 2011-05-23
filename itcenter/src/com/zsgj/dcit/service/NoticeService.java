package com.zsgj.dcit.service;

import java.util.List;

import com.zsgj.dcit.entity.Notice;
import com.zsgj.dcit.util.Pagination;

public interface NoticeService {
	
	/**
	 * ���IT���桢ITС��ʿ��IT��ѵ����Ŀ�ڵ���Ϣ
	 * @Class Name getInfosService
	 * @Author zhangzy
	 * @Create In 31, 5, 2010
	 */
	public List<Notice> getInfosService(int offset,int length,int infoLength ,Long columnType);
	
	/**
	 * ���IT���桢ITС��ʿ��IT��ѵ����Ŀ�ڵ���ϸ��Ϣ
	 * @Class Name updateAndGetContentInfosService
	 * @Author zhangzy
	 * @Create In 1, 6, 2010
	 */
	public Notice updateAndGetContentInfosService(Long id);
	
	/**
	 * ���IT���桢ITС��ʿ��IT��ѵ����Ŀ�б���Ϣ
	 * @Class Name getListInfoService
	 * @Author zhangzy
	 * @Create In 1 , 6, 2010
	 */	
	public Pagination<Notice> getListInfoService(Pagination<Notice> pagination,int infoLength ,Long columnType);
	
	/**
	 * ���IT���桢ITС��ʿ��IT��ѵ����Ŀ������Ϣ
	 * @Class Name getSearchInfoService
	 * @Author zhangzy
	 * @Create In 1 , 6, 2010
	 */	
	public Pagination<Notice> getSearchInfoService(Pagination<Notice> pagination,int infoLength ,Long columnType,String keyValue);

}
