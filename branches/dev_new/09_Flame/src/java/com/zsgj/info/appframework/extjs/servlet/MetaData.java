package com.zsgj.info.appframework.extjs.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.appframework.metadata.entity.UserTableQueryColumn;
import com.zsgj.info.appframework.metadata.entity.UserTableSetting;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;

@SuppressWarnings("serial")
public class MetaData extends HttpServlet {
	private MetaDataManager metaDataManager = (MetaDataManager) ContextHolder.getBean("metaDataManager");
	//private Service service = (Service) ContextHolder.getBean("baseService");

	public MetaData() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("gbk");
		response.setCharacterEncoding("gbk");
		String msg = "";
		String method = request.getParameter("method");
		String clazz = request.getParameter("clazz");
		if (StringUtils.isBlank(method)) {
			msg = "Error: no method specified.";
		} else if (StringUtils.isBlank(clazz)) {
			msg = "Error: no class specified.";
		} else {
			if (method.trim().equalsIgnoreCase("add")) {// 暂时,将来由save代替
				// msg = forAdd(request);
				msg = forSave(request);
			} else if (method.trim().equalsIgnoreCase("edit")) {// 暂时，将来由save代替
				// msg = forEdit(request);
				msg = forSave(request);
			} else if (method.trim().equalsIgnoreCase("save")) {
				msg = forSave(request);
			} else if (method.trim().equalsIgnoreCase("look")) {
				msg = forLook(request);
			}
			else if (method.trim().equalsIgnoreCase("query")) {
				msg = forQuery(request);
			} else if (method.trim().equalsIgnoreCase("html")) {
				msg = forHtmlQuery(request);
				//System.out.print(msg);
			} else if (method.trim().equalsIgnoreCase("head")) {
				msg = forHead(request);
			} 
//			else if (method.trim().equalsIgnoreCase("getItems")) {
//				msg = getItems(request);
//			}
		}
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(msg);
		out.flush();
		out.close();
	}

