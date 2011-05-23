package com.zsgj.dcit.service;

import java.util.List;

import com.zsgj.dcit.entity.Knowledge;
import com.zsgj.dcit.entity.Notice;
import com.zsgj.dcit.util.Pagination;

public interface KnowledgeService {

	/**
	 * ��ò����ֲἰ�����������Ŀ�ڵ���Ϣ
	 * @Class Name getInfosService
	 * @Author zhangzy
	 * @Create In 31, 5, 2010
	 */
	public List getInfosService(int offset,int length,int infoLength,Long columnType );
	
	/**
	 * ��ò����ֲἰ�����������Ŀ�ڵ���ϸ��Ϣ
	 * @Class Name updateAndGetContentInfosService
	 * @Author zhangzy
	 * @Create In 1, 6, 2010
	 */
	public Knowledge  updateAndGetContentInfosService(Long id);
	
	/**
	 * ��ò����ֲἰ�����������Ŀ�б���Ϣ
	 * @Class Name getListInfoService
	 * @Author zhangzy
	 * @Create In 1 , 6, 2010
	 */	
	public Pagination getListInfoService(Pagination  pagination,int infoLength,Long columnType );
	/**
	 * ��ò����ֲἰ�����������Ŀ��ѯ��Ϣ
	 * @Class Name getSearchInfoService
	 * @Author zhangzy
	 * @Create In 1 , 6, 2010
	 */	
	public Pagination getSearchInfoService(Pagination  pagination,int infoLength,Long columnType,String keyValue );
}
