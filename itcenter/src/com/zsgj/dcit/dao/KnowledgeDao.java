package com.zsgj.dcit.dao;

import java.util.List;

import com.zsgj.dcit.entity.Knowledge;
import com.zsgj.dcit.util.Pagination;

public interface KnowledgeDao {

	/**
	 * 获得操作手册及常见问题等栏目内的信息
	 * @Class Name getInfos
	 * @Author zhangzy
	 * @Create In 31 5, 2010
	 */
	public List getInfos(final int offset,final int length ,final Long columnType);
	
	/**
	 * 获得操作手册及常见问题等栏目内的详细信息
	 * @Class Name getInfos
	 * @Author zhangzy
	 * @Create In 1 6, 2010
	 */
	public Knowledge getContentInfos(Long id) ;
	
	
	/**
	 * 获得操作手册及常见问题等栏目列表信息
	 * @Class Name getListInfo
	 * @Author zhangzy
	 * @Create In 1 , 6, 2010
	 */	
	public Pagination getListInfo(final Pagination pagination ,final Long columnType);
	
	/**
	 * 获得操作手册及常见问题等栏目查询信息
	 * @Class Name geSearchInfo
	 * @Author zhangzy
	 * @Create In 1 , 6, 2010
	 */	
	public Pagination getSearchInfo(final Pagination pagination ,final Long columnType,final String keyValue);
	
	/**
	 *对操作手册及常见问题等栏目信息计数字段加一
	 * @Class Name updateReadTimes
	 * @Author zhangzy
	 * @Create In 3 , 6, 2010
	 */	
	public void updateReadTimes(Long id);
}
