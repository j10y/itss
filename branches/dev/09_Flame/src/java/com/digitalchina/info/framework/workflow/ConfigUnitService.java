package com.digitalchina.info.framework.workflow;

import java.util.List;
import java.util.Map;

import org.jbpm.taskmgmt.exe.TaskInstance;

import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.Role;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.workflow.entity.ConfigUnitMail;
import com.digitalchina.info.framework.workflow.entity.ConfigUnitMailNodeSender;
import com.digitalchina.info.framework.workflow.entity.ConfigUnitRole;
import com.digitalchina.info.framework.workflow.entity.ConfigUnitTimer;
import com.digitalchina.info.framework.workflow.entity.SubProcessConfigUnit;
import com.digitalchina.info.framework.workflow.entity.VirtualDefinitionInfo;
import com.digitalchina.info.framework.workflow.entity.VirtualNodeInfo;
import com.digitalchina.info.framework.workflow.entity.WorkflowRecordTaskInfo;

/**
 * 配置单元的一些业务方法
 * @Class Name ConfigUnitService
 * @Author guangsa
 * @Create In Mar 4, 2009
 */
public interface ConfigUnitService {
	/**
	 * 通过两个ID来查找相应的timer单元
	 * @Methods Name showConfigUnitTimer
	 * @Create In Mar 30, 2009 By guangsa
	 * @param processName
	 * @param nodeName
	 * @return ConfigUnitTimer
	 */
	public ConfigUnitTimer showConfigUnitTimer(Long virtualId ,Long nodeId);
	/**
	 * 
	 * @Methods Name showRole
	 * @Create In Mar 30, 2009 By guangsa
	 * @param virProcessId
	 * @param nodeId
	 * @return List<Role>
	 */
	public Map showRole(String virProcessId ,String nodeId);
	/**
	 * 根据虚拟流程ID和节点ID找到唯一的一个角色配置单元
	 * @Methods Name findConfigUnitRole
	 * @Create In Mar 30, 2009 By guangsa
	 * @param virProcessId
	 * @param nodeId
	 * @return ConfigUnitRole
	 */
	public ConfigUnitRole findConfigUnitRole(String virProcessId ,String nodeId);
	/**
	 * 根据虚拟流程ID和节点ID找到唯一的一个角色配置单元
	 * @Methods Name findConfigUnitRoleTableByConfigUnitRole
	 * @Create In Mar 30, 2009 By guangsa
	 * @param configUnitRole
	 * @param u
	 * @return boolean
	 */
	public boolean findConfigUnitRoleTableByConfigUnitRole(ConfigUnitRole configUnitRole,UserInfo u);
	/**
	 * 根据虚拟流程ID和节点ID找到唯一的一个邮件配置单元
	 * @param virProcessId
	 * @param nodeId
	 * @return
	 */
	public ConfigUnitMailNodeSender findConfigUnitMailNodeSenderById(String virProcessId ,String nodeId);
	/**
	 * 发现mailCc的用户信息
	 * @Methods Name findUserInfoByParams
	 * @Create In Mar 31, 2009 By guangsa
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	public Page findUserInfoByParams(Map params, int pageNo, int pageSize);
	
	/**
	 * 根据相关参数实现部门的查找
	 * @Methods Name findDepartmentByParams
	 * @Create In Mar 31, 2009 By guangsa
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page findDepartmentByParams(Map params, int pageNo, int pageSize);
	
	/**
	 * 发现MailNodeSender的用户信息
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page findMailNodeSenderUserInfoByParams(Map params, int pageNo, int pageSize);
	/**
	 * 
	 * @Methods Name findMailObjectById
	 * @Create In Mar 31, 2009 By guangsa
	 * @param virtualId
	 * @param nodeId
	 * @return ConfigUnitMail
	 */
	public ConfigUnitMail findMailObjectById(String virtualId,String nodeId);
	/**
	 * 根据虚拟流程ID和节点ID来确定邮件指派这个配置单元
	 * @param virtualId
	 * @param nodeId
	 */
	public ConfigUnitMailNodeSender findMailNodeById(String virtualId,String nodeId);
	/**
	 * 根据虚拟定义和节点来唯一确定一个虚拟节点
	 * @Methods Name findVirtualNodeInfo
	 * @Create In Apr 10, 2009 By guangsa
	 * @param definitionInfo
	 * @param nodeId
	 * @return VirtualNodeInfo
	 */
	public VirtualNodeInfo findVirtualNodeInfo(VirtualDefinitionInfo definitionInfo,String nodeId);
	/**
	 * 根据虚拟流程ID和节点ID来确定一个虚拟节点
	 * @Methods Name findVirtualNodeInfo
	 * @Create In Apr 10, 2009 By guangsa
	 * @param vProcessId
	 * @param nodeId
	 * @return
	 */
	public VirtualNodeInfo findVirtualNodeInfoByDoubleId(Long vProcessId , Long nodeId);
	
