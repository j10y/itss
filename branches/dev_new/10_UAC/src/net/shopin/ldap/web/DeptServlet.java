package net.shopin.ldap.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.shopin.ldap.dao.DeptDao;
import net.shopin.ldap.entity.Department;
import net.shopin.util.SpringContextUtils;

import org.springframework.ldap.samples.utils.LdapTreeBuilder;

public class DeptServlet extends HttpServlet {
	
	LdapTreeBuilder ldapTreeBuilder = (LdapTreeBuilder) SpringContextUtils.getBean("ldapTreeBuilder");
	DeptDao deptDao = (DeptDao) SpringContextUtils.getBean("deptDao");

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		StringBuffer json = new StringBuffer("{success:true}");
		try {
			String methodCall = req.getParameter("methodCall");
			if(methodCall.equalsIgnoreCase("add")){
				String dn = req.getParameter("parentDN");
				dn = dn!=null && !"".equals(dn) ? dn : "o=orgnizations";//一般会有根节点
				Department department = new Department();
				department.setParentNo(req.getParameter("parentNo"));
				department.setDeptName(req.getParameter("deptName"));
				deptDao.create(department);
				
			}else if(methodCall.equalsIgnoreCase("delete")){
				String dn = req.getParameter("dn");
				Department department = new Department();
				department.setDeptNo(req.getParameter("deptNo"));
				deptDao.delete(department);
			}else if(methodCall.equalsIgnoreCase("modify")){
				Department department = new Department();
				department.setDeptName(req.getParameter("deptName"));
				department.setDeptNo(req.getParameter("deptNo"));
				deptDao.update(department);				
			}else if (methodCall.equalsIgnoreCase("searchDepts")){
				
				List<Department> depts = deptDao.findDeptsByParam(req.getParameter("param") != null ? req.getParameter("param") : "");
				json = new StringBuffer();
				for (Department dept : depts) {
					json.append( "{deptNo:'" + dept.getDeptNo() + "',");
					json.append( "deptName:'" + dept.getDeptName() + "',");
					json.append( "erpId:'" + dept.getErpId() + "',");
					json.append( "status:'" + dept.getStatus() + "',");
					json.append( "parentNo:'" + dept.getParentNo() + "',");
					json.append( "deptDesc:'" + dept.getDeptDesc() + "'},");
				}
				if (json.toString().endsWith(",")) {
					json = new StringBuffer(json.substring(0, json.length() - 1));
				}
				json = new StringBuffer("{success: true,rowCount:'" + depts.size() + "',data:[" + json + "]}");
			}
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
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
