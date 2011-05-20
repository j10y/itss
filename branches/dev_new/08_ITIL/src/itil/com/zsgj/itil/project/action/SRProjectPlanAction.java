package com.zsgj.itil.project.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.UserTableSetting;
import com.zsgj.info.appframework.pagemodel.PageManager;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelColumn;
import com.zsgj.info.appframework.pagemodel.service.PageModelService;
import com.zsgj.info.appframework.pagemodel.service.PagePanelService;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.itil.config.extlist.entity.SRProjectPlan;
import com.zsgj.itil.project.service.SRProjectPlanService;
/**
 * 项目计划用ACTION
 * @Class Name ProjectPlanAction
 * @Author lee
 * @Create In Mar 13, 2009
 */
public class SRProjectPlanAction extends BaseAction{
	private MetaDataManager metaDataManager = (MetaDataManager) super.getBean("metaDataManager");
	private SRProjectPlanService planService = (SRProjectPlanService)getBean("SRprojectPlanService");
//	private PagePanelService pagePanelService = (PagePanelService) getBean("pagePanelService");
//	private PageManager pageManager = (PageManager) getBean("pageManager");
	/**
	 * 为项目计划树状列表提供数据
	 * @Methods Name getGridTreeData
	 * @Create In Mar 13, 2009 By lee
	 * @return String
	 */
	public String getGridTreeData(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String id=request.getParameter("rootPlanId");
		String json="";
		if(id==null||"".equals(id)||"0".equals(id)){
			return null;
		}else{
			SRProjectPlan projectPlan = planService.findProjectPlanById(id);
			json = this.getJson(projectPlan);
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取树信息数据
	 * @Methods Name getJson
	 * @Create In Mar 13, 2009 By lee
	 * @param projectPlan
	 * @return String
	 */
	private String getJson(SRProjectPlan projectPlan){
		int parent=0;
		int level=1;
		int lft=1;
		int rgt=1;
		String json = "{";
		List subList=new ArrayList();
		List paramete=new ArrayList();
		List<SRProjectPlan> childPlans = planService.findChildPlans(projectPlan);
		List<UserTableSetting> utss = metaDataManager.getUserColumnForList(SRProjectPlan.class);
		Map<String,Object> dataMap = metaDataManager.getEntityDataForLook(projectPlan);
		if(!childPlans.isEmpty()){
			paramete.add(projectPlan.getId());
			paramete.add(level);
			paramete.add(lft);
			subList = this.initChildPlans(projectPlan, childPlans.size(), paramete);
			for(UserTableSetting uts : utss){
        		Column column = uts.getColumn();
        		String tableName = column.getSystemMainTable().getTableName();
        		String propertyName = column.getPropertyName();	
				Object value = dataMap.get(propertyName);
				if(value==null) value="";
        			json+="\""+tableName+"$"+propertyName+"\":\""+value+"\",";
        	}
			json += "_parent:"+parent+",";
			json += "_level:"+level+",";
			json += "_lft:"+lft+",";
			int subNum=0;
			if(!subList.get(1).equals("")){
			   subNum=((Integer) subList.get(1)).intValue();
			}
			rgt=lft+subNum*2+1;
			json += "_rgt:"+rgt+",";
			json += "_is_leaf:"+false+"},"; 
			if(!subList.get(0).equals("")){
				json +=subList.get(0);
			 }
		}else{
			for(UserTableSetting uts : utss){
        		Column column = uts.getColumn();
        		String tableName = column.getSystemMainTable().getTableName();
        		String propertyName = column.getPropertyName();	
				Object value = dataMap.get(propertyName);
				if(value==null) value="";
        			json+="\""+tableName+"$"+propertyName+"\":\""+value+"\",";
        	}
			json += "_parent:"+parent+",";
			json += "_level:"+level+",";
			json += "_lft:"+lft+",";
			rgt=lft+1;
			json += "_rgt:"+rgt+",";
			json += "_is_leaf:"+true+"},"; 
		}
		lft=rgt+1;
		if(json.equals("")){
			json="[]";
		}else{
			json = json.substring(0, json.length()-1);
			json="["+json+"]";
		}
		return json;
	}
	/**
	 * 递归生成子节点数据
	 * @Methods Name initChildPlans
	 * @Create In Mar 13, 2009 By lee
	 * @param parentPlan
	 * @param childNum
	 * @param param
	 * @return List
	 */
	private List initChildPlans(SRProjectPlan parentPlan,int childNum,List param){
		List<SRProjectPlan> childPlans = planService.findChildPlans(parentPlan);
		List list=new ArrayList();
		List subList=new ArrayList();
		List paramete=new ArrayList();
		int parent1=((Long)param.get(0)).intValue();
		int level=((Integer)param.get(1)).intValue()+1;
		int lft=((Integer)param.get(2)).intValue()+1;
		int rgt=0;
		String temp = "";
		List<UserTableSetting> utss = metaDataManager.getUserColumnForList(SRProjectPlan.class);
		for(SRProjectPlan plan : childPlans){
			Map<String,Object> dataMap = metaDataManager.getEntityDataForLook(plan);
			List<SRProjectPlan> grandchilds = planService.findChildPlans(plan);
			if(!grandchilds.isEmpty()){
				paramete.clear();
				paramete.add(plan.getId());
				paramete.add(level);
				paramete.add(lft);
				subList=this.initChildPlans(plan,grandchilds.size(),paramete);
				temp += "{";
				for(UserTableSetting uts : utss){
	        		Column column = uts.getColumn();
	        		String tableName = column.getSystemMainTable().getTableName();
	        		String propertyName = column.getPropertyName();	
					Object value = dataMap.get(propertyName);
					if(value==null) value="";
					temp+="\""+tableName+"$"+propertyName+"\":'"+value+"',";
	        	}
				temp += "_parent:"+parent1+",";
				temp += "_level:"+level+",";
				temp += "_lft:"+lft+",";
				int subNum=0;
				if(!subList.get(1).equals("")){
				   subNum=((Integer) subList.get(1)).intValue();
				   childNum+=((Integer) subList.get(1)).intValue();
				}
				rgt=lft+subNum*2+1;
				temp += "_rgt:"+rgt+",";
				temp += "_is_leaf:"+false+"},"; 
				if(!subList.get(0).equals("")){
					temp +=subList.get(0);
				 }
			}else{
				temp += "{";
				for(UserTableSetting uts : utss){
	        		Column column = uts.getColumn();
	        		String tableName = column.getSystemMainTable().getTableName();
	        		String propertyName = column.getPropertyName();	
					Object value = dataMap.get(propertyName);
					if(value==null) value="";
					temp+="\""+tableName+"$"+propertyName+"\":'"+value+"',";
	        	}
				temp += "_parent:"+parent1+",";
				temp += "_level:"+level+",";
				temp += "_lft:"+lft+",";
				rgt=lft+1;
				temp += "_rgt:"+rgt+",";
				temp += "_is_leaf:"+true+"},"; 
			}
			lft=rgt+1;
		}
		list.add(temp);
		list.add(childNum);
		return list;
	}
}
