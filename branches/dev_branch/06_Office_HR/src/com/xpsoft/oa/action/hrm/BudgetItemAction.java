package com.xpsoft.oa.action.hrm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.Budget;
import com.xpsoft.oa.model.hrm.BudgetItem;
import com.xpsoft.oa.model.hrm.JobSalaryRelation;
import com.xpsoft.oa.model.hrm.RealExecution;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.Department;
import com.xpsoft.oa.service.hrm.BudgetItemService;
import com.xpsoft.oa.service.hrm.BudgetService;
import com.xpsoft.oa.service.hrm.JobSalaryRelationService;
import com.xpsoft.oa.service.hrm.RealExecutionService;

import flexjson.JSONSerializer;

public class BudgetItemAction extends BaseAction {

	@Resource
	private BudgetItemService budgetItemService;
	private BudgetItem budgetItem;
	private Long budgetItemId;

	@Resource
	private JobSalaryRelationService jobSalaryRelationService;
	
	@Resource
	private RealExecutionService realExecutionService;
	
	@Resource
	private BudgetService budgetService;
	
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
		BudgetItem budgetItem = (BudgetItem) this.budgetItemService.get(this.budgetItemId);
		//add by awen for add get default budgetItem value logic on 2011-09-01 begin
		
		/*if(budgetItem.getIsDefault().intValue()==1){//默认成本要素
			Department department = budgetItem.getBudget().getBelongDept();
			Map filterMap = new HashMap();
			filterMap.put("Q_deleteFlag_N_EQ", "0");
			filterMap.put("Q_department.depId_L_EQ", department.getDepId().toString());
			QueryFilter filter = new QueryFilter(filterMap);
			List<JobSalaryRelation> list = this.jobSalaryRelationService.getAll(filter);
			BigDecimal totalMoney = new BigDecimal(0);
			for(JobSalaryRelation relation : list){
				totalMoney = totalMoney.add(relation.getTotalMoney());
			}
			budgetItem.setValue(totalMoney.doubleValue()*Double.valueOf(AppUtil.getPropertity("budget.default.budgetItemMonth")));
		}*/
		//add by awen for add get default budgetItem value logic on 2011-09-01 end
		StringBuffer sb = new StringBuffer("{success:true,totalCounts:1,data:[");

		JSONSerializer serializer = new JSONSerializer();
		sb.append(serializer.exclude(new String[] { "class", "department.class" })
			.serialize(budgetItem));
		sb.append("]}");
		setJsonString(sb.toString());
		//setJsonString("{success:true,totalCounts:1,data:[{'budget':{'beginDate':1312992000000,'belongDept':{'class':'com.xpsoft.oa.model.system.Department_$$_javassist_122','depDesc':'维护系统','depId':1,'depLevel':2,'depName':'信息部门','parentId':0,'path':'0.1.'},'budgetId':1,'class':'com.xpsoft.oa.model.hrm.Budget_$$_javassist_62','createDate':1313047728000,'createPerson':{'accessionTime':1261065600000,'accountNonExpired':true,'accountNonLocked':true,'address':null,'businessEmail':'csx@jee-soft.cn','class':'com.xpsoft.oa.model.system.AppUser_$$_javassist_133','credentialsNonExpired':true,'delFlag':0,'department':{'class':'com.xpsoft.oa.model.system.Department_$$_javassist_122','depDesc':'维护系统','depId':1,'depLevel':2,'depName':'信息部门','parentId':0,'path':'0.1.'},'education':null,'email':'csx@jee-soft.cn','enabled':true,'familyName':'超级管理员','fax':null,'firstKeyColumnName':'userId','fullname':'超级管理员','functionRights':'','givenName':'超级管理员','id':'1','mobile':null,'password':'a4ayc/80/OGda4BO/1o/V0etpOqiLx1JwB5S3beHW0s=','phone':null,'photo':null,'position':null,'status':1,'title':1,'userId':1,'username':'admin','zip':null},'endDate':1313078400000,'modifyDate':1313113100000,'modifyPerson':{'accessionTime':1261065600000,'accountNonExpired':true,'accountNonLocked':true,'address':null,'businessEmail':'csx@jee-soft.cn','class':'com.xpsoft.oa.model.system.AppUser_$$_javassist_133','credentialsNonExpired':true,'delFlag':0,'department':{'class':'com.xpsoft.oa.model.system.Department_$$_javassist_122','depDesc':'维护系统','depId':1,'depLevel':2,'depName':'信息部门','parentId':0,'path':'0.1.'},'education':null,'email':'csx@jee-soft.cn','enabled':true,'familyName':'超级管理员','fax':null,'firstKeyColumnName':'userId','fullname':'超级管理员','functionRights':'','givenName':'超级管理员','id':'1','mobile':null,'password':'a4ayc/80/OGda4BO/1o/V0etpOqiLx1JwB5S3beHW0s=','phone':null,'photo':null,'position':null,'status':1,'title':1,'userId':1,'username':'admin','zip':null},'name':'2011IT预算','publishStatus':null,'remark':null},'budgetItemId':1,'code':'1.1','deleteFlag':0,'key':'cost','name':'成本','parent':{budgetItemId:0},'threshold':0.1,'value':100.0}]}");

