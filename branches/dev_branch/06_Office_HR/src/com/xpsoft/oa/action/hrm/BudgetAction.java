package com.xpsoft.oa.action.hrm;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.util.JsonUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.form.BudgetForm;
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
import com.xpsoft.oa.util.RealExecutionUtil;

import flexjson.JSONSerializer;

public class BudgetAction extends BaseAction {

	@Resource
	private BudgetService budgetService;
	
	@Resource
	private BudgetItemService budgetItemService;
	
	@Resource
	private RealExecutionService realExecutionService;
	
	@Resource
	private JobSalaryRelationService jobSalaryRelationService;
	
	private Budget budget;
	private Long budgetId;

	public Long getBudgetId() {
		/* 35 */return this.budgetId;
	}

	public void setBudgetId(Long budgetId) {
		/* 39 */this.budgetId = budgetId;
	}

	public Budget getBudget() {
		/* 43 */return this.budget;
	}

	public void setBudget(Budget budget) {
		/* 47 */this.budget = budget;
	}

	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List list = this.budgetService.getAll(filter);

		StringBuffer buff = new StringBuffer(
			"{success:true,'totalCounts':")
			.append(filter.getPagingBean().getTotalItems()).append(
			",result:");

//		JSONSerializer serializer = new JSONSerializer();
		JSONSerializer serializer = JsonUtil
		.getJSONSerializer(new String[] { "beginDate", "endDate", "createDate", "modifyDate" });
		buff.append(serializer.exclude(new String[] { "class" })
			.serialize(list));
		buff.append("}");

		this.jsonString = buff.toString();	

