package com.zsgj.info.framework.workflow.handler;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.message.mail.service.MailSenderService;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.workflow.ConfigUnitService;
import com.zsgj.info.framework.workflow.TaskAssignService;
import com.zsgj.info.framework.workflow.WorkflowConstants;
import com.zsgj.info.framework.workflow.entity.ConfigUnitMail;
import com.zsgj.info.framework.workflow.entity.ConfigUnitMailCC;
import com.zsgj.info.framework.workflow.entity.ConfigUnitRole;
import com.zsgj.info.framework.workflow.entity.ConfigUnitRoleTable;
import com.zsgj.info.framework.workflow.entity.VirtualDefinitionInfo;
import com.zsgj.info.framework.workflow.info.NodeInfo;

public class SendMailHandler extends BaseActionHandler implements ActionHandler,WorkflowConstants{
	
	private Service service = (Service)ContextHolder.getBean("baseService");
	private TaskAssignService si = (TaskAssignService) ContextHolder.getBean("taskAssignService");
	private ConfigUnitService cs = (ConfigUnitService)ContextHolder.getBean("configUnitService");
	private MailSenderService ms = (MailSenderService)ContextHolder.getBean("mailSenderService");
	private static Logger log;
	static 
	{
		log = Logger.getLogger("workflowlog");
	}
	/**
	 * 1.首先查找数据库中指派的角色；2.其次再查找动态指派的人；3.进入找到相应的人之后目的就是发送邮件
	 * 发送邮件参数（creator,toActorId(以逗号分隔),toNodeName）
	 * 有可能后台没有配置，但是只有动态指派的人；还有可能后台配置，但动态指派没有指派
	 */
	@Override
	public void execute(ExecutionContext executionContext) throws Exception {
		
		//保存当前节点的nodeName，以便于流程回退；
		String virualDesc = "";
		String nowNodeDesc = "";
		String nowNodeName = "";
		Long nodeid = 0l;
		ContextInstance ci = executionContext.getContextInstance();
		String nodeName = executionContext.getToken().getNode().getName();//当前节点名称
		//Long processInstanceId = ci.getProcessInstance().getId();
		//从上下文中拿到虚拟记录的id，得到虚拟对象；根据虚拟记录ID，虚拟描述，节点描述确定后台配置角色
		Long virtualDefinintionId = (Long)executionContext.getContextInstance().getVariable("VIRTUALDEFINITIONINFO_ID");
		String creator = (String)executionContext.getContextInstance().getVariable(WorkflowConstants.PROCESS_CREATOR_FLAG);
		Map bizParam = (Map)executionContext.getContextInstance().getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
		String vDesc = (String)executionContext.getContextInstance().getVariable("VIRTUALDEFINITIONINFO_DESC");
		//add testParam by guangsa in 20090716 begin
		Long processInstanceId = (Long)executionContext.getContextInstance().getVariable("testProcessId");
		//add testParam by guangsa in 20090716 end
		VirtualDefinitionInfo virtualDefinitionInfo = (VirtualDefinitionInfo) service.find(VirtualDefinitionInfo.class, String.valueOf(virtualDefinintionId));
		if(virtualDefinitionInfo!=null){
			virualDesc = virtualDefinitionInfo.getVirtualDefinitionDesc();	
			NodeInfo nodeInfo = new NodeInfo(executionContext.getNode());
			nowNodeDesc = nodeInfo.getDesc();
			nowNodeName = nodeInfo.getNodeName();
			nodeid = nodeInfo.getId();
		}
		String creatorEmail = "";
		log.info(virualDesc+"在"+nodeName+"的进入事件中给指派的人审批发邮件。审批人分为三部分：");
		ConfigUnitRole unitRole = si.findUnitRoleByNodeTypeAndProDesc(virualDesc, nowNodeDesc, String.valueOf(virtualDefinintionId),nodeid);//后台配置角色
		String[] configEmail = null;//后台配置，如果有就复制进入，没有就为空值
		if(unitRole!=null&&!"".equals(unitRole)){
			
			int isCreator = unitRole.getIsGiveCreate();//后台配置是否有创建者
			//根据配置的相应角色找到相应的人,然后要拼成相应的字符串（以逗号分隔）
			List<ConfigUnitRoleTable> list = si.findRoleTableByConfigUnitRole(unitRole);	
			if(list.isEmpty()){
				if(isCreator==1){//说明后台没有配置角色审批人，这时需要看审批人是否有创建者
					List creat = service.find(UserInfo.class, "userName", creator);
					if(!creat.isEmpty()){
						UserInfo creatorUser = (UserInfo)(creat.get(0));
						creatorEmail = creatorUser.getEmail();
					}else{
						throw new Exception("创建者为空导致无法查找userinfo中的信息");
					}
				}else{
					throw new Exception("后台角色并没有配置");
				}
			}
			Set user = new HashSet();
			for(ConfigUnitRoleTable roles : list){
				Role role = roles.getRole();
				Set<UserInfo> userinfos=role.getUserInfos();
				for(UserInfo userinfo:userinfos){
					if(userinfo.getEmail()!=null&&!"".equals(userinfo.getEmail())){
						user.add(userinfo.getEmail());//用set集合防止人重名
					}					
				}							
			} 
			if(creatorEmail!=null&&!"".equals(creatorEmail)){//说明审批人包括创建者
				user.add(creatorEmail);
			}
			configEmail = new String[user.size()];
			int flag = 0;
			Iterator ite1 = user.iterator();
			while(ite1.hasNext()){
				String userConfig = (String)ite1.next();
				configEmail[flag] = userConfig;
				flag++;
			}			
			log.info("***************第一部分为后台配置的人，查找成功");
		}else{
			log.info("(请检查一下)"+virualDesc+"(流程)是否不用后台配置相应的审批人");
		}
		
		//以上就用字符串包装了后台配置的人，还需动态指派的人
		String[] toEmail = null;//动态指派人的email
		if(!bizParam.isEmpty()){		
			String dynaAssign = (String)bizParam.get("userList");
			if(!"".equals(dynaAssign)&&dynaAssign!=null){	
				if (dynaAssign.contains("$")) {
					String[] nodeUser = dynaAssign.split("\\$");//有多个节点信息(包括节点描述和相应人)
					Set user = new HashSet();
					for (String dyNodeName : nodeUser) {
						
						String dynaDesc = dyNodeName.substring(0, dyNodeName.indexOf(":")).trim();//nodeDesc(英文)
						String dyUserMeg = dyNodeName.substring(dyNodeName.indexOf(":") + 1);
						String[] users = dyUserMeg.split(",");//具体的多个人
						
						if(nowNodeDesc.equals(dynaDesc)){	
							for(int i=0;i<users.length;i++){
								UserInfo info = (UserInfo)service.find(UserInfo.class, "userName", users[i]).get(0);
								String email = info.getEmail();
								if(!"".equals(email)&&email!=null){
									user.add(email);
								}								
							}
						}						
						
					}
					toEmail = new String[user.size()];
					int flag = 0;
					Iterator ite1 = user.iterator();
					while(ite1.hasNext()){
						String userConfig = (String)ite1.next();
						toEmail[flag] = userConfig;
						flag++;
					}		
				}else{
					String dynaDesc = dynaAssign.substring(0, dynaAssign.indexOf(":")).trim();
					String dyUserMeg = dynaAssign.substring(dynaAssign.indexOf(":") + 1);
					String[] users = dyUserMeg.split(",");//具体的多个人
					
					Set user = new HashSet();
					
					if(nowNodeDesc.equals(dynaDesc)){	
						for(int i=0;i<users.length;i++){
							UserInfo info = (UserInfo)service.find(UserInfo.class, "userName", users[i]).get(0);
							String email = info.getEmail();
							if(!"".equals(email)&&email!=null){
								user.add(email);
								
							}								
						}
					}
					toEmail = new String[user.size()];
					int flag = 0;
					Iterator ite1 = user.iterator();
					while(ite1.hasNext()){
						String userConfig = (String)ite1.next();
						toEmail[flag] = userConfig;
						flag++;
					}			
				}
			}
			log.info("***************第二部分为动态指派的人，查找成功");
		}else{
			log.info("(请检查一下)"+virualDesc+"(流程)业务参数为空");
		}	
		//合并后台配置和动态指派的人的email地址
		String[] combinEmail = null;
		List com = new ArrayList();
		if(configEmail!=null&&toEmail!=null){
			combinEmail = new String[configEmail.length+toEmail.length];
			for(int i=0;i<configEmail.length;i++){
				com.add(configEmail[i]);
			}
			for(int i=0;i<toEmail.length;i++){
				com.add(toEmail[i]);
			}
			for(int j=0;j<combinEmail.length;j++){
				combinEmail[j] = (String)com.get(j);
			}			
		}else if(configEmail!=null&&toEmail==null){//后台配置的email不为空，而动态指派的email为空
			combinEmail = new String[configEmail.length];
			for(int i=0;i<configEmail.length;i++){
				com.add(configEmail[i]);
			}
			for(int j=0;j<configEmail.length;j++){
				combinEmail[j] = (String)com.get(j);
			}	
		}else if(configEmail==null&&toEmail!=null){//动态指派的email不为空，而后台配置的email为空
			combinEmail = new String[toEmail.length];
			for(int i=0;i<toEmail.length;i++){
				com.add(toEmail[i]);
			}
			for(int j=0;j<toEmail.length;j++){
				combinEmail[j] = (String)com.get(j);
			}	
		}
		//remove by lee for 去掉垃圾抄送 in 20091221 begin
//		else if(configEmail==null&&toEmail==null){
//			combinEmail = new String[]{"guangshunan0813@163.com"};
//		}
		//remove by lee for 去掉垃圾抄送 in 20091221 end
		log.info("***************然后合并前两部分的审批人员");
		//再加上后台配置的抄送人信息（未考虑不是本集团人的情况）
		String nodeId = String.valueOf(executionContext.getNode().getId());
		ConfigUnitMail unitMail = cs.findMailObjectById(String.valueOf(virtualDefinintionId), nodeId);
		
		int flag = 0;
		String subject = null;
		String content = null;
		String[] ccEmail = null;//抄送人的email地址
		
		if(unitMail!=null&&!"".equals(unitMail)){
			subject = unitMail.getSubject();
			content = unitMail.getContent();
			
			Set<UserInfo> userInfos = unitMail.getUserInfos();
			if(userInfos.isEmpty()){
				throw new Exception("邮件抄送配置单元角色没有配置");
			}
			ccEmail = new String[userInfos.size()];
			
			List<ConfigUnitMailCC> mailCC = service.find(ConfigUnitMailCC.class, "configUnitMail", unitMail);
			/*******************************************************************************************************************/
			//这部的意思是如果数据库中用户有邮件，那么我就用数据库。如果用户不想用数据库中，我就用用户手动输入的邮件地址
			if(!mailCC.isEmpty()){
				for(int i=0;i<mailCC.size();i++){
					
					ConfigUnitMailCC confirmMailCC = mailCC.get(i);				
					UserInfo userInfo = confirmMailCC.getUserInfo();
					
					if(userInfo!=null&&!"".equals(userInfo)){
						String email = userInfo.getEmail();
						if("".equals(email)||email==null){
							email = confirmMailCC.getMail();//这个是用户手写的邮件地址
						}
						ccEmail[i] = email;
					}				
				}		
			}
			
		}else if(unitMail==null||"".equals(unitMail)){
			//remove by lee for 去掉垃圾抄送 in 20091221 begin
			//ccEmail = new String[]{"guangshunan0813@163.com"};//   guangsa@information.digitalchina.com
			//remove by lee for 去掉垃圾抄送 in 20091221 end
		}
		log.info("***************第三部分为后台配置抄送的人，查找成功");
		//add by guangsa for sendComplexMail in 2009-07-15 begin
		String auditMeg = "点击此链接，查看仔细并请审批！链接：----------------------------";
		String workflowEntity = (String)bizParam.get("workflowHistory");		
		List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, processInstanceId);//查找出来的是所有的按流程顺序排列的节点信息
		if(auditHis.size()>1){//说明不是流程刚提交
			for(int i=1;i<auditHis.size();i++){
				BeanWrapper baseObjectWrapper = new BeanWrapperImpl(auditHis.get(i));
				String nodeMeg = (String)baseObjectWrapper.getPropertyValue("nodeName");
				UserInfo user = (UserInfo)baseObjectWrapper.getPropertyValue("approver");
				String userName = user.getRealName();
				auditMeg += nodeMeg+"(节点)"+userName+"审批通过!  ;";
			}
		}
		//add by guangsa for sendComplexMail in 2009-07-15 end
		/*************************再次调用其他方法*****************************************/
		if(subject==null||"".equals(subject)){
			subject=creator+"提交了"+vDesc+"请审批!";//"审批通知";
		}
		if(content==null||"".equals(content)){
			content=auditMeg;//"ITIL项目中有一个需求需要您审批";
		}
		//add by guangsa for sendComplexMail in 2009-07-17 begin
		String context = this.htmlContent(creator, vDesc, auditHis);
		try{
			ms.sendMimeMail(combinEmail, ccEmail, null, subject, context, null);
		}catch(Exception e){
			log.info(virualDesc+"(流程)在"+nodeName+"(节点)给三部分的审批人发送邮件时发生异常！");
			e.printStackTrace();
		}
		//add by guangsa for sendComplexMail in 2009-07-17 end
//		try{
//			ms.sendSimplyMail(combinEmail,ccEmail , null, subject, content);
//		}catch(Exception e){
//			log.info(virualDesc+"(流程)在"+nodeName+"(节点)给三部分的审批人发送邮件时发生异常！");
//			e.printStackTrace();
//		}
		log.info(virualDesc+"在"+nodeName+"(节点)给三部分的审批人发送邮件成功！");
		
	}
	
	/**
	 * 组装HTML邮件发送给IBM
	 * @Methods Name htmlContent
	 * @Create In 2009-7-17 By 光顺安
	 * @param order
	 * @param opl
	 * @return String
	 */
	private String htmlContent(String creator,String vDesc,List auditHis) {
		
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
		sb.append("		<meta http-equiv=\"content-type\" content=\"text/html; charset=GBK\">");

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
		sb.append("					<td ><div align=\"left\"><strong>提交人：</strong></div></td>");
		sb.append("                 <td >" + creator + "提交了"+vDesc +",请审批！"+"</td>");
		sb.append("				</tr>");
		sb.append("				<tr>");
		sb.append("					<td align=\"left\">"+"点击此链接，查看仔细并请审批！链接："+"</td>");
		sb.append("					<td >"+"<a href=http://localhost:8080/itil/servlet/getPageModel?taskId=6096&dataId=&reqClass=&taskId=6096&goStartState=null>"+"http://10.1.180.167/itil</a>"+"</td>");
		sb.append("				</tr>");
		for (int i=0;i<auditHis.size();i++) {
			BeanWrapper baseObjectWrapper = new BeanWrapperImpl(auditHis.get(i));
			String nodeMeg = (String)baseObjectWrapper.getPropertyValue("nodeName");
			UserInfo user = (UserInfo)baseObjectWrapper.getPropertyValue("approver");
			String userName = user.getRealName();
			String auditMeg = nodeMeg+"(节点)"+userName+"审批通过!  ;";
        	sb.append("				 <tr>");
        	sb.append("					<td><div align=\"left\">" + auditMeg + "</div></td>");
        	sb.append("				 </tr>");
        }
		sb.append("			</table>");
		sb.append("		</div>");
		sb.append("	</body>");
		sb.append("</html>");

		return sb.toString();
	}

}
