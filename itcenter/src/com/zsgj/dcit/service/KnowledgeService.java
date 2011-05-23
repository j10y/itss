package com.zsgj.dcit.service;

import java.util.List;

import com.zsgj.dcit.entity.Knowledge;
import com.zsgj.dcit.entity.Notice;
import com.zsgj.dcit.util.Pagination;

public interface KnowledgeService {

	/**
	 * 获得操作手册及常见问题等栏目内的信息
	 * @Class Name getInfosService
	 * @Author zhangzy
	 * @Create In 31, 5, 2010
	 */
	public List getInfosService(int offset,int length,int infoLength,Long columnType );
	
	/**
	 * 获得操作手册及常见问题等栏目内的详细信息
	 * @Class Name updateAndGetContentInfosService
	 * @Author zhangzy
	 * @Create In 1, 6, 2010
	 */
	public Knowledge  updateAndGetContentInfosService(Long id);
	
	/**
	 * 获得操作手册及常见问题等栏目列表信息
	 * @Class Name getListInfoService
	 * @Author zhangzy
	 * @Create In 1 , 6, 2010
	 */	
	public Pagination getListInfoService(Pagination  pagination,int infoLength,Long columnType );
	/**
	 * 获得操作手册及常见问题等栏目查询信息
	 * @Class Name getSearchInfoService
	 * @Author zhangzy
	 * @Create In 1 , 6, 2010
	 */	
	public Pagination getSearchInfoService(Pagination  pagination,int infoLength,Long columnType,String keyValue );
}
