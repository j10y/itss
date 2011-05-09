package com.zsgj.itil.knowledge.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.GrantedAuthority;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.pagemodel.PageManager;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelColumn;
import com.zsgj.info.appframework.pagemodel.service.PageModelService;
import com.zsgj.info.appframework.pagemodel.service.PagePanelService;
import com.zsgj.info.appframework.pagemodel.servlet.CoderForSave;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.ApplicationException;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.itil.event.entity.Event;
import com.zsgj.itil.event.entity.EventSulotion;
import com.zsgj.itil.event.entity.EventType;
import com.zsgj.itil.knowledge.entity.KnowContract;
import com.zsgj.itil.knowledge.entity.KnowFile;
import com.zsgj.itil.knowledge.entity.KnowProblemType;
import com.zsgj.itil.knowledge.entity.Knowledge;
import com.zsgj.itil.knowledge.entity.KnowledgeType;
import com.zsgj.itil.knowledge.service.KnowledgeService;
import com.zsgj.itil.service.entity.ServiceItemType;

public class KnowledgeAction extends BaseAction {
	static final String FSP = System.getProperty("file.separator");
	static final String LSP = System.getProperty("line.separator");
	private MetaDataManager metaDataManager = (MetaDataManager) ContextHolder.getBean("metaDataManager");
	private PageManager pageManager = (PageManager) ContextHolder.getBean("pageManager");
	private PagePanelService pagePanelService = (PagePanelService)ContextHolder.getBean("pagePanelService");
	private Service service = (Service) ContextHolder.getBean("baseService");
	private KnowledgeService knowService = (KnowledgeService) getBean("KnowledgeService");

	/**
	 * 获取知识类型
	 * 
	 * @Methods Name findKnowledgeTypes
	 * @Create In Apr 1, 2009 By daijf
	 * @return
	 * @throws IOException
	 *             String
	 */
	public String findKnowledgeTypes() throws IOException {
		HttpServletResponse response = super.getResponse();
		String json = "";
		List<KnowledgeType> list = super.getService().findAll(KnowledgeType.class);
		Iterator iter = list.iterator();
		int i = 1;
		json = "{total:" + list.size();
		json += ",data:[";
		System.out.println(list.size());
		while (iter.hasNext()) {
			KnowledgeType c = (KnowledgeType) iter.next();
			json += "{name:\"" + c.getName() + "\",";
			if (i < list.size()) {
				json += "id:\"" + c.getId() + "\"},";
			} else {
				json += "id:\"" + c.getId() + "\"}";
			}
			i++;
		}
		json += "]}";
		System.out.println(json);
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 根据知识类型ID找相关数据
	 * 
	 * @Methods Name getKnowledgeTypeData
	 * @Create In Apr 1, 2009 By daijf
	 * @return
	 * @throws IOException
	 *             String
	 */
	public String getKnowledgeTypeData() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String KnowledgeTypeId = request.getParameter("KnowledgeTypeId");
		Class clazz = KnowledgeType.class;
		KnowledgeType knowledge = (KnowledgeType) service.find(clazz, KnowledgeTypeId);
		String pagePanelName = knowledge.getPagePanel().getName();
		String queryPanelName = knowledge.getPageQueryPanel().getName();
		String knowName = knowledge.getName();
		String pageListName = knowledge.getPageListPanel().getName();
		String MainTableName = knowledge.getSystemMainTable().getTableName();
		String className = knowledge.getClassName();
		String result = "{success:true,pagePanelName:'" + pagePanelName + "',pageListName:'" + pageListName
				+ "',MainTableName:'" + MainTableName + "',knowName:'" + knowName + "',className:'" + className
				+ "',queryPanelName:'" + queryPanelName + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		response.getWriter().write(result);
		writer.flush();
		return null;
	}

	/**
	 * 保存知识类型各个实体草稿
	 * 
	 * @Methods Name saveKnowledgeDraft
	 * @Create In Apr 9, 2009 By daijf
	 * @return
	 * @throws IOException
	 *             String
	 */
//	@SuppressWarnings("unchecked")
//	public String saveKnowledgeDraft() throws IOException {
//		HttpServletResponse response = super.getResponse();
//		HttpServletRequest request = super.getRequest();
//		String KnowledgeTypeID = request.getParameter("KnowledgeTypeID");
//		String info = request.getParameter("panelTypeParam");//各个参数
//		String resolvent = request.getParameter("resolvent");//解决方案
//		String knowLedgeFlag = request.getParameter("knowLedgeFlag");//"know" or "" 用于区分是否为解决方案
//		Class clazz = KnowledgeType.class;
//		KnowledgeType knowledge = (KnowledgeType) service.find(clazz, KnowledgeTypeID);//知识类型
//		String clazzInstance = knowledge.getClassName();
//		Class clazzExample = getClass(clazzInstance);//
//		HashMap myinfoMap = new HashMap();
//		JSONObject jo = JSONObject.fromObject(info);
//		Iterator itInfo = jo.keys();
//		while (itInfo.hasNext()) {
//			String key = (String) itInfo.next();
//			String[] keyString = key.split("\\$");
//			String value = jo.getString(key);
//			myinfoMap.put(keyString[1], value);
//		}
//		myinfoMap.put("status", 0);
//		if ("know".equals(knowLedgeFlag)) {//是解决方案类型
//			// resolvent = resolvent.substring(1, resolvent.length() - 1);
//			myinfoMap.put("resolvent", resolvent);
//		}
//		myinfoMap.put("createUser", UserContext.getUserInfo());
//		myinfoMap.put("createDate", new Date());
//		Object obj = metaDataManager.saveEntityData(clazzExample, myinfoMap);
//		BaseObject baseObject = (BaseObject) obj;
//		Long templeteId = baseObject.getId();//存入数据库后的id
//		String json = "{success:true,templeteId:'" + templeteId + "'}";
//		PrintWriter writer = response.getWriter();
//		response.setContentType("text/plain");
//		response.setCharacterEncoding("utf-8");
//		response.getWriter().write(json);
//		writer.flush();
//		return null;
//	}

