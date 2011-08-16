package com.xpsoft.oa.action.hrm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.Budget;
import com.xpsoft.oa.model.hrm.BudgetItem;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.hrm.BudgetItemService;

import flexjson.JSONSerializer;

public class BudgetItemAction extends BaseAction {

	@Resource
	private BudgetItemService budgetItemService;
	private BudgetItem budgetItem;
	private Long budgetItemId;

	public Long getBudgetItemId() {
		/* 35 */return this.budgetItemId;
	}

	public void setBudgetItemId(Long budgetItemId) {
		/* 39 */this.budgetItemId = budgetItemId;
	}

	public BudgetItem getBudgetItem() {
		/* 43 */return this.budgetItem;
	}

	public void setBudgetItem(BudgetItem budgetItem) {
		/* 47 */this.budgetItem = budgetItem;
	}

	public String list() {
		
		QueryFilter filter = new QueryFilter(getRequest());
		List list = this.budgetItemService.getAll(filter);

		StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(
				",result:");

		JSONSerializer serializer = new JSONSerializer();
		buff.append(serializer.exclude(new String[] { "class" })
				.serialize(list));
		buff.append("}");

		this.jsonString = buff.toString();
		
		return "success";
	}
	

	public String combo() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<BudgetItem> list = this.budgetItemService.getAll(filter);
		StringBuffer sb = new StringBuffer("[");
		for (BudgetItem budgetItem : list) {
			sb.append("['").append(budgetItem.getBudgetItemId()).append("','")
					.append(budgetItem.getName()).append("'],");
		}
		if (list.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]");
		setJsonString(sb.toString());
		return "success";
	}

	public String multiDel() {
		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				//this.budgetItemService.remove(new Long(id));
				//逻辑删除
				BudgetItem budgetItem = this.budgetItemService.get(new Long(id));
				budgetItem.setDeleteFlag(new Integer(1));
				this.budgetItemService.save(budgetItem);
			}
		}

		/* 103 */this.jsonString = "{success:true}";

		/* 105 */return "success";
	}

	public String get() {
		/* 113 */BudgetItem budgetItem = (BudgetItem) this.budgetItemService.get(this.budgetItemId);

		/* 117 */StringBuffer sb = new StringBuffer("{success:true,totalCounts:1,data:[");

		/* 119 */JSONSerializer serializer = new JSONSerializer();
		/* 120 */sb
				.append(serializer.exclude(
						new String[] { "class", "department.class" })
						.serialize(budgetItem));
		/* 121 */sb.append("]}");
		/* 122 */setJsonString(sb.toString());
		//setJsonString("{success:true,totalCounts:1,data:[{'budget':{'beginDate':1312992000000,'belongDept':{'class':'com.xpsoft.oa.model.system.Department_$$_javassist_122','depDesc':'维护系统','depId':1,'depLevel':2,'depName':'信息部门','parentId':0,'path':'0.1.'},'budgetId':1,'class':'com.xpsoft.oa.model.hrm.Budget_$$_javassist_62','createDate':1313047728000,'createPerson':{'accessionTime':1261065600000,'accountNonExpired':true,'accountNonLocked':true,'address':null,'businessEmail':'csx@jee-soft.cn','class':'com.xpsoft.oa.model.system.AppUser_$$_javassist_133','credentialsNonExpired':true,'delFlag':0,'department':{'class':'com.xpsoft.oa.model.system.Department_$$_javassist_122','depDesc':'维护系统','depId':1,'depLevel':2,'depName':'信息部门','parentId':0,'path':'0.1.'},'education':null,'email':'csx@jee-soft.cn','enabled':true,'familyName':'超级管理员','fax':null,'firstKeyColumnName':'userId','fullname':'超级管理员','functionRights':'','givenName':'超级管理员','id':'1','mobile':null,'password':'a4ayc/80/OGda4BO/1o/V0etpOqiLx1JwB5S3beHW0s=','phone':null,'photo':null,'position':null,'status':1,'title':1,'userId':1,'username':'admin','zip':null},'endDate':1313078400000,'modifyDate':1313113100000,'modifyPerson':{'accessionTime':1261065600000,'accountNonExpired':true,'accountNonLocked':true,'address':null,'businessEmail':'csx@jee-soft.cn','class':'com.xpsoft.oa.model.system.AppUser_$$_javassist_133','credentialsNonExpired':true,'delFlag':0,'department':{'class':'com.xpsoft.oa.model.system.Department_$$_javassist_122','depDesc':'维护系统','depId':1,'depLevel':2,'depName':'信息部门','parentId':0,'path':'0.1.'},'education':null,'email':'csx@jee-soft.cn','enabled':true,'familyName':'超级管理员','fax':null,'firstKeyColumnName':'userId','fullname':'超级管理员','functionRights':'','givenName':'超级管理员','id':'1','mobile':null,'password':'a4ayc/80/OGda4BO/1o/V0etpOqiLx1JwB5S3beHW0s=','phone':null,'photo':null,'position':null,'status':1,'title':1,'userId':1,'username':'admin','zip':null},'name':'2011IT预算','publishStatus':null,'remark':null},'budgetItemId':1,'code':'1.1','deleteFlag':0,'key':'cost','name':'成本','parent':{budgetItemId:0},'threshold':0.1,'value':100.0}]}");

		/* 124 */return "success";
	}

	public String save() {
		AppUser user = ContextUtil.getCurrentUser();
		
		BudgetItem parent = getRequest().getParameter("budgetItem.parent.budgetItemId")!=null && 
			!getRequest().getParameter("budgetItem.parent.budgetItemId").equals("0") ? 
			new BudgetItem() : null;
		if(parent!=null){
			parent.setBudgetItemId(Long.valueOf(getRequest().getParameter("budgetItem.parent.budgetItemId")));
		}
		this.budgetItem.setParent(parent);
		
		Budget budget = new Budget();
		budget.setBudgetId(Long.valueOf(getRequest().getParameter("budgetItem.budget.budgetId")));
		this.budgetItem.setBudget(budget);
		this.budgetItem.setDeleteFlag(Integer.valueOf(0));//未删除
		this.budgetItemService.save(this.budgetItem);
		setJsonString("{success:true, budgetItemId:'" + this.budgetItem.getBudgetItemId() + "'}");
		return "success";
	}
	
	public String tree(){
		List<BudgetItem> itemList = null;
		if(getRequest().getParameter("budgetId")!=null){
			Map map = new HashMap();
			map.put("Q_budget.budgetId_L_EQ", getRequest().getParameter("budgetId"));
			map.put("Q_deleteFlag_N_EQ", "0");
			QueryFilter paramQueryFilter = new QueryFilter(map);
			itemList = this.budgetItemService.getAll(paramQueryFilter);
		}else{
			itemList = new ArrayList();
		}
	 
		//这里需要对其进行树形结构的json重组
		List<Map> result = new ArrayList();//结果
		Iterator<BudgetItem> iterator = itemList.iterator();
		while(iterator.hasNext()){
			BudgetItem item = iterator.next();
			if(item.getParent()==null){
				Map rootNode = new HashMap();
				rootNode.put("id", item.getBudgetItemId().toString());
				rootNode.put("text", item.getName());
				rootNode.put("iconCls", "task-folder");
				rootNode.put("leaf", "true");
				rootNode.put("expanded", "false");

				result.add(rootNode);//可能有多个根节点的情况
				iterator.remove();//删除
			}
		}
		
		for(Map node : result){//所有root
			cascade(node, itemList);
		}
		
		StringBuffer buff = new StringBuffer();

		JSONSerializer serializer = new JSONSerializer();
		buff.append(serializer.exclude(new String[] { "class" })
			.serialize(result));
		
//		this.jsonString = buff.toString();
		
		
//		StringBuffer sb = new StringBuffer();
//		sb.append("[{id:'0',text:'所有成本要素',expanded:true,children:[");
//		for (BudgetItem item : itemList) {
//			sb.append("{id:'" + item.getBudgetItemId()).append("',text:'" + item.getName()).append("',leaf:true,expanded:true},");
//		}
//		if (itemList.size() > 0) {
//			sb.deleteCharAt(sb.length() - 1);
//		}
//		sb.append("]}]");
		StringBuffer sb = new StringBuffer();
		sb.append("[{id:'0',text:'所有成本要素',expanded:true,children:");
		sb.append(buff.toString());
		sb.append("}]");
		setJsonString(sb.toString());
		return "success";
	}
	
	/**
	 * 递归组装treeGrid树
	 */
	private void cascade(Map parentNode, List resource){
		boolean hasChild = false;
		Iterator<BudgetItem> iterator = resource.iterator();
		while(iterator.hasNext()){
			BudgetItem item = iterator.next();
			if(item.getParent()!=null && item.getParent().getBudgetItemId().toString().equals(parentNode.get("id"))){
				hasChild = true;//有孩子
				Map node = new HashMap();
				Map rootNode = new HashMap();
				node.put("id", item.getBudgetItemId().toString());
				node.put("text", item.getName());
				node.put("iconCls", "task-folder");
				node.put("leaf", "true");
				node.put("expanded", "false");
				parentNode.put("iconCls", "task-folder");
				parentNode.put("expanded", "true");
				parentNode.remove("leaf");
				//parentNode.
				if(parentNode.get("children")!=null){
					((List)(parentNode.get("children"))).add(node);
				}else{
					List list = new ArrayList();
					list.add(node);
					parentNode.put("children", list);
				}
				iterator.remove();//删除
				cascade(node, resource);
			}
		}
		if(hasChild){
			parentNode.put("expanded", "true");
		}
	}
}
