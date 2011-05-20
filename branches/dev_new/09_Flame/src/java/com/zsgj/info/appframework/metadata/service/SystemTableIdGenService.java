package com.zsgj.info.appframework.metadata.service;

import java.util.Map;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.framework.dao.support.Page;

/**
 * 系统主表编号生成器服务
 * @Class Name SystemTableIdGenService
 * @Author peixf
 * @Create In Mar 26, 2009
 */
public interface SystemTableIdGenService {
	
	/**
	 * 查询所有的主表编号生成器
	 * @Methods Name findAllSystemMainTableIdBuilder
	 * @Create In Mar 29, 2009 By sa
	 * @param requestParams
	 * @param pageNo
	 * @param pageSize
	 * @param orderBy
	 * @param isAsc
	 * @return Page
	 */
	Page findAllSystemMainTableIdBuilder(Map requestParams, int pageNo, int pageSize, String orderBy, boolean isAsc);
	/**
	 * 获取所有的编号生成器
	 */
	//List<SystemMainTableIdBuilder> findAllIdBuilders(SystemMainTable smt);
	
	/**
	 * 通过ID获取编号生成器
	 * @Methods Name findSystemMainTableIdBuilder
	 * @Create In Mar 27, 2009 By peixf
	 * @param id
	 * @return SystemMainTableIdBuilder
	 */
	//SystemMainTableIdBuilder findSystemMainTableIdBuilder(String id);
	
	/**
	 * 获取当前有效的编号生成器
	 * @Methods Name findIdBuilder
	 * @Create In Mar 27, 2009 By peixf
	 * @param smt
	 * @return SystemMainTableIdBuilder
	 */
	//SystemMainTableIdBuilder findCurrentIdBuilder(SystemMainTable smt);
	
	/**
	 * 通过底层配置的编号生成器规则，获取当前主表的下一个编号
	 * @Methods Name findCurrentIdByRule
	 * @Create In Mar 27, 2009 By peixf
	 * @param smt
	 * @return String
	 */
	String findCurrentIdByRule(SystemMainTable smt); //, String contextPath
}
