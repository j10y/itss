package com.zsgj.info.framework.workflow.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmContext;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.zsgj.info.framework.workflow.ParameterService;
import com.zsgj.info.framework.workflow.WorkflowConstants;
import com.zsgj.info.framework.workflow.base.JbpmContextFactory;

public class ParameterServiceImpl implements ParameterService{
	
	private static Log log;
	static 
	{
		log = LogFactory.getLog(ParameterServiceImpl.class);
	}
	
	public Object getVariableByProcessId(long instanceId, String name) {
		log.debug("getVariableByProcessId"+" instanceid:"+instanceId+" var name:"+name);
		
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		Object value = null;
		try {
			log.debug("getVariableByProcessId");
			ProcessInstance processInstance = jbpmContext.loadProcessInstance(instanceId);
			ContextInstance contextInstance = processInstance.getContextInstance();
			Map params = (Map)contextInstance.getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
			if(params!=null) {
				value = params.get(name);
			}
		} 
		catch(Exception e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}	
		return value;
	}
	
	public Map listVariablesByProcessId(long processInstanceId) {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		Map mapVariable = null;
		try {
			log.debug("listVariablesByProcessId "+processInstanceId);
			ProcessInstance processInstance = jbpmContext.loadProcessInstance(processInstanceId);
			ContextInstance contextInstance = processInstance.getContextInstance();
			mapVariable = (Map)contextInstance.getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
		} 
		catch(Exception e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}	
		return mapVariable;
	}

	public void setVariableByProcessId(long instanceId, String name, Object value) {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		try {
			log.debug("setVariableByProcessId");
			ProcessInstance processInstance = jbpmContext.loadProcessInstance(instanceId);
			ContextInstance contextInstance = processInstance.getContextInstance();
			Map params = (Map)contextInstance.getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
			if(params == null) {
				params = new HashMap();			
			}
			params.put(name,value);
			contextInstance.setVariable(WorkflowConstants.BUSINESS_PARAM_KEY, params);
		} 
		catch(Exception e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}	
	}

	public Object getVariableByTaskId(long taskId, String name) {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		Object value = null;
		try {
			log.debug("getVariableByTaskId");
			TaskInstance taskInstance = jbpmContext.loadTaskInstance(taskId);
			ContextInstance contextInstance = taskInstance.getContextInstance();
			Map params = (Map)contextInstance.getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
			if(params!=null) {
				value = params.get(name);
			}
		} 
		catch(Exception e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}	
		return value;
	}

	public Map listVariablesByTaskId(long taskId) {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		Map mapVariable = null;
		try {
			log.debug("listVariablesByTaskId");
			TaskInstance taskInstance = jbpmContext.loadTaskInstance(taskId);
			mapVariable = (Map)taskInstance.getContextInstance().getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
		} 
		catch(Exception e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}	
		return mapVariable;
	}

	public void setVariableByTaskId(long taskId, String name, Object value) {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		try {
			log.debug("setVariableByTaskId");
			TaskInstance taskInstance = jbpmContext.loadTaskInstance(taskId);
			ContextInstance contextInstance = taskInstance.getContextInstance();
			Map params = (Map)contextInstance.getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
			if(params == null) {
				params = new HashMap();			
			}
			params.put(name,value);
			contextInstance.setVariable(WorkflowConstants.BUSINESS_PARAM_KEY, params);
		} 
		catch(Exception e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}	
	}

	public void removeVariableByProcessId(long instanceId, String name) {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		try {
			log.debug("removeVariableByProcessId");
			ProcessInstance processInstance = jbpmContext.loadProcessInstance(instanceId);
			ContextInstance contextInstance = processInstance.getContextInstance();
			Map params = (Map)contextInstance.getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
			if(params!=null) {
				params.remove(name);
			}
			contextInstance.setVariable(WorkflowConstants.BUSINESS_PARAM_KEY, params);
		} 
		catch(Exception e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}	
		
	}

	public void removeVariableByTaskId(long taskId, String name) {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		try {
			log.debug("removeVariableByTaskId");
			TaskInstance taskInstance = jbpmContext.loadTaskInstance(taskId);
			ContextInstance contextInstance = taskInstance.getContextInstance();
			Map params = (Map)contextInstance.getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
			if(params!=null) {
				params.remove(name);
			}
			contextInstance.setVariable(WorkflowConstants.BUSINESS_PARAM_KEY, params);
		} 
		catch(Exception e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}	
	}

	public void addVariablesByProcessId(long instanceId, Map params) {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		try {
			log.debug("setVariableByProcessId");
			ProcessInstance processInstance = jbpmContext.loadProcessInstance(instanceId);
			ContextInstance contextInstance = processInstance.getContextInstance();
			Map oldParams = (Map)contextInstance.getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
			if(oldParams == null) {
				oldParams = new HashMap();			
			} 
			oldParams.putAll(params);
			contextInstance.setVariable(WorkflowConstants.BUSINESS_PARAM_KEY, oldParams);
		} 
		catch(Exception e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		}
		finally {
			JbpmContextFactory.closeJbpmContext();
		}	
		
	}
	
}
