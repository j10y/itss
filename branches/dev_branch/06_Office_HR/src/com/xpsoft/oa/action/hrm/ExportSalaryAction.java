package com.xpsoft.oa.action.hrm;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.JsonUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.ExportSalary;
import com.xpsoft.oa.model.hrm.ExportSalaryItemOrder;
import com.xpsoft.oa.model.hrm.RewardsPunishmentsType;
import com.xpsoft.oa.model.hrm.SalaryItem;
import com.xpsoft.oa.service.hrm.ExportSalaryItemService;
import com.xpsoft.oa.service.hrm.ExportSalaryService;
import com.xpsoft.oa.service.hrm.RewardsPunishmentsTypeService;
import com.xpsoft.oa.service.hrm.SalaryItemService;

import flexjson.JSONSerializer;

public class ExportSalaryAction extends BaseAction{
	private ExportSalary exportSalary;
	private ExportSalaryItemOrder exportSalaryItemOrder;
	
	@Resource
	private ExportSalaryService exportSalaryService;
	
	@Resource
	private ExportSalaryItemService exportSalaryItemService;
	
	
	public ExportSalaryItemOrder getExportSalaryItemOrder() {
		return exportSalaryItemOrder;
	}

	public void setExportSalaryItemOrder(ExportSalaryItemOrder exportSalaryItemOrder) {
		this.exportSalaryItemOrder = exportSalaryItemOrder;
	}

	public ExportSalaryItemService getExportSalaryItemService() {
		return exportSalaryItemService;
	}

	public void setExportSalaryItemService(
			ExportSalaryItemService exportSalaryItemService) {
		this.exportSalaryItemService = exportSalaryItemService;
	}

	public ExportSalary getExportSalary() {
		return exportSalary;
	}

	public void setExportSalary(ExportSalary exportSalary) {
		this.exportSalary = exportSalary;
	}

	public ExportSalaryService getExportSalaryService() {
		return exportSalaryService;
	}

