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
import com.zsgj.dcit.entity.KnowFile;
import com.zsgj.dcit.service.KnowFileService;
import com.zsgj.dcit.util.Pagination;

public class KnowFileAction extends ActionSupport {

	private KnowFileService knowFileService;
	
	/**
	 * 获得IT管理制度、IT管理工作体系、IT服务报告、业务支持等栏目内的信息
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
			List<KnowFile> list = knowFileService.getInfosService(offset, length, Integer.parseInt(strInfoLength),Long.valueOf(strColumnType));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
			Date currentDate = new Date();
			String strCurrentDate = sdf.format(currentDate);
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			if(null != list){
				int i = 0;
				for(KnowFile knowFile:list){		
					if(i!=0){
						sb.append(",");
					}
					String strCreateDate = sdf.format(knowFile.getCreateDate());
					String strIsToday = "";
					if(strCurrentDate.equals(strCreateDate)){//如果信息的发布日期和系统当前日期相同，就显示“今天”
						strIsToday="[今天]";
					}
					sb.append("{");
					sb.append("'id':'"+knowFile.getId()+"',");
					sb.append("'name':'"+knowFile.getName()+"',");
					sb.append("'createDate':'"+knowFile.getCreateDate()+"',");
					sb.append("'isToday':'"+strIsToday+"',");
					sb.append("'linkUrl':'"+strContextpath+"/knowFileAction_getContentInfoAction.action?id="+knowFile.getId()+"'");
					sb.append("}");
					i++;
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
	 * 获得IT管理制度、IT管理工作体系、IT服务报告、业务支持等栏目详细信息
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
			KnowFile knowFile = knowFileService.updateAndGetContentInfosService(Long.parseLong(strId));	
			String konwFileStr = knowFile.getDescn();
			konwFileStr = konwFileStr.replace("\\r\\n","\r\n").replace("\\\\","\\").replace("\\\'","\'").replace("\\t","\t").replace("\\\"","\"");
			knowFile.setDescn(konwFileStr);
			request.setAttribute("knowFile", knowFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "contentPage";
	}
	
	/**
	 * 获得IT管理制度、IT管理工作体系、IT服务报告、业务支持等栏目列表信息
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
			Pagination<KnowFile> pagination = new Pagination<KnowFile>(Integer.valueOf(strPageSize),Integer.valueOf(strPageNum));
			
			 pagination= knowFileService.getListInfoService(pagination,Integer.parseInt(strInfoLength),Long.parseLong(columnType));
			request.setAttribute("pagination", pagination);
			request.setAttribute("columnType", columnType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "listPage";
	}
	/**
	 * 获得IT管理制度、IT管理工作体系、IT服务报告、业务支持等栏目搜索信息
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
			
			Pagination<KnowFile> pagination = new Pagination<KnowFile>(Integer.valueOf(strPageSize),Integer.valueOf(strPageNum));
			
			pagination= knowFileService.getSearchInfoService(pagination,Integer.parseInt(strInfoLength),Long.parseLong(columnType),strKeyValue);
			request.setAttribute("pagination", pagination);
			request.setAttribute("columnType", columnType);
			request.setAttribute("infoLength", strInfoLength);
			request.setAttribute("pageSize", strPageSize);
			request.setAttribute("keyValue", strKeyValue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "searchPage";
	}
	public KnowFileService getKnowFileService() {
		return knowFileService;
	}
	public void setKnowFileService(KnowFileService knowFileService) {
		this.knowFileService = knowFileService;
	}
	
}
