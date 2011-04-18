package com.digitalchina.itil.finance.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.config.entity.ConfigItem;
import com.digitalchina.itil.finance.dao.CostHandInputDao;
import com.digitalchina.itil.finance.entity.FinanceConstant;
import com.digitalchina.itil.finance.entity.FinanceCostCenter;
import com.digitalchina.itil.finance.entity.FinanceCostSchedules;
import com.digitalchina.itil.finance.entity.FinanceCostType;
import com.digitalchina.itil.finance.service.CostHandInputService;
import com.digitalchina.itil.service.entity.ServiceItem;

public class CostHandInputServiceImpl implements CostHandInputService{
	
	private CostHandInputDao costHandInputDao ;

	public CostHandInputDao getCostHandInputDao() {
		return costHandInputDao;
	}

	public void setCostHandInputDao(CostHandInputDao costHandInputDao) {
		this.costHandInputDao = costHandInputDao;
	}
	
	public String findItem(int start, int pageSize,String item,String propertyValue) {
		String json = "";
		Long total=1L;
		int pageNo=start/pageSize+1;
		if(item.equals("1")){
			Page page=costHandInputDao.selectConfigItem(pageNo, pageSize, propertyValue);
			List<ConfigItem> list = page.list();		
			total = page.getTotalCount();
			for(int i=0; i< list.size(); i++){
				ConfigItem configItem = (ConfigItem)list.get(i);			
				String name = configItem.getName();
				String cisn = configItem.getCisn();
				json += "{name:\""+name+"\",itemCode:\""+cisn+"\"},";
			}
		}else{//服务项
			Page page=costHandInputDao.selectServiceItem(pageNo, pageSize,propertyValue);
			List<ServiceItem> list = page.list();		
			total = page.getTotalCount();
			for(int i=0; i< list.size(); i++){
				ServiceItem serviceItem = (ServiceItem)list.get(i);			
				String name = serviceItem.getName();
				String serviceItemCode = serviceItem.getServiceItemCode();
				json += "{name:\""+name+"\",itemCode:\""+serviceItemCode+"\"},";
			}
		}
		if(json.length()==0){
			json = "{success:true,rowCount:"+total+",data:[]}";
		}else{
			json = "{success:true,rowCount:"+total+",data:[" + json.substring(0, json.length()-1) + "]}";
		}
		return json;
	}
	
	public String findCostType(int start, int pageSize,String propertyValue) {
		String json = "";
		Long total=1L;
		int pageNo=start/pageSize+1;
		Page page = costHandInputDao.selectFinanceCostType(pageNo, pageSize, propertyValue);
		List<FinanceCostType> list = page.list();		
		total = page.getTotalCount();
		for(int i=0; i< list.size(); i++){
			FinanceCostType financeCostType = (FinanceCostType)list.get(i);			
			String costTypeCode = financeCostType.getCostTypeCode();
			String costTypeName = financeCostType.getCostTypeName();
			String value = costTypeName+"/"+costTypeCode;
			Long id = financeCostType.getId();
			json += "{id:\""+id+"\",value:\""+value+"\"},";
		}
		
		if(json.length()==0){
			json = "{success:true,rowCount:"+total+",data:[]}";
		}else{
			json = "{success:true,rowCount:"+total+",data:[" + json.substring(0, json.length()-1) + "]}";
		}
		return json;
	}

	public String findReimbursement(int start, int pageSize,String propertyValue) {
		String json = "";
		Long total=1L;
		int pageNo=start/pageSize+1;
		Page page = costHandInputDao.selectReimbursement(pageNo, pageSize,propertyValue);
		List<UserInfo> list = page.list();	
		total = page.getTotalCount();
		for(int i=0; i< list.size(); i++){
			UserInfo userInfo = (UserInfo)list.get(i);			
			String userName = userInfo.getUserName();
			String realName = userInfo.getRealName();
			String departmentName = userInfo.getDepartment().getDepartName();
			String value = realName+"/"+userName+"/"+departmentName;
			Long id = userInfo.getId();
			json += "{id:\""+id+"\",realName:\""+value+"\"},";
		}
		if(json.length()==0){
			json = "{success:true,rowCount:"+total+",data:[]}";
		}else{
			json = "{success:true,rowCount:"+total+",data:[" + json.substring(0, json.length()-1) + "]}";
		}
		return json;
	}

