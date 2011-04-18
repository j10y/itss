package com.digitalchina.info.framework.workflow;

import java.util.List;
import java.util.Map;

import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.def.Task;

import com.digitalchina.info.appframework.pagemodel.entity.PageModel;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.service.Service;
import com.digitalchina.info.framework.workflow.entity.DefinitionInfo;
import com.digitalchina.info.framework.workflow.info.HistoryInfo;
import com.digitalchina.info.framework.workflow.info.ProcessInfo;
import com.digitalchina.info.framework.workflow.info.TaskInfo;
/**
 * ���ã���������ڵ��ҵ����õ�PageModel
 * @Class Name TaskPageModelService
 * @Author yang Tao
 * @Create In 2009-2-26
 */
public interface TaskPageModelService {
	
	/**
	 * ����taskId�õ�ĳ���ڵ��pageModel
	 * @Methods Name getPageModel
	 * @Create In Feb 25, 2009 By Administrator
	 * @param taskId
	 * @return long
	 */
	public PageModel getPageModel(Task  task,Map mapVar);
	/**
	 * ͨ����������id�ͽڵ�id�õ���Ӧ�ڵ��pageModel
	 * @param vProcessId
	 * @param nodeId
	 * @return
	 */
	public PageModel findPageModelByVritualProcessIdAndNodeId(Long vProcessId , Long nodeId);

	
	
}
