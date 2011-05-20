/**
 * @Probject Name: 10_InfoFramework_B2
 * @Path: com.zsgj.knowledge.common.actionNoticeAction.java
 * @Create By zhangpeng
 * @Create In 2008-5-21 ����04:59:12
 * TODO
 */
package com.zsgj.itil.system.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.appframework.metadata.QueryService;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.entity.UserTableSetting;
import com.zsgj.info.appframework.metadata.service.MetaDataService;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.util.TimeTool;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;
import com.zsgj.itil.notice.entity.NewNotice;
import com.zsgj.itil.system.entity.Notice;
import com.zsgj.itil.train.service.TrainPlanService;

/**
 * @Class Name NoticeAction
 * @Author zhangpeng
 * @Create In 2008-5-21
 */
public class NoticeAction extends BaseDispatchAction {

	private Service bs = getService();
	private MetaDataService ms = (MetaDataService) getBean("metaDataService");
	private boolean showListSearchTitle = true;
	private MetaDataManager metaDataManager = (MetaDataManager) getBean("metaDataManager");
//	private QueryService queryService = (QueryService) getBean("columnQueryServiceDefaultImpl");
//	private TrainPlanService trainPlanService = (TrainPlanService) getBean("trainPlanService");
	public ActionForward query(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse httpServletResponse)
			throws Exception {

		SystemMainTable systemMainTable = ms
				.findSystemMainTableByName("Notice");
		request.setAttribute("sysMainTable", systemMainTable);

//		Map params = HttpUtil.requestParam2Map(request);
		
		// ��ȡ��ѯ���ص������ݣ�ʹ��request�еĲ�ѯ����ֵ����ѯ�ͻ���������name�����������true��ʾ���򣬷�����
//		List queryList = queryService.queryByParams(Notice.class, params, null, "name", true);
		/*List queryList = MetaDataUtil.getDataListByEntityAndParam(request,
				"Notice", Notice.class, "name", true);*/

		// List list = ms.findDataListByEntityAndParam(entityClazz, paramValues,
		// pageNo, pageSide, orderProp, isAsc)
		// ��ȡ�ɼ����ֶ�
		UserInfo userInfo = UserContext.getUserInfo();
		List columnsVisible = ms.findTableAllColumns(userInfo, systemMainTable,
				UserTableSetting.LIST, true);
		request.setAttribute("columnsVisible", columnsVisible);

		// ��ȡ��չ���ݣ�false��ʾ����map��ʾ�����е�����
//		List mapList = metaDataManager.getEntityDataForList(Notice.class, queryList);
		/*request.setAttribute("list", MetaDataUtil.getExtendDatas(queryList,
				"Notice", true));*/

		// �Ƿ����б�ҳ����ʾ��ѯ�����ı���
		if (showListSearchTitle) {
			request.setAttribute("showListSearchTitle", showListSearchTitle);
			// ��ȡ��ǰ�û��Ĳ�ѯ�ֶΣ�����ʹ��UserContext.getUserInfo();��ȡ��ǰ�û�
			// List userQueryColumns = MetaDataUtil.getQueryColumn(request,
			// "Notice");
			Map<String, Object> queryColumnData = metaDataManager
					.getUserColumnDataForQuery(Notice.class);
			HttpUtil.map2RequestAttri(request, queryColumnData);

			List userQueryColumns = metaDataManager
					.getUserColumnForQuery(Notice.class);
			request.setAttribute("userQueryColumns", userQueryColumns);
		}

		return mapping.findForward("list");
	}