	/**
	 * 根据相应参数来查找虚拟流程信息并分页
	 * @Methods Name findVirtualDefinitionInfos
	 * @Create In Apr 13, 2009 By Administrator
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	public Page findVirtualDefinitionInfos(Map params, int pageNo, int pageSize);
	
	/**
	 * 得到子流程配置单元
	 * @Methods Name findSubProcessConfigUnit
	 * @Create In Apr 22, 2009 By guangsa
	 * @param virtualId
	 * @param nodeId
	 * @return SubProcessConfigUnit
	 */
	public SubProcessConfigUnit findSubProcessConfigUnit(Long virtualId ,Long nodeId);
	/**
	 * 实现后台pagemodel的分页功能
	 * @Methods Name findPageModelByParams
	 * @Create In May 4, 2009 By guangsa
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	public Page findPageModelByParams(Map params, int pageNo, int pageSize);
	/**
	 * 实现后台系统角色的分页功能
	 * @Methods Name findSystemRoleByParams
	 * @Create In May 4, 2009 By guangsa
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	public Page findSystemRoleByParams(Map params, int pageNo, int pageSize);
	/**
	 * 在流程过程当中记录当前节点的任务ID
	 * @Methods Name findSystemRoleByParams
	 * @Create In May 4, 2009 By guangsa
	 * @param ti
	 */
	public void saveRecordTaskMessage(Long vProcess,Long nodeId,Long processInstanceId ,TaskInstance ti,String vProcessName,String dataId,String nodeName,String nodeDesc,String[] auditPers,String processCreator);
	/**
	 * 根据虚拟流程名字和实体数据唯一确定一条数据
	 * @param dataId
	 * @param vProcessName
	 * @return
	 */
	public WorkflowRecordTaskInfo findWorkflowRecordTaskInfo(String dataId,String vProcessName);
	/**
	 * 通过反射得到相应的流程历史表，在通过流程实例得到相应的流程历史
	 * @param historyEntity
	 * @param processInstanceId
	 * @return
	 */
	public List findAllWorkflowHistoryMessage(String historyEntity,Long processInstanceId);
	/**
	 * 得到相应的用户信息，取出用户信息中的几个关键字段
	 * @param userId
	 * @return
	 */
	public String findUserInfoMessageById(Long userId);
	
	/**
	 * 组装HTML邮件发送
	 * @Methods Name htmlContent
	 * @Create In 2009-7-17 By guangsa
	 * @param order
	 * @param opl
	 * @return String
	 */
	public String htmlContent(String nodeName,String pageUrl,String applyType,String dataId,String reqClass,String goStartState,Long taskId,String creator,String vDesc,List auditHis,String hurryFlag ,boolean browsePerson);
	
	/**
	 * 组装HTML邮件发送 ITIL专用
	 * @Methods Name htmlContent
	 * @Create In 2009-11-27 By gaowen
	 * @param order
	 * @param opl
	 * @return String
	 */
	public String htmlContent(String nodeName,String pageUrl,String applyType,String dataId,String reqClass,String goStartState,Long taskId,UserInfo creatorMeg,String vDesc,List auditHis,String hurryFlag ,boolean browsePerson,UserInfo userInfo);
	/**
	 * 通过角色的ID来查找相应的用户名
	 * @Methods Name getUserNameByRoleId
	 * @Create In 2009-7-17 By guangsa
	 * @param roleId
	 * @return String
	 */
	public List getUserNameByRoleId(Role role);
	/**
	 * 通过用户名来查找相应的待审记录
	 * @Methods Name getUserNameByRoleId
	 * @Create In 2009-7-17 By guangsa
	 * @param roleId
	 * @return String
	 */
	public List getTaskProxyObject(String userName);
	/**
	 * 根据流程ID和节点ID找到相应的邮件配置单元
	 * @param virtualDefinitionId
	 * @param nodeId
	 * @return
	 */
	public ConfigUnitMail findConfigUnitMailById(Long virtualDefinitionId,Long nodeId);
	
	 /**
	 * 通过真实流程ID来得到相应的工作流任务实例
	 * @param processId
	 * @return
	 */
	public WorkflowRecordTaskInfo findWorkflowRecordByProcessId(Long processId);
	/**
	 * 根据实体保存相应任务历史
	 * @Methods Name saveWorkflowTaskInfoByEntity
	 * @Create In Mar 9, 2010 By debby
	 * @param recordTask void
	 */
	public void saveWorkflowTaskInfoByEntity(WorkflowRecordTaskInfo recordTask);
	
	

}