	public String findFinanceCostCenter(int start, int pageSize, String propertyValue) {
		String json = "";
		Long total=1L;
		int pageNo=start/pageSize+1;
		Page page = costHandInputDao.selectFinanceCostCenter(pageNo, pageSize, propertyValue);
		List<FinanceCostCenter> list = page.list();		
		total = page.getTotalCount();
		for(int i=0; i< list.size(); i++){
			FinanceCostCenter financeCostCenter = (FinanceCostCenter)list.get(i);			
			String CBZXDM = financeCostCenter.getCBZXDM();
			String CBZXMC = financeCostCenter.getCBZXMC();
			String value = CBZXMC+"/"+CBZXDM;
			Long id = financeCostCenter.getId();
			json += "{id:\""+id+"\",value:\""+value+"\"},";
		}
		
		if(json.length()==0){
			json = "{success:true,rowCount:"+total+",data:[]}";
		}else{
			json = "{success:true,rowCount:"+total+",data:[" + json.substring(0, json.length()-1) + "]}";
		}
		return json;
	}

	public boolean saveFinanceCostSchedules(JSONObject jo)  {
		UserInfo currentUser = UserContext.getUserInfo();
		HashMap infoMap = new HashMap();
		Iterator it = jo.keys();
		while(it.hasNext()){
			String key = (String) it.next();
			String value = jo.getString(key);
			infoMap.put(key, value);
		}
		FinanceCostSchedules fcs = new FinanceCostSchedules();
		int costReduceType = Integer.valueOf((String)infoMap.get("costReduceType"));
		fcs.setCostReduceType(costReduceType);
		fcs.setCostItem((String)infoMap.get("configItem"));
		// 费用类型，根据成本不同来源得到不同成本类型
		Long id =Long.valueOf((String)infoMap.get("financeCostType"));
		FinanceCostType fct = (FinanceCostType)costHandInputDao.findObjectByProperty("com.digitalchina.itil.finance.entity.FinanceCostType", id , null, null);
		fcs.setFinanceCostType(fct);
		// 成本发生金额
		String amountStr = (String)infoMap.get("costAmount");
		amountStr = amountStr.replaceAll(",", "");
		double amountDouble = Double.valueOf(amountStr);
		BigDecimal amount = BigDecimal.valueOf(amountDouble);
		fcs.setAmount(amount);
		//财务报销人员
		String tempReimbursement = (String)infoMap.get("reimbursement");
		UserInfo reimbursement = null;
		if(!"".equals(tempReimbursement)&&tempReimbursement!=null){
			Long userId = Long.valueOf((String)infoMap.get("reimbursement"));
			reimbursement = (UserInfo)costHandInputDao.findObjectByProperty("com.digitalchina.info.framework.security.entity.UserInfo", userId , null, null);
		}
		fcs.setReimbursement(reimbursement);
		//服务提供商
		fcs.setServiceProvider((String)infoMap.get("serviceProvider"));
		//成本中心
		Long costCenterId =  Long.valueOf((String)infoMap.get("costCenter"));
		FinanceCostCenter costCenter = (FinanceCostCenter)costHandInputDao.findObjectByProperty("com.digitalchina.itil.finance.entity.FinanceCostCenter", costCenterId , null, null);
		fcs.setCostCenter(costCenter);
		//当前费用发生日期
		//借款日期
		String costData = (String)infoMap.get("costDate");
		String borrowDate = (String)infoMap.get("borrowDate");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fcs.setCostDate(simpleDateFormat.parse(costData));
			if(!"".equals(borrowDate)){
				fcs.setBorrowDate(simpleDateFormat.parse(borrowDate));
			}else{
				fcs.setBorrowDate(null);
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 提交日期
		Date costApplyDate = new Date();
		fcs.setCostApplyDate(costApplyDate);
		//借款类型；暂定1为直接报销；2为借款；3为借款后清帐
		String tempBorrowType =(String)infoMap.get("borrowType");
		int borrowType = 0;
		if(!"".equals(tempBorrowType)&&tempBorrowType!=null){
			borrowType = Integer.valueOf(tempBorrowType);
		}
		fcs.setBorrowType(borrowType);
		//费用明细说明
		fcs.setCostDetailExplanation((String)infoMap.get("CostDetailExplanation"));
		//费用数据来源；暂定1为手工录入；2为ERP导入；3为系统计算
		fcs.setCostDataSource(1);
		Long auditUserId =  Long.valueOf((String)infoMap.get("auditPersonId"));
		UserInfo costAuditUser = null;
		costAuditUser = (UserInfo)costHandInputDao.findObjectByProperty("com.digitalchina.info.framework.security.entity.UserInfo", auditUserId , null, null);
		fcs.setCostAuditUser(costAuditUser);
		fcs.setCostApplyUser(currentUser);
		costHandInputDao.save(fcs);
		return true;
	}

	public String findList(Map<String, String> map, int start,
			int pageSize) {
		StringBuffer sb = new StringBuffer();
		String json = "";
		int pageNo=start/pageSize+1;
		Long total=1L;
		Page page=costHandInputDao.selectList(map, pageNo, pageSize);
		List<FinanceCostSchedules> list = page.list();		
		total = page.getTotalCount();
		Long id = null;
		for(int i=0; i< list.size(); i++){
			FinanceCostSchedules fcs = (FinanceCostSchedules)list.get(i);			
			String costReduceType;//成本归结类型,是配置项还是服务项;暂定配置项为1,服务项为2
			String costItem = "";//具体到某个配置项或是服务项，此处记录的是各自编号
			FinanceCostType financeCostType = null ;//费用类型，根据成本不同来源得到不同成本类型
			String financeCostTypeName = "";
			BigDecimal amount = null;//成本发生金额
			UserInfo reimbursement = null;//财务报销人员
			String reimbursementName ="";
			String serviceProvider = "";//服务提供商
			FinanceCostCenter costCenter =null ;//成本中心
			String costCenterName = "";
			String costDateName = "";   //当前费用发生日期
			String borrowDateName = ""; //借款日期
			int borrowType;//借款类型；暂定1为直接报销；2为借款；3为借款后清帐
			String borrowTypeName = "";
			String costDetailExplanation = "";//费用明细说明
			int costDataSource;//费用数据来源；暂定1为手工录入；2为ERP导入；3为系统计算
			String costDataSourceName = "";
			UserInfo costAuditUser = null ;//费用审批人
			String costAuditUserName ="";
			UserInfo costApplyUser = null;//费用提交人
			String costApplyUserName =""; 
			String costApplyDateName = ""; // 费用提交日期
			
			id = fcs.getId();
			costReduceType = (fcs.getCostReduceType()==FinanceConstant.COSTREDUCECONFIG ? "配置项":"服务项");
			costItem = fcs.getCostItem();
			if("配置项".equals(costReduceType)){
				ConfigItem ci = (ConfigItem)costHandInputDao.findObjectByProperty("com.digitalchina.itil.config.entity.ConfigItem", null, "cisn", costItem);
				costItem = ci.getName();
			}else{
				ServiceItem si = (ServiceItem)costHandInputDao.findObjectByProperty("com.digitalchina.itil.service.entity.ServiceItem", null, "serviceItemCode", costItem);
				costItem = si.getName();
			}
			financeCostType = fcs.getFinanceCostType();
			if(financeCostType!=null){
				financeCostTypeName = financeCostType.getCostTypeName()+"/"+financeCostType.getCostTypeCode();
			}
			
			amount = fcs.getAmount();
			
			reimbursement = fcs.getReimbursement();
			reimbursementName = (reimbursement==null ? "":reimbursement.getRealName()+"/"+reimbursement.getUserName());
			
			serviceProvider = (fcs.getServiceProvider()==null ? "":fcs.getServiceProvider());
			costDetailExplanation = (fcs.getCostDetailExplanation()==null ? "":fcs.getCostDetailExplanation());
			
			costCenter = fcs.getCostCenter();
			costCenterName=(costCenter==null ? "":costCenter.getCBZXMC()+"/"+costCenter.getCBZXDM());
		
			costDateName = convertDateToString(fcs.getCostDate(),false);
			borrowDateName = convertDateToString(fcs.getBorrowDate(),false);
			costApplyDateName = convertDateToString(fcs.getCostApplyDate(),true);
			
			borrowType = fcs.getBorrowType();
			switch(borrowType){
				case 1 : borrowTypeName="直接报销";break;
				case 2 : borrowTypeName="借款";break;
				case 3 : borrowTypeName="借款后清帐";break;
				default : borrowTypeName="其他";
			}
			
			costDataSource = fcs.getCostDataSource();
			switch(costDataSource){
				case 1 : costDataSourceName="手工录入";break;
				case 2 : costDataSourceName="ERP导入";break;
				case 3 : costDataSourceName="系统计算";break;
				default : costDataSourceName="其他";
			}
			costAuditUser = fcs.getCostAuditUser();
			costAuditUserName = (costAuditUser==null ? "":costAuditUser.getRealName()+"/"+costAuditUser.getUserName());
			
			costApplyUser = fcs.getCostApplyUser();
			costApplyUserName = (costApplyUser==null ? "":costApplyUser.getRealName()+"/"+costApplyUser.getUserName());

			sb.append("{id:\"").append(id).
			append("\",costReduceType:\"").append(costReduceType).
			append("\",costItem:\"").append(costItem).
			append("\",financeCostTypeName:\"").append(financeCostTypeName).
			append("\",amount:\"").append(amount).
			append("\",reimbursementName:\"").append(reimbursementName).
			append("\",serviceProvider:\"").append(serviceProvider	).
			append("\",costCenterName:\"").append(costCenterName).
			append("\",costDateName:\"").append(costDateName).
			append("\",borrowDateName:\"").append(borrowDateName).
			append("\",borrowTypeName:\"").append(borrowTypeName).
			append("\",costDetailExplanation:\"").append(costDetailExplanation).
			append("\",costDataSourceName:\"").append(costDataSourceName).
			append("\",costAuditUserName:\"").append(costAuditUserName).
			append("\",costApplyUserName:\"").append(costApplyUserName).
			append("\",costApplyDateName:\"").append(costApplyDateName).
			append("\"},");
			
		}
		json = sb.toString();
		if(json.length()==0){
			json = "{success:true,rowCount:"+total+",data:[]}";
		}else{
			json = "{success:true,rowCount:"+total+",data:[" + json.substring(0, json.length()-1) + "]}";
		}
		return json;
	}

	public Object findObjectByProperty(String s, Long id, String propertyName,
			Object propertyValue) {
		return costHandInputDao.findObjectByProperty(s, id, propertyName, propertyValue);
	}
	
	/**
	 * 转换Date为字符串
	 * @Methods Name convertStringToDate
	 * @Create In Jul 6, 2009 By liaogs1
	 * @param dateStr
	 * @return Date
	 */
	private String convertDateToString(Date date,boolean isTime){
		String dateStr = null;
		if(date==null){
			return "";
		}
		if(isTime){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
			dateStr = sdf.format(date);
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			dateStr = sdf.format(date);
		}
		return dateStr;
	}
	
}
