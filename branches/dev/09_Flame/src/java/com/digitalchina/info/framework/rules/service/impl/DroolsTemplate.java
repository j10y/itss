package com.digitalchina.info.framework.rules.service.impl;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.drools.FactHandle;
import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatefulSession;
import org.drools.compiler.PackageBuilder;

import com.digitalchina.info.framework.rules.service.RuleService;

/**
 * 规则服务的实现类
 * 
 * @Class Name DroolsTemplate
 * @Author itnova
 * @Create In Mar 11, 2008
 */
public class DroolsTemplate implements RuleService {

	private Map ruleFileMap;

	/**
	 * 执行规则
	 * 
	 * @Methods Name executeRules
	 * @Create In Mar 11, 2008 By itnova
	 * @param ruleFileName    执行的规则文件名,对应applicationContext-rule.xml中ruleSetFileMap的key值。
	 * @param fact            参与规则运算的事实
	 * @return Object
	 * @throws Exception
	 */
	public Object executeRules(String ruleFileName, Object fact) throws Exception {

		// String filePath = (String) ruleSetFileMap.get(ruleSetName);
		PackageBuilder builder = new PackageBuilder();
		loadRuleFile(ruleFileName, builder);
		RuleBase ruleBase = RuleBaseFactory.newRuleBase();
		ruleBase.addPackage(builder.getPackage());
		StatefulSession session = ruleBase.newStatefulSession();
		FactHandle factHandle = session.insert(fact);
		session.fireAllRules();
		return session.getObject(factHandle);

	}

	/**
	 * 加载规则文件
	 * 
	 * @Methods Name loadRuleFile
	 * @Create In Mar 11, 2008 By itnova
	 * @param filePath      规则文件的绝对路径,对应applicationContext-rule.xml中ruleSetFileMap的value值
	 * @param  builder     PackageBuilder对象的实例
	 * @throws Exception
	 */
	public void loadRuleFile(String ruleFileName, PackageBuilder builder)
			throws Exception {

		// System.out.println("ruleName :"+ruleName);
		String filePath = (String) ruleFileMap.get(ruleFileName);
		if (filePath == null) {
			throw new Exception("通过xml规则配置文件 ,文件" + ruleFileName + ".drl未找到");
		}
		try {
			InputStream is = this.getClass().getResourceAsStream(filePath);
			if (is == null) {
				throw new Exception("通过xml规则配置文件 ,文件" + ruleFileName + ".drl未找到");
			}
			InputStreamReader isReader = new InputStreamReader(is);
			builder.addPackageFromDrl(isReader);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 执行规则
	 * 
	 * @Methods Name executeRules
	 * @Create In Mar 11, 2008 By itnova
	 * @param ruleFileName    执行的规则文件名,对应applicationContext-rule.xml中ruleSetFileMap的key值。
	 * @param facts           参与规则运算的事实的集合
	 * @return List			  符合规则的事实的集合
	 * @throws Exception
	 */
	public List executeRules(String ruleFileName, List facts) throws Exception {
		
		List newFacts = new ArrayList(); //存放经过规则匹配后的事实
		List handles = new ArrayList();  //规则匹配时,存放每个事实的句柄
		
		PackageBuilder builder = new PackageBuilder();
		loadRuleFile(ruleFileName, builder);
		RuleBase ruleBase = RuleBaseFactory.newRuleBase();
		
		ruleBase.addPackage(builder.getPackage());
		StatefulSession session = ruleBase.newStatefulSession();
		for (Iterator objectIter = facts.iterator(); objectIter.hasNext();) {
			FactHandle factHandle = session.insert(objectIter.next());
			handles.add(factHandle);
		}
		session.fireAllRules();
		//获得经过规则处理后的事实的集合
		Iterator iter = handles.iterator();
		while(iter.hasNext()){
			FactHandle handle = (FactHandle) iter.next();
			Object obj = session.getObject(handle);
			newFacts.add(obj);
		}
		return newFacts;
	}
	/**
	 * @Param Map	ruleSetFileMap to set
	 *            
	 */
	public void setRuleFileMap(Map ruleFileMap) {
		this.ruleFileMap = ruleFileMap;
	}
	
	/**
	 * 执行与工作流相关的规则
	 * @Methods Name executeWFRules
	 * @Create In Mar 11, 2008 By itnova
	 * @param ruleFileName      执行的规则文件名,对应applicationContext-rule.xml中ruleSetFileMap的key值
	 * @param fact	   		    参与规则运算的事实
	 * @throws Exception 
	 */
	public void executeWFRules(String ruleFileName, Object fact)
			throws Exception {
		
		
		// TODO Auto-generated method stub
		
	}

}
