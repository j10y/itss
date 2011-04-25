package com.digitalchina.info.framework.security.service;

import java.util.Map;

import com.digitalchina.info.framework.security.entity.Platform;

/**
 * 平台相关服务
 * @Class Name PlatformService
 * @Author lee
 * @Create In Jun 13, 2009
 */
public interface PlatformService {
	
	/**
	 * 根据平台名称查询平台
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
	 * 通过ID获取平台
	 * @Methods Name findPlatformById
	 * @Create In Jun 13, 2009 By lee
	 * @param id
	 * @return Platform
	 */
	Platform findPlatformById(Long id);
	/**
	 * 根据区域名称查区域
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
	 * 根据省名称查省份
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
