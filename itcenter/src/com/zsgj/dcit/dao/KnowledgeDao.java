package com.zsgj.dcit.dao;

import java.util.List;

import com.zsgj.dcit.entity.Knowledge;
import com.zsgj.dcit.util.Pagination;

public interface KnowledgeDao {

	/**
	 * ��ò����ֲἰ�����������Ŀ�ڵ���Ϣ
	 * @Class Name getInfos
	 * @Author zhangzy
	 * @Create In 31 5, 2010
	 */
	public List getInfos(final int offset,final int length ,final Long columnType);
	
	/**
	 * ��ò����ֲἰ�����������Ŀ�ڵ���ϸ��Ϣ
	 * @Class Name getInfos
	 * @Author zhangzy
	 * @Create In 1 6, 2010
	 */
	public Knowledge getContentInfos(Long id) ;
	
	
	/**
	 * ��ò����ֲἰ�����������Ŀ�б���Ϣ
	 * @Class Name getListInfo
	 * @Author zhangzy
	 * @Create In 1 , 6, 2010
	 */	
	public Pagination getListInfo(final Pagination pagination ,final Long columnType);
	
	/**
	 * ��ò����ֲἰ�����������Ŀ��ѯ��Ϣ
	 * @Class Name geSearchInfo
	 * @Author zhangzy
	 * @Create In 1 , 6, 2010
	 */	
	public Pagination getSearchInfo(final Pagination pagination ,final Long columnType,final String keyValue);
	
	/**
	 *�Բ����ֲἰ�����������Ŀ��Ϣ�����ֶμ�һ
	 * @Class Name updateReadTimes
	 * @Author zhangzy
	 * @Create In 3 , 6, 2010
	 */	
	public void updateReadTimes(Long id);
}