	/**
	 * 保存知识类型各个实体
	 * 
	 * @Methods Name saveKnowledgeEntity
	 * @Create In Apr 9, 2009 By daijf
	 * @return
	 * @throws IOException
	 *             String
	 */
	@SuppressWarnings("unchecked")
	public String saveKnowledgeEntity() throws IOException {
		HttpServletResponse response = super.getResponse();
		HttpServletRequest request = super.getRequest();
		String knowledgeId = request.getParameter("knowledgeId");// 知识（文件id、解决方案id、合同id）
		String KnowledgeTypeID = request.getParameter("KnowledgeTypeID");// 知识类型
		String info = request.getParameter("panelTypeParam");
		String resolvent = request.getParameter("resolvent");
		if (KnowledgeTypeID == null || KnowledgeTypeID.equals("")) {
			HashMap myinfoMap = new HashMap();
			JSONObject jo = JSONObject.fromObject(info);
			Iterator itInfo = jo.keys();
			while (itInfo.hasNext()) {
				String key = (String) itInfo.next();
				String[] keyString = key.split("\\$");
				String value = jo.getString(key);
				myinfoMap.put(keyString[1], value);
			}
			
			myinfoMap.put("id", knowledgeId);
			myinfoMap.put("status", 0);
			myinfoMap.put("resolvent", resolvent);
			Knowledge knowledge = (Knowledge) metaDataManager.saveEntityData(Knowledge.class, myinfoMap);
			Long knowledgeIDd = knowledge.getId();
			PagePanel pagePanel = (PagePanel) getService().findUnique(PagePanel.class, "name",
					"KnowLedgeSolution_pagepanel");
			KnowledgeType knowledget = (KnowledgeType) getService().findUnique(KnowledgeType.class, "pagePanel",
					pagePanel);
			String knowledgeTypeDD = knowledget.getId().toString();
			String json = "{success:true,knowledgeIDd:'" + knowledgeIDd + "',knowledgeTypeDD:'" + knowledgeTypeDD
					+ "'}";
			PrintWriter writer = response.getWriter();
			response.setContentType("text/plain");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(json);
			writer.flush();
			return null;
		} else {
			Class clazz = KnowledgeType.class;
			KnowledgeType knowledge = (KnowledgeType) service.find(clazz, KnowledgeTypeID, true);
			String clazzInstance = knowledge.getClassName();
			Class clazzExample = getClass(clazzInstance);// clazzExample
			// ：KnowFile or
			// KnowContract or
			// Knowledge
			HashMap myinfoMap = new HashMap();
			JSONObject jo = JSONObject.fromObject(info);
			Iterator itInfo = jo.keys();
			while (itInfo.hasNext()) {
				String key = (String) itInfo.next();
				String[] keyString = key.split("\\$");
				String value = jo.getString(key);
				myinfoMap.put(keyString[1], value);
			}
			myinfoMap.put("id", knowledgeId);
			myinfoMap.put("status", 0);
			myinfoMap.put("resolvent", resolvent);
			// add by awen for reSave Knowledge\KnowFile\KnowContract in May 6
			// 2009 begin
			Object object = null;
			try {
				object = clazzExample.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			// 修改KnowFile、Knowledge、KnowContract，如有未保存完善字段继续添加
			if (object instanceof Knowledge) {
				Knowledge knowledge2 = (Knowledge) service.find(Knowledge.class, knowledgeId, true);
				myinfoMap.put("createDate", knowledge2.getCreateDate());
				myinfoMap.put("createUser", knowledge2.getCreateUser());
			} else if (object instanceof KnowFile) {
				KnowFile knowFile2 = (KnowFile) service.find(KnowFile.class, knowledgeId, true);
				myinfoMap.put("createDate", knowFile2.getCreateDate());
				myinfoMap.put("createUser", knowFile2.getCreateUser());
			} else if (object instanceof KnowContract) {
				KnowContract knowContract2 = (KnowContract) service.find(KnowContract.class, knowledgeId, true);
				myinfoMap.put("createDate", knowContract2.getCreateDate());
				myinfoMap.put("createUser", knowContract2.getCreateUser());
			}
			// add by awen for reSave Knowledge\KnowFile\KnowContract in May 6
			// 2009 end

			Object obj = metaDataManager.saveEntityData(clazzExample, myinfoMap);// clazzExample
			BaseObject baseObject = (BaseObject) obj;
			Long templeteId = baseObject.getId();
			String json = "{success:true,templeteId:'" + templeteId + "'}";
			PrintWriter writer = response.getWriter();
			response.setContentType("text/plain");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(json);
			writer.flush();
			return null;
		}
	}

	/**
	 * 保存知识类型各个实体
	 * 
	 * @Methods Name saveKnowledgeType
	 * @Create In Apr 1, 2009 By daijf
	 * @return
	 * @throws IOException
	 *             String
	 */
	@SuppressWarnings("unchecked")
	public String saveKnowledgeType() throws IOException {
		HttpServletResponse response = super.getResponse();
		HttpServletRequest request = super.getRequest();
		String KnowledgeTypeID = request.getParameter("KnowledgeTypeID");
		String pagePanelString = request.getParameter("pagePanelString");
		String resolvent = request.getParameter("resolvent");
		String info = request.getParameter("panelTypeParam");
		if (KnowledgeTypeID == null || KnowledgeTypeID.equals("")) {//知识类型为解决方案
			HashMap myinfoMap = new HashMap();
			JSONObject jo = JSONObject.fromObject(info);
			Iterator itInfo = jo.keys();
			while (itInfo.hasNext()) {
				String key = (String) itInfo.next();
				String[] keyString = key.split("\\$");
				String value = jo.getString(key);
				if("Knowledge$createDate".equals(key)){
					myinfoMap.put(keyString[1], java.sql.Timestamp.valueOf(value));//对存入数据库后没有时间的处理
				}else{
				myinfoMap.put(keyString[1], value);
				}
			}
			myinfoMap.put("resolvent", resolvent);
			metaDataManager.saveEntityData(Knowledge.class, myinfoMap);
		} else {//知识类型为文件或合同
			Class clazz = KnowledgeType.class;
			KnowledgeType knowledge = (KnowledgeType) service.find(clazz, KnowledgeTypeID);
			String clazzInstance = knowledge.getClassName();
			Class clazzExample = getClass(clazzInstance);
			HashMap myinfoMap = new HashMap();
			JSONObject jo = JSONObject.fromObject(info);
			Iterator itInfo = jo.keys();
			while (itInfo.hasNext()) {
				String key = (String) itInfo.next();
				String[] keyString = key.split("\\$");
				String value = jo.getString(key);
				myinfoMap.put(keyString[1], value);
				if("KnowFile$createDate".equals(key)||"KnowContract$createDate".equals(key)){
					myinfoMap.put(keyString[1], java.sql.Timestamp.valueOf(value));
				}else{
					myinfoMap.put(keyString[1], value);
				}
			}
			myinfoMap.put("resolvent", resolvent);
			metaDataManager.saveEntityData(clazzExample, myinfoMap);
		}
		String json = "{success:true}";
		PrintWriter writer = response.getWriter();
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json);
		writer.flush();
		return null;
	}

