package com.zsgj.itil.account.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.SameMailDept;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.security.entity.WorkSpace;
import com.digitalchina.info.framework.util.DateUtil;
import com.digitalchina.info.framework.util.HttpUtil;
import com.digitalchina.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.itil.account.entity.AccountModifyDesc;
import com.zsgj.itil.account.entity.AccountSBUOfficer;
import com.zsgj.itil.account.entity.AccountType;
import com.zsgj.itil.account.entity.DeviceType;
import com.zsgj.itil.account.entity.SpecialAccount;
import com.zsgj.itil.account.entity.SystemAppAdmin;
import com.zsgj.itil.account.entity.Win7PlatForm;
import com.zsgj.itil.account.service.AccountService;
import com.zsgj.itil.account.service.AccountSystemAdminService;
import com.zsgj.itil.actor.entity.RAndBUserList;
import com.zsgj.itil.config.extlist.entity.MailGroup;
import com.zsgj.itil.config.extlist.entity.MobileTelAllowance;
import com.zsgj.itil.config.extlist.entity.PlatFormHRCountSign;
import com.zsgj.itil.config.extlist.entity.TelephoneCountSign;

public class AccountSystemAdminAction extends BaseAction{
	private AccountSystemAdminService accountSystemAdminService = (AccountSystemAdminService) getBean("accountSystemAdminService");
	/**
	 * 获取一级部门
	 * 
	 * @Methods Name listsSameMailDeptAdmin
	 * @Create In Dec 27, 2009 By gaowen
	 * @return String
	 */
	public String listsSameMailDeptAdmin() {
		int pageSize = 10;//每页行数
		HttpServletRequest request = super.getRequest();//拿到request
		String name = request.getParameter("name");//得到itCode
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo = start/pageSize+1;
		
		Page page = accountSystemAdminService.findSameMailDeptByPage(name,
				pageNo, pageSize);
		List<SameMailDept> list = page.list();

		Long total = page.getTotalCount();// 这是查询出所有的记录
		String json = "";
		for (SameMailDept sameMailDept : list) {
			Long id = sameMailDept.getId();
			String sameMailDeptName=sameMailDept.getName();
			json += "{\"id\":\"" + id + "\",\"name\":\""
					+ sameMailDeptName +  "\"},";
		}
		if (json.length() == 0) {
			json = "{success:true,rowCount:" + "1" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}

		try {
			HttpServletResponse response = super.getResponse();
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取邮件群组
	 * 
	 * @Methods Name listMailGroupManger
	 * @Create In Dec 27, 2009 By gaowen
	 * @return String
	 */
	public String listMailGroupManger() {
		int pageSize = 10;//每页行数
		HttpServletRequest request = super.getRequest();//拿到request
		String name = request.getParameter("groupNewName");//得到itCode
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo = start/pageSize+1;
		
		Page page = accountSystemAdminService.findMailGroupByPage(name,
				pageNo, pageSize);
		List<MailGroup> list = page.list();

		Long total = page.getTotalCount();// 这是查询出所有的记录
		String json = "";
		for (MailGroup mailGroup : list) {
			Long id = mailGroup.getId();
			String groupNewName=mailGroup.getGroupNewName();
			String groupManger=mailGroup.getChangeGroupManger();
			json += "{\"id\":\"" + id + "\",\"groupNewName\":\""
					+ groupNewName + "\",\"groupManger\":\""
					+ groupManger +  "\"},";
		}
		if (json.length() == 0) {
			json = "{success:true,rowCount:" + "1" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}

		try {
			HttpServletResponse response = super.getResponse();
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * 获取工作地点-邮件服务器
	 * 
	 * @Methods Name listMailGroupManger
	 * @Create In Dec 27, 2009 By gaowen
	 * @return String
	 */
	public String listWorkSpace() {
		int pageSize = 10;//每页行数
		HttpServletRequest request = super.getRequest();//拿到request
		String name = request.getParameter("name");//得到itCode
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo = start/pageSize+1;
		
		Page page = accountSystemAdminService.findWorkSpaceByPage(name,
				pageNo, pageSize);
		List<WorkSpace> workSpaces = page.list();

		Long total = page.getTotalCount();// 这是查询出所有的记录
		String json = "";
		for (WorkSpace workSpace : workSpaces) {
			Long id = workSpace.getId();
			String workSpaceName=workSpace.getName();
			String mailServer=workSpace.getMailServer();
			json += "{\"id\":\"" + id + "\",\"name\":\""
					+ workSpaceName + "\",\"mailServer\":\""
					+ mailServer +  "\"},";
		}
		if (json.length() == 0) {
			json = "{success:true,rowCount:" + "1" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}

		try {
			HttpServletResponse response = super.getResponse();
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * 获取工作地点-邮件服务器
	 * 
	 * @Methods Name listPlatFormHrSign
	 * @Create In Dec 27, 2009 By gaowen
	 * @return String
	 */
	public String listPlatFormHrSign() {
		int pageSize = 10;//每页行数
		HttpServletRequest request = super.getRequest();//拿到request
		String name = request.getParameter("department");//得到itCode
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo = start/pageSize+1;
		Page page = accountSystemAdminService.findPlatFormHrSignByPage(name, pageNo, pageSize);
		List<PlatFormHRCountSign> signUsers = page.list();

		Long total = page.getTotalCount();// 这是查询出所有的记录
		String json = "";
		for (PlatFormHRCountSign signUser : signUsers) {
			Long id = signUser.getId();
			String department=signUser.getDepartment();
			String realName=signUser.getRealName();
			String itcode=signUser.getItcode();
			json += "{\"id\":\"" + id + "\",\"department\":\""
					+ department + "\",\"realName\":\""
					+ realName + "\",\"itcode\":\""
					+ itcode  + "\"},";
		}
		if (json.length() == 0) {
			json = "{success:true,rowCount:" + "1" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}

		try {
			HttpServletResponse response = super.getResponse();
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取手机座机加签人
	 * @Methods Name listTelephoneCountSign
	 * @Create In Jul 1, 2010 By liuying
	 * @return String
	 */
	public String listTelephoneCountSign(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String deptname=request.getParameter("deptname");
		
		int start = HttpUtil.getInt(request, "start", 0);
		int pageSize = HttpUtil.getInt(request, "pageSize", 10);
		int pageNo = start/pageSize +1;
		Page page=accountSystemAdminService.findTelephoneCountSignByDeptName(deptname,pageNo,pageSize);
		String json="";
		Long total = page.getTotalCount();
		List<TelephoneCountSign> sas = page.list();
		for(TelephoneCountSign tcs:sas){
			
			json += "{\"id\":\"" + tcs.getId() + "\",\"countSignItcode\":\"" + tcs.getCountSignItcode()
			 + "\",\"countSignName\":\"" + tcs.getCountSignName() + "\",\"department\":\"" + tcs.getDepartment()
			+ "\"},";
		}
		
		if (json.length() == 0) {
			json = "{success:true,rowCount:" + "1" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 修改部门加签人帐号
	 * @Methods Name modifyTelephoneCountSign
	 * @Create In Jun 22, 2010 By liuying
	 * @return String
	 */
	public String modifyTelephoneCountSign(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String deptname=request.getParameter("deptname");
		String manageritcode=request.getParameter("manageritcode");
		String managername=request.getParameter("managername");
		String id=request.getParameter("id");
		
		String json = "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		UserInfo user=(UserInfo) getService().findUnique(UserInfo.class, "userName", manageritcode.toLowerCase());
		if(user==null){
			json="{success:true,noitcode:true}";
		}else{
			TelephoneCountSign sa=(TelephoneCountSign) getService().find(TelephoneCountSign.class, id);
			if(sa!=null){
				sa.setCountSignItcode(manageritcode.toLowerCase());
				sa.setCountSignName(managername);
				sa.setDepartment(deptname);
				getService().save(sa);
			}else{
				json = "{success:false}";
			}
		}
		
				
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
	}
	/**
	 * 保存部门加签人
	 * @Methods Name addTelephoneCountSign
	 * @Create In Jun 22, 2010 By liuying
	 * @return String
	 */
	public String addTelephoneCountSign(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String deptname=request.getParameter("deptname");
		String manageritcode=request.getParameter("manageritcode");
		String managername=request.getParameter("managername");

		TelephoneCountSign tcs=new TelephoneCountSign();
		String json = "{success:true}";
		
		UserInfo user=(UserInfo) getService().findUnique(UserInfo.class, "userName", manageritcode.toLowerCase());
		if(user==null){
			json="{success:true,noitcode:true}";
		}else{
			tcs.setCountSignItcode(manageritcode.toLowerCase());
			tcs.setCountSignName(managername);
			tcs.setDepartment(deptname);
			getService().save(tcs);
		}
			
		
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	public String deleteSameMailDept(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String id=request.getParameter("id");
		
		String json = "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		

		SameMailDept sa=(SameMailDept) getService().find(SameMailDept.class, id);
		if(sa!=null){
			Date date =new Date();
			sa.setEndDate(date);
			getService().save(sa);
		}else{
			json = "{success:false}";
		}
				
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	/**
	 * 列出所有SBU加签人信息
	 * @Methods Name listAccountSBUOfficer
	 * @Create In Jul 2, 2010 By liuying
	 * @return String
	 */
	public String listAccountSBUOfficer(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String deptname=request.getParameter("deptname");
		
		int start = HttpUtil.getInt(request, "start", 1);
		int pageSize = HttpUtil.getInt(request, "pageSize", 10);
		int pageNo=start/pageSize+1;
		Page page=accountSystemAdminService.findAccountSBUOfficerByName(deptname,pageNo,pageSize);
		String json="";
		Long total = page.getTotalCount();
		List<AccountSBUOfficer> sas = page.list();
		for(AccountSBUOfficer tcs:sas){
			
			json += "{\"id\":\"" + tcs.getId() + "\",\"countSignItcode\":\"" + tcs.getProcessNameDescription()
			 + "\",\"countSignName\":\"" + tcs.getPersonScope() + "\",\"department\":\"" + tcs.getConfirmUser()
			+ "\"},";
		}
		
		if (json.length() == 0) {
			json = "{success:true,rowCount:" + "1" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 修改SBU加签人信息
	 * @Methods Name modifyAccountSBUOfficer
	 * @Create In Jul 2, 2010 By liuying
	 * @return String
	 */
	public String modifyAccountSBUOfficer(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String deptname=request.getParameter("deptname");
		
		String id=request.getParameter("id");
		
		String json = "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		UserInfo user=(UserInfo) getService().findUnique(UserInfo.class, "userName", deptname.toLowerCase());
		if(user==null){
			json="{success:true,noitcode:true}";
		}else{
			AccountSBUOfficer sa=(AccountSBUOfficer) getService().find(AccountSBUOfficer.class, id);
			if(sa!=null){
				
				sa.setConfirmUser(deptname.toLowerCase());
				getService().save(sa);
			}else{
				json = "{success:false}";
			}
		}
		
				
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
	}
	/**
	 * 列出所有手机补贴标准信息
	 * @Methods Name listMobileTelAllowance
	 * @Create In Jul 2, 2010 By liuying
	 * @return String
	 */
	public String listMobileTelAllowance(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String deptname=request.getParameter("deptname");
		
		int start = HttpUtil.getInt(request, "start", 1);
		int pageSize = HttpUtil.getInt(request, "pageSize", 10);
		int pageNo=start/pageSize+1;
		Page page=accountSystemAdminService.findMobileTelAllowance(deptname,pageNo,pageSize);
		String json="";
		Long total = page.getTotalCount();
		List<MobileTelAllowance> sas = page.list();
		for(MobileTelAllowance tcs:sas){
			
			json += "{\"id\":\"" + tcs.getId() + "\",\"countSignItcode\":\"" + tcs.getPostName()
			 + "\",\"countSignName\":\"" + tcs.getAllowance() + "\",\"department\":\"" + tcs.getPostCode()
			+ "\"},";
		}
		
		if (json.length() == 0) {
			json = "{success:true,rowCount:" + "1" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 修改手机补贴标准信息
	 * @Methods Name modifyMobileTelAllowance
	 * @Create In Jul 2, 2010 By liuying
	 * @return String
	 */
	public String addMobileTelAllowance(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String deptname=request.getParameter("deptname");
		String manageritcode=request.getParameter("manageritcode");
		String managername=request.getParameter("managername");
		
		String json = "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		
		MobileTelAllowance sa=new MobileTelAllowance();
			
		sa.setAllowance(Double.parseDouble(managername));
		sa.setPostCode(deptname);
		sa.setPostName(manageritcode);
		getService().save(sa);
			
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
	}

	/**
	 * 修改手机补贴标准信息
	 * @Methods Name modifyMobileTelAllowance
	 * @Create In Jul 2, 2010 By liuying
	 * @return String
	 */
	public String modifyMobileTelAllowance(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String postname=request.getParameter("manageritcode");
		String aw=request.getParameter("managername");
		
		String id=request.getParameter("id");
		
		String json = "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
	
		MobileTelAllowance sa=(MobileTelAllowance) getService().find(MobileTelAllowance.class, id);
		if(sa!=null){
			sa.setAllowance(Double.parseDouble(aw));
			sa.setPostName(postname);
			getService().save(sa);
		}else{
			json = "{success:false}";
		}
	
		
				
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
	}

	/**
	 * 列出所有红黑名单
	 * @Methods Name listRAndBUserList
	 * @Create In Jul 14, 2010 By liuying
	 * @return String
	 */
	public String listRAndBUserList(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String deptname=request.getParameter("deptname");
		String type=request.getParameter("rbtype");
		String rbtype="";
		if(type!=null){
			if(type.equals("红")){
				rbtype="0";
			}
			if(type.equals("黑")){
				rbtype="1";
			}
		}
		int start = HttpUtil.getInt(request, "start", 1);
		int pageSize = HttpUtil.getInt(request, "pageSize", 10);
		int pageNo=start/pageSize+1;
		Page page=accountSystemAdminService.findRAndBUserListByNameAndType(deptname,rbtype,pageNo,pageSize);
		String json="";
		Long total = page.getTotalCount();
		List<RAndBUserList> sas = page.list();
		for(RAndBUserList tcs:sas){
			String randbtype="";
			if(tcs.getType().equals("0")){
				randbtype="红";
			}else{
				randbtype="黑";
			}
			json += "{\"id\":\"" + tcs.getId() + "\",\"countSignItcode\":\"" + randbtype
			 + "\",\"countSignName\":\"" + tcs.getType() + "\",\"department\":\"" + tcs.getUserName()
			+ "\"},";
		}
		
		if (json.length() == 0) {
			json = "{success:true,rowCount:" + "0" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 修改红黑名单
	 * @Methods Name modifyRAndBUserList
	 * @Create In Jun 22, 2010 By liuying
	 * @return String
	 */
	public String modifyRAndBUserList(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String manageritcode=request.getParameter("manageritcode");
		String id=request.getParameter("id");
		
		String json = "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		
		RAndBUserList sa=(RAndBUserList) getService().find(RAndBUserList.class, id);
		if(sa!=null){
			sa.setType(manageritcode);
			getService().save(sa);
		}else{
			json = "{success:false}";
		}
				
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
	}
	/**
	 * 增加红黑名单
	 * @Methods Name addRAndBUserList
	 * @Create In Jun 22, 2010 By liuying
	 * @return String
	 */
	public String addRAndBUserList(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String manageritcode=request.getParameter("manageritcode");
		String managername=request.getParameter("managername");

		RAndBUserList tcs=new RAndBUserList();
		String json = "{success:true}";
		
		UserInfo user=(UserInfo) getService().findUnique(UserInfo.class, "userName", manageritcode.toLowerCase());
		if(user==null){
			json="{success:true,noitcode:1}";
		}else{
			RAndBUserList rbuser=(RAndBUserList) getService().findUnique(RAndBUserList.class, "userName", manageritcode.toLowerCase());
			if(rbuser!=null){
				json="{success:true,noitcode:2}";
			}else{
				tcs.setUserName(manageritcode.toLowerCase());
				tcs.setType(managername);
				getService().save(tcs);
			}
		}
			
		
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
	}
	/**
	 * 删除红黑名单
	 * @Methods Name DeleteRAndBUserList
	 * @Create In Jul 14, 2010 By liuying
	 * @return String
	 */
	public String DeleteRAndBUserList(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String id=request.getParameter("id");
		String json = "{success:true}";
		RAndBUserList sa=(RAndBUserList) getService().find(RAndBUserList.class, id);
		if(sa!=null){
			getService().remove(sa);
		}else{
			json = "{success:false}";
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
	}
	/**
	 * 判断用户红黑类型
	 * @Methods Name findRBType
	 * @Create In Jul 15, 2010 By liuying
	 * @return String
	 */
	public String findRBType(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String username=request.getParameter("username");
		String json = "{success:true,type:2}";
		List<RAndBUserList> list=getService().find(RAndBUserList.class, "userName", username.toLowerCase());
		if(list.size()!=0&&list!=null){
			json="{success:true,type:'"+list.get(0).getType()+"'}";
		}
				
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
	}
}
