package net.shopin.ldap.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.shopin.ldap.dao.GroupDao;
import net.shopin.ldap.entity.UserGroup;
import net.shopin.util.SpringContextUtils;

import org.springframework.ldap.samples.utils.LdapTreeBuilder;


public class GroupServlet extends HttpServlet {
	
	LdapTreeBuilder ldapTreeBuilder = (LdapTreeBuilder) SpringContextUtils.getBean("ldapTreeBuilder");
	GroupDao groupDao = (GroupDao) SpringContextUtils.getBean("groupDao");

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
				UserGroup userGroup = new UserGroup();
				userGroup.setCn(req.getParameter("cn"));
				userGroup.setDisplayName(req.getParameter("displayName"));
				userGroup.setDescription(req.getParameter("description"));
				userGroup.setStatus((req.getParameter("status") == null || "".equals(req.getParameter("status"))) ? 0 : Integer.parseInt(req.getParameter("status")));
				groupDao.create(userGroup);
			} else if(methodCall.equalsIgnoreCase("modify")) {//修改
				UserGroup oldUg = groupDao.findByRDN(req.getParameter("rdn"));
				UserGroup userGroup = new UserGroup();
				userGroup.setRdn(req.getParameter("rdn"));
				userGroup.setCn(req.getParameter("cn"));
				userGroup.setDisplayName(req.getParameter("displayName"));
				userGroup.setDescription(req.getParameter("description"));
				userGroup.setStatus((req.getParameter("status") == null || "".equals(req.getParameter("status"))) ? 0 : Integer.parseInt(req.getParameter("status")));
				userGroup.setMembers(oldUg.getMembers());
				groupDao.update(userGroup);
			} else if(methodCall.equalsIgnoreCase("delete")) {//删除
				String groupRDN = req.getParameter("groupRDN");
				groupDao.deleteByRDN(groupRDN);
			} else if(methodCall.equalsIgnoreCase("getList")) {
				List<UserGroup> list = groupDao.findGroupsByParam("");
				json = new StringBuffer("{success:true,result:[");
				for(UserGroup ug : list) {
					json.append("{'rdn':'" + ug.getRdn() + "',")
							.append("'cn':'" + ug.getCn() + "',")
							.append("'displayName':'" + ug.getDisplayName() + "'},");
				}
				if(list.size() > 0) {
					json.deleteCharAt(json.length() - 1);
				}
				json.append("]}");
			} else if (methodCall.equalsIgnoreCase("getDetailByGroupRDN")) {
				String groupRDN = req.getParameter("groupRDN");
				UserGroup ug = groupDao.findByRDN(groupRDN);
				json = new StringBuffer("{success:true,data:{");
				json.append("'rdn':'" + ug.getRdn() + "'")
					.append(",'cn':'" + ug.getCn() + "'")
					.append(",'displayName':'" + ug.getDisplayName() + "'")
					.append(",'description':'" + (ug.getDescription() == null ? "" : ug.getDescription()) + "'")
					.append(",'status':'" + (ug.getStatus() != null ? ug.getStatus() : "") + "'")
					.append("}}");
			} else if(methodCall.equalsIgnoreCase("findByUserRDN")) {//根据用户RDN查找
				String userRDN = req.getParameter("userRDN");
				List<UserGroup> list = groupDao.findGroupsByParam("", userRDN, true);
				json = new StringBuffer("{success:true,result:[");
				for(UserGroup ug : list) {
					json.append("{'rdn':'" + ug.getRdn() + "',")
							.append("'cn':'" + ug.getCn() + "',")
							.append("'displayName':'" + ug.getDisplayName() + "'},");
				}
				if(list.size() > 0) {
					json.deleteCharAt(json.length() - 1);
				}
				json.append("]}");
			} else if(methodCall.equalsIgnoreCase("combo")) {
				String param = req.getParameter("param");
				List<UserGroup> list = groupDao.findGroupsByParam(param);
				json = new StringBuffer("[");
				for(UserGroup ug : list) {
					json.append("['" + ug.getRdn() + "','" + ug.getDisplayName() + "'],");
				}
				if(list.size() > 0) {
					json.deleteCharAt(json.length() - 1);
				}
				json.append("]");
			}
		} catch(Exception e) {
			e.printStackTrace();
			json = new StringBuffer("{success:false}");
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