//	@SuppressWarnings("unchecked")
//	private String getItems(HttpServletRequest request) {
//		String json = "";
//		String pClazz = request.getParameter("clazz");
//		// String dcOrderCode = request.getParameter("dcOrderCode");
//		String bidId = request.getParameter("bidId");
//		String bidApply = "com.digitalchina.ibmb2b.order.entity.OrderApplyBid";
//		Class bidApplyClass = getClass(bidApply);
//		Class<Object> clazz = getClass(pClazz);
//
//		String dcOrderCode="";
//		try {
//			OrderApplyBid bidApplys = (OrderApplyBid) service.findUnique(
//					bidApplyClass, "id", Long.parseLong(bidId));
//			dcOrderCode = bidApplys.getDcOrderCode();
//		} catch (RuntimeException e1) {
//			e1.printStackTrace();
//		}
//
//		Object result;
//		try {
//			result = service.findUnique(clazz, "dcOrderCode", dcOrderCode);
//			OrderApplyCancle bidCancel = (OrderApplyCancle) result;
//			long id = bidCancel.getId();
//			List<UserTableSetting> columns = metaDataManager
//					.getUserColumnForEdit(clazz);
//			Map<String, Object> editMap = metaDataManager.getEntityDataForEdit(
//					clazz, id + "");
//			json = CoderForEdit.encode(editMap, columns);
//		} catch (RuntimeException e) {
//			List<UserTableSetting> columns = metaDataManager
//					.getUserColumnForEdit(clazz);
//			Map<String, Object> addMap = metaDataManager
//					.getEntityDataForAdd(clazz);
//			json = CoderForAdd.encode(addMap, columns);
//		}
//
//		return json;
//	}

	/**
	 * 增加使用的元数据
	 * 
	 * @Methods Name forAdd
	 * @Create In Aug 30, 2008 By yang
	 * @param request
	 * @return
	 * @ReturnType String
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private String forAdd(HttpServletRequest request) {
		String json = "";
		String pClazz = request.getParameter("clazz");
		Class<Object> clazz = getClass(pClazz);
		List<UserTableSetting> columns = metaDataManager
				.getUserColumnForEdit(clazz);
		Map<String, Object> addMap = metaDataManager.getEntityDataForAdd(clazz);
		json = CoderForAdd.encode(addMap, columns);
		return json;
	}

	/**
	 * 修改使用的元数据
	 * 
	 * @Methods Name forEdit
	 * @Create In Aug 30, 2008 By yang
	 * @param request
	 * @return
	 * @ReturnType String
	 */
	@SuppressWarnings("unused")
	private String forEdit(HttpServletRequest request) {
		String json = "";
		String id = request.getParameter("id");
		String className = request.getParameter("clazz");
		Class clazz = getClass(className);
		List<UserTableSetting> columns = metaDataManager
				.getUserColumnForEdit(clazz);
		if (StringUtils.isNotBlank(id)) {
			//Object detail = (Object) service.find(clazz, id, true);
			Map<String, Object> editMap = metaDataManager.getEntityDataForEdit(
					clazz, id);
			json = CoderForEdit.encode(editMap, columns);
		} else {
			json = "no id is specified.";
		}

		return json;
	}

	/**
	 * 保存使用的元数据
	 * 
	 * @Methods Name forSave
	 * @Create In Sep 9, 2008 By yang
	 * @param request
	 * @return
	 * @ReturnType String
	 */
	private String forSave(HttpServletRequest request) {
		String json = "";
		String id = request.getParameter("id");
		String className = request.getParameter("clazz");
		Class clazz = getClass(className);
		List<UserTableSetting> columns = metaDataManager
				.getUserColumnForEdit(clazz);
		Map<String, Object> dataMap = null;
		if (StringUtils.isNotBlank(id)) {
			//Object detail = (Object) service.find(clazz, id, true);//此行保留，删除则无效
			dataMap = metaDataManager.getEntityDataForEdit(clazz, id);
			String webContext = request.getContextPath();
			dataMap.put("webContext", webContext);
			json = CoderForSave.encode(dataMap, columns, true);
		} else {
			dataMap = metaDataManager.getEntityDataForAdd(clazz);
			json = CoderForSave.encode(dataMap, columns, false);
		}

		return json;
	}
	/**
	 * 保存使用的元数据
	 * 
	 * @Methods Name forSave
	 * @Create In Sep 9, 2008 By su
	 * @param request
	 * @return
	 * @ReturnType String
	 */
	private String forLook(HttpServletRequest request) {
		String json = "";
		String id = request.getParameter("id");
		String className = request.getParameter("clazz");
		Class clazz = getClass(className);
		List<UserTableSetting> columns = metaDataManager
				.getUserColumnForEdit(clazz);
		Map<String, Object> dataMap = null;
		if (StringUtils.isNotBlank(id)) {
			dataMap = metaDataManager.getEntityDataForLook(clazz, id);
			json = CoderForLook.encode(dataMap, columns, true);
		} else {
			dataMap = metaDataManager.getEntityDataForAdd(clazz);
			json = CoderForLook.encode(dataMap, columns, false);
		}

		return json;
	}

	/**
	 * 查询使用的元数据
	 * 
	 * @Methods Name forQuery
	 * @Create In Aug 30, 2008 By yang
	 * @param request
	 * @return
	 * @ReturnType String
	 */
	@SuppressWarnings("unchecked")
	private String forQuery(HttpServletRequest request) {
		String json = "";
		String className = request.getParameter("clazz");
		Class clazz = getClass(className);
		List<UserTableQueryColumn> userQueryColumns = metaDataManager
				.getUserColumnForQuery(clazz);
		Map queryMap = metaDataManager.getUserColumnDataForQuery(clazz);//getUserColumnDataForQuery(clazz);
		json = CoderForFind.encode(queryMap, userQueryColumns);
		return json;
	}

	/**
	 * 表头使用的元数据
	 * 
	 * @Methods Name forHead
	 * @Create In Aug 30, 2008 By yang
	 * @param request
	 * @return
	 * @ReturnType String
	 */
	private String forHead(HttpServletRequest request) {
		String json = "";
		String pClazz = request.getParameter("clazz");
		Class clazz = getClass(pClazz);
		if(UserContext.getUserInfo()==null){
			//return json;
		}
		List<UserTableSetting> userVisibleColumns = metaDataManager
				.getUserColumnForList(clazz);
		json = CoderForHead.encode(userVisibleColumns);
		return json;
	}

	private Class getClass(String className) {
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			System.out.print("类名：" + className + "不正确！");
			e.printStackTrace();
		}
		return clazz;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void init() throws ServletException {
	}
	
	/**
	 * 
	 * TODO
	 * Oct 16, 2008 By zhangys
	 * @param request
	 * @return TODO
	 */
	@SuppressWarnings("unchecked")
	private String forHtmlQuery(HttpServletRequest request) {
		String json = "";
		String className = request.getParameter("clazz");
		Class clazz = getClass(className);
		List<UserTableQueryColumn> userQueryColumns = metaDataManager.getUserColumnForQuery(clazz);
		Map queryMap = metaDataManager.getEntityDataForAdd(clazz);
		json = ExtHtmlBuilder.genQueryHtml(queryMap, userQueryColumns);
		return json;
	}
}
