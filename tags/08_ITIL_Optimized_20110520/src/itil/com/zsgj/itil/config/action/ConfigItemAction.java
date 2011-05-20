package com.zsgj.itil.config.action;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.zsgj.info.appframework.extjs.servlet.CoderForList;
import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.service.SystemColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainTableService;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.ApplicationException;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.util.DateUtil;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.itil.config.entity.CIBatchModify;
import com.zsgj.itil.config.entity.CIBatchModifyPlan;
import com.zsgj.itil.config.entity.CIBatchModifyShip;
import com.zsgj.itil.config.entity.CIRelationShip;
import com.zsgj.itil.config.entity.CIRelationShipDisplay;
import com.zsgj.itil.config.entity.CIRelationShipGrade;
import com.zsgj.itil.config.entity.CIRelationShipType;
import com.zsgj.itil.config.entity.ConfigItem;
import com.zsgj.itil.config.entity.ConfigItemExtendInfo;
import com.zsgj.itil.config.entity.ConfigItemNecessaryRel;
import com.zsgj.itil.config.entity.ConfigItemStatus;
import com.zsgj.itil.config.entity.ConfigItemType;
import com.zsgj.itil.config.extci.entity.Server;
import com.zsgj.itil.config.extci.entity.ServiceEngineer;
import com.zsgj.itil.config.service.ConfigItemService;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.entity.ServiceItemType;

public class ConfigItemAction extends BaseAction {
	private ConfigItemService configItemService;
	private MetaDataManager metaDataManager;
	private SystemColumnService systemColumnService = (SystemColumnService) ContextHolder.getBean("systemColumnService");
	private SystemMainTableService systemMainTableService=(SystemMainTableService) ContextHolder.getBean("systemMainTableService");
	/**
	 * 1.根据配置项类型获取扩展面板pagepanel的name
	 * 2.如果request中的参数configItemId不为null，按此主数据configItemId去取扩展数据的id
	 * 3.判断此配置项是否为孤立的配置项(在变更时，孤立的配置项需要显示变更为孤立状态之后将会删除的关系)
	 * 4.如果此配置项类型为服务器，判断当前用户是否有权限查看服务器ILO远程管理密码
	 *   密码只对此服务器的维护工程师和拥有配置-服务器管理员角色的用户可见
	 * @Methods Name getPagePanelName
	 * @Create In Jan 26, 2010 By duxh
	 * @Return String
	 */
	public String getPagePanelName() throws Exception{
		String configItemTypeId=getRequest().getParameter("configItemTypeId");
		String configItemId=getRequest().getParameter("configItemId");
		ConfigItemType type=configItemService.findObjects(ConfigItemType.class, "id", new Object[]{Long.valueOf(configItemTypeId)}, "pagePanel").get(0);
		PagePanel panel=type.getPagePanel();
		String extendId=null;
		boolean isOrphan=false;
		boolean displayServerPassword=false;
		if(!configItemId.equals("")){
			ConfigItem configItem=(ConfigItem) getService().find(ConfigItem.class, configItemId);
			List<String>  status=new ArrayList<String>();
			status.add(ConfigItemStatus.CONFIGITEMSTATUS_ARCHIVED);
			status.add(ConfigItemStatus.CONFIGITEMSTATUS_DISABLED);
			status.add(ConfigItemStatus.CONFIGITEMSTATUS_LOAN);
			status.add(ConfigItemStatus.CONFIGITEMSTATUS_STANDBY);
			if(configItem.getConfigItemStatus()!=null&&status.contains(configItem.getConfigItemStatus().getEnname())){
				isOrphan=true;
			}
			if(configItem.getConfigItemType().getId().toString().equals(configItemTypeId))
				extendId=((ConfigItemExtendInfo)getService().findUnique(ConfigItemExtendInfo.class, "configItem", configItem)).getExtendDataId().toString();
			if(Class.forName(type.getClassName()).isAssignableFrom(Server.class)){
				if(UserContext.getUserInfo()!=null){
					//服务器对应的维护工程师
					List<Long> userId=configItemService.findServerEngineer(configItem.getCisn());
					if(userId.contains(UserContext.getUserInfo().getId())){
						displayServerPassword=true;
					}else{
						//拥有配置-服务器管理员角色的用户
						Role role=(Role) getService().findUnique(Role.class, "keyword", Server.ROLE_SERVERADMINISTRATOR);
						if(role!=null){
							Set<UserInfo> users=role.getUserInfos();
							for(UserInfo user:users){
								if(user.getId().compareTo(UserContext.getUserInfo().getId())==0){
									displayServerPassword=true;
								}
							}
						}
						
					}
				}
			}
		}
		String json="{panelName:\""+panel.getName()+"\",isOrphan:"+isOrphan+",panelTitle:\""+panel.getTitle()+"\""+",displayServerPassword:"+displayServerPassword;
		if(extendId!=null)
			json=json+",extendId:\""+extendId+"\"";
		json=json+"}";
		getResponse().setContentType("text/plain");
		getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out = getResponse().getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
	}
	
