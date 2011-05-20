package com.zsgj.info.framework.workflow.web.adapter.struts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;
import com.zsgj.info.framework.workflow.ConfigUnitService;
import com.zsgj.info.framework.workflow.TaskService;

/** 
 * @author 杨涛 E-mail: yangtao@info.com
 * @version 创建时间：Mar 26, 2009 12:04:04 PM 
 * 类说明 
 */

public class AssginAndAddMarkAction extends BaseDispatchAction {
//	private MetaDataManager metaDataManager = (MetaDataManager) ContextHolder
//			.getBean("metaDataManager");
	private Service service = (Service) ContextHolder.getBean("baseService");
//	private ProcessService processService = (ProcessService) ContextHolder.getBean("processService");
	
//	private ConfigUnitService configUnitService = (ConfigUnitService)ContextHolder.getBean("configUnitService");
//	private DefinitionService ds = (DefinitionService) ContextHolder.getBean("definitionService");
	private TaskService tm = (TaskService) ContextHolder.getBean("taskService");
	private ConfigUnitService cs = (ConfigUnitService)ContextHolder.getBean("configUnitService");
	/**
	 * 得到某个流程的所有节点信息
	 * @Methods Name getNodeStore
	 * @Create In Mar 26, 2009 By Administrator
	 * @return
	 * @throws Exception String
	 */
	public ActionForward getNodeStore(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String strTaskId=request.getParameter("taskId");
		Long taskId=null;
		if (strTaskId != null && !strTaskId.equals("")) {
			taskId = Long.parseLong(strTaskId);
		}
		// 找到节点信息和相应小form信息
		String json=tm.getAllNodeByTaskId(taskId);
		
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json);
		response.getWriter().flush();
		return null;
	}
	
	/**
	 * 得到所有用户
	 * @Methods Name getUserStore
	 * @Create In Mar 31, 2009 By Administrator
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
//	public ActionForward getUserStore(ActionMapping mapping,
//			ActionForm form, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		String json = "{data:[";
//		List<UserInfo> list=service.findAll(UserInfo.class);
//		for(UserInfo userInfo : list){
//			json+="{id:"+userInfo.getId()+",name:'"+userInfo.getUserName()+"'}";
//			json += ",";
//		}
//		if (json.endsWith(",")) {
//			json = json.substring(0, json.length() - 1);
//		}
//		json += "]}";
//		response.setCharacterEncoding("utf-8");
//		response.getWriter().write(json);
//		response.getWriter().flush();
//		return null;
//	}
	
	/**
	 * 得到某个节点描述信息
	 * @Methods Name getNodeDesc
	 * @Create In Mar 31, 2009 By Administrator
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward getNodeDesc(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String nodeId=request.getParameter("nodeId");
		String strTaskId=request.getParameter("taskId");
		Long taskId=null;
		if (strTaskId != null && !strTaskId.equals("")) {
			taskId = Long.parseLong(strTaskId);
		}
		// 找到节点信息和相应小form信息
		String json=tm.getNodeDesc(taskId, Long.valueOf(nodeId));
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json);
		response.getWriter().flush();
		return null;
	}
	
	/**
	 * 根据用户id得到真实姓名
	 * @Methods Name getUserName
	 * @Create In Mar 31, 2009 By Administrator
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward getUserName(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userId=request.getParameter("userId");
		String json = "";
		UserInfo userInfo=(UserInfo)service.find(UserInfo.class, userId);
		if(userInfo!=null){
			json+="{id:"+userInfo.getId()+",realName:'"+userInfo.getRealName()+"'}";
		}
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json);
		response.getWriter().flush();
		return null;
	}
	
	/**
	 * 延迟加载列出所有的用户
	 * @Methods Name getUserNames
	 * @Create In Mar 27, 2009 By Administrator
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	@SuppressWarnings("unchecked")
	public ActionForward getUserNames(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
        String 	json = "";	
		int start = this.getInt(request, "start", 0);		
		int pageNo = start / pageSize + 1;
		int pageSize = 10;
		String realName = request.getParameter("realName");
		realName = HttpUtil.ConverUnicode(realName);
		//把查询结果做分页
		Map requestParams = new HashMap();
		requestParams.put("userName", realName);
		Page page = cs.findUserInfoByParams(requestParams, pageNo, pageSize);
		Long total = page.getTotalCount();
		List<UserInfo> userInfo = page.list();
		
		if(userInfo.size()==0){
			
			json = "{success: true, rowCount:" + total + ",data:[" + json + "]}";
			try{				
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/plain");
				response.getWriter().write(json);
				response.getWriter().flush();
			}catch(Exception e){
				e.printStackTrace();
			}					
		}else{
			for(UserInfo info: userInfo){
				
				String dataStr = "";
				dataStr+="{realName:'"+info.getRealName()+"("+info.getUserName()+")',id:"+info.getId()+"},";
				json += dataStr;
			}
			if (json.endsWith(",")) {
				json = json.substring(0, json.length() - 1);
			}
			json = "{success: true, rowCount:" + total + ",data:[" + json + "]}";
			
			try{				
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/plain");
				response.getWriter().write(json);
				response.getWriter().flush();
			}catch(Exception e){
				e.printStackTrace();
			}			
		}	
		return null;
	}
	
	/**
	 * 得到下一个节点的信息
	 * @Methods Name getNextNodeInfo
	 * @Create In Mar 30, 2009 By Administrator
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward getNextNodeInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String json="";
		String taskId=request.getParameter("taskId");
		Map map=tm.getNextNodeInfo(Long.valueOf(taskId));
		String nextNodeName=(String)map.get("nodeName");
		String nextNodeDesc=(String)map.get("nodeDesc");
		json+="{nextNodeName:'"+nextNodeName+"',nextNodeDesc:'"+nextNodeDesc+"'}";
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json);
		response.getWriter().flush();
		return null;
	}
	
	
	
	
	public int getInt(HttpServletRequest request, String param,
			int defaultValue) {
		String strValue = request.getParameter(param);
		if (strValue == null) {
			return defaultValue;
		} else {
			return Integer.parseInt(strValue);
		}
	}
	
	@SuppressWarnings("unused")
	private String encodeForCombo(String propertyName,
			List<Map<String, Object>> listData, Long total) {
		String json = "";
		for (Map<String, Object> item : listData) {
			String dataItem = "";
			// ---valueField
			Object value = item.get("id");
			if (value == null)
				value = "";
			dataItem += "id:'" + value + "',";

			// ---displayField
			value = item.get(propertyName);
			if (value == null)
				value = "";
			dataItem += "" + propertyName + ":'" + value + "',";

			if (dataItem.endsWith(",")) {
				dataItem = dataItem.substring(0, dataItem.length() - 1);
			}
			dataItem = "{" + dataItem + "},";
			json += dataItem;
		}

		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		json = "{success: true, rowCount:" + total + ",data:[" + json + "]}";
		return json;
	}
	
	
	public Class getClass(String className) {
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return clazz;
	}
}

