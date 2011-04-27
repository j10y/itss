package com.zsgj.info.framework.workflow.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.exception.RuleFileException;
import com.zsgj.info.framework.message.mail.service.MailSenderService;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.PropertiesUtil;
import com.zsgj.info.framework.workflow.WorkFlowGoBackService;
import com.zsgj.info.framework.workflow.WorkflowConstants;
import com.zsgj.info.framework.workflow.entity.RuleConfigUnit;
import com.zsgj.info.framework.workflow.entity.WorkflowRegressionParameters;
import com.zsgj.info.framework.workflow.rules.ProcessRuleHelper;


public class RuleActionHandler extends BaseActionHandler implements ActionHandler,WorkflowConstants{
	private static Logger log;
	private Service service = (Service) ContextHolder.getBean("baseService");
	private WorkFlowGoBackService wfBack = (WorkFlowGoBackService) ContextHolder.getBean("workflowGoBackService");
	private MailSenderService ms = (MailSenderService)ContextHolder.getBean("mailSenderService");
	static 
	{
		log = Logger.getLogger("workflowlog");
	}
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -5941772379061882906L;

	/**
	 * 所有action的执行接口
	 * 同一个流程的node节点调这个action都去读同一个规则文件
	 * 决定转向那个节点
	 * @throws Exception 
	 * 
	 * 
	 */
	public void execute(ExecutionContext executionContext) throws Exception{
		//前期数据准备
		ContextInstance ci = executionContext.getContextInstance();
		String nodeName = executionContext.getToken().getNode().getName();//当前节点名称
		String nodeDesc = executionContext.getToken().getNode().getDescription();//当前节点描述
		String nodeType = executionContext.getToken().getNode().toString();
		Token token = executionContext.getToken();
		Long processId=(Long)executionContext.getProcessInstance().getContextInstance().getVariable("VIRTUALDEFINITIONINFO_ID");
		String vProcessName = (String)executionContext.getProcessInstance().getContextInstance().getVariable("VIRTUALDEFINITIONINFO_NAME");
		String vProcessDesc = (String)executionContext.getProcessInstance().getContextInstance().getVariable("VIRTUALDEFINITIONINFO_DESC");
		Map mapParams=(Map)executionContext.getProcessInstance().getContextInstance().getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
		Long processInstanceId = executionContext.getProcessInstance().getId();
		Long nodeId=executionContext.getNode().getId();
		String paramId = "";
		
		log.info(vProcessDesc+"(流程)"+nodeName+"(节点)是"+nodeType+",流程开始读取规则文件"+"(RuleActionHandler的execute()方法)");
		/*************************首先判断是否第一次进入当前节点******************************************/
		WorkflowRegressionParameters regParam = wfBack.findWorkflowRegressionParametersByMuitlyId(processId, processInstanceId, nodeId);
		if(regParam==null||"".equals(regParam)){
			//如果是第一次进入当前节点，只需要往库中记录当前节点的参数
			try{
				regParam = wfBack.saveWorkflowRegressionParams(processId, processInstanceId, nodeId,nodeName, nodeDesc,mapParams);
			}catch(Exception e){//这个地方不用删除节点参数，因为这是保存时报的异常，所以说明没有保存成功
				log.error(e.getMessage());
				this.handlerSaveExceptionMethod(nodeType, ci, token, vProcessName, nodeName, e,processId,processInstanceId,nodeId);
				throw new RuntimeException(e.getMessage());
			}			
		}else{
			//如果不是第一次进入当前节点，需要从库中取出参数并覆盖原来的业务参数
			String bizParam = regParam.getRegressionParams();
			Map nowNodeBizParam = new HashMap();
			//参数格式：{key+value;key+value;key+value;+key+value}
			String[] mutils = bizParam.split("\\;");
			for(int i=0;i<mutils.length;i++){
				String[] single = mutils[i].split("\\+");
				nowNodeBizParam.put(single[0], single[1]);
			}
			if(!nowNodeBizParam.isEmpty()){
//				executionContext.getProcessInstance().getContextInstance().deleteVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
//				executionContext.getProcessInstance().getContextInstance().setVariable(WorkflowConstants.BUSINESS_PARAM_KEY, nowNodeBizParam);
			}else{
				//如果是空
				log.info(nodeName+"节点业务参数为空，请检查是否有误");
			}
		}
		
		paramId = String.valueOf(regParam.getId());
		String nowNodeMessage = paramId+"+"+nodeName;
		List goBack = (List)ci.getVariable("goBack");
		if(goBack!=null&&!"".equals(goBack)){
			goBack.add(nowNodeMessage);
			ci.setVariable("goBack", goBack);
		}else{
			goBack = new ArrayList();
			goBack.add(nowNodeMessage);
			ci.setVariable("goBack", goBack);
		}		
		/**********************************保存节点信息为回退流程作准备*****************************************************************/
		//从流程实例上下文中取出rulePath，与业务参数
		String rulePath=(String)executionContext.getProcessInstance().getContextInstance().getVariable("rulePath");
		List<RuleConfigUnit> list=service.findAll(RuleConfigUnit.class);
		String ruleName=null;
		for(RuleConfigUnit rc : list){
			if(nodeId.equals(rc.getNodeId())&&processId.equals(rc.getProcessId())){
				ruleName=rc.getRuleName();
			}
		}
		if(ruleName!=null&&!"".equals(ruleName)){
			mapParams.put("ruleName",ruleName);
			mapParams.put("nodeId",String.valueOf(nodeId));
			mapParams.put("nodeName",nodeName);
			
			String transitionName=null;
			if(rulePath!=null){//调规则文件中的规则，规则应该往上抛
				try{
					transitionName=ProcessRuleHelper.executeRule(rulePath, mapParams);
				}catch(RuleFileException e){
					this.handlerOtherExceptionMethod(nodeType, ci, token, vProcessName, nodeName, e,processId,processInstanceId,nodeId);
					throw new RuleFileException(e.getMessage().substring(e.getMessage().indexOf(":")+1,e.getMessage().length()));
				}catch(Exception e){
					this.handlerOtherExceptionMethod(nodeType, ci, token, vProcessName, nodeName, e,processId,processInstanceId,nodeId);
					throw new RuntimeException(vProcessDesc+"(流程)"+nodeName+"(节点),读取规则文件是发生异常");
				}
			}
		
			if(transitionName!=null&&!"".equals(transitionName)){
				try{
					if(transitionName.equalsIgnoreCase("NOLEAVE")){
						//在开始节点leave事件时什么也不执行,这个不能用executeContext.leaveNode,否则会出现死循环
						log.info(nodeName+"是"+nodeType+",流程结束读取规则文件"+"(RuleActionHandler的execute()方法)，朝转向为NOLEAVE流转");
						//throw new RuntimeException("结束节点在删除当前流程的每步业务参的时候发生异常，请检查核实！");
					}else if(executionContext.getNode().toString().indexOf("EndState")==0){
						//如果是结束节点就把当前流程实例的表中的参数去掉
						try{
							wfBack.removeWorkflowRegressionParametersByProcessId(processId, processInstanceId);
							log.info(nodeName+"是"+nodeType+",流程结束读取规则文件"+"(RuleActionHandler的execute()方法)");
						}catch(Exception e){
							this.handlerOtherExceptionMethod(nodeType, ci, token, vProcessName, nodeName, e,processId,processInstanceId,nodeId);
							throw new RuntimeException(e.getMessage());
						}
						
					}else{
						try{
							executionContext.leaveNode(transitionName);
							log.info(nodeName+"是"+nodeType+",流程结束读取规则文件"+"(RuleActionHandler的execute()方法)，朝转向为"+transitionName+"流转");
						}catch(Exception e){
							this.handlerOtherExceptionMethod(nodeType, ci, token, vProcessName, nodeName, e,processId,processInstanceId,nodeId);
							throw new RuntimeException(e.getMessage());
						}
					}
				}catch(Exception e){
					throw new RuntimeException(e.getMessage());
				}
					
			}else{
				if(executionContext.getNode().toString().indexOf("EndState")==0){
					//如果是结束节点就把当前流程实例的表中的参数去掉
					try{
						wfBack.removeWorkflowRegressionParametersByProcessId(processId, processInstanceId);
						log.info(nodeName+"是"+nodeType+",流程结束读取规则文件"+"(RuleActionHandler的execute()方法)");
					}catch(Exception e){
						this.handlerOtherExceptionMethod(nodeType, ci, token, vProcessName, nodeName, e,processId,processInstanceId,nodeId);
						throw new RuntimeException("结束节点在删除当前流程的每步业务参的时候发生异常，请检查核实！");
					}
				}else if(executionContext.getNode().toString().indexOf("StartState")==0){
					log.info(nodeName+"是"+nodeType+",流程结束读取规则文件"+"(RuleActionHandler的execute()方法)");
				}else{
					try{
						executionContext.leaveNode();
						log.info(nodeName+"是"+nodeType+",流程结束读取规则文件"+"(RuleActionHandler的execute()方法),此时没有转向值，按默认的值转向");
					}catch(Exception e){
						this.handlerOtherExceptionMethod(nodeType, ci, token, vProcessName, nodeName, e,processId,processInstanceId,nodeId);
						throw new RuntimeException("离开"+nodeName+"节点时发生异常，请检查核实！");
					}
				}
			}
		}else{
			if(executionContext.getNode().toString().indexOf("EndState")==0){
				try{
					wfBack.removeWorkflowRegressionParametersByProcessId(processId, processInstanceId);
					log.info(nodeName+"是"+nodeType+",流程结束读取规则文件"+"(RuleActionHandler的execute()方法)");
				}catch(Exception e){
					this.handlerOtherExceptionMethod(nodeType, ci, token, vProcessName, nodeName, e,processId,processInstanceId,nodeId);
					throw new RuntimeException("结束节点在删除当前流程的每步业务参的时候发生异常，请检查核实！");
				}
			}else if(executionContext.getNode().toString().indexOf("StartState")==0){
				log.info(nodeName+"是"+nodeType+",流程结束读取规则文件"+"(RuleActionHandler的execute()方法)");
			}else{
				try{
					executionContext.leaveNode();
					log.info(nodeName+"是"+nodeType+",流程结束读取规则文件"+"(RuleActionHandler的execute()方法),此时没有转向值，按默认的值转向");
				}catch(Exception e){
					this.handlerOtherExceptionMethod(nodeType, ci, token, vProcessName, nodeName, e,processId,processInstanceId,nodeId);
					throw new RuntimeException("离开"+nodeName+"节点时发生异常，请检查核实！");
				}
			}
		}		
	}
	/**
	 * 系统异常之后发送邮件给系统管理员和维护管理员
	 * @param vProcessName
	 * @param nodeName
	 */
	public void sendSimpleEmail(String vProcessName , String nodeName){
		
		String contentDefault = vProcessName+"(流程)提交之后"+"在"+nodeName+"节点发生异常";
		String subject = PropertiesUtil.getProperties("system.mail.excepition.subject", "ITIL审批流程发生异常，请检查系统");
		String content = PropertiesUtil.getProperties("system.mail.exception.content", contentDefault);
		String to = PropertiesUtil.getProperties("system.mail.sendmail.from");
		String cc = PropertiesUtil.getProperties("system.mail.develop.debug.mailrecirve");
		try{
			ms.sendSimplyMail(to,cc , null, subject, content);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	/**
	 * node节点在发生异常时候回退流程
	 * 因为是node节点，此时还没有进行字符串goBack的拼接，rule并不是enter事件的action;所以goback的最后一个就是上个节点的信息
	 * @param ci
	 * @param token
	 */
	public void nodeTypeSaveException(ContextInstance ci,Token token){
		
		String fromNodeName = "";
		String fromParamId = "";
		//把记录每一个节点的参数删除掉
		List allNodeMessage = (List)ci.getVariable("goBack");//List中每一个对象就是一个String，格式为paramId+nodeName；
		//allNodeMessage.remove(allNodeMessage.size()-1);
		String  fromNodeMessage = (String)allNodeMessage.get(allNodeMessage.size()-1);
		String[] mutipleMessage = fromNodeMessage.split("\\+");
		fromParamId = mutipleMessage[0];//上个节点参数Id
		fromNodeName = mutipleMessage[1];//节点名称为中文，这里无法用到nodeDesc（api限制）
		allNodeMessage.remove(allNodeMessage.size()-1);
		//开始回退了
		try{
			Node fromNode = ci.getProcessInstance().getProcessDefinition().getNode(fromNodeName);
			token.setNode(fromNode);
			ExecutionContext ec = new ExecutionContext(token);
			fromNode.enter(ec);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	/**
	 * node节点在发生异常时候回退流程
	 * 此时发生异常已经进行了goBack的拼接
	 * @param ci
	 * @param token
	 */
	public void nodeTypeRuleException(ContextInstance ci,Token token,List allNodeMessage){
		String fromNodeName = "";
		String fromParamId = "";
		//把记录每一个节点的参数删除掉
		//List allNodeMessage = (List)ci.getVariable("goBack");//List中每一个对象就是一个String，格式为paramId+nodeName；
		allNodeMessage.remove(allNodeMessage.size()-1);
		String  fromNodeMessage = (String)allNodeMessage.get(allNodeMessage.size()-1);
		String[] mutipleMessage = fromNodeMessage.split("\\+");
		fromParamId = mutipleMessage[0];//上个节点参数Id
		fromNodeName = mutipleMessage[1];//节点名称为中文，这里无法用到nodeDesc（api限制）
		allNodeMessage.remove(allNodeMessage.size()-1);
		//开始回退了
		try{
			Node fromNode = ci.getProcessInstance().getProcessDefinition().getNode(fromNodeName);
			token.setNode(fromNode);
			ExecutionContext ec = new ExecutionContext(token);
			fromNode.enter(ec);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 这是适合任务接点发生异常后抛给node节点，然后由node节点进行流程回退的情况
	 * @param ci
	 * @param token
	 * @param allNodeMessage
	 */
	public void fromTaskToNodeTypeRuleException(ContextInstance ci,Token token,List allNodeMessage){
		String fromNodeName = "";
		String fromParamId = "";
		//把记录每一个节点的参数删除掉
		String  fromNodeMessage = (String)allNodeMessage.get(allNodeMessage.size()-1);
		String[] mutipleMessage = fromNodeMessage.split("\\+");
		fromParamId = mutipleMessage[0];//上个节点参数Id
		fromNodeName = mutipleMessage[1];//节点名称为中文，这里无法用到nodeDesc（api限制）
		allNodeMessage.remove(allNodeMessage.size()-1);
		//开始回退了
		try{
			Node fromNode = ci.getProcessInstance().getProcessDefinition().getNode(fromNodeName);
			token.setNode(fromNode);
			ExecutionContext ec = new ExecutionContext(token);
			fromNode.enter(ec);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 处理异常方法(此节点没有把本节点信息增加到goBack中)
	 * 这种情况是本节点还没有进行回退参数添加，更没有执行leave方法；所以按正常情况处理即可
	 * @param nodeType
	 * @param ci
	 * @param token
	 * @param vProcessName
	 * @param nodeName
	 * @param e
	 */
	public void handlerSaveExceptionMethod(String nodeType,ContextInstance ci,Token token,String vProcessName , String nodeName,Exception e,Long virtualProcessId,Long processInstanceId,Long nodeId){
		log.error(vProcessName+"(流程)提交之后"+"在"+nodeName+"(节点)发生异常");
		log.debug(e.getMessage());
		if(nodeType.indexOf("StartState")==0){//之需要抛出，然后前台页面会显示
			//throw new RuntimeException("read ruleFile exception!~!");
		}else if(nodeType.indexOf("Node")==0){//需要流程回退的同时，然后发邮件通知管理员
			//如果node节点异常了，回退回去，但是再次回退时候则本node节点已经有参数，所以要异常以后删除已有参数
			log.error(nodeName+"是"+nodeType+"(类型),流程结束读取规则文件"+"(RuleActionHandler的execute()方法),由于发生异常,流程将开始回到上一个节点("+nodeName+")");
			log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!回退开始(handlerSaveExceptionMethod)!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			this.nodeTypeSaveException(ci, token);
			//然后发送邮件,通知管理员
			this.sendSimpleEmail(vProcessName, nodeName);
			log.error(nodeName+"是"+nodeType+"(类型),流程结束读取规则文件"+"(RuleActionHandler的execute()方法),由于发生异常，流程回到上一个节点("+nodeName+")完毕");
			log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!回退结束(handlerSaveExceptionMethod)!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}else if(nodeType.indexOf("EndState")==0){//如果是在结束节点，则无需做任何操作，字需要记录日志即可
			e.printStackTrace();
		}
	}
	/**
	 * 处理异常方法(此节点已经把本节点信息增加到goBack中)
	 * 这是在node.leave()方法中抛出异常
	 * @param nodeType
	 * @param ci
	 * @param token
	 * @param vProcessName
	 * @param nodeName
	 * @param e
	 */
	public void handlerOtherExceptionMethod(String nodeType,ContextInstance ci,Token token,String vProcessName , String nodeName,Exception e,Long virtualProcessId,Long processInstanceId,Long nodeId){
		log.error(vProcessName+"(流程)提交之后"+"在"+nodeName+"(节点)发生异常");
		log.error(e.getMessage());
		if(nodeType.indexOf("StartState")==0){//之需要抛出，然后前台页面会显示
			//throw new RuntimeException("read ruleFile exception!~!");
		}else if(nodeType.indexOf("Node")==0){//需要流程回退的同时，然后发邮件通知管理员
			log.error(nodeName+"是"+nodeType+"(类型),流程结束读取规则文件"+"(RuleActionHandler的execute()方法),由于发生异常,流程将开始回到上一个节点("+nodeName+")");
			log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!回退开始(handlerOtherExceptionMethod)!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			List allNodeMessage = (List)ci.getVariable("goBack");//List中每一个对象就是一个String，格式为paramId+nodeName；
			wfBack.removeNodeWorkflowRegressionParameters(virtualProcessId, processInstanceId, nodeId);
			boolean flag = this.isHaveNowNodeMessage(nodeName, allNodeMessage);
			if(flag){//如果标示为真，说明回退参数中包含了当前节点，说明不是task回退情况，此时需要按原来的异常机制处理
				this.nodeTypeRuleException(ci, token,allNodeMessage);
			}else{//如果为假，则说明是task回退的情况。此时只需要删除上一个节点
				this.fromTaskToNodeTypeRuleException(ci, token, allNodeMessage);
			}
			//然后发送邮件,通知管理员
			this.sendSimpleEmail(vProcessName, nodeName);
			log.error(nodeName+"是"+nodeType+"(类型),流程结束读取规则文件"+"(RuleActionHandler的execute()方法),由于发生异常，流程回到上一个节点("+nodeName+")完毕");
			log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!回退结束(handlerOtherExceptionMethod)!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}else if(nodeType.indexOf("EndState")==0){//如果是在结束节点，则无需做任何操作，字需要记录日志即可
			e.printStackTrace();
		}
	}
	/**
	 * 用来判断回退参数中是否包含当前节点
	 * @param nodeName
	 * @param allNodeMessage
	 * @return
	 */
	public boolean isHaveNowNodeMessage(String nodeName , List allNodeMessage){
		
		for(int i = allNodeMessage.size()-1 ; i>=0 ; i--){
			String nodeMessage = (String)allNodeMessage.get(i);
			String[] mutipleMessage = nodeMessage.split("\\+");
			String mutilpleNodeName = mutipleMessage[1];//节点名称为中文，这里无法用到nodeDesc（api限制）
			if(nodeName.equals(mutilpleNodeName)){
				return true;
			}
		}
		return false;
	}
}
