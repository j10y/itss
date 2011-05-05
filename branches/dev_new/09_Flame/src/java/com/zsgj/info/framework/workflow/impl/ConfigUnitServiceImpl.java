package com.zsgj.info.framework.workflow.impl;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.zsgj.info.appframework.pagemodel.entity.PageModel;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.security.entity.UserRole;
import com.zsgj.info.framework.util.PropertiesUtil;
import com.zsgj.info.framework.workflow.ConfigUnitService;
import com.zsgj.info.framework.workflow.entity.ConfigUnitMail;
import com.zsgj.info.framework.workflow.entity.ConfigUnitMailNodeSender;
import com.zsgj.info.framework.workflow.entity.ConfigUnitRole;
import com.zsgj.info.framework.workflow.entity.ConfigUnitRoleTable;
import com.zsgj.info.framework.workflow.entity.ConfigUnitTimer;
import com.zsgj.info.framework.workflow.entity.SubProcessConfigUnit;
import com.zsgj.info.framework.workflow.entity.TaskPreAssign;
import com.zsgj.info.framework.workflow.entity.VirtualDefinitionInfo;
import com.zsgj.info.framework.workflow.entity.VirtualNodeInfo;
import com.zsgj.info.framework.workflow.entity.WorkflowRecordTaskInfo;

public class ConfigUnitServiceImpl extends BaseDao implements ConfigUnitService{
	/**
	 * 根据流程名和节点名得到相应的timer数据
	 * @Methods Name showNodeTimer
	 * @Create In Mar 4, 2009 By guangsa
	 * @return
	 * @throws Exception String
	 */
	public ConfigUnitTimer showConfigUnitTimer(Long virtualId ,Long nodeId) {
		Criteria c = super.getCriteria(ConfigUnitTimer.class);
		c.add(Restrictions.eq("virtualProcessId", virtualId));
		c.add(Restrictions.eq("nodeId", nodeId));
		ConfigUnitTimer configUnitTimer = (ConfigUnitTimer)c.uniqueResult();
		return configUnitTimer;
	}

	public Map showRole(String virProcessId, String nodeId) {
		VirtualDefinitionInfo virtualDefinitionInfo = (VirtualDefinitionInfo)super.findUniqueBy(VirtualDefinitionInfo.class, "id", Long.valueOf(virProcessId));
		//根据虚拟ID和节点ID唯一确定一个节点
		Criteria criteria = super.getCriteria(VirtualNodeInfo.class);		
		criteria.add(Restrictions.eq("virtualDefinitionInfo", virtualDefinitionInfo));
		criteria.add(Restrictions.eq("nodeId", Long.valueOf(nodeId)));
		VirtualNodeInfo virtualNodeInfo = (VirtualNodeInfo)criteria.uniqueResult();
		
		String desc = virtualDefinitionInfo.getVirtualDefinitionDesc();
		String nodeName = virtualNodeInfo.getVirtualNodeName();
		String nodeDesc = virtualNodeInfo.getVirtualNodeDesc();
		
		Criteria c = super.getCriteria(ConfigUnitRole.class);
		c.add(Restrictions.eq("nodeId", Long.valueOf(nodeId)));
		c.add(Restrictions.eq("processId", Long.valueOf(virProcessId)));
		ConfigUnitRole configUnitRole = (ConfigUnitRole)c.uniqueResult();
		
		List<ConfigUnitRoleTable> list = super.findBy(ConfigUnitRoleTable.class, "configUnitRole", configUnitRole);
		//List<Role> roles = new ArrayList<Role>();
		Map roles = new HashMap();
		for(ConfigUnitRoleTable table : list){
			//利用map得到相应的角色和审批类型
			roles.put(table.getRole(), table.getFlag());
		}
		
		return roles;
	}

