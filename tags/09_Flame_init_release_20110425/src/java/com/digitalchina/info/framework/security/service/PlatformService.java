package com.digitalchina.info.framework.security.service;

import java.util.Map;

import com.digitalchina.info.framework.security.entity.Platform;

/**
 * ƽ̨��ط���
 * @Class Name PlatformService
 * @Author lee
 * @Create In Jun 13, 2009
 */
public interface PlatformService {
	
	/**
	 * ����ƽ̨���Ʋ�ѯƽ̨
	 * @Methods Name findFlatformByName
	 * @Create In Jun 13, 2009 By lee
	 * @param name
	 * @param orderBy
	 * @param isAsc
	 * @param pageNo
	 * @param pageSize
	 * @return Map
	 */
	Map findPlatformByName(String name, String orderBy, boolean isAsc, int pageNo, int pageSize);
	
	/**
	 * ͨ��ID��ȡƽ̨
	 * @Methods Name findPlatformById
	 * @Create In Jun 13, 2009 By lee
	 * @param id
	 * @return Platform
	 */
	Platform findPlatformById(Long id);
	/**
	 * �����������Ʋ�����
	 * @Methods Name findRegionByName
	 * @Create In Jun 26, 2009 By sujs
	 * @param name
	 * @param orderBy
	 * @param isAsc
	 * @param pageNo
	 * @param pageSize
	 * @return Map
	 */
	Map findRegionByName(String name, String orderBy, boolean isAsc, int pageNo, int pageSize);
	/**
	 * ����ʡ���Ʋ�ʡ��
	 * @Methods Name findRegionByName
	 * @Create In Sep 7, 2009 By sujs
	 * @param name
	 * @param orderBy
	 * @param isAsc
	 * @param pageNo
	 * @param pageSize
	 * @return Map
	 */
	Map findProvinceByName(String name, String orderBy, boolean isAsc, int pageNo, int pageSize);
}
