package com.zsgj.info.framework.workflow.info;

import java.util.Date;
import java.util.List;

import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.workflow.entity.VirtualDefinitionInfo;
import com.zsgj.info.framework.workflow.entity.WorkflowRecordTaskInfo;
/**
 * 包装流程实例
 * @Class Name ProcessInfo
 * @Author yang
 * @Create In 2008-6-10
 */
public class ProcessInfo  extends BaseInfo{ 
	
	boolean isActive;//是否活动
	String definitionName;//定义名称
	int version;//版本号
	int defVersion;//流程定义版本号
	boolean isSuspended;//是否挂起
	long taskid;//活动流程的任务id
	String processCreator;//流程创建人
	Date start;//提交时间
	String virtualDefinitionDesc;//虚拟流程描述
	String nodeDesc;//节点描述
	String nodeName;//节点名称
	Long dataId;//主实体id
	Service service = (Service) ContextHolder.getBean("baseService");

	public long getTaskid() {
		return taskid;
	}

	public void setTaskid(long taskid) {
		this.taskid = taskid;
	}

	public ProcessInfo() {	
	}
	/**
	 * 2010-05-12 abate by 光顺安 for 具体失效缘由
	 * 删除该方法
	 * 用新的方法替代
	 * public ProcessInfo(ProcessInstance pi,List<TaskInstance> taskList)
	 */
	public ProcessInfo(ProcessInstance pi) {
		isActive = pi.getEnd()==null;
		definitionName = pi.getProcessDefinition().getName();
		version = pi.getVersion();
		defVersion = pi.getProcessDefinition().getVersion();
		start = pi.getStart();
		isSuspended = pi.isSuspended();
		setId(pi.getId());
		setName(pi.getKey());
	}
	//add by guangsa for 后台详细流程信息 in 20100513 begin
	public ProcessInfo(ProcessInstance pi,Long taskId) {
		isActive = pi.getEnd()==null;
		definitionName = pi.getProcessDefinition().getName();
		version = pi.getVersion();
		defVersion = pi.getProcessDefinition().getVersion();
		start = pi.getStart();
		isSuspended = pi.isSuspended();
		taskid = taskId;
		WorkflowRecordTaskInfo recordTaskInfo = (WorkflowRecordTaskInfo)(service.find(WorkflowRecordTaskInfo.class, "taskId", taskid).get(0));
		if(recordTaskInfo!=null&&!"".equals(recordTaskInfo)){
			processCreator = recordTaskInfo.getProcessCreator();
			dataId = recordTaskInfo.getDataId();
			nodeDesc = recordTaskInfo.getNodeDesc();
			nodeName = recordTaskInfo.getNodeName();
			Long virProcessId = recordTaskInfo.getVirtualProcessId();
			if(virProcessId!=null&&!"".equals(virProcessId)){
				VirtualDefinitionInfo virDefInfo = (VirtualDefinitionInfo)(service.find(VirtualDefinitionInfo.class, "id", virProcessId).get(0));
				if(virDefInfo!=null&&!"".equals(virDefInfo)){
					virtualDefinitionDesc = virDefInfo.getVirtualDefinitionDesc();
				}
			}
		}
		setId(pi.getId());
		setName(pi.getKey());
	}
	//add by guangsa for 后台详细流程信息 in 20100513 end
	public static ProcessInfo copy(ProcessInstance pi) {
		return new ProcessInfo(pi);
	}
	public String getDefinitionName() {
		return definitionName;
	}

	public void setDefinitionName(String definitionName) {
		this.definitionName = definitionName;
	}

	public boolean isSuspended() {
		return isSuspended;
	}

	public void setSuspended(boolean isSuspended) {
		this.isSuspended = isSuspended;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getDefVersion() {
		return defVersion;
	}

	public void setDefVersion(int defVersion) {
		this.defVersion = defVersion;
	}

	public String getProcessCreator() {
		return processCreator;
	}

	public void setProcessCreator(String processCreator) {
		this.processCreator = processCreator;
	}

	public Long getDataId() {
		return dataId;
	}

	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}

	public String getNodeDesc() {
		return nodeDesc;
	}

	public void setNodeDesc(String nodeDesc) {
		this.nodeDesc = nodeDesc;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public String getVirtualDefinitionDesc() {
		return virtualDefinitionDesc;
	}

	public void setVirtualDefinitionDesc(String virtualDefinitionDesc) {
		this.virtualDefinitionDesc = virtualDefinitionDesc;
	}
}
