package net.shopin.ldap.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.shopin.ldap.dao.UserDao;
import net.shopin.ldap.entity.User;
import net.shopin.util.SpringContextUtils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.ldap.NameAlreadyBoundException;
import org.springframework.ldap.samples.utils.LdapTreeBuilder;


public class UserServlet extends HttpServlet {
	
	LdapTreeBuilder ldapTreeBuilder = (LdapTreeBuilder) SpringContextUtils.getBean("ldapTreeBuilder");
	UserDao userDao = (UserDao) SpringContextUtils.getBean("userDao");

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		// TODO Auto-generated method stub
		String realPath = null;
		String filePath = null;
		byte [] photo = new byte[1024*1024>>>1];//0.5M
		String methodCall = req.getParameter("methodCall");
		
		StringBuffer json = new StringBuffer("{success:true}");
		User user = new User();
		user.setDn(req.getParameter("dn"));
		user.setUid(req.getParameter("uid"));
		user.setPassword(req.getParameter("password"));
		user.setCn(req.getParameter("cn"));
		user.setSn(req.getParameter("sn"));
		user.setDepartmentNumber(req.getParameter("departmentNumber"));
		user.setTitle(req.getParameter("title"));
		user.setMail(req.getParameter("mail"));
		user.setTelephoneNumber(req.getParameter("telephoneNumber"));
		user.setMobile(req.getParameter("mobile"));
		user.setFacsimileTelephoneNumber(req.getParameter("facsimileTelephoneNumber"));
		user.setUserType(req.getParameter("userType"));
		
		//add by awen for add photo to user on 2001-05-16 begin
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			boolean isMutipart = ServletFileUpload.isMultipartContent(req);
			if(isMutipart){
				ServletFileUpload upload = new ServletFileUpload(factory);
				List fileItems = upload.parseRequest(req);
				Iterator iterator = fileItems.iterator();
				while(iterator.hasNext()){
					FileItem fi = (FileItem)iterator.next();
					if (!fi.isFormField()) {
						String fileName = fi.getName();
						if(fileName==null || "".equals(fileName)) break;
						String suffix = fileName.substring(fileName.lastIndexOf("."));
			            String systemFileName = "upload-" + System.currentTimeMillis() + suffix;
			            if(!methodCall.equalsIgnoreCase("import")){
			            	filePath = "../../../" + "userPhoto" + "/" + systemFileName;
			            }else{
			            	filePath = "../../../" + "upload" + "/" + systemFileName;	
			            }
			            
			            realPath = req.getSession().getServletContext().getRealPath("/") + filePath;
			            File uploadedFile = new File(realPath);
			            fi.write(uploadedFile);
			            //FileInputStream fis = new FileInputStream(filePath);
					}else{
						String name = fi.getFieldName();
						String value = fi.getString();
						//在enctype="multipart/form-data") 情况下，form表单的数据通过流传递，且在Tomcat服务器下，默认使用ISO-8859-1编码
						value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
						BeanUtils.setProperty(user, name, value);
					}
				}
			}
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//add by awen for add photo to user on 2001-05-16 end
		if(!methodCall.equalsIgnoreCase("import") && filePath != null && !"".equals(realPath)){
			FileInputStream fis = new FileInputStream(realPath);
			fis.read(photo);
			fis.close();
			user.setPhoto(photo);			
		}
		