	public ActionForward list(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// ���շ�ҳ����
		int pageNo = 1; // Ĭ��Ϊ��1ҳ
		String pageNoStr = request.getParameter("start");
		if (StringUtils.isNotBlank(pageNoStr)) {
			pageNo = Integer.valueOf(pageNoStr);
		}

		// ��ȡ��ѯ���ص������ݣ�ʹ��request�еĲ�ѯ����ֵ����ѯ�ͻ���������name�����������true��ʾ���򣬷�����
		Page page = getService().findByPagedQuery(Notice.class, "id", false,
				pageNo, pageSize);

		List queryList = (List) page.getResult();

		// ��ȡ��չ���ݣ�false��ʾ����map��ʾ�����е�����
		// List listData = MetaDataUtil.getMainAndExtendDatas(queryList,
		// "Notice");
		List listData = metaDataManager.getEntityMapDataForList(Notice.class,
				queryList);
		request.setAttribute("list", listData);
		String js = "";
		for (int i = 0; i < listData.size(); i++) {
			Map data = (Map) listData.get(i);
			Set keySet = data.keySet();
			Iterator it = keySet.iterator();
			String jss = "";
//			String enabled = (String) data.get("enabled");
			while (it.hasNext()) {
				String key = (String) it.next();
				if ("enabled".equals(key)) {
					String enabledValue = (String) data.get(key);
					if ("��".equals(enabledValue)) {
						jss += "'" + key + "':'��',";
					} else {
						jss += "'" + key + "':'��',";
					}
				} else {
					jss += "'" + key + "':'"
							+ (data.get(key) == null ? "" : data.get(key))
							+ "',";
				}
			}
			if (jss.length() > 0 && jss.endsWith(",")) {
				jss = jss.substring(0, jss.length() - 1);
			}
			js += "{" + jss + "},";
		}
		if (js.length() > 0 && js.endsWith(",")) {
			js = "{success: true, rowCount:" + page.getTotalCount() + ",data:["
					+ js.substring(0, js.length() - 1) + "]}";
		}

		response.setCharacterEncoding("gbk");
		response.getWriter().write(js);
		response.getWriter().flush();

		return null;
	}

	public ActionForward toEdit(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		SystemMainTable sysMainTable = ms.findSystemMainTableByName("Notice");
		request.setAttribute("sysMainTable", sysMainTable);

		UserInfo userInfo = UserContext.getUserInfo();
		List columnsVisible = ms.findTableAllColumns(userInfo, sysMainTable,
				UserTableSetting.INPUT, true);
		request.setAttribute("columnsVisible", columnsVisible);

		String customerId = request.getParameter("id");
		Notice Notice = (Notice) bs.find(Notice.class, customerId);
		request.setAttribute("detail", Notice);

		Map map = metaDataManager.getEntityDataForEdit(Notice.class, customerId);
		HttpUtil.map2RequestAttri(request, map);
		/*MetaDataUtil.getMainColumnDatas(request, Notice, "Notice");
		MetaDataUtil.getExtendColumns(request, "Notice");
		MetaDataUtil.getEditExtendData(request, Notice, "Notice");*/
		// MetaDataUtil.doRequestData(request, response);
		return mapping.findForward("detail");
	}

	public ActionForward toAdd(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse httpServletResponse)
			throws Exception {

		List provider = bs.findAll(Notice.class);
		request.setAttribute("provider", provider);

		SystemMainTable sysMainTable = ms.findSystemMainTableByName("Notice");
		request.setAttribute("sysMainTable", sysMainTable);

		UserInfo userInfo = UserContext.getUserInfo();
		List columnsVisible = ms.findTableAllColumns(userInfo, sysMainTable,
				UserTableSetting.INPUT, true);
		request.setAttribute("columnsVisible", columnsVisible);

		/*MetaDataUtil.getMainColumns(request, "Notice");
		MetaDataUtil.getExtendColumns(request, "Notice");*/

		return mapping.findForward("detail");
	}

