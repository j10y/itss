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
import org.jbpm.graph.node.TaskNode;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.message.mail.service.MailSenderService;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.PropertiesUtil;
import com.zsgj.info.framework.workflow.ConfigUnitService;
import com.zsgj.info.framework.workflow.TaskAssignService;
import com.zsgj.info.framework.workflow.WorkflowConstants;
import com.zsgj.info.framework.workflow.entity.ConfigUnitMail;
import com.zsgj.info.framework.workflow.entity.ConfigUnitMailCC;
import com.zsgj.info.framework.workflow.entity.ConfigUnitRole;
import com.zsgj.info.framework.workflow.entity.ConfigUnitRoleTable;
import com.zsgj.info.framework.workflow.entity.VirtualDefinitionInfo;
import com.zsgj.info.framework.workflow.info.NodeInfo;

@SuppressWarnings("serial")
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
	 * 1.���Ȳ������ݿ���ָ�ɵĽ�ɫ��2.����ٲ��Ҷ�ָ̬�ɵ��ˣ�3.�����ҵ���Ӧ����֮��Ŀ�ľ��Ƿ����ʼ�
	 * �����ʼ�������creator,toActorId(�Զ��ŷָ�),toNodeName��
	 * �п��ܺ�̨û�����ã�����ֻ�ж�ָ̬�ɵ��ˣ����п��ܺ�̨���ã�����ָ̬��û��ָ��
	 */
	@Override
	public void execute(ExecutionContext executionContext) throws Exception {
		
		//���浱ǰ�ڵ��nodeName���Ա������̻��ˣ�
		String virualDesc = "";
		String nowNodeDesc = "";
		Long nodeid = 0l;
		String nodeName = executionContext.getToken().getNode().getName();//��ǰ�ڵ�����
		//�����������õ������¼��id���õ�������󣻸��������¼ID�������������ڵ�����ȷ����̨���ý�ɫ
		Long virtualDefinintionId = (Long)executionContext.getContextInstance().getVariable("VIRTUALDEFINITIONINFO_ID");
		String creator = (String)executionContext.getContextInstance().getVariable(WorkflowConstants.PROCESS_CREATOR_FLAG);
		Map bizParam = (Map)executionContext.getContextInstance().getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
		String vDesc = (String)executionContext.getContextInstance().getVariable("VIRTUALDEFINITIONINFO_DESC");
		Long processInstanceId = (Long)executionContext.getContextInstance().getVariable("testProcessId");
		
		String dataId = (String)bizParam.get("dataId");
		String reqClass = (String)bizParam.get("reqClass");
		String goStartState = (String)bizParam.get("goStartState");
		String applyType = (String)bizParam.get("applyType");
		UserInfo creatorMeg = (UserInfo) service.findUnique(UserInfo.class, "userName", creator);
		
		//�ʼ������е��˵���ʵ����
		String userRN = "";
		
		VirtualDefinitionInfo virtualDefinitionInfo = (VirtualDefinitionInfo) service.find(VirtualDefinitionInfo.class, String.valueOf(virtualDefinintionId));
		if(virtualDefinitionInfo!=null){
			virualDesc = virtualDefinitionInfo.getVirtualDefinitionDesc();	
			NodeInfo nodeInfo = new NodeInfo(executionContext.getNode());
			nowNodeDesc = nodeInfo.getDesc();
			nodeid = nodeInfo.getId();
		}
		String creatorEmail = "";
		log.info(virualDesc+"��"+nodeName+"�Ľ����¼��и�ָ�ɵ����������ʼ��������˷�Ϊ�����֣�");
		ConfigUnitRole unitRole = si.findUnitRoleByNodeTypeAndProDesc(virualDesc, nowNodeDesc, String.valueOf(virtualDefinintionId),nodeid);//��̨���ý�ɫ
		String[] configEmail = null;//��̨���ã�����о͸��ƽ��룬û�о�Ϊ��ֵ
		if(unitRole!=null&&!"".equals(unitRole)){
			
			int isCreator = unitRole.getIsGiveCreate();//��̨�����Ƿ��д�����
			//�������õ���Ӧ��ɫ�ҵ���Ӧ����,Ȼ��Ҫƴ����Ӧ���ַ������Զ��ŷָ���
			List<ConfigUnitRoleTable> list = si.findRoleTableByConfigUnitRole(unitRole);	
			if(list.isEmpty()){
				if(isCreator==1){//˵����̨û�����ý�ɫ�����ˣ���ʱ��Ҫ���������Ƿ��д�����
					
					if(creatorMeg != null){
						creatorEmail = creatorMeg.getEmail();
					}else{
						throw new Exception("������Ϊ�յ����޷�����userinfo�е���Ϣ");
					}
				}else{
					throw new Exception("��̨��ɫ��û������");
				}
			}
			Set user = new HashSet();
			for(ConfigUnitRoleTable roles : list){
				Role role = roles.getRole();
				Set<UserInfo> userinfos=role.getUserInfos();
				for(UserInfo userinfo:userinfos){
					userRN += userinfo.getRealName() + "/" + userinfo.getUserName() + ",";
					if(userinfo.getEmail()!=null&&!"".equals(userinfo.getEmail())){
						user.add(userinfo.getEmail());//��set���Ϸ�ֹ������
					}					
				}							
			} 
			if(creatorEmail!=null&&!"".equals(creatorEmail)){//˵�������˰���������
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
			log.info("***************��һ����Ϊ��̨���õ��ˣ����ҳɹ�");
		}else{
			log.info("(����һ��)"+virualDesc+"(����)�Ƿ��ú�̨������Ӧ��������");
		}
		
		//���Ͼ����ַ�����װ�˺�̨���õ��ˣ����趯ָ̬�ɵ���
		String[] toEmail = null;//��ָ̬���˵�email
		if(!bizParam.isEmpty()){		
			String dynaAssign = (String)bizParam.get("userList");
			if(!"".equals(dynaAssign)&&dynaAssign!=null){	
				if (dynaAssign.contains("$")) {
					String[] nodeUser = dynaAssign.split("\\$");//�ж���ڵ���Ϣ(�����ڵ���������Ӧ��)
					Set user = new HashSet();
					for (String dyNodeName : nodeUser) {
						
						String dynaDesc = dyNodeName.substring(0, dyNodeName.indexOf(":")).trim();//nodeDesc(Ӣ��)
						String dyUserMeg = dyNodeName.substring(dyNodeName.indexOf(":") + 1);
						String[] users = dyUserMeg.split(",");//����Ķ����
						
						if(nowNodeDesc.equals(dynaDesc)){	
							for(int i=0;i<users.length;i++){
								UserInfo info = (UserInfo)service.find(UserInfo.class, "userName", users[i]).get(0);
								userRN += info.getRealName() + "/" + info.getUserName() + ",";
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
					String[] users = dyUserMeg.split(",");//����Ķ����
					
					Set user = new HashSet();
					
					if(nowNodeDesc.equals(dynaDesc)){	
						for(int i=0;i<users.length;i++){
							UserInfo info = (UserInfo)service.find(UserInfo.class, "userName", users[i]).get(0);
							userRN += info.getRealName() + "/" + info.getUserName() + ",";
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
			log.info("***************�ڶ�����Ϊ��ָ̬�ɵ��ˣ����ҳɹ�");
		}else{
			log.info("(����һ��)"+virualDesc+"(����)ҵ�����Ϊ��");
		}	
		//�ϲ���̨���úͶ�ָ̬�ɵ��˵�email��ַ
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
			
			combinEmail = (String[])com.toArray();
		}else if(configEmail!=null&&toEmail==null){//��̨���õ�email��Ϊ�գ�����ָ̬�ɵ�emailΪ��
			 toEmail = configEmail;
		}else if(configEmail==null&&toEmail!=null){//��ָ̬�ɵ�email��Ϊ�գ�����̨���õ�emailΪ��
			combinEmail = toEmail;
				
		}

		log.info("***************Ȼ��ϲ�ǰ�����ֵ�������Ա");
		//�ټ��Ϻ�̨���õĳ�������Ϣ��δ���ǲ��Ǳ������˵������
		String nodeId = String.valueOf(executionContext.getNode().getId());
		ConfigUnitMail unitMail = cs.findMailObjectById(String.valueOf(virtualDefinintionId), nodeId);
		
		String subject = null;
		String content = null;
		String[] ccEmail = null;//�����˵�email��ַ
		
		if(unitMail!=null&&!"".equals(unitMail)){
			subject = unitMail.getSubject();
			content = unitMail.getContent();
			
			Set<UserInfo> userInfos = unitMail.getUserInfos();
			if(userInfos.isEmpty()){
				throw new Exception("�ʼ��������õ�Ԫ��ɫû������");
			}
			ccEmail = new String[userInfos.size()];
			
			List<ConfigUnitMailCC> mailCC = service.find(ConfigUnitMailCC.class, "configUnitMail", unitMail);
			/*******************************************************************************************************************/
			//�ⲿ����˼��������ݿ����û����ʼ�����ô�Ҿ������ݿ⡣����û����������ݿ��У��Ҿ����û��ֶ�������ʼ���ַ
			if(!mailCC.isEmpty()){
				for(int i=0;i<mailCC.size();i++){
					
					ConfigUnitMailCC confirmMailCC = mailCC.get(i);				
					UserInfo userInfo = confirmMailCC.getUserInfo();
					
					if(userInfo!=null&&!"".equals(userInfo)){
						userRN += userInfo.getRealName() + "/" + userInfo.getUserName() + ",";
						String email = userInfo.getEmail();
						if("".equals(email)||email==null){
							email = confirmMailCC.getMail();//������û���д���ʼ���ַ
						}
						ccEmail[i] = email;
					}				
				}		
			}
			
		}
		log.info("***************��������Ϊ��̨���ó��͵��ˣ����ҳɹ�");
		Task task = ((TaskNode)executionContext.getToken().getNode()).getTask(nodeName);
		String url = "<a href="
			+ PropertiesUtil.getProperties("system.web.url",
					"localhost:8080")
			+ "/infoAdmin/workflow/configPage/auditFromMail.jsp?"
			+ "taskId=" + task.getId() + "&dataId=" + dataId
			+ "&reqClass=" + "&goStartState=" + goStartState
			+ "&taskName=" + "&applyType=" + applyType
			+ "&browseFlag=" + false + ">" + "��������</a>";
		String workflowEntity = (String)bizParam.get("workflowHistory");		
		List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, processInstanceId);//���ҳ����������еİ�����˳�����еĽڵ���Ϣ
//		if(auditHis.size()>1){//˵���������̸��ύ
//			for(int i=1;i<auditHis.size();i++){
//				BeanWrapper baseObjectWrapper = new BeanWrapperImpl(auditHis.get(i));
//				String nodeMeg = (String)baseObjectWrapper.getPropertyValue("nodeName");
//				UserInfo user = (UserInfo)baseObjectWrapper.getPropertyValue("approver");
//				String userName = user.getRealName();
//				auditMeg += nodeMeg+"(�ڵ�)"+userName+"����ͨ��!  ;";
//			}
//		}
		//add by guangsa for sendComplexMail in 2009-07-15 end
		/*************************�ٴε�����������*****************************************/
		if(subject==null||"".equals(subject)){
			subject=creator+"�ύ��"+vDesc+"������!";//"����֪ͨ";
		}
		if(content==null||"".equals(content)){
			userRN = userRN.substring(0, userRN.length()-1);
			content= cs.htmlContent(virtualDefinintionId, nodeName, url, applyType, dataId, reqClass, goStartState, task.getId(), creatorMeg, vDesc, auditHis, "", false, userRN);//"ITIL��Ŀ����һ��������Ҫ������";
		}
		//add by guangsa for sendComplexMail in 2009-07-17 begin
		//String context = "" this.htmlContent(creator, vDesc, auditHis);
		try{
			ms.sendMimeMail(combinEmail, ccEmail, null, subject, content, null);
		}catch(Exception e){
			log.info(virualDesc+"(����)��"+nodeName+"(�ڵ�)�������ֵ������˷����ʼ�ʱ�����쳣��");
			e.printStackTrace();
		}

		log.info(virualDesc+"��"+nodeName+"(�ڵ�)�������ֵ������˷����ʼ��ɹ���");
		
	}

}
