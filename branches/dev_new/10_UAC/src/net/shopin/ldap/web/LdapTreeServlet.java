package net.shopin.ldap.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.shopin.util.SpringContextUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.samples.utils.LdapTree;
import org.springframework.ldap.samples.utils.LdapTreeBuilder;

public class LdapTreeServlet extends HttpServlet {
	
	LdapTreeBuilder ldapTreeBuilder = (LdapTreeBuilder) SpringContextUtils.getBean("ldapTreeBuilder");

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
		LdapTree ldapTree = null;
		String methodCall = req.getParameter("methodCall");
		if(methodCall.equalsIgnoreCase("rootLevel")){//菜单
			StringBuilder json = new StringBuilder("{success: true,");
			ldapTree = ldapTreeBuilder.getAnLevelLdapTree(null);
			List<LdapTree> list = ldapTree.getSubContexts();
			json.append("size:'" + list.size());
			json.append("', data:[");
			for(int i=0; i<list.size(); i++){
				try {
					String dnStr = list.get(i).getNode().getDn().toString();
					String desc = list.get(i).getNode().getAttributes().get("description").get(0).toString();
					json.append("{");
					json.append("id:'" + dnStr + "',");
					json.append("text:'" + desc + "'");
					if(i<(list.size()-1)) {
						json.append("},");
					}else {
						json.append("}");
					}
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			json.append("]");
			json.append("}");
			try {
				resp.setContentType("text/html;charset=utf-8");
				resp.setCharacterEncoding("utf-8");
				PrintWriter pw = resp.getWriter();
				pw.write(json.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(methodCall.equalsIgnoreCase("childLevel")){
//			StringBuilder json = new StringBuilder("{success: true,");
//			String parentDN = req.getParameter("parentDN");
//			DistinguishedName dn =null;
//			dn = StringUtils.isEmpty(parentDN) ? DistinguishedName.EMPTY_PATH : new DistinguishedName(parentDN);
//			ldapTree = ldapTreeBuilder.getAnLevelLdapTree(dn);
//			List<LdapTree> list = ldapTree.getSubContexts();
//			json.append("size:'" + list.size());
//			json.append("', data:[");
//			for(int i=0; i<list.size(); i++){
//				try {
//					String dnStr = list.get(i).getNode().getDn().toString();
//					String desc = null;
//					Attributes attributes = list.get(i).getNode().getAttributes();
//					if(dnStr.indexOf("ou=")==0 || dnStr.indexOf("o=")==0){//orgnizationUnit or orgnization
//						desc = attributes.get("description") != null ? attributes.get("description").get(0).toString() : dnStr + " description 为空";
//					}else if(dnStr.indexOf("uid=")==0){//person
//						desc = attributes.get("cn") != null ? attributes.get("cn").get(0).toString() : dnStr + " common name 为空";
//					}
//					json.append("{");
//					json.append("id:'" + dnStr + "',");
//					json.append("text:'" + desc + "'");
//					if(i<(list.size()-1)) {
//						json.append("},");
//					}else {
//						json.append("}");
//					}
//				} catch (NamingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			json.append("]");
//			json.append("}");
//			req.setAttribute("json", json.toString());
//			RequestDispatcher dispatcher = req.getRequestDispatcher("/menu/menu-json.jsp");
//			dispatcher.forward(req, resp);
			StringBuilder json = new StringBuilder("[");
			String parentDN = req.getParameter("parentDN");
			DistinguishedName dn =null;
			dn = StringUtils.isEmpty(parentDN) ? DistinguishedName.EMPTY_PATH : new DistinguishedName(parentDN);
			ldapTree = ldapTreeBuilder.getAnLevelLdapTree(dn);
			List<LdapTree> list = ldapTree.getSubContexts();

			for(int i=0; i<list.size(); i++){
				try {
					String dnStr = list.get(i).getNode().getDn().toString();
					String desc = null;
					Attributes attributes = list.get(i).getNode().getAttributes();
					if(dnStr.indexOf("ou=")==0 || dnStr.indexOf("o=")==0){//orgnizationUnit or orgnization
						desc = attributes.get("description") != null ? attributes.get("description").get(0).toString() : dnStr + " description 为空";
					}else if(dnStr.indexOf("uid=")==0){//person
						desc = attributes.get("cn") != null ? attributes.get("cn").get(0).toString() : dnStr + " common name 为空";
					}
					json.append("{");
					json.append("id:'" + dnStr + "," + parentDN + "',");
					json.append("text:'" + desc + "'");
					if(i<(list.size()-1)) {
						json.append("},");
					}else {
						json.append("}");
					}
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			json.append("]");
			req.setAttribute("json", json.toString());
			RequestDispatcher dispatcher = req.getRequestDispatcher("/menu/menu-json.jsp");
			dispatcher.forward(req, resp);
		}
	}

}
