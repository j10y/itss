package com.zsgj.dcit.dao;

import java.util.List;

import com.zsgj.dcit.entity.KnowFile;
import com.zsgj.dcit.util.Pagination;

public interface KnowFileDao {

	/**
	 * 获得IT管理制度、IT管理工作体系、IT服务报告、业务支持等栏目内的信息
	 * @Class Name getInfos
	 * @Author zhangzy
	 * @Create In 31 5, 2010
	 */
	public List<KnowFile> getInfos(final int offset,final int length ,final Long columnType);
	
	/**
	 * 获得IT管理制度、IT管理工作体系、IT服务报告、业务支持等栏目内的详细信息
	 * @Class Name getInfos
	 * @Author zhangzy
	 * @Create In 1 6, 2010
	 */
	public KnowFile  getContentInfos(Long id) ;
	
	
	/**
	 * 获得IT管理制度、IT管理工作体系、IT服务报告、业务支持等栏目列表信息
	 * @Class Name getListInfo
	 * @Author zhangzy
	 * @Create In 1 , 6, 2010
	 */	
	public Pagination<KnowFile> getListInfo(final Pagination<KnowFile> pagination ,final Long columnType);
	
	
	/**
	 * 获得IT管理制度、IT管理工作体系、IT服务报告、业务支持等栏目搜索信息
	 * @Class Name getSearchInfo
	 * @Author zhangzy
	 * @Create In 1 , 6, 2010
	 */	
	public Pagination<KnowFile> getSearchInfo(final Pagination<KnowFile> pagination ,final Long columnType,final String keyValue);
	
	/**
	 *对IT管理制度、IT管理工作体系、IT服务报告、业务支持等栏目信息计数字段加一
	 * @Class Name updateReadTimes
	 * @Author zhangzy
	 * @Create In 3 , 6, 2010
	 */	
	public void updateReadTimes(Long id);
	
}