	public ConfigUnitRole findConfigUnitRole(String virProcessId, String nodeId) {
		
		VirtualDefinitionInfo virtualDefinitionInfo = (VirtualDefinitionInfo)super.findUniqueBy(VirtualDefinitionInfo.class, "id", Long.valueOf(virProcessId));
		Criteria criteria  = super.getCriteria(VirtualNodeInfo.class);
		criteria.add(Restrictions.eq("virtualDefinitionInfo", virtualDefinitionInfo));
		criteria.add(Restrictions.eq("nodeId", Long.valueOf(nodeId)));
		VirtualNodeInfo virtualNodeInfo = (VirtualNodeInfo)criteria.uniqueResult();
		
		String desc = virtualDefinitionInfo.getVirtualDefinitionDesc();
		String nodeName = virtualNodeInfo.getVirtualNodeName();
		String nodeDesc = virtualNodeInfo.getVirtualNodeDesc();
		
		Criteria c = super.getCriteria(ConfigUnitRole.class);
		c.add(Restrictions.eq("nodeId", Long.valueOf(nodeId)));
		c.add(Restrictions.eq("processId", Long.valueOf(virProcessId)));
		List<ConfigUnitRole> list = c.list();
		if(list.size()!=0){
			ConfigUnitRole configUnitRole = (ConfigUnitRole)list.get(0);
			return configUnitRole;
		}
		return null;
	}

	public boolean findConfigUnitRoleTableByConfigUnitRole(ConfigUnitRole configUnitRole,UserInfo u){
		
		boolean flag = false;//默认是查看人
		Set<Role> userRoles = u.getRoles();
		//首先通过configUnitRole得到多个ConfigUnitRoleTable
		Criteria criteria = super.getCriteria(ConfigUnitRoleTable.class);
		criteria.add(Restrictions.eq("configUnitRole", configUnitRole));
		List<ConfigUnitRoleTable> roleTables = criteria.list();
		//开始遍历ConfigUnitRoleTable，如果在这个配置单元中角色和当前登入人角色一致，则再看是否是查看人，如果是查看人则把标志为致为true；
		for(ConfigUnitRoleTable roleTable : roleTables){
			Role role = roleTable.getRole();
			//开始遍历当前登入人的角色，如果角色一致，再看当前登入人员是否是审批人
			Iterator iter = userRoles.iterator();
			while(iter.hasNext()){
				Role perRole = (Role)iter.next();
				if(role.getId().equals(perRole.getId())||role.getId()==perRole.getId()){
					String auditPerson = roleTable.getWorkflowBrowsePerson();
					if(auditPerson!=null&&!"".equals(auditPerson)){
						if(auditPerson.contains(u.getUserName())){
							flag = true;//不是审批人是查看人
						}
					}
					
				}
			}
		}
		return flag;
	}
	
	public Page findUserInfoByParams(Map params, int pageNo, int pageSize) {
		
		String userName = (String)params.get("userName");		
		Criteria criteria = super.getCriteria(UserInfo.class);
		//modify by lee for 修改为中英文过滤 in 20090917 begin
		criteria.add(Restrictions.or(
				Restrictions.like("realName",userName,MatchMode.START),
				Restrictions.like("userName",userName,MatchMode.ANYWHERE)));
		//modify by lee for 修改为中英文过滤 in 20090917 end
		Page page = super.pagedQuery(criteria, pageNo, pageSize);
		return page;
	}
	
	public Page findDepartmentByParams(Map params, int pageNo, int pageSize) {

		String userName = (String)params.get("department");		
		Criteria criteria = super.getCriteria(Department.class);
		criteria.add(Restrictions.like("departName",userName,MatchMode.ANYWHERE));
		Page page = super.pagedQuery(criteria, pageNo, pageSize);
		return page;
	}
	
	public ConfigUnitMail findMailObjectById(String virtualId, String nodeId) {
		
		Criteria criteria = super.getCriteria(ConfigUnitMail.class);
		criteria.add(Restrictions.eq("VirtualProcessId", Long.valueOf(virtualId)));
		criteria.add(Restrictions.eq("NodeId", Long.valueOf(nodeId)));
		ConfigUnitMail mail = (ConfigUnitMail)criteria.uniqueResult();
		
		return mail;
	}

	public VirtualNodeInfo findVirtualNodeInfo(VirtualDefinitionInfo definitionInfo, String nodeId) {
		Criteria criteria = super.getCriteria(VirtualNodeInfo.class);		
		criteria.add(Restrictions.eq("virtualDefinitionInfo", definitionInfo));
		criteria.add(Restrictions.eq("nodeId", Long.valueOf(nodeId)));
		VirtualNodeInfo virtualNodeInfo = (VirtualNodeInfo)criteria.uniqueResult();
		return virtualNodeInfo;
	}

