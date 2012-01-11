package net.shopin.ldap.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.shopin.ldap.dao.RoleDao;
import net.shopin.ldap.entity.Role;
import net.shopin.ldap.entity.User;
import net.shopin.ldap.entity.UserGroup;
import net.shopin.util.SpringContextUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.ldap.NameAlreadyBoundException;
import org.springframework.ldap.samples.utils.LdapTreeBuilder;


public class RoleServlet extends HttpServlet {
	
	LdapTreeBuilder ldapTreeBuilder = (LdapTreeBuilder) SpringContextUtils.getBean("ldapTreeBuilder");
	RoleDao roleDao = (RoleDao) SpringContextUtils.getBean("roleDao");

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
				Role role = new Role();
				role.setCn(req.getParameter("cn"));
				role.setDisplayName(req.getParameter("displayName"));
				role.setDescription(req.getParameter("description"));
				role.setStatus((req.getParameter("status") == null || "".equals(req.getParameter("status"))) ? 0 : Integer.parseInt(req.getParameter("status")));
				roleDao.create(role);
			} else if(methodCall.equalsIgnoreCase("modify")) {//修改
				Role oldRole = roleDao.findByDN(req.getParameter("dn"));
				Role role = new Role();
				role.setDn(req.getParameter("dn"));
				role.setCn(req.getParameter("cn"));
				role.setDisplayName(req.getParameter("displayName"));
				role.setDescription(req.getParameter("description"));
				role.setStatus((req.getParameter("status") == null || "".equals(req.getParameter("status"))) ? 0 : Integer.parseInt(req.getParameter("status")));
				role.setRoleOccupant(oldRole.getRoleOccupant());
				roleDao.update(role);
			} else if(methodCall.equalsIgnoreCase("delete")) {//删除
				String roleDN = req.getParameter("roleDN");
				roleDao.deleteByDN(roleDN);
			} else if(methodCall.equalsIgnoreCase("getList")) {
				List<Role> list = roleDao.findRolesByParam("");
				json = new StringBuffer("{success:true,result:[");
				for(Role ug : list) {
					json.append("{'dn':'" + ug.getDn() + "',")
							.append("'cn':'" + ug.getCn() + "',")
							.append("'displayName':'" + ug.getDisplayName() + "'},");
				}
				if(list.size() > 0) {
					json.deleteCharAt(json.length() - 1);
				}
				json.append("]}");
			} else if (methodCall.equalsIgnoreCase("getDetailByRoleDN")) {
				String roleDN = req.getParameter("roleDN");
				Role ug = roleDao.findByDN(roleDN);
				json = new StringBuffer("{success:true,data:{");
				json.append("'dn':'" + ug.getDn() + "'")
					.append(",'cn':'" + ug.getCn() + "'")
					.append(",'displayName':'" + ug.getDisplayName() + "'")
					.append(",'description':'" + (ug.getDescription() == null ? "" : ug.getDescription()) + "'")
					.append(",'status':'" + (ug.getStatus() != null ? ug.getStatus() : "") + "'")
					.append("}}");
			} else if(methodCall.equalsIgnoreCase("findByUserDN")) {//根据用户DN查找
				String userDN = req.getParameter("userDN");
				List<Role> list = roleDao.findRolesByParam("", userDN, true);
				json = new StringBuffer("{success:true,result:[");
				for(Role ug : list) {
					json.append("{'dn':'" + ug.getDn() + "',")
							.append("'cn':'" + ug.getCn() + "',")
							.append("'displayName':'" + ug.getDisplayName() + "'},");
				}
				if(list.size() > 0) {
					json.deleteCharAt(json.length() - 1);
				}
				json.append("]}");
			} else if(methodCall.equalsIgnoreCase("combo")) {
				String param = req.getParameter("param");
				List<Role> list = roleDao.findRolesByParam(param);
				json = new StringBuffer("[");
				for(Role ug : list) {
					json.append("['" + ug.getDn() + "','" + ug.getDisplayName() + "'],");
				}
				if(list.size() > 0) {
					json.deleteCharAt(json.length() - 1);
				}
				json.append("]");
			} else if(methodCall.equalsIgnoreCase("listMembers")) {
				String roleDN = req.getParameter("roleDN");
				List<User> userList = roleDao.listMembers(roleDN);
				json = new StringBuffer("{success:true,result:[");
				for(User user : userList) {
					json.append("{'dn':'" + user.getDn() + "',")
							.append("'uid':'" + user.getUid() + "',")
							.append("'displayName':'" + user.getDisplayName() + "'},");
				}
				if(userList.size() > 0) {
					json.deleteCharAt(json.length() - 1);
				}
				json.append("]}");
			} else if(methodCall.equalsIgnoreCase("relatUsers")) {
				//绑定用户组与组员的关系
				String roleDN = req.getParameter("roleDN");
				String roleOccupants = req.getParameter("roleOccupants");
				String [] roleOccupantArray = StringUtils.isNotEmpty(roleOccupants) ? roleOccupants.split("#") : null;
				Role role = roleDao.findByDN(roleDN);
				role.setRoleOccupant(roleOccupantArray);
				roleDao.update(role);
				
			}
		} catch(NameAlreadyBoundException e) {
			e.printStackTrace();
			json = new StringBuffer("{success:false,msg:'输入的角色信息的cn已经存在！'}");
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
