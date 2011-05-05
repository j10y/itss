package com.zsgj.info.appframework.metadata.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableIdBuilder;
import com.zsgj.info.appframework.metadata.service.SystemTableIdGenService;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.util.idgen.IdGenRuleMethodHelper;

public class SystemTableIdGenServiceImpl extends BaseDao implements
		SystemTableIdGenService {
	static final String FSP = System.getProperty("file.separator");
	static final String LSP = System.getProperty("line.separator");
	
	public Page findAllSystemMainTableIdBuilder(Map requestParams, int pageNo,
			int pageSize, String orderBy, boolean isAsc) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<SystemMainTableIdBuilder> findAllIdBuilders(SystemMainTable smt) {
		
		return null;
	}

	public SystemMainTableIdBuilder findCurrentIdBuilder(SystemMainTable smt) {
		// TODO Auto-generated method stub
		return null;
	}

	
	private SystemMainTableIdBuilder getIdBuilder(SystemMainTable smt){
		SystemMainTableIdBuilder result = null;
		Criteria c = super.getCriteria(SystemMainTableIdBuilder.class);
		c.add(Restrictions.eq("systemMainTable", smt));
		c.add(Restrictions.eq("deployFlag", Integer.valueOf(1)));
		List<SystemMainTableIdBuilder> list = c.list();
		if(!list.isEmpty()){
			result = list.get(0);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public String findCurrentIdByRule(SystemMainTable smt){//, String contextPath
	
		//begin
		String nextValue = null;
		SystemMainTableIdBuilder smtIdBuilder = this.getIdBuilder(smt);
		if(smtIdBuilder!=null){
			String prefix = smtIdBuilder.getPrefix(); //C014-
			Long length = smtIdBuilder.getLength(); 
			//int numberLength = length.intValue()-prefix.length();
			String ruleFile = smtIdBuilder.getRuleFileName();
			String latestValue = smtIdBuilder.getLatestValue();
			Department dept = smtIdBuilder.getDepartment();
										
			Map map = new HashedMap();
			map.put("ruleName", "systemTableIdGen");
			map.put("prefix", prefix);
			map.put("length", String.valueOf(length));
			map.put("latestValue", latestValue);
			//map.put("latestNumber", latestNumber);
			map.put("dept", dept);
			
			if(ruleFile==null){
				ruleFile = "/com/zsgj/info/framework/util/idgen/systemTableIdGen.drl";
			}
			nextValue = IdGenRuleMethodHelper.executeRule(ruleFile, map);
			smtIdBuilder.setLatestValue(nextValue);
			
		}
		//end

		return nextValue;
	}

	public SystemMainTableIdBuilder findSystemMainTableIdBuilder(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Class getClass(String className) {
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			System.out.print("类名：" + className + "不正确！");
			e.printStackTrace();
		}
		return clazz;
	}

	//begin
//	String nextValue = null;
//	Criteria c = super.getCriteria(SystemMainTableIdBuilder.class);
//	c.add(Restrictions.eq("systemMainTable", smt));
//	c.add(Restrictions.eq("deployFlag", Integer.valueOf(1)));
//	SystemMainTableIdBuilder result = (SystemMainTableIdBuilder) c.uniqueResult();
//	if(result!=null){
//		String prefix = result.getPrefix();//编号前缀，如C21-
//		Long length = result.getLength(); //如14,那么去了前缀剩余的就是10位编号了
//		String ruleFile = result.getRuleFileName();
//		
//		String latestValue = result.getLatestValue(); //最新的编号值（上一次用得编号）
//		String latestNumber = "";
//		
//		Map map = new HashedMap();
//		map.put("ruleName", "systemTableIdGen");
//		map.put("prefix", prefix);
//		map.put("length", String.valueOf(length));
//		map.put("latestValue", latestValue);
//		map.put("latestNumber", latestNumber);
//		
//		if(ruleFile==null){
//			ruleFile = "systemTableIdGen.drl";
//		}
//		String result2 = "/com/digitalchina/info/framework/util/idgen/" + ruleFile;
//		
//		nextValue = IdGenRuleMethodHelper.executeRule(result2, map);
//		
//	}
	//end

	
	
//	String realPath = request.getRealPath(FSP) + filePath;
//
//
//	ruleName = "/com/digitalchina/itil/rules/" + ruleName;
//	rulePath = ruleName;
//	String className = smt.getClassName();
//	Class clazz = this.getClass(className);
//	Criteria cc = super.getCriteria(clazz);
	
}