	public Page findVirtualDefinitionInfos(Map params, int pageNo, int pageSize) {
		String subProcessName = (String)params.get("subProcessName");
		String virtualDefinitionInfoId = (String)params.get("virtualDefinitionInfoId");
		Criteria criteria = super.getCriteria(VirtualDefinitionInfo.class);
		if(subProcessName!=null&&!"".equals(subProcessName)){
			criteria.add(Restrictions.like("virtualDefinitionDesc", "%"+subProcessName+"%"));
		}
		criteria.add(Restrictions.ne("id", Long.valueOf(virtualDefinitionInfoId)));
		Page page = super.pagedQuery(criteria, pageNo, pageSize);
		return page;
	}

	public SubProcessConfigUnit findSubProcessConfigUnit(Long virtualId,
			Long nodeId) {
		
		Criteria criteria = super.getCriteria(SubProcessConfigUnit.class);		
		criteria.add(Restrictions.eq("superProcessId", virtualId));
		criteria.add(Restrictions.eq("nodeId",nodeId));
		SubProcessConfigUnit subProcessConfigUnit = (SubProcessConfigUnit)criteria.uniqueResult();
		
		return subProcessConfigUnit;
	}

	public Page findPageModelByParams(Map params, int pageNo, int pageSize) {
		
		String pageModelName = (String)params.get("pageModelName");		
		Criteria criteria = super.getCriteria(PageModel.class);
		criteria.add(Restrictions.like("name", pageModelName,MatchMode.START));
		Page page = super.pagedQuery(criteria, pageNo, pageSize);
		return page;
	}

	public Page findSystemRoleByParams(Map params, int pageNo, int pageSize) {
		
		String departCode = (String)params.get("departCode");	
		if(departCode!=null&&!"".equals(departCode)){
			Department department = super.findUniqueBy(Department.class, "departCode", Long.valueOf(departCode));
			Criteria criteria = super.getCriteria(Role.class);
			criteria.add(Restrictions.eq("department", department));
			Page page = super.pagedQuery(criteria, pageNo, pageSize);
			return page;
		}else{
			return null;
		}
	}

	public ConfigUnitMailNodeSender findMailNodeById(String virtualId,
			String nodeId) {
		
		Criteria criteria = super.getCriteria(ConfigUnitMailNodeSender.class);
		criteria.add(Restrictions.eq("virtualProcessId", Long.valueOf(virtualId)));
		criteria.add(Restrictions.eq("nodeId", Long.valueOf(nodeId)));
		ConfigUnitMailNodeSender mailSender = (ConfigUnitMailNodeSender)criteria.uniqueResult();
		
		return mailSender;
	}

	public Page findMailNodeSenderUserInfoByParams(Map params, int pageNo,
			int pageSize) {
		
		String userName = (String)params.get("userName");		
		Criteria criteria = super.getCriteria(UserInfo.class);
		criteria.add(Restrictions.like("realName", userName,MatchMode.ANYWHERE));
		Page page = super.pagedQuery(criteria, pageNo, pageSize);
		return page;
		
	}

