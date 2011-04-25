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
 * ��������ʵ����
 * 
 * @Class Name DroolsTemplate
 * @Author itnova
 * @Create In Mar 11, 2008
 */
public class DroolsTemplate implements RuleService {

	private Map ruleFileMap;

	/**
	 * ִ�й���
	 * 
	 * @Methods Name executeRules
	 * @Create In Mar 11, 2008 By itnova
	 * @param ruleFileName    ִ�еĹ����ļ���,��ӦapplicationContext-rule.xml��ruleSetFileMap��keyֵ��
	 * @param fact            ��������������ʵ
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
	 * ���ع����ļ�
	 * 
	 * @Methods Name loadRuleFile
	 * @Create In Mar 11, 2008 By itnova
	 * @param filePath      �����ļ��ľ���·��,��ӦapplicationContext-rule.xml��ruleSetFileMap��valueֵ
	 * @param  builder     PackageBuilder�����ʵ��
	 * @throws Exception
	 */
	public void loadRuleFile(String ruleFileName, PackageBuilder builder)
			throws Exception {

		// System.out.println("ruleName :"+ruleName);
		String filePath = (String) ruleFileMap.get(ruleFileName);
		if (filePath == null) {
			throw new Exception("ͨ��xml���������ļ� ,�ļ�" + ruleFileName + ".drlδ�ҵ�");
		}
		try {
			InputStream is = this.getClass().getResourceAsStream(filePath);
			if (is == null) {
				throw new Exception("ͨ��xml���������ļ� ,�ļ�" + ruleFileName + ".drlδ�ҵ�");
			}
			InputStreamReader isReader = new InputStreamReader(is);
			builder.addPackageFromDrl(isReader);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * ִ�й���
	 * 
	 * @Methods Name executeRules
	 * @Create In Mar 11, 2008 By itnova
	 * @param ruleFileName    ִ�еĹ����ļ���,��ӦapplicationContext-rule.xml��ruleSetFileMap��keyֵ��
	 * @param facts           ��������������ʵ�ļ���
	 * @return List			  ���Ϲ������ʵ�ļ���
	 * @throws Exception
	 */
	public List executeRules(String ruleFileName, List facts) throws Exception {
		
		List newFacts = new ArrayList(); //��ž�������ƥ������ʵ
		List handles = new ArrayList();  //����ƥ��ʱ,���ÿ����ʵ�ľ��
		
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
		//��þ�������������ʵ�ļ���
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
	 * ִ���빤������صĹ���
	 * @Methods Name executeWFRules
	 * @Create In Mar 11, 2008 By itnova
	 * @param ruleFileName      ִ�еĹ����ļ���,��ӦapplicationContext-rule.xml��ruleSetFileMap��keyֵ
	 * @param fact	   		    ��������������ʵ
	 * @throws Exception 
	 */
	public void executeWFRules(String ruleFileName, Object fact)
			throws Exception {
		
		
		// TODO Auto-generated method stub
		
	}

}
