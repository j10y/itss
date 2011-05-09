package com.zsgj.itil.event.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.GrantedAuthority;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.ApplicationException;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.itil.actor.entity.SupportGroup;
import com.zsgj.itil.actor.entity.SupportGroupEngineer;
import com.zsgj.itil.actor.entity.SupportGroupRank;
import com.zsgj.itil.actor.entity.SupportGroupServiceItem;
import com.zsgj.itil.config.extci.entity.ServiceEngineer;
import com.zsgj.itil.config.service.ConfigItemService;
import com.zsgj.itil.event.entity.Event;
import com.zsgj.itil.event.service.SupportGroupService;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.train.entity.Quest;
import com.zsgj.itil.train.entity.QuestOption;
import com.zsgj.itil.train.entity.Survey;

public class SupportGroupAction extends BaseAction {
	static final String FSP = System.getProperty("file.separator");
	static final String LSP = System.getProperty("line.separator");
	private SupportGroupService supportGroupService = (SupportGroupService) getBean("SupportGroupService");
	private MetaDataManager metaDataManager = (MetaDataManager) ContextHolder.getBean("metaDataManager");
	private Service service = (Service) ContextHolder.getBean("baseService");
	private ConfigItemService configItemService = (ConfigItemService) getBean("configItemService");
	
	/**
	 * 获取默认支持组数据.
	 * @Methods Name findAllSupportGroupServiceItem
	 * @Create In Nov 7, 2009 By duxh
	 * @return
	 * @throws IOException
	 */
	public String findAllSupportGroupServiceItem() throws IOException {
		String supportId = super.getRequest().getParameter("supportId");
		 List<SupportGroupServiceItem> list = supportGroupService.findSupportGroupData(supportId);
			try {
				String json = "";
				for (SupportGroupServiceItem support : list) {
					json += "[";
					json += "'" + support.getServiceItem().getId() + "',";
					json += "'" + support.getServiceItem().getName() + "'";
					json += "],";
				}
				if (json.endsWith(",")) {
					json = json.substring(0, json.length() - 1);
				}
				json = "[" + json + "]";
				HttpServletResponse response = super.getResponse();
				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				PrintWriter writer = response.getWriter();
				writer.write(json);
				writer.flush();
				return null;
			} catch (RuntimeException e) {
				HttpServletResponse response = super.getResponse();
				e.printStackTrace();
				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				PrintWriter writer = response.getWriter();
				writer.write("{success:false}");
				writer.flush();
				return null;
			}

	}

	/**
	 * 获取服务项数据
	 * 
	 * @Methods Name serviceItemData
	 * @Create In Mar 17, 2009 By Administrator
	 * @return
	 * @throws IOException
	 *             String
	 */
	public String serviceItemData() throws IOException {
		String official = super.getRequest().getParameter("official");//2010-05-25 add by huzh for
		List<ServiceItem> list = supportGroupService.findServiceItemData(official);
		try {
			String json = "";
			for (ServiceItem serviceItem : list) {
				json += "[";
				json += "'" + serviceItem.getId() + "',";
				json += "'" + serviceItem.getName() + "'";
				json += "],";
			}
			if (json.endsWith(",")) {
				json = json.substring(0, json.length() - 1);
			}
			json = "[" + json + "]";
			HttpServletResponse response = super.getResponse();
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write(json);
			writer.flush();
			return null;
		} catch (RuntimeException e) {
			HttpServletResponse response = super.getResponse();
			e.printStackTrace();
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write("{success:false}");
			writer.flush();
			return null;
		}

	}

	/**
	 * 获取服务商类型
	 * 
	 * @Methods Name selectServiceProviderTypeData
	 * @Create In Mar 18, 2009 By Administrator
	 * @return
	 * @throws IOException
	 *             String
	 */

//	public String selectServiceProviderTypeData() throws IOException {
//		HttpServletResponse response = super.getResponse();
//		String serviceProviderTypeId = "";
//		List<ServiceProviderType> list = supportGroupService.findServiceProviderType(serviceProviderTypeId);
//		String json = "";
//		for (ServiceProviderType serviceProviderType : list) {
//			json += "{id:'" + serviceProviderType.getId() + "',name:'" + serviceProviderType.getName() + "'},";
//		}
//		if (json.endsWith(",")) {
//			json = json.substring(0, json.length() - 1);
//		}
//		json = "{success: true, rowCount:" + list.size() + ",data:[" + json + "]}";
//		response.setContentType("text/plain");
//		response.setCharacterEncoding("UTF-8");
//		PrintWriter out = response.getWriter();
//		out.println(json);
//		out.flush();
//		out.close();
//		return null;
//	}