	public ConfigUnitMailNodeSender findConfigUnitMailNodeSenderById(
			String virProcessId, String nodeId) {
		
		Criteria c = super.getCriteria(ConfigUnitMailNodeSender.class);
		c.add(Restrictions.eq("nodeId", Long.valueOf(nodeId)));
		c.add(Restrictions.eq("virtualProcessId", Long.valueOf(virProcessId)));
		ConfigUnitMailNodeSender mailSender = (ConfigUnitMailNodeSender)c.uniqueResult();
		
		return mailSender;
	}

//	public void saveRecordTaskMessage(Long vProcess,Long nodeId ,Long processInstanceId,TaskInstance ti,String vProcessName,String dataId,String nodeName,String nodeDesc) {
//		
//		if(ti!=null&&!"".equals(ti)){
//			WorkflowRecordTaskInfo recordTask = new WorkflowRecordTaskInfo();
//			recordTask.setVirtualProcessId(vProcess);
//			recordTask.setNodeId(nodeId);
//			recordTask.setProcessInstanceId(processInstanceId);
//			recordTask.setTaskId(ti.getId());
//			recordTask.setDataId(Long.valueOf(dataId));
//			recordTask.setVirtualProcessName(vProcessName);
//			recordTask.setNodeDesc(nodeDesc);
//			recordTask.setNodeName(nodeName);
//			super.save(recordTask);
//		}
//		
//	}
	public void saveRecordTaskMessage(Long vProcess,Long nodeId ,Long processInstanceId,TaskInstance ti,String vProcessName,String dataId,String nodeName,String nodeDesc,String[] auditUserInfos,String processCreator) {
			
			if(ti!=null&&!"".equals(ti)){
				//add by guangsa for takeAuditUserInfo in 20090805 begin
				String auditUser = "";
				for(String user : auditUserInfos){
					auditUser += user;
					auditUser += ",";
				}
				if(auditUser.endsWith(",")){
					auditUser = auditUser.substring(0, auditUser.length()-1);
				}
				System.out.println(auditUser);
				//add by guangsa for takeAuditUserInfo in 20090805 end
				WorkflowRecordTaskInfo recordTask = new WorkflowRecordTaskInfo();
				recordTask.setVirtualProcessId(vProcess);
				recordTask.setNodeId(nodeId);
				recordTask.setProcessInstanceId(processInstanceId);
				recordTask.setTaskId(ti.getId());
				recordTask.setDataId(Long.valueOf(dataId));
				recordTask.setVirtualProcessName(vProcessName);
				recordTask.setNodeDesc(nodeDesc);
				recordTask.setNodeName(nodeName);
				recordTask.setAuditUserInfos(auditUser);
				recordTask.setProcessCreator(processCreator);
				try{
					super.save(recordTask);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
	}

	public WorkflowRecordTaskInfo findWorkflowRecordTaskInfo(String dataId,
			String vProcessName) {
		
		Criteria criteria = super.getCriteria(WorkflowRecordTaskInfo.class);
		criteria.add(Restrictions.eq("virtualProcessName", vProcessName));
		criteria.add(Restrictions.eq("dataId", Long.valueOf(dataId)));
		WorkflowRecordTaskInfo workflowRecordTaskInfo = (WorkflowRecordTaskInfo)criteria.uniqueResult();
		
		return workflowRecordTaskInfo;
	}

	public VirtualNodeInfo findVirtualNodeInfoByDoubleId(Long processId,
			Long nodeId) {
		VirtualDefinitionInfo vdInfo = super.findUniqueBy(VirtualDefinitionInfo.class, "id", processId);
		Criteria criteria = super.getCriteria(VirtualNodeInfo.class);		
		criteria.add(Restrictions.eq("virtualDefinitionInfo", vdInfo));
		criteria.add(Restrictions.eq("nodeId",nodeId));
		VirtualNodeInfo virtualNodeInfo = (VirtualNodeInfo)criteria.uniqueResult();
		return virtualNodeInfo;
	}
	/**
	 * 通过反射得到相应的流程历史表，在通过流程实例得到相应的流程历史
	 * @param historyEntity
	 * @param processInstanceId
	 * @return
	 */
	public List findAllWorkflowHistoryMessage(String historyEntity,
			Long processInstanceId) {
		
		Class clazz = this.getClass(historyEntity);
		Criteria criteria = super.getCriteria(clazz);
		criteria.add(Restrictions.eq("processId", processInstanceId));
//		criteria.addOrder(Order.asc("nodeId"));
		//add by gaowen in 2009-12-2 审批历史顺序 按审批时间排序
		criteria.addOrder(Order.asc("approverDate"));
		List list = criteria.list();
		return list;
	}
	
	private Class getClass(String className) {
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			System.out.print("类名：" + className + "不正确！");
			e.printStackTrace();
		}
		return clazz;
	}

	public String findUserInfoMessageById(Long userId) {
		
		Criteria criteria = super.getCriteria(UserInfo.class);
		criteria.add(Restrictions.eq("processId", userId));
		criteria.setProjection(Projections.property("realName"));
		String realName = (String)criteria.uniqueResult();
		return realName;
	}
	
	
	/**
	 * 组装HTML邮件发送
	 * @Methods Name htmlContent
	 * @Create In 2009-7-17 By guangsa
	 * @param order
	 * @param opl
	 * @return String
	 */
	public String htmlContent(String nodeName,String pageUrl,String applyType,String dataId, String reqClass,
			String goStartState, Long taskId, String creator, String vDesc,
			List auditHis,String hurryFlag,boolean browsePerson) {
		//add by guangsa for 查看人标识 in 20090824 begin
		String browseFlag = "";
		if(browsePerson){//是查看人
			browseFlag = "1";
		}
		String reqFlag = "";
		if("1".equals(hurryFlag)){
			reqFlag = "加急";
		}
		//add by guangsa for 查看人标识 in 20090824 end
		StringBuilder sb = new StringBuilder();
		NumberFormat currencyFormat = NumberFormat.getNumberInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		sb
				.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		sb.append("<html>");
		sb.append("	<head>");
		sb.append("		<title>PO Details</title>");

		sb.append("		<meta http-equiv=\"keywords\" content=\"keyword1,keyword2,keyword3\">");
		sb.append("		<meta http-equiv=\"description\" content=\"this is my page\">");
		sb.append("		<meta http-equiv=\"content-type\" content=\"text/plain; charset=GBK\">");

		sb.append("		<!--<link rel=\"stylesheet\" type=\"text/css\" href=\"./styles.css\">-->");
		sb.append("		<style type=\"text/css\">");
		sb.append("		<!--");
		sb.append("		.STYLE1 {");
		sb.append("			font-size: 24px;");
		sb.append("			font-weight: bold;");
		sb.append("		}");
		sb.append("		-->");
		sb.append("		</style>");
		sb.append("	</head>");

		sb.append("	<body>");
		sb.append("		<div align=\"center\">");
		sb.append("			<table width=\"1000\" height=\"200\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		sb.append("				<tr>");
		sb.append("					<td height=\"29\" colspan=\"3\" nowrap><div align=\"center\" class=\"STYLE1\">邮件通知</div></td>");
		sb.append("				</tr>");
		sb.append("				<tr>");
		sb.append("					<td ><div align=\"left\"><strong>提交人:</strong></div>"+creator+"提交了"+reqFlag+vDesc +",请审批！"+"</td>");
		sb.append("				</tr>");
		sb.append("				<tr>");
		sb.append("					<td align=\"left\">"+"点击此链接，查看仔细并请审批！链接：");///infoAdmin/workflow/configPage/defaultPageModel.jsp///nodeName
		sb.append("				"+"<a href=" + PropertiesUtil.getProperties("system.web.url","localhost:8080") + "/infoAdmin/workflow/configPage/auditFromMail.jsp?"+"taskId="+taskId+"&dataId="+dataId+"&goStartState="+goStartState+"&taskName="+"&applyType="+applyType+"&browseFlag="+browseFlag+">"+"审批链接</a>"+"</td>");
		sb.append("				</tr>");
		if(auditHis!=null&&!"".equals(auditHis)){
			for (int i=0;i<auditHis.size();i++) {
				BeanWrapper baseObjectWrapper = new BeanWrapperImpl(auditHis.get(i));
				String nodeMeg = (String)baseObjectWrapper.getPropertyValue("nodeName");
				UserInfo user = (UserInfo)baseObjectWrapper.getPropertyValue("approver");
				if(user!=null){
				String userName = user.getRealName();
				String auditMeg = nodeMeg+"(节点)"+userName+"审批通过!  ;";
	        	sb.append("				 <tr>");
	        	sb.append("					<td><div align=\"left\">" + auditMeg + "</div></td>");
	        	sb.append("				 </tr>");
				}
	        }
		}
		sb.append("			</table>");
		sb.append("		</div>");
		sb.append("	</body>");
		sb.append("</html>");

		return sb.toString();
	}
	
	/**
	 * 组装HTML邮件发送 ITIL专用
	 * @Methods Name htmlContent
	 * @Create In 2009-11-30 By gaowen
	 * @param nodeName 节点名称
	 * @param creatorMeg 提交申请人
	 * @param userInfo 该环节审批人
	 * @return String
	 */
	public String htmlContent(String nodeName,String pageUrl,String applyType,String dataId, String reqClass,
			String goStartState, Long taskId, UserInfo creatorMeg, String vDesc,
			List auditHis,String hurryFlag,boolean browsePerson,UserInfo userInfo) {
		String browseFlag = "";
		if(browsePerson){//是查看人
			browseFlag = "1";
		}
		String reqFlag = "";
		if("1".equals(hurryFlag)){
			reqFlag = "  --  "+"<font color=red><B>'加急'</B></font>"+"  --  ";
		}
		StringBuilder sb = new StringBuilder();
		NumberFormat currencyFormat = NumberFormat.getNumberInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(); 
		String dateString  = dateFormat.format(date);
		sb
				.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		sb.append("<html>");
		sb.append("	<head>");
		sb.append("		<title>PO Details</title>");

		sb.append("		<meta http-equiv=\"keywords\" content=\"keyword1,keyword2,keyword3\">");
		sb.append("		<meta http-equiv=\"description\" content=\"this is my page\">");
		sb.append("		<meta http-equiv=\"content-type\" content=\"text/html; charset=GBK\">");

		sb.append("		<!--<link rel=\"stylesheet\" type=\"text/css\" href=\"./styles.css\">-->");
		sb.append("<style type=\"text/css\">");

		sb.append("<!--");
		sb.append(".STYLE1 {");
		sb.append("font-size: 14px;");
		sb.append("line-height:20px;");
		sb.append("}");
		sb.append(".STYLE2 {");
		sb.append("font-family:'楷体';");
		sb.append("font-size: 14px;");
		sb.append("}");
		sb.append("-->");
		sb.append("</style>");
		sb.append("	</head>");

		sb.append("	<body>");
		sb.append("		<div align=\"center\">");
		sb.append("			<table width=\"900\" height=\"200\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		sb.append("				<tr>");
		sb.append("					<td height=\"29\" colspan=\"3\" nowrap><div align=\"center\" class=\"STYLE1\"><h3>邮件通知</h3></div></td>");
		sb.append("				</tr>");
		sb.append("				<tr>");
		sb.append("                <td class=\"STYLE1\">尊敬的"+userInfo.getRealName()+"/"+userInfo.getUserName()+"，您好:</td>");
		sb.append("				</tr>");
		sb.append("				 <br>");
		sb.append("				<tr>");
		sb.append("             <td class=\"STYLE1\" style=\"padding-left:2em\">");
		sb.append("				"+creatorMeg.getRealName()+"/"+creatorMeg.getUserName()+"提交了"+reqFlag+vDesc +"，<a href=" + PropertiesUtil.getProperties("system.web.url","localhost:8080") + "/infoAdmin/workflow/configPage/auditFromMail.jsp?"+"taskId="+taskId+"&dataId="+dataId+"&goStartState="+goStartState+"&taskName="+"&applyType="+applyType+"&browseFlag="+browseFlag+">"+"请点击链接审批。</a>"+"谢谢!</td>");
		sb.append("				</tr>");
		sb.append("<tr>");
		sb.append("             <td class=\"STYLE1\" style=\"padding-left:2em\">");
		sb.append("如果需要同时审批多个IT服务类申请，您可以直接访问"+"<a href=" + PropertiesUtil.getProperties("system.web.url","http://10.1.120.53/itil") +">"+"IT服务系统（ITSS）</a>"+"，减少您多次输入ITcode和ITpassword进行验证的过程。"); 
		sb.append("</td>");	
		sb.append("</tr>");
	
		sb.append("				 <tr>");
    	sb.append("					<td><div class=\"STYLE1\" align=\"left\"><br>过程：</div></td>");
    	sb.append("				 </tr>");
		if(auditHis!=null&&!"".equals(auditHis)){
			for (int i=0;i<auditHis.size();i++) {
				BeanWrapper baseObjectWrapper = new BeanWrapperImpl(auditHis.get(i));
				String nodeMeg = (String)baseObjectWrapper.getPropertyValue("nodeName");
				UserInfo user = (UserInfo)baseObjectWrapper.getPropertyValue("approver");
				Date approverDate = (Date)baseObjectWrapper.getPropertyValue("approverDate");
				SimpleDateFormat dateFormats = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				String timeString  = dateFormats.format(approverDate);
				String auditMeg=null;
				if(user!=null){
				String userName = user.getRealName();
				if(nodeMeg.contains("提交")){
					auditMeg= nodeMeg+"环节"+" "+userName+" "+timeString+" "+"提交；";
				}else{
					auditMeg= nodeMeg+"环节"+" "+userName+" "+timeString+" "+"审批通过；";
				}
		        sb.append("				 <tr>");
	        	sb.append("					<td><div class=\"STYLE1\" align=\"left\">" + auditMeg + "</div></td>");
	        	sb.append("				 </tr>");
				}
	        }
		}
		
		sb.append("<tr>");
		sb.append("<td  style=\"font-family:楷体\">");
		sb.append("<br>感谢您使用集团IT服务，如果您对我们有任何意见和建议,可以发送邮件到it-manage@zsgj.com,或者拨打IT服务建议及投诉热线7888-0。"); 
		sb.append("</td>");	
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" align=\"right\">");
		sb.append("<br>集团信息化管理部(神州数码IT)");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" align=\"right\">");
		sb.append(dateString);
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td  style=\"FILTER:alpha(opacity=30);font-size:10px\" align=\"left\">");
		sb.append("<br>本邮件由集团IT服务系统（ITSS）自动发送，请勿直接回复。");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("			</table>");
		sb.append("		</div>");
		sb.append("	</body>");
		sb.append("</html>");

		return sb.toString();
	}
	