	@SuppressWarnings("unchecked")
	private Class getClass(String className) {
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return clazz;
	}

	/**
	 * 保存知识问题类型
	 * 
	 * @Methods Name saveProblemType
	 * @Create In Apr 7, 2009 By daijf
	 * @return
	 * @throws IOException
	 *             String
	 */
	@SuppressWarnings("unchecked")
	public String saveProblemType() throws IOException {
//		HttpServletResponse response = super.getResponse();
		HttpServletRequest request = super.getRequest();
		String info = request.getParameter("problemTypeparam");
//		String serviceItem = request.getParameter("siId");
		HashMap myinfoMap = new HashMap();
		JSONObject jo = JSONObject.fromObject(info);
		Iterator itInfo = jo.keys();
		while (itInfo.hasNext()) {
			String key = (String) itInfo.next();
			String[] keyString = key.split("\\$");
			String value = jo.getString(key);
			myinfoMap.put(keyString[1], value);
		}
		myinfoMap.put("deleteFalg", KnowProblemType.DELETE_FALSE);
//		//检验是否具有管理员权限
//		GrantedAuthority[] authorities=UserContext.getAuthorities();
//		String json="";
//		boolean isAdmin=false;
//			for(int i=0;i<authorities.length;i++){
//				if(authorities[i].getAuthority().equals("AUTH_SYS_ADMIN")){
//					isAdmin=true;
//					break;
//				}
//			}
//		if(isAdmin==true){//为管理员
//			json = "{success:true,flag:'yes'}";
//			metaDataManager.saveEntityData(KnowProblemType.class, myinfoMap);
//		}else{
//			UserInfo userInfo=UserContext.getUserInfo();
//			//检验当前登录人是否为服务项所在支持组的组长
//			String flag=knowService.confirmServiceItemInSupportGroup(Long.valueOf(serviceItem),userInfo);
//			if("no".equals(flag)){
//				 json = "{success:true,flag:'no'}";
//			}else{
//				 json = "{success:true,flag:'yes'}";
				 metaDataManager.saveEntityData(KnowProblemType.class, myinfoMap);
//			}
//		}
//		PrintWriter writer = response.getWriter();
//		response.setContentType("text/plain");
//		response.setCharacterEncoding("utf-8");
//		response.getWriter().write(json);
//		writer.flush();
		return null;
	}
	public String confirmUserInfo() throws IOException {
		HttpServletResponse response = super.getResponse();
		HttpServletRequest request = super.getRequest();
		String problmeTypeId = request.getParameter("problmeTypeId");
		GrantedAuthority[] authorities=UserContext.getAuthorities();
		String json="";
		boolean isAdmin=false;
			for(int i=0;i<authorities.length;i++){
				if(authorities[i].getAuthority().equals("AUTH_SYS_ADMIN")){
					isAdmin=true;
					break;
				}
			}
		if(isAdmin==true){//为管理员
			json = "{success:true,flag:'yes'}";
		}else{
			UserInfo userInfo=UserContext.getUserInfo();
			KnowProblemType knowProblemType=(KnowProblemType) this.service.findUnique(KnowProblemType.class, "id", Long.valueOf(problmeTypeId));
			//检验当前登录人是否为服务项所在支持组的组长
			String flag=knowService.confirmServiceItemInSupportGroup(knowProblemType.getServiceItem().getId(),userInfo);
			if("no".equals(flag)){
				 json = "{success:true,flag:'no'}";
			}else{
				 json = "{success:true,flag:'yes'}";
			}
		}
		PrintWriter writer = response.getWriter();
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json);
		writer.flush();
		return null;
	}
	/**
	 * 根据面板查询解决方案Id
	 * 
	 * @Methods Name queryKnowledgetId
	 * @Create In Apr 10, 2009 By daijf
	 * @return
	 * @throws IOException
	 *             String
	 */
	@SuppressWarnings("unchecked")
	public String queryKnowledgetId() throws IOException {
		HttpServletResponse response = super.getResponse();
		HttpServletRequest request = super.getRequest();
		String panel = request.getParameter("panel");
		PagePanel pagePanel = (PagePanel) getService().findUnique(PagePanel.class, "name", panel);
		KnowledgeType knowledge = (KnowledgeType) getService().findUnique(KnowledgeType.class, "pagePanel", pagePanel);
		String knowledgeId = knowledge.getId().toString();
		String json = "{success:true,knowledgeId:'" + knowledgeId + "'}";
		PrintWriter writer = response.getWriter();
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json);
		writer.flush();
		return null;
	}
	/**
	 * 查询知识审批历史表中关于某个审批知识（文件或解决方案或合同）的最新processId
	 * 
	 * @Methods Name findProcessIdOfLatestProcess
	 * @Create In May 6, 2009 By awen
	 * @return
	 * @throws IOException
	 *             String
	 */
	public String findProcessIdOfLatestProcess() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		Long kType = Long.parseLong(request.getParameter("kType"));
		Long kId = Long.parseLong(request.getParameter("kId"));
		// ktype值含义： 1，文件类型 2，解决方案类型 3，合同类型
		Long processId = knowService.findProcessIdOfLatestProcess(kId, kType);
		String json = "{success : true, processId : '" + String.valueOf(processId) + "'}";
		PrintWriter writer = response.getWriter();
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json);
		writer.flush();
		return null;
	}

	/**
	 * 使解决方案的使用次数加一，如果解决方案是草稿状态，准备启动解决方案审批工作流的数据
	 * 
	 * @Methods Name prepareForKnowledgeAuditWorkflow
	 * @Create In Oct 21, 2009 By duxh
	 * @return String
	 * @throws IOException
	 */
	public String prepareForKnowledgeAuditWorkflow() throws IOException {
		String json = "";
		Event event = new Event();
		event.setId(Long.parseLong(getRequest().getParameter("eventId")));
		EventSulotion eventSulotion = (EventSulotion) service.findUnique(EventSulotion.class, "event", event);
		Knowledge knowledge = (Knowledge) service
				.find(Knowledge.class, eventSulotion.getKnowledge().getId().toString());
		knowService.modifySolutionUseTime(knowledge.getId());
		if (knowledge.getStatus().compareTo(Knowledge.STATUS_DRAFT)==0) {//2010-05-18 modified by huzh 
			KnowledgeType knowledgeType = (KnowledgeType) getService().findUnique(KnowledgeType.class, "className",
					"com.zsgj.itil.knowledge.entity.Knowledge");
			String knowledgeTypeId = knowledgeType.getId().toString();
			json = "{success:true,createUser:'" + knowledge.getCreateUser().getId() + "',knowledgeId:'"
					+ eventSulotion.getKnowledge().getId() + "',knowledgeTypeId:'" + knowledgeTypeId + "'}";
		} else {
			json = "{success:true}";
		}
		super.getResponse().setContentType("text/plain");
		PrintWriter writer = getResponse().getWriter();
		writer.print(json);
		writer.flush();
		writer.close();
		return null;
	}

	/**
	 * 使用解决方案。
	 * 
	 * @Methods Name useSolution
	 * @Create In Oct 26, 2009 By duxh
	 * @return String
	 * @throws IOException
	 */
	public String useSolution() throws IOException {
		try {
			//该解决方案的使用次数加1
			knowService.modifySolutionUseTime(Long.parseLong(getRequest().getParameter("SolutionID")));
			HttpServletResponse response = getResponse();
			response.setContentType("text/plain;charset=gbk");
			PrintWriter out = response.getWriter();
			out.print("{success:true}");
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return null;
	}

	/**
	 * 通过事件Id查询解决方案
	 * 
	 * @Methods Name findKnowLedgeByEventId
	 * @Create In Oct 29, 2009 By duxh
	 * @return String
	 */
	public String findKnowledgeByEventId() throws IOException {
		try {
			Knowledge knowledge = knowService.findKnowledgeByEventId(Long.parseLong(getRequest()
					.getParameter("eventId")));
			String json = "";
			if (knowledge == null) {
				json = "{success : true,knowledgeId : 'noknowledge'}";
			} else {
				json = "{success : true,knowledgeId : '" + knowledge.getId() + "'}";
			}
			HttpServletResponse response = getResponse();
			response.setContentType("text/plain;charset=gbk");
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}

	/**
	 * 查找知识问题类型。
	 * 
	 * @Methods Name findProblemType
	 * @Create In Nov 2, 2009 By duxh
	 * @return String
	 */
	public String findKnowProblemType() throws IOException {
		String serviceItem = super.getRequest().getParameter("serviceItem");
		try {
			int pageSize = 10;
			int start = HttpUtil.getInt(super.getRequest(), "start", 0);
			int pageNo = start / pageSize + 1;
			String orderBy = HttpUtil.getString(super.getRequest(), "orderBy", "id");
			boolean isAsc = HttpUtil.getBoolean(super.getRequest(), "isAsc", true);
			Map requestParams = HttpUtil.requestParam2Map(super.getRequest());
			if (serviceItem.length() == 0) {
				requestParams.remove("serviceItem");
			}
			Page page = metaDataManager.query(KnowProblemType.class, requestParams, pageNo, pageSize, orderBy, isAsc);
			Long total = page.getTotalCount();
			List queryList = page.list();
			List<Map<String, Object>> listData = metaDataManager.getEntityMapDataForList(KnowProblemType.class,
					queryList);
			StringBuilder json = new StringBuilder("{success: true, rowCount:" + total + ",data:[");
			for (int i = 0; i < listData.size(); i++) {
				if (i != 0)
					json.append(",");
				json.append("{");
				json.append("id:'" + listData.get(i).get("id") + "',");
				json.append("name:'" + listData.get(i).get("name") + "'");
				json.append("}");
			}
			json.append("]");
			json.append("}");
			HttpServletResponse response = getResponse();
			response.setContentType("text/plain;charset=gbk");
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.flush();
			out.close();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	
	/**
	 * 获取知识部分面板（实现计数器功能）
	 * @Methods Name getKnowledgePanelData
	 * @Create In Mar 15, 2010 By lee
	 * @return String
	 */
	public String getKnowledgePanelData(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		
		String json = "";
		String id = request.getParameter("id");
		String panelName = request.getParameter("panelname");
		PagePanel panel=pagePanelService.findPagePanel(panelName);
		SystemMainTable smt= panel.getSystemMainTable();
		String tableName = smt.getTableName();
		String className = smt.getClassName();
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		List<PagePanelColumn> pagePanelColumns=pageManager.getUserPagePanelColumn(panelName);

		Map<String, Object> dataMap = null;
		if (StringUtils.isNotBlank(id)) {
			if(panel.getXtype().getName().equals("form")){
				Object obj = service.find(clazz, id, true);
				if(service.findUnique(KnowledgeType.class, "className", className)!=null){
					BeanWrapper bw = new BeanWrapperImpl(obj);
					Long readTimes = (Long) bw.getPropertyValue("readTimes");
					if(readTimes==null){
						readTimes = Long.valueOf(1);
					}
					bw.setPropertyValue("readTimes", ++readTimes);
					service.save(obj);
				}
				dataMap = metaDataManager.getEntityDataForEdit(obj,tableName);
				json = CoderForSave.encode(pagePanelColumns, dataMap, true);
					
			}else {
				json = null;
			}
			
		} else {
			dataMap = pageManager.getPagePanelDataForAdd(panelName);
			json = CoderForSave.encode(pagePanelColumns, dataMap , false);	
		}	
		
		response.setContentType("text/plain;charset=gbk");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 通过服务项的id来查找所有的解决方案（新添加的，事件创建页面重新布局时会用到）
	 * @Methods Name findKnowledgeByServiceItem
	 * @Create In Apr 15, 2010 By huzh
	 * @return 
	 * @Return String
	 */
	public String findKnowledgeByServiceItem(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		int start = HttpUtil.getInt(getRequest(), "start",0);
		int pageSize = HttpUtil.getInt(getRequest(), "pageSize",10);
		int pageNo=start/pageSize+1;
		String serviceItemId = request.getParameter("siId");
		//2010-07-16 add by huzh for 添加问题名称多关键字筛选(以空格为分隔符) begin
		String keyword=request.getParameter("summaryKeyWord");
		String summary = (keyword==null?"":keyword.trim());
		String[] summarykeyWord=summary.split(" ");//空格分隔
		Page page=knowService.findKnowledgeBySiId(serviceItemId,summarykeyWord, pageNo, pageSize);
		//2010-07-16 add by huzh for 添加问题名称多关键字筛选(以空格为分隔符) end
		Long total=1L;
		List<Knowledge> list = page.list();	
		if(list!=null&&list.size()!=0){
			total = page.getTotalCount();
		}
		 StringBuilder json = new StringBuilder("{success: true,"+"rowCount:"+total+",data:[");
			if(list!=null&&list.size()!=0){
				for(int i=0;i<list.size();i++){
					Knowledge know=(Knowledge) list.get(i);
					if (i != 0)
						json.append(",");
					json.append("{");
					json.append("id:'" + know.getId()+ "',");
					json.append("summary:'" + know.getSummary()+ "'");
					json.append("}");	
				}
			}
			json.append("]");
			json.append("}");
			response.setContentType("text/plain;charset=gbk");
			try {
				PrintWriter out = response.getWriter();
				out.print(json.toString());
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
	}
	/**
	 * 对解决方案问题类型进行逻辑删除(新添加，但由于解决方案
     *       问题类型维护中去掉了“删除”按钮操作，所以暂时无用)
	 * @Methods Name removeProblemTypes
	 * @Create In Apr 16, 2010 By huzh
	 * @return 
	 * @Return String
	 */
	public String removeProblemTypes() {
			HttpServletResponse response = getResponse();
			HttpServletRequest request = getRequest();
			String params = request.getParameter("problemTypesId");
			JSONArray jsonArray =JSONArray.fromObject(params);
		    Long[] longArray = new Long[jsonArray.size()];   
		        for( int i = 0 ; i<jsonArray.size() ; i++ ){   
		            longArray[i] = Long.parseLong(jsonArray.getString(i));   
		        } 
			knowService.removeProblemType(longArray);
			String json = "{success:true}";
			try {
				PrintWriter writer = response.getWriter();
				response.setContentType("text/plain");
				response.setCharacterEncoding("utf-8");
				response.getWriter().write(json);
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		
	}
	/**
	 * 保存解决方案变更草稿
	 * @Methods Name saveKnowledgeChangDraft
	 * @Create In May 11, 2010 By huzh
	 * @return 
	 * @Return String
	 */
	public String saveKnowledgeChangDraft(){
		HttpServletResponse response = super.getResponse();
		HttpServletRequest request = super.getRequest();
		String resolvent = request.getParameter("resolvent");
		String knowledgeId=request.getParameter("knowledgeId");
		String info = request.getParameter("panelTypeParam");
		HashMap myinfoMap = new HashMap();
		JSONObject jo = JSONObject.fromObject(info);
		Iterator itInfo = jo.keys();
		while (itInfo.hasNext()) {
			String key = (String) itInfo.next();
			String[] keyString = key.split("\\$");
			String value = jo.getString(key);
			myinfoMap.put(keyString[1], value);
		}
			Knowledge oldKnowledge=new Knowledge();
			oldKnowledge.setId(Long.parseLong((String) myinfoMap.get("id")));
			myinfoMap.put("oldKnowledge", oldKnowledge);
			myinfoMap.put("createDate", new Date());	
			myinfoMap.put("createUser", UserContext.getUserInfo());
			myinfoMap.put("status", Knowledge.STATUS_CHANGEDRAFT);//变更草稿
//			myinfoMap.put("readTimes", 0);// 2010-07-16 保留原来的阅读量
//			myinfoMap.put("useTime", 0);// 2010-07-16 保留原来的使用次数
			myinfoMap.put("resolvent", resolvent);
//			myinfoMap.remove("knowledgeCisn");// 2010-07-16 保留原来的知识编号
			myinfoMap.remove("id");
			Knowledge knowledge=(Knowledge) metaDataManager.saveEntityData(Knowledge.class, myinfoMap);
			String json="";
			Long dataId = knowledge.getId();
		    json = "{success:true,dataId:'" + dataId + "'}";
		    if(knowledgeId!=null&&"".equals(knowledgeId)==false){
				Knowledge ke=new Knowledge();
				ke.setId(Long.parseLong(knowledgeId));
				List<Knowledge> newKnowList=(List<Knowledge>) service.find(Knowledge.class, "oldKnowledge", ke);
				if(newKnowList!=null&&newKnowList.size()!=0){
					for(Knowledge newKnowledge: newKnowList){
						if(Knowledge.STATUS_APPROVING.equals(newKnowledge.getStatus())||Knowledge.STATUS_FINISHED.equals(newKnowledge.getStatus())){
							json = "{success:false}";
							break;
						}
					}
				}
			}
			try {
				PrintWriter writer = response.getWriter();
				response.setContentType("text/plain");
				response.setCharacterEncoding("utf-8");
				response.getWriter().write(json);
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
	}
	/**
	 * 保存文件变更草稿
	 * @Methods Name saveKnowFileChangDraft
	 * @Create In May 11, 2010 By huzh
	 * @return 
	 * @Return String
	 */
	public String saveKnowFileChangDraft(){
		HttpServletResponse response = super.getResponse();
		HttpServletRequest request = super.getRequest();
		String knowFileId=request.getParameter("knowFileId");
		String info = request.getParameter("panelTypeParam");
		HashMap myinfoMap = new HashMap();
		JSONObject jo = JSONObject.fromObject(info);
		Iterator itInfo = jo.keys();
		while (itInfo.hasNext()) {
			String key = (String) itInfo.next();
			String[] keyString = key.split("\\$");
			String value = jo.getString(key);
			myinfoMap.put(keyString[1], value);
			
		}
		if(KnowFile.STATUS_FINISHED.toString().equals(myinfoMap.get("status"))){//文件变更
			KnowFile oldKnowFile=new KnowFile();
			oldKnowFile.setId(Long.parseLong((String) myinfoMap.get("id")));
			myinfoMap.put("oldKnowFile", oldKnowFile);
			myinfoMap.put("createDate", new Date());
			myinfoMap.put("createUser", UserContext.getUserInfo());
			myinfoMap.put("status", KnowFile.STATUS_CHANGEDRAFT);//变更草稿
//			myinfoMap.put("readTimes", 0);//2010-07-16 保留原来的阅读次数
//			myinfoMap.remove("number");//2010-07-16 保留原来的知识编号
			myinfoMap.remove("id");//去掉Id，新数据
		}
		KnowFile knowFile=(KnowFile) metaDataManager.saveEntityData(KnowFile.class, myinfoMap);
		Long dataId = knowFile.getId();
		String json = "{success:true,dataId:'" + dataId + "'}";
		if(knowFileId!=null&&"".equals(knowFileId)==false){
			KnowFile kf=new KnowFile();
			kf.setId(Long.parseLong(knowFileId));
			List<KnowFile> newKnowList=(List<KnowFile>) service.find(KnowFile.class, "oldKnowFile", kf);
			if(newKnowList!=null&&newKnowList.size()!=0){
			for(KnowFile newKnowFile: newKnowList){
				if(KnowFile.STATUS_APPROVING.equals(newKnowFile.getStatus())||KnowFile.STATUS_FINISHED.equals(newKnowFile.getStatus())){
					json = "{success:false}";
					break;
				}
				}
			}
		}
		try {
			PrintWriter writer = response.getWriter();
			response.setContentType("text/plain");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(json);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 保存合同变更草稿
	 * @Methods Name saveKnowContracChangDraft
	 * @Create In May 11, 2010 By huzh
	 * @return 
	 * @Return String
	 */
	public String saveKnowContractChangDraft(){
		HttpServletResponse response = super.getResponse();
		HttpServletRequest request = super.getRequest();
		String knowContractId=request.getParameter("knowContractId");
		String info = request.getParameter("panelTypeParam");
		HashMap myinfoMap = new HashMap();
		JSONObject jo = JSONObject.fromObject(info);
		Iterator itInfo = jo.keys();
		while (itInfo.hasNext()) {
			String key = (String) itInfo.next();
			String[] keyString = key.split("\\$");
			String value = jo.getString(key);
			KnowContract kc=new KnowContract();
			myinfoMap.put(keyString[1], value);
		}
		if(KnowContract.STATUS_FINISHED.toString().equals(myinfoMap.get("status"))){//合同变更
			KnowContract oldKnowContract=new KnowContract();
			oldKnowContract.setId(Long.parseLong((String) myinfoMap.get("id")));
			myinfoMap.put("oldKnowContract", oldKnowContract);
			myinfoMap.put("createDate", new Date());
			myinfoMap.put("createUser", UserContext.getUserInfo());
			myinfoMap.put("status", KnowContract.STATUS_CHANGEDRAFT);//变更草稿
//			myinfoMap.put("readTimes", 0);//2010-07-16 保留原来的阅读次数
//			myinfoMap.remove("number");//2010-07-16 保留原来的知识编号
			myinfoMap.remove("id");
		}
		KnowContract knowContract=(KnowContract) metaDataManager.saveEntityData(KnowContract.class, myinfoMap);
		Long dataId = knowContract.getId();
		String json = "{success:true,dataId:'" + dataId + "'}";
		if(knowContractId!=null&&"".equals(knowContractId)==false){
			KnowContract kc=new KnowContract();
			kc.setId(Long.parseLong(knowContractId));
			List<KnowContract> newKnowList=(List<KnowContract>) service.find(KnowContract.class, "oldKnowContract", kc);
			if(newKnowList!=null&&newKnowList.size()!=0){
			for(KnowContract newKnowContract: newKnowList){
				if(KnowContract.STATUS_APPROVING.equals(newKnowContract.getStatus())||KnowContract.STATUS_FINISHED.equals(newKnowContract.getStatus())){
					json = "{success:false}";
					break;
				}
			}
			}
		}
		try {
			PrintWriter writer = response.getWriter();
			response.setContentType("text/plain");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(json);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 保存解决方案草稿（包括知识变更维护和知识维护）
	 * @Methods Name saveKnowledgeEntityDraft
	 * @Create In May 13, 2010 By huzh
	 * @return 
	 * @Return String
	 */
	public String saveKnowledgeEntityDraft(){
		HttpServletResponse response = super.getResponse();
		HttpServletRequest request = super.getRequest();
		String resolvent = request.getParameter("resolvent");
		String knowledgeId=request.getParameter("knowledgeId");
		String info = request.getParameter("knowledgeParam");
		HashMap myinfoMap = new HashMap();
		JSONObject jo = JSONObject.fromObject(info);
		Iterator itInfo = jo.keys();
		while (itInfo.hasNext()) {
			String key = (String) itInfo.next();
			String[] keyString = key.split("\\$");
			String value = jo.getString(key);
			if("Knowledge$createDate".equals(key)){
				myinfoMap.put(keyString[1], java.sql.Timestamp.valueOf(value));
			}else{
			 myinfoMap.put(keyString[1], value);
			}
		}
			myinfoMap.put("resolvent", resolvent);
		Knowledge knowledge=(Knowledge) metaDataManager.saveEntityData(Knowledge.class, myinfoMap);
		String json="";
		Long dataId = knowledge.getId();
	    json = "{success:true,dataId:'" + dataId + "'}";
	    if(knowledgeId!=null&&"".equals(knowledgeId)==false){
	    	Knowledge ke=(Knowledge) service.findUnique(Knowledge.class, "id", Long.parseLong(knowledgeId));
	    	Knowledge oldKe=ke.getOldKnowledge();
	    	if(oldKe!=null){
				List<Knowledge> newKnowList=(List<Knowledge>) service.find(Knowledge.class, "oldKnowledge", oldKe);
				if(newKnowList!=null&&newKnowList.size()!=0){
				for(Knowledge newKnowledge: newKnowList){
					if(Knowledge.STATUS_APPROVING.equals(newKnowledge.getStatus())||Knowledge.STATUS_FINISHED.equals(newKnowledge.getStatus())){
						json = "{success:false}";
						break;
					}
				}
				}
	    	}
		}
		try {
			PrintWriter writer = response.getWriter();
			response.setContentType("text/plain");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(json);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 保存文件草稿（包括知识变更维护和知识维护）
	 * @Methods Name saveKnowFileEntityDraft
	 * @Create In May 13, 2010 By huzh
	 * @return 
	 * @Return String
	 */
	public String saveKnowFileEntityDraft(){
		HttpServletResponse response = super.getResponse();
		HttpServletRequest request = super.getRequest();
		String knowFileId=request.getParameter("knowFileId");
		String info = request.getParameter("knowFileParam");
		HashMap myinfoMap = new HashMap();
		JSONObject jo = JSONObject.fromObject(info);
		Iterator itInfo = jo.keys();
		while (itInfo.hasNext()) {
			String key = (String) itInfo.next();
			String[] keyString = key.split("\\$");
			String value = jo.getString(key);
			if("KnowFile$createDate".equals(key)){
				myinfoMap.put(keyString[1], java.sql.Timestamp.valueOf(value));
			}else{
			 myinfoMap.put(keyString[1], value);
			}
		}
		KnowFile knowFile=(KnowFile) metaDataManager.saveEntityData(KnowFile.class, myinfoMap);
		String json="";
		Long dataId = knowFile.getId();
	    json = "{success:true,dataId:'" + dataId + "'}";
	    if(knowFileId!=null&&"".equals(knowFileId)==false){
			KnowFile kf=(KnowFile) service.findUnique(KnowFile.class, "id", Long.parseLong(knowFileId));
			KnowFile oldKf=kf.getOldKnowFile();
			if(oldKf!=null){
				List<KnowFile> newKnowList=(List<KnowFile>) service.find(KnowFile.class, "oldKnowFile", oldKf);
				if(newKnowList!=null&&newKnowList.size()!=0){
				for(KnowFile newKnowFile: newKnowList){
					if(KnowFile.STATUS_APPROVING.equals(newKnowFile.getStatus())||KnowFile.STATUS_FINISHED.equals(newKnowFile.getStatus())){
						json = "{success:false}";
						break;
					}
				}
				}
			}
		}
		try {
			PrintWriter writer = response.getWriter();
			response.setContentType("text/plain");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(json);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 保存合同草稿（包括知识变更维护和知识维护）
	 * @Methods Name saveKnowContractEntityDraft
	 * @Create In May 13, 2010 By huzh
	 * @return 
	 * @Return String
	 */
	public String saveKnowContractEntityDraft(){
		HttpServletResponse response = super.getResponse();
		HttpServletRequest request = super.getRequest();
		String knowContractId=request.getParameter("knowContractId");
		String info = request.getParameter("knowContractParam");
		HashMap myinfoMap = new HashMap();
		JSONObject jo = JSONObject.fromObject(info);
		Iterator itInfo = jo.keys();
		while (itInfo.hasNext()) {
			String key = (String) itInfo.next();
			String[] keyString = key.split("\\$");
			String value = jo.getString(key);
			if("KnowContract$createDate".equals(key)){
				myinfoMap.put(keyString[1], java.sql.Timestamp.valueOf(value));
			}else{
			 myinfoMap.put(keyString[1], value);
			}
		}
		KnowContract knowContract=(KnowContract) metaDataManager.saveEntityData(KnowContract.class, myinfoMap);
		String json="";
		Long dataId = knowContract.getId();
	    json = "{success:true,dataId:'" + dataId + "'}";
	    if(knowContractId!=null&&"".equals(knowContractId)==false){
			KnowContract kc=(KnowContract) service.findUnique(KnowContract.class, "id", Long.parseLong(knowContractId));
			KnowContract oldKc=kc.getOldKnowContract();
			if(oldKc!=null){
				List<KnowContract> newKnowList=(List<KnowContract>) service.find(KnowContract.class, "oldKnowContract", oldKc);
				if(newKnowList!=null&&newKnowList.size()!=0){
				for(KnowContract newKnowContract: newKnowList){
					if(KnowContract.STATUS_APPROVING.equals(newKnowContract.getStatus())||KnowContract.STATUS_FINISHED.equals(newKnowContract.getStatus())){
						json = "{success:false}";
						break;
					}
				}
				}
			}
		}
		try {
			PrintWriter writer = response.getWriter();
			response.setContentType("text/plain");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(json);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 根据事件类型查找解决方案
	 * @Methods Name findKnowledgeByEventType
	 * @Create In May 19, 2010 By huzh
	 * @return 
	 * @Return String
	 */
	public String findKnowledgeByEventType(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		int start = HttpUtil.getInt(getRequest(), "start",0);
		int pageSize = HttpUtil.getInt(getRequest(), "pageSize",10);
		String eventtypeId = request.getParameter("eventtypeId");
		int pageNo = start / pageSize + 1;
		Long total=1L;
		Page page=knowService.findKnowledgeByEventTypeId(eventtypeId,pageNo,pageSize);
		List<Knowledge> knowlist = page.list();	
		if(knowlist!=null&&knowlist.size()!=0){
			total = page.getTotalCount();
		}
		 StringBuilder json = new StringBuilder("{success: true,"+"rowCount:"+total+",data:[");
		if(knowlist!=null&&knowlist.size()!=0){
			for(int i=0;i<knowlist.size();i++){
				if (i != 0)
					json.append(",");
				json.append("{");
				json.append("id:'" + knowlist.get(i).getId() + "',");
				json.append("summary:'" + knowlist.get(i).getSummary()+ "'");
				json.append("}");	
			}
		}
		json.append("]");
		json.append("}");
		response.setContentType("text/plain;charset=gbk");
		try {
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 创建解决方案（知识维护）
	 * @Methods Name saveKnowledgeDraft
	 * @Create In May 25, 2010 By huzh
	 * @return 
	 * @Return String
	 */
	public String createKnowledge(){
		HttpServletResponse response = super.getResponse();
		HttpServletRequest request = super.getRequest();
		String info = request.getParameter("knowledgeParam");
		Map myinfoMap= this.getMapFormPanelJson(info);
		myinfoMap.put("createDate", new Date());
		myinfoMap.put("createUser", UserContext.getUserInfo());
		myinfoMap.put("status", Knowledge.STATUS_DRAFT);
		Knowledge knowledge=(Knowledge) metaDataManager.saveEntityData(Knowledge.class, myinfoMap);
		String json="";
		Long dataId = knowledge.getId();
	    json = "{success:true,dataId:'" + dataId + "'}";
		try {
			PrintWriter writer = response.getWriter();
			response.setContentType("text/plain");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(json);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 创建文件（知识维护）
	 * @Methods Name createKnowFile
	 * @Create In May 25, 2010 By huzh
	 * @return 
	 * @Return String
	 */
	public String createKnowFile(){
		HttpServletResponse response = super.getResponse();
		HttpServletRequest request = super.getRequest();
		String info = request.getParameter("knowFileParam");
		Map myinfoMap= this.getMapFormPanelJson(info);
		myinfoMap.put("createDate", new Date());
		myinfoMap.put("createUser", UserContext.getUserInfo());
		myinfoMap.put("status", KnowFile.STATUS_DRAFT);
		KnowFile knowFile=(KnowFile) metaDataManager.saveEntityData(KnowFile.class, myinfoMap);
		String json="";
		Long dataId = knowFile.getId();
	    json = "{success:true,dataId:'" + dataId + "'}";
		try {
			PrintWriter writer = response.getWriter();
			response.setContentType("text/plain");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(json);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 创建合同（知识维护）
	 * @Methods Name createKnowContract
	 * @Create In May 25, 2010 By huzh
	 * @return 
	 * @Return String
	 */
	public String createKnowContract(){
		HttpServletResponse response = super.getResponse();
		HttpServletRequest request = super.getRequest();
		String info = request.getParameter("knowContractParam");
		Map myinfoMap= this.getMapFormPanelJson(info);
		myinfoMap.put("createDate", new Date());
		myinfoMap.put("createUser", UserContext.getUserInfo());
		myinfoMap.put("status", KnowContract.STATUS_DRAFT);
		KnowContract knowContract=(KnowContract) metaDataManager.saveEntityData(KnowContract.class, myinfoMap);
		String json="";
		Long dataId = knowContract.getId();
	    json = "{success:true,dataId:'" + dataId + "'}";
		try {
			PrintWriter writer = response.getWriter();
			response.setContentType("text/plain");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(json);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 在知识审批阶段保存解决方案修改
	 * @Methods Name saveKnowledgeInApproval
	 * @Create In Jul 8, 2010 By huzh
	 * @return 
	 * @Return String
	 */
	public String saveKnowledgeInApproval(){
		HttpServletResponse response = super.getResponse();
		HttpServletRequest request = super.getRequest();
		String info = request.getParameter("knowledgeParam");
		String resolvent = request.getParameter("resolvent");
		Map myinfoMap= this.getMapFormPanelJson(info);
		myinfoMap.put("resolvent", resolvent);
		Knowledge knowledge=(Knowledge) metaDataManager.saveEntityData(Knowledge.class, myinfoMap);
	    String json = "{success:true}";
		try {
			PrintWriter writer = response.getWriter();
			response.setContentType("text/plain");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(json);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String saveKnowFileInApproval(){
		HttpServletResponse response = super.getResponse();
		HttpServletRequest request = super.getRequest();
		String info = request.getParameter("knowFileParam");
		String descn = request.getParameter("descn");
		Map myinfoMap= this.getMapFormPanelJson(info);
		myinfoMap.put("descn", descn);
		KnowFile knowFile=(KnowFile) metaDataManager.saveEntityData(KnowFile.class, myinfoMap);
		String json = "{success:true}";
		try {
			PrintWriter writer = response.getWriter();
			response.setContentType("text/plain");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(json);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String saveKnowContractInApproval(){
		HttpServletResponse response = super.getResponse();
		HttpServletRequest request = super.getRequest();
		String info = request.getParameter("knowContractParam");
		Map myinfoMap= this.getMapFormPanelJson(info);
		KnowContract knowContract=(KnowContract) metaDataManager.saveEntityData(KnowContract.class, myinfoMap);
		String json = "{success:true}";
		try {
			PrintWriter writer = response.getWriter();
			response.setContentType("text/plain");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(json);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Map getMapFormPanelJson(String param){
		JSONObject basicJson=JSONObject.fromObject(param);
		Set<String> basicSet=basicJson.keySet();
		Map map = new HashMap();
		for (String key:basicSet) {
			String keyString = StringUtils.substringAfter(key, "$");
			String value = basicJson.getString(key);
			map.put(keyString, value);
		}
		return map;
	}
	/**
	 * 根据当前登录人查询问题类型
	 * @Methods Name findAllProblemType
	 * @Create In Jul 15, 2010 By huzh
	 * @return 
	 * @Return String
	 */
	public String findAllProblemType(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String name =HttpUtil.getString(getRequest(), "name","");
		String serviceItem = request.getParameter("serviceItemId");
		int pageSize = HttpUtil.getInt(request, "pageSize", 10);
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo = start / pageSize + 1;
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
		Page page =knowService.findAllProblemType(UserContext.getUserInfo(),adminFlag,name,serviceItem,pageNo,pageSize);
		Long total=1L;
		List typeList=page.list();
		if(typeList!=null&&typeList.size()!=0){
			total=page.getTotalCount();
		}
		StringBuilder json = new StringBuilder("{success: true,rowCount:"+total+",data:[");
		if(typeList!=null&&typeList.size()!=0){
			for(int i=0;i<typeList.size();i++){
				KnowProblemType problemType=(KnowProblemType) typeList.get(i);
				if (i != 0)
					json.append(",");
				json.append("{");
				json.append("id:'" + problemType.getId() + "',");
				json.append("name:'" + problemType.getName()+ "'");
				json.append("serviceItemName:'" + problemType.getServiceItem().getName()+ "'");
				if(problemType.getDeleteFlag().equals(0)){
					json.append("deleteFlag:'否'");
				}else{
					json.append("deleteFlag:'是'");
				}
				json.append("}");	
			}
		}
		json.append("]");
		json.append("}");
		response.setContentType("text/plain;charset=gbk");
		try {
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 变更解决方案的使用次数
	 * @Methods Name modifyKnowledgeUseTimes
	 * @Create In Jul 16, 2010 By huzh
	 * @return 
	 * @Return String
	 */
	public String modifyKnowledgeUseTimes(){
		HttpServletRequest request = super.getRequest();
		String knowledge = request.getParameter("knowledgeId");
		Long knowledgeId=0L;
		if(knowledge!=null&&"".equals(knowledge.trim())==false){
			knowledgeId=Long.valueOf(knowledge);
		}
		Knowledge know=(Knowledge) this.service.findUnique(Knowledge.class, "id", knowledgeId);
		if(know!=null){
			long newUseTimes=know.getUseTime()+1;
			know.setUseTime(newUseTimes);
			this.service.save(know);
		}
		return null;
	}
}