		return "success";
	}
	
	public String load() {
		BudgetItem budgetItem = (BudgetItem) this.budgetItemService.load(this.budgetItemId);
		//add by awen for add get default budgetItem value logic on 2011-09-01 begin
		
		/*if(budgetItem.getIsDefault().intValue()==1){//默认成本要素
			Department department = budgetItem.getBudget().getBelongDept();
			Map filterMap = new HashMap();
			filterMap.put("Q_deleteFlag_N_EQ", "0");
			filterMap.put("Q_department.depId_L_EQ", department.getDepId().toString());
			QueryFilter filter = new QueryFilter(filterMap);
			List<JobSalaryRelation> list = this.jobSalaryRelationService.getAll(filter);
			BigDecimal totalMoney = new BigDecimal(0);
			for(JobSalaryRelation relation : list){
				totalMoney = totalMoney.add(relation.getTotalMoney());
			}
			budgetItem.setValue(totalMoney.doubleValue()*Double.valueOf(AppUtil.getPropertity("budget.default.budgetItemMonth")));
		}*/
		//add by awen for add get default budgetItem value logic on 2011-09-01 end
		StringBuffer sb = new StringBuffer("{success:true,totalCounts:1,data:[");

		JSONSerializer serializer = new JSONSerializer();
		sb.append(serializer.exclude(new String[] { "class", "department.class" })
			.serialize(budgetItem));
		sb.append("]}");
		setJsonString(sb.toString());
		//setJsonString("{success:true,totalCounts:1,data:[{'budget':{'beginDate':1312992000000,'belongDept':{'class':'com.xpsoft.oa.model.system.Department_$$_javassist_122','depDesc':'维护系统','depId':1,'depLevel':2,'depName':'信息部门','parentId':0,'path':'0.1.'},'budgetId':1,'class':'com.xpsoft.oa.model.hrm.Budget_$$_javassist_62','createDate':1313047728000,'createPerson':{'accessionTime':1261065600000,'accountNonExpired':true,'accountNonLocked':true,'address':null,'businessEmail':'csx@jee-soft.cn','class':'com.xpsoft.oa.model.system.AppUser_$$_javassist_133','credentialsNonExpired':true,'delFlag':0,'department':{'class':'com.xpsoft.oa.model.system.Department_$$_javassist_122','depDesc':'维护系统','depId':1,'depLevel':2,'depName':'信息部门','parentId':0,'path':'0.1.'},'education':null,'email':'csx@jee-soft.cn','enabled':true,'familyName':'超级管理员','fax':null,'firstKeyColumnName':'userId','fullname':'超级管理员','functionRights':'','givenName':'超级管理员','id':'1','mobile':null,'password':'a4ayc/80/OGda4BO/1o/V0etpOqiLx1JwB5S3beHW0s=','phone':null,'photo':null,'position':null,'status':1,'title':1,'userId':1,'username':'admin','zip':null},'endDate':1313078400000,'modifyDate':1313113100000,'modifyPerson':{'accessionTime':1261065600000,'accountNonExpired':true,'accountNonLocked':true,'address':null,'businessEmail':'csx@jee-soft.cn','class':'com.xpsoft.oa.model.system.AppUser_$$_javassist_133','credentialsNonExpired':true,'delFlag':0,'department':{'class':'com.xpsoft.oa.model.system.Department_$$_javassist_122','depDesc':'维护系统','depId':1,'depLevel':2,'depName':'信息部门','parentId':0,'path':'0.1.'},'education':null,'email':'csx@jee-soft.cn','enabled':true,'familyName':'超级管理员','fax':null,'firstKeyColumnName':'userId','fullname':'超级管理员','functionRights':'','givenName':'超级管理员','id':'1','mobile':null,'password':'a4ayc/80/OGda4BO/1o/V0etpOqiLx1JwB5S3beHW0s=','phone':null,'photo':null,'position':null,'status':1,'title':1,'userId':1,'username':'admin','zip':null},'name':'2011IT预算','publishStatus':null,'remark':null},'budgetItemId':1,'code':'1.1','deleteFlag':0,'key':'cost','name':'成本','parent':{budgetItemId:0},'threshold':0.1,'value':100.0}]}");

		return "success";
	}

	public String save() {
		AppUser user = ContextUtil.getCurrentUser();
		boolean isNew=false;
		if(this.getBudgetItem().getBudgetItemId()==null){
			isNew = true;
		}
		
		
		BudgetItem parent = getRequest().getParameter("budgetItem.parent.budgetItemId")!=null && 
			!getRequest().getParameter("budgetItem.parent.budgetItemId").equals("0") ? 
			new BudgetItem() : null;
		if(parent!=null){
			parent.setBudgetItemId(Long.valueOf(getRequest().getParameter("budgetItem.parent.budgetItemId")));
		}
		this.budgetItem.setParent(parent);
		this.budgetItem.setIsDefault(0);//非默认
		
		Budget budget = new Budget();
		budget.setBudgetId(Long.valueOf(getRequest().getParameter("budgetItem.budget.budgetId")));
		this.budgetItem.setBudget(budget);
		this.budgetItem.setDeleteFlag(Integer.valueOf(0));//未删除
		
		//add by awen for add belongItem logic on 2011-09-27 begin
		BudgetItem belongItem = null;
		if(!StringUtils.isEmpty(getRequest().getParameter("budgetItem.belongItem.budgetItemId"))){
			belongItem = new BudgetItem();
			belongItem.setBudgetItemId(Long.valueOf(getRequest().getParameter("budgetItem.belongItem.budgetItemId")));
		}
		this.budgetItem.setBelongItem(belongItem);
		//add by awen for add belongItem logic on 2011-09-27 end
		
		//add by awen for add validate totalMoney logic on 2011-09-27 begin
		//TODO:1,如果存在belongItem那么所有属于这个belongItem的所有季度item的和必须小于belongItem的value
		//TODO:2,如果不存在belongItem，那么隶属于这个ITEM的parentItem的所有子item的和小于这个Item的和才可以
		if(Boolean.valueOf(getRequest().getParameter("isQuarter")) && !validateValue(this.budgetItem)){
			setJsonString("{suxccess:true,isValid:false}");
			return "success";
		}
		
		//add by awen for add validate totalMoney logic on 2011-09-27 end
		
		this.budgetItemService.save(this.budgetItem);
		
		//add by awen for add default realExecution on 2011-09-07 begin
		if(isNew){
			RealExecution re = new RealExecution();
			re.setBudget(this.budgetItem.getBudget());
			re.setBudgetItem(this.budgetItem);
			re.setMonth(0);
			re.setRealValue(0d);
			re.setDeleteFlag(0);
			this.realExecutionService.save(re);
		}
		//add by awen for add default realExecution on 2011-09-07 end
		
		setJsonString("{success:true,isValid:true, budgetItemId:'" + this.budgetItem.getBudgetItemId() + "'}");
		return "success";
	}
	
	private boolean validateValue(BudgetItem budgetItem){
		Long belongItemId = null;
		if(budgetItem.getBelongItem()!=null) belongItemId = budgetItem.getBelongItem().getBudgetItemId();
		
		Map mapFilter = new HashMap();
		List<BudgetItem> list = null;
		QueryFilter filter = null;
		mapFilter.put("Q_deleteFlag_N_EQ", "0");
		
		BudgetItem compare = null;
		
		mapFilter.put("Q_budgetItemId_L_NEQ", budgetItem.getBudgetItemId().toString());//去除自己
		
		if(belongItemId!=null){
			mapFilter.put("Q_belongItem.budgetItemId_L_EQ", belongItemId.toString());
			filter = new QueryFilter(mapFilter);
			list = this.budgetItemService.getAll(filter);
			compare = this.budgetItemService.get(belongItemId);
		}else{
			mapFilter.put("Q_parent.budgetItemId_L_EQ", budgetItem.getParent().getBudgetItemId().toString());
			filter = new QueryFilter(mapFilter);
			list = this.budgetItemService.getAll(filter);
			compare = this.budgetItemService.get(budgetItem.getParent().getBudgetItemId());
		}
		Double total = new Double(0);
		for(BudgetItem item : list){
			/*if(item.getBudgetItemId()!=budgetItem.getBudgetItemId()){//不包含他
				total += item.getValue();
			}*/
			total += item.getValue();
		}
		
		total += budgetItem.getValue();//小于他的父亲或者
		if(total<=compare.getValue()){
			return true;
		}else{
			return false;
		}
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
				rootNode.put("data", item);
				rootNode.put("iconCls", "task-folder");
				rootNode.put("leaf", "true");
				rootNode.put("expanded", "false");
				rootNode.put("isDefault", item.getIsDefault());
				result.add(rootNode);//可能有多个根节点的情况
				iterator.remove();//删除
			}
		}
		
		Budget budget = (Budget)budgetService.get(Long.valueOf(getRequest().getParameter("budgetId")));
		for(Map node : result){//所有root
//			if(budget.getBudgetType().intValue()==1){//年度才会显示岗位树
//				buildDefaultBudgetItem(node);
//			}			
			cascade(node, itemList);
		}
		
		StringBuffer buff = new StringBuffer();

		JSONSerializer serializer = new JSONSerializer();
		buff.append(serializer.exclude(new String[] { "class" })
			.serialize(result));
		
		StringBuffer sb = new StringBuffer();
		sb.append("[{id:'0',text:'所有成本要素',expanded:true,leaf : false, children:");
		sb.append(buff.toString());
		sb.append("}]");
		setJsonString(sb.toString());
		return "success";
	}
	
	/**
	 * 递归组装treeGrid树
	 */
	private void cascade(Map parentNode, List resource){
		buildDefaultBudgetItem(parentNode);
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
				node.put("data", item);
				node.put("iconCls", "task-folder");
				node.put("leaf", "true");
				node.put("expanded", "false");
				node.put("isDefault", item.getIsDefault());
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
				//iterator.remove();//删除
				cascade(node, resource);
			}
		}
		if(hasChild){
			parentNode.put("expanded", "true");
		}
	}
	
	private void buildDefaultBudgetItem(Map defaultNode){
		if(((BudgetItem)defaultNode.get("data")).getBudget().getBudgetType().intValue()!=1) return;
		if(!defaultNode.get("isDefault").toString().equals("1")) return;
		if(!((BudgetItem)defaultNode.get("data")).getKey().equals(AppUtil
				.getPropertity("budget.in.execute.budgetItemKey"))) return;
		String id = defaultNode.get("id").toString();
		BudgetItem budgetItem = (BudgetItem) this.budgetItemService.get(Long.valueOf(id));
		if(budgetItem.getIsDefault().intValue()==1){//默认成本要素
			Department department = budgetItem.getBudget().getBelongDept();
			Map filterMap = new HashMap();
			filterMap.put("Q_deleteFlag_N_EQ", "0");
			filterMap.put("Q_department.depId_L_EQ", department.getDepId().toString());
			QueryFilter filter = new QueryFilter(filterMap);
			List<JobSalaryRelation> list = this.jobSalaryRelationService.getAll(filter);
			List childList = new ArrayList();
			Iterator<JobSalaryRelation> iterator = list.iterator();
			while(iterator.hasNext()){
				JobSalaryRelation item = iterator.next();
				Map node = new HashMap();
				Map rootNode = new HashMap();
				
				node.put("id", "0");
				node.put("text", "岗位：" + item.getJob().getJobName() + "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;薪标：" + item.getStandSalary().getStandardName() 
						+ "  <br>&nbsp;&nbsp;&nbsp;&nbsp;编制人数：" + item.getEmpCount()
						+ "  <br>&nbsp;&nbsp;&nbsp;&nbsp;在岗人数：" + (item.getOnEmpCount() != null? item.getOnEmpCount() : 0)
						+ "  <br>&nbsp;&nbsp;&nbsp;&nbsp;月度薪酬总额：" + item.getStandSalary().getTotalMoney()
						//+ "  <br>&nbsp;&nbsp;&nbsp;&nbsp;总金额（月）：" + item.getTotalMoney()
						+ "  <br>&nbsp;&nbsp;&nbsp;&nbsp;总金额（月）：" + item.getStandSalary().getTotalMoney().doubleValue()*(item.getOnEmpCount() != null? item.getOnEmpCount() : 0)
						+ "");
				node.put("iconCls", "task-folder");
				node.put("leaf", "true");
				node.put("isDefault", "1");
				
				defaultNode.put("iconCls", "task-folder");
				defaultNode.put("expanded", "true");
				defaultNode.remove("leaf");
				childList.add(node);
				defaultNode.put("children", childList);
			}
		}		
	}
	
	/**
	 * 校验某一层级所有成本要素的和小于其父成本要素
	 * 1,季度预算
	 *     a,第一层增加必须选择年度的有的增加，且小于年度中相应的ITEM
	 *     b,第二层增加必须的和必须小于其父亲
	 * 2,年度预算
	 *     a,无所谓
	 *     b,第二层增加必须的和必须小于其父亲
	 */
	public void validateSum(){
		//this.budgetItem;
		
		BudgetItem parent = null;
		Map filterMap = new HashMap();
		if(StringUtils.isNotEmpty(getRequest().getParameter("budgetItem.parent.budgetItemId"))){
			filterMap.put("Q_parent.budgetItemId_L_EQ", getRequest().getParameter("budgetItem.parent.budgetItemId"));
			parent = this.budgetItemService.get(Long.valueOf(getRequest().getParameter("budgetItem.parent.budgetItemId")));
		}else{
			filterMap.put("Q_parent.budgetItemId_L_NULL", "");
			//
		}
		filterMap.put("Q_deleteFlag_N_EQ", "0");
		QueryFilter queryFilter = new QueryFilter(filterMap);
		List<BudgetItem> list = this.budgetItemService.getAll(queryFilter);
		
		//求之前同一级别的预算和
		Double result = new Double(0);
		for(BudgetItem item : list){
			result += item.getValue();
		}
		
		//求父亲的和
		
	}
}
