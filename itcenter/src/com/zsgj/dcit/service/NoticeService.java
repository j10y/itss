package com.zsgj.dcit.service;

import java.util.List;

import com.zsgj.dcit.entity.Notice;
import com.zsgj.dcit.util.Pagination;

public interface NoticeService {
	
	/**
	 * 获得IT公告、IT小贴士、IT培训等栏目内的信息
	 * @Class Name getInfosService
	 * @Author zhangzy
	 * @Create In 31, 5, 2010
	 */
	public List<Notice> getInfosService(int offset,int length,int infoLength ,Long columnType);
	
	/**
	 * 获得IT公告、IT小贴士、IT培训等栏目内的详细信息
	 * @Class Name updateAndGetContentInfosService
	 * @Author zhangzy
	 * @Create In 1, 6, 2010
	 */
	public Notice updateAndGetContentInfosService(Long id);
	
	/**
	 * 获得IT公告、IT小贴士、IT培训等栏目列表信息
	 * @Class Name getListInfoService
	 * @Author zhangzy
	 * @Create In 1 , 6, 2010
	 */	
	public Pagination<Notice> getListInfoService(Pagination<Notice> pagination,int infoLength ,Long columnType);
	
	/**
	 * 获得IT公告、IT小贴士、IT培训等栏目搜索信息
	 * @Class Name getSearchInfoService
	 * @Author zhangzy
	 * @Create In 1 , 6, 2010
	 */	
	public Pagination<Notice> getSearchInfoService(Pagination<Notice> pagination,int infoLength ,Long columnType,String keyValue);

}
