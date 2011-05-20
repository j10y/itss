package com.zsgj.info.appframework.metadata.service;

import java.util.Map;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.framework.dao.support.Page;

/**
 * ϵͳ����������������
 * @Class Name SystemTableIdGenService
 * @Author peixf
 * @Create In Mar 26, 2009
 */
public interface SystemTableIdGenService {
	
	/**
	 * ��ѯ���е�������������
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
	 * ��ȡ���еı��������
	 */
	//List<SystemMainTableIdBuilder> findAllIdBuilders(SystemMainTable smt);
	
	/**
	 * ͨ��ID��ȡ���������
	 * @Methods Name findSystemMainTableIdBuilder
	 * @Create In Mar 27, 2009 By peixf
	 * @param id
	 * @return SystemMainTableIdBuilder
	 */
	//SystemMainTableIdBuilder findSystemMainTableIdBuilder(String id);
	
	/**
	 * ��ȡ��ǰ��Ч�ı��������
	 * @Methods Name findIdBuilder
	 * @Create In Mar 27, 2009 By peixf
	 * @param smt
	 * @return SystemMainTableIdBuilder
	 */
	//SystemMainTableIdBuilder findCurrentIdBuilder(SystemMainTable smt);
	
	/**
	 * ͨ���ײ����õı�����������򣬻�ȡ��ǰ�������һ�����
	 * @Methods Name findCurrentIdByRule
	 * @Create In Mar 27, 2009 By peixf
	 * @param smt
	 * @return String
	 */
	String findCurrentIdByRule(SystemMainTable smt); //, String contextPath
}
