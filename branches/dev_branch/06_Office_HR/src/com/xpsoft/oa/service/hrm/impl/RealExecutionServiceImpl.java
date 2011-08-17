package com.xpsoft.oa.service.hrm.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.hrm.RealExecutionDao;
import com.xpsoft.oa.model.hrm.Budget;
import com.xpsoft.oa.model.hrm.BudgetItem;
import com.xpsoft.oa.model.hrm.RealExecution;
import com.xpsoft.oa.service.hrm.RealExecutionService;

public class RealExecutionServiceImpl extends BaseServiceImpl<RealExecution> implements
		RealExecutionService {
	private RealExecutionDao dao;

	public RealExecutionServiceImpl(RealExecutionDao dao) {
		/* 15 */super(dao);
		/* 16 */this.dao = dao;
	}

	public List<Map> treeStatics(Long paramLong) {
		// TODO 1,首先获取list{budgetId,sumrealValue)} 2,重组数据
		List<Map> result = new ArrayList();//结果
		
		List<Object []> list = dao.treeStatics(paramLong);
		if(list.size()<=0){
			return result;
		}
		Iterator<Object []> iterator = list.iterator();
		while(iterator.hasNext()){
			Object [] objs = iterator.next();
			BudgetItem item = (BudgetItem) objs[0];
			if(item.getParent()==null){
				Map rootNode = null;
				rootNode = new HashMap();
				rootNode.put("id", item.getBudgetItemId().toString());
				rootNode.put("text", item.getName());
				rootNode.put("code", item.getCode());
				rootNode.put("name", item.getName());
				rootNode.put("key", item.getKey());
				rootNode.put("value", item.getValue());
				rootNode.put("threshold", item.getThreshold());
				rootNode.put("realValue", objs[1].toString());
				rootNode.put("iconCls", "task-folder");
				rootNode.put("expanded", "false");
				rootNode.put("leaf", "true");				
				rootNode.put("alarm", alarm(item.getValue(), item.getThreshold(), Double.valueOf(objs[1].toString())));
				result.add(rootNode);//可能有多个根节点的情况
				iterator.remove();//删除
			}
		}
		
		for(Map node : result){//所有root
			cascade(node, list);
		}
		
		//递归
		
		return result;
	}
	
	/**
	 * <阀值 红灯
	 * >=阀值+阀值*50% 绿
	 * <阀值+阀值*50% &&>=阀值 黄
	 * @param value 预算金额
	 * @param threshold 阀值
	 * @param realValue 执行值
	 * @return 
	 */
	private String alarm(Double value, Double threshold, Double realValue){
		
		if((value-realValue) < value*threshold){
			return Budget.ALARM_RED;
		}else if((value-realValue) < ((value-realValue)*(threshold*1.5))){
			return Budget.ALARM_YELLOW;
		}else{
			return Budget.ALARM_GREEN;
		}
	}
	
	/**
	 * 递归组装treeGrid树
	 */
	private void cascade(Map parentNode, List resource){
		boolean hasChild = false;
		Iterator<Object []> iterator = resource.iterator();
		while(iterator.hasNext()){
			Object [] objs = iterator.next();
			BudgetItem item = (BudgetItem) objs[0];
			if(item.getParent()!=null && item.getParent().getBudgetItemId().toString().equals(parentNode.get("id"))){
				hasChild = true;//有孩子
				
				Map node = null;
				node = new HashMap();
				node.put("id", item.getBudgetItemId().toString());
				node.put("text", item.getName());
				//告警标志
				node.put("code", item.getCode());
				node.put("name", item.getName());
				node.put("key", item.getKey());
				node.put("value", item.getValue());
				node.put("threshold", item.getThreshold());
				node.put("realValue", objs[1].toString());				
				node.put("leaf", "true");
				parentNode.put("iconCls", "task-folder");
				parentNode.put("expanded", "true");
				parentNode.put("leaf", "false");
				parentNode.remove("leaf");
				if(parentNode.get("children")!=null){
					((List)(parentNode.get("children"))).add(node);
				}else{
					List list = new ArrayList();
					list.add(node);
					parentNode.put("children", list);
				}
				iterator.remove();//删除
				node.put("alarm", alarm(item.getValue(), item.getThreshold(), Double.valueOf(objs[1].toString())));
				cascade(node, resource);
			}
		}
		if(hasChild){
			parentNode.put("expanded", "true");
			parentNode.put("alarm", "");
		}
	}
	
}