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
	 * ����action��ִ�нӿ�
	 * ͬһ�����̵�node�ڵ�����action��ȥ��ͬһ�������ļ�
	 * ����ת���Ǹ��ڵ�
	 * @throws Exception 
	 * 
	 * 
	 */
	public void execute(ExecutionContext executionContext) throws Exception{
		//ǰ������׼��
		ContextInstance ci = executionContext.getContextInstance();
		String nodeName = executionContext.getToken().getNode().getName();//��ǰ�ڵ�����
		String nodeDesc = executionContext.getToken().getNode().getDescription();//��ǰ�ڵ�����
		String nodeType = executionContext.getToken().getNode().toString();
		Token token = executionContext.getToken();
		Long processId=(Long)executionContext.getProcessInstance().getContextInstance().getVariable("VIRTUALDEFINITIONINFO_ID");
		String vProcessName = (String)executionContext.getProcessInstance().getContextInstance().getVariable("VIRTUALDEFINITIONINFO_NAME");
		String vProcessDesc = (String)executionContext.getProcessInstance().getContextInstance().getVariable("VIRTUALDEFINITIONINFO_DESC");
		Map mapParams=(Map)executionContext.getProcessInstance().getContextInstance().getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
		Long processInstanceId = executionContext.getProcessInstance().getId();
		Long nodeId=executionContext.getNode().getId();
		String paramId = "";
		
		log.info(vProcessDesc+"(����)"+nodeName+"(�ڵ�)��"+nodeType+",���̿�ʼ��ȡ�����ļ�"+"(RuleActionHandler��execute()����)");
		/*************************�����ж��Ƿ��һ�ν��뵱ǰ�ڵ�******************************************/
		WorkflowRegressionParameters regParam = wfBack.findWorkflowRegressionParametersByMuitlyId(processId, processInstanceId, nodeId);
		if(regParam==null||"".equals(regParam)){
			//����ǵ�һ�ν��뵱ǰ�ڵ㣬ֻ��Ҫ�����м�¼��ǰ�ڵ�Ĳ���
			try{
				regParam = wfBack.saveWorkflowRegressionParams(processId, processInstanceId, nodeId,nodeName, nodeDesc,mapParams);
			}catch(Exception e){//����ط�����ɾ���ڵ��������Ϊ���Ǳ���ʱ�����쳣������˵��û�б���ɹ�
				log.error(e.getMessage());
				this.handlerSaveExceptionMethod(nodeType, ci, token, vProcessName, nodeName, e,processId,processInstanceId,nodeId);
				throw new RuntimeException(e.getMessage());
			}			
		}else{
			//������ǵ�һ�ν��뵱ǰ�ڵ㣬��Ҫ�ӿ���ȡ������������ԭ����ҵ�����
			String bizParam = regParam.getRegressionParams();
			Map nowNodeBizParam = new HashMap();
			//������ʽ��{key+value;key+value;key+value;+key+value}
			String[] mutils = bizParam.split("\\;");
			for(int i=0;i<mutils.length;i++){
				String[] single = mutils[i].split("\\+");
				nowNodeBizParam.put(single[0], single[1]);
			}
			if(!nowNodeBizParam.isEmpty()){
//				executionContext.getProcessInstance().getContextInstance().deleteVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
//				executionContext.getProcessInstance().getContextInstance().setVariable(WorkflowConstants.BUSINESS_PARAM_KEY, nowNodeBizParam);
			}else{
				//����ǿ�
				log.info(nodeName+"�ڵ�ҵ�����Ϊ�գ������Ƿ�����");
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
		/**********************************����ڵ���ϢΪ����������׼��*****************************************************************/
		//������ʵ����������ȡ��rulePath����ҵ�����
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
			if(rulePath!=null){//�������ļ��еĹ��򣬹���Ӧ��������
				try{
					transitionName=ProcessRuleHelper.executeRule(rulePath, mapParams);
				}catch(RuleFileException e){
					this.handlerOtherExceptionMethod(nodeType, ci, token, vProcessName, nodeName, e,processId,processInstanceId,nodeId);
					throw new RuleFileException(e.getMessage().substring(e.getMessage().indexOf(":")+1,e.getMessage().length()));
				}catch(Exception e){
					this.handlerOtherExceptionMethod(nodeType, ci, token, vProcessName, nodeName, e,processId,processInstanceId,nodeId);
					throw new RuntimeException(vProcessDesc+"(����)"+nodeName+"(�ڵ�),��ȡ�����ļ��Ƿ����쳣");
				}
			}
		
			if(transitionName!=null&&!"".equals(transitionName)){
				try{
					if(transitionName.equalsIgnoreCase("NOLEAVE")){
						//�ڿ�ʼ�ڵ�leave�¼�ʱʲôҲ��ִ��,���������executeContext.leaveNode,����������ѭ��
						log.info(nodeName+"��"+nodeType+",���̽�����ȡ�����ļ�"+"(RuleActionHandler��execute()����)����ת��ΪNOLEAVE��ת");
						//throw new RuntimeException("�����ڵ���ɾ����ǰ���̵�ÿ��ҵ��ε�ʱ�����쳣�������ʵ��");
					}else if(executionContext.getNode().toString().indexOf("EndState")==0){
						//����ǽ����ڵ�Ͱѵ�ǰ����ʵ���ı��еĲ���ȥ��
						try{
							wfBack.removeWorkflowRegressionParametersByProcessId(processId, processInstanceId);
							log.info(nodeName+"��"+nodeType+",���̽�����ȡ�����ļ�"+"(RuleActionHandler��execute()����)");
						}catch(Exception e){
							this.handlerOtherExceptionMethod(nodeType, ci, token, vProcessName, nodeName, e,processId,processInstanceId,nodeId);
							throw new RuntimeException(e.getMessage());
						}
						
					}else{
						try{
							executionContext.leaveNode(transitionName);
							log.info(nodeName+"��"+nodeType+",���̽�����ȡ�����ļ�"+"(RuleActionHandler��execute()����)����ת��Ϊ"+transitionName+"��ת");
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
					//����ǽ����ڵ�Ͱѵ�ǰ����ʵ���ı��еĲ���ȥ��
					try{
						wfBack.removeWorkflowRegressionParametersByProcessId(processId, processInstanceId);
						log.info(nodeName+"��"+nodeType+",���̽�����ȡ�����ļ�"+"(RuleActionHandler��execute()����)");
					}catch(Exception e){
						this.handlerOtherExceptionMethod(nodeType, ci, token, vProcessName, nodeName, e,processId,processInstanceId,nodeId);
						throw new RuntimeException("�����ڵ���ɾ����ǰ���̵�ÿ��ҵ��ε�ʱ�����쳣�������ʵ��");
					}
				}else if(executionContext.getNode().toString().indexOf("StartState")==0){
					log.info(nodeName+"��"+nodeType+",���̽�����ȡ�����ļ�"+"(RuleActionHandler��execute()����)");
				}else{
					try{
						executionContext.leaveNode();
						log.info(nodeName+"��"+nodeType+",���̽�����ȡ�����ļ�"+"(RuleActionHandler��execute()����),��ʱû��ת��ֵ����Ĭ�ϵ�ֵת��");
					}catch(Exception e){
						this.handlerOtherExceptionMethod(nodeType, ci, token, vProcessName, nodeName, e,processId,processInstanceId,nodeId);
						throw new RuntimeException("�뿪"+nodeName+"�ڵ�ʱ�����쳣�������ʵ��");
					}
				}
			}
		}else{
			if(executionContext.getNode().toString().indexOf("EndState")==0){
				try{
					wfBack.removeWorkflowRegressionParametersByProcessId(processId, processInstanceId);
					log.info(nodeName+"��"+nodeType+",���̽�����ȡ�����ļ�"+"(RuleActionHandler��execute()����)");
				}catch(Exception e){
					this.handlerOtherExceptionMethod(nodeType, ci, token, vProcessName, nodeName, e,processId,processInstanceId,nodeId);
					throw new RuntimeException("�����ڵ���ɾ����ǰ���̵�ÿ��ҵ��ε�ʱ�����쳣�������ʵ��");
				}
			}else if(executionContext.getNode().toString().indexOf("StartState")==0){
				log.info(nodeName+"��"+nodeType+",���̽�����ȡ�����ļ�"+"(RuleActionHandler��execute()����)");
			}else{
				try{
					executionContext.leaveNode();
					log.info(nodeName+"��"+nodeType+",���̽�����ȡ�����ļ�"+"(RuleActionHandler��execute()����),��ʱû��ת��ֵ����Ĭ�ϵ�ֵת��");
				}catch(Exception e){
					this.handlerOtherExceptionMethod(nodeType, ci, token, vProcessName, nodeName, e,processId,processInstanceId,nodeId);
					throw new RuntimeException("�뿪"+nodeName+"�ڵ�ʱ�����쳣�������ʵ��");
				}
			}
		}		
	}
	/**
	 * ϵͳ�쳣֮�����ʼ���ϵͳ����Ա��ά������Ա
	 * @param vProcessName
	 * @param nodeName
	 */
	public void sendSimpleEmail(String vProcessName , String nodeName){
		
		String contentDefault = vProcessName+"(����)�ύ֮��"+"��"+nodeName+"�ڵ㷢���쳣";
		String subject = PropertiesUtil.getProperties("system.mail.excepition.subject", "ITIL�������̷����쳣������ϵͳ");
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
	 * node�ڵ��ڷ����쳣ʱ���������
	 * ��Ϊ��node�ڵ㣬��ʱ��û�н����ַ���goBack��ƴ�ӣ�rule������enter�¼���action;����goback�����һ�������ϸ��ڵ����Ϣ
	 * @param ci
	 * @param token
	 */
	public void nodeTypeSaveException(ContextInstance ci,Token token){
		
		String fromNodeName = "";
		String fromParamId = "";
		//�Ѽ�¼ÿһ���ڵ�Ĳ���ɾ����
		List allNodeMessage = (List)ci.getVariable("goBack");//List��ÿһ���������һ��String����ʽΪparamId+nodeName��
		//allNodeMessage.remove(allNodeMessage.size()-1);
		String  fromNodeMessage = (String)allNodeMessage.get(allNodeMessage.size()-1);
		String[] mutipleMessage = fromNodeMessage.split("\\+");
		fromParamId = mutipleMessage[0];//�ϸ��ڵ����Id
		fromNodeName = mutipleMessage[1];//�ڵ�����Ϊ���ģ������޷��õ�nodeDesc��api���ƣ�
		allNodeMessage.remove(allNodeMessage.size()-1);
		//��ʼ������
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
	 * node�ڵ��ڷ����쳣ʱ���������
	 * ��ʱ�����쳣�Ѿ�������goBack��ƴ��
	 * @param ci
	 * @param token
	 */
	public void nodeTypeRuleException(ContextInstance ci,Token token,List allNodeMessage){
		String fromNodeName = "";
		String fromParamId = "";
		//�Ѽ�¼ÿһ���ڵ�Ĳ���ɾ����
		//List allNodeMessage = (List)ci.getVariable("goBack");//List��ÿһ���������һ��String����ʽΪparamId+nodeName��
		allNodeMessage.remove(allNodeMessage.size()-1);
		String  fromNodeMessage = (String)allNodeMessage.get(allNodeMessage.size()-1);
		String[] mutipleMessage = fromNodeMessage.split("\\+");
		fromParamId = mutipleMessage[0];//�ϸ��ڵ����Id
		fromNodeName = mutipleMessage[1];//�ڵ�����Ϊ���ģ������޷��õ�nodeDesc��api���ƣ�
		allNodeMessage.remove(allNodeMessage.size()-1);
		//��ʼ������
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
	 * �����ʺ�����ӵ㷢���쳣���׸�node�ڵ㣬Ȼ����node�ڵ�������̻��˵����
	 * @param ci
	 * @param token
	 * @param allNodeMessage
	 */
	public void fromTaskToNodeTypeRuleException(ContextInstance ci,Token token,List allNodeMessage){
		String fromNodeName = "";
		String fromParamId = "";
		//�Ѽ�¼ÿһ���ڵ�Ĳ���ɾ����
		String  fromNodeMessage = (String)allNodeMessage.get(allNodeMessage.size()-1);
		String[] mutipleMessage = fromNodeMessage.split("\\+");
		fromParamId = mutipleMessage[0];//�ϸ��ڵ����Id
		fromNodeName = mutipleMessage[1];//�ڵ�����Ϊ���ģ������޷��õ�nodeDesc��api���ƣ�
		allNodeMessage.remove(allNodeMessage.size()-1);
		//��ʼ������
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
	 * �����쳣����(�˽ڵ�û�аѱ��ڵ���Ϣ���ӵ�goBack��)
	 * ��������Ǳ��ڵ㻹û�н��л��˲�����ӣ���û��ִ��leave���������԰��������������
	 * @param nodeType
	 * @param ci
	 * @param token
	 * @param vProcessName
	 * @param nodeName
	 * @param e
	 */
	public void handlerSaveExceptionMethod(String nodeType,ContextInstance ci,Token token,String vProcessName , String nodeName,Exception e,Long virtualProcessId,Long processInstanceId,Long nodeId){
		log.error(vProcessName+"(����)�ύ֮��"+"��"+nodeName+"(�ڵ�)�����쳣");
		log.debug(e.getMessage());
		if(nodeType.indexOf("StartState")==0){//֮��Ҫ�׳���Ȼ��ǰ̨ҳ�����ʾ
			//throw new RuntimeException("read ruleFile exception!~!");
		}else if(nodeType.indexOf("Node")==0){//��Ҫ���̻��˵�ͬʱ��Ȼ���ʼ�֪ͨ����Ա
			//���node�ڵ��쳣�ˣ����˻�ȥ�������ٴλ���ʱ����node�ڵ��Ѿ��в���������Ҫ�쳣�Ժ�ɾ�����в���
			log.error(nodeName+"��"+nodeType+"(����),���̽�����ȡ�����ļ�"+"(RuleActionHandler��execute()����),���ڷ����쳣,���̽���ʼ�ص���һ���ڵ�("+nodeName+")");
			log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!���˿�ʼ(handlerSaveExceptionMethod)!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			this.nodeTypeSaveException(ci, token);
			//Ȼ�����ʼ�,֪ͨ����Ա
			this.sendSimpleEmail(vProcessName, nodeName);
			log.error(nodeName+"��"+nodeType+"(����),���̽�����ȡ�����ļ�"+"(RuleActionHandler��execute()����),���ڷ����쳣�����̻ص���һ���ڵ�("+nodeName+")���");
			log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!���˽���(handlerSaveExceptionMethod)!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}else if(nodeType.indexOf("EndState")==0){//������ڽ����ڵ㣬���������κβ���������Ҫ��¼��־����
			e.printStackTrace();
		}
	}
	/**
	 * �����쳣����(�˽ڵ��Ѿ��ѱ��ڵ���Ϣ���ӵ�goBack��)
	 * ������node.leave()�������׳��쳣
	 * @param nodeType
	 * @param ci
	 * @param token
	 * @param vProcessName
	 * @param nodeName
	 * @param e
	 */
	public void handlerOtherExceptionMethod(String nodeType,ContextInstance ci,Token token,String vProcessName , String nodeName,Exception e,Long virtualProcessId,Long processInstanceId,Long nodeId){
		log.error(vProcessName+"(����)�ύ֮��"+"��"+nodeName+"(�ڵ�)�����쳣");
		log.error(e.getMessage());
		if(nodeType.indexOf("StartState")==0){//֮��Ҫ�׳���Ȼ��ǰ̨ҳ�����ʾ
			//throw new RuntimeException("read ruleFile exception!~!");
		}else if(nodeType.indexOf("Node")==0){//��Ҫ���̻��˵�ͬʱ��Ȼ���ʼ�֪ͨ����Ա
			log.error(nodeName+"��"+nodeType+"(����),���̽�����ȡ�����ļ�"+"(RuleActionHandler��execute()����),���ڷ����쳣,���̽���ʼ�ص���һ���ڵ�("+nodeName+")");
			log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!���˿�ʼ(handlerOtherExceptionMethod)!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			List allNodeMessage = (List)ci.getVariable("goBack");//List��ÿһ���������һ��String����ʽΪparamId+nodeName��
			wfBack.removeNodeWorkflowRegressionParameters(virtualProcessId, processInstanceId, nodeId);
			boolean flag = this.isHaveNowNodeMessage(nodeName, allNodeMessage);
			if(flag){//�����ʾΪ�棬˵�����˲����а����˵�ǰ�ڵ㣬˵������task�����������ʱ��Ҫ��ԭ�����쳣���ƴ���
				this.nodeTypeRuleException(ci, token,allNodeMessage);
			}else{//���Ϊ�٣���˵����task���˵��������ʱֻ��Ҫɾ����һ���ڵ�
				this.fromTaskToNodeTypeRuleException(ci, token, allNodeMessage);
			}
			//Ȼ�����ʼ�,֪ͨ����Ա
			this.sendSimpleEmail(vProcessName, nodeName);
			log.error(nodeName+"��"+nodeType+"(����),���̽�����ȡ�����ļ�"+"(RuleActionHandler��execute()����),���ڷ����쳣�����̻ص���һ���ڵ�("+nodeName+")���");
			log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!���˽���(handlerOtherExceptionMethod)!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}else if(nodeType.indexOf("EndState")==0){//������ڽ����ڵ㣬���������κβ���������Ҫ��¼��־����
			e.printStackTrace();
		}
	}
	/**
	 * �����жϻ��˲������Ƿ������ǰ�ڵ�
	 * @param nodeName
	 * @param allNodeMessage
	 * @return
	 */
	public boolean isHaveNowNodeMessage(String nodeName , List allNodeMessage){
		
		for(int i = allNodeMessage.size()-1 ; i>=0 ; i--){
			String nodeMessage = (String)allNodeMessage.get(i);
			String[] mutipleMessage = nodeMessage.split("\\+");
			String mutilpleNodeName = mutipleMessage[1];//�ڵ�����Ϊ���ģ������޷��õ�nodeDesc��api���ƣ�
			if(nodeName.equals(mutilpleNodeName)){
				return true;
			}
		}
		return false;
	}
}
