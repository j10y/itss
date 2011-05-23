package com.zsgj.dcit.service;

import java.util.List;

import com.zsgj.dcit.entity.KnowFile;
import com.zsgj.dcit.util.Pagination;

public interface KnowFileService {
	
	/**
	 * ���IT�����ƶȡ�IT��������ϵ��IT���񱨸桢ҵ��֧�ֵ���Ŀ�ڵ���Ϣ
	 * @Class Name getInfosService
	 * @Author zhangzy
	 * @Create In 31, 5, 2010
	 */
	public List<KnowFile> getInfosService(int offset,int length,int infoLength ,Long columnType);
	
	/**
	 * ���IT�����ƶȡ�IT��������ϵ��IT���񱨸桢ҵ��֧�ֵ���Ŀ�ڵ���ϸ��Ϣ
	 * @Class Name updateAndGetContentInfosService
	 * @Author zhangzy
	 * @Create In 1, 6, 2010
	 */
	public KnowFile updateAndGetContentInfosService(Long id);
	
	/**
	 * ���IT�����ƶȡ�IT��������ϵ��IT���񱨸桢ҵ��֧�ֵ���Ŀ�б���Ϣ
	 * @Class Name getListInfoService
	 * @Author zhangzy
	 * @Create In 1 , 6, 2010
	 */	
	public Pagination<KnowFile> getListInfoService(Pagination<KnowFile> pagination,int infoLength ,Long columnType);
	/**
	 * ���IT�����ƶȡ�IT��������ϵ��IT���񱨸桢ҵ��֧�ֵ���Ŀ������Ϣ
	 * @Class Name getSearchInfoService
	 * @Author zhangzy
	 * @Create In 1 , 6, 2010
	 */	
	public Pagination<KnowFile> getSearchInfoService(Pagination<KnowFile> pagination,int infoLength ,Long columnType,String keyValue);

}