	/**
	 * 获取服务商
	 * 
	 * @Methods Name selectServiceProvider
	 * @Create In Mar 18, 2009 By Administrator
	 * @return
	 * @throws IOException
	 *             String
	 */
//	public String selectServiceProvider() throws IOException {
//		HttpServletRequest request = super.getRequest();
//		HttpServletResponse response = super.getResponse();
//		String serviceProviderId = request.getParameter("serviceProviderId");
//		if (serviceProviderId == null) {
//			return null;
//		}
//		if (serviceProviderId != null) {
//			String json = "";
//			int size = 0;
//			List<ServiceProviderType> list = supportGroupService.findServiceProviderType(serviceProviderId);
//			if (list.get(0).getDiscValue().equals(ServiceProviderType.DISCVALUE_IN)) {
//				List<ServiceProviderIn> serviceProviderIn = supportGroupService.findServiceProviderIn();
//				size = serviceProviderIn.size();
//				for (ServiceProviderIn ServiceIN : serviceProviderIn) {
//					json += "{id:'" + ServiceIN.getId()+ "',name:'" + ServiceIN.getName() + "'},";
//				}
//			} else if (list.get(0).getDiscValue().equals(ServiceProviderType.DISCVALUE_OUT)) {
//				List<ServiceProviderOut> serviceEngineerOut = supportGroupService.findServiceProviderOut();
//				size = serviceEngineerOut.size();
//				for (ServiceProviderOut ServiceOUT : serviceEngineerOut) {
//					json += "{id:'" + ServiceOUT.getId()+ "',name:'" + ServiceOUT.getName() + "'},";
//				}
//
//			}
//
//			if (json.endsWith(",")) {
//				json = json.substring(0, json.length() - 1);
//			}
//			json = "{success: true, rowCount:" + size + ",data:[" + json + "]}";
//			response.setContentType("text/plain");
//			response.setCharacterEncoding("UTF-8");
//			PrintWriter out = response.getWriter();
//			out.println(json);
//			out.flush();
//			out.close();
//			return null;
//		}
//		return null;
//
//	}

