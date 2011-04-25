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
//		log.info(processName+"(流程)"+nodeName+"开始进行任务预指派");
//		/*当流程要跑到下一个节点的时候需要指派一个人做的指派*/
//		//Map assignPerson = (Map)ci.getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
//		
//		String addDynAssign = (String)assignPerson.get("addDynAssignPer");
//		//有增加动态指派的描述
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
//						//当前制定了人，那么还需要把后台的人加进去
//						List userInfos = this.takeConfigPerson(list, unitRole, creator);
//						if(userInfos.size()!=0){
//							for(int i=0 ;i<users.length;i++){
//								userInfos.add(users[i]);
//							}
//							String[] obj = (String[])userInfos.toArray(new String[0]);//把一个list变成一个数组
//							assignable.setPooledActors(obj);
//							log.info(processName+"(流程)"+nodeName+"为特殊的追加预指派任务节点，并且已经把预指派人员成功指派");
//						}else{
//							assignable.setPooledActors(users);	
//							log.info(processName+"(流程)"+nodeName+"为特殊的追加预指派任务节点，并且已经把预指派人员成功指派");
//						}
//					}
//				}
//				if(!mark){
//					//当前节点不是加载动态指派节点，则再判断是不是其它动态指派情况
//					log.info(processName+"(流程)，到"+nodeName+"(节点)为止还没有进行增加的动态指派，即有后台配置的人加上动态指派的人");
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
//					log.info(processName+"(流程)"+nodeName+"为特殊的追加预指派任务节点，并且已经把预指派人员成功指派");
//				}else{
//					//当前节点不是加载动态指派节点，则再判断是不是其它动态指派情况
//					log.info(processName+"(流程)，到"+nodeName+"(节点)为止还没有进行增加的动态指派，即有后台配置的人加上动态指派的人");
//					this.dynAssignHandler(assignable, context);
//				}			
//			}
//		}else{
//			//当前节点不是加载动态指派节点，则再判断是不是其它动态指派情况
//			log.info(processName+"(流程)，到"+nodeName+"(节点)为止还没有进行增加的动态指派，即有后台配置的人加上动态指派的人");
//			this.dynAssignHandler(assignable, context);
//		}
//		
//	}
//	/**
//	 * 得到后台配置的人员
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
//				if(isCreator==1){//说明有人
//					configPer.add(creator);
//				}else{
//					//这种情况已经在audit的时候做了判断，不会出现
//				}
//			}else{
//				Iterator ite = user.iterator();
//				while(ite.hasNext()){
//					String use = (String)ite.next();
//					configPer.add(use);
//				}
//				if(createFlag==1){//说明要把申请人加入到当前节点，让申请人成为审批人之一
//					if(configPer.contains(creator)){
//						//说明包含申请人
//					}else{
//						configPer.add(creator);
//					}
//				}else{
//					//说明没有把申请人加入到审批人中当中
//				}
//			}
//		}else{
//			if(createFlag==1){//说明要把申请人加入到当前节点，让申请人成为审批人之一
//				configPer.add(creator);
//			}else{
//				//这种情况不可能出现，因为在审批的时候已经做了相应的有无角色判断
//			}
//		}
//		return configPer;
//	}
//	/**
//	 * 这是单纯的动态指派情况
//	 * @param assignable
//	 * @param context
//	 */
//	public void dynAssignHandler(Assignable assignable,ExecutionContext context){
//		/*节点配置相应的角色*/
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
//		/*当流程要跑到下一个节点的时候需要指派一个人做的指派*/
//		Map assignPerson = (Map)ci.getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
//		
//		String dynaAssign = (String)assignPerson.get("userList");//这是动态指派的参数
//		/**
//		 * 1.首先判断用户有没有在提交流程时候制定好相应节点审批人
//		 * 2.现在动态指派的优先级为:首先找动态指派的人员，然后在找的是后台配置角色和申请人
//		 */
//		//根据流程名字和流程类型到相应的角色配置单元中去找这条记录;然后再根据找到相应的角色,防止新老版本取到多个角色配置信息
//		ConfigUnitRole unitRole = si.findUnitRoleByNodeTypeAndProDesc(processName, nodeDesc,virtualDefinintionId,nodeId);
//		List<ConfigUnitRoleTable> list = si.findRoleTableByConfigUnitRole(unitRole);
//		if(!"".equals(dynaAssign)&&dynaAssign!=null){
//			//动态指派中指派了多了节点
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
//						log.info(processName+"(流程)"+nodeName+"为普通的预指派任务节点，并且已经把预指派人员成功指派");
//					}					
//				}
//				if(mark==false){//动态指派没有指派当前节点
//					
//					//分别做一些从后台取数据的操作
//					this.assignConfigPersonToActorPool(unitRole, list, assignable, creator);
//					//制定会签的json串
//					String json = this.overSign(list);
//					assignPerson.put("signerUser", json);//会签的人员
//					ci.setVariable(WorkflowConstants.BUSINESS_PARAM_KEY,assignPerson);
//				}
//			}else{//动态指派中只指派了一个节点
//				String assignNodeName = dynaAssign.substring(0, dynaAssign.indexOf(":")).trim();
//					if(nodeDesc.equals(assignNodeName)){
//						String somePer =dynaAssign.substring(dynaAssign.indexOf(":")+1);
//						String[] personStrings = somePer.split(",");
//						assignable.setPooledActors(personStrings);
//						log.info(processName+"(流程)"+nodeName+"为普通的预指派任务节点，并且已经把预指派人员成功指派");
//					}else{
//						//分别做一些从后台取数据的操作
//						this.assignConfigPersonToActorPool(unitRole, list, assignable, creator);
//						String json = this.overSign(list);
//						assignPerson.put("signerUser", json);//会签的人员
//						ci.setVariable(WorkflowConstants.BUSINESS_PARAM_KEY,assignPerson);
//					}			
//			}			
//		}else{
//			log.info(processName+"(流程)，到"+nodeName+"(节点)为止也没有进行普通的动态指派，即有动态指派的人则无需后台配置的人");
//			this.assignConfigPersonToActorPool(unitRole, list, assignable, creator);
//			//去重，以便于加签人审批
//			String json = this.overSign(list);
//			assignPerson.put("signerUser", json);//会签的人员
//			ci.setVariable(WorkflowConstants.BUSINESS_PARAM_KEY,assignPerson);
//		}
//	}
//	/**
//	 * 如果有后台数据，取后台数据
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
//			/*然后在通过角色对应到每一个人,然后再把每一个人都加到里面去*/
//			Set<UserInfo> user = new HashSet();
//			for(ConfigUnitRoleTable roles : list){
//				Role role = roles.getRole();
//				Set<UserInfo> userinfos=role.getUserInfos();
//				for(UserInfo userinfo:userinfos){
//					user.add(userinfo);
//				}							
//			}	
//			/*放到这里面*/
//			if(user.size()==0){
//				int isCreator = unitRole.getIsGiveCreate();
//				if(isCreator==1){//说明有人
//					assignable.setActorId(creator);
//				}else{
//					//这种情况已经在audit的时候做了判断，不会出现
//				}
//			}else{			
//				String[] str = new String[user.size()];
//				int i=0;
//				for(UserInfo u:user){
//					str[i]=u.getUserName();
//					i++;
//				}
//				boolean flag = false;
//				if(createFlag==1){//说明要把申请人加入到当前节点，让申请人成为审批人之一
//					for(int j=0;j<str.length;j++){
//						if(creator.equals(str[j])){
//							flag = true;//说明里面包含着申请人
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
//			if(createFlag==1){//说明要把申请人加入到当前节点，让申请人成为审批人之一
//				assignable.setActorId(creator);
//			}else{
//				//这种情况不可能出现，因为在审批的时候已经做了相应的有无角色判断
//			}
//		}
//		log.info("当前节点的任务预指派指派完毕");
//	}
//	
//	
//	
//	/**
//	 * 去重每个角色中的人
//	 * @param list
//	 * @return
//	 */
//	public String overSign(List<ConfigUnitRoleTable> list){
//		//把角色和对应的人一一对应以来
//		Map<ConfigUnitRoleTable,List> totalRole = new HashMap<ConfigUnitRoleTable,List>();			
//		//先把角色和对应的人都放到一个map当中
//		for(ConfigUnitRoleTable roles : list){
//			List allUser = new ArrayList(); 
//			Role role = roles.getRole();
//			Set<UserInfo> userinfos=role.getUserInfos();
//			for(UserInfo userinfo:userinfos){
//				allUser.add(userinfo.getUserName());
//			}
//			totalRole.put(roles, allUser);
//		}		
//		//遍历map，取出第一个值并且从map中去除掉，重新遍历一边map得到不是第一个值的所有人
//		//让单独去取出的人和剩余的人比较，判断有没有重复，有重复就去掉
//		 Map<ConfigUnitRoleTable,List>  personMap = new HashMap<ConfigUnitRoleTable,List> ();
//	      Set<ConfigUnitRoleTable> personSet = totalRole.keySet();
//	      Iterator it=personSet.iterator();
//	      while(it.hasNext()){
//	       ConfigUnitRoleTable key = (ConfigUnitRoleTable)it.next();
//	       List person = (List)totalRole.get(key);
//	       personMap.put(key, person);
//	      }
//	    //把角色和人又重新放入另一个personMap中，为了方便去重，集合是不能在遍历的时候删除集合元素的
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
//	           rePerson.addAll(remainPer);//剩余所有的人
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
//	      //上面得到的是所有的不重复的角色和相应的人
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
