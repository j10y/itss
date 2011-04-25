package com.digitalchina.info.framework.workflow.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.jbpm.JbpmContext;
import org.jbpm.db.GraphSession;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.node.TaskNode;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.workflow.DefinitionService;
import com.digitalchina.info.framework.workflow.WorkflowConstants;
import com.digitalchina.info.framework.workflow.base.JbpmConfig;
import com.digitalchina.info.framework.workflow.base.JbpmContextFactory;
import com.digitalchina.info.framework.workflow.entity.DefinitionInfo;
import com.digitalchina.info.framework.workflow.entity.VirtualDefinitionInfo;
import com.digitalchina.info.framework.workflow.info.NodeInfo;
import com.digitalchina.info.framework.workflow.info.ProcessInfo;
/**
 * 
 * @Class Name ProcessManagerImpl
 * @Author yang
 * @Create In 2008-3-20
 */
public class DefinitionServiceImpl extends BaseDao implements DefinitionService,WorkflowConstants {
	private static Log log;
	static 
	{
		log = LogFactory.getLog(com.digitalchina.info.framework.workflow.impl.DefinitionServiceImpl.class);
	}

	public static void main(String[] v) {
//		DefinitionServiceImpl p = new DefinitionServiceImpl();

	}
	/**
	 * 获得图中的所有任务节点的名称
	 */
	public List<NodeInfo> getTaskNodes(String processDefinitionName) {
		log.debug("getTaskNodes"+" processDefinitionName:"+processDefinitionName);
		
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		List<NodeInfo> nodeNames = null;
		try {
			ProcessDefinition pd = jbpmContext.getGraphSession().findLatestProcessDefinition(processDefinitionName);
			List nodes = pd.getNodes();
			nodeNames = new ArrayList<NodeInfo>();
			for(int i=0;i<nodes.size();i++) {
				Node n = (Node)nodes.get(i);
				if(n instanceof TaskNode) {
					nodeNames.add(new NodeInfo(n));					
				}
			}
		} catch (RuntimeException e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return nodeNames;
	}

	public List<DefinitionInfo> getAllDefinitions() {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		List<DefinitionInfo> listName = new ArrayList<DefinitionInfo>();
		try {
			log.debug("getAllDefinitions");
			List listDefination = jbpmContext.getGraphSession().findAllProcessDefinitions();
			
			for(int i=0;i<listDefination.size();i++) {
				ProcessDefinition pd = (ProcessDefinition)listDefination.get(i);
				DefinitionInfo definitionInfo=(DefinitionInfo)this.findUniqueBy(DefinitionInfo.class, "processDefinitionId", pd.getId());
				if(definitionInfo==null){
					definitionInfo=new DefinitionInfo(pd);
					definitionInfo.setProcessDefinitionId(pd.getId());
				}
				listName.add(definitionInfo);
			}
		} catch (RuntimeException e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return listName;
		
	}
	
	public List<DefinitionInfo> getLatestDefinitions() {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		List<DefinitionInfo> listName = new ArrayList<DefinitionInfo>();
		try {
			log.debug("getAllDefinitions");
			List listDefination = jbpmContext.getGraphSession().findLatestProcessDefinitions();
			
			for(int i=0;i<listDefination.size();i++) {
				ProcessDefinition pd = (ProcessDefinition)listDefination.get(i);
				DefinitionInfo definitionInfo=(DefinitionInfo)this.findUniqueBy(DefinitionInfo.class, "processDefinitionId", pd.getId());
				if(definitionInfo==null){
					definitionInfo=new DefinitionInfo(pd);
					definitionInfo.setProcessDefinitionId(pd.getId());
				}
				listName.add(definitionInfo);
			}
		} catch (RuntimeException e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return listName;
		
	}

	public void deployDefinition(String name) {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		try {
			log.debug("deployDefinition");
			ProcessDefinition processDefinition = ProcessDefinition.parseXmlResource(JbpmConfig.JPDL_PACKAGE+name);
			jbpmContext.deployProcessDefinition(processDefinition);
		} 
		catch(Exception e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}		
	}
	
	public void deleteDefinition(long processDefinitionId) {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		try {
			log.debug("deleteDefinition");
			jbpmContext.getGraphSession().deleteProcessDefinition(processDefinitionId);
		} 
		catch(Exception e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}		
	}
	/**
	 * 2010-05-12 abate by 光顺安 for 具体失效缘由
	 * 删除该方法
	 * 用新的方法替代
	 */
	public List<ProcessInfo> getAllActiveProcess() {
		List<ProcessInfo> processInfos = null;
		List<DefinitionInfo> definitions = getAllDefinitions();
		log.debug("getAllActiveProcess");
		for(int i=0;i<definitions.size();i++) {
			List<ProcessInfo> l = getActiveProcess(definitions.get(i).getProcessDefinitionId());
			if(i==0) {
				processInfos = l;
			}
			else {
				for(int j=0;j<l.size();j++) {
					processInfos.add(l.get(j));
				}
			}
		}
		return processInfos;
	}

	public List<ProcessInfo> getAllActiveProcessInstance() {
		List<ProcessInfo> processInfos = null;
		List<DefinitionInfo> definitions = getAllDefinitions();
		log.debug("getAllActiveProcessInstance");
		for(int i=0;i<definitions.size();i++) {
			List<ProcessInfo> l = getActiveProcessInstance(definitions.get(i).getProcessDefinitionId());
			if(i==0) {
				processInfos = l;
			}
			else {
				for(int j=0;j<l.size();j++) {
					processInfos.add(l.get(j));
				}
			}
		}
		return processInfos;
	}
	/**
	 * 2010-05-12 abate by 光顺安 for 具体失效缘由
	 * 删除该方法
	 * 用新的方法替代
	 * public List<ProcessInfo> getActiveProcessInstance(long definitionId)
	 */
	public List<ProcessInfo> getActiveProcess(long definitionId) {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		List<ProcessInfo> processInfos = new ArrayList<ProcessInfo>();  
		try {
			log.debug("getActiveProcess");
			GraphSession graphSession = jbpmContext.getGraphSession();
			List processInstances = graphSession.findProcessInstances(definitionId);
			for(int i=0;i<processInstances.size();i++) {
				ProcessInstance processInstance = (ProcessInstance)processInstances.get(i);
				if(processInstance.getEnd()==null) {
						processInfos.add(new ProcessInfo(processInstance));
					
				}
			}
		}
		catch(Exception e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return processInfos;
	}
	
	public List<ProcessInfo> getActiveProcessInstance(long definitionId) {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		List<ProcessInfo> processInfos = new ArrayList<ProcessInfo>();  
		try {
			log.debug("getActiveProcessInstance");
			GraphSession graphSession = jbpmContext.getGraphSession();
			List processInstances = graphSession.findProcessInstances(definitionId);
			for(int i=0;i<processInstances.size();i++) {
				ProcessInstance processInstance = (ProcessInstance)processInstances.get(i);
				if(processInstance.getEnd()==null) {
					//add by guangsa for 后台详细流程信息 in 20100513 begin
					Long taskId = this.getActiveTaskInstance(processInstance);
					//add by guangsa for 后台详细流程信息 in 20100513 end
					if(taskId!=null&&!"".equals(taskId)){
						processInfos.add(new ProcessInfo(processInstance,taskId));
					}
					
				}
			}
		}
		catch(Exception e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return processInfos;
	}
	//add by guangsa for 后台详细流程信息 in 20100513 begin
	public Long getActiveTaskInstance(ProcessInstance processInstance){
		Long taskId = null;
		Collection collection = (Collection)processInstance.getTaskMgmtInstance().getTaskInstances();
		if(collection!=null&&collection.size()!=0){
			Iterator iterator = collection.iterator();
			while(iterator.hasNext()){
				TaskInstance ti = (TaskInstance)iterator.next();
				if(ti.isOpen()){
					return ti.getId();
				}
			}
		}
		return taskId;
	}
	//add by guangsa for 后台详细流程信息 in 20100513 end
	public ProcessDefinition getDefinitionById(long id) {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		ProcessDefinition processDefinition = null;
		try {
			log.debug("ProcessDefinition");
			GraphSession graphSession = jbpmContext.getGraphSession();
			processDefinition = graphSession.getProcessDefinition(id);
		}
		catch(Exception e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return processDefinition;
	}
	public List<DefinitionInfo> searchDefinition(String nameLike, String descLike) {
		nameLike = nameLike==null?"":nameLike.toLowerCase();
		descLike = descLike==null?"":descLike.toLowerCase();
		List<DefinitionInfo> definitionInfos = getLatestDefinitions();
		if(definitionInfos==null) {
			return null;
		}
		List<DefinitionInfo> result = new ArrayList();
		for(DefinitionInfo definitionInfo: definitionInfos) {
			String desc = definitionInfo.getDescription().toLowerCase().trim();
			String name = definitionInfo.getName().toLowerCase().trim();			
			if(nameLike.equals("")||name.indexOf(nameLike)>=0) {
				if(nameLike.equals("")||name.indexOf(nameLike)>=0) {
					result.add(definitionInfo);
				}				
			}
		}
		return result;		
	}
	public List<NodeInfo> getAllNodes(String processDefinitionName) {
		log.debug("getTaskNodes"+" processDefinitionName:"+processDefinitionName);
		
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		List<NodeInfo> nodeInfos = null;
		try {
			ProcessDefinition pd = jbpmContext.getGraphSession().findLatestProcessDefinition(processDefinitionName);
			List nodes = pd.getNodes();
			nodeInfos = new ArrayList<NodeInfo>();
			for(int i=0;i<nodes.size();i++) {
				Node n = (Node)nodes.get(i);
				nodeInfos.add(new NodeInfo(n));					
			}
		} catch (RuntimeException e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return nodeInfos;
	}
	
	public String getNodeByNodeId(String nodeId,Long p) {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		String type="";
		try {
			log.debug("deployDefinition");
			ProcessDefinition processDefinition=jbpmContext.getGraphSession().loadProcessDefinition(p);
			List<Node> list=processDefinition.getNodes();
			for(Node node : list){
				if(Long.valueOf(nodeId).equals(node.getId())){
					type=node.toString();
				}
			}
		} 
		catch(Exception e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return type;
	}
	
	
	public List<ProcessDefinition> getAllLatestProcess() {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		List<ProcessDefinition> listDefination = new ArrayList<ProcessDefinition>();
		try {
			listDefination = jbpmContext.getGraphSession()
					.findLatestProcessDefinitions();
		} catch (RuntimeException e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		} finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return listDefination;
	}
	public Page getProcessDefinition(String processName, int pageNo,
			int pageSize) {
		Criteria criteria = super.createCriteria(VirtualDefinitionInfo.class);
		if(StringUtils.isNotBlank(processName)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.ilike("virtualDefinitionName", processName, MatchMode.ANYWHERE)) //忽略大小写
					.add(Restrictions.ilike("virtualDefinitionDesc", processName, MatchMode.ANYWHERE)));
		}
		Page page = super.pagedQuery(criteria, pageNo, pageSize);
		return page;
	}
	

}