	public ActionForward save(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse httpServletResponse)
			throws Exception {
		// ����������
		// Notice Notice = (Notice) BeanUtil.getObject(request, Notice.class);
		String title = HttpUtil.ConverUnicode(request.getParameter("title"));
		String content = HttpUtil
				.ConverUnicode(request.getParameter("content"));
		String id = request.getParameter("id");

		title = HttpUtil.ConverHtml(title);
		content = HttpUtil.ConverHtml(content);

		String enabled = request.getParameter("enabled");

		Notice notice ;		
		if(id != null && !id.equalsIgnoreCase("")){
			notice = (Notice)bs.find(Notice.class, id, true);
			notice.setTitle(title);
			notice.setContent(content);
			notice.setEnabled(Integer.parseInt(enabled));
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
					"userInfo");
			notice.setModifyUser(userInfo);
			notice.setModifyDate(Calendar.getInstance().getTime());;
		}else{
			notice = new Notice();
			notice.setTitle(title);
			notice.setContent(content);
			notice.setEnabled(Integer.parseInt(enabled));
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
					"userInfo");
			notice.setCreateUser(userInfo);
			notice.setCreateDate(Calendar.getInstance().getTime());
		}
		
		// boolean isUpdate = Notice.getId()!=null;
		// ����������
		bs.save(notice);
		// ������չ����
		// MetaDataUtil.saveExtendData(request, Notice, "Notice", isUpdate);

		List noticeList = bs.findAll(Notice.class);
		request.getSession().setAttribute("nl", noticeList);
		PrintWriter out = null;
		try {
			out = httpServletResponse.getWriter();
			out.write("{success:" + true + "}");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			
		}finally{
			if(out != null)
				out.close();
		}
		return null;
		// return HttpUtil.redirect("noticeManage.do?methodCall=list");

	}

	public ActionForward remove(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse httpServletResponse)
			throws Exception {

		String[] ids = request.getParameterValues("id");
		if (ids != null && ids.length != 0) {
			for (int i = 0; i < ids.length; i++) {
				String noticeId = ids[i];
				//FIX BY DJ. ɾ������� remove����
//				bs.remove(Notice.class, noticeId);
				// MetaDataUtil.removeExtendData(noticeId, "Notice");
				metaDataManager.removeEntityData(Notice.class, noticeId);
			}
		}

		try {
			httpServletResponse.getWriter().write("{success:" + true + "}");
			httpServletResponse.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	// ��ѯ��ͷ
	public ActionForward head(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		SystemMainTable systemMainTable = ms
				.findSystemMainTableByName("Notice");
		request.setAttribute("sysMainTable", systemMainTable);

//		UserInfo userInfo = UserContext.getUserInfo();

		/*List columnsVisible = ms.findTableAllColumns(userInfo, systemMainTable,
				UserTableSetting.LIST, true);
		request.setAttribute("columnsVisible", columnsVisible);*/
		List columnsVisible =  metaDataManager.getUserColumnForList(Notice.class);
		request.setAttribute("columnsVisible", columnsVisible);

		String configStr = "";
		// configStr += "['id','id']";
		for (int i = 0; i < columnsVisible.size(); i++) {
			UserTableSetting uts = (UserTableSetting) columnsVisible.get(i);
			if (uts != null) {
				SystemMainTableColumn mainTableColumn = uts
						.getMainTableColumn();
				//if (mainTableColumn != null) {
					String strExt = "";
					String propertyName = mainTableColumn.getPropertyName();
					String columnTitle = mainTableColumn.getColumnCnName();
					strExt += "[\"" + propertyName + "\",\"" + columnTitle
							+ "\"]";
					if (configStr.equals("")) {
						configStr = strExt;
					} else {
						configStr += "," + strExt;
					}
//				} else {
//					SystemMainTableExtColumn extendTableColumn = uts
//							.getExtendTableColumn();
//					if (extendTableColumn != null) {
//						String strExt = "";
//						String propertyNameExt = extendTableColumn
//								.getPropertyName();
//						String columnTitleExt = extendTableColumn
//								.getExtTableColumnDispName();
//						strExt += "[\"" + propertyNameExt + "\",\""
//								+ columnTitleExt + "\"]";
//						if (configStr.equals("")) {
//							configStr = strExt;
//						} else {
//							configStr += "," + strExt;
//						}
//					}
//				}
			}
		}

		String headConfig = "[" + configStr + "]";
		response.setCharacterEncoding("gbk");
		response.getWriter().write(headConfig);
		response.getWriter().flush();
		return null;

	}

	/**
	 * ����֪ͨ
	 * 
	 * @Methods Name find
	 * @Create In Jul 4, 2008 By itnova
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward find(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse httpServletResponse)
			throws Exception {

		String noticeId = request.getParameter("id");
		
		Notice notice = (Notice) bs.find(Notice.class, noticeId, true);
		Long id = notice.getId();
		String title = notice.getTitle();
		String content = notice.getContent();
		//FIX BY DJ ��ӿ�ֵ�ж�.oracle�»᷵��null ����ҳ���쳣
		if(StringUtils.isEmpty(content)){
			content="";
		}
//		String contentHtml = HttpUtil.ConverHtml(content);
		//System.out.println("contentHtml :" + contentHtml);
		Integer enabled = notice.getEnabled();
		boolean checked = false;
		if (enabled == 1) {
			checked = true;
		} else {
			checked = false;
		}
		String createUserName = notice.getCreateUser().getRealName();
		String jsonString = "[{\"id\":" + id + ",\"title\":" + "\"" + title
				+ "\"" + ",\"content\":" + "\"" + content + "\""
				+ ",\"enabled\":" + checked + ",\"createUser\":" + "\""
				+ createUserName + "\"" + "}]";
		// String jsonString = new
		// JSONSerializer().exclude("createUser").serialize(notice);
		// JSONArray jsonObject = JSONArray.fromObject(notice);
		//System.out.println(jsonString);

		try {
			httpServletResponse.setCharacterEncoding("gbk");
			httpServletResponse.getWriter().write(
					"{success:" + true + ",notice:" + jsonString + "}");
			httpServletResponse.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * չʾ�����б�
	 * @Methods Name listNews
	 * @Create In May 21, 2010 By lee
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward listNews(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String js = "";
		Date date = new Date();
		List<NewNotice> noticList =super.getService().find(NewNotice.class, "auditflag", NewNotice.STATUS_FINISHED);// bs.findAll(NewNotice.class);//Ӧ�ü��ϲ�ѯ���� ʱ�䣬�������� ,״̬
		if (noticList != null && noticList.size()> 0) {
			for(NewNotice notice : noticList){
				Date edDate = notice.getEndDate();
				Date bgDate = notice.getBeginDate();
//				String context = HttpUtil.htmlEncode(notice.getContent());
				if(date.after(bgDate)&&date.before(edDate)){
					String jsonString = "{\"id\":" + notice.getId() + ",\"title\":" + "\"" + notice.getTitle()
					+ "\"" + ",\"createUser\":" + "\""
					+ notice.getCreateUser().getRealName() + "\"" + ",\"createDate\":" + "\""
					+ TimeTool.toStrDateFromUtilDateByFormat(notice.getCreateDate(),"yyyy��MM��dd��") 
					+ "\"" + ",\"content\":'" + notice.getContent()+"',\"creuser\":"+"\""+"  ������ �� "+"\""+ "},";
					js += jsonString;
				}
			}
			//modify by duxh in 2009-11-2 ---begin---
			//���ӶԿ��ַ������жϡ�
			if(js.length()!=0){
				js = "{success: true, rowCount:" + noticList.size() + ",data:["
						+ js.substring(0, js.length() - 1) + "]}";
			}else{
				js = "{success: true}";
			}
			//modify by duxh in 2009-11-2 ---end---
		}else{
			js = "{success: false}";
		}

		response.setCharacterEncoding("gbk");
		response.getWriter().write(js);
		response.getWriter().flush();

		return null;
	}
}
