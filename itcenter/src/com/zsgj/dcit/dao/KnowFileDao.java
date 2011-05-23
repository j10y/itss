package com.zsgj.dcit.dao;

import java.util.List;

import com.zsgj.dcit.entity.KnowFile;
import com.zsgj.dcit.util.Pagination;

public interface KnowFileDao {

	/**
	 * ���IT�����ƶȡ�IT��������ϵ��IT���񱨸桢ҵ��֧�ֵ���Ŀ�ڵ���Ϣ
	 * @Class Name getInfos
	 * @Author zhangzy
	 * @Create In 31 5, 2010
	 */
	public List<KnowFile> getInfos(final int offset,final int length ,final Long columnType);
	
	/**
	 * ���IT�����ƶȡ�IT��������ϵ��IT���񱨸桢ҵ��֧�ֵ���Ŀ�ڵ���ϸ��Ϣ
	 * @Class Name getInfos
	 * @Author zhangzy
	 * @Create In 1 6, 2010
	 */
	public KnowFile  getContentInfos(Long id) ;
	
	
	/**
	 * ���IT�����ƶȡ�IT��������ϵ��IT���񱨸桢ҵ��֧�ֵ���Ŀ�б���Ϣ
	 * @Class Name getListInfo
	 * @Author zhangzy
	 * @Create In 1 , 6, 2010
	 */	
	public Pagination<KnowFile> getListInfo(final Pagination<KnowFile> pagination ,final Long columnType);
	
	
	/**
	 * ���IT�����ƶȡ�IT��������ϵ��IT���񱨸桢ҵ��֧�ֵ���Ŀ������Ϣ
	 * @Class Name getSearchInfo
	 * @Author zhangzy
	 * @Create In 1 , 6, 2010
	 */	
	public Pagination<KnowFile> getSearchInfo(final Pagination<KnowFile> pagination ,final Long columnType,final String keyValue);
	
	/**
	 *��IT�����ƶȡ�IT��������ϵ��IT���񱨸桢ҵ��֧�ֵ���Ŀ��Ϣ�����ֶμ�һ
	 * @Class Name updateReadTimes
	 * @Author zhangzy
	 * @Create In 3 , 6, 2010
	 */	
	public void updateReadTimes(Long id);
	
}
