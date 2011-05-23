package com.zsgj.dcit.dao;

import java.util.List;

import com.zsgj.dcit.entity.Notice;
import com.zsgj.dcit.util.Pagination;

public interface NoticeDao {

	/**
	 * 获得IT公告、IT小贴士、IT培训等栏目内的信息
	 * @Class Name getInfos
	 * @Author zhangzy
	 * @Create In 31 5, 2010
	 */
	public List<Notice> getInfos(final int offset,final int length ,final Long columnType);
	
	/**
	 * 获得IT公告、IT小贴士、IT培训等栏目内的详细信息
	 * @Class Name getInfos
	 * @Author zhangzy
	 * @Create In 1 6, 2010
	 */
	public Notice getContentInfos(Long id) ;
	
	
	/**
	 * 获得IT公告、IT小贴士、IT培训等栏目列表信息
	 * @Class Name getListInfo
	 * @Author zhangzy
	 * @Create In 1 , 6, 2010
	 */	
	public Pagination<Notice> getListInfo(final Pagination<Notice> pagination ,final Long columnType);
	
	/**
	 *对IT公告、IT小贴士、IT培训等栏目信息计数字段加一
	 * @Class Name updateReadTimes
	 * @Author zhangzy
	 * @Create In 3 , 6, 2010
	 */	
	public void updateReadTimes(Long id);
	/**
	 * 获得IT公告、IT小贴士、IT培训等栏目搜索信息
	 * @Class Name getSearchInfo
	 * @Author zhangzy
	 * @Create In 1 , 6, 2010
	 */	
	public Pagination<Notice> getSearchInfo(final Pagination<Notice> pagination ,final Long columnType,final String keyValue);
	
}