	/**
	 * 查看某配置项的关系
	 * @Methods Name findCIRel
	 * @Create In Apr 22, 2010 By duxh
	 * @Return String
	 */
	public String findCIRel(){
		try {
			String json="";
			String oldId=getRequest().getParameter("oldId");
			ConfigItem ci=(ConfigItem) getService().find(ConfigItem.class, oldId);
			List<CIRelationShip> rels=configItemService.findCIRelationShip(ci);
			json+="{rels:\"";
			for(CIRelationShip ship:rels){
				String parentCode="";
				String childCode="";
				if(ship.getParentConfigItemCode()!=null){
					parentCode=ship.getParentConfigItemCode();
				}else if(ship.getParentServiceItemCode()!=null){
					parentCode=ship.getParentServiceItemCode();
				}
				if(ship.getChildConfigItemCode()!=null){
					childCode=ship.getChildConfigItemCode();
				}
				else if(ship.getChildServiceItemCode()!=null){
					childCode=ship.getChildServiceItemCode();
				}
				json+="<p>【父】"+parentCode+"--->【子】"+childCode+".</p>";
			}
			json+="\"}";
			getResponse().setContentType("text/plain");
			getResponse().setCharacterEncoding("UTF-8");
			PrintWriter out = getResponse().getWriter();
			out.write(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return null;
	}
	/**
	 * 变更配置项的状态为"备用、禁用、已归档、租出"时验证及提示
	 * 如果已经为配置项新建或变更了关系,不允许将其状态置为孤立
	 * 否则将当前有效的关系回写
	 * @Methods Name saveOrphanCIAlert
	 * @Create In Apr 21, 2010 By duxh
	 * @Return String
	 */
	public String saveOrphanCIAlert(){
		try {
			String json="";
			String modifyId=getRequest().getParameter("modifyId");
			String cisn=getRequest().getParameter("cisn");
			String statusString=getRequest().getParameter("configItemStatus");
			ConfigItemStatus configItemStatus=(ConfigItemStatus) getService().find(ConfigItemStatus.class, statusString);
			List<String>  status=new ArrayList<String>();
			status.add(ConfigItemStatus.CONFIGITEMSTATUS_ARCHIVED);
			status.add(ConfigItemStatus.CONFIGITEMSTATUS_DISABLED);
			status.add(ConfigItemStatus.CONFIGITEMSTATUS_LOAN);
			status.add(ConfigItemStatus.CONFIGITEMSTATUS_STANDBY);
			if(configItemStatus!=null&&status.contains(configItemStatus.getEnname())){
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("batchModify.id", Long.valueOf(modifyId));
				List<String> fetchProperty=new ArrayList<String>();
				fetchProperty.add("newCIRelationShip");
				List<CIBatchModifyPlan> plans=configItemService.findObjects(CIBatchModifyPlan.class, map, fetchProperty);
				//判断本次变更申请是否为此配置项新建或变更了关系
				for(CIBatchModifyPlan plan:plans){
					CIRelationShip rel=plan.getNewCIRelationShip();
					if(rel!=null){
						if(rel.getParentConfigItemCode()!=null&&rel.getParentConfigItemCode().equals(cisn)||
						   rel.getChildConfigItemCode()!=null&&rel.getChildConfigItemCode().equals(cisn)){
							json="{error:\"您已经为配置项"+cisn+"新建或变更了关系,不允许将其状态置为“"+configItemStatus.getName()+"”.\"}";
						}
					}
				}
				if(json.length()==0){
					List<CIRelationShip> rels=configItemService.findValidCIRelationShip(cisn);
					if(!rels.isEmpty()){
						json+="{alert:\"";
						for(CIRelationShip ship:rels){
							String parentCode="";
							String childCode="";
							if(ship.getParentConfigItemCode()!=null){
								parentCode=ship.getParentConfigItemCode();
							}else if(ship.getParentServiceItemCode()!=null){
								parentCode=ship.getParentServiceItemCode();
							}
							if(ship.getChildConfigItemCode()!=null){
								childCode=ship.getChildConfigItemCode();
							}
							else if(ship.getChildServiceItemCode()!=null){
								childCode=ship.getChildServiceItemCode();
							}
							json+="<p>【父】"+parentCode+"--->【子】"+childCode+".</p>";
						}
						json+="\"}";
					}
				}
			}
			getResponse().setContentType("text/plain");
			getResponse().setCharacterEncoding("UTF-8");
			PrintWriter out = getResponse().getWriter();
			out.write(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return null;
	}
	/**
	 * 查找是否有不存在的可选必要关系
	 * 有则返回(排除孤立的配置项)
	 * @Methods Name findHasNotExistOptionalRel
	 * @Create In Jul 8, 2010 By duxh
	 * @return
	 * @return String
	 */
	public String findHasNotExistOptionalRel(){
		try {
			String modifyId=getRequest().getParameter("modifyId");
			String configItemTypeId=getRequest().getParameter("configItemTypeId");
			String cisn=getRequest().getParameter("cisn");
			String cid=getRequest().getParameter("cid");
			String configItemStatusStr=getRequest().getParameter("configItemStatus");
			ConfigItemStatus configItemStatus=null;
			if(cid!=null&&cid.trim().length()!=0){
				ConfigItem ci=(ConfigItem) getService().find(ConfigItem.class, cid);
				configItemStatus=ci.getConfigItemStatus();
			}else{
				configItemStatus=(ConfigItemStatus) getService().findUnique(ConfigItemStatus.class, "id", Long.valueOf(configItemStatusStr));
			}
			List<String>  status=new ArrayList<String>();
			status.add(ConfigItemStatus.CONFIGITEMSTATUS_ARCHIVED);
			status.add(ConfigItemStatus.CONFIGITEMSTATUS_DISABLED);
			status.add(ConfigItemStatus.CONFIGITEMSTATUS_LOAN);
			status.add(ConfigItemStatus.CONFIGITEMSTATUS_STANDBY);
			if(configItemStatus!=null){
				if(!status.contains(configItemStatus.getEnname())){	//排除孤立的配置项
					List<ConfigItemNecessaryRel> notExistOptionalRel=configItemService.findHasNotExistOptionalRel(Long.valueOf(modifyId), Long.valueOf(configItemTypeId), cisn);
					String message="";
					for(ConfigItemNecessaryRel rel:notExistOptionalRel){
						message+="<li><p>存在可选必要关系：“"+rel.getConfigItemType().getName()+
						          "”-->“"+(rel.getOtherConfigItemType()==null?"":rel.getOtherConfigItemType().getName())+"”。</p>"+
						          "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;关系描述："+(rel.getDescription()==null?"":rel.getDescription())+"</p></li>";
					}
					if(message.trim().length()!=0){
						getResponse().setContentType("text/plain");
						getResponse().setCharacterEncoding("UTF-8");
						PrintWriter out = getResponse().getWriter();
						out.write("{message:\""+message+"\"}");
						out.flush();
						out.close();
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return null;
	}
	/**
	 * 保存、更新配置项信息包括基本信息，财务信息，扩展信息、变更计划
	 * @Methods Name saveOrUpdateConfigItemAndPlan
	 * @Create In Jan 26, 2010 By duxh
	 * @Return String
	 */
	public String saveOrUpdateConfigItemAndPlan(){
		try {
			String basicPanel=getRequest().getParameter("basicPanel");
			String financePanel=getRequest().getParameter("financePanel");
			String extendPanel=getRequest().getParameter("extendPanel");
			String planPanel=getRequest().getParameter("planPanel");
			String modifyId=getRequest().getParameter("modifyId");
			String oldId=getRequest().getParameter("oldId");
			
			String createAllNecessaryRelStr=getRequest().getParameter("createAllNecessaryRel");
			//是否生成所有必要关系
			boolean createAllNecessaryRel=false;
			if(createAllNecessaryRelStr!=null&&createAllNecessaryRelStr.trim().length()!=0){
				createAllNecessaryRel=Boolean.valueOf(createAllNecessaryRelStr);
			}
			
			JSONObject basicJson=JSONObject.fromObject(basicPanel);
			JSONObject financeJson=JSONObject.fromObject(financePanel);
			JSONObject extendJson=JSONObject.fromObject(extendPanel);
			JSONObject planJson=JSONObject.fromObject(planPanel);
			
			Map basicMap=new HashMap();
			Map financeMap=new HashMap();
			Map extendMap=new HashMap();
			Map planMap=new HashMap();
			
			Set<String> basicSet=basicJson.keySet();
			Set<String> financeSet=financeJson.keySet();
			Set<String> extendSet=extendJson.keySet();
			Set<String> planSet=planJson.keySet();
			
			for (String key:basicSet) {
				String keyString = key.split("\\$")[1];
				String value = basicJson.getString(key);
				basicMap.put(keyString, value);
			}
			for (String key:financeSet) {
				String keyString = key.split("\\$")[1];
				String value = financeJson.getString(key);
				financeMap.put(keyString, value);
			}
			for (String key:extendSet) {
				String keyString = key.split("\\$")[1];
				String value = extendJson.getString(key);
				extendMap.put(keyString, value);
			}
			for (String key:planSet) {
				String keyString = key.split("\\$")[1];
				String value = planJson.getString(key);
				planMap.put(keyString, value);
			}
			planMap.put("oldConfigItem", oldId);
			CIBatchModifyPlan plan=configItemService.saveOrUpdateConfigItemAndPlan(basicMap, financeMap, extendMap,planMap,modifyId,createAllNecessaryRel);
			String json="{id:"+plan.getNewConfigItem().getId()+",pid:\""+plan.getId()+"\"}";
			getResponse().setContentType("text/plain");
			getResponse().setCharacterEncoding("UTF-8");
			PrintWriter out = getResponse().getWriter();
			out.write(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return null;
	}
	/**
	 * 保存或更新关系及其变更计划
	 * @Methods Name saveOrUpdateRelAndPlan
	 * @Create In Mar 26, 2010 By duxh
	 * @Return String
	 */
	public String saveOrUpdateRelAndPlan(){
		try {
			String relPanel=getRequest().getParameter("relPanel");
			String relPlanPanel=getRequest().getParameter("relPlanPanel");
			String modifyId=getRequest().getParameter("modifyId");
			String oldRid=getRequest().getParameter("oldRid");
			JSONObject relJson=JSONObject.fromObject(relPanel);
			JSONObject relPlanJson=JSONObject.fromObject(relPlanPanel);
			Map<String,String> relMap=new HashMap<String,String>();
			Map relPlanMap=new HashMap();
			Set<String> relSet=relJson.keySet();
			Set<String> relPlanSet=relPlanJson.keySet();
			for (String key:relSet) {
				String value = relJson.getString(key);
				relMap.put(key, value);
			}
			for (String key:relPlanSet) {
				String keyString = key.split("\\$")[1];
				String value = relPlanJson.getString(key);
				relPlanMap.put(keyString, value);
			}
			relPlanMap.put("oldCIRelationShip", oldRid);
			CIBatchModifyPlan plan=configItemService.saveOrUpdateRelAndPlan(relMap, relPlanMap, modifyId);
			getResponse().setContentType("text/plain");
			getResponse().setCharacterEncoding("UTF-8");
			PrintWriter out = getResponse().getWriter();
			out.println("{id:"+plan.getNewCIRelationShip().getId()+",pid:\""+plan.getId()+"\"}");
			out.flush();
			out.close();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}

	/**
	 * 根据条件分页查询所有正式的配置项或服务项
	 * @throws IOException 
	 * @Methods Name findItem
	 * @Create In Feb 22, 2010 By duxh
	 * @Return String
	 */
	public String findItem() throws IOException{
		int start = HttpUtil.getInt(getRequest(), "start",0);
		int pageSize = HttpUtil.getInt(getRequest(), "pageSize",10);
		int pageNo=start/pageSize+1;
		String modifyId = getRequest().getParameter("modifyId");
		String item = getRequest().getParameter("item");
		String itemName = getRequest().getParameter("name");
		String itemType = getRequest().getParameter("type");
		String itemCode = getRequest().getParameter("code");
		Long itemTypeId=null;
		if(itemType!=null&&!itemType.trim().equals(""))
			itemTypeId=Long.valueOf(itemType);
		Long total=1L;
		String json = "";
		//配置项
		if(item.equals("ci")){
			Page page=configItemService.findConfigItem(modifyId,itemName, itemCode, itemTypeId, pageNo, pageSize);
			List<ConfigItem> list = page.list();		
			 total = page.getTotalCount();
			for(int i=0; i< list.size(); i++){
				ConfigItem configItem = (ConfigItem)list.get(i);			
				Long id = configItem.getId();		
				String name = configItem.getName();
				String type = "";
				String typeId = "";
				String cisn = configItem.getCisn();
				if(configItem.getConfigItemType()!=null){
					 type = configItem.getConfigItemType().getName();
					 typeId = configItem.getConfigItemType().getId().toString();
				}
				json += "{id:"+id+",Type:\""+type+"\",TypeId:\""+typeId+"\",name:\""+name+"\",itemCode:\""+cisn+"\"},";
			}
		}else{//服务项
			Page page=configItemService.findServiceItem(itemName, itemCode, itemTypeId, pageNo, pageSize);
			List<ServiceItem> list = page.list();		
			 total = page.getTotalCount();
			
			for(int i=0; i< list.size(); i++){
				ServiceItem serviceItem = (ServiceItem)list.get(i);			
				Long id = serviceItem.getId();		
				String name = serviceItem.getName();
				String type = "";
				String typeId = "";
				String serviceItemCode = serviceItem.getServiceItemCode();
				if(serviceItem.getServiceItemType()!=null){
					type = serviceItem.getServiceItemType().getName();
					typeId = serviceItem.getServiceItemType().getId().toString();
				}
				json += "{id:"+id+",Type:\""+type+"\",TypeId:\""+typeId+"\",name:\""+name+"\",itemCode:\""+serviceItemCode+"\"},";
			}
		}
		if(json.length()==0){
			json = "{success:true,rowCount:"+total+",data:[]}";
		}else{
			json = "{success:true,rowCount:"+total+",data:[" + json.substring(0, json.length()-1) + "]}";
		}
		super.getResponse().setCharacterEncoding("utf-8");
		PrintWriter pw = super.getResponse().getWriter();	
		pw.write(json);		
		return null;
	}
	/**
	 * 获取所有关系信息(分为基本查询和高级查询)
	 * @Methods Name getRelList
	 * @Create In Mar 28, 2010 By duxh
	 * @Return String
	 */
	public String getRelList(){
		try {
			int start = HttpUtil.getInt(getRequest(), "start",0);
			int pageSize = HttpUtil.getInt(getRequest(), "pageSize",10);
			//基本查询参数
			String basicSearchFormString=getRequest().getParameter("basicSearchForm");
			//高级查询参数
			String advancedSearchFormString=getRequest().getParameter("advancedSearchForm");
			Map basicMap=new HashMap();
			if(basicSearchFormString!=null&&basicSearchFormString.trim().length()!=0){
				JSONObject basicSearchFormJson=JSONObject.fromObject(basicSearchFormString);
				Set<String> basicSearchSet=basicSearchFormJson.keySet();
				for (String key:basicSearchSet) {
					String value = basicSearchFormJson.getString(key).trim();
					if(value.length()!=0){
						basicMap.put(key, value);
					}
				}
			}
			Map advancedMap=new HashMap();
			if(advancedSearchFormString!=null&&advancedSearchFormString.trim().length()!=0){
				JSONObject advancedSearchFormJson=JSONObject.fromObject(advancedSearchFormString);
				Set<String> advancedSearchFormSet=advancedSearchFormJson.keySet();
				for (String key:advancedSearchFormSet) {
					String value = advancedSearchFormJson.getString(key).trim();
					if(value.length()!=0){
						advancedMap.put(key, value);
					}
				}
			}
			Page page=configItemService.findRelList(basicMap,advancedMap, start, pageSize);
			List<CIRelationShipDisplay> rels = page.list();
			Long total=page.getTotalCount();
			if(total.compareTo(0L)==0){
				total=1L;
			}
			StringBuilder json = new StringBuilder("{success: true, rowCount:" + total + ",data:[");
			for (int i = 0; i < rels.size(); i++) {
				CIRelationShipDisplay rel=rels.get(i);
				if (i != 0)
					json.append(",");
				json.append("{");
				json.append("id:\""+rel.getId()+"\",");
				json.append("relationShipType:\""+(rel.getRelationShipType()==null?"":rel.getRelationShipType())+"\",");
				json.append("relationShipGrade:\""+(rel.getRelationShipGrade()==null?"":rel.getRelationShipGrade())+"\",");
				json.append("attachQuotiety:\""+(rel.getAttachQuotiety()==null?"":rel.getAttachQuotiety())+"\",");
				json.append("atechnoInfo:\""+(rel.getAtechnoInfo()==null?"":rel.getAtechnoInfo())+"\",");
				json.append("btechnoInfo:\""+(rel.getBtechnoInfo()==null?"":rel.getBtechnoInfo())+"\",");
				json.append("otherInfo:\""+(rel.getOtherInfo()==null?"":rel.getOtherInfo())+"\",");
				json.append("parentType:\""+(rel.getParentItemType()==null?"":rel.getParentItemType())+"\",");
				json.append("parentName:\""+(rel.getParentItemName()==null?"":rel.getParentItemName())+"\",");
				json.append("parentCode:\""+(rel.getParentItemCode()==null?"":rel.getParentItemCode())+"\",");
				json.append("parentItem:\""+(rel.getParentItem()==null?"":rel.getParentItem())+"\",");
				json.append("childType:\""+(rel.getChildItemType()==null?"":rel.getChildItemType())+"\",");
				json.append("childName:\""+(rel.getChildItemName()==null?"":rel.getChildItemName())+"\",");
				json.append("childCode:\""+(rel.getChildItemCode()==null?"":rel.getChildItemCode())+"\",");
				json.append("childItem:\""+(rel.getChildItem()==null?"":rel.getChildItem())+"\"");
				json.append("}");
			}
			json.append("]");
			json.append("}");
			getResponse().setContentType("text/plain");
			getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = getResponse().getWriter();	
			pw.write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return null;
	}
	/**
	 * 获取某配置项可以替换的关系信息
	 * @Methods Name getReplaceRelList
	 * @Create In Mar 28, 2010 By duxh
	 * @Return String
	 */
	public String getReplaceRelList(){
		try {
			int start = HttpUtil.getInt(getRequest(), "start",0);
			int pageSize = HttpUtil.getInt(getRequest(), "pageSize",10);
			int pageNo=start/pageSize+1;
			String itemCode=getRequest().getParameter("itemCode");
			String modifyId=getRequest().getParameter("modifyId");
			Page page=configItemService.findReplaceRelList(itemCode,Long.valueOf(modifyId), pageNo, pageSize);
			List<CIRelationShip> rels = page.list();
			Long total=page.getTotalCount();
			if(total.compareTo(0L)==0){
				total=1L;
			}
			StringBuilder json = new StringBuilder("{success: true, rowCount:" + total + ",data:[");
			for (int i = 0; i < rels.size(); i++) {
				CIRelationShip rel=rels.get(i);
				if (i != 0)
					json.append(",");
				json.append("{");
				json.append("id:\""+rel.getId()+"\",");
				json.append("relationShipType:\""+(rel.getRelationShipType()==null?"":rel.getRelationShipType().getName())+"\",");
				json.append("relationShipGrade:\""+(rel.getRelationShipGrade()==null?"":rel.getRelationShipGrade().getName())+"\",");
				json.append("attachQuotiety:\""+(rel.getAttachQuotiety()==null?"":rel.getAttachQuotiety())+"\",");
				json.append("atechnoInfo:\""+(rel.getAtechnoInfo()==null?"":rel.getAtechnoInfo())+"\",");
				json.append("btechnoInfo:\""+(rel.getBtechnoInfo()==null?"":rel.getBtechnoInfo())+"\",");
				json.append("otherInfo:\""+(rel.getOtherInfo()==null?"":rel.getOtherInfo())+"\",");
				if(rel.getParentConfigItemCode()!=null){
					json.append("parentType:\""+rel.getParentConfigItemType().getName()+"\",");
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("cisn", rel.getParentConfigItemCode());
					map.put("status", ConfigItem.VALID_STATUS);
					List<ConfigItem> ci=configItemService.findObjects(ConfigItem.class, map, null);
					if(!ci.isEmpty()){
						json.append("parentName:\""+ci.get(0).getName()+"\",");
					}
					json.append("parentCode:\""+rel.getParentConfigItemCode()+"\",");
				}else if(rel.getParentServiceItemCode()!=null){
					json.append("parentType:\""+rel.getParentServiceItemType().getName()+"\",");
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("serviceItemCode",rel.getParentServiceItemCode());
					map.put("deleteFlag", ServiceItem.DELETE_FALSE);
					List<ServiceItem> si=configItemService.findObjects(ServiceItem.class, map, null);
					if(!si.isEmpty()){
						json.append("parentName:\""+si.get(0).getName()+"\",");
					}
					json.append("parentCode:\""+rel.getParentServiceItemCode()+"\",");
				}
				if(rel.getChildConfigItemCode()!=null){
					json.append("childType:\""+rel.getChildConfigItemType().getName()+"\",");
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("cisn", rel.getChildConfigItemCode());
					map.put("status", ConfigItem.VALID_STATUS);
					List<ConfigItem> ci=configItemService.findObjects(ConfigItem.class, map, null);
					if(!ci.isEmpty()){
						json.append("childName:\""+ci.get(0).getName()+"\",");
					}
					json.append("childCode:\""+rel.getChildConfigItemCode()+"\"");
				}else if(rel.getChildServiceItemCode()!=null){
					json.append("childType:\""+rel.getChildServiceItemType().getName()+"\",");
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("serviceItemCode",rel.getChildServiceItemCode());
					map.put("deleteFlag", ServiceItem.DELETE_FALSE);
					List<ServiceItem> si=configItemService.findObjects(ServiceItem.class, map, null);
					if(!si.isEmpty()){
						json.append("childName:\""+si.get(0).getName()+"\",");
					}
					json.append("childCode:\""+rel.getChildServiceItemCode()+"\"");
				}
				json.append("}");
			}
			json.append("]");
			json.append("}");
			getResponse().setContentType("text/plain");
			getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = getResponse().getWriter();	
			pw.write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return null;
	}
	/**
	 * 查询配置项或服务项的所有类型
	 * @Methods Name findItemTypeByItem
	 * @Create In Feb 23, 2010 By duxh
	 * @Return String
	 */
	public String findItemTypeByItem(){
		String item= super.getRequest().getParameter("item");
		String json = "";
		//配置项
		if("ci".equals(item)){
			List cType = super.getService().find(ConfigItemType.class,"deployFlag",ConfigItemType.DEPLOYFLAG_YES);	
			for(int i=0; i< cType.size(); i++){
				ConfigItemType configItemType = (ConfigItemType)cType.get(i);			
				Long id = configItemType.getId();
				String name = configItemType.getName();
				json += "{\"id\":"+id+",\"name\":\""+name+"\"},";
			}
		}else{//服务项
			List sType = super.getService().find(ServiceItemType.class,"deployFlag",ServiceItemType.DEPLOYFLAG_YES);
			for(int i=0;i<sType.size();i++){
				ServiceItemType serviceItemType =(ServiceItemType)sType.get(i);
				Long id =serviceItemType.getId();
				String name = serviceItemType.getName();
				json += "{\"id\":"+id+",\"name\":\""+name+"\"},";
			}
		}
		//json = "{data:[" + json.substring(0, json.length()-1) + "]}";
		json = "{data:[" + (json.length()>1 ? json.substring(0, json.length()-1) : "") + "]}";
		try {			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(json);		
		} catch (IOException e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return null;
	}
	/**
	 * 列出配置项关系树。(由系统中有效的服务项开始)
	 * @Methods Name listConfigItemRelation
	 * @Create In Nov 18, 2009 By duxh
	 * @return String
	 */
	public String listConfigItemRelation(){
		try {
			String itemCode=getRequest().getParameter("itemCode");
			List<TreeNode> treeNodes=new ArrayList<TreeNode>();
			List<ServiceItem> serviceItems=new ArrayList<ServiceItem>();
			//是根节点时,列出所有有效的服务项
			if(itemCode.trim().equals("0")){
				Map<String,Object> siNamesAndValues=new HashMap<String,Object>();
				siNamesAndValues.put("deleteFlag", ServiceItem.DELETE_FALSE);
				serviceItems=configItemService.findObjects(ServiceItem.class, siNamesAndValues, null,"serviceItemCode",true);
				for(ServiceItem si:serviceItems){
					TreeNode treeNode=new TreeNode();
					treeNode.setText(si.getName()+"("+si.getServiceItemCode()+")");
					treeNode.setItemCode(si.getServiceItemCode());
					treeNode.setItemType("si");
					treeNode.setIcon(getRequest().getContextPath()+TreeNode.SERVICEITEM_ICON);
					treeNode.setRid(0L);
					treeNode.setDoubleClickExpand(false);
					treeNode.setLeaf(false);
					treeNodes.add(treeNode);
				}
			}else{//不是根节点时,在关系中查询出以此为父的关系
				List<CIRelationShip> ciRelationShips=configItemService.findDirectChildRel(itemCode);
				List<String> ciCode=new ArrayList<String>();
				List<String> siCode=new ArrayList<String>();
				Map<String,Long> codeAndRid=new HashMap<String,Long>();
				for(int i=0;i<ciRelationShips.size();i++){
					String serviceItemCode=ciRelationShips.get(i).getChildServiceItemCode();
					String configItemCode=ciRelationShips.get(i).getChildConfigItemCode();
					if(serviceItemCode!=null){
						siCode.add(serviceItemCode);
						codeAndRid.put(serviceItemCode, ciRelationShips.get(i).getId());
					}
					else if(configItemCode!=null){
						ciCode.add(configItemCode);
						codeAndRid.put(configItemCode, ciRelationShips.get(i).getId());
					}
				}
				if(!siCode.isEmpty()){
					Map<String,Object> siNamesAndValues=new HashMap<String,Object>();
					siNamesAndValues.put("serviceItemCode", siCode);
					siNamesAndValues.put("deleteFlag", ServiceItem.DELETE_FALSE);
					//查询出对应的服务项,主要为取服务项的名称
					List<ServiceItem> sis=configItemService.findObjects(ServiceItem.class, siNamesAndValues, null,"serviceItemCode",true);
					for(ServiceItem si:sis){
						TreeNode treeNode=new TreeNode();
						treeNode.setText(si.getName()+"("+si.getServiceItemCode()+")");
						treeNode.setItemCode(si.getServiceItemCode());
						treeNode.setItemType("si");
						treeNode.setIcon(getRequest().getContextPath()+TreeNode.SERVICEITEM_ICON);
						treeNode.setRid(codeAndRid.get(si.getServiceItemCode()));
						treeNode.setDoubleClickExpand(false);
						treeNode.setLeaf(false);
						treeNodes.add(treeNode);
					}
				}
				if(!ciCode.isEmpty()){
					Map<String,Object> ciNamesAndValues=new HashMap<String,Object>();
					ciNamesAndValues.put("cisn", ciCode);
					ciNamesAndValues.put("status", ConfigItem.VALID_STATUS);
					//查询出对应的配置项，主要为取其名称
					List<ConfigItem> cis=configItemService.findObjects(ConfigItem.class, ciNamesAndValues, null,"cisn",true);
					for(ConfigItem ci:cis){
						TreeNode treeNode=new TreeNode();
						treeNode.setText(ci.getName()+"("+ci.getCisn()+")");
						treeNode.setItemCode(ci.getCisn());
						treeNode.setItemType("ci");
						treeNode.setIcon(getRequest().getContextPath()+TreeNode.CONFIGITEM_ICON);
						treeNode.setRid(codeAndRid.get(ci.getCisn()));
						treeNode.setDoubleClickExpand(false);
						treeNode.setLeaf(false);
						treeNodes.add(treeNode);
					}
				}
			}
			HttpServletResponse response = getResponse();
			response.setContentType("text/plain;charset=gbk");
			PrintWriter out = getResponse().getWriter();
			out.print(JSONArray.fromObject(treeNodes).toString());
			out.flush();
			out.close();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	/**
	 * 查询所有关系类型
	 * @Methods Name findAllRelationType
	 * @Create In Feb 24, 2010 By duxh
	 * @Return String
	 */
	public String  findAllRelationType(){
		try {
			List type = super.getService().findAll(CIRelationShipType.class);			
			String json = "";
			for(int i=0; i< type.size(); i++){
				CIRelationShipType relationType = (CIRelationShipType)type.get(i);			
				Long id = relationType.getId();
				String name = relationType.getName();
				json += "{\"id\":"+id+",\"name\":\""+name+"\"},";
			}
			if(json.length()!=0)
				json = "{data:[" + json.substring(0, json.length()-1) + "]}";
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(json);		
		} catch (IOException e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return null;
	}
	/**
	 * 查询所有关系级别
	 * @Methods Name findAllRelationGrade
	 * @Create In Feb 24, 2010 By duxh
	 * @Return String
	 */
	public String findAllRelationGrade(){		
		try {
			List grade = super.getService().findAll(CIRelationShipGrade.class);			
			String json = "";
			for(int i=0; i< grade.size(); i++){
				CIRelationShipGrade relationfGrade = (CIRelationShipGrade)grade.get(i);			
				Long id = relationfGrade.getId();
				String name = relationfGrade.getName();
				json += "{\"id\":"+id+",\"name\":\""+name+"\"},";
			}
			if(json.length()!=0)
				json = "{data:[" + json.substring(0, json.length()-1) + "]}";
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(json);		
		} catch (IOException e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return null;
	}
	/**
	 * 查询关系信息 
	 * @Methods Name findRelationShipInfo
	 * @Create In Feb 24, 2010 By duxh
	 * @Return String
	 */
	public String findRelationShipInfo(){
		try {
			String rid = super.getRequest().getParameter("rid");
			String modifyIdString = super.getRequest().getParameter("modifyId");
			Long modifyId=null;
			if(modifyIdString!=null&&modifyIdString.trim().length()!=0){
				modifyId=Long.valueOf(modifyIdString);
			}
			CIRelationShip rel=(CIRelationShip) getService().find(CIRelationShip.class, rid);
			String parentCode="";
			String parentItem="";
			String parentName="";
			String parentType="";
			String parentTypeId="";
			if(rel.getParentConfigItemType()!=null){
				parentType=rel.getParentConfigItemType().getName();
				parentTypeId=rel.getParentConfigItemType().getId().toString();
			}else if(rel.getParentServiceItemType()!=null){
				parentType=rel.getParentServiceItemType().getName();
				parentTypeId=rel.getParentServiceItemType().getId().toString();
			}
			if(rel.getParentConfigItemCode()!=null){
				parentItem="ci";
				parentCode=rel.getParentConfigItemCode();
				parentName=configItemService.findItemName("ci", parentCode,modifyId );
			}else if(rel.getParentServiceItemCode()!=null){
				parentItem="si";
				parentCode=rel.getParentServiceItemCode();
				parentName=configItemService.findItemName("si", parentCode,modifyId);
			}
			String childCode="";
			String childItem="";
			String childType="";
			String childTypeId="";
			String childName="";
			if(rel.getChildConfigItemType()!=null){
				childType=rel.getChildConfigItemType().getName();
				childTypeId=rel.getChildConfigItemType().getId().toString();
			}else if(rel.getChildServiceItemType()!=null){
				childType=rel.getChildServiceItemType().getName();
				childTypeId=rel.getChildServiceItemType().getId().toString();
			}
			if(rel.getChildConfigItemCode()!=null){
				childItem="ci";
				childCode=rel.getChildConfigItemCode();
				childName=configItemService.findItemName("ci", childCode,modifyId);
			}else if(rel.getChildServiceItemCode()!=null){
				childItem="si";
				childCode=rel.getChildServiceItemCode();
				childName=configItemService.findItemName("si", childCode,modifyId);
			}
			CIRelationShipType type=rel.getRelationShipType();
			CIRelationShipGrade grade=rel.getRelationShipGrade();
			Double attachQuotiety=rel.getAttachQuotiety();
			String atechnoInfo=rel.getAtechnoInfo();
			String btechnoInfo=rel.getBtechnoInfo();
			String otherInfo=rel.getOtherInfo();
			Integer status=rel.getStatus();
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date createDate=rel.getCreateDate();
			Date modifyDate=rel.getModifyDate();
			UserInfo createUser=rel.getCreateUser();
			UserInfo modifyUser=rel.getModifyUser();
			String json="{data:[{id:\""+rel.getId()+"\","+
					"relationShipType:\""+(type==null?"":type.getId())+"\","+
					"relationShipGrade:\""+(grade==null?"":grade.getId())+"\","+
					"attachQuotiety:\""+(attachQuotiety==null?"":attachQuotiety)+"\","+
					"atechnoInfo:\""+(atechnoInfo==null?"":atechnoInfo)+"\","+
					"btechnoInfo:\""+(btechnoInfo==null?"":btechnoInfo)+"\","+
					"otherInfo:\""+(otherInfo==null?"":otherInfo)+"\","+
					"status:\""+(status==null?"":status)+"\","+
					"createDate:\""+(createDate==null?"":df.format(createDate))+"\","+
					"modifyDate:\""+(modifyDate==null?"":df.format(modifyDate))+"\","+
					"createUser:\""+(createUser==null?"":createUser.getId())+"\","+
					"modifyUser:\""+(modifyUser==null?"":modifyUser.getId())+"\","+
					"parentCode:\""+parentCode+"\","+
					"parentItem:\""+parentItem+"\","+
					"parentType:\""+parentType+"\","+
					"parentTypeId:\""+parentTypeId+"\","+
					"parentName:\""+parentName+"\","+
					"childCode:\""+childCode+"\","+
					"childType:\""+childType+"\","+
					"childTypeId:\""+childTypeId+"\","+
					"childName:\""+childName+"\","+
					"childItem:\""+childItem+"\"";
			json+="}]}";
			getResponse().setContentType("text/plain");
			getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = getResponse().getWriter();	
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ApplicationException();
		}		
		return null;
	}
	/**
	 * 获取批量变更列表数据
	 * @Methods Name getBatchModifyInfoList
	 * @Create In Mar 1, 2010 By duxh
	 * @return 
	 * @Return String
	 */
	public String getBatchModifyInfoList(){
		try{
			String id=getRequest().getParameter("id");
			String type=getRequest().getParameter("type");
			int start = HttpUtil.getInt(getRequest(), "start",0);
			int pageSize = HttpUtil.getInt(getRequest(), "pageSize",5);
			int pageNo=start/pageSize+1;
			Page page=configItemService.findBatchModifyInfo(Long.valueOf(id), type, pageNo, pageSize);
			List<CIBatchModify> batchModifyList=page.list();
			long count=page.getTotalCount();
			if(count==0)
				count=1;
			StringBuilder json = new StringBuilder("{success: true, rowCount:" + count + ",data:[");
			for (int i = 0; i < batchModifyList.size(); i++) {
				CIBatchModify batchModify=batchModifyList.get(i);
				if (i != 0)
					json.append(",");
				json.append("{");
				json.append("id:\"" + batchModify.getId()+"\",");
				if(batchModify.getModifyNo()!=null)
					json.append("modifyNo:\"" + batchModify.getModifyNo()+"\",");
				if(batchModify.getName()!=null)
					json.append("name:\"" + batchModify.getName()+"\",");
				if(batchModify.getDescn()!=null)
					json.append("descn:\"" + batchModify.getDescn()+"\",");
				if(batchModify.getReason()!=null)
					json.append("reason:\"" + batchModify.getReason()+"\",");
				if(batchModify.getApplyDate()!=null){
					SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					json.append("applyDate:\"" + (batchModify.getApplyDate()==null?"":df.format(batchModify.getApplyDate()))+"\",");
				}
				if(batchModify.getApplyUser()!=null)
					json.append("applyUser:\"" + batchModify.getApplyUser().getRealName() + "/" + batchModify.getApplyUser().getUserName() + "/"
							+ batchModify.getApplyUser().getDepartment().getDepartName()+"\",");
				String status="";
				if(batchModify.getStatus().compareTo(CIBatchModify.STATUS_DRAFT)==0)
					status="草稿";
				else if(batchModify.getStatus().compareTo(CIBatchModify.STATUS_PROCESSING)==0)
					status="处理中";
				else if(batchModify.getStatus().compareTo(CIBatchModify.STATUS_PASSED)==0)
					status="处理结束";
				else if(batchModify.getStatus().compareTo(CIBatchModify.STATUS_GIVEUP)==0)
					status="放弃";
				json.append("status:\"" + status+"\"");
				json.append("}");
			}
			json.append("]");
			json.append("}");
			super.getResponse().setContentType("text/plain");
			super.getResponse().setCharacterEncoding("UTF-8");
			PrintWriter out = super.getResponse().getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return null;
	}
	/**
	 * 保存批量变更实体
	 * @Methods Name saveCIBatchModify
	 * @Create In Mar 1, 2010 By duxh
	 * @Return String
	 */
	public String saveCIBatchModify(){
		try {
			String ciModifyPanelParam = super.getRequest().getParameter("ciModifyPanelParam");
			String typeIdString = super.getRequest().getParameter("typeId");
			String type = super.getRequest().getParameter("type");
			Long typeId=null;
			if(!typeIdString.equals("")){
				typeId=Long.valueOf(typeIdString);
			}
			HashMap infoMap = new HashMap();
			JSONObject jo = JSONObject.fromObject(ciModifyPanelParam);
			Iterator itInfo = jo.keys();
			while (itInfo.hasNext()) {
				String key = (String) itInfo.next();
				String value=jo.getString(key);
				key = key.split("\\$")[1];
				infoMap.put(key, value);
			}
			
			UserInfo user=UserContext.getUserInfo();
			infoMap.put("applyUser", UserContext.getUserInfo());
			infoMap.put("applyDate", new Date());
			infoMap.put("status", CIBatchModify.STATUS_DRAFT);
			
			Long modifyId=configItemService.saveCIBatchModify(infoMap,typeId,type);
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();
			pw.write(modifyId.toString());
		} catch (IOException e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return null;
	}
	/**
	 * 获取批量变更关联的配置项列表
	 * @Methods Name getBatchModifyConfigItemList
	 * @Create In Mar 3, 2010 By duxh
	 * @Return String
	 */
	public String getBatchModifyConfigItemList(){
		try {
			String modifyId = super.getRequest().getParameter("modifyId");
			int start = HttpUtil.getInt(getRequest(), "start",0);
			int pageSize = HttpUtil.getInt(getRequest(), "pageSize",10);
			int pageNo=start/pageSize+1;
			Page page=configItemService.findBatchModifyCIList(Long.valueOf(modifyId), pageNo, pageSize);
			List<CIBatchModifyPlan> plans=page.list();
			long count=page.getTotalCount();
			if(count==0)
				count=1;
			StringBuilder json = new StringBuilder("{success: true, rowCount:" + count + ",data:[");
			for (int i = 0; i < plans.size(); i++) {
				CIBatchModifyPlan plan=plans.get(i);
				ConfigItem newCi=plan.getNewConfigItem();
				ConfigItem oldCi=plan.getOldConfigItem();
				ConfigItem maintenanceCIRel=plan.getMaintenanceCIRel();
		    	ConfigItem ci=null;
		    	if(newCi!=null){
		    		ci=newCi;
		    	}else if(oldCi!=null){
		    		ci=oldCi;
		    	}else if(maintenanceCIRel!=null){
		    		ci=maintenanceCIRel;
		    	}
				if (i != 0)
					json.append(",");
				json.append("{");
				json.append("cid:\"" + ci.getId()+"\",");
				json.append("pid:\"" + plan.getId()+"\",");
				if(oldCi!=null){
					json.append("oldId:\"" + oldCi.getId()+"\",");
				}
				if(ci.getName()!=null)
					json.append("configItemName:\"" + ci.getName()+"\",");
				if(ci.getCisn()!=null)
					json.append("configItemCisn:\"" + ci.getCisn()+"\",");
				if(ci.getConfigItemType().getName()!=null)
					json.append("configItemType:\"" + ci.getConfigItemType().getName()+"\",");
				if(plan.getDescn()!=null)
					json.append("descn:\"" + plan.getDescn()+"\",");
				if(plan.getOfficer()!=null)
					json.append("officer:\"" + plan.getOfficer().getRealName() + "/" + plan.getOfficer().getUserName() + "/"
							+ plan.getOfficer().getDepartment().getDepartName()+"\",");
				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(plan.getStartDate()!=null){
					json.append("startDate:\"" + (plan.getStartDate()==null?"":df.format(plan.getStartDate()))+"\",");
				}
				if(plan.getEndDate()!=null)
					json.append("endDate:\"" + (plan.getEndDate()==null?"":df.format(plan.getEndDate()))+"\",");
				String status="";
				if(plan.getOldConfigItem()!=null&&plan.getNewConfigItem()!=null)
					status="变更";
				else if(plan.getNewConfigItem()!=null&&plan.getOldConfigItem()==null)
					status="新建";
				else if(plan.getNewConfigItem()==null&&plan.getOldConfigItem()!=null)
					status="删除";
				else if(plan.getMaintenanceCIRel()!=null){
					status="维护必要关系";
				}
				json.append("status:\"" + status+"\",");
				String result="";
				if(plan.getResult().compareTo(CIBatchModifyPlan.MODIFY_SUCCESS)==0){
					result="成功";
				}else{
					result="未成功";
				}
				json.append("result:\"" + result+"\"");
				json.append("}");
			}
			json.append("]");
			json.append("}");
			super.getResponse().setContentType("text/plain");
			super.getResponse().setCharacterEncoding("UTF-8");
			PrintWriter out = super.getResponse().getWriter();
			out.println(json);
			out.flush();
			out.close();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return null;
	}
	/**
	 * 建立父子关系之前验证此关系是否有效
	 * 1.本次变更中是否存在此关系草稿，
	 * 2.当前系统中是否存在正式的此种关系
	 * 3.关系是否存在环
	 * 4.如果关系相关的配置项为孤立状态，不允许建立关系
	 * @Methods Name relValidate
	 * @Create In Apr 1, 2010 By duxh
	 * @Return String
	 */
	public String relValidate(){
		try {
			String modifyId=getRequest().getParameter("modifyId");
			String parentItem=getRequest().getParameter("parentItem");
			String childItem=getRequest().getParameter("childItem");
			String parentCode=getRequest().getParameter("parentCode");
			String childCode=getRequest().getParameter("childCode");
			String ridString=getRequest().getParameter("rid");
			String oldRidString=getRequest().getParameter("oldRid");
			Long rid=null;
			if(ridString.trim().length()!=0){
				rid=Long.valueOf(ridString);
			}
			Long oldRid=null;
			if(oldRidString.trim().length()!=0){
				oldRid=Long.valueOf(oldRidString);
			}
			
			String message="";
			
			List<String> cisns=new ArrayList<String>();
			if(parentItem.equals("ci")){
				cisns.add(parentCode);
			}
			if(childItem.equals("ci")){
				cisns.add(childCode);
			}
			if(!cisns.isEmpty()){
				//如果关系相关的配置项为孤立状态，不允许建立关系
				List<ConfigItem> orphanCI=configItemService.findOrphanCI(Long.valueOf(modifyId),cisns);
				for(ConfigItem ci:orphanCI){
					message+="<li><p>配置项"+ci.getCisn()+"的状态为“"+ci.getConfigItemStatus().getName()+"”,不允许建立关系!</p></li>";
				}
			}
			if(message.length()==0){
				//本次变更中是否存在此关系草稿
				boolean hasSameDraft=configItemService.findHasSameDraft(Long.valueOf(modifyId), parentCode, childCode,rid);
				if(hasSameDraft){
					message="您已存在此种关系草稿:【父】"+parentCode+"--->【子】"+childCode+".";
				}else{ 
					//当前系统中是否存在正式的此种关系
					boolean hasSameValidRel=configItemService.findHasSameValidAndProcessingRel(parentCode, childCode,null,oldRid);
					if(hasSameValidRel){
						message="已存在此种关系:【父】"+parentCode+"--->【子】"+childCode+".";
					}else{
						CIBatchModify bm=new CIBatchModify();
						bm.setId(Long.valueOf(modifyId));
						List<CIBatchModifyPlan> plans=getService().find(CIBatchModifyPlan.class, "batchModify", bm);
						List<Long> rids=new ArrayList<Long>();
						for(CIBatchModifyPlan plan:plans){
							if(plan.getNewCIRelationShip()!=null){
								//排除此关系本身
								if(rid==null||rid.compareTo(plan.getNewCIRelationShip().getId())!=0)
									rids.add(plan.getNewCIRelationShip().getId());
							}
						}
						List<Long> ignoreRid=new ArrayList<Long>();
						if(oldRid!=null){
							ignoreRid.add(oldRid);
						}
						//关系是否存在环
						Set<String> codes=configItemService.findLoop(parentCode, childCode,rids,ignoreRid);
						if(!codes.isEmpty()){
							message="建立关系：【父】"+parentCode+"--->【子】"+childCode+",将会导致以下配置项或服务项存在环:";
							int i=0;
							for(String code:codes){
								if(i!=0){
									message+=",";
								}
								message+=code;
								i++;
							}
							message+=".";
						}
					}
				}
			}
			super.getResponse().setContentType("text/plain");
			super.getResponse().setCharacterEncoding("UTF-8");
			PrintWriter out = super.getResponse().getWriter();
			out.write(message);
			out.flush();
			out.close();
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	/**
	 * 变更提交前验证
	 * 不允许提交:
	 * 1.未变更配置项或关系
	 * 2.关系信息填写不完整
	 * 3.其他变更申请中变更了本次变更申请中变更的配置项并且已经提交。
	 * 4.其他变更申请中变更了本次变更申请中变更的配置项（变更为孤立状态）的关系并且已经提交。
	 * 5.本次变更申请将配置项变更为孤立，但并未删除其全部关系.
	 * 6.其他变更申请和本次变更申请新建或变更了相同的关系，并且其他变更申请已经提交。
	 * 7.其他变更申请将配置项变更为孤立并提交，本次变更申请为此配置项新建或变更了关系。
	 * 8.配置项状态为孤立状态，本次申请中未变更此配置项只是为其新建或变更了关系
	 * 9.在该配置项被删除之前新建或变更了关系.
	 * 10.本次变更申请中新建的关系和系统中正式的关系、或审批中的关系存在环。
	 * 11.新建配置项、变更配置项或维护配置项关系时，未建立该配置项的所有不存在的必须建立的必要关系.
	 * 确认之后可以提交：
	 * 1.已存在此种关系
	 * 2.其他申请变更成功了本次申请中变更的配置项
	 * 3.配置项被删除
	 * 4.其他申请变更成功了本次申请中变更的关系
	 * 5.其他申请变更删除并且变更成功了本次变更中变更的关系
	 * @Methods Name modifySubmitValidate
	 * @Create In Mar 7, 2010 By duxh
	 * @Return String
	 */
	public String modifySubmitValidate(){
		List<String>  status=new ArrayList<String>();
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_ARCHIVED);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_DISABLED);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_LOAN);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_STANDBY);
		try {
			StringBuilder message=new StringBuilder();
			String modifyId=getRequest().getParameter("modifyId");
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("batchModify.id", Long.valueOf(modifyId));
			List<String> fetchProperty=new ArrayList<String>();
			fetchProperty.add("newConfigItem");
			fetchProperty.add("oldConfigItem");
			fetchProperty.add("newCIRelationShip");
			fetchProperty.add("oldCIRelationShip");
			//本次变更中的所有变更计划
			List<CIBatchModifyPlan> currentplans=configItemService.findObjects(CIBatchModifyPlan.class, map,fetchProperty);			
			if(!currentplans.isEmpty()){
				//本次变更中、当前正在处理中的申请中新建或变更的关系id
				List<Long> rids=new ArrayList<Long>();
				//变更之前的配置项编号
				List<String> oldCisns=new ArrayList<String>();
				//变更之前的配置项
				List<ConfigItem> oldCI=new ArrayList<ConfigItem>();
				//变更之前的关系
				List<CIRelationShip> oldRel=new ArrayList<CIRelationShip>();
				//新建或变更的关系
				List<CIRelationShip> newRel=new ArrayList<CIRelationShip>();
				//变更为孤立状态的配置项
				List<ConfigItem> orphanCIs=new ArrayList<ConfigItem>();
				//本次变更申请中新建（包括只是新建、变更配置项时新建）的配置项编号
				List<String> allModifyCI=new ArrayList<String>();
				//本次变更申请中新建、变更、维护关系的配置项
				List<ConfigItem> allCI=new ArrayList<ConfigItem>();
				for(CIBatchModifyPlan plan:currentplans){
					//新建、变更的配置项
					if(plan.getNewConfigItem()!=null){
						allCI.add(plan.getNewConfigItem());
						allModifyCI.add(plan.getNewConfigItem().getCisn());
					}
					//维护关系的配置项
					if(plan.getMaintenanceCIRel()!=null&&
					   plan.getNewCIRelationShip()==null&&
					   plan.getOldCIRelationShip()==null&&
					   plan.getNewConfigItem()==null&&
					   plan.getOldConfigItem()==null){
				    		allCI.add(plan.getMaintenanceCIRel());
					}
					//是否为变更配置项
					if(plan.getOldConfigItem()!=null){
						oldCisns.add(plan.getOldConfigItem().getCisn());
						oldCI.add(plan.getOldConfigItem());
						if(plan.getNewConfigItem()!=null){
							ConfigItemStatus configItemStatus=plan.getNewConfigItem().getConfigItemStatus();
							//变更为孤立状态的配置项
							if(configItemStatus!=null&&status.contains(configItemStatus.getEnname())){
								orphanCIs.add(plan.getNewConfigItem());
							}
						}
					}
					//新建或变更的关系
					if(plan.getNewCIRelationShip()!=null){
						rids.add(plan.getNewCIRelationShip().getId());
						newRel.add(plan.getNewCIRelationShip());
					}
					//变更之前的关系
					if(plan.getOldCIRelationShip()!=null){
						oldRel.add(plan.getOldCIRelationShip());
					}
				}
				for(CIRelationShip rel:newRel){
					//关系信息填写不完整
					if(rel.getParentConfigItemCode()==null&&
					   rel.getParentServiceItemCode()==null||
					   rel.getChildConfigItemCode()==null&&
					   rel.getChildServiceItemCode()==null){
						String parentType="";
						String childType="";
						if(rel.getParentConfigItemType()!=null){
							parentType=rel.getParentConfigItemType().getName();
						}else if(rel.getParentServiceItemType()!=null){
							parentType=rel.getParentServiceItemType().getName();
						}
						if(rel.getChildConfigItemType()!=null){
							childType=rel.getChildConfigItemType().getName();
						}else if(rel.getChildServiceItemType()!=null){
							childType=rel.getChildServiceItemType().getName();
						}
						message.append("<li><p>配置项类型为:【父】“"+parentType+"”--->【子】“"+childType+"”的关系信息填写不完整,请检查!</p></li>");
					}
				}
				if(!oldCisns.isEmpty()){
					List<String> processingCI=configItemService.findCIProcessing(Long.valueOf(modifyId),oldCisns);
					//其他变更申请中变更了本次变更申请中变更的配置项并且已经提交。
					if(!processingCI.isEmpty()){
						for(String cisn:processingCI){
							message.append("<li><p>配置项:"+cisn+"正在变更审批中,请等待其审批结束!</p></li>");
						}
					}
				}
				//当前正在处理中的所有关系
				List<CIBatchModifyPlan> processingRelPlans=configItemService.findProcessingRelPlan(Long.valueOf(modifyId));
				for(CIBatchModifyPlan plan:processingRelPlans){
					if(plan.getNewCIRelationShip()!=null){
						rids.add(plan.getNewCIRelationShip().getId());
					}
				}
				if(!processingRelPlans.isEmpty()){
					//变更计划对应配置项关系中的父子配置项编号
					Set<String> allProcessingCICode=findAllCICode(processingRelPlans);
					for(ConfigItem orphanCI:orphanCIs){
						//其他变更申请中变更了本次变更申请中变更的配置项（变更为孤立状态）的关系并且已经提交。
						if(allProcessingCICode.contains(orphanCI.getCisn())){
							message.append("<li><p>配置项:"+orphanCI.getCisn()+"的关系正在变更审批中,不允许将其状态置为“"+orphanCI.getConfigItemStatus().getName()+"”,请等待其审批结束!</p></li>");
						}else{
							List<CIRelationShip> validRels=configItemService.findValidCIRelationShip(orphanCI.getCisn());
							if(!validRels.isEmpty()){
								List<CIRelationShip> deleteRels=configItemService.findDeleteRel(Long.valueOf(modifyId),validRels);
								//本次变更申请将配置项变更为孤立，但并未删除其全部关系.
								if(deleteRels.size()<validRels.size()){
									message.append("<li><p>配置项:"+orphanCI.getCisn()+"状态为“"+orphanCI.getConfigItemStatus().getName()+"”,需删除其全部关系.请检查!</p></li>");
								}
							}
						}
					}
					//变更计划中配置项关系父子编号的对应关系
					List<String[]> processingCodes=findParentAndChildCode(processingRelPlans);
					List<String[]> currentcodes=findParentAndChildCode(currentplans);
					for(String[] currentcode:currentcodes){
						for(String[] processingCode:processingCodes){
							//其他变更申请和本次变更申请新建或变更了相同的关系，并且其他变更申请已经提交。
							if(currentcode[0].equals(processingCode[0])&&currentcode[1].equals(processingCode[1])){
								message.append("<li><p>关系:【父】"+currentcode[0]+"--->【子】"+currentcode[1]+"正在变更审批中,请等待其审批结束!</p></li>");
							}
						}
					}
				}
				Set<String> allCurrentCICode=findAllCICode(currentplans);
				if(!allCurrentCICode.isEmpty()){
					//其他变更申请将配置项变更为孤立并提交，本次变更申请为此配置项新建或变更了关系。
					List<ConfigItem> processingOrphanCI=configItemService.findProcessingOrphanCI(Long.valueOf(modifyId),allCurrentCICode);
					for(ConfigItem ci:processingOrphanCI){
						message.append("<li><p>配置项:"+ci.getCisn()+"的状态在其他变更申请中被置为“"+ci.getConfigItemStatus().getName()+"”,并且此变更申请正在审批中,不允许为该配置项新建或变更关系!</p></li>");
					}
					//去除本次变更申请中新建、变更的配置项。因为可以将孤立状态的配置项变更为不孤立，这时在同一申请中可以新建或变更关系
					allCurrentCICode.removeAll(allModifyCI);
					if(!allCurrentCICode.isEmpty()){
						Map<String,Object> propertyValuesCI=new HashMap<String,Object>();
						propertyValuesCI.put("cisn",allCurrentCICode);
						propertyValuesCI.put("status", ConfigItem.VALID_STATUS);
						List<String> fetchPropertyCI=new ArrayList<String>();
						fetchPropertyCI.add("configItemStatus");
						//当前有效的配置项
						List<ConfigItem> validCI=configItemService.findObjects(ConfigItem.class, propertyValuesCI,fetchPropertyCI);
						//配置项状态为孤立状态，本次申请中未变更此配置项只是为其新建或变更了关系
						for(ConfigItem ci:validCI){
							if(ci.getConfigItemStatus()!=null){
								if(status.contains(ci.getConfigItemStatus().getEnname())){
									message.append("<li><p>配置项:"+ci.getCisn()+"的状态为“"+ci.getConfigItemStatus().getName()+"”,不允许新建或变更其关系!</p></li>");
								}
							}
							allCurrentCICode.remove(ci.getCisn());
						}
						//去掉当前有效的，剩余的是当前已经被删除的配置项
						for(String cisn:allCurrentCICode){
							message.append("<li><p>配置项:"+cisn+"已被删除,不允许新建或变更其关系!</p></li>");
						}
					}
				}
				//新建的配置项关系对应的父子编号和关系id
				List<Object[]> newcodes=findNewParentAndChildCode(currentplans);
				for(Object[] newcode:newcodes){
					if(newcode[0].toString().length()!=0){
						//排除此关系自身
						List<Long> ignoreRid=new ArrayList<Long>();
						ignoreRid.add((Long)newcode[2]);
						Set<String> loopcodes=configItemService.findLoop(newcode[0].toString(), newcode[1].toString(),rids,ignoreRid);
						//本次变更申请中新建的关系和系统中正式的关系、或审批中的关系存在环
						if(!loopcodes.isEmpty()){
							message.append("<li><p>建立关系:【父】"+newcode[0]+"--->【子】"+newcode[1]+",将会导致以下配置项或服务项存在环:");
							for(String code:loopcodes){
								message.append(code+",");
							}
							message.delete(message.length()-1, message.length());
							message.append(".</p></li>");
						}
					}
				}
				for(ConfigItem ci:allCI){
					if(!status.contains(ci.getConfigItemStatus().getEnname())){
						boolean has=configItemService.findHasAllNecessaryRel(ci, Long.valueOf(modifyId));
						//新建配置项、变更配置项或维护配置项关系时，未建立该配置项的所有不存在的必须建立的必要关系.
						if(!has){
							message.append("<li><p>配置项:"+ci.getCisn()+"的必要关系未全部建立,请检查.</p></li>");
						}
					}
				}
				if(message.length()!=0){
					message.insert(0, "{error:\"");
					message.append("\"}");
				}else{
					for(Object[] newcode:newcodes){
						boolean hasSameValidRel=configItemService.findHasSameValidAndProcessingRel(newcode[0].toString(), newcode[1].toString(),null,null);
						//已存在此种关系
						if(hasSameValidRel){
							message.append("<li><p>关系:【父】"+newcode[0]+"--->【子】"+newcode[1]+"已存在!</p></li>");
						}
					}
					for(ConfigItem ci:oldCI){
						if(ci.getStatus().compareTo(ConfigItem.DELETE_STATUS)==0){
							Map<String,Object> propertyNameAndValue=new HashMap<String,Object>();
							propertyNameAndValue.put("status", ConfigItem.VALID_STATUS);
							propertyNameAndValue.put("cisn", ci.getCisn());
							List<ConfigItem> modify=configItemService.findObjects(ConfigItem.class, propertyNameAndValue, null);
							 //其他申请变更成功了本次申请中变更的配置项
							if(!modify.isEmpty()){
								message.append("<li><p>配置项:"+ci.getCisn()+"已被变更!</p></li>");
							}else{//配置项被删除
								message.append("<li><p>配置项:"+ci.getCisn()+"已被删除!</p></li>");
							}
						}
					}
					for(CIRelationShip rel:oldRel){
						if(rel.getStatus().compareTo(CIRelationShip.DELETE_STATUS)==0){
							String parentCode="";
							String childCode="";
							if(rel.getParentConfigItemCode()!=null){
								parentCode=rel.getParentConfigItemCode();
							}else if(rel.getParentServiceItemCode()!=null){
								parentCode=rel.getParentServiceItemCode();
							}
							if(rel.getChildConfigItemCode()!=null){
								childCode=rel.getChildConfigItemCode();
							}
							else if(rel.getChildServiceItemCode()!=null){
								childCode=rel.getChildServiceItemCode();
							}
							boolean hasSameValidRel=configItemService.findHasSameValidAndProcessingRel(parentCode, childCode,null,null);
							//其他申请变更成功了本次申请中变更的关系
							if(hasSameValidRel){
								message.append("<li><p>关系:【父】"+parentCode+"--->【子】"+childCode+"已被变更!</p></li>");
							}else{ //其他申请变更删除并且变更成功了本次变更中变更的关系
								message.append("<li><p>关系:【父】"+parentCode+"--->【子】"+childCode+"已被删除!</p></li>");
							}
						}
					}
					if(message.length()!=0){
						message.insert(0, "{confirm:\"");
						message.append("\"}");
					}
				}
			}else{
				message.append("{error:\"请变更配置项或关系!\"}");
			}
			super.getResponse().setContentType("text/plain");
			super.getResponse().setCharacterEncoding("UTF-8");
			PrintWriter out = super.getResponse().getWriter();
			out.write(message.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return null;
	}
	/**
	 * 新建的配置项关系对应的父子编号和关系id
	 * @Methods Name findNewParentAndChildCode
	 * @Create In Apr 3, 2010 By duxh
	 * @param plans
	 * @Return List<Object[]> Object[0]父编号，Object[1]子编号，Object[2]关系id
	 */
	private List<Object[]> findNewParentAndChildCode(List<CIBatchModifyPlan> plans){
		List<Object[]> list=new ArrayList<Object[]>();
		for(CIBatchModifyPlan plan:plans){
			CIRelationShip newRel=plan.getNewCIRelationShip();
			CIRelationShip oldRel=plan.getOldCIRelationShip();
			if(newRel!=null&&oldRel==null){
				String parentCode="";
				String childCode="";
				if(newRel.getParentConfigItemCode()!=null){
					parentCode=newRel.getParentConfigItemCode();
				}else if(newRel.getParentServiceItemCode()!=null){
					parentCode=newRel.getParentServiceItemCode();
				}
				if(newRel.getChildConfigItemCode()!=null){
					childCode=newRel.getChildConfigItemCode();
				}
				else if(newRel.getChildServiceItemCode()!=null){
					childCode=newRel.getChildServiceItemCode();
				}
				list.add(new Object[]{parentCode,childCode,newRel.getId()});
			}
		}
		return list;
	}
	/**
	 * 变更计划中配置项关系父子编号
	 * @Methods Name findParentAndChildCode
	 * @Create In Apr 3, 2010 By duxh
	 * @param plans
	 * @Return List<String[]> String[0]父编号，String[1]子编号
	 */
	private List<String[]> findParentAndChildCode(List<CIBatchModifyPlan> plans){
		List<String[]> list=new ArrayList<String[]>();
		for(CIBatchModifyPlan plan:plans){
			CIRelationShip newRel=plan.getNewCIRelationShip();
			CIRelationShip oldRel=plan.getOldCIRelationShip();
			String parentCode="";
			String childCode="";
			if(newRel!=null&&oldRel==null){
				if(newRel.getParentConfigItemCode()!=null){
					parentCode=newRel.getParentConfigItemCode();
				}else if(newRel.getParentServiceItemCode()!=null){
					parentCode=newRel.getParentServiceItemCode();
				}
				if(newRel.getChildConfigItemCode()!=null){
					childCode=newRel.getChildConfigItemCode();
				}
				else if(newRel.getChildServiceItemCode()!=null){
					childCode=newRel.getChildServiceItemCode();
				}
			}else if(oldRel!=null){
				if(oldRel.getParentConfigItemCode()!=null){
					parentCode=oldRel.getParentConfigItemCode();
				}else if(oldRel.getParentServiceItemCode()!=null){
					parentCode=oldRel.getParentServiceItemCode();
				}
				if(oldRel.getChildConfigItemCode()!=null){
					childCode=oldRel.getChildConfigItemCode();
				}
				else if(oldRel.getChildServiceItemCode()!=null){
					childCode=oldRel.getChildServiceItemCode();
				}
			}else{
				continue;
			}
			list.add(new String[]{parentCode , childCode});
		}
		return list;
	}
	/**
	 * 变更计划对应配置项关系中的父子配置项编号
	 * @Methods Name findAllCICode
	 * @Create In Apr 3, 2010 By duxh
	 * @param plans
	 * @Return List<String>
	 */
	private Set<String> findAllCICode(List<CIBatchModifyPlan> plans){
		Set<String> set=new HashSet<String>();
		for(CIBatchModifyPlan plan:plans){
			CIRelationShip newRel=plan.getNewCIRelationShip();
			CIRelationShip oldRel=plan.getOldCIRelationShip();
			if(newRel!=null){
				if(newRel.getParentConfigItemCode()!=null){
					set.add(newRel.getParentConfigItemCode());
				}
				if(newRel.getChildConfigItemCode()!=null){
					set.add(newRel.getChildConfigItemCode());
				}
			}else if(oldRel!=null&&newRel==null){
				if(oldRel.getParentConfigItemCode()!=null){
					set.add(oldRel.getParentConfigItemCode());
				}
				if(oldRel.getChildConfigItemCode()!=null){
					set.add(oldRel.getChildConfigItemCode());
				}
			}
		}
		return set;
	}
	/**
	 * 1.已存在此种关系
	 * 2.其他申请变更成功了本次申请中变更的配置项
	 * 3.配置项被删除
	 * 4.其他申请变更成功了本次申请中变更的关系
	 * 5.其他申请变更删除并且变更成功了本次变更中变更的关系
	 * 如果配置项或关系已存在或已被变更,继续提交视为在已存在或已被变更的配置项或关系的基础之上做变更!
	 * 如果原配置项或关系已被删除,本次申请中状态为变更,继续提交视为新建!
	 * @Methods Name saveOrUpdateOldCIAndOldRel
	 * @Create In Apr 3, 2010 By duxh
	 * @Return String
	 */
	public String saveOrUpdateOldCIAndOldRel(){
		String modifyId=getRequest().getParameter("modifyId");
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("batchModify.id", Long.valueOf(modifyId));
		List<String> fetchProperty=new ArrayList<String>();
		fetchProperty.add("newConfigItem");
		fetchProperty.add("oldConfigItem");
		fetchProperty.add("newCIRelationShip");
		fetchProperty.add("oldCIRelationShip");
		List<CIBatchModifyPlan> plans=configItemService.findObjects(CIBatchModifyPlan.class, map,fetchProperty);			
		configItemService.saveOrUpdateOldCIAndOldRel(plans);
		return null;
	}
	/**
	 * 变更配置项之前验证
	 * @Methods Name modifyConfigItemValidate
	 * @Create In Mar 4, 2010 By duxh
	 * @Return String
	 */
	public String modifyConfigItemValidate(){
		try {
			String cidString=getRequest().getParameter("cid");
			String cisn=getRequest().getParameter("cisn");
			String modifyId=getRequest().getParameter("modifyId");
			Long cid=null;
			if(cidString!=null&&cidString.trim().length()!=0){
				cid=Long.valueOf(cidString);
			}
			String json="";
			CIBatchModifyPlan plan= configItemService.findHasCIModifyDraft(Long.valueOf(modifyId), cisn,cid);
			//已存在此种草稿
			if(plan!=null){
				json="{exist:true}";
			}else{
				json="{exist:false}";
			}
			super.getResponse().setContentType("text/plain");
			super.getResponse().setCharacterEncoding("UTF-8");
			PrintWriter out = super.getResponse().getWriter();
			out.println(json);
			out.flush();
			out.close();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return null;
	}
	/**
	 * 变更配置项关系之前验证
	 * 1.已存在此种草稿
	 * 2.配置项状态为孤立
	 * @Methods Name modifyCIRelValidate
	 * @Create In Mar 29, 2010 By duxh
	 * @Return String
	 */
	public String modifyCIRelValidate(){
		try {
			String json="";
			String rid=getRequest().getParameter("rid");
			String modifyId=getRequest().getParameter("modifyId");
			List<String> cisns=new ArrayList<String>();
			CIRelationShip rel=(CIRelationShip)getService().find(CIRelationShip.class, rid);
			String parentCode="";
			String childCode="";
			if(rel.getParentConfigItemCode()!=null){
				parentCode=rel.getParentConfigItemCode();
				cisns.add(rel.getParentConfigItemCode());
			}else if(rel.getParentServiceItemCode()!=null){
				parentCode=rel.getParentServiceItemCode();
			}
			if(rel.getChildConfigItemCode()!=null){
				childCode=rel.getChildConfigItemCode();
				cisns.add(rel.getChildConfigItemCode());
			}else if(rel.getChildServiceItemCode()!=null){
				childCode=rel.getChildServiceItemCode();
			}
			if(!cisns.isEmpty()){
				//配置项状态为孤立
				List<ConfigItem> orphanCI=configItemService.findOrphanCI(Long.valueOf(modifyId),cisns);
				if(!orphanCI.isEmpty()){
					json+="{error:\"";
					for(ConfigItem ci:orphanCI){
						json+="<p>配置项"+ci.getCisn()+"的状态为“"+ci.getConfigItemStatus().getName()+"”,不允许变更关系!</p>";
					}
					json+="\"}";
				}
			}
			if(json.length()==0){
				CIBatchModifyPlan plan= configItemService.findHasCIRelModifyDraft(Long.valueOf(modifyId), parentCode,childCode);
				//已存在此种草稿
				if(plan!=null){
					json="{exist:true}";
				}
			}
			super.getResponse().setContentType("text/plain");
			super.getResponse().setCharacterEncoding("UTF-8");
			PrintWriter out = super.getResponse().getWriter();
			out.write(json);
			out.flush();
			out.close();
		}  catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return null;
	}
	/**
	 * 变更删除配置项关系之前验证
	 * @Methods Name deleteCIRelValidate
	 * @Create In Mar 30, 2010 By duxh
	 * @Return String
	 */
	public String deleteCIRelValidate(){
		try {
			String rid=getRequest().getParameter("rid");
			String modifyId=getRequest().getParameter("modifyId");
			CIRelationShip rel=(CIRelationShip)getService().find(CIRelationShip.class, rid);
			String parentCode="";
			String childCode="";
			if(rel.getParentConfigItemCode()!=null){
				parentCode=rel.getParentConfigItemCode();
			}else if(rel.getParentServiceItemCode()!=null){
				parentCode=rel.getParentServiceItemCode();
			}
			if(rel.getChildConfigItemCode()!=null){
				childCode=rel.getChildConfigItemCode();
			}else if(rel.getChildServiceItemCode()!=null){
				childCode=rel.getChildServiceItemCode();
			}
			CIBatchModifyPlan plan= configItemService.findHasCIRelModifyDraft(Long.valueOf(modifyId),parentCode,childCode);
			String json="";
			//已存在此种草稿
			if(plan!=null){
				json="{exist:true}";
			}
			if(json.length()!=0){
				super.getResponse().setContentType("text/plain");
				super.getResponse().setCharacterEncoding("UTF-8");
				PrintWriter out = super.getResponse().getWriter();
				out.println(json);
				out.flush();
				out.close();
			}
		}  catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return null;
	}
	/**
	 * 保存变更删除的变更计划
	 * @Methods Name saveDelCIBatchModifyPlan
	 * @Create In Mar 3, 2010 By duxh
	 * @return 
	 * @Return String
	 */
	public String saveDelCIBatchModifyPlan(){
		String planPanel = super.getRequest().getParameter("planPanel");
		String rid = super.getRequest().getParameter("rid");
		String modifyId = super.getRequest().getParameter("modifyId");
		
		HashMap infoMap = new HashMap();
		JSONObject jo = JSONObject.fromObject(planPanel);
		Iterator itInfo = jo.keys();
		while (itInfo.hasNext()) {
			String key = (String) itInfo.next();
			String value=jo.getString(key);
			key = key.split("\\$")[1];
			infoMap.put(key, value);
		}
		infoMap.put("batchModify", modifyId);
		infoMap.put("oldCIRelationShip", rid);
		infoMap.put("result", CIBatchModifyPlan.MODIFY_SUCCESS);
		metaDataManager.saveEntityData(CIBatchModifyPlan.class, infoMap);
		return null;
	}
	/**
	 *  删除变更计划及其新建的配置项和关系
	 * @Methods Name removeBatchModifyPlans
	 * @Create In Mar 4, 2010 By duxh
	 * @Return String
	 */
	public String removeBatchModifyPlans(){
		String planIdString=getRequest().getParameter("planId");
		Object[] planIdTemp=JSONArray.fromObject(planIdString).toArray();
		Long[] planId =new Long[planIdTemp.length];
		for(int i=0;i<planIdTemp.length;i++){
			planId[i]=Long.parseLong(planIdTemp[i].toString());
		}
		configItemService.removeBatchModifyPlans(planId);
		return null;
	}
	/**
	 * 查找批量变更申请中的关系变更
	 * @Methods Name getBatchModifyRelList
	 * @Create In Mar 25, 2010 By duxh
	 * @Return String
	 */
	public String getBatchModifyRelList(){
		try {
			String modifyId = super.getRequest().getParameter("modifyId");
			int start = HttpUtil.getInt(getRequest(), "start",0);
			int pageSize = HttpUtil.getInt(getRequest(), "pageSize",10);
			int pageNo=start/pageSize+1;
			Page page=configItemService.findBatchModifyRelList(Long.valueOf(modifyId), pageNo, pageSize);
			List<CIBatchModifyPlan> plans=page.list();
			long count=page.getTotalCount();
			if(count==0)
				count=1;
			StringBuilder json = new StringBuilder("{success: true, rowCount:" + count + ",data:[");
			for (int i = 0; i < plans.size(); i++) {
				CIBatchModifyPlan plan=plans.get(i);
				CIRelationShip rel=plan.getNewCIRelationShip();
				CIRelationShip oldRel=plan.getOldCIRelationShip();
				if(rel==null){
					rel=oldRel;
				}
				if (i != 0)
					json.append(",");
				json.append("{");
				json.append("rid:\"" + rel.getId()+"\",");
				json.append("pid:\"" + plan.getId()+"\",");
				if(oldRel!=null){
					json.append("oldRid:\"" + oldRel.getId()+"\",");
				}
				if(rel.getParentConfigItemType()!=null){
					json.append("parentType:\""+rel.getParentConfigItemType().getName()+"\",");
				}
				if(rel.getParentConfigItemCode()!=null){
					json.append("parentName:\""+configItemService.findItemName("ci", rel.getParentConfigItemCode(), Long.valueOf(modifyId))+"\",");
					json.append("parentCode:\""+rel.getParentConfigItemCode()+"\",");
				}
				if(rel.getParentServiceItemType()!=null){
					json.append("parentType:\""+rel.getParentServiceItemType().getName()+"\",");
				}
				if(rel.getParentServiceItemCode()!=null){
					json.append("parentName:\""+configItemService.findItemName("si", rel.getParentServiceItemCode(), Long.valueOf(modifyId))+"\",");
					json.append("parentCode:\""+rel.getParentServiceItemCode()+"\",");
				}
				if(rel.getChildConfigItemType()!=null){
					json.append("childType:\""+rel.getChildConfigItemType().getName()+"\",");
				}
				if(rel.getChildConfigItemCode()!=null){
					json.append("childName:\""+configItemService.findItemName("ci", rel.getChildConfigItemCode(), Long.valueOf(modifyId))+"\",");
					json.append("childCode:\""+rel.getChildConfigItemCode()+"\",");
				}
				if(rel.getChildServiceItemType()!=null){
					json.append("childType:\""+rel.getChildServiceItemType().getName()+"\",");
				}
				if(rel.getChildServiceItemCode()!=null){
					json.append("childName:\""+configItemService.findItemName("si", rel.getChildServiceItemCode(), Long.valueOf(modifyId))+"\",");
					json.append("childCode:\""+rel.getChildServiceItemCode()+"\",");
				}
				if(plan.getDescn()!=null)
					json.append("descn:\"" + plan.getDescn()+"\",");
				if(plan.getOfficer()!=null)
					json.append("officer:\"" + plan.getOfficer().getRealName() + "/" + plan.getOfficer().getUserName() + "/"
							+ plan.getOfficer().getDepartment().getDepartName()+"\",");
				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(plan.getStartDate()!=null)
					json.append("startDate:\"" + (plan.getStartDate()==null?"":df.format(plan.getStartDate()))+"\",");
				if(plan.getEndDate()!=null)
					json.append("endDate:\"" + (plan.getEndDate()==null?"":df.format(plan.getEndDate()))+"\",");
				String status="";
				if(plan.getOldCIRelationShip()!=null&&plan.getNewCIRelationShip()!=null)
					status="变更";
				else if(plan.getNewCIRelationShip()!=null&&plan.getOldCIRelationShip()==null)
					status="新建";
				else if(plan.getNewCIRelationShip()==null&&plan.getOldCIRelationShip()!=null)
					status="删除";
				json.append("status:\"" + status+"\",");
				String result="";
				if(plan.getResult().compareTo(CIBatchModifyPlan.MODIFY_SUCCESS)==0){
					result="成功";
				}else{
					result="未成功";
				}
				json.append("result:\"" + result+"\"");
				json.append("}");
			}
			json.append("]");
			json.append("}");
			super.getResponse().setContentType("text/plain");
			super.getResponse().setCharacterEncoding("UTF-8");
			PrintWriter out = super.getResponse().getWriter();
			out.println(json);
			out.flush();
			out.close();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return null;
	}
	/**
	 * 获取批量变更关联的问题或需求id
	 * @Methods Name getModifyTypeAndTypeId
	 * @Create In Mar 5, 2010 By duxh
	 * @Return String
	 */
	public String getModifyTypeAndTypeId(){
		try {
			String modifyId = super.getRequest().getParameter("modifyId");
			CIBatchModify ciBatchModify = new CIBatchModify();
			ciBatchModify.setId(Long.valueOf(modifyId));
			String json = "";
			CIBatchModifyShip modifyShip = (CIBatchModifyShip) getService().findUnique(CIBatchModifyShip.class, "ciBatchModify", ciBatchModify);
			if(modifyShip.getProblem()!=null){
				json = "{typeId:\""+modifyShip.getProblem().getId()+"\",type:\"problem\"}";
				
			}else if(modifyShip.getSpecialRequirement()!=null){
				json = "{typeId:\""+modifyShip.getSpecialRequirement().getId()+"\",type:\"specialRequirement\"}";
			}else{
				json = "{typeId:\"\",type:\"\"}";
			}
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return null;
	}
	/**
	 * 保存变更成功的变更计划
	 * 1.对于孤立的配置项变更成功时，所有其变更删除的关系也会变更成功
	 * 2.对于新建的配置项：变更成功时，不级联关系；配置项未变更成功，关系不允许变更成功。
	 * @Methods Name saveSuccessCIBatchModifyPlan
	 * @Create In Mar 5, 2010 By duxh
	 * @Return String
	 */
	public String saveSuccessCIBatchModifyPlan(){
		try {
			String pidsString=getRequest().getParameter("pids");
			Object[] pidsTemp=JSONArray.fromObject(pidsString).toArray();
			List<Long> pids =new ArrayList<Long>();
			for(int i=0;i<pidsTemp.length;i++){
				pids.add(Long.parseLong(pidsTemp[i].toString()));
			}
			
			if(!pids.isEmpty()){
				StringBuilder json=new StringBuilder();
				List<CIBatchModifyPlan> plans=configItemService.findObjects(CIBatchModifyPlan.class, "id", pids.toArray(), "newCIRelationShip");
				List<String> cisns=configItemService.findNewCIModifyUnsuccess(plans);
				for(String cisn:cisns){
					json.append("<li><p>配置项："+cisn+"为“新建”,并且未变更成功,其关系不允许变更成功!</p></li>");
				}
				if(json.length()==0){
					configItemService.saveSuccessCIBatchModifyPlan(plans);
				}else{
					super.getResponse().setContentType("text/plain");
					super.getResponse().setCharacterEncoding("utf-8");
					PrintWriter pw = super.getResponse().getWriter();
					pw.write(json.toString());
				}
			}
			
			return null;
		}  catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	/**
	 * 保存未变更成功的变更计划
	 * 1.孤立的配置项变更未成功，不级联关系；配置项变更成功，变更删除的关系必须变更成功。
	 * @Methods Name saveUnSuccessCIBatchModifyPlan
	 * @Create In Mar 5, 2010 By duxh
	 * @Return String
	 */
	public String saveUnSuccessCIBatchModifyPlan(){
		try {
			String pidsString=getRequest().getParameter("pids");
			Object[] pidsTemp=JSONArray.fromObject(pidsString).toArray();
			List<Long> pids =new ArrayList<Long>();
			for(int i=0;i<pidsTemp.length;i++){
				pids.add(Long.parseLong(pidsTemp[i].toString()));
			}
			if(!pids.isEmpty()){
				StringBuilder json=new StringBuilder();
				List<CIBatchModifyPlan> plans=configItemService.findObjects(CIBatchModifyPlan.class, "id", pids.toArray(), "oldCIRelationShip");
				List<ConfigItem> orphanCIs=configItemService.findOrphanCIModifySuccess(plans);
				for(ConfigItem ci:orphanCIs){
					json.append("<li><p>配置项："+ci.getCisn()+"状态为“"+ci.getConfigItemStatus().getName()+"”,并且已变更成功,其关系必须变更成功!</p></li>");
				}
				if(json.length()==0){
					configItemService.saveUnSuccessCIBatchModifyPlan(plans);
				}else{
					super.getResponse().setContentType("text/plain");
					super.getResponse().setCharacterEncoding("utf-8");
					PrintWriter pw = super.getResponse().getWriter();
					pw.write(json.toString());
				}
			}
			return null;
		}  catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	/**
	 * 查询配置项批量变更相关信息。
	 * @Methods Name findBatchModify
	 * @Create In Dec 1, 2009 By duxh
	 * @return String
	 */
	public String findBatchModify(){
		try {
			HttpServletRequest request = super.getRequest();
			String modifyNo = request.getParameter("modifyNo");
			String name = request.getParameter("name");
			String applyDateStartTemp = request.getParameter("applyDateStart");
			String applyDateEndTemp = request.getParameter("applyDateEnd");
			String statusTemp = request.getParameter("status");
			int pageSize = HttpUtil.getInt(super.getRequest(), "pageSize", 10);
			int start = HttpUtil.getInt(super.getRequest(), "start", 0);
			Date applyDateStart=null;
			if(applyDateStartTemp!=null&&applyDateStartTemp.trim().length()!=0){
				applyDateStart=java.sql.Date.valueOf(applyDateStartTemp);
			}
			Date applyDateEnd=null;
			if(applyDateEndTemp!=null&&applyDateEndTemp.trim().length()!=0){
				applyDateEnd=java.sql.Date.valueOf(applyDateEndTemp);
			}
			Integer status=null;
			if(statusTemp!=null&&!statusTemp.trim().equals("4")){//不限
				status=Integer.parseInt(statusTemp);
			}
			Page page=configItemService.findBatchModify(modifyNo, name, applyDateStart,applyDateEnd, status, start, pageSize);
			List<CIBatchModify> batchModifys=page.list();
			StringBuilder json = new StringBuilder("{success:true,rowCount:" + page.getTotalCount() + ",data:[");
			for (int i = 0; i < batchModifys.size(); i++) {
				if (i != 0)
					json.append(",");
				json.append("{");
				json.append("id:\"" + batchModifys.get(i).getId() + "\",");
				json.append("modifyNo:\"" + batchModifys.get(i).getModifyNo() + "\",");
				json.append("name:\"" + batchModifys.get(i).getName() + "\",");
				String emergent="";
				if(batchModifys.get(i).getEmergent().compareTo(1)==0)
					emergent="紧急";
				else if(batchModifys.get(i).getEmergent().compareTo(0)==0)
					emergent="正常";
				json.append("emergent:\"" + emergent + "\",");
				UserInfo user=batchModifys.get(i).getApplyUser();
				json.append("applyUser:\""+user.getRealName()+ "/" + user.getUserName() + "/"
						+ user.getDepartment().getDepartName()+ "\",");
				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				json.append("applyDate:\"" + (batchModifys.get(i).getApplyDate()==null?"":df.format(batchModifys.get(i).getApplyDate()))+ "\",");
				String str="";
				if(batchModifys.get(i).getStatus().compareTo(CIBatchModify.STATUS_DRAFT)==0){
					str="草稿";
				}
				else if(batchModifys.get(i).getStatus().compareTo(CIBatchModify.STATUS_PROCESSING)==0){
					str="处理中";
				}
				else if(batchModifys.get(i).getStatus().compareTo(CIBatchModify.STATUS_PASSED)==0){
					str="处理结束";
				}else if(batchModifys.get(i).getStatus().compareTo(CIBatchModify.STATUS_GIVEUP)==0){
					str="放弃";
				}
				json.append("status:\"" + str+ "\"");
				json.append("}");
			}
			json.append("]");
			json.append("}");
			super.getResponse().setContentType("text/plain");
			super.getResponse().setCharacterEncoding("UTF-8");
			PrintWriter out =getResponse().getWriter();
			out.println(json);
			out.flush();
			out.close();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	/**
	 * 删除配置项批量变更草稿
	 * @Methods Name deleteBatchModifyDraft
	 * @Create In Dec 1, 2009 By duxh
	 * @return String
	 */
	public String deleteBatchModifyDraft(){
		HttpServletRequest request = super.getRequest();
		String modifyId = request.getParameter("modifyId");
		Object[] temp=JSONArray.fromObject(modifyId).toArray(); 
		Long[] modifyIdArray=new Long[temp.length];
		for(int i=0;i<temp.length;i++)
			modifyIdArray[i]=Long.valueOf(temp[i].toString());
		configItemService.removeBatchModifyDraft(modifyIdArray);
		return null;
	}
	/**
	 * 分页查询有效的配置项个性化信息
	 * @Methods Name findConfigItemExtendInfo
	 * @Create In May 12, 2010 By duxh
	 * @Return String
	 */
    public String findConfigItemExtendInfo(){
		try {
			String json = "";
			int start = HttpUtil.getInt(this.getRequest(), "start", 0);
			int pageSize = HttpUtil.getInt(this.getRequest(), "pageSize", 10);
			int pageNo = start / pageSize + 1;
			//类名
			String pClazz = this.getRequest().getParameter("clazz");
			//模糊查询的字段名称
			String fuzzyQueryString = this.getRequest().getParameter("fuzzyQuery");
			List<String> fuzzyQuery=new ArrayList<String>();
			if(fuzzyQueryString!=null){
				String[] fuzzyQueryArray=fuzzyQueryString.split(";");
				fuzzyQuery=Arrays.asList(fuzzyQueryArray);
			}
			if(StringUtils.isNotBlank(pClazz)){
				Class clazz  = Class.forName((pClazz));
				Map requestParams = HttpUtil.requestParam2Map(this.getRequest());
				Map map=new HashMap();
				Set<String> keys=requestParams.keySet();
				for(String key:keys){
					try {
						String[] str=key.split("\\.");
						//判断类中是否确实存在此属性
						PropertyDescriptor pro=new PropertyDescriptor(str[0],clazz);
						Object value=null;
						if(str.length==1){
							value=convertStringToObject(requestParams.get(key).toString(), pro.getPropertyType());
						}else if(str.length==2){
							PropertyDescriptor postfix=new PropertyDescriptor(str[1],pro.getPropertyType());
							value=convertStringToObject(requestParams.get(key).toString(), postfix.getPropertyType());
						}
						map.put(key,value);
					} catch (Exception e) {
					}
				}
				Page page = configItemService.findValidConfigItemExtendInfo(pClazz, map, fuzzyQuery,pageNo, pageSize);
						
				Long total = page.getTotalCount();
				List queryList = page.list();
				List<Map<String, Object>> listData = metaDataManager
						.getEntityMapDataForList(clazz, queryList);
				SystemMainTable smt=systemMainTableService.findSystemMainTableByClazz(clazz);
				List<SystemMainTableColumn> visibleColumns = systemColumnService.findSystemTableColumns(smt);
				json = CoderForList.encodeForComboList(visibleColumns, listData, total);
			}
			getResponse().setContentType("text/plain;charset=gbk");
			PrintWriter out = getResponse().getWriter();
			out.write(json);
			out.flush();
			out.close();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
    /**
     * 分页查询工程师
     * @Methods Name findConfigItemExtendInfo
     * @Create In May 12, 2010 By duxh
     * @Return String
     */
    public String findServiceEngineer(){
    	try {
    		String json = "";
    		int start = HttpUtil.getInt(this.getRequest(), "start", 0);
    		int pageSize = HttpUtil.getInt(this.getRequest(), "pageSize", 10);
    		int pageNo = start / pageSize + 1;
    		//模糊查询的字段名称
    		String fuzzyQueryString = this.getRequest().getParameter("fuzzyQuery");
    		//交付团队id
    		String deliveryTeamStr=getRequest().getParameter("deliveryTeamId");
    		Long deliveryTeamId=null;
    		if(deliveryTeamStr!=null&&deliveryTeamStr.trim().length()!=0){
    			deliveryTeamId=Long.valueOf(deliveryTeamStr);
    		}
    		List<String> fuzzyQuery=new ArrayList<String>();
    		if(fuzzyQueryString!=null){
    			String[] fuzzyQueryArray=fuzzyQueryString.split(";");
    			fuzzyQuery=Arrays.asList(fuzzyQueryArray);
    		}
			Class clazz  = ServiceEngineer.class;
			Map requestParams = HttpUtil.requestParam2Map(this.getRequest());
			Map map=new HashMap();
			Set<String> keys=requestParams.keySet();
			for(String key:keys){
				try {
					String[] str=key.split("\\.");
					//判断类中是否确实存在此属性
					PropertyDescriptor pro=new PropertyDescriptor(str[0],clazz);
					Object value=null;
					if(str.length==1){
						value=convertStringToObject(requestParams.get(key), pro.getPropertyType());
					}else if(str.length==2){
						PropertyDescriptor postfix=new PropertyDescriptor(str[1],pro.getPropertyType());
						value=convertStringToObject(requestParams.get(key), postfix.getPropertyType());
					}
					map.put(key,value);
				} catch (Exception e) {
				}
			}
			Page page = configItemService.findServiceEngineer(deliveryTeamId, map, fuzzyQuery,pageNo, pageSize);
			
			Long total = page.getTotalCount();
			List queryList = page.list();
			List<Map<String, Object>> listData = metaDataManager
			.getEntityMapDataForList(clazz, queryList);
			SystemMainTable smt=systemMainTableService.findSystemMainTableByClazz(clazz);
			List<SystemMainTableColumn> visibleColumns = systemColumnService.findSystemTableColumns(smt);
			json = CoderForList.encodeForComboList(visibleColumns, listData, total);
    		getResponse().setContentType("text/plain;charset=gbk");
    		PrintWriter out = getResponse().getWriter();
    		out.write(json);
    		out.flush();
    		out.close();
    		return null;
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new ApplicationException();
    	}
    }
    /**
     * 将参数val根据参数valueType所表示的类型做类型转换
     * @Methods Name convertStringToObject
     * @Create In Sep 15, 2010 By duxh
     * @param val
     * @param valueType
     * @Return Object
     */
    private Object convertStringToObject(Object val,Class valueType){
    	if(val==null){
    		return null;
    	}
    	String value=val.toString();
    	if(value.trim().length()==0){
    		return null;
    	}
		if(valueType.isAssignableFrom(Integer.class)){
			return Integer.valueOf(value);
		}else if(valueType.isAssignableFrom(Double.class)){
			return Double.valueOf(value);
		}else if(valueType.isAssignableFrom(Float.class)){
			return Float.valueOf(value);
		}else if(valueType.isAssignableFrom(Long.class)){
			return Long.valueOf(value);
		}else if(valueType.isAssignableFrom(String.class)){
			return value;
		}else if(valueType.isAssignableFrom(Date.class)){
			return DateUtil.convertStringToDate(value);
		}else if(valueType.isAssignableFrom(BaseObject.class)){
			return Long.valueOf(value);
		}else{
			return null;
		}
    }
    /**
     * 保存配置项必要关系
     * @Methods Name saveConfigItemNecessaryRel
     * @Create In May 25, 2010 By duxh
     * @Return String
     */
    public String saveConfigItemNecessaryRel(){
		try {
			String configItemNecessaryRelPanel = super.getRequest().getParameter("configItemNecessaryRelPanel");
			String id = super.getRequest().getParameter("id");
			HashMap infoMap = new HashMap();
			JSONObject jo = JSONObject.fromObject(configItemNecessaryRelPanel);
			Iterator itInfo = jo.keys();
			String configItemType="";
			String otherConfigItemType="";
			String parentOrChildType="";
			while (itInfo.hasNext()) {
				String key = (String) itInfo.next();
				String value=jo.getString(key);
				key = key.split("\\$")[1];
				infoMap.put(key, value);
				if(key.equals("configItemType")){
					configItemType=value;
				}
				if(key.equals("otherConfigItemType")){
					otherConfigItemType=value;
				}
				if(key.equals("parentOrChildType")){
					parentOrChildType=value;
				}
			}
			String json="";
			if(otherConfigItemType!=null&&otherConfigItemType.trim().length()!=0){
				Map<String,Object> map=new HashMap<String,Object>();
				Map<String,Object> reverseMap=new HashMap<String,Object>();
				map.put("configItemType.id", Long.valueOf(configItemType));
				reverseMap.put("otherConfigItemType.id", Long.valueOf(configItemType));
				map.put("otherConfigItemType.id", Long.valueOf(otherConfigItemType));
				reverseMap.put("configItemType.id", Long.valueOf(otherConfigItemType));
				reverseMap.put("parentOrChildType",Integer.valueOf(parentOrChildType));
				List<ConfigItemNecessaryRel> necessaryRel=configItemService.findObjects(ConfigItemNecessaryRel.class, map, null);
				//已存在此种必要关系
				if(!necessaryRel.isEmpty()&&!id.trim().equals(necessaryRel.get(0).getId().toString())){
					json="{exist:true}";
				}
				if(json.length()==0){
					List<ConfigItemNecessaryRel> reverseNecessaryRel=configItemService.findObjects(ConfigItemNecessaryRel.class, reverseMap, null);
					//存在冲突的必要关系
					if(!reverseNecessaryRel.isEmpty()&&!id.trim().equals(reverseNecessaryRel.get(0).getId().toString())){
						json="{reverse:true}";
					}
				}
			}
			if(json.length()==0){
				if(id==null||id.trim().length()==0){
					infoMap.put("createDate", new Date());
					infoMap.put("createUser", UserContext.getUserInfo());
				}else{
					infoMap.put("modifyDate", new Date());
					infoMap.put("modifyUser", UserContext.getUserInfo());
				}
				metaDataManager.saveEntityData(ConfigItemNecessaryRel.class, infoMap);
			}else{
				getResponse().setContentType("text/plain;charset=gbk");
				PrintWriter out = getResponse().getWriter();
				out.write(json);
				out.flush();
				out.close();
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
    }
    /**
     * 删除配置项必要关系
     * @Methods Name removeConfigItemNecessaryRel
     * @Create In May 25, 2010 By duxh
     * @Return String
     */
    public String removeConfigItemNecessaryRel(){
		try {
			String idArrayString=getRequest().getParameter("idArray");
			Object[] idArrayTemp=JSONArray.fromObject(idArrayString).toArray();
			Long[] idArray =new Long[idArrayTemp.length];
			for(int i=0;i<idArrayTemp.length;i++){
				idArray[i]=Long.parseLong(idArrayTemp[i].toString());
			}
			configItemService.removeObjects(ConfigItemNecessaryRel.class, idArray);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
    }
    /**
     * 配置项关系替换
     * 以下情况不允许替换
     * 1.已存在此种正式关系
     * 2.当前变更申请已存在此种关系草稿
     * @Methods Name replaceRel
     * @Create In May 28, 2010 By duxh
     * @Return String
     */
    public String replaceRel(){
    	String ridArrayString=getRequest().getParameter("rids");
    	String itemCode=getRequest().getParameter("itemCode");
    	String replaceItemCode=getRequest().getParameter("replaceItemCode");
    	String modifyId=getRequest().getParameter("modifyId");
    	List<CIRelationShip> rels=new ArrayList<CIRelationShip>();
    	if(ridArrayString!=null&&ridArrayString.trim().length()!=0){
			Object[] ridArrayTemp=JSONArray.fromObject(ridArrayString).toArray();
			Long[] ridArray =new Long[ridArrayTemp.length];
			for(int i=0;i<ridArrayTemp.length;i++){
				ridArray[i]=Long.parseLong(ridArrayTemp[i].toString());
			}
			rels=configItemService.findObjects(CIRelationShip.class, "id", ridArray, null);
    	}else{
    		//可以替换的关系
    		rels=configItemService.findReplaceRelList(itemCode,Long.valueOf(modifyId));
    	}
    	List<CIRelationShip> replaceRels=new ArrayList<CIRelationShip>();
    	StringBuilder sameValid=new StringBuilder();
    	StringBuilder sameDraft=new StringBuilder();
    	for(CIRelationShip rel:rels){
    		CIRelationShip replaceRel=new CIRelationShip();
    		//拷贝原关系
    		BeanUtils.copyProperties(rel, replaceRel, new String[]{"id"});
    		//替换拷贝之后的关系
    		if(replaceRel.getParentConfigItemCode()!=null&&replaceRel.getParentConfigItemCode().equals(itemCode)){
    			replaceRel.setParentConfigItemCode(replaceItemCode);
    		}else if(replaceRel.getChildConfigItemCode()!=null&&replaceRel.getChildConfigItemCode().equals(itemCode)){
    			replaceRel.setChildConfigItemCode(replaceItemCode);
    		}
    		replaceRel.setStatus(CIRelationShip.DRAFT_STATUS);
    		replaceRels.add(replaceRel);
    		String parentCode="";
    		String childCode="";
    		if(replaceRel.getParentConfigItemCode()!=null){
    			parentCode=replaceRel.getParentConfigItemCode();
    		}else if(replaceRel.getParentServiceItemCode()!=null){
    			parentCode=replaceRel.getParentServiceItemCode();
    		}
    		if(replaceRel.getChildConfigItemCode()!=null){
    			childCode=replaceRel.getChildConfigItemCode();
    		}else if(replaceRel.getChildServiceItemCode()!=null){
    			childCode=replaceRel.getChildServiceItemCode();
    		}
    		CIRelationShip sameValidRel=configItemService.findHasSameValidRel(parentCode, childCode);
    		//正式关系中已存在此种关系
    		if(sameValidRel!=null){
    			sameValid.append("<li><p>已存在此种关系:【父】"+parentCode+"--->【子】"+childCode+".</p></li>");
    		}
    		//本次申请中已存在此种草稿
    		boolean has=configItemService.findHasSameDraft(Long.valueOf(modifyId), parentCode, childCode, null);
    		if(has){
    			sameDraft.append("<li><p>您已存在此种关系草稿:【父】"+parentCode+"--->【子】"+childCode+".</p></li>");
    		}
    	}
    	sameValid.append(sameDraft);
    	if(sameValid.length()==0){
    		configItemService.saveReplaceRel(rels, replaceRels, Long.valueOf(modifyId));
    	}else{
			try {
				getResponse().setContentType("text/plain;charset=gbk");
				PrintWriter out = getResponse().getWriter();
				out.write(sameValid.toString());
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new ApplicationException();
			}
    	}
    	return null;
    }
    /**
     * 查询某配置项的必要关系及是否已经存在
     * @Methods Name findCINecessaryRel
     * @Create In May 31, 2010 By duxh
     * @Return String
     */
    public String findCINecessaryRel(){
    	String itemCode=getRequest().getParameter("itemCode");
    	String modifyId=getRequest().getParameter("modifyId");
    	String TypeId=getRequest().getParameter("TypeId");
    	Map map=new HashMap();
    	map.put("configItemType.id", Long.valueOf(TypeId));
    	List<ConfigItemNecessaryRel> necessaryRels=configItemService.findObjects(ConfigItemNecessaryRel.class, map, null);
    	StringBuilder json=null;
    	if(!necessaryRels.isEmpty()){
    		json=new StringBuilder("{success: true,data:[");
    		//此配置项的所有关系
    		List<CIRelationShip> rels=configItemService.findCIRelationShip(Long.valueOf(modifyId), itemCode);
    		//不存在的必要关系
    		List<ConfigItemNecessaryRel> notExistNecessaryRels=configItemService.findNotExistNecessaryRel(necessaryRels, rels);
    		int i=0;
    		toNext:for(ConfigItemNecessaryRel necessaryRel:necessaryRels){
		    			if(i!=0){
		    				json.append(",");
		    			}
		    			i++;
		    			json.append("{configItemType:\""+necessaryRel.getConfigItemType().getName()+"\",");
		    			json.append("otherConfigItemType:\""+(necessaryRel.getOtherConfigItemType()==null?"":necessaryRel.getOtherConfigItemType().getName())+"\",");
		    			json.append("isOptional:\""+(necessaryRel.getIsOptional()==1?"是":"否")+"\",");
		    			json.append("parentOrChildType:\""+(necessaryRel.getParentOrChildType().compareTo(ConfigItemNecessaryRel.PARENT_CHILD_REL_TYPE)==0?"父-->子":"子-->父")+"\",");
		    			//哪些必要关系已存在哪些不存在
		    			for(ConfigItemNecessaryRel notExistNecessaryRel:notExistNecessaryRels){
		    				if(necessaryRel.getId().compareTo(notExistNecessaryRel.getId())==0){
		    						json.append("isExist:\"不存在\"}");
		    						continue toNext;
		    				}
		    			}
		    			json.append("isExist:\"已存在\"}");
    				}
			json.append("]}");
			try {
				getResponse().setContentType("text/plain;charset=gbk");
				PrintWriter out = getResponse().getWriter();
				out.write(json.toString());
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new ApplicationException();
			}
    	}
    	return null;
    }
    /**
     * 保存维护关系的变更计划并生成必要关系
     * @Methods Name savePlanAndCreateNecessaryRel
     * @Create In Jun 2, 2010 By duxh
     * @Return String
     */
    public String savePlanAndCreateNecessaryRel(){
		try {
			String planPanel=getRequest().getParameter("planPanel");
			String modifyId=getRequest().getParameter("modifyId");
			String configItemId=getRequest().getParameter("configItemId");
			String createAllNecessaryRelStr=getRequest().getParameter("createAllNecessaryRel");
			ConfigItem ci=(ConfigItem) getService().find(ConfigItem.class, configItemId);
			boolean createAllNecessaryRel=false;
			if(createAllNecessaryRelStr!=null&&createAllNecessaryRelStr.trim().length()!=0){
				createAllNecessaryRel=Boolean.valueOf(createAllNecessaryRelStr);
			}
			JSONObject planJson=JSONObject.fromObject(planPanel);
			
			Map planMap=new HashMap();
			
			Set<String> planSet=planJson.keySet();
			
			for (String key:planSet) {
				String keyString = key.split("\\$")[1];
				String value = planJson.getString(key);
				planMap.put(keyString, value);
			}
			configItemService.savePlanAndCreateNecessaryRel(planMap,Long.valueOf(modifyId),ci,createAllNecessaryRel);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return null;
	}
    /**
     * 生成必要关系
     * @Methods Name createNecessaryRel
     * @Create In Jun 4, 2010 By duxh
     * @Return String
     */
    public String createNecessaryRel(){
		String modifyId=getRequest().getParameter("modifyId");
		String dataId=getRequest().getParameter("dataId");
		String pid=getRequest().getParameter("pid");
		String createAllNecessaryRelStr=getRequest().getParameter("createAllNecessaryRel");
		
		boolean createAllNecessaryRel=false;
		if(createAllNecessaryRelStr!=null&&createAllNecessaryRelStr.trim().length()!=0){
			createAllNecessaryRel=Boolean.valueOf(createAllNecessaryRelStr);
		}
		//维护关系的配置项
		ConfigItem ci=(ConfigItem) getService().find(ConfigItem.class, dataId);
		//维护关系的变更计划
		CIBatchModifyPlan plan=(CIBatchModifyPlan) getService().find(CIBatchModifyPlan.class, pid);
		configItemService.saveNecessaryRel(Long.valueOf(modifyId), ci, plan,createAllNecessaryRel);
    	return null;
    }
    /**
     * 查询某配置项状态是否为"备用、禁用、已归档、租出"（如果存在变更，以变更为准）
     * @Methods Name findIsOrphanCI
     * @Create In May 31, 2010 By duxh
     * @Return String
     */
    public String findIsOrphanCI(){
    	String json="";
    	String itemCode=getRequest().getParameter("itemCode");
    	String modifyId=getRequest().getParameter("modifyId");
    	String TypeId=getRequest().getParameter("TypeId");
    	ConfigItem modifyCI=configItemService.findModifyConfigItem(Long.valueOf(modifyId), itemCode);
		List<String>  status=new ArrayList<String>();
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_ARCHIVED);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_DISABLED);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_LOAN);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_STANDBY);
		//本次变更申请变更了此配置项
    	if(modifyCI!=null){
    		String modifyCIStatus=modifyCI.getConfigItemStatus()==null?null:modifyCI.getConfigItemStatus().getEnname();
    		if(status.contains(modifyCIStatus)){
    			json="{isOrphan:true,status:\""+modifyCI.getConfigItemStatus().getName()+"\"}";
    		}
    	}else{
        	Map map=new HashMap();
        	map.put("cisn", itemCode);
        	map.put("status", ConfigItem.VALID_STATUS);
        	List<ConfigItem> validCIs=configItemService.findObjects(ConfigItem.class, map, null);
        	ConfigItem validCI=null;
        	if(!validCIs.isEmpty()){
        		validCI=validCIs.get(0);
        	}
    		if(validCI!=null){
    			String validCIStatus=validCI.getConfigItemStatus()==null?null:validCI.getConfigItemStatus().getEnname();
        		if(status.contains(validCIStatus)){
        			json="{isOrphan:true,status:\""+validCI.getConfigItemStatus().getName()+"\"}";
        		}
    		}
    	}
    	if(json.length()==0){
	    	Map necessaryMap=new HashMap();
	    	necessaryMap.put("configItemType.id", Long.valueOf(TypeId));
	    	List<ConfigItemNecessaryRel> necessaryRels=configItemService.findObjects(ConfigItemNecessaryRel.class, necessaryMap, null);
	    	//没有可维护的必要关系
	    	if(necessaryRels.isEmpty()){
	    		json="{noneNecessaryRel:true}";
	    	}
    	}
    	if(json.length()!=0){
    		try {
				getResponse().setContentType("text/plain;charset=gbk");
				PrintWriter out = getResponse().getWriter();
				out.write(json);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new ApplicationException();
			}
    	}
    	return null;
    }
    /**
     * 查询某次变更中某配置项维护的必要关系
     * @Methods Name findMaintenanceRel
     * @Create In Jun 4, 2010 By duxh
     * @Return String
     */
    public String findMaintenanceRel(){
    	String configItemId=getRequest().getParameter("configItemId");
    	String modifyId=getRequest().getParameter("modifyId");
    	ConfigItem ci=new ConfigItem();
    	ci.setId(Long.valueOf(configItemId));
    	List<CIRelationShip> rels=configItemService.findMaintenanceRelPlan(Long.valueOf(modifyId), ci);
    	StringBuilder json=new StringBuilder("{data:[");
    	for(int i=0;i<rels.size();i++){
    		if(i!=0){
    			json.append(",");
    		}
    		json.append("{");
    		json.append("parentType:\""+(rels.get(i).getParentConfigItemType()==null?"":rels.get(i).getParentConfigItemType().getName())+"\",");
    		json.append("childType:\""+(rels.get(i).getChildConfigItemType()==null?"":rels.get(i).getChildConfigItemType().getName())+"\",");
    		json.append("parentCode:\""+(rels.get(i).getParentConfigItemCode()==null?"":rels.get(i).getParentConfigItemCode())+"\",");
    		json.append("childCode:\""+(rels.get(i).getChildConfigItemCode()==null?"":rels.get(i).getChildConfigItemCode())+"\"");
    		json.append("}");
    	}
    	json.append("]}");
		try {
			getResponse().setContentType("text/plain;charset=gbk");
			PrintWriter out = getResponse().getWriter();
			out.write(json.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
    	return null;
    }
    /**
     * 维护配置项必要关系
     * @Methods Name maintenanceNecessaryRel
     * @Create In Jun 13, 2010 By duxh
     * @return String
     */
    public String maintenanceNecessaryRel(){
    	String modifyId=getRequest().getParameter("modifyId");
		String dataId=getRequest().getParameter("dataId");
		//是否生成所有必要关系包括可选的
		String createAllNecessaryRelStr=getRequest().getParameter("createAllNecessaryRel");
		
		boolean createAllNecessaryRel=false;
		if(createAllNecessaryRelStr!=null&&createAllNecessaryRelStr.trim().length()!=0){
			createAllNecessaryRel=Boolean.valueOf(createAllNecessaryRelStr);
		}
		ConfigItem ci=(ConfigItem) getService().find(ConfigItem.class, dataId);
		List<CIBatchModifyPlan> plan=configItemService.findMaintenanceConfigItem(Long.valueOf(modifyId), ci, CIBatchModifyPlan.MODIFY_SUCCESS);
		if(!plan.isEmpty()){
			configItemService.saveNecessaryRel(Long.valueOf(modifyId), ci, plan.get(0),createAllNecessaryRel);
		}else{
			try {
				getResponse().setContentType("text/plain;charset=gbk");
				PrintWriter out = getResponse().getWriter();
				out.write("{message:'toMaintenance'}");
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new ApplicationException();
			}
		}
    	return null;
    }
    
    
    /**
     * 查找缺少必要关系的配置项
     * @Methods Name findConfigItemNecessaryRelation
     * @Create In 22 7, 2010 By zhangzy
     * @return void
     */
    public void findConfigItemNecessaryRelation(){
    	HttpServletRequest request = (HttpServletRequest) this.getRequest();
    	HttpServletResponse response = this.getResponse();
    	int start =HttpUtil.getInt(request,"start",0);
		int pageSize = HttpUtil.getInt(request, "pageSize",10);
    	String strConfigItemNum = request.getParameter("configItemNum");
    	String strConfigItemName = request.getParameter("configItemName");
    	String strConfigItemType = request.getParameter("configItemType");
    	String strEngineer = request.getParameter("engineer");
    	String strIsOptional = request.getParameter("isOptional");
    	
    	Map paramMap = new HashMap();
    	paramMap.put("configItemNum", strConfigItemNum);
    	paramMap.put("configItemName", strConfigItemName);
    	paramMap.put("configItemType", strConfigItemType);
    	paramMap.put("engineer", strEngineer);
    	paramMap.put("isOptional", strIsOptional);
    	
    	
    	Page page = configItemService.getConfigItemNecessaryRelation(paramMap,start,pageSize);
    	Long count = page.getTotalCount();
    	if(count.compareTo(0L)==0){
    		count=1L;
    	}
    	StringBuilder sb = new StringBuilder("{success:true, rowCount : '"+count+"', data:[");
    	List dataList = page.list();
    	if(dataList!=null){
	    	for(int i =0; i<dataList.size();i++){
	    		Object[] dataArray = (Object[]) dataList.get(i);
	    		if(i != 0){
	    			sb.append(",");
	    		}
	    		sb.append("{'configItemType':'"+dataArray[0]+"',");
	    		sb.append("'otherConfigItemType':'"+dataArray[1]+"',");
	    		sb.append("'parentOrChildType':'"+dataArray[2]+"',");
	    		sb.append("'configItemId':'"+dataArray[3]+"',");
	    		sb.append("'configItemName':'"+dataArray[4]+"',");
	    		sb.append("'configItemNum':'"+dataArray[5]+"',");
	    		sb.append("'configItemNecessaryId':'"+dataArray[6]+"',");
	    		sb.append("'isOptional':'"+dataArray[7]+"',");
	    		sb.append("'engineer':'"+dataArray[8]+"',");
	    		sb.append("'configItemTypeName':'"+dataArray[9]+"',");
	    		sb.append("'otherConfigItemTypeName':'"+dataArray[10]+"',");
	    		sb.append("'necessaryRelation':'"+dataArray[11]+"',");
	    		sb.append("'necessaryRelationType':'"+dataArray[12]+"'");
	    		sb.append("}");	    		
	    	}
    	}
    	sb.append("]}"); 
    	
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			if(sb!=null){
				out.println(sb.toString());
			}
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
			throw new ApplicationException();
		}
    }
    
    /**
     * 把配置项的缺少必要关系添加到配置单草稿中
     * @Methods Name CreateConfigItemNecessaryRelation
     * @Create In 28 7, 2010 By zhangzy
     * @return void
     */
	public void createConfigItemNecessaryRelation(){
		HttpServletRequest request = getRequest();
		HttpServletResponse response = getResponse();
		
		String[] necessaryIds = request.getParameterValues("necessaryIds") ; 
		String[] configItemIds = request.getParameterValues("configItemIds") ; 
		String batchModifyId = request.getParameter("batchModifyId");
		String[] configItemCode = request.getParameterValues("configItemCodes");
		List<ConfigItem> cis=new ArrayList<ConfigItem>();
		for(String configItemId:configItemIds){
			ConfigItem ci=new ConfigItem();
			ci.setId(Long.valueOf(configItemId));
			cis.add(ci);
		}
		List<CIRelationShip> returnDataList = configItemService.saveNecessaryRelation(necessaryIds, batchModifyId, configItemCode ,cis);		
		StringBuilder sb = new StringBuilder("{'necessaryRelationIsExist':");
		if(returnDataList.isEmpty()){
			sb.append("'0',");
		}else{
			sb.append("'1',");
		}
		sb.append("'relationArray':[");
		for(int i = 0 ; i < returnDataList.size();i++){
			CIRelationShip cirs = returnDataList.get(i);
			if(i!=0){
				sb.append(",");
			}
			sb.append("{");
			sb.append("'parentConfigItemTypeName':'"+cirs.getParentConfigItemType().getName()+"',");
			sb.append("'parentConfigItemCode':'"+cirs.getParentConfigItemCode()+"',");
			sb.append("'childConfigItemTypeName':'"+cirs.getChildConfigItemType().getName()+"',");
			sb.append("'childConfigItemCode':'"+cirs.getChildConfigItemCode()+"'");
			sb.append("}");
		}
		sb.append("]}");
		
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			if(sb!=null){
				out.println(sb.toString());
			}
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
    /**
     * 查询必要关系A端、B端相关信息
     * 因为不确定此关系到底是父还是子的必要关系，
     * 查询时要分别查询父和子的必要关系，只取其一
     * @Methods Name findTechnoInfo
     * @Create In Aug 4, 2010 By duxh
     * @return String
     */
    public String findTechnoInfo(){
    	String parentConfigItemType=getRequest().getParameter("parentConfigItemType");
		String childConfigItemType=getRequest().getParameter("childConfigItemType");
		if(parentConfigItemType==null||parentConfigItemType.trim().length()==0||
		   childConfigItemType==null||childConfigItemType.trim().length()==0){
			return null;
		}
		Map properties=new HashMap();
		properties.put("configItemType.id", Long.valueOf(parentConfigItemType));
		properties.put("otherConfigItemType.id", Long.valueOf(childConfigItemType));
		properties.put("parentOrChildType", ConfigItemNecessaryRel.PARENT_CHILD_REL_TYPE);
		//父的必要关系
		List<ConfigItemNecessaryRel> configItemNecessaryRels=configItemService.findObjects(ConfigItemNecessaryRel.class, properties, null);
		if(configItemNecessaryRels.isEmpty()){
			Map reverse = new HashMap();
			reverse.put("configItemType.id" , Long.valueOf(childConfigItemType));
			reverse.put("otherConfigItemType.id" , Long.valueOf(parentConfigItemType));
			reverse.put("parentOrChildType", ConfigItemNecessaryRel.CHILD_PARENT_REL_TYPE);
			//子的必要关系
			configItemNecessaryRels=configItemService.findObjects(ConfigItemNecessaryRel.class, reverse, null);
			if(configItemNecessaryRels.isEmpty()){
				return null;
			}
		}
		
		ConfigItemNecessaryRel configItemNecessaryRel=configItemNecessaryRels.get(0);
		String atechnoInfoDisplayName="";
		String atechnoInfoAllowBlank="";
		String atechnoInfoTip="";
		String atechnoInfo="";
		String btechnoInfoDisplayName="";
		String btechnoInfoAllowBlank="";
		String btechnoInfoTip="";
		String btechnoInfo="";
		if(configItemNecessaryRel.getParentOrChildType().compareTo(ConfigItemNecessaryRel.PARENT_CHILD_REL_TYPE)==0){
			atechnoInfoDisplayName=(configItemNecessaryRel.getAtechnoInfoDisplayName()==null?"":configItemNecessaryRel.getAtechnoInfoDisplayName());
			atechnoInfoAllowBlank=(configItemNecessaryRel.getAtechnoInfoAllowBlank()==null?"":configItemNecessaryRel.getAtechnoInfoAllowBlank().toString());
			atechnoInfoTip=(configItemNecessaryRel.getAtechnoInfoTip()==null?"":configItemNecessaryRel.getAtechnoInfoTip());
			atechnoInfo=(configItemNecessaryRel.getAtechnoInfo()==null?"":configItemNecessaryRel.getAtechnoInfo());
			btechnoInfoDisplayName=(configItemNecessaryRel.getBtechnoInfoDisplayName()==null?"":configItemNecessaryRel.getBtechnoInfoDisplayName());
			btechnoInfoAllowBlank=(configItemNecessaryRel.getBtechnoInfoAllowBlank()==null?"":configItemNecessaryRel.getBtechnoInfoAllowBlank().toString());
			btechnoInfoTip=(configItemNecessaryRel.getBtechnoInfoTip()==null?"":configItemNecessaryRel.getBtechnoInfoTip());
			btechnoInfo=(configItemNecessaryRel.getBtechnoInfo()==null?"":configItemNecessaryRel.getBtechnoInfo());
		}else{
			atechnoInfoDisplayName=(configItemNecessaryRel.getBtechnoInfoDisplayName()==null?"":configItemNecessaryRel.getBtechnoInfoDisplayName());
			atechnoInfoAllowBlank=(configItemNecessaryRel.getBtechnoInfoAllowBlank()==null?"":configItemNecessaryRel.getBtechnoInfoAllowBlank().toString());
			atechnoInfoTip=(configItemNecessaryRel.getBtechnoInfoTip()==null?"":configItemNecessaryRel.getBtechnoInfoTip());
			atechnoInfo=(configItemNecessaryRel.getBtechnoInfo()==null?"":configItemNecessaryRel.getBtechnoInfo());
			btechnoInfoDisplayName=(configItemNecessaryRel.getAtechnoInfoDisplayName()==null?"":configItemNecessaryRel.getAtechnoInfoDisplayName());
			btechnoInfoAllowBlank=(configItemNecessaryRel.getAtechnoInfoAllowBlank()==null?"":configItemNecessaryRel.getAtechnoInfoAllowBlank().toString());
			btechnoInfoTip=(configItemNecessaryRel.getAtechnoInfoTip()==null?"":configItemNecessaryRel.getAtechnoInfoTip());
			btechnoInfo=(configItemNecessaryRel.getAtechnoInfo()==null?"":configItemNecessaryRel.getAtechnoInfo());
		}
		try {
			StringBuilder sb=new StringBuilder();
			sb.append(" { ");
			sb.append(" atechnoInfoDisplayName : \""+atechnoInfoDisplayName+"\"");
			sb.append(" , atechnoInfoAllowBlank : \""+atechnoInfoAllowBlank+"\"");
			sb.append(" , atechnoInfoTip : \""+atechnoInfoTip+"\"");
			sb.append(" , atechnoInfo : \""+atechnoInfo+"\"");
			sb.append(" , btechnoInfoDisplayName : \""+btechnoInfoDisplayName+"\"");
			sb.append(" , btechnoInfoAllowBlank : \""+btechnoInfoAllowBlank+"\"");
			sb.append(" , btechnoInfoTip : \""+btechnoInfoTip+"\"");
			sb.append(" , btechnoInfo : \""+btechnoInfo+"\"");
			sb.append(" } ");
			getResponse().setContentType("text/plain;charset=gbk");
			PrintWriter out = getResponse().getWriter();
			out.write(sb.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return null;
    }
    
	public ConfigItemService getConfigItemService() {
		return configItemService;
	}
	
	public MetaDataManager getMetaDataManager() {
		return metaDataManager;
	}

	public void setMetaDataManager(MetaDataManager metaDataManager) {
		this.metaDataManager = metaDataManager;
	}

	public void setConfigItemService(ConfigItemService configItemService) {
		this.configItemService = configItemService;
	}
}
