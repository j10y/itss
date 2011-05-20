package com.zsgj.itil.finance.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.itil.finance.entity.FinanceCostCenter;
import com.zsgj.itil.finance.entity.FinanceCostSchedules;
import com.zsgj.itil.finance.service.UserCostInputService;

public class UserCostInputAction extends BaseAction{
	
	private FinanceCostSchedules financeCostSchedules;
	private UserCostInputService userCostInputService;
	public FinanceCostSchedules getFinanceCostSchedules() {
		return financeCostSchedules;
	}
	public void setFinanceCostSchedules(FinanceCostSchedules financeCostSchedules) {
		this.financeCostSchedules = financeCostSchedules;
	}
	public UserCostInputService getUserCostInputService() {
		return userCostInputService;
	}
	public void setUserCostInputService(UserCostInputService userCostInputService) {
		this.userCostInputService = userCostInputService;
	}
	/**
	 * 保存人员成本信息数据
	 * @return
	 */
	public String saveUserCostMesg(){
		String formParams = super.getRequest().getParameter("formParams");
		String costCenter = super.getRequest().getParameter("costCenter");
		JSONObject jo = JSONObject.fromObject(formParams);
		HashMap paramMap = new HashMap();
		Iterator it = jo.keys();
		while(it.hasNext()){
			String key = (String) it.next();
			String value = jo.getString(key);
			paramMap.put(key, value);
		}
		userCostInputService.saveUserCostMesg(financeCostSchedules, paramMap,costCenter);
		super.getResponse().setCharacterEncoding("utf-8");
		String json = "{success:true}";
		PrintWriter pw;
		try {
			pw = super.getResponse().getWriter();
			pw.write(json); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public String findCostCenter(){
		
		int start = HttpUtil.getInt(getRequest(), "start",0);
		int pageSize = HttpUtil.getInt(getRequest(), "pageSize",10);
		int pageNo=start/pageSize+1;
		String propertyValue = null;
		propertyValue = HttpUtil.ConverUnicode(super.getRequest().getParameter("propertyValue"));
		Long total=1L;
		String json = "";
		Page page=userCostInputService.findFinanceCostCenterBySpecialParam(pageNo, pageSize,propertyValue);
		
		List<FinanceCostCenter> list = page.list(); 
		total = page.getTotalCount();
		for(int i=0; i< list.size(); i++){
		FinanceCostCenter costCenter = (FinanceCostCenter)list.get(i); 
		String centerCode = costCenter.getCBZXDM();
		String centerName = costCenter.getCBZXMC();
		String value = centerName+"/"+centerCode;
		Long id = costCenter.getId();
		json += "{id:\""+id+"\",costCenter:\""+value+"\"},";
		}

		if(json.length()==0){
		json = "{success:true,rowCount:"+total+",data:[]}";
		}else{
		json = "{success:true,rowCount:"+total+",data:[" + json.substring(0, json.length()-1) + "]}";
		}
		super.getResponse().setCharacterEncoding("utf-8");
		PrintWriter pw;
		try {
			pw = super.getResponse().getWriter();
			pw.write(json); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return null; 

	}
	
}