	/**
	 * 通过服务商类和服务商和itcode得到服务工程师
	 * @Methods Name findServiceEngineer
	 * @Create In Nov 7, 2009 By duxh
	 * @return
	 * @throws IOException
	 */
//	public String findServiceEngineer() throws IOException {
//		HttpServletRequest request = super.getRequest();
//		String serviceProviderType= request.getParameter("serviceProviderType");
//		String serviceProviderIdTemp = request.getParameter("serviceProviderID");
//		Long serviceProviderID=null;
//		if(serviceProviderIdTemp.length()!=0){
//			serviceProviderID=Long.parseLong(serviceProviderIdTemp);
//		}
//		String itcode =request.getParameter("itcode");
//		int start = HttpUtil.getInt(request, "start", 0);
//		int pageSize = 10;
//		Page page = supportGroupService.findServiceEngineer(Long.parseLong(serviceProviderType),serviceProviderID, itcode, start, pageSize);
//		Long total = page.getTotalCount();
//		List<UserInfo> queryList = page.list();
//		StringBuilder json = new StringBuilder("{success: true, rowCount:" + total + ",data:[");
//		for (int i = 0; i < queryList.size(); i++) {
//			UserInfo user = queryList.get(i);
//			if (i != 0)
//				json.append(",");
//			json.append("{");
//			json.append("id:'" + user.getId() + "',");
//			json.append("userInfo:'" + user.getRealName() + "/" + user.getUserName() + "/"
//					+ user.getDepartment().getDepartName() + "'");
//			json.append("}");
//		}
//		json.append("]");
//		json.append("}");
//		super.getResponse().setContentType("text/plain");
//		super.getResponse().setCharacterEncoding("UTF-8");
//		PrintWriter out = super.getResponse().getWriter();
//		out.println(json);
//		out.flush();
//		out.close();
//		return null;
//
//	}
	/**
	 * 保存支持组及其工程师、支持的服务项。
	 * @Methods Name saveOrModifySupportGroup
	 * @Create In Nov 12, 2009 By duxh
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public String saveOrModifySupportGroup() throws IOException {
		try {
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String json="";
			String supportGroup = request.getParameter("supportGroup");
			String engineersIdString = request.getParameter("engineersId");
			String serviceItemsIdString = request.getParameter("serviceItemsId");
			String supportGroupIdString = request.getParameter("supportGroupId");
			String[] serviceItemsIdTemp=serviceItemsIdString.split(",");
			Long[] serviceItemsId=new Long[serviceItemsIdTemp.length];
			for(int i=0;i<serviceItemsIdTemp.length;i++){
				serviceItemsId[i]=Long.parseLong(serviceItemsIdTemp[i]);
			}
			Long supportGroupId=null;
			if(supportGroupIdString.trim().length()!=0){
				supportGroupId = Long.parseLong(supportGroupIdString);
			}
			HashMap supportGroupMap = new HashMap();
			JSONObject jo = JSONObject.fromObject(supportGroup);
			Iterator itInfo = jo.keys();
			while (itInfo.hasNext()) {
				String key = (String) itInfo.next();
				String[] keyString = key.split("\\$");
				String value = jo.getString(key);
				supportGroupMap.put(keyString[1], value);
			}
			SupportGroupRank groupRank=(SupportGroupRank) getService().find(SupportGroupRank.class, supportGroupMap.get("groupRank").toString());
			//查找服务项是否已经有一级支持组,并且不是当前支持组。
			List<String>  serviceItemsName=new ArrayList();
			if(groupRank.getKeyString().equals(SupportGroupRank.FIRST_RANK)){
				serviceItemsName=supportGroupService.findFirstRankGroup(serviceItemsId, supportGroupId);
			}
			if(!serviceItemsName.isEmpty()){
				json=serviceItemsName.toString().substring(1,serviceItemsName.toString().length()-1);
			}else{
				Object[] engineersIdTemp=JSONArray.fromObject(engineersIdString).toArray();
				Long[] engineersId =new Long[engineersIdTemp.length];
				for(int i=0;i<engineersIdTemp.length;i++){
					engineersId[i]=Long.parseLong(engineersIdTemp[i].toString());
				}
				if(supportGroupId==null){//新建支持组
					supportGroupMap.put("createUser", UserContext.getUserInfo());
					supportGroupMap.put("createDate", new Date());
					supportGroupMap.put("deleteFlag", SupportGroup.DELETEFLAG_USEING);
					supportGroupService.saveSupportGroup(supportGroupMap,engineersId,serviceItemsId);
				}
				else{//修改支持组
					supportGroupMap.put("modifyUser", UserContext.getUserInfo());
					supportGroupMap.put("modifyDate", new Date());
					supportGroupService.modifySupportGroup(supportGroupMap,engineersId,serviceItemsId);
				}
			}
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write(json);
			writer.flush();
			writer.close();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}

	}
	/**
	 * 删除支持组。
	 * 
	 * @Methods Name removeSupportGroup
	 * @Create In Nov 5, 2009 By duxh
	 * @return String
	 */
	public String removeSupportGroup() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String json = "";
		String idsStr = request.getParameter("ids");
		idsStr = idsStr.substring(0, idsStr.length() - 1);
		String[] ids = idsStr.split(",");
		Boolean isSuccess = supportGroupService.removeSupportGroupByIds(ids);
		if (isSuccess) {
			json = "{success : true}";
		} else {
			json = "{success : false}";
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(json);
		writer.flush();
		return null;
	}