		return "success";
	}
	
	public String listAlarm() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<Budget> list = this.budgetService.getAll(filter);
		List result = new ArrayList();
		for(Budget budget : list){
			BudgetForm budgetDto = new BudgetForm();
			try {
				BeanUtils.copyProperties(budgetDto, budget);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//设置alarmStatus
			List<Map> tree = realExecutionService.treeStatics(budget.getBudgetId());
			int alarmStatus = -1;
			try {
				for(Map map : tree){
					if(map.get("leaf")!=null &&map.get("leaf").equals("true")){//叶子结点
						buildDefaultBudgetItem(map);
						String alarm = map.get("alarm").toString();
						if(Integer.valueOf(alarm).intValue()>alarmStatus){
							alarmStatus = Integer.valueOf(alarm);
						}
					}
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			budgetDto.setAlarmStatus(alarmStatus + "");
			result.add(budgetDto);
		}

		StringBuffer buff = new StringBuffer(
			"{success:true,'totalCounts':")
			.append(filter.getPagingBean().getTotalItems()).append(
			",result:");

//		JSONSerializer serializer = new JSONSerializer();
		JSONSerializer serializer = JsonUtil
		.getJSONSerializer(new String[] { "beginDate", "endDate", "createDate", "modifyDate" });
		buff.append(serializer.exclude(new String[] { "class" })
			.serialize(result));
		buff.append("}");

		this.jsonString = buff.toString();	

		return "success";
	}
	
	private void buildDefaultBudgetItem(Map defaultNode){
		if(!defaultNode.get("isDefault").toString().equals("1")) return;
		String id = defaultNode.get("id").toString();
		BudgetItem budgetItem = (BudgetItem) this.budgetItemService.get(Long.valueOf(id));
		if(budgetItem.getIsDefault().intValue()==1){//默认成本要素
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
			//defaultNode.put("value", totalMoney.doubleValue());
			try {
				defaultNode.put("alarm", 
						//RealExecutionUtil.alarm(totalMoney.doubleValue(), 
						RealExecutionUtil.alarm(Double.valueOf(defaultNode.get("value").toString()),
								Double.valueOf(defaultNode.get("threshold").toString()), 
								Double.valueOf(defaultNode.get("realValue").toString())));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}

	public String combo() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<Budget> list = this.budgetService.getAll(filter);
		StringBuffer sb = new StringBuffer("[");
		for (Budget budget : list) {
			sb.append("['").append(budget.getBudgetId()).append("','")
					.append(budget.getName()).append("'],");
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
				//this.budgetService.remove(new Long(id));
				Budget budget =  this.budgetService.get(new Long(id));
				budget.setPublishStatus(new Integer("4"));
				this.budgetService.save(budget);
			}
		}

		/* 103 */this.jsonString = "{success:true}";

		/* 105 */return "success";
	}

	public String get() {
		Budget budget = (Budget) this.budgetService.get(this.budgetId);

		StringBuffer sb = new StringBuffer("{success:true,totalCounts:1,data:[");

		//JSONSerializer serializer = new JSONSerializer();
		JSONSerializer serializer = JsonUtil
		.getJSONSerializer(new String[] { "beginDate", "endDate", "createDate", "modifyDate" });
		sb.append(serializer.exclude(
						new String[] { "class", "department.class" })
						.serialize(budget));
		sb.append("]}");
		setJsonString(sb.toString().replaceAll("\\s[\\d]{2}:[\\d]{2}:[\\d]{2}", ""));
		return "success";
	}

	public String save() {
		boolean isNew=false;
		AppUser user = ContextUtil.getCurrentUser();
		if(this.budget.getBudgetId()!=null){
			this.budget.setModifyDate(new Date());
			this.budget.setModifyPerson(user);
			//创建人和创建时间永远不变
//			Budget budget = this.budgetService.get(this.budget.getBudgetId());
//			this.budget.setCreateDate(budget.getCreateDate());
//			this.budget.setCreatePerson(budget.getCreatePerson());
		}else{
			isNew = true;
			this.budget.setCreateDate(new Date());
			this.budget.setCreatePerson(user);
		}
		//this.budget.setPublishStatus(new Integer("0"));
		
		this.budget.setBelongDept(new Department(Long.valueOf(getRequest().getParameter("budget.belongDept.depId"))));
		if(this.budget.getBudgetType().intValue()==2){//季度
			Budget belongBudget = new Budget();
			belongBudget.setBudgetId(Long.valueOf(getRequest().getParameter("budget.belongBudget.budgetId")));
			this.budget.setBelongBudget(belongBudget);
		}
		this.budgetService.save(this.budget);
		//add by awen add default budgetItem hr on 2011-09-01 begin
		if(isNew){
			if (this.budget.getBudgetType().intValue()==1) {//年度
				BudgetItem budgetItem = new BudgetItem();
				budgetItem.setBudget(this.budget);
				budgetItem.setName(AppUtil
						.getPropertity("budget.default.budgetItemName"));
				budgetItem.setCode(AppUtil
						.getPropertity("budget.default.budgetItemCode"));
				budgetItem.setKey(AppUtil
						.getPropertity("budget.default.budgetItemKey"));
				budgetItem.setThreshold(Double.valueOf(AppUtil
						.getPropertity("budget.default.budgetItemThreshold")));
				budgetItem.setIsDefault(1);
				budgetItem.setDeleteFlag(0);
				//add by awen for add get default budgetItem value logic on 2011-09-01 begin			
				Department department = budgetItem.getBudget().getBelongDept();
				Map filterMap = new HashMap();
				filterMap.put("Q_deleteFlag_N_EQ", "0");
				filterMap.put("Q_department.depId_L_EQ", department.getDepId()
						.toString());
				QueryFilter filter = new QueryFilter(filterMap);
				List<JobSalaryRelation> list = this.jobSalaryRelationService
						.getAll(filter);
				BigDecimal totalMoney = new BigDecimal(0);
				for (JobSalaryRelation relation : list) {
					totalMoney = totalMoney.add(relation.getTotalMoney());
				}
				budgetItem
						.setValue(totalMoney.doubleValue()
								* Double.valueOf(AppUtil
										.getPropertity("budget.default.budgetItemMonth")));
				//add by awen for add get default budgetItem value logic on 2011-09-01 end
				this.budgetItemService.save(budgetItem);
				//add by awen for add default realExecution on 2011-09-07 begin
				RealExecution re = new RealExecution();
				re.setBudget(budgetItem.getBudget());
				re.setBudgetItem(budgetItem);
				re.setMonth(0);//默认
				re.setRealValue(0d);
				re.setDeleteFlag(0);
				this.realExecutionService.save(re);
				//add by awen for add default realExecution on 2011-09-07 end
			}else if (this.budget.getBudgetType().intValue()==2) {//季度
				//增加默认的成本科目：人力成本，默认金额为总的1/4
				BudgetItem budgetItem = new BudgetItem();
				budgetItem.setBudget(this.budget);
				budgetItem.setName(AppUtil
						.getPropertity("budget.default.budgetItemName"));
				budgetItem.setCode(AppUtil
						.getPropertity("budget.default.budgetItemCode"));
				budgetItem.setKey(AppUtil
						.getPropertity("budget.default.budgetItemKey"));
				budgetItem.setThreshold(Double.valueOf(AppUtil
						.getPropertity("budget.default.budgetItemThreshold")));
				budgetItem.setIsDefault(1);
				budgetItem.setDeleteFlag(0);
				//查询belongItem
				Map filterMap = new HashMap();
				filterMap.put("Q_budget.budgetId_L_EQ", this.budget.getBelongBudget().getBudgetId().toString());
				filterMap.put("Q_isDefault_N_EQ", "1");
				filterMap.put("Q_deleteFlag_N_EQ", "0");
				QueryFilter filter = new QueryFilter(filterMap);
				List<BudgetItem> list = this.budgetItemService.getAll(filter);				
				BudgetItem belongItem = list.get(0);
				budgetItem.setBelongItem(belongItem);
				budgetItem.setIsDefault(belongItem.getIsDefault());//isDefault可以代表类型，不仅仅是 0/1(默认人力成本) 扩展的话2，外包成本 3 其他成本等
				budgetItem.setValue(belongItem.getValue()/4);
				this.budgetItemService.save(budgetItem);
				
				//add by awen for add some budgetItems for jobsalaryrelation on 2011-09-28 begin
				Department department = budgetItem.getBudget().getBelongDept();
				Map filterMap1 = new HashMap();
				filterMap.put("Q_deleteFlag_N_EQ", "0");
				filterMap.put("Q_department.depId_L_EQ", department.getDepId()
						.toString());
				QueryFilter filter1 = new QueryFilter(filterMap1);
				List<JobSalaryRelation> list1 = this.jobSalaryRelationService
						.getAll(filter1);
				int count=0;
				for (JobSalaryRelation relation : list1) {
					BudgetItem item = new BudgetItem();//从岗位生成
					item.setBelongItem(null);//第二级的根据岗位获取的没有
					item.setBudget(budgetItem.getBudget());
					item.setIsDefault(0);
					item.setParent(budgetItem);
					item.setName(relation.getJob().getJobName() + " -- " + relation.getStandSalary().getStandardName());
					item.setValue(relation.getStandSalary().getTotalMoney().doubleValue()
							*(relation.getOnEmpCount() == null ? 0 : relation.getOnEmpCount())*3);//默认三个月*在编制人数*工资总金额
					item.setThreshold(budgetItem.getThreshold());
					item.setDeleteFlag(0);
					count++;
					item.setKey(budgetItem.getKey() + "_" + count);
					item.setCode(budgetItem.getCode() + "." + count);
					this.budgetItemService.save(item);
				}
				//add by awen for add some budgetItems for jobsalaryrelation on 2011-09-28 end
				
				//add by awen for add default realExecution on 2011-09-07 begin
				RealExecution re = new RealExecution();
				re.setBudget(budgetItem.getBudget());
				re.setBudgetItem(budgetItem);
				re.setMonth(0);//默认
				re.setRealValue(0d);
				re.setDeleteFlag(0);
				this.realExecutionService.save(re);
				//add by awen for add default realExecution on 2011-09-07 end
			}
		}
		//add by awen add default budgetItem hr on 2011-09-01 end
		setJsonString("{success:true,budgetId:'" + budget.getBudgetId() + "'}");
		return "success";
	}
}
