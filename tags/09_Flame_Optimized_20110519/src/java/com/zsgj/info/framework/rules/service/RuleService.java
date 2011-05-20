package com.zsgj.info.framework.rules.service;

import java.util.List;


/**
 * 规则相关的服务
 * @Class Name RuleService
 * @Author zhangys
 * @Create In Mar 11, 2008
 */
public interface RuleService {
	
	/**
	 * 执行规则
	 * @Methods Name executeRules
	 * @Create In Mar 11, 2008 By itnova
	 * @param ruleFileName      执行的规则文件名,对应applicationContext-rule.xml中ruleSetFileMap的key值
	 * @param fact		   		参与规则运算的事实
	 * @return Object
	 * @throws Exception 
	 */
	public Object executeRules(String ruleFileName, Object fact)throws Exception;
	
	/**
	 * 执行规则
	 * @Methods Name executeRules
	 * @Create In Mar 11, 2008 By itnova
	 * @param ruleFileName      执行的规则文件名,对应applicationContext-rule.xml中ruleSetFileMap的key值
	 * @param facts		   		参与规则运算的事实的集合
	 * @return List				符合规则的事实的集合
	 * @throws Exception 
	 */
	public List executeRules(String ruleFileName, List facts)throws Exception;
	
	/**
	 * 执行与工作流相关的规则
	 * @Methods Name executeWFRules
	 * @Create In Mar 11, 2008 By itnova
	 * @param ruleFileName      执行的规则文件名,对应applicationContext-rule.xml中ruleSetFileMap的key值
	 * @param fact	   		    参与规则运算的事实
	 * @throws Exception 
	 */
	public void executeWFRules(String ruleFileName,Object fact)throws Exception;
}