		try {
			if(methodCall.equalsIgnoreCase("add")){
				userDao.create(user);
			}else if(methodCall.equalsIgnoreCase("delete")){
				userDao.delete(user);
			}else if(methodCall.equalsIgnoreCase("modify")){
				userDao.update(user);
			}else if(methodCall.equalsIgnoreCase("getDetail")){
				String uid = null;
				String userType = "1";//员工
				if(user.getUid()==null){
					uid = user.getDn().substring(user.getDn().indexOf("=")+1, user.getDn().indexOf(","));
				}
				if(user.getUserType()==null){
					userType = user.getDn().contains("ou=employees") ? "1" : (user.getDn().contains("ou=customers") ? "2" : "3");
				}
				User userDetail = userDao.findByPrimaryKey(uid, userType);
				json = new StringBuffer("{success:true");
				json.append(",dn:'" + (userDetail.getDn() != null ? userDetail.getDn() : "") + "'")
					.append(",uid:'" + (userDetail.getUid() != null ? userDetail.getUid() : uid) + "'")
					.append(",password:'" + (userDetail.getPassword() != null ? userDetail.getPassword() : "") + "'")
					.append(",cn:'" + (userDetail.getCn() != null ? userDetail.getCn() : "") + "'")
					.append(",sn:'" + (userDetail.getSn() != null ? userDetail.getSn() : "") + "'")
					.append(",departmentNumber:'" + (userDetail.getDepartmentNumber() != null ? userDetail.getDepartmentNumber() : "") + "'")
					.append(",title:'" + (userDetail.getTitle() != null ? userDetail.getTitle() : "") + "'")
					.append(",mail:'" + (userDetail.getMail() != null ? userDetail.getMail() : "") + "'")
					.append(",mobile:'" + (userDetail.getMobile() != null ? userDetail.getMobile() : "") + "'")
					.append(",deptNo:'" + (userDetail.getDepartmentNumber() != null ? userDetail.getDepartmentNumber() : "") + "'")
					.append(",deptName:'" + (userDetail.getDeptName() != null ? userDetail.getDeptName() : "") + "'")
					.append(",telephoneNumber:'" + (userDetail.getTelephoneNumber() != null ? userDetail.getTelephoneNumber() : "") + "'")
					.append(",facsimileTelephoneNumber:'" + (userDetail.getFacsimileTelephoneNumber() != null ? userDetail.getFacsimileTelephoneNumber() : "") + "'")
					.append(",userType:'" + (userDetail.getUserType() != null ? userDetail.getUserType() : user.getDn().contains("ou=employees") ? "1" : (user.getDn().contains("ou=customers") ? "2" : "3")) + "'")
					.append("}");
			}else if(methodCall.equalsIgnoreCase("getDetailByUid")){
				String uid = req.getParameter("uid");
				User userDetail = userDao.findByPrimaryKey(uid);
				json = new StringBuffer("{success:true");
				json.append(",dn:'" + (userDetail.getDn() != null ? userDetail.getDn() : "") + "'")
					.append(",uid:'" + (userDetail.getUid() != null ? userDetail.getUid() : uid) + "'")
					.append(",password:'" + (userDetail.getPassword() != null ? userDetail.getPassword() : "") + "'")
					.append(",cn:'" + (userDetail.getCn() != null ? userDetail.getCn() : "") + "'")
					.append(",sn:'" + (userDetail.getSn() != null ? userDetail.getSn() : "") + "'")
					.append(",departmentNumber:'" + (userDetail.getDepartmentNumber() != null ? userDetail.getDepartmentNumber() : "") + "'")
					.append(",title:'" + (userDetail.getTitle() != null ? userDetail.getTitle() : "") + "'")
					.append(",mail:'" + (userDetail.getMail() != null ? userDetail.getMail() : "") + "'")
					.append(",mobile:'" + (userDetail.getMobile() != null ? userDetail.getMobile() : "") + "'")					
					.append(",deptNo:'" + (userDetail.getDepartmentNumber() != null ? userDetail.getDepartmentNumber() : "") + "'")
					.append(",deptName:'" + (userDetail.getDeptName() != null ? userDetail.getDeptName() : "") + "'")
					.append(",telephoneNumber:'" + (userDetail.getTelephoneNumber() != null ? userDetail.getTelephoneNumber() : "") + "'")
					.append(",facsimileTelephoneNumber:'" + (userDetail.getFacsimileTelephoneNumber() != null ? userDetail.getFacsimileTelephoneNumber() : "") + "'")
					.append(",userType:'" + (userDetail.getUserType() != null ? userDetail.getUserType() : userDetail.getDn().contains("ou=employees") ? "1" : (userDetail.getDn().contains("ou=customers") ? "2" : "3")) + "'")
					.append("}");
			}else if(methodCall.equalsIgnoreCase("import")){
				String msg = null;
				//try {					
					/*DiskFileItemFactory factory = new DiskFileItemFactory();
					boolean isMutipart = ServletFileUpload.isMultipartContent(req);
					if(isMutipart){
						ServletFileUpload upload = new ServletFileUpload(factory);
						List fileItems = upload.parseRequest(req);
						Iterator iterator = fileItems.iterator();
						while(iterator.hasNext()){
							FileItem fi = (FileItem)iterator.next();
							if (!fi.isFormField()) {
								String fileName = fi.getName();
								String suffix = fileName.substring(fileName.lastIndexOf("."));
				                //String systemFileName = "upload-" + System.currentTimeMillis() + ".xls";
				                String systemFileName = "upload-" + System.currentTimeMillis() + suffix;
				                String filePath = "../../../" + "upload" + "/" + systemFileName;
				                String realPath = req.getSession().getServletContext().getRealPath("/") + filePath;
				                File uploadedFile = new File(realPath);
				                fi.write(uploadedFile);
				                try {
									//导入人员信息
				                	msg = userDao.importUsersFromFile(realPath);
								} catch (Exception e) {
									msg = e.getMessage().substring(e.getMessage().lastIndexOf(":")+1);
							        e.printStackTrace();
							        json = new StringBuffer("{success:false,msg:'" + msg.trim() + "'}");
								}
								json = new StringBuffer("{success:true,msg:'" + msg.trim() + "'}");
							}
						}
					}*/
				//} catch (FileUploadException e) {
					// TODO Auto-generated catch block
				//	e.printStackTrace();
					//msg = userDao.importUsersFromFile(realPath);
				//} catch (Exception e) {
					// TODO Auto-generated catch block
				//	e.printStackTrace();
				//} 
				try {
					//导入人员信息
                	msg = userDao.importUsersFromFile(realPath);
				} catch (Exception e) {
					msg = e.getMessage().substring(e.getMessage().lastIndexOf(":")+1);
			        e.printStackTrace();
				}
		        json = new StringBuffer("{success:false,msg:'" + msg.trim() + "'}");
			}
		}catch(NameAlreadyBoundException e){
			e.printStackTrace();
			json = new StringBuffer("{success:false,msg:'uid="+user.getUid()+"的用户已经存在'}");
		}catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = new StringBuffer("{success:false,msg:'服务器端异常'}");
		}
		try {
			resp.setContentType("text/html;charset=utf-8");
			resp.setCharacterEncoding("utf-8");
			PrintWriter pw = resp.getWriter();
			pw.write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
