package com.zsgj.info.framework.rules.service;

import java.util.List;


/**
 * ������صķ���
 * @Class Name RuleService
 * @Author zhangys
 * @Create In Mar 11, 2008
 */
public interface RuleService {
	
	/**
	 * ִ�й���
	 * @Methods Name executeRules
	 * @Create In Mar 11, 2008 By itnova
	 * @param ruleFileName      ִ�еĹ����ļ���,��ӦapplicationContext-rule.xml��ruleSetFileMap��keyֵ
	 * @param fact		   		��������������ʵ
	 * @return Object
	 * @throws Exception 
	 */
	public Object executeRules(String ruleFileName, Object fact)throws Exception;
	
	/**
	 * ִ�й���
	 * @Methods Name executeRules
	 * @Create In Mar 11, 2008 By itnova
	 * @param ruleFileName      ִ�еĹ����ļ���,��ӦapplicationContext-rule.xml��ruleSetFileMap��keyֵ
	 * @param facts		   		��������������ʵ�ļ���
	 * @return List				���Ϲ������ʵ�ļ���
	 * @throws Exception 
	 */
	public List executeRules(String ruleFileName, List facts)throws Exception;
	
	/**
	 * ִ���빤������صĹ���
	 * @Methods Name executeWFRules
	 * @Create In Mar 11, 2008 By itnova
	 * @param ruleFileName      ִ�еĹ����ļ���,��ӦapplicationContext-rule.xml��ruleSetFileMap��keyֵ
	 * @param fact	   		    ��������������ʵ
	 * @throws Exception 
	 */
	public void executeWFRules(String ruleFileName,Object fact)throws Exception;
}


