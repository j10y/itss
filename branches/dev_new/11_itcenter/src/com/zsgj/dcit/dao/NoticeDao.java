package com.zsgj.dcit.dao;

import java.util.List;

import com.zsgj.dcit.entity.Notice;
import com.zsgj.dcit.util.Pagination;

public interface NoticeDao {

	/**
	 * ���IT���桢ITС��ʿ��IT��ѵ����Ŀ�ڵ���Ϣ
	 * @Class Name getInfos
	 * @Author zhangzy
	 * @Create In 31 5, 2010
	 */
	public List<Notice> getInfos(final int offset,final int length ,final Long columnType);
	
	/**
	 * ���IT���桢ITС��ʿ��IT��ѵ����Ŀ�ڵ���ϸ��Ϣ
	 * @Class Name getInfos
	 * @Author zhangzy
	 * @Create In 1 6, 2010
	 */
	public Notice getContentInfos(Long id) ;
	
	
	/**
	 * ���IT���桢ITС��ʿ��IT��ѵ����Ŀ�б���Ϣ
	 * @Class Name getListInfo
	 * @Author zhangzy
	 * @Create In 1 , 6, 2010
	 */	
	public Pagination<Notice> getListInfo(final Pagination<Notice> pagination ,final Long columnType);
	
	/**
	 *��IT���桢ITС��ʿ��IT��ѵ����Ŀ��Ϣ�����ֶμ�һ
	 * @Class Name updateReadTimes
	 * @Author zhangzy
	 * @Create In 3 , 6, 2010
	 */	
	public void updateReadTimes(Long id);
	/**
	 * ���IT���桢ITС��ʿ��IT��ѵ����Ŀ������Ϣ
	 * @Class Name getSearchInfo
	 * @Author zhangzy
	 * @Create In 1 , 6, 2010
	 */	
	public Pagination<Notice> getSearchInfo(final Pagination<Notice> pagination ,final Long columnType,final String keyValue);
	
}
