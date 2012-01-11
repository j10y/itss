package net.shopin.ldap.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.shopin.ldap.dao.SystemDao;
import net.shopin.ldap.entity.Role;
import net.shopin.ldap.entity.System;
import net.shopin.ldap.entity.User;
import net.shopin.util.SpringContextUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.ldap.NameAlreadyBoundException;
import org.springframework.ldap.samples.utils.LdapTreeBuilder;


public class SystemServlet extends HttpServlet {
	
	LdapTreeBuilder ldapTreeBuilder = (LdapTreeBuilder) SpringContextUtils.getBean("ldapTreeBuilder");
	SystemDao systemDao = (SystemDao) SpringContextUtils.getBean("systemDao");

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		StringBuffer json = new StringBuffer("{success:true}");
		try {
			String methodCall = req.getParameter("methodCall");
			if(methodCall.equalsIgnoreCase("add")) {//新增
				System system = new System();
				system.setCn(req.getParameter("cn"));
				system.setDisplayName(req.getParameter("displayName"));
				system.setDescription(req.getParameter("description"));
				system.setStatus((req.getParameter("status") == null || "".equals(req.getParameter("status"))) ? 0 : Integer.parseInt(req.getParameter("status")));
				systemDao.create(system);
			} else if(methodCall.equalsIgnoreCase("modify")) {//修改
				System system = new System();
				system.setDn(req.getParameter("dn"));
				system.setCn(req.getParameter("cn"));
				system.setDisplayName(req.getParameter("displayName"));
				system.setDescription(req.getParameter("description"));
				system.setStatus((req.getParameter("status") == null || "".equals(req.getParameter("status"))) ? 0 : Integer.parseInt(req.getParameter("status")));
				systemDao.update(system);
			} else if(methodCall.equalsIgnoreCase("delete")) {//删除
				String systemDN = req.getParameter("systemDN");
				systemDao.deleteByDN(systemDN);
			} else if(methodCall.equalsIgnoreCase("getDetailBySystemDN")) {
				String systemDN = req.getParameter("systemDN");
				System system = systemDao.findByDN(systemDN);
				json = new StringBuffer("{success:true,data:{");
				json.append("'dn':'" + system.getDn() + "'")
					.append(",'cn':'" + system.getCn() + "'")
					.append(",'displayName':'" + system.getDisplayName() + "'")
					.append(",'description':'" + (system.getDescription() == null ? "" : system.getDescription()) + "'")
					.append(",'status':'" + (system.getStatus() != null ? system.getStatus() : "") + "'")
					.append("}}");
			} else if(methodCall.equalsIgnoreCase("getList")) {
				List<System> list = systemDao.findSystemsByParam("");
				json = new StringBuffer("{success:true,result:[");
				for(System system : list) {
					json.append("{'dn':'" + system.getDn() + "',")
							.append("'cn':'" + system.getCn() + "',")
							.append("'displayName':'" + system.getDisplayName() + "'},");
				}
				if(list.size() > 0) {
					json.deleteCharAt(json.length() - 1);
				}
				json.append("]}");
			} else if(methodCall.equalsIgnoreCase("combo")) {
				String param = req.getParameter("param");
				List<System> list = systemDao.findSystemsByParam(param);
				json = new StringBuffer("[");
				for(System system : list) {
					json.append("['" + system.getDn() + "','" + system.getDisplayName() + "'],");
				}
				if(list.size() > 0) {
					json.deleteCharAt(json.length() - 1);
				}
				json.append("]");
			} else if(methodCall.equalsIgnoreCase("listMembers")) {
				String systemDN = req.getParameter("systemDN");
				List<Role> roleList = systemDao.listMembers(systemDN);
				json = new StringBuffer("{success:true,result:[");
				for(Role role : roleList) {
					json.append("{'dn':'" + role.getDn() + "',")
							.append("'displayName':'" + role.getDisplayName() + "'},");
				}
				if(roleList.size() > 0) {
					json.deleteCharAt(json.length() - 1);
				}
				json.append("]}");
			} else if(methodCall.equalsIgnoreCase("relatRoles")) {
				//绑定用户组与组员的关系
				String systemDN = req.getParameter("systemDN");
				String systemOccupants = req.getParameter("systemOccupants");
				String [] systemOccupantArray = StringUtils.isNotEmpty(systemOccupants) ? systemOccupants.split("#") : null;
				System system = systemDao.findByDN(systemDN);
				system.setSystemOccupant(systemOccupantArray);
				systemDao.update(system);
				
			}
		} catch(NameAlreadyBoundException e) {
			e.printStackTrace();
			json = new StringBuffer("{success:false,msg:'输入的职务信息的cn已经存在！'}");
		} catch(Exception e) {
			e.printStackTrace();
			json = new StringBuffer("{success:false,msg:'服务器端异常，请联系管理员！'}");
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