	/**
	 * 查找支持组。
	 * 
	 * @Methods Name findSupportGroup
	 * @Create In Oct 30, 2009 By duxh
	 * @return String
	 */
	public String findSupportGroup() throws IOException {
		try {
			int pageSize = HttpUtil.getInt(getRequest(), "pageSize", 10);
			int start = HttpUtil.getInt(getRequest(), "start", 0);
			int pageNo = start / pageSize + 1;
			String orderBy = HttpUtil.getString(getRequest(), "orderBy", "id");
			boolean isAsc = HttpUtil.getBoolean(getRequest(), "isAsc", true);
			Map requestParams = HttpUtil.requestParam2Map(getRequest());
			Page page = metaDataManager.query(SupportGroup.class, requestParams, pageNo, pageSize, orderBy, isAsc);
			Long total = page.getTotalCount();
			List queryList = page.list();
			List<Map<String, Object>> listData = metaDataManager.getEntityMapDataForList(SupportGroup.class, queryList);
			StringBuilder json = new StringBuilder("{success: true, rowCount:" + total + ",data:[");
			for (int i = 0; i < listData.size(); i++) {
				if (i != 0)
					json.append(",");
				json.append("{");
				json.append("id:'" + listData.get(i).get("id") + "',");
				json.append("groupName:'" + listData.get(i).get("groupName") + "'");
				json.append("}");
			}
			json.append("]");
			json.append("}");
			getResponse().setContentType("text/plain");
			getResponse().setCharacterEncoding("UTF-8");
			PrintWriter out = super.getResponse().getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}

	/**
	 * 获取支持组工程师。
	 * 
	 * @Methods Name findSupportGroupEngineer
	 * @Create In Oct 30, 2009 By duxh
	 * @return String
	 */
	public String findSupportGroupEngineer() throws Exception {
		try {
			HttpServletRequest httpServletRequest = super.getRequest();
			String groupId = httpServletRequest.getParameter("groupId");
			String itcode = httpServletRequest.getParameter("itcode");
			int pageSize = HttpUtil.getInt(super.getRequest(), "pageSize", 10);
			int start = HttpUtil.getInt(super.getRequest(), "start", 0);
			SupportGroup supportGroup = null;
			if (!groupId.equals("")) {
				supportGroup = new SupportGroup();
				supportGroup.setId(Long.parseLong(groupId));
			}
			Page page = supportGroupService.findGroupEngineers(supportGroup, itcode, start, pageSize);
			Long total = page.getTotalCount();
			List queryList = page.list();
			StringBuilder json = new StringBuilder("{success: true, rowCount:" + total + ",data:[");
			for (int i = 0; i < queryList.size(); i++) {
				UserInfo user = ((SupportGroupEngineer) queryList.get(i)).getUserInfo();
				if (i != 0)
					json.append(",");
				json.append("{");
				json.append("id:'" + user.getId() + "',");
				json.append("userInfo:'" + user.getRealName() + "/" + user.getUserName() + "/"
						+ user.getDepartment().getDepartName() + "'");
				json.append("}");
			}
			json.append(",");
			json.append("{");
			json.append("id:'',");
			json.append("userInfo:'全部'");
			json.append("}");
			json.append("]");
			json.append("}");
			super.getResponse().setContentType("text/plain");
			super.getResponse().setCharacterEncoding("UTF-8");
			PrintWriter out = super.getResponse().getWriter();
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
	 * 查看当前工程师所在组的组长信息
	 * 
	 * @Methods Name findSupprotGroupLeader
	 * @Create In Oct 30, 2009 By duxh
	 * @return String
	 */
	public String findSupprotGroupLeader() throws IOException {
		try {
			HttpServletRequest httpServletRequest = super.getRequest();
			Long eventId = Long.parseLong(httpServletRequest.getParameter("eventId"));
			//2010-06-17 add by huzh for 保存服务项和服务项类型 begin
			Long siId = Long.parseLong(httpServletRequest.getParameter("serviceItemId"));
			Event event=(Event) service.findUnique(Event.class, "id", eventId);
			ServiceItem si=(ServiceItem) service.findUnique(ServiceItem.class, "id", siId);
			event.setScidData(si);
			event.setScidType(si.getServiceItemType());
			service.save(event);
			//2010-06-17 add by huzh for 保存服务项和服务项类型 end
			UserInfo currentUserInfo = UserContext.getUserInfo();
			Long userId = currentUserInfo.getId();
			// 2010-06-17 modified by huzh for 查找组长处理,返回工程师所在组的所有组长 begin
//			UserInfo groupLeader = supportGroupService.fingCurrentGroupLeader(userId, eventId);
			List<UserInfo> groupLeaders = supportGroupService.fingCurrentGroupLeaders(userId);
			String json = "";
			String leaders="";
			if (groupLeaders == null) {
				json = "{success : true, groupLeaderId : 'noLeader'}";
				super.getResponse().setContentType("text/plain");
				super.getResponse().setCharacterEncoding("UTF-8");
				PrintWriter out = super.getResponse().getWriter();
				out.println(json);
				out.flush();
				out.close();
				return null;
			}else{
				for (UserInfo groupleader : groupLeaders) {
					leaders += groupleader.getId() + ",";
				}
				leaders = leaders.substring(0, leaders.length() - 1);
			}
			// 2010-06-17 modified by huzh for 查找组长处理,返回工程师所在组的所有组长 end
			json = "{success : true, groupLeaderId : '" + leaders + "'}";
			super.getResponse().setContentType("text/plain");
			super.getResponse().setCharacterEncoding("UTF-8");
			PrintWriter out = super.getResponse().getWriter();
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
	 * 查询当前事件对应服务项的支持组组长为当前登录的组长的所有支持组工程师。
	 * 
	 * @Methods Name findCurrentGroupEngineers
	 * @Create In Nov 3, 2009 By duxh
	 * @return String
	 */
	public String findCurrentGroupEngineers() throws Exception {
		try {
			HttpServletRequest httpServletRequest = super.getRequest();
			String eventId = httpServletRequest.getParameter("eventId");
			String itcode = httpServletRequest.getParameter("itcode");
			UserInfo userInfo = UserContext.getUserInfo();
			int pageSize = HttpUtil.getInt(httpServletRequest, "pageSize", 10);
			int start = HttpUtil.getInt(httpServletRequest, "start", 0);
			int pageNo = start / pageSize + 1;
			Page page = supportGroupService.findCurrentGroupEngineers(eventId, userInfo, itcode, pageNo, pageSize);
			Long total = page.getTotalCount();
			List queryList = page.list();
			StringBuilder json = new StringBuilder("{success: true, rowCount:" + total + ",data:[");
			for (int i = 0; i < queryList.size(); i++) {
				UserInfo user =  (UserInfo) queryList.get(i);
				if (i != 0)
					json.append(",");
				json.append("{");
				json.append("id:'" + user.getId() + "',");
				json.append("userInfo:'" + user.getRealName() + "/" + user.getUserName() + "/"
						+ user.getDepartment().getDepartName() + "'");
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
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}

	/**
	 * 查询当前用户是否为支持组工程师.
	 * 
	 * @Methods Name isSupportEngineer
	 * @Create In Nov 5, 2009 By duxh
	 * @return String
	 */
	public String isSupportEngineer() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		UserInfo userInfo = UserContext.getUserInfo();
		String json = "";
		List<SupportGroupEngineer> list = super.getService().find(SupportGroupEngineer.class, "userInfo", userInfo);
		if (list.size() > 0) {
			json = "{success:true,flag:'yes'}";
		} else {
			json = "{success:true,flag:'no'}";
		}
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
	 * 根据支持组查找所有支持组工程师。
	 * @Methods Name findEngineersBySupportGroupId
	 * @Create In Nov 12, 2009 By duxh
	 * @return
	 */
	public String findEngineersBySupportGroupId(){
		try {
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String supportGroupId = request.getParameter("supportGroupId");
			List<SupportGroupEngineer> engineers=supportGroupService.findEngineersBySupportGroupId(Long.parseLong(supportGroupId));
			StringBuilder json = new StringBuilder("{success: true,data:[");
			for (int i = 0; i < engineers.size(); i++) {
				UserInfo user = ((SupportGroupEngineer) engineers.get(i)).getUserInfo();
				if (i != 0)
					json.append(",");
				json.append("{");
				json.append("id:'" + user.getId() + "',");
				json.append("userInfo:'" + user.getRealName() + "/" + user.getUserName() + "/"
						+ user.getDepartment().getDepartName() + "'");
				json.append("}");
			}
			json.append("]");
			json.append("}");
			super.getResponse().setContentType("text/plain");
			super.getResponse().setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	
	/**
	 * 查询支持组所选服务项
	 * @Methods Name findServiceItemBySupportGroup
	 * @Create In Jul 5, 2010 By huzh
	 * @return 
	 * @Return String
	 */
	public String findServiceItemBySupportGroup(){
		HttpServletRequest request =super.getRequest();
		HttpServletResponse response =super.getResponse();
		try {
			String supportGroupId = HttpUtil.getString(request, "supportGroupId","0");
			String official = HttpUtil.getString(request, "official","1");
			List<ServiceItem> siList=supportGroupService.findServiceItemsBySupportGroupId(Long.valueOf(supportGroupId),Integer.valueOf(official));
			StringBuilder json = new StringBuilder("{success: true,data:[");
			for (int i = 0; i < siList.size(); i++) {
				ServiceItem si = siList.get(i);
				if (i != 0)
					json.append(",");
				json.append("{");
				json.append("id:'" + si.getId() + "',");
				json.append("name:'" + si.getName());
				json.append("'}");
			}
			json.append("]");
			json.append("}");
			super.getResponse().setContentType("text/plain");
			super.getResponse().setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	/**
	 * 判断当前登录人是不是改组组长，让后转到不同页面
	 * @Methods Name toModifyorShow
	 * @Create In Jul 5, 2010 By huzh
	 * @return
	 * @throws IOException 
	 * @Return String
	 */
	public String toModifyorShow() throws IOException {
		HttpServletRequest request = super.getRequest();
		String supportId = HttpUtil.getString(request, "dataId","0");
		super.getRequest().setAttribute("dataId", supportId);
		GrantedAuthority[] authorities=UserContext.getAuthorities();
		String adminFlag="no";
		if(authorities!=null){
			for(int i=0;i<authorities.length;i++){
				if(authorities[i].getAuthority().equals("AUTH_SYS_ADMIN")){
					adminFlag="yes";
					break;
				}
			}
		}
		if(adminFlag.equals("yes")){
			return "toModifyPage";
		}
		UserInfo userInfo=UserContext.getUserInfo();
		SupportGroup group=supportGroupService.findSupportGroupByLeaderAndSupportId(Long.valueOf(supportId),userInfo.getId());
		if (group!=null) {
			return "toModifyPage";
		} else {
			return "toShowPage";
		}
	}
	/**
	 * 支持组维护中用于验证当前登录人是否为管理员
	 * @Methods Name confirmUserInfo
	 * @Create In Jul 7, 2010 By huzh
	 * @return
	 * @throws IOException 
	 * @Return String
	 */
	public String confirmUserInfo() throws IOException {
		HttpServletResponse response =super.getResponse();
		GrantedAuthority[] authorities=UserContext.getAuthorities();
		String adminFlag="no";
		String json="{success:true,isAdmin:'no'}";
		if(authorities!=null){
			for(int i=0;i<authorities.length;i++){
				if(authorities[i].getAuthority().equals("AUTH_SYS_ADMIN")){
					adminFlag="yes";
					break;
				}
			}
		}
		if(adminFlag.equals("yes")){
			json="{success:true,isAdmin:'yes'}";
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
	}
	/**
	 * 新建支持组时查询所有的支持组工程师
	 * @Methods Name findAllEngineers
	 * @Create In Jul 8, 2010 By huzh
	 * @return 
	 * @Return String
	 */
	 public String findAllServiceEngineers(){
	    	try {
				int start = HttpUtil.getInt(this.getRequest(), "start", 0);
				int pageSize = HttpUtil.getInt(this.getRequest(), "pageSize", 10);
				int pageNo = start / pageSize + 1;
				String fuzzyQueryString = this.getRequest().getParameter("fuzzyQuery");
				String deliveryTeam=getRequest().getParameter("deliveryTeamId");
				String useName=getRequest().getParameter("userInfo.userName");
	    		Long deliveryTeamId=null;
	    		if(deliveryTeam!=null&&"".equals(deliveryTeam.trim())==false){
	    			deliveryTeamId=Long.valueOf(deliveryTeam);
	    		}
				List<String> fuzzyQuery=new ArrayList<String>();
				if(fuzzyQueryString!=null){
					String[] fuzzyQueryArray=fuzzyQueryString.split(";");
					fuzzyQuery=Arrays.asList(fuzzyQueryArray);
				}
				Long total =1L;
				Map requestParams=new HashMap();
				requestParams.put("userInfo.userName", useName);
				Page page = configItemService.findServiceEngineer(deliveryTeamId, requestParams, fuzzyQuery, pageNo, pageSize);
				List queryList = page.list();
				if(queryList!=null&&queryList.size()>=1){
						total =page.getTotalCount();
				}
				StringBuilder json = new StringBuilder("{success: true,rowCount:" + total+",data:[");
				for (int i = 0; i < queryList.size(); i++) {
					ServiceEngineer serviceEngineer = (ServiceEngineer) queryList.get(i);
					if (i != 0)
						json.append(",");
					json.append("{");
					json.append("id:'" + serviceEngineer.getUserInfo().getId() + "',");
					json.append("userInfo:'" + serviceEngineer.getUserInfo().getRealName() + "/" + serviceEngineer.getUserInfo().getUserName() + "/"
							+ serviceEngineer.getUserInfo().getDepartment().getDepartName() + "'");
					json.append("}");
				}
				json.append("]");
				json.append("}");	
				getResponse().setContentType("text/plain;charset=gbk");
				PrintWriter out = getResponse().getWriter();
				out.write(json.toString());
				out.flush();
				out.close();
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				throw new ApplicationException();
			}
		}
}
