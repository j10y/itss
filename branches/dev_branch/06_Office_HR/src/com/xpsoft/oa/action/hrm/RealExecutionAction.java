package com.xpsoft.oa.action.hrm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.util.JsonUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.Budget;
import com.xpsoft.oa.model.hrm.BudgetItem;
import com.xpsoft.oa.model.hrm.JobSalaryRelation;
import com.xpsoft.oa.model.hrm.RealExecution;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.Department;
import com.xpsoft.oa.service.hrm.BudgetItemService;
import com.xpsoft.oa.service.hrm.JobSalaryRelationService;
import com.xpsoft.oa.service.hrm.RealExecutionService;
import com.xpsoft.oa.util.RealExecutionUtil;

import flexjson.JSONSerializer;

public class RealExecutionAction extends BaseAction {

	@Resource
	private RealExecutionService realExecutionService;
	private RealExecution realExecution;
	private Long realExecutionId;
	
	@Resource
	private BudgetItemService budgetItemService;

	@Resource
	private JobSalaryRelationService jobSalaryRelationService;

	public Long getRealExecutionId() {
		/* 35 */return this.realExecutionId;
	}

	public void setRealExecutionId(Long realExecutionId) {
		/* 39 */this.realExecutionId = realExecutionId;
	}

	public RealExecution getBudget() {
		/* 43 */return this.realExecution;
	}

	public void setBudget(RealExecution realExecution) {
		/* 47 */this.realExecution = realExecution;
	}

	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List list = this.realExecutionService.getAll(filter);

		StringBuffer buff = new StringBuffer(
			"{success:true,'totalCounts':")
			.append(filter.getPagingBean().getTotalItems()).append(
			",result:");

//		JSONSerializer serializer = new JSONSerializer();
		JSONSerializer serializer = JsonUtil
		.getJSONSerializer(new String[] { "inputDate"});
		buff.append(serializer.exclude(new String[] { "class" })
			.serialize(list));
		buff.append("}");

		this.jsonString = buff.toString();

