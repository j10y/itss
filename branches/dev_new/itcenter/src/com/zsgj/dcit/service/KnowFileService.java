package com.zsgj.dcit.service;

import java.util.List;

import com.zsgj.dcit.entity.KnowFile;
import com.zsgj.dcit.util.Pagination;

public interface KnowFileService {
	
	/**
	 * 获得IT管理制度、IT管理工作体系、IT服务报告、业务支持等栏目内的信息
	 * @Class Name getInfosService
	 * @Author zhangzy
	 * @Create In 31, 5, 2010
	 */
	public List<KnowFile> getInfosService(int offset,int length,int infoLength ,Long columnType);
	
	/**
	 * 获得IT管理制度、IT管理工作体系、IT服务报告、业务支持等栏目内的详细信息
	 * @Class Name updateAndGetContentInfosService
	 * @Author zhangzy
	 * @Create In 1, 6, 2010
	 */
	public KnowFile updateAndGetContentInfosService(Long id);
	
	/**
	 * 获得IT管理制度、IT管理工作体系、IT服务报告、业务支持等栏目列表信息
	 * @Class Name getListInfoService
	 * @Author zhangzy
	 * @Create In 1 , 6, 2010
	 */	
	public Pagination<KnowFile> getListInfoService(Pagination<KnowFile> pagination,int infoLength ,Long columnType);
	/**
	 * 获得IT管理制度、IT管理工作体系、IT服务报告、业务支持等栏目搜索信息
	 * @Class Name getSearchInfoService
	 * @Author zhangzy
	 * @Create In 1 , 6, 2010
	 */	
	public Pagination<KnowFile> getSearchInfoService(Pagination<KnowFile> pagination,int infoLength ,Long columnType,String keyValue);

}
