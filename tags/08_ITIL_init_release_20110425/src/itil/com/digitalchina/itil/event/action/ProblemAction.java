package com.digitalchina.itil.event.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.digitalchina.info.appframework.extjs.servlet.CoderForList;
import com.digitalchina.info.appframework.metadata.MetaDataManager;
import com.digitalchina.info.appframework.metadata.entity.Column;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumn;
import com.digitalchina.info.appframework.metadata.entity.UserTableSetting;
import com.digitalchina.info.appframework.metadata.service.SystemColumnService;
import com.digitalchina.info.appframework.metadata.service.SystemMainTableService;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.exception.ApplicationException;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.service.Service;
import com.digitalchina.info.framework.util.HttpUtil;
import com.digitalchina.info.framework.web.adapter.struts2.BaseAction;
import com.digitalchina.itil.config.entity.CIBatchModify;
import com.digitalchina.itil.event.entity.Event;
import com.digitalchina.itil.event.entity.EventProblem;
import com.digitalchina.itil.event.entity.Problem;
import com.digitalchina.itil.event.entity.ProblemHandleLog;
import com.digitalchina.itil.event.entity.ProblemRelation;
import com.digitalchina.itil.event.entity.ProblemRelationType;
import com.digitalchina.itil.event.entity.ProblemStatus;
import com.digitalchina.itil.event.service.ProblemService;

public class ProblemAction extends BaseAction {
	static final String FSP = System.getProperty("file.separator");
	static final String LSP = System.getProperty("line.separator");
	private MetaDataManager metaDataManager = (MetaDataManager) ContextHolder.getBean("metaDataManager");
	private SystemColumnService systemColumnService = (SystemColumnService) ContextHolder
			.getBean("systemColumnService");
	private SystemMainTableService systemMainTableService = (SystemMainTableService) ContextHolder
			.getBean("systemMainTableService");
	private ProblemService problemService = (ProblemService) getBean("ProblemService");
	private Service service = (Service) ContextHolder.getBean("baseService");