		return "success";
	}

	public String combo() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<RealExecution> list = this.realExecutionService.getAll(filter);
		StringBuffer sb = new StringBuffer("[");
		for (RealExecution realExecution : list) {
			sb.append("['").append(realExecution.getRealExecutionId()).append("','")
					.append(realExecution.getRemark()).append("'],");
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
				//this.realExecutionService.remove(new Long(id));
				RealExecution realExecution =  this.realExecutionService.get(new Long(id));
				realExecution.setDeleteFlag(new Integer("1"));
				this.realExecutionService.save(realExecution);
			}
		}

		/* 103 */this.jsonString = "{success:true}";

		/* 105 */return "success";
	}

	public String get() {
		RealExecution realExecution = (RealExecution) this.realExecutionService.get(this.realExecutionId);

		StringBuffer sb = new StringBuffer("{success:true,totalCounts:1,data:[");

		//JSONSerializer serializer = new JSONSerializer();
		JSONSerializer serializer = JsonUtil
		.getJSONSerializer(new String[] { "inputDate" });
		sb.append(serializer.exclude(
						new String[] { "class", "department.class" })
						.serialize(realExecution));
		sb.append("]}");
		setJsonString(sb.toString());
		return "success";
	}

	public String save() {
		AppUser user = ContextUtil.getCurrentUser();
		if(this.realExecution.getRealExecutionId()!=null){
		}else{
			this.realExecution.setInputDate(new Date());
		}
		this.realExecution.setDeleteFlag(new Integer(0));
		
		Budget budget = new Budget();
		budget.setBudgetId(Long.valueOf(getRequest().getParameter("realExecution.budget.budgetId")));
		this.realExecution.setBudget(budget);
		BudgetItem budgetItem = new BudgetItem();
		budgetItem.setBudgetItemId(Long.valueOf(getRequest().getParameter("realExecution.budgetItem.budgetItemId")));
		this.realExecution.setBudgetItem(budgetItem);
		
		this.realExecutionService.save(this.realExecution);
		setJsonString("{success:true,realExecutionId:'" + realExecution.getRealExecutionId() + "'}");
		return "success";
	}
	
	public String tree() {

		//List list = this.realExecutionService.getAll(filter);

		List<Map> list = this.realExecutionService.treeStatics(Long.valueOf(getRequest().getParameter("budgetId")));
		
		//add default budgetItem alarm logic on 2011-09-01 begin
		if(!Boolean.valueOf(getRequest().getParameter("isQuarter"))){
			for(Map map : list)
				this.buildDefaultBudgetItem(map);	
		}
		
		//add default budgetItem alarm logic on 2011-09-01 end

		StringBuffer buff = new StringBuffer();

		JSONSerializer serializer = new JSONSerializer();
		buff.append(serializer.exclude(new String[] { "class" })
			.serialize(list));
		
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	private void buildDefaultBudgetItem(Map defaultNode){
		if(!defaultNode.get("isDefault").toString().equals("1")) return;
		defaultNode.put("leaf", false);
		String id = defaultNode.get("id").toString();
		BudgetItem budgetItem = (BudgetItem) this.budgetItemService.get(Long.valueOf(id));
		if(budgetItem.getIsDefault().intValue()==1){//默认成本要素
			Department department = budgetItem.getBudget().getBelongDept();
			Map filterMap = new HashMap();
			filterMap.put("Q_deleteFlag_N_EQ", "0");
			filterMap.put("Q_department.depId_L_EQ", department.getDepId().toString());
			QueryFilter filter = new QueryFilter(filterMap);
			List<JobSalaryRelation> list = this.jobSalaryRelationService.getAll(filter);//所有的子
			BigDecimal totalMoney = new BigDecimal(0);
			if(list.size()<=0){
				defaultNode.put("leaf", true);
			}
			for(JobSalaryRelation relation : list){
				totalMoney = totalMoney.add(relation.getTotalMoney());
				Map node = new HashMap();
				node.put("id", "rel" + relation.getRelationId());
				node.put("name", "岗位：" + relation.getJob().getJobName() + "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;薪标：" + relation.getStandSalary().getStandardName() 
						+ "  <br>&nbsp;&nbsp;&nbsp;&nbsp;人数：" + relation.getEmpCount()
						+ "  <br>&nbsp;&nbsp;&nbsp;&nbsp;标准金额：" + relation.getStandSalary().getTotalMoney()
						+ "  <br>&nbsp;&nbsp;&nbsp;&nbsp;总金额（月）：" + relation.getTotalMoney()
						+ "");
				node.put("text", "岗位：" + relation.getJob().getJobName() + "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;薪标：" + relation.getStandSalary().getStandardName() 
						+ "  <br>&nbsp;&nbsp;&nbsp;&nbsp;人数：" + relation.getEmpCount()
						+ "  <br>&nbsp;&nbsp;&nbsp;&nbsp;标准金额：" + relation.getStandSalary().getTotalMoney()
						+ "  <br>&nbsp;&nbsp;&nbsp;&nbsp;总金额（月）：" + relation.getTotalMoney()
						+ "");
				node.put("leaf", true);
				node.put("alarm", "");
				if(defaultNode.get("children")!=null){
					((List)(defaultNode.get("children"))).add(node);
				}else{
					List child = new ArrayList();
					child.add(node);
					defaultNode.put("children", child);
				}
				
			}
			
			//defaultNode.put("value", totalMoney.doubleValue()*Double.valueOf(AppUtil.getPropertity("budget.default.budgetItemMonth")));
			try {
				defaultNode.put("alarm", 
						//RealExecutionUtil.alarm(totalMoney.doubleValue(), 
						RealExecutionUtil.alarm(Double.valueOf(defaultNode.get("value").toString()),//进行了修改
								Double.valueOf(defaultNode.get("threshold").toString()), 
								Double.valueOf(defaultNode.get("realValue").toString())));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	

	public RealExecution getRealExecution() {
		return realExecution;
	}

	public void setRealExecution(RealExecution realExecution) {
		this.realExecution = realExecution;
	}
	
	
}