	/**
	 * 通过角色的ID来查找相应的用户名
	 * @Methods Name getUserNameByRoleId
	 * @Create In 2009-7-17 By guangsa
	 * @param roleId
	 * @return String
	 */
	public List getUserNameByRoleId(Role role) {
		
		List<UserInfo> userNameList = new ArrayList();
		List userList = new ArrayList();
		Criteria criteria = super.getCriteria(UserRole.class);
		criteria.add(Restrictions.eq("role", role));
		List<UserRole> userRoles = criteria.list();
		for(UserRole user : userRoles){
			userNameList.add(user.getUserInfo());
		}
		for(int i=0;i<userNameList.size();i++){
			String realName = userNameList.get(i).getRealName();
			String userName = userNameList.get(i).getUserName();
			String json = realName+"("+userName+")";
			userList.add(json);
		}
		
		return userList;
	}
	/**
	 * 通过用户名来查找相应的待审记录
	 * @Methods Name getUserNameByRoleId
	 * @Create In 2009-7-17 By guangsa
	 * @param roleId
	 * @return String
	 */
	public List getTaskProxyObject(String userName) {
		Criteria criteria = super.getCriteria(TaskPreAssign.class);
		criteria.add(Restrictions.eq("actorId", userName));
		List proxyObjects = criteria.list();
		return proxyObjects;
	}
	/**
	 * 根据流程ID和节点ID找到相应的邮件配置单元
	 * @param virtualDefinitionId
	 * @param nodeId
	 * @return
	 */
	public ConfigUnitMail findConfigUnitMailById(Long virtualDefinitionId,Long nodeId) {
		Criteria criteria = super.getCriteria(ConfigUnitMail.class);
		criteria.add(Restrictions.eq("VirtualProcessId", virtualDefinitionId));
		criteria.add(Restrictions.eq("NodeId", nodeId));
		ConfigUnitMail configUnitMail = (ConfigUnitMail)criteria.uniqueResult();
		
		return configUnitMail;
	}
	public WorkflowRecordTaskInfo findWorkflowRecordByProcessId(Long processId) {
		// TODO Auto-generated method stub
		Criteria criteria = super.getCriteria(WorkflowRecordTaskInfo.class);
		criteria.add(Restrictions.eq("processInstanceId", processId));
		WorkflowRecordTaskInfo workflowRecordTaskInfo = (WorkflowRecordTaskInfo)criteria.uniqueResult();
		
		return workflowRecordTaskInfo;
	}
public void saveWorkflowTaskInfoByEntity(WorkflowRecordTaskInfo recordTask){
		
		try{
			super.save(recordTask);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