	public void setExportSalaryService(ExportSalaryService exportSalaryService) {
		this.exportSalaryService = exportSalaryService;
	}
	
	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<ExportSalary> list = this.exportSalaryService.getAll(filter);
		Type type = new TypeToken<List<ExportSalary>>() {
		}
		.getType();
		StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':'" +
				filter.getPagingBean().getTotalItems() + "',result:");
		Gson gson = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation().create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		setJsonString(buff.toString());
		return "success";
	}
	public String get(){
		QueryFilter filter = new QueryFilter(getRequest());
		List<ExportSalary> list = this.exportSalaryService.getAll(filter);
		ExportSalary exportSalary=new ExportSalary();
		if(list!=null&&list.size()>0){
			exportSalary=list.get(0);
		}else{
			exportSalary.setName("默认模板");
			exportSalary.setDescr("默认模板");
			exportSalary=exportSalaryService.save(exportSalary);
			
			ExportSalaryItemOrder exportSalaryItemOrder=new ExportSalaryItemOrder();
			exportSalaryItemOrder.setExportName("部门");
			exportSalaryItemOrder.setExportSalId(exportSalary);
			exportSalaryItemOrder.setFromTable("3");//系统默认
			exportSalaryItemOrder.setFromTableName("depName");
			exportSalaryItemOrder.setOrderCol(1);
			exportSalaryItemOrder.setIsDefaut(0);
			this.exportSalaryItemService.save(exportSalaryItemOrder);
			
			ExportSalaryItemOrder exportSalaryItemOrder2=new ExportSalaryItemOrder();
			exportSalaryItemOrder2.setExportName("姓名");
			exportSalaryItemOrder2.setExportSalId(exportSalary);
			exportSalaryItemOrder2.setFromTable("3");//系统默认
			exportSalaryItemOrder2.setFromTableName("fullname");
			exportSalaryItemOrder2.setOrderCol(2);
			exportSalaryItemOrder2.setIsDefaut(0);
			this.exportSalaryItemService.save(exportSalaryItemOrder2);
			
			ExportSalaryItemOrder exportSalaryItemOrder3=new ExportSalaryItemOrder();
			exportSalaryItemOrder3.setExportName("职位");
			exportSalaryItemOrder3.setExportSalId(exportSalary);
			exportSalaryItemOrder3.setFromTable("3");//系统默认
			exportSalaryItemOrder3.setFromTableName("position");
			exportSalaryItemOrder3.setOrderCol(3);
			exportSalaryItemOrder3.setIsDefaut(0);
			this.exportSalaryItemService.save(exportSalaryItemOrder3);
			
			ExportSalaryItemOrder exportSalaryItemOrder4=new ExportSalaryItemOrder();
			exportSalaryItemOrder4.setExportName("员工性质");
			exportSalaryItemOrder4.setExportSalId(exportSalary);
			exportSalaryItemOrder4.setFromTable("4");//系统默认,如果是4，则是员工性质特殊处理
			exportSalaryItemOrder4.setFromTableName("positiveTime");
			exportSalaryItemOrder4.setOrderCol(4);
			exportSalaryItemOrder4.setIsDefaut(0);
			this.exportSalaryItemService.save(exportSalaryItemOrder4);
			
			ExportSalaryItemOrder exportSalaryItemOrder5=new ExportSalaryItemOrder();
			exportSalaryItemOrder5.setExportName("入职调动情况");
			exportSalaryItemOrder5.setExportSalId(exportSalary);
			exportSalaryItemOrder5.setFromTable("3");//系统默认
			exportSalaryItemOrder5.setFromTableName("accessionTime");
			exportSalaryItemOrder5.setOrderCol(5);
			exportSalaryItemOrder5.setIsDefaut(0);
			this.exportSalaryItemService.save(exportSalaryItemOrder5);
			
			ExportSalaryItemOrder exportSalaryItemOrder6=new ExportSalaryItemOrder();
			exportSalaryItemOrder6.setExportName("银行卡号");
			exportSalaryItemOrder6.setExportSalId(exportSalary);
			exportSalaryItemOrder6.setFromTable("3");//系统默认
			exportSalaryItemOrder6.setFromTableName("bankNo");
			exportSalaryItemOrder6.setOrderCol(6);
			exportSalaryItemOrder6.setIsDefaut(0);
			this.exportSalaryItemService.save(exportSalaryItemOrder6);
			
			ExportSalaryItemOrder exportSalaryItemOrder7=new ExportSalaryItemOrder();
			exportSalaryItemOrder7.setExportName("固定工资");
			exportSalaryItemOrder7.setExportSalId(exportSalary);
			exportSalaryItemOrder7.setFromTable("3");//系统默认
			exportSalaryItemOrder7.setFromTableName("standardMoney");
			exportSalaryItemOrder7.setOrderCol(7);
			exportSalaryItemOrder7.setIsDefaut(0);
			this.exportSalaryItemService.save(exportSalaryItemOrder7);
			
			ExportSalaryItemOrder exportSalaryItemOrder8=new ExportSalaryItemOrder();
			exportSalaryItemOrder8.setExportName("浮动工资");
			exportSalaryItemOrder8.setExportSalId(exportSalary);
			exportSalaryItemOrder8.setFromTable("3");//系统默认
			exportSalaryItemOrder8.setFromTableName("perCoefficient");
			exportSalaryItemOrder8.setOrderCol(8);
			exportSalaryItemOrder8.setIsDefaut(0);
			this.exportSalaryItemService.save(exportSalaryItemOrder8);
			
			ExportSalaryItemOrder exportSalaryItemOrder9=new ExportSalaryItemOrder();
			exportSalaryItemOrder9.setExportName("绩效系数");
			exportSalaryItemOrder9.setExportSalId(exportSalary);
			exportSalaryItemOrder9.setFromTable("3");//系统默认
			exportSalaryItemOrder9.setFromTableName("jxxs");
			exportSalaryItemOrder9.setOrderCol(9);
			exportSalaryItemOrder9.setIsDefaut(0);
			this.exportSalaryItemService.save(exportSalaryItemOrder9);
			
			ExportSalaryItemOrder exportSalaryItemOrder10=new ExportSalaryItemOrder();
			exportSalaryItemOrder10.setExportName("绩效奖金");
			exportSalaryItemOrder10.setExportSalId(exportSalary);
			exportSalaryItemOrder10.setFromTable("3");//系统默认
			exportSalaryItemOrder10.setFromTableName("achieveAmount");
			exportSalaryItemOrder10.setOrderCol(10);
			exportSalaryItemOrder10.setIsDefaut(0);
			this.exportSalaryItemService.save(exportSalaryItemOrder10);
			
			ExportSalaryItemOrder exportSalaryItemOrder11=new ExportSalaryItemOrder();
			exportSalaryItemOrder11.setExportName("应发金额");
			exportSalaryItemOrder11.setExportSalId(exportSalary);
			exportSalaryItemOrder11.setFromTable("3");//系统默认
			exportSalaryItemOrder11.setFromTableName("issuedAmount");
			exportSalaryItemOrder11.setOrderCol(11);
			exportSalaryItemOrder11.setIsDefaut(0);
			this.exportSalaryItemService.save(exportSalaryItemOrder11);
			
			ExportSalaryItemOrder exportSalaryItemOrder12=new ExportSalaryItemOrder();
			exportSalaryItemOrder12.setExportName("保险扣款");
			exportSalaryItemOrder12.setExportSalId(exportSalary);
			exportSalaryItemOrder12.setFromTable("3");//系统默认
			exportSalaryItemOrder12.setFromTableName("insurance");
			exportSalaryItemOrder12.setOrderCol(12);
			exportSalaryItemOrder12.setIsDefaut(0);
			this.exportSalaryItemService.save(exportSalaryItemOrder12);
			
			ExportSalaryItemOrder exportSalaryItemOrder13=new ExportSalaryItemOrder();
			exportSalaryItemOrder13.setExportName("公积金扣款");
			exportSalaryItemOrder13.setExportSalId(exportSalary);
			exportSalaryItemOrder13.setFromTable("3");//系统默认
			exportSalaryItemOrder13.setFromTableName("provident");
			exportSalaryItemOrder13.setOrderCol(13);
			exportSalaryItemOrder13.setIsDefaut(0);
			this.exportSalaryItemService.save(exportSalaryItemOrder13);
			
			ExportSalaryItemOrder exportSalaryItemOrder14=new ExportSalaryItemOrder();
			exportSalaryItemOrder14.setExportName("应税金额");
			exportSalaryItemOrder14.setExportSalId(exportSalary);
			exportSalaryItemOrder14.setFromTable("3");//系统默认
			exportSalaryItemOrder14.setFromTableName("taxableAmount");
			exportSalaryItemOrder14.setOrderCol(14);
			exportSalaryItemOrder14.setIsDefaut(0);
			this.exportSalaryItemService.save(exportSalaryItemOrder14);
			
			ExportSalaryItemOrder exportSalaryItemOrder15=new ExportSalaryItemOrder();
			exportSalaryItemOrder15.setExportName("扣税");
			exportSalaryItemOrder15.setExportSalId(exportSalary);
			exportSalaryItemOrder15.setFromTable("3");//系统默认
			exportSalaryItemOrder15.setFromTableName("selftax");
			exportSalaryItemOrder15.setOrderCol(15);
			exportSalaryItemOrder15.setIsDefaut(0);
			this.exportSalaryItemService.save(exportSalaryItemOrder15);
			
			ExportSalaryItemOrder exportSalaryItemOrder16=new ExportSalaryItemOrder();
			exportSalaryItemOrder16.setExportName("实发金额");
			exportSalaryItemOrder16.setExportSalId(exportSalary);
			exportSalaryItemOrder16.setFromTable("3");//系统默认
			exportSalaryItemOrder16.setFromTableName("acutalAmount");
			exportSalaryItemOrder16.setOrderCol(16);
			exportSalaryItemOrder16.setIsDefaut(0);
			this.exportSalaryItemService.save(exportSalaryItemOrder16);
			
			ExportSalaryItemOrder exportSalaryItemOrder17=new ExportSalaryItemOrder();
			exportSalaryItemOrder17.setExportName("备注");
			exportSalaryItemOrder17.setExportSalId(exportSalary);
			exportSalaryItemOrder17.setFromTable("3");//系统默认
			exportSalaryItemOrder17.setFromTableName("memo");
			exportSalaryItemOrder17.setOrderCol(17);
			exportSalaryItemOrder17.setIsDefaut(0);
			this.exportSalaryItemService.save(exportSalaryItemOrder17);	
			
		}
		JSONSerializer json = JsonUtil.getJSONSerializer(new String[] {});
		StringBuffer sb = new StringBuffer("{success:true,data:[");
		sb.append(json.exclude(new String[] { "class"})
				.serialize(exportSalary));
		sb.append("]}");
		setJsonString(sb.toString());
		return "success";
	}
	
	public String combo(){
		String fromTable=getRequest().getParameter("fromTable");
		SalaryItemService salaryItemService=(SalaryItemService) AppUtil.getBean("salaryItemService");
		RewardsPunishmentsTypeService rewardsPunishmentsTypeService=(RewardsPunishmentsTypeService) AppUtil.getBean("rewardsPunishmentsTypeService");
		if(fromTable.equals("1")){
			 List<SalaryItem> list = salaryItemService.getAll();
		       StringBuffer sb = new StringBuffer("[");
		       for (SalaryItem salaryItem : list) {
		         sb.append("['").append(salaryItem.getSalaryItemId()).append("','").append(salaryItem.getItemName()).append("'],");
		       }
		       if (list.size() > 0) {
		         sb.deleteCharAt(sb.length() - 1);
		       }
		      sb.append("]");
		       setJsonString(sb.toString());
		}else if(fromTable.equals("2")){
			List<RewardsPunishmentsType> list = rewardsPunishmentsTypeService.getAll();
		       StringBuffer sb = new StringBuffer("[");
		       for (RewardsPunishmentsType rewardsPunishmentsType : list) {
		         sb.append("['").append(rewardsPunishmentsType.getTypeId()).append("','").append(rewardsPunishmentsType.getTypeName()).append("'],");
		       }
		       if (list.size() > 0) {
		         sb.deleteCharAt(sb.length() - 1);
		       }
		      sb.append("]");
		       setJsonString(sb.toString());
		}else {
		       setJsonString("{success:false}");
	     }
		return "success";
	}
	public String save(){
		exportSalaryService.save(exportSalary);
		return "success";
	}
	public String saveExSaItem(){
		if(exportSalaryItemOrder.getId()!=null){
			
		}else{
			exportSalaryItemOrder.setIsDefaut(1);
			List list=exportSalaryItemService.getAll();
			exportSalaryItemOrder.setOrderCol(list.size()+1);
		}
		exportSalaryItemService.save(exportSalaryItemOrder);
		return "success";
	}
	public String listExSaItem(){
		Map map=new HashMap();
		map.put("limit", "999");
		map.put("sort", "orderCol");
		map.put("dir", "asc");
		QueryFilter filter=new QueryFilter(map);
		List<ExportSalaryItemOrder> list = this.exportSalaryItemService.getAll(filter);
		Type type = new TypeToken<List<ExportSalaryItemOrder>>() {
		}
		.getType();
		StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':'" +
				list.size() + "',result:");
		Gson gson = new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		setJsonString(buff.toString());
		return "success";
	}
	public String removeExSaItem(){
		String id=getRequest().getParameter("id");
		exportSalaryItemOrder=this.exportSalaryItemService.get(Long.parseLong(id));
		Integer ordercol=exportSalaryItemOrder.getOrderCol();
		this.exportSalaryItemService.remove(Long.parseLong(id));
		Map map=new HashMap();
		map.put("Q_orderCol_N_GE", ordercol+"");
		QueryFilter filter=new QueryFilter(map);
		List<ExportSalaryItemOrder> list=this.exportSalaryItemService.getAll(filter);
		for(ExportSalaryItemOrder eso:list){
			eso.setOrderCol(eso.getOrderCol()-1);
			this.exportSalaryItemService.save(eso);
		}
		return "success";
	}
	public String sort(){
		String id=getRequest().getParameter("id");
		String opt=getRequest().getParameter("opt");
		exportSalaryItemOrder=this.exportSalaryItemService.get(Long.parseLong(id));
		Integer ordercol=exportSalaryItemOrder.getOrderCol();
		Integer len=this.exportSalaryItemService.getAll().size();
		if(opt.equals("1")&&ordercol!=1){//置顶
			Map map=new HashMap();
			map.put("Q_orderCol_N_LT", ordercol+"");
			QueryFilter filter=new QueryFilter(map);
			List<ExportSalaryItemOrder> list=this.exportSalaryItemService.getAll(filter);
			for(ExportSalaryItemOrder eso:list){
				eso.setOrderCol(eso.getOrderCol()+1);
				this.exportSalaryItemService.save(eso);
			}
			exportSalaryItemOrder.setOrderCol(1);
			this.exportSalaryItemService.save(exportSalaryItemOrder);
		}else if(opt.equals("2")&&ordercol!=1){//上移
			Map map=new HashMap();
			map.put("Q_orderCol_N_EQ", (ordercol-1)+"");
			QueryFilter filter=new QueryFilter(map);
			List<ExportSalaryItemOrder> list=this.exportSalaryItemService.getAll(filter);
			for(ExportSalaryItemOrder eso:list){
				eso.setOrderCol(eso.getOrderCol()+1);
				this.exportSalaryItemService.save(eso);
			}
			exportSalaryItemOrder.setOrderCol(ordercol-1);
			this.exportSalaryItemService.save(exportSalaryItemOrder);
		}else if(opt.equals("3")&&!ordercol.equals(len)){//下移
			Map map=new HashMap();
			map.put("Q_orderCol_N_EQ", (ordercol+1)+"");
			QueryFilter filter=new QueryFilter(map);
			List<ExportSalaryItemOrder> list=this.exportSalaryItemService.getAll(filter);
			for(ExportSalaryItemOrder eso:list){
				eso.setOrderCol(eso.getOrderCol()-1);
				this.exportSalaryItemService.save(eso);
			}
			exportSalaryItemOrder.setOrderCol(ordercol+1);
			this.exportSalaryItemService.save(exportSalaryItemOrder);
		}else if(opt.equals("4")&&!ordercol.equals(len)){//置末
			Map map=new HashMap();
			map.put("Q_orderCol_N_GT", ordercol+"");
			QueryFilter filter=new QueryFilter(map);
			List<ExportSalaryItemOrder> list=this.exportSalaryItemService.getAll(filter);
			for(ExportSalaryItemOrder eso:list){
				eso.setOrderCol(eso.getOrderCol()-1);
				this.exportSalaryItemService.save(eso);
			}
			exportSalaryItemOrder.setOrderCol(len);
			this.exportSalaryItemService.save(exportSalaryItemOrder);
		}
		return "success";
	}
	public String getExSaItem(){
		String id=getRequest().getParameter("id");
		exportSalaryItemOrder=this.exportSalaryItemService.get(Long.parseLong(id));
		JSONSerializer json = JsonUtil.getJSONSerializer(new String[] {});
		StringBuffer sb = new StringBuffer("{success:true,data:[");
		sb.append(json.exclude(new String[] { "class"})
				.serialize(exportSalaryItemOrder));
		sb.append("]}");
		setJsonString(sb.toString());
		return "success";
	}
}
