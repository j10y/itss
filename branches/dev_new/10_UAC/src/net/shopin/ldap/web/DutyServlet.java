package net.shopin.ldap.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.shopin.ldap.dao.DutyDao;
import net.shopin.ldap.dao.GroupDao;
import net.shopin.ldap.entity.Duty;
import net.shopin.ldap.entity.UserGroup;
import net.shopin.util.SpringContextUtils;

import org.springframework.ldap.samples.utils.LdapTreeBuilder;


public class DutyServlet extends HttpServlet {
	
	LdapTreeBuilder ldapTreeBuilder = (LdapTreeBuilder) SpringContextUtils.getBean("ldapTreeBuilder");
	DutyDao dutyDao = (DutyDao) SpringContextUtils.getBean("dutyDao");

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
				Duty duty = new Duty();
				duty.setCn(req.getParameter("cn"));
				duty.setTitle(req.getParameter("title"));
				duty.setDescription(req.getParameter("description"));
				duty.setStatus((req.getParameter("status") == null || "".equals(req.getParameter("status"))) ? 0 : Integer.parseInt(req.getParameter("status")));
				dutyDao.create(duty);
			} else if(methodCall.equalsIgnoreCase("modify")) {//修改
				Duty duty = new Duty();
				duty.setDn(req.getParameter("dn"));
				duty.setCn(req.getParameter("cn"));
				duty.setTitle(req.getParameter("title"));
				duty.setDescription(req.getParameter("description"));
				duty.setStatus((req.getParameter("status") == null || "".equals(req.getParameter("status"))) ? 0 : Integer.parseInt(req.getParameter("status")));
				dutyDao.update(duty);
			} else if(methodCall.equalsIgnoreCase("delete")) {//删除
				String dutyRDN = req.getParameter("dutyRDN");
				dutyDao.deleteByRDN(dutyRDN);
			} else if(methodCall.equalsIgnoreCase("getDetailByDutyRDN")) {
				String dutyRDN = req.getParameter("dutyRDN");
				Duty duty = dutyDao.findByRDN(dutyRDN);
				json = new StringBuffer("{success:true,data:{");
				json.append("'dn':'" + duty.getDn() + "'")
					.append(",'cn':'" + duty.getCn() + "'")
					.append(",'title':'" + duty.getTitle() + "'")
					.append(",'description':'" + (duty.getDescription() == null ? "" : duty.getDescription()) + "'")
					.append(",'status':'" + (duty.getStatus() != null ? duty.getStatus() : "") + "'")
					.append("}}");
			} else if(methodCall.equalsIgnoreCase("getList")) {
				List<Duty> list = dutyDao.findDutysByParam("");
				json = new StringBuffer("{success:true,result:[");
				for(Duty duty : list) {
					json.append("{'dn':'" + duty.getDn() + "',")
							.append("'cn':'" + duty.getCn() + "',")
							.append("'title':'" + duty.getTitle() + "'},");
				}
				if(list.size() > 0) {
					json.deleteCharAt(json.length() - 1);
				}
				json.append("]}");
			} else if(methodCall.equalsIgnoreCase("combo")) {
				String param = req.getParameter("param");
				List<Duty> list = dutyDao.findDutysByParam(param);
				json = new StringBuffer("[");
				for(Duty duty : list) {
					json.append("['" + duty.getDn() + "','" + duty.getTitle() + "'],");
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
