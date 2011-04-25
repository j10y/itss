package com.digitalchina.info.framework.workflow.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jbpm.JbpmContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.service.Service;
import com.digitalchina.info.framework.workflow.ContextService;
import com.digitalchina.info.framework.workflow.WorkflowConstants;
import com.digitalchina.info.framework.workflow.base.JbpmContextFactory;
import com.digitalchina.info.framework.workflow.entity.WorkflowRecordTaskInfo;

public class ContextServiceImpl extends BaseDao implements ContextService {

	private static Log log;
	// private Service service = (Service) ContextHolder.getBean("baseService");
	static {
		log = LogFactory
				.getLog(com.digitalchina.info.framework.workflow.impl.ContextServiceImpl.class);
	}

	public Object getVariableByProcessId(long instanceId, String name) {
		log.debug("getVariableByProcessId" + " instanceid:" + instanceId
				+ " var name:" + name);

		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		Object value = null;
		try {
			log.debug("getVariableByProcessId");
			ProcessInstance processInstance = jbpmContext
					.loadProcessInstance(instanceId);
			value = processInstance.getContextInstance().getVariable(name);
		} catch (Exception e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		} finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return value;
	}

	public Map listVariablesByProcessId(long processInstanceId) {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		Map mapVariable = null;
		try {
			log.debug("listVariablesByProcessId " + processInstanceId);
			ProcessInstance processInstance = jbpmContext
					.loadProcessInstance(processInstanceId);
			mapVariable = processInstance.getContextInstance().getVariables();
		} catch (Exception e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		} finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return mapVariable;
	}

	public void setVariableByProcessId(long instanceId, String name,
			Object value) {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		try {
			log.debug("setVariableByProcessId");
			ProcessInstance processInstance = jbpmContext
					.loadProcessInstance(instanceId);
			processInstance.getContextInstance().setVariable(name, value);
		} catch (Exception e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		} finally {
			JbpmContextFactory.closeJbpmContext();
		}
	}

	public Object getVariableByTaskId(long taskId, String name) {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		Object value = null;
		try {
			log.debug("getVariableByTaskId");
			TaskInstance taskInstance = jbpmContext.loadTaskInstance(taskId);
			value = taskInstance.getContextInstance().getVariable(name);
		} catch (Exception e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		} finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return value;
	}
	/**
	 * 通过任务获得上下文变量列表
	 * @Methods Name listVariablesByTaskId
	 * @Create In 2008-4-3 By yang
	 * @param taskId 任务标识
	 * @ReturnType Map
	 */
	public Map listVariablesByTaskId(long taskId) {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		Map mapVariable = null;
		try {
			log.debug("listVariablesByTaskId");
			TaskInstance taskInstance = jbpmContext.loadTaskInstance(taskId);
			mapVariable = taskInstance.getContextInstance().getVariables();
		} catch (Exception e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		} finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return mapVariable;
	}

	public Map listBizVariablesByTaskId(long taskId) {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		Map mapVariable = null;
		try {
			log.debug("listVariablesByTaskId");
			TaskInstance taskInstance = jbpmContext.loadTaskInstance(taskId);
			mapVariable = (Map) taskInstance.getContextInstance().getVariable(
					WorkflowConstants.BUSINESS_PARAM_KEY);
		} catch (Exception e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		} finally {
			JbpmContextFactory.closeJbpmContext();
		}
		return mapVariable;
	}
	/**
	 * 通过任务设置上下文变量值
	 * @Methods Name setVariableByTaskId
	 * @Create In 2008-4-3 By yang
	 * @param taskId 任务标识
	 * @param name 变量名称
	 * @param value 变量的值
	 * @ReturnType void
	 */
	public void setVariableByTaskId(long taskId, String name, Object value) {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		try {
			log.debug("setVariableByTaskId");
			TaskInstance taskInstance = jbpmContext.loadTaskInstance(taskId);
			taskInstance.getContextInstance().setVariable(name, value);
		} catch (Exception e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		} finally {
			JbpmContextFactory.closeJbpmContext();
		}
	}
	/**
	 * 把组装成的userlist追加到原有的当中,还是放到bizparam当中，让contextInstance把其包装
	 * @param taskId
	 * @param name
	 * @param value
	 */
	public void setVariableToBizParam(long taskId, String name, String value) {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		Service service = (Service) ContextHolder.getBean("baseService");
		try {
			log.debug("setVariableByTaskId");
			TaskInstance taskInstance = jbpmContext.loadTaskInstance(taskId);
			Map mapParams = (Map) taskInstance.getContextInstance()
					.getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
			if("userList".equals(name)){//动态指派时，userList值是增加的(对一个流程)
				String userList=(String)mapParams.get("userList");
				if(userList!=null&&!"".equals(userList)){
					userList+="$";
					userList+=value;
				}else{
					userList=value;
				}
				mapParams.put("userList", userList);
			}else{
				mapParams.put(name,value);
			}
			taskInstance.getContextInstance().setVariable(
					WorkflowConstants.BUSINESS_PARAM_KEY, mapParams);
		} catch (Exception e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		} finally {
			JbpmContextFactory.closeJbpmContext();
		}
	}

	public void removeVariableByProcessId(long instanceId, String name) {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		try {
			log.debug("removeVariableByProcessId");
			ProcessInstance processInstance = jbpmContext
					.loadProcessInstance(instanceId);
			processInstance.getContextInstance().deleteVariable(name);
		} catch (Exception e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		} finally {
			JbpmContextFactory.closeJbpmContext();
		}

	}

	public void removeVariableByTaskId(long taskId, String name) {
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		try {
			log.debug("removeVariableByTaskId");
			TaskInstance taskInstance = jbpmContext.loadTaskInstance(taskId);
			taskInstance.getContextInstance().deleteVariable(name);
		} catch (Exception e) {
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
		} finally {
			JbpmContextFactory.closeJbpmContext();
		}
	}

	public WorkflowRecordTaskInfo findWorkflowRecordTaskInfoById(Long processId, Long nodeId) {
		
		Criteria criteria = super.getCriteria(WorkflowRecordTaskInfo.class);
		criteria.add(Restrictions.eq("processInstanceId", processId));
		criteria.add(Restrictions.eq("nodeId", nodeId));
		WorkflowRecordTaskInfo recordTaskInfo = (WorkflowRecordTaskInfo)criteria.uniqueResult();
		return recordTaskInfo;
				
	}

}
