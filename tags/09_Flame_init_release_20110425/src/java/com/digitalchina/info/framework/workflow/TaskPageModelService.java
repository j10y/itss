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
 * 作用：根据任务节点找到配置的PageModel
 * @Class Name TaskPageModelService
 * @Author yang Tao
 * @Create In 2009-2-26
 */
public interface TaskPageModelService {
	
	/**
	 * 根据taskId得到某个节点的pageModel
	 * @Methods Name getPageModel
	 * @Create In Feb 25, 2009 By Administrator
	 * @param taskId
	 * @return long
	 */
	public PageModel getPageModel(Task  task,Map mapVar);
	/**
	 * 通过虚拟流程id和节点id得到相应节点的pageModel
	 * @param vProcessId
	 * @param nodeId
	 * @return
	 */
	public PageModel findPageModelByVritualProcessIdAndNodeId(Long vProcessId , Long nodeId);

	
	
}