	/**
	 * 列出所有相应事件的问题。
	 * 
	 * @Methods Name listProblem
	 * @Create In Nov 2, 2009 By duxh
	 * @return String
	 */
	public String listProblem() throws Exception {
		HttpServletResponse response = super.getResponse();
		int pageSize =HttpUtil.getInt(getRequest(),"pageSize",5);
		int start = HttpUtil.getInt(super.getRequest(), "start", 0);
		int pageNo=start/pageSize+1;
		String orderBy = HttpUtil.getString(super.getRequest(), "orderBy", "id");
		boolean isAsc = HttpUtil.getBoolean(super.getRequest(), "isAsc", true);
		String evid = super.getRequest().getParameter("eventId");
		Event event = new Event();
		event.setId(Long.parseLong(evid));
		Map requestParams = HttpUtil.requestParam2Map(super.getRequest());
		requestParams.put("event", event);
		Page page = metaDataManager.query(EventProblem.class, requestParams, pageNo, pageSize, orderBy, isAsc);
		Long total = page.getTotalCount();
		List<EventProblem> eventProblemList = page.list();
		String json = "";
		// modify by guoxl in 20090803 begin 原因：列表中没有数据时不停刷新
		if (eventProblemList.size() > 0) {
			for (EventProblem eventProblem : eventProblemList) {
				String pid = eventProblem.getProblem().getId().toString();
				String submitUser = eventProblem.getProblem().getSubmitUser().getRealName();
				String contactPhone = eventProblem.getProblem().getContactPhone();
				String contactEmail = eventProblem.getProblem().getContactEmail();
				Date submitTime = eventProblem.getProblem().getSubmitTime();
				// System.out.println(eventProblem.getProblem().getId());
				String problemStatus = eventProblem.getProblem().getStatus().getName();
				String summary = eventProblem.getProblem().getSummary();
				String contactUser = eventProblem.getProblem().getContactUser().getRealName();
				String pa = eventProblem.getProblem().getContactEmail();
				Date contactDate = eventProblem.getProblem().getClosedDate();
				String closedDate = "";
				if (contactDate != null) {
					closedDate = contactDate.toString();
					closedDate = closedDate.substring(0, 19);
				}
				json += "{Problem$id:\"" + pid + "\",";
				json += "Problem$submitUser:\"" + submitUser + "\",";
				json += "Problem$summary:\"" + summary + "\",";
				json += "Problem$contactPhone:\"" + contactPhone + "\",";
				json += "Problem$contactUser:\"" + contactUser + "\",";
				json += "Problem$contactEmail:\"" + contactEmail + "\",";
				json += "Problem$closedDate:\"" + closedDate + "\",";
				json += "Problem$status:\"" + problemStatus + "\",";
				// json +="Problem$submitTime:\""+submitTime+"\",";
				json += "Problem$problemCisn:\"" + eventProblem.getProblem().getProblemCisn() + "\",";
				json += "Problem$submitTime:\"" + submitTime + "\"},";
			}
			if (json.endsWith(",")) {
				json = json.substring(0, json.length() - 1);
			}
			json = "{success : true,rowCount:" + total + ",data:[" + json + "]}";
		} else {
			//json = "{success : true,rowCount:" + total + ",data:[]}";
			json = "{success : true,rowCount:" + 1 + ",data:[]}";
		}
		// modify by guoxl in 20090803 end
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
	}
	//add by hu --- start
	/**
	 * 根据问题ID获得问题的详细信息
	 * @Methods Name findProblemDetailByID
	 * @Create In Feb 1, 2010 By huzh
	 * @return
	 * @throws Exception 
	 * @Return String
	 */
	public String findProblemDetailByID() throws Exception{
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String problemId = request.getParameter("problemId");
		Class clazz = Problem.class;
		Problem problem = (Problem) service.find(clazz, problemId);
		String json = "";
		if(problem!=null){
			String problemStatus = problem.getStatus().getName();
			// 2010-04-20 modified by huzh for 加上userName和department begin
			String sRealName = problem.getSubmitUser().getRealName();
			String sUserName = problem.getSubmitUser().getUserName();
			String department = problem.getSubmitUser().getDepartment().getDepartName();
			// 2010-04-20 modified by huzh for 加上userName和department end
			String contactEmail = problem.getContactEmail();
			String summary = problem.getSummary();
			Date submitTime = problem.getSubmitTime();
			String problemCisn = problem.getProblemCisn();
			String remark = problem.getRemark()==null? "":problem.getRemark();
			String files = problem.getFiles()==null? "":problem.getFiles();
			// 2010-04-20 modified by huzh for 加上userName和department begin
			json += "{submitUser:\"" + sRealName+"/"+sUserName+"/"+department+ "\",";
			// 2010-04-20 modified by huzh for 加上userName和department end
			json += "summary:\"" + summary + "\",";
			json += "contactEmail:\"" + contactEmail + "\",";
			json += "status:\"" + problemStatus + "\",";
			json += "problemCisn:\"" + problemCisn + "\",";
			json += "files:\"" + files + "\",";
			json += "remark:\"" + remark + "\",";
			json += "submitTime:\"" + submitTime + "\"}";	
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json);
		response.getWriter().flush();
		return null;
	}
	//add by hu --- end
	/**
	 * 修改关联事件问题
	 * 
	 * @Methods Name modifyRelationEvent
	 * @Create In Mar 31, 2009 By daijf
	 * @return
	 * @throws IOException
	 *             String
	 */
	public String modifyProblemRelation() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String problemReId = request.getParameter("problemReId");
		String eid = request.getParameter("eid");
		String pid = request.getParameter("pid");
		String parampid = request.getParameter("parampid");
		String problemRelationTypeId = request.getParameter("problemRelationTypeId");
		Class clazz = ProblemRelation.class;
		ProblemRelation problem = (ProblemRelation) service.find(clazz, problemReId);
		Event et = new Event();
		et.setId(Long.parseLong(eid));
		problem.setEvent(et);
		Problem pr = new Problem();
		pr.setId(Long.parseLong(pid));
		problem.setProblem(pr);
		if (parampid == null || "".equals(parampid)) {

		} else {
			Problem ppr = new Problem();
			ppr.setId(Long.parseLong(parampid));
			problem.setParentProblem(ppr);
		}
		if (problemRelationTypeId == null || "".equals(problemRelationTypeId)) {

		} else {
			ProblemRelationType ert = new ProblemRelationType();
			ert.setId(Long.parseLong(problemRelationTypeId));
			problem.setProblemRelationType(ert);
		}
		service.save(problem);
		response.setContentType("text/plain");
		PrintWriter out = super.getResponse().getWriter();
		String json = "{success : true}";
		out.println(json);
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 根据关系问题去找问题ID
	 * 
	 * @Methods Name relationProblem
	 * @Create In Mar 30, 2009 By daijf
	 * @Modify By lee in 20090917
	 * @return
	 * @throws IOException
	 *             String
	 */
	public String relationProblem() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String prId = request.getParameter("prId");
		ProblemRelation pr = (ProblemRelation) service.find(ProblemRelation.class, prId);
		String relationEventId = pr.getEvent().getId().toString();
		String relatioProblemId = pr.getParentProblem().getId().toString();
		String relationProblemTypeId = pr.getProblemRelationType().getId().toString();
		String result = "{success:true,relationEventId:'" + relationEventId + "',relatioProblemId:'" + relatioProblemId
				+ "',relationProblemTypeId:'" + relationProblemTypeId + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		response.getWriter().write(result);
		writer.flush();
		return null;
	}
	/**
	 * 根据问题状态判断跳转的页面
	 * 
	 * @Methods Name findProblemStatus
	 * @Create In Mar 18, 2009 By daijf
	 * @return
	 * @throws IOException
	 *             String
	 */
	public String findProblemStatus() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String evid = request.getParameter("eventId");
		if (!evid.equals("")) {
			Problem problem = (Problem) service.find(Problem.class, evid);
			String pStatus = problem.getStatus().getKeyword();
			String result = "{success:true,STATUS:'" + pStatus + "'}";
			PrintWriter writer = response.getWriter();
			response.setContentType("text/plain");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(result);
			writer.flush();
			return null;
		} else {
			response.setContentType("text/plain");
			PrintWriter out = super.getResponse().getWriter();
			String json = "{success : true}";
			out.println(json);
			out.flush();
			out.close();
			return null;
		}
	}

	/**
	 * 获取关联事件combox数据
	 * 
	 * @Methods Name findProblemEvent
	 * @Create In Mar 10, 2009 By daijf
	 * @return
	 * @throws IOException
	 *             String
	 */
	public String findProblemEvent() throws IOException {
		int pageSize = 10;
		// 注意以后从设置中取出
		int start = HttpUtil.getInt(super.getRequest(), "start", 0);
		int pageNo = start / pageSize + 1;
		Page page = this.problemService.findProblemsEvents(null, pageNo, pageSize);
		Long total = page.getTotalCount();
		List queryList = page.list();
		List<Map<String, Object>> listData = metaDataManager.getEntityMapDataForList(Event.class, queryList);
		List<UserTableSetting> userVisibleColumns = metaDataManager.getUserColumnForList(Event.class);
		String json = "";
		json = this.encodeProblem(userVisibleColumns, listData, total);
		super.getResponse().setContentType("text/plain");
		super.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out = super.getResponse().getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
	}
	public static String encodeProblem(List<UserTableSetting> userVisibleColumns, List<Map<String, Object>> listData,
			Long total) {
		String json = "";
		for (Map<String, Object> item : listData) {
			String dataItem = "";
			for (UserTableSetting uts : userVisibleColumns) {
				String columnCnName = uts.getColumn().getColumnCnName();// 表头标题
				// SystemMainTableColumn mainTableColumn =
				// uts.getMainTableColumn();
				Column column = uts.getColumn();
				String propertyName = column.getPropertyName();//
				String columnTypeName = column.getSystemMainTableColumnType().getColumnTypeName();
				if (columnTypeName.equalsIgnoreCase("hidden")) {
					Object value = item.get(propertyName);
					if (value == null)
						value = "";
					dataItem += "" + propertyName + ":'" + value + "',";
					// value ${item.id}
				} else if (columnTypeName.equalsIgnoreCase("text") || columnTypeName.equalsIgnoreCase("dateText")
						|| columnTypeName.equalsIgnoreCase("radio")) {
					Object value = item.get(propertyName);
					if (value == null)
						value = "";
					if (propertyName.equals("name")) {
						propertyName = "knowProblemType";
					}
					dataItem += "" + propertyName + ":'" + value + "',";
					// value ${item[column.mainTableColumn.propertyName]}
				} else if (columnTypeName.equalsIgnoreCase("select")) {
					// ?????
					// String foreignPropertyName =
					// column.getForeignTableValueColumn().getPropertyName();
					Object value = item.get(propertyName);
					if (value == null)
						value = "";
					// Integer isAbstract = column.getAbstractFlag();
					// if(isAbstract!=null&& isAbstract.intValue()==1){
					// propertyName = "name";
					// }

					dataItem += "" + propertyName + ":'" + value + "',";
					// value ${item[column.mainTableColumn.propertyName]}
				} else if (columnTypeName.equalsIgnoreCase("yesNoSelect")) {
					Object value = item.get(propertyName);
					if (value == null)
						value = "";
					dataItem += "" + propertyName + ":'" + value + "',";
					// value ${item[column.mainTableColumn.propertyName]}
				} else {// 其他
					Object value = item.get(propertyName);
					if (value == null)
						value = "";
					dataItem += "" + propertyName + ":'" + value + "',";
				}
			}
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

	/**
	 * 获取关联事件的问题combox数据
	 * 
	 * @Methods Name findEventProblems
	 * @Create In Mar 10, 2009 By daijf
	 * @return
	 * @throws IOException
	 *             String
	 */
	public String findEventProblems() throws IOException {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String eventId = request.getParameter("relEventId");
		String dataId = request.getParameter("id");
		String name = request.getParameter("sumarry");
		String eStatus=request.getParameter("status");

		int pageSize = HttpUtil.getInt(request, "pageSize", 10);
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo = start / pageSize + 1;
		String json = "";
		// ----如果没有关联事件传过来，则不查
		if (StringUtils.isBlank(eventId)) {
			json = "{success:true,rowCount:0,data:[]}";
			response.setContentType("text/plain");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(json);
			response.getWriter().flush();
			return null;
		}

		Page page = problemService.getEventProblem(dataId, eventId, name, pageNo, pageSize,eStatus);
		Long total = page.getTotalCount();
		List queryList = page.list();
		List<Map<String, Object>> listData = metaDataManager.getEntityMapDataForList(Problem.class, queryList);
		SystemMainTable smt = systemMainTableService.findSystemMainTableByClazz(Problem.class);
		List<SystemMainTableColumn> visibleColumns = systemColumnService.findSystemTableColumns(smt);
		json = CoderForList.encodeForComboList(visibleColumns, listData, total);
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json);
		response.getWriter().flush();

		return null;

	}

	/**
	 * 保存问题关系
	 * 
	 * @Methods Name saveProblemRelation
	 * @Create In Mar 12, 2009 By daijf
	 * @return
	 * @throws IOException
	 *             String
	 */
	@SuppressWarnings("unchecked")
	public String saveProblemRelation() throws IOException {
		HttpServletResponse response = super.getResponse();
		HttpServletRequest request = super.getRequest();
		String params = request.getParameter("eid");
		String RelationEventId = request.getParameter("RelationEventId");
		String paramsProblem = request.getParameter("pid");
		String paramsPrarentProblem = request.getParameter("parampid");
		String paramsRelationId = request.getParameter("problemRelatinId");
		HashMap evpro = new HashMap();
		Event parentEvent = new Event();
		parentEvent.setId(Long.parseLong(RelationEventId));
		evpro.put("parentEvent", parentEvent);
		Event e = new Event();
		e.setId(Long.parseLong(params));
		evpro.put("event", e);
		Problem p = new Problem();
		p.setId(Long.parseLong(paramsProblem));
		evpro.put("problem", p);
		Problem pp = new Problem();
		pp.setId(Long.parseLong(paramsPrarentProblem));
		evpro.put("parentProblem", pp);
		ProblemRelationType ert = new ProblemRelationType();
		ert.setId(Long.parseLong(paramsRelationId));
		evpro.put("problemRelationType", ert);
		metaDataManager.saveEntityData(ProblemRelation.class, evpro);
		String json = "{success:true}";
		response.setContentType("text/plain");
		PrintWriter writer = response.getWriter();
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json);
		writer.flush();
		return null;
	}

	/**
	 * 保存问题.
	 * 
	 * @Methods Name saveProblem
	 * @Create In Nov 2, 2009 By duxh
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String saveProblem() throws IOException {
		HttpServletResponse response = super.getResponse();
		HttpServletRequest request = super.getRequest();
		String params = request.getParameter("eid");
		Long para = Long.parseLong(params);
		String info = request.getParameter("panelparam");
		HashMap myinfoMap = new HashMap();
		JSONObject jo = JSONObject.fromObject(info);
		Iterator itInfo = jo.keys();
		while (itInfo.hasNext()) {
			String key = (String) itInfo.next();
			String[] keyString = key.split("\\$");
			String value = jo.getString(key);
			myinfoMap.put(keyString[1], value);
		}
		myinfoMap.put("contactUser", UserContext.getUserInfo());
		myinfoMap.put("submitUser", UserContext.getUserInfo());
		myinfoMap.put("contactEmail", UserContext.getUserInfo().getEmail());
		myinfoMap.put("contactPhone", UserContext.getUserInfo().getTelephone());
		ProblemStatus problemStatus = (ProblemStatus) getService().findUnique(ProblemStatus.class,"keyword","dealing");
		myinfoMap.put("status", problemStatus);
		myinfoMap.put("submitTime", new Date());
		Problem problemee = (Problem) metaDataManager.saveEntityData(Problem.class, myinfoMap);
		HashMap evpro = new HashMap();
		Event e = new Event();
		e.setId(para);
		evpro.put("event", e);
		Problem p = new Problem();
		p.setId(problemee.getId());
		evpro.put("problem", p);
		metaDataManager.saveEntityData(EventProblem.class, evpro);
		String json = "{success:true}";
		PrintWriter writer = response.getWriter();
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json);
		writer.flush();
		return null;
	}

	/**
	 * 保存问题状态
	 * 
	 * @Methods Name saveProblemStatus
	 * @Create In Mar 20, 2009 By daijf
	 * @return
	 * @throws IOException
	 *             String
	 */
	@SuppressWarnings("unchecked")
	public String saveProblemStatus() throws IOException {
		HttpServletResponse response = super.getResponse();
		HttpServletRequest request = super.getRequest();
		String params = request.getParameter("eid");
		Problem problem = (Problem) service.find(Problem.class, params);
		String id = problem.getId().toString();
		//modified by huzh in 20100311 ---start 如果有配置项批量变更未审批通过，则不能结束
		String flag=problemService.modifyStatusOfProblem(Long.parseLong(id));
		String json=null;
		if("saved".equals(flag)){
		   json = "{success:true}";
		}else if("cibmNOPass".equals(flag)){
		  json ="{success:false}";
		}
		//modified by huzh in 20100311 --- end 
		PrintWriter writer = response.getWriter();
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json);
		writer.flush();
		return null;
	}

	/**
	 * 保存问题日志
	 * 
	 * @Methods Name saveProblemLog
	 * @Create In Mar 20, 2009 By daijf
	 * @return
	 * @throws IOException
	 *             String
	 */
	@SuppressWarnings("unchecked")
	public String saveProblemLog() throws IOException {
		HttpServletResponse response = super.getResponse();
		HttpServletRequest request = super.getRequest();
		String params = request.getParameter("eid");
		Long para = Long.parseLong(params);
		String info = request.getParameter("problemlogparam");
		HashMap myinfoMap = new HashMap();
		JSONObject jo = JSONObject.fromObject(info);
		Iterator itInfo = jo.keys();
		while (itInfo.hasNext()) {
			String key = (String) itInfo.next();
			String[] keyString = key.split("\\$");
			String value = jo.getString(key);
			myinfoMap.put(keyString[1], value);
		}
		Problem p = new Problem();
		p.setId(para);
		myinfoMap.put("problem", p);
		myinfoMap.put("handleUser", UserContext.getUserInfo());
		myinfoMap.put("handleDate", new Date());
		metaDataManager.saveEntityData(ProblemHandleLog.class, myinfoMap);
		String json = "{success:true}";
		PrintWriter writer = response.getWriter();
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json);
		writer.flush();
		return null;
	}

	/**
	 * 保存问题日志（配置项变更提交成功后将变更基本信息作为变更问题的日志存一条记录）
	 * 
	 * @Methods Name saveModifyProblemLog
	 * @Create In Sep 23, 2009 By guoxl
	 * @return
	 * @throws IOException
	 *             String
	 */
	public String saveModifyProblemLog() throws IOException {
		HttpServletResponse response = super.getResponse();
		HttpServletRequest request = super.getRequest();
		String params = request.getParameter("eid");
		String modifyId = request.getParameter("modifyId");// 得到问题对应的变更id
		Long para = Long.parseLong(params);
		Problem p = new Problem();
		CIBatchModify ciBatchModify = null;
		if (modifyId != null) {
			ciBatchModify = (CIBatchModify) super.getService().find(CIBatchModify.class, modifyId, true);
		}
		p.setId(para);

		String log = "";
		if (ciBatchModify != null) {
			log += "变更编号:" + ciBatchModify.getModifyNo() + "，";
			log += "变更名称:" + ciBatchModify.getName();
		}
		// String info=request.getParameter("problemlogparam");
		HashMap myinfoMap = new HashMap();
		// JSONObject jo = JSONObject.fromObject(info);
		// Iterator itInfo = jo.keys();
		// while (itInfo.hasNext()) {
		// String key = (String) itInfo.next();
		// String [] keyString=key.split("\\$");
		// String value=jo.getString(key);
		// myinfoMap.put(keyString[1], value);
		// }
		myinfoMap.put("handleLog", log);
		myinfoMap.put("problem", p);
		myinfoMap.put("handleUser", UserContext.getUserInfo());
		myinfoMap.put("handleDate", new Date());
		metaDataManager.saveEntityData(ProblemHandleLog.class, myinfoMap);
		String json = "{success:true}";
		PrintWriter writer = response.getWriter();
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json);
		writer.flush();
		return null;
	}

	/**
	 * 逻辑删除问题。
	 * 
	 * @Methods Name removeProblems
	 * @Create In Oct 30, 2009 By duxh
	 * @return String
	 */
	public String removeProblems() throws IOException {
		try {
			HttpServletResponse response = getResponse();
			HttpServletRequest request = getRequest();
			String params = request.getParameter("problemsId");
			Object[] obj=JSONArray.fromObject(params).toArray();
			Long[] problemsId=new Long[obj.length];
			for(int i=0;i<obj.length;i++){
				problemsId[i]=Long.parseLong(obj[i].toString());
			}
			List<String> problemCisns=problemService.removeProblems(problemsId);
			String json = problemCisns.toString().substring(1, problemCisns.toString().length()-1);
			PrintWriter writer = response.getWriter();
			response.setContentType("text/plain");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(json);
			writer.flush();
			writer.close();
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}

	/**
	 * 查询问题树
	 * 
	 * @Methods Name queryTreeData
	 * @Create In Mar 22, 2009 By sujs
	 * @return String
	 * @throws IOException
	 */
	public String queryTreeData() throws IOException {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String eventId = request.getParameter("eventId");
		Event event=new Event();
		event.setId(Long.parseLong(eventId));
		List<EventProblem> problemList = getService().find(EventProblem.class, "event",event);
		String json = "";
		int k = 1;
		for (EventProblem eventProblem : problemList) {
			json += "{id:'" + eventProblem.getProblem().getId().toString() + "_P'" + ",";
			json += "text:'" + eventProblem.getProblem().getSummary() + "',";// +eventProblem.getProblem().getName()+"',";
			json += "icon :webContext+'/images/other/class.gif',";
			json += "cls : 'x-btn-text-icon',";
			List<ProblemHandleLog> ProblemHandList = getService().find(ProblemHandleLog.class, "problem",eventProblem.getProblem());
			if (ProblemHandList.size() < 1) {
				json += "leaf:true},";
			} else {
				int h = 1;
				json += "children:[";
				for (ProblemHandleLog problemLog : ProblemHandList) {
					json += "{id:'" + problemLog.getId().toString() + "_L'" + ",";
					json += "text:'日志" + h + "',";// +eventProblem.getProblem().getName()+"',";
					json += "icon :webContext+'/images/other/class.gif',";
					json += "cls : 'x-btn-text-icon',";
					json += "leaf:true},";
					h++;
				}
				if (json.endsWith(",")) {
					json = json.substring(0, json.length() - 1);
				}
				json += "]},";

			}
			k++;
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		json = "[" + json + "]";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(json);
		writer.flush();
		return null;
	}
	/**
	 * 查询日志真实的数据
	 * @Methods Name queryLogData
	 * @Create In Mar 23, 2009 By sujs
	 * @return String
	 */
	public String queryLogData(){
	    String problemlogId = super.getRequest().getParameter("problemId");
	    if(problemlogId == null || problemlogId == ""){
	    	return null;
	    }
		String json = "{\"problemlog\":[{";
		ProblemHandleLog problemHandleLog = (ProblemHandleLog) getService().find(ProblemHandleLog.class, problemlogId);
		json = json + "\"problemlogdata\":\"" + problemHandleLog.getHandleLog() + "\"";
		json += "}]}";
		try {
			HttpServletResponse response = super.getResponse();
			response.setContentType("text/plain");
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; 
	}
	/**
	 * 列出某工程师所有已结束事件的未结束问题
	 * 
	 * @author awen
	 * @Create In Mar 26, 2009 By awen
	 * @return
	 * @throws IOException
	 */
	public String findAllNotEndProblemsOFAllEndEvents() throws IOException {
		HttpServletResponse response = super.getResponse();
		UserInfo userInfo = UserContext.getUserInfo();
		int pageSize = 10;
		String problemName = super.getRequest().getParameter("problemName");
		int start = HttpUtil.getInt(super.getRequest(), "start", 0);// 起始页
        int pageNo=start/pageSize+1;
		Page page = problemService.findAllNotEndProblemsOfAllEndEvents(userInfo.getId(), pageNo, pageSize,problemName);
		long total = page.getTotalCount();
		List<EventProblem> eventProblemList = (List<EventProblem>) page.list();
		String json = "";
		if (eventProblemList.size()==0||eventProblemList == null) {
			json = "{rowCount : 1, data : []}";
		} else {
			for (EventProblem eventProblem : eventProblemList) {
				json += "{id:'" + eventProblem.getProblem().getId() + "',summary:'"
						+ eventProblem.getProblem().getSummary() + "',submitTime:'"
						+ eventProblem.getProblem().getSubmitTime().toString().substring(0, 16) + "',problemCisn:'"
						+ eventProblem.getProblem().getProblemCisn() + "'},";
			}
			if (json.endsWith(",")) {
				json = json.substring(0, json.length() - 1);
			}
			json = "{rowCount:" + total + ",data:[" + json + "]}";
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(json);
		writer.flush();
		return null;
	}

	/**
	 * 保存修改问题数据
	 * 
	 * @Methods Name saveModifyProblem
	 * @Create In Apr 8, 2009 By daijf
	 * @return
	 * @throws IOException
	 *             String
	 */
	public String saveModifyProblem() throws IOException {
		HttpServletResponse response = super.getResponse();
		HttpServletRequest request = super.getRequest();
		String problemId = request.getParameter("problemId");
		String info = request.getParameter("problemPanelParam");
		HashMap myinfoMap = new HashMap();
		JSONObject jo = JSONObject.fromObject(info);
		Iterator itInfo = jo.keys();
		while (itInfo.hasNext()) {
			String key = (String) itInfo.next();
			String[] keyString = key.split("\\$");
			String value = jo.getString(key);
			myinfoMap.put(keyString[1], value);
		}
		Problem pro = (Problem) service.find(Problem.class, problemId);
		// pro.setSummary(myinfoMap.get("summary").toString());
		// pro.setRemark(myinfoMap.get("remark").toString());
		// pro.

		myinfoMap.put("status", pro.getStatus());
		myinfoMap.put("submitUser", pro.getSubmitUser());
		myinfoMap.put("contactUser", pro.getContactUser());
		myinfoMap.put("contactEmail", pro.getContactEmail());
		myinfoMap.put("contactPhone", pro.getContactPhone());
		myinfoMap.put("submitTime", pro.getSubmitTime());
		myinfoMap.put("modifyTime", new Date());// 修改时间

		myinfoMap.put("id", problemId);
		// service.save(pro);
		metaDataManager.saveEntityData(Problem.class, myinfoMap);
		String json = "{success:true}";
		PrintWriter writer = response.getWriter();
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json);
		writer.flush();
		return null;
	}

	public String createProblemRelation() throws IOException {
		HttpServletRequest httpServletRequest = super.getRequest();
		String relId = httpServletRequest.getParameter("curRelId");// 问题关系id,来判断是新增还是修改
		Long currentEventId = Long.parseLong(httpServletRequest.getParameter("curEventId"));// 当前事件id
		Long parentEventId = Long.parseLong(httpServletRequest.getParameter("parentEventId"));// 关联事件id

		Long currProblemId = Long.parseLong(httpServletRequest.getParameter("currProblemId"));// 当前问题id
		Long parentProblemId = Long.parseLong(httpServletRequest.getParameter("parentProblemId"));// 关联问题id

		Long problemRelationTypeId = Long.parseLong(httpServletRequest.getParameter("problemRelationTypeId"));// 新的问题关系id
		String json = "";
		if (!"".equals(relId)) {// 修改
			// eventService.modifyEventRelation(relId,currentEventId,
			// parentEventId,
			// eventRelationTypeId);
			problemService.modifyEventRelation(relId, currentEventId, parentEventId, currProblemId, parentProblemId,
					problemRelationTypeId);
			json = "{success : true,isExist : 'true'}";
		} else {// 新增
			if (!problemService.isExistProblem(currProblemId, parentProblemId)) {
				problemService.createEventRelation(currentEventId, parentEventId, currProblemId, parentProblemId,
						problemRelationTypeId);
				json = "{success:true,isExist:'true'}";
			} else {
				json = "{success:true,isExist:'false'}";
			}
			//			
		}
		super.getResponse().setContentType("text/plain");
		PrintWriter out = super.getResponse().getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 获取当前问题的关联问题。
	 * 
	 * @Methods Name getProblemRelByCurrProblem
	 * @Create In Nov 5, 2009 By duxh
	 * @return String
	 */
	public String getProblemRelByCurrProblem() {

		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String currProblemId = request.getParameter("problemId");
		Problem problem = (Problem) super.getService().find(Problem.class, currProblemId, true);
		Long total = 1L;
		int pageSize = HttpUtil.getInt(request, "pageSize", 10);
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo = start / pageSize + 1;
		Page page = problemService.getProblemRelByCurrEvent(problem, pageNo, pageSize);
		List<ProblemRelation> relList = new ArrayList<ProblemRelation>();
		String json = "";
		if (page != null) {
			total = page.getTotalCount();
			relList = page.list();
		}

		String relProblemCisn = "";
		String relProblemName = "";
		String eventName = "";
		String relProblemRelType = "";
		String problemStatus = "";
		String dealUser = "";
		String closeDate = "";

		if (relList.size() > 0) {
			for (ProblemRelation rel : relList) {
				// if((rel.getParentEvent().getId()!=event.getId())&&(rel.getEventRelationType().getId()==EventRelTypeA.getId()||rel.getEventRelationType().getId()==EventRelTypeC.getId()||rel.getEventRelationType().getId()==EventRelTypeD.getId())){
				if (rel.getParentProblem().getStatus() != null) {
					problemStatus = rel.getParentProblem().getStatus().getName();
				}
				// if(rel.getParentEvent().getDealuser()!=null){
				// dealUser = rel.getParentEvent().getDealuser().getRealName();
				// }
				if (rel.getParentProblem().getClosedDate() != null) {
					closeDate = rel.getParentProblem().getClosedDate().toString();
					closeDate = closeDate.substring(0, 19);
				}
				if (rel.getParentEvent() != null) {
					eventName = rel.getParentEvent().getSummary();
				}

				relProblemCisn = rel.getParentProblem().getProblemCisn();
				relProblemName = rel.getParentProblem().getSummary();
				relProblemRelType = rel.getProblemRelationType().getName();
				json += "{\"id\":" + rel.getId() + "," + "\"parentProblemCisn\":'" + relProblemCisn + "',"
						+ "\"problem\":'" + relProblemName + "'," + "\"event\":'" + eventName + "'," + "\"typeName\":'"
						//2010-04-20 modified by huzh for 去掉处理人 begin
//						+ relProblemRelType + "'," + "\"problemStatus\":'" + problemStatus + "'," + "\"dealUser\":'"
//						+ dealUser + "'," + "\"closeDate\":'" + closeDate + "'},";
						+ relProblemRelType + "'," + "\"problemStatus\":'" + problemStatus + "'," 
						+ "\"closeDate\":'" + closeDate + "'},";
						//2010-04-20 modified by huzh for 去掉处理人 end

			}
		}
		if (json.length() == 0) {
			json = "{success:true,rowCount:" + "1" + ",data:[" + json.substring(0, json.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:[" + json.substring(0, json.length() - 1) + "]}";
		}
		try {
			response.setContentType("text/plain");
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除问题关系。
	 * 
	 * @Methods Name removeProblemDoubleRel
	 * @Create In Nov 5, 2009 By duxh
	 * @return String
	 */
	public String removeProblemDoubleRel() {
		String json = "{suceess:true}";
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String[] ids = request.getParameterValues("id");
		for (int i = 0; i < ids.length; i++) {
			problemService.removeProblemDoubleRel(ids[i]);

		}
		try {
			response.setContentType("text/plain");
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 确定分页的页码
	 * 
	 * @Methods Name confirmPageNo
	 * @Create In Dec 23, 2008 By Administrator
	 * @param paramName
	 * @param size
	 * @return int
	 */
	public int confirmPageNo(String paramName, int size) {

		if (paramName == null || paramName.equals("")) {
			return size;
		}
		return Integer.parseInt(paramName);
	}

	/**
	 * 获取combox数据
	 * 
	 * @Methods Name findProblemRelationTypes Create In Apr 9, 2009 By daijf
	 * @return
	 * @throws IOException
	 */
	public String findProblemRelationTypes() throws IOException {
		List<ProblemRelationType> problemRelationTypeList = service.findAll(ProblemRelationType.class);
		String json = "";
		for (ProblemRelationType problemRelationType : problemRelationTypeList) {
			json += "{id:'" + problemRelationType.getId() + "',name:'" + problemRelationType.getName() + "'},";
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		json = "{rowCount:" + problemRelationTypeList.size() + ",data:[" + json + "]}";
		super.getResponse().setContentType("text/plain");
		super.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out = super.getResponse().getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
	}
}
