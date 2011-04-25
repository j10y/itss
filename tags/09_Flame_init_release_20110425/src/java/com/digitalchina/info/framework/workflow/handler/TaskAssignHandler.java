//package com.digitalchina.info.framework.workflow.handler;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.log4j.Logger;
//import org.jbpm.context.exe.ContextInstance;
//import org.jbpm.graph.def.Node;
//import org.jbpm.graph.exe.ExecutionContext;
//import org.jbpm.taskmgmt.def.AssignmentHandler;
//import org.jbpm.taskmgmt.exe.Assignable;
//
//import com.digitalchina.info.framework.context.ContextHolder;
//import com.digitalchina.info.framework.security.entity.Role;
//import com.digitalchina.info.framework.security.entity.UserInfo;
//import com.digitalchina.info.framework.service.Service;
//import com.digitalchina.info.framework.workflow.TaskAssignService;
//import com.digitalchina.info.framework.workflow.WorkflowConstants;
//import com.digitalchina.info.framework.workflow.entity.ConfigUnitRole;
//import com.digitalchina.info.framework.workflow.entity.ConfigUnitRoleTable;
//import com.digitalchina.info.framework.workflow.entity.VirtualDefinitionInfo;
//
//public class TaskAssignHandler implements AssignmentHandler {
//
//	private static final long serialVersionUID = -7197003572477964635L;
//	private TaskAssignService si=(TaskAssignService) ContextHolder.getBean("taskAssignService");
//	private Service service = (Service) ContextHolder.getBean("baseService");
//	private static Logger log;
//	static 
//	{
//		log = Logger.getLogger("workflowlog");
//	}
//	
//	public void assign(Assignable assignable, ExecutionContext context) throws Exception {
//		
//		ContextInstance ci = context.getContextInstance();
//		String creator = (String)ci.getVariable(WorkflowConstants.PROCESS_CREATOR_FLAG);
//		Node node = context.getNode();
//		Long nodeId = node.getId();
//		String nodeDesc = node.getDescription();
//		String nodeName = node.getName();
//		String virtualDefinintionId = String.valueOf(ci.getVariable("VIRTUALDEFINITIONINFO_ID"));
//		VirtualDefinitionInfo virtualDefinitionInfo = (VirtualDefinitionInfo)service.find(VirtualDefinitionInfo.class, virtualDefinintionId);
//		String processName = virtualDefinitionInfo.getVirtualDefinitionDesc();
//		
//		log.info(processName+"(����)"+nodeName+"��ʼ��������Ԥָ��");
//		/*������Ҫ�ܵ���һ���ڵ��ʱ����Ҫָ��һ��������ָ��*/
//		//Map assignPerson = (Map)ci.getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
//		
//		String addDynAssign = (String)assignPerson.get("addDynAssignPer");
//		//�����Ӷ�ָ̬�ɵ�����
//		ConfigUnitRole unitRole = si.findUnitRoleByNodeTypeAndProDesc(processName, nodeDesc,virtualDefinintionId,nodeId);
//		List<ConfigUnitRoleTable> list = si.findRoleTableByConfigUnitRole(unitRole);
//		if(!"".equals(addDynAssign)&&addDynAssign!=null){
//			if (addDynAssign.contains("$")) {
//				String[] nodeUser = addDynAssign.split("\\$");
//				boolean mark = false;
//				for (String dyNodeName : nodeUser) {
//					String dynaName = dyNodeName.substring(0, dyNodeName.indexOf(":")).trim();
//					String dyMeg = dyNodeName.substring(dyNodeName.indexOf(":") + 1);
//					String[] users = dyMeg.split(",");						
//					if(nodeDesc.equals(dynaName)){
//						mark=true;
//						//��ǰ�ƶ����ˣ���ô����Ҫ�Ѻ�̨���˼ӽ�ȥ
//						List userInfos = this.takeConfigPerson(list, unitRole, creator);
//						if(userInfos.size()!=0){
//							for(int i=0 ;i<users.length;i++){
//								userInfos.add(users[i]);
//							}
//							String[] obj = (String[])userInfos.toArray(new String[0]);//��һ��list���һ������
//							assignable.setPooledActors(obj);
//							log.info(processName+"(����)"+nodeName+"Ϊ�����׷��Ԥָ������ڵ㣬�����Ѿ���Ԥָ����Ա�ɹ�ָ��");
//						}else{
//							assignable.setPooledActors(users);	
//							log.info(processName+"(����)"+nodeName+"Ϊ�����׷��Ԥָ������ڵ㣬�����Ѿ���Ԥָ����Ա�ɹ�ָ��");
//						}
//					}
//				}
//				if(!mark){
//					//��ǰ�ڵ㲻�Ǽ��ض�ָ̬�ɽڵ㣬�����ж��ǲ���������ָ̬�����
//					log.info(processName+"(����)����"+nodeName+"(�ڵ�)Ϊֹ��û�н������ӵĶ�ָ̬�ɣ����к�̨���õ��˼��϶�ָ̬�ɵ���");
//					this.dynAssignHandler(assignable, context);
//				}
//			}else{
//				String assignNodeName = addDynAssign.substring(0, addDynAssign.indexOf(":")).trim();
//				if(nodeDesc.equals(assignNodeName)){
//					String somePer =addDynAssign.substring(addDynAssign.indexOf(":")+1);
//					String[] personStrings = somePer.split(",");
//					List userInfos = this.takeConfigPerson(list, unitRole, creator);
//					for(int i=0 ;i<personStrings.length;i++){
//						userInfos.add(personStrings[i]);
//					}
//					String[] obj = (String[])userInfos.toArray(new String[0]);
//					assignable.setPooledActors(obj);
//					log.info(processName+"(����)"+nodeName+"Ϊ�����׷��Ԥָ������ڵ㣬�����Ѿ���Ԥָ����Ա�ɹ�ָ��");
//				}else{
//					//��ǰ�ڵ㲻�Ǽ��ض�ָ̬�ɽڵ㣬�����ж��ǲ���������ָ̬�����
//					log.info(processName+"(����)����"+nodeName+"(�ڵ�)Ϊֹ��û�н������ӵĶ�ָ̬�ɣ����к�̨���õ��˼��϶�ָ̬�ɵ���");
//					this.dynAssignHandler(assignable, context);
//				}			
//			}
//		}else{
//			//��ǰ�ڵ㲻�Ǽ��ض�ָ̬�ɽڵ㣬�����ж��ǲ���������ָ̬�����
//			log.info(processName+"(����)����"+nodeName+"(�ڵ�)Ϊֹ��û�н������ӵĶ�ָ̬�ɣ����к�̨���õ��˼��϶�ָ̬�ɵ���");
//			this.dynAssignHandler(assignable, context);
//		}
//		
//	}
//	/**
//	 * �õ���̨���õ���Ա
//	 * @return
//	 */
//	public List takeConfigPerson(List<ConfigUnitRoleTable> list,ConfigUnitRole unitRole,String creator){
//		
//		Integer createFlag = 0;
//		
//		List configPer = new ArrayList();
//		if(unitRole!=null&&!"".equals(unitRole)){
//			createFlag = unitRole.getIsGiveCreate();
//		}
//		if(list.size()!=0){
//			Set user = new HashSet();
//			for(ConfigUnitRoleTable roles : list){
//				Role role = roles.getRole();
//				Set<UserInfo> userinfos=role.getUserInfos();
//				for(UserInfo userinfo:userinfos){
//					user.add(userinfo.getUserName());
//				}							
//			}	
//			if(user.size()==0){
//				int isCreator = unitRole.getIsGiveCreate();
//				if(isCreator==1){//˵������
//					configPer.add(creator);
//				}else{
//					//��������Ѿ���audit��ʱ�������жϣ��������
//				}
//			}else{
//				Iterator ite = user.iterator();
//				while(ite.hasNext()){
//					String use = (String)ite.next();
//					configPer.add(use);
//				}
//				if(createFlag==1){//˵��Ҫ�������˼��뵽��ǰ�ڵ㣬�������˳�Ϊ������֮һ
//					if(configPer.contains(creator)){
//						//˵������������
//					}else{
//						configPer.add(creator);
//					}
//				}else{
//					//˵��û�а������˼��뵽�������е���
//				}
//			}
//		}else{
//			if(createFlag==1){//˵��Ҫ�������˼��뵽��ǰ�ڵ㣬�������˳�Ϊ������֮һ
//				configPer.add(creator);
//			}else{
//				//������������ܳ��֣���Ϊ��������ʱ���Ѿ�������Ӧ�����޽�ɫ�ж�
//			}
//		}
//		return configPer;
//	}
//	/**
//	 * ���ǵ����Ķ�ָ̬�����
//	 * @param assignable
//	 * @param context
//	 */
//	public void dynAssignHandler(Assignable assignable,ExecutionContext context){
//		/*�ڵ�������Ӧ�Ľ�ɫ*/
//		ContextInstance ci = context.getContextInstance();
//		String creator = (String)ci.getVariable(WorkflowConstants.PROCESS_CREATOR_FLAG);
//		
//		Node node = context.getNode();
//		Long nodeId = node.getId();
//		String nodeDesc = node.getDescription();
//		String nodeName = node.getName();
//		String virtualDefinintionId = String.valueOf(ci.getVariable("VIRTUALDEFINITIONINFO_ID"));
//		VirtualDefinitionInfo virtualDefinitionInfo = (VirtualDefinitionInfo)service.find(VirtualDefinitionInfo.class, virtualDefinintionId);
//		String processName = virtualDefinitionInfo.getVirtualDefinitionDesc();
//		/*������Ҫ�ܵ���һ���ڵ��ʱ����Ҫָ��һ��������ָ��*/
//		Map assignPerson = (Map)ci.getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
//		
//		String dynaAssign = (String)assignPerson.get("userList");//���Ƕ�ָ̬�ɵĲ���
//		/**
//		 * 1.�����ж��û���û�����ύ����ʱ���ƶ�����Ӧ�ڵ�������
//		 * 2.���ڶ�ָ̬�ɵ����ȼ�Ϊ:�����Ҷ�ָ̬�ɵ���Ա��Ȼ�����ҵ��Ǻ�̨���ý�ɫ��������
//		 */
//		//�����������ֺ��������͵���Ӧ�Ľ�ɫ���õ�Ԫ��ȥ��������¼;Ȼ���ٸ����ҵ���Ӧ�Ľ�ɫ,��ֹ���ϰ汾ȡ�������ɫ������Ϣ
//		ConfigUnitRole unitRole = si.findUnitRoleByNodeTypeAndProDesc(processName, nodeDesc,virtualDefinintionId,nodeId);
//		List<ConfigUnitRoleTable> list = si.findRoleTableByConfigUnitRole(unitRole);
//		if(!"".equals(dynaAssign)&&dynaAssign!=null){
//			//��ָ̬����ָ���˶��˽ڵ�
//			if (dynaAssign.contains("$")) {
//				String[] nodeUser = dynaAssign.split("\\$");
//				boolean mark = false;
//				for (String dyNodeName : nodeUser) {
//					String dynaName = dyNodeName.substring(0, dyNodeName.indexOf(":")).trim();
//					String dyMeg = dyNodeName.substring(dyNodeName.indexOf(":") + 1);
//					String[] users = dyMeg.split(",");						
//					if(nodeDesc.equals(dynaName)){
//						mark=true;
//						assignable.setPooledActors(users);	
//						log.info(processName+"(����)"+nodeName+"Ϊ��ͨ��Ԥָ������ڵ㣬�����Ѿ���Ԥָ����Ա�ɹ�ָ��");
//					}					
//				}
//				if(mark==false){//��ָ̬��û��ָ�ɵ�ǰ�ڵ�
//					
//					//�ֱ���һЩ�Ӻ�̨ȡ���ݵĲ���
//					this.assignConfigPersonToActorPool(unitRole, list, assignable, creator);
//					//�ƶ���ǩ��json��
//					String json = this.overSign(list);
//					assignPerson.put("signerUser", json);//��ǩ����Ա
//					ci.setVariable(WorkflowConstants.BUSINESS_PARAM_KEY,assignPerson);
//				}
//			}else{//��ָ̬����ָֻ����һ���ڵ�
//				String assignNodeName = dynaAssign.substring(0, dynaAssign.indexOf(":")).trim();
//					if(nodeDesc.equals(assignNodeName)){
//						String somePer =dynaAssign.substring(dynaAssign.indexOf(":")+1);
//						String[] personStrings = somePer.split(",");
//						assignable.setPooledActors(personStrings);
//						log.info(processName+"(����)"+nodeName+"Ϊ��ͨ��Ԥָ������ڵ㣬�����Ѿ���Ԥָ����Ա�ɹ�ָ��");
//					}else{
//						//�ֱ���һЩ�Ӻ�̨ȡ���ݵĲ���
//						this.assignConfigPersonToActorPool(unitRole, list, assignable, creator);
//						String json = this.overSign(list);
//						assignPerson.put("signerUser", json);//��ǩ����Ա
//						ci.setVariable(WorkflowConstants.BUSINESS_PARAM_KEY,assignPerson);
//					}			
//			}			
//		}else{
//			log.info(processName+"(����)����"+nodeName+"(�ڵ�)ΪֹҲû�н�����ͨ�Ķ�ָ̬�ɣ����ж�ָ̬�ɵ����������̨���õ���");
//			this.assignConfigPersonToActorPool(unitRole, list, assignable, creator);
//			//ȥ�أ��Ա��ڼ�ǩ������
//			String json = this.overSign(list);
//			assignPerson.put("signerUser", json);//��ǩ����Ա
//			ci.setVariable(WorkflowConstants.BUSINESS_PARAM_KEY,assignPerson);
//		}
//	}
//	/**
//	 * ����к�̨���ݣ�ȡ��̨����
//	 * @param unitRole
//	 * @param list
//	 * @param assignable
//	 * @param creator
//	 */
//	public void assignConfigPersonToActorPool(ConfigUnitRole unitRole,List<ConfigUnitRoleTable> list,Assignable assignable,String creator){
//		
//		Integer createFlag = 0;
//		if(unitRole!=null&&!"".equals(unitRole)){
//			createFlag = unitRole.getIsGiveCreate();
//		}
//		if(list.size()!=0){
//			/*Ȼ����ͨ����ɫ��Ӧ��ÿһ����,Ȼ���ٰ�ÿһ���˶��ӵ�����ȥ*/
//			Set<UserInfo> user = new HashSet();
//			for(ConfigUnitRoleTable roles : list){
//				Role role = roles.getRole();
//				Set<UserInfo> userinfos=role.getUserInfos();
//				for(UserInfo userinfo:userinfos){
//					user.add(userinfo);
//				}							
//			}	
//			/*�ŵ�������*/
//			if(user.size()==0){
//				int isCreator = unitRole.getIsGiveCreate();
//				if(isCreator==1){//˵������
//					assignable.setActorId(creator);
//				}else{
//					//��������Ѿ���audit��ʱ�������жϣ��������
//				}
//			}else{			
//				String[] str = new String[user.size()];
//				int i=0;
//				for(UserInfo u:user){
//					str[i]=u.getUserName();
//					i++;
//				}
//				boolean flag = false;
//				if(createFlag==1){//˵��Ҫ�������˼��뵽��ǰ�ڵ㣬�������˳�Ϊ������֮һ
//					for(int j=0;j<str.length;j++){
//						if(creator.equals(str[j])){
//							flag = true;//˵�����������������
//							break;
//						}
//					}
//					if(flag==true){
//						assignable.setPooledActors(str);
//					}else{
//						String[] realStr = new String[str.length+1];
//						for(int k = 0;k<realStr.length-1;k++){
//							realStr[k] = str[k];
//						}
//						realStr[str.length]=creator;
//						assignable.setPooledActors(realStr);
//					}
//				}else{
//					assignable.setPooledActors(str);
//				}
//			}
//		}else{
//			if(createFlag==1){//˵��Ҫ�������˼��뵽��ǰ�ڵ㣬�������˳�Ϊ������֮һ
//				assignable.setActorId(creator);
//			}else{
//				//������������ܳ��֣���Ϊ��������ʱ���Ѿ�������Ӧ�����޽�ɫ�ж�
//			}
//		}
//		log.info("��ǰ�ڵ������Ԥָ��ָ�����");
//	}
//	
//	
//	
//	/**
//	 * ȥ��ÿ����ɫ�е���
//	 * @param list
//	 * @return
//	 */
//	public String overSign(List<ConfigUnitRoleTable> list){
//		//�ѽ�ɫ�Ͷ�Ӧ����һһ��Ӧ����
//		Map<ConfigUnitRoleTable,List> totalRole = new HashMap<ConfigUnitRoleTable,List>();			
//		//�Ȱѽ�ɫ�Ͷ�Ӧ���˶��ŵ�һ��map����
//		for(ConfigUnitRoleTable roles : list){
//			List allUser = new ArrayList(); 
//			Role role = roles.getRole();
//			Set<UserInfo> userinfos=role.getUserInfos();
//			for(UserInfo userinfo:userinfos){
//				allUser.add(userinfo.getUserName());
//			}
//			totalRole.put(roles, allUser);
//		}		
//		//����map��ȡ����һ��ֵ���Ҵ�map��ȥ���������±���һ��map�õ����ǵ�һ��ֵ��������
//		//�õ���ȥȡ�����˺�ʣ����˱Ƚϣ��ж���û���ظ������ظ���ȥ��
//		 Map<ConfigUnitRoleTable,List>  personMap = new HashMap<ConfigUnitRoleTable,List> ();
//	      Set<ConfigUnitRoleTable> personSet = totalRole.keySet();
//	      Iterator it=personSet.iterator();
//	      while(it.hasNext()){
//	       ConfigUnitRoleTable key = (ConfigUnitRoleTable)it.next();
//	       List person = (List)totalRole.get(key);
//	       personMap.put(key, person);
//	      }
//	    //�ѽ�ɫ���������·�����һ��personMap�У�Ϊ�˷���ȥ�أ������ǲ����ڱ�����ʱ��ɾ������Ԫ�ص�
//	      Set<ConfigUnitRoleTable> totalSet = totalRole.keySet();
//	      Iterator ite = totalSet.iterator();
//	      while(ite.hasNext()){
//	       ConfigUnitRoleTable key = (ConfigUnitRoleTable)ite.next();
//	       List person = (List)totalRole.get(key);
//	       personMap.remove(key);
//	       
//	       Set<ConfigUnitRoleTable> remain = personMap.keySet();
//	          List rePerson = new ArrayList();
//	          for(ConfigUnitRoleTable rePer : remain){
//	           List remainPer = (List)totalRole.get(rePer);
//	           rePerson.addAll(remainPer);//ʣ�����е���
//	          }
//	          
//	         for(int i=0;i<person.size();i++){
//	          if(rePerson.contains(person.get(i))){
//	           person.remove(person.get(i));
//	          }
//	         }
//	         personMap.put(key, person);
//		
//	      }
//	      //����õ��������еĲ��ظ��Ľ�ɫ����Ӧ����
//		  String json = "";
//		  Set<ConfigUnitRoleTable> total = personMap.keySet();
//		  Iterator ites = total.iterator();
//		  List allPerson = new ArrayList();
//		  while(ites.hasNext()){
//			  ConfigUnitRoleTable roleTable = (ConfigUnitRoleTable)ites.next();
//			  allPerson = (List)personMap.get(roleTable);
//			  json += roleTable.getRole().getName()+"+";
//			  json += roleTable.getFlag()+":";
//			  for(int i=0;i<allPerson.size();i++){
//				 
//				  json += allPerson.get(i);
//				  json += "|";
//			  }
//			  if(json.endsWith("|")){
//				  json = json.substring(0, json.length()-1);
//			  }
//			  json += "$";
//		  }
//		  if(json.endsWith("$")){
//			  json = json.substring(0, json.length()-1);
//		  }
//		  return json;
//	}
//	
//	public static void main(String[] argv) {
//		String ss = "ss|";
//		int a = ss.indexOf('|');
//		int b = a;
//		String[] sa = ss.split("\\|");
//		String[] sa1 = sa;
//	}
//
//}
