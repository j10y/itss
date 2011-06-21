package com.zsgj.dcit.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.shopin.ldap.ws.client.SystemWSImpl;
import net.shopin.ldap.ws.client.SystemWSImplService;
import net.shopin.ldap.ws.client.User;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zsgj.dcit.util.PropertiesUtil;

public class UserAction extends ActionSupport {
	
	public static final String urlStr = "http://172.16.103.165/uac/services/sysService?wsdl";
	
	public static final SystemWSImpl port = SystemWSImplService.getPort(PropertiesUtil.getProperties("uac.wsdl", urlStr));
	
	public static final int pageSize = 20;

	public static final String RESULTKEY = "RESULT_KEY";
	
	public static final String lineSeparator = System.getProperty("line.separator");
	
	/**
	 * 通过用户uid查询用户详细信息
	 */
	public String getUserDetailByUid(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();	
		response.setContentType("text/html;charset=utf-8");
		String uid = request.getParameter("uid");
		User user = port.getUserDetailByUid(uid);
		String fileName = request.getSession().getServletContext().getRealPath("/")+"/images/userphoto/" + uid + ".jpg";
		FileOutputStream fos = null;
		try {
			if(user.getPhoto()!=null){
				File file = new File(fileName);
				if(!file.exists()){
					file.createNewFile();
				}
				fos = new FileOutputStream(file);
				fos.write(user.getPhoto());				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally{
			try {
				if(fos!=null)
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		StringBuffer json = new StringBuffer("{success:true,");
		json.append("dn:'" + user.getDn() + "',");
		json.append("uid:'" + user.getUid() + "',");
		json.append("displayName:'" + user.getDisplayName() + "',");
		json.append("description:'" + user.getDescription() + "',");
		json.append("deptName:'" + (user.getDeptName()!=null ? user.getDeptName() : "") + "',");
		json.append("password:'" + (user.getPassword()!=null ? user.getPassword() : "") + "',");
		json.append("cn:'" + user.getCn() + "',");
		json.append("sn:'" + (user.getSn()!=null ? user.getSn() : "") + "',");
		json.append("departmentNumber:'" + (user.getDepartmentNumber()!=null ? user.getDeptName() : "") + "',");
		json.append("title:'" + (user.getTitle()!=null ? user.getTitle() : "") + "',");
		json.append("mail:'" + (user.getMail()!=null ? user.getMail() : "") + "',");
		json.append("mobile:'" + (user.getMobile() !=null ? user.getMobile() : "") + "',");
		json.append("telephoneNumber:'" + (user.getTelephoneNumber() !=null ? user.getTelephoneNumber() : "") + "',");
		json.append("facsimileTelephoneNumber:'" + (user.getFacsimileTelephoneNumber()!=null ? user.getFacsimileTelephoneNumber() : "") + "',");
		json.append("userType:'" + (user.getUserType() !=null ? user.getUserType() : "")+ "'}");
		Writer out = null;
		try {
			out = response.getWriter();
//			out.write(json.toString().replaceAll(lineSeparator, "<br/>"));			
			out.write(json.toString());			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 通过用户名称或账号查询用户列表 
	 * @return
	 */
	public String getUserListByParam(){
//		StringBuffer json = new StringBuffer("{success:true,data:[");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		List<net.shopin.ldap.ws.client.User> userList = null;
		Map result = new HashMap();
		
		response.setContentType("text/html;charset=utf-8");
		
		String uidOrName = request.getParameter("username");
		try {
			//get请求 TOMCAT 默认ISO-8859-1
			uidOrName = new String(uidOrName.getBytes("ISO-8859-1"), "utf-8");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		String firstFlag = request.getParameter("firstFlag");//第一次进入1，非为0
		int currentPage = request.getParameter("currentPage") != null ? new Integer(request.getParameter("currentPage")) : 1;//当前页
		
		int start = pageSize*(currentPage-1);//从哪一个条数开始		
		
		HttpSession session = request.getSession();
				
		if(firstFlag.equals("1")||session.getAttribute(RESULTKEY)==null){
			userList = port.findUserListByParam(uidOrName);
			session.setAttribute(RESULTKEY, userList);
		}else{
			userList = (List)session.getAttribute(RESULTKEY);
		}
		
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("totalCount", userList.size());
		request.setAttribute("totalPage", (userList.size()+pageSize-1)/pageSize);
		request.setAttribute("data", (start + pageSize > userList.size()) ? userList.subList(start, userList.size()) : userList.subList(start, start + pageSize));
		
		return "userList";

		
//		for(int i=0; i<userList.size(); i++){
//			json.append("{");
//			json.append("dn:'" + userList.get(i).getDn() + "'");
//			json.append("uid:'" + userList.get(i).getUid() + "'");
//			json.append("password:'" + userList.get(i).getPassword() + "'");
//			json.append("cn:'" + userList.get(i).getCn() + "'");
//			json.append("sn:'" + userList.get(i).getSn() + "'");
//			json.append("departmentNumber:'" + userList.get(i).getDepartmentNumber() + "'");
//			json.append("title:'" + userList.get(i).getTitle() + "'");
//			json.append("mail:'" + userList.get(i).getMail() + "'");
//			json.append("mobile:'" + userList.get(i).getMobile() + "'");
//			json.append("facsimileTelephoneNumber:'" + userList.get(i).getFacsimileTelephoneNumber() + "'");
//			json.append("userType:'" + userList.get(i).getUserType() + "'");
//			//照片自动生成
//			if(i<userList.size()-1){
//				json.append("},");
//			}else{
//				json.append("}");
//			}
//			
//		}
//		json.append("],rowCount:'" + userList.size() + "'}");
//		Writer out = null;
//		try {
//			out = response.getWriter();
//			out.write(json.toString());
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally{
//			try {
//				out.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		return null;
	}
	
}
