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
import com.zsgj.dcit.entity.Notice;
import com.zsgj.dcit.service.NoticeService;
import com.zsgj.dcit.util.Pagination;

public class NoticeAction extends ActionSupport {

	private NoticeService noticeService;
	
	/**
	 * ���IT���桢ITС��ʿ��IT��ѵ����Ŀ�ڵ���Ϣ
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
			
			List<Notice> list = noticeService.getInfosService(offset, length, Integer.parseInt(strInfoLength),Long.valueOf(strColumnType));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
			Date currentDate = new Date();
			String strCurrentDate = sdf.format(currentDate);
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			if(null != list){
				int i = 0;
				for(Notice notice:list){		
					if(i!=0){
						sb.append(",");
					}
					String strCreateDate = sdf.format(notice.getCreateDate());
					String strIsToday = "";
					if(strCurrentDate.equals(strCreateDate)){//�����Ϣ�ķ������ں�ϵͳ��ǰ������ͬ������ʾ�����족
						strIsToday="[����]";
					}
					sb.append("{");
					sb.append("'id':'"+notice.getId()+"',");
					sb.append("'title':'"+notice.getTitle()+"',");
					sb.append("'createDate':'"+notice.getCreateDate()+"',");
					sb.append("'isToday':'"+strIsToday+"',");
					sb.append("'linkUrl':'"+strContextpath+"/noticeAction_getContentInfoAction.action?id="+notice.getId()+"'");
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
	 * ���IT���桢ITС��ʿ��IT��ѵ����Ŀ��ϸ��Ϣ
	 * @Class Name getContentInfoAction
	 * @Author zhangzy
	 * @Create In 1 , 6, 2010
	 */	
	public String getContentInfoAction(){
		
		HttpServletRequest request;
		Notice notice;
		try {
			request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();	
			HttpSession session= request.getSession();
			String strId = request.getParameter("id");
			notice = noticeService.updateAndGetContentInfosService(Long.parseLong(strId));
			String noticeStr = notice.getContent();
			noticeStr = noticeStr.replace("\\r\\n","\r\n").replace("\\\\","\\").replace("\\\'","\'").replace("\\t","\t").replace("\\\"","\"");
			notice.setContent(noticeStr);
			request.setAttribute("notice", notice);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return "contentPage";
	}
	
	/**
	 * ���IT���桢ITС��ʿ��IT��ѵ����Ŀ�б���Ϣ
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
			Pagination<Notice> pagination = new Pagination<Notice>(Integer.valueOf(strPageSize),Integer.valueOf(strPageNum));
			
			 pagination= noticeService.getListInfoService(pagination,Integer.parseInt(strInfoLength),Long.parseLong(columnType));
			request.setAttribute("pagination", pagination);
			request.setAttribute("columnType", columnType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "listPage";
	}
	
	/**
	 * ���IT���桢ITС��ʿ��IT��ѵ����Ŀ������Ϣ
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
			Pagination<Notice> pagination = new Pagination<Notice>(Integer.valueOf(strPageSize),Integer.valueOf(strPageNum));
			
			pagination= noticeService.getSearchInfoService(pagination, Integer.valueOf(strInfoLength), Long.valueOf(columnType), strKeyValue);
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

	public NoticeService getNoticeService() {
		return noticeService;
	}

	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}
	
	
	
}
