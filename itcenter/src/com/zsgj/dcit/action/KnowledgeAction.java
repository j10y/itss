package com.zsgj.dcit.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zsgj.dcit.entity.Knowledge;
import com.zsgj.dcit.entity.Notice;
import com.zsgj.dcit.service.KnowledgeService;
import com.zsgj.dcit.util.Pagination;

public class KnowledgeAction extends ActionSupport {

	private KnowledgeService knowledgeService;
	
	/**
	 * 获得操作手册及常见问题等栏目内的信息
	 * @Class Name getInfosAction
	 * @Author zhangzy
	 * @Create In 31 5, 2010
	 */
	public void  getInfosAction(){
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();	
			String strContextpath = request.getContextPath();
			
			String strInfoLength = request.getParameter("infoLength");
			String strOffset = request.getParameter("offset");
			String strLength = request.getParameter("length");
			String strColumnType = request.getParameter("columnType");			
			int offset = Integer.parseInt(strOffset);
			int length = Integer.parseInt(strLength);
			
			List list = knowledgeService.getInfosService(offset, length, Integer.parseInt(strInfoLength),Long.parseLong(strColumnType));
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			if(null != list){
				for(int i = 0;i<list.size();i++){
					Object[] obj = (Object[]) list.get(i);
					Knowledge knowledge = (Knowledge) obj[0];
					Date date = knowledge.getCreateDate();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String createDate = sdf.format(date);
					if(i!=0){
						sb.append(",");
					}
					sb.append("{");
					sb.append("'id':'"+knowledge.getId()+"',");
					sb.append("'summary':'"+knowledge.getSummary()+"',");
					sb.append("'createDate':'"+createDate+"',");
					sb.append("'linkUrl':'"+strContextpath+"/knowledgeAction_getContentInfoAction.action?id="+knowledge.getId()+"'");
					sb.append("}");
				}
			}
			sb.append("]");
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
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 获得操作手册及常见问题等栏目详细信息
	 * @Class Name getContentInfoAction
	 * @Author zhangzy
	 * @Create In 1 , 6, 2010
	 */	
	public String getContentInfoAction(){
		
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();	
			HttpSession session= request.getSession();
			
			String strId = request.getParameter("id");
			Knowledge knowledge = knowledgeService.updateAndGetContentInfosService(Long.parseLong(strId));	
			String knowledgeStr = knowledge.getResolvent();
			knowledgeStr = knowledgeStr.replace("\\r\\n","\r\n").replace("\\\\","\\").replace("\\\'","\'").replace("\\t","\t").replace("\\\"","\"");
			knowledge.setResolvent(knowledgeStr);
			request.setAttribute("knowledge", knowledge);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "contentPage";
	}
	
	/**
	 * 获得操作手册及常见问题等栏目列表信息
	 * @Class Name getListInfoAction
	 * @Author zhangzy
	 * @Create In 1 , 6, 2010
	 */	
	public String getListInfoAction(){
		
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String strInfoLength = request.getParameter("infoLength");
			String columnType = request.getParameter("columnType");				
			String strPageSize = request.getParameter("pageSize");
			String strPageNum = request.getParameter("pageNum");
			if(strPageNum==null){
				strPageNum = "1";
			}
			Pagination pagination = new Pagination(Integer.valueOf(strPageSize),Integer.valueOf(strPageNum));
			
			 pagination= knowledgeService.getListInfoService(pagination,Integer.parseInt(strInfoLength),Long.parseLong(columnType));
			request.setAttribute("pagination", pagination);
			request.setAttribute("columnType", columnType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "listPage";
	}
	/**
	 * 获得操作手册及常见问题等栏目搜索信息
	 * @Class Name getSearchInfoAction
	 * @Author zhangzy
	 * @Create In 1 , 6, 2010
	 */	
	public String getSearchInfoAction(){
		
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String strInfoLength = request.getParameter("infoLength");
			String columnType = request.getParameter("columnType");				
			String strPageSize = request.getParameter("pageSize");
			String strPageNum = request.getParameter("pageNum");
			String strKeyValue = request.getParameter("keyValue");
			strKeyValue = new String(strKeyValue.getBytes("iso-8859-1"),"UTF-8");
			
			if(strPageNum==null){
				strPageNum = "1";
			}
			Pagination pagination = new Pagination(Integer.valueOf(strPageSize),Integer.valueOf(strPageNum));
			
			pagination= knowledgeService.getSearchInfoService(pagination,Integer.parseInt(strInfoLength),Long.parseLong(columnType), strKeyValue);
			request.setAttribute("pagination", pagination);
			request.setAttribute("columnType", columnType);
			request.setAttribute("infoLength", strInfoLength);
			request.setAttribute("pageSize", strPageSize);
			request.setAttribute("keyValue", strKeyValue);
			request.setAttribute("redKeyValue", "<font color=red>"+strKeyValue+"</font>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "searchPage";
	}
	public KnowledgeService getKnowledgeService() {
		return knowledgeService;
	}
	public void setKnowledgeService(KnowledgeService knowledgeService) {
		this.knowledgeService = knowledgeService;
	}


	
	
	
}
