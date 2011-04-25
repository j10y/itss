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
 * ���õ�Ԫ��һЩҵ�񷽷�
 * @Class Name ConfigUnitService
 * @Author guangsa
 * @Create In Mar 4, 2009
 */
public interface ConfigUnitService {
	/**
	 * ͨ������ID��������Ӧ��timer��Ԫ
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
	 * ������������ID�ͽڵ�ID�ҵ�Ψһ��һ����ɫ���õ�Ԫ
	 * @Methods Name findConfigUnitRole
	 * @Create In Mar 30, 2009 By guangsa
	 * @param virProcessId
	 * @param nodeId
	 * @return ConfigUnitRole
	 */
	public ConfigUnitRole findConfigUnitRole(String virProcessId ,String nodeId);
	/**
	 * ������������ID�ͽڵ�ID�ҵ�Ψһ��һ����ɫ���õ�Ԫ
	 * @Methods Name findConfigUnitRoleTableByConfigUnitRole
	 * @Create In Mar 30, 2009 By guangsa
	 * @param configUnitRole
	 * @param u
	 * @return boolean
	 */
	public boolean findConfigUnitRoleTableByConfigUnitRole(ConfigUnitRole configUnitRole,UserInfo u);
	/**
	 * ������������ID�ͽڵ�ID�ҵ�Ψһ��һ���ʼ����õ�Ԫ
	 * @param virProcessId
	 * @param nodeId
	 * @return
	 */
	public ConfigUnitMailNodeSender findConfigUnitMailNodeSenderById(String virProcessId ,String nodeId);
	/**
	 * ����mailCc���û���Ϣ
	 * @Methods Name findUserInfoByParams
	 * @Create In Mar 31, 2009 By guangsa
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	public Page findUserInfoByParams(Map params, int pageNo, int pageSize);
	
	/**
	 * ������ز���ʵ�ֲ��ŵĲ���
	 * @Methods Name findDepartmentByParams
	 * @Create In Mar 31, 2009 By guangsa
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page findDepartmentByParams(Map params, int pageNo, int pageSize);
	
	/**
	 * ����MailNodeSender���û���Ϣ
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
	 * ������������ID�ͽڵ�ID��ȷ���ʼ�ָ��������õ�Ԫ
	 * @param virtualId
	 * @param nodeId
	 */
	public ConfigUnitMailNodeSender findMailNodeById(String virtualId,String nodeId);
	/**
	 * �������ⶨ��ͽڵ���Ψһȷ��һ������ڵ�
	 * @Methods Name findVirtualNodeInfo
	 * @Create In Apr 10, 2009 By guangsa
	 * @param definitionInfo
	 * @param nodeId
	 * @return VirtualNodeInfo
	 */
	public VirtualNodeInfo findVirtualNodeInfo(VirtualDefinitionInfo definitionInfo,String nodeId);
	/**
	 * ������������ID�ͽڵ�ID��ȷ��һ������ڵ�
	 * @Methods Name findVirtualNodeInfo
	 * @Create In Apr 10, 2009 By guangsa
	 * @param vProcessId
	 * @param nodeId
	 * @return
	 */
	public VirtualNodeInfo findVirtualNodeInfoByDoubleId(Long vProcessId , Long nodeId);
	
	/**
	 * ������Ӧ��������������������Ϣ����ҳ
	 * @Methods Name findVirtualDefinitionInfos
	 * @Create In Apr 13, 2009 By Administrator
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	public Page findVirtualDefinitionInfos(Map params, int pageNo, int pageSize);
	
	/**
	 * �õ����������õ�Ԫ
	 * @Methods Name findSubProcessConfigUnit
	 * @Create In Apr 22, 2009 By guangsa
	 * @param virtualId
	 * @param nodeId
	 * @return SubProcessConfigUnit
	 */
	public SubProcessConfigUnit findSubProcessConfigUnit(Long virtualId ,Long nodeId);
	/**
	 * ʵ�ֺ�̨pagemodel�ķ�ҳ����
	 * @Methods Name findPageModelByParams
	 * @Create In May 4, 2009 By guangsa
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	public Page findPageModelByParams(Map params, int pageNo, int pageSize);
	/**
	 * ʵ�ֺ�̨ϵͳ��ɫ�ķ�ҳ����
	 * @Methods Name findSystemRoleByParams
	 * @Create In May 4, 2009 By guangsa
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	public Page findSystemRoleByParams(Map params, int pageNo, int pageSize);
	/**
	 * �����̹��̵��м�¼��ǰ�ڵ������ID
	 * @Methods Name findSystemRoleByParams
	 * @Create In May 4, 2009 By guangsa
	 * @param ti
	 */
	public void saveRecordTaskMessage(Long vProcess,Long nodeId,Long processInstanceId ,TaskInstance ti,String vProcessName,String dataId,String nodeName,String nodeDesc,String[] auditPers,String processCreator);
	/**
	 * ���������������ֺ�ʵ������Ψһȷ��һ������
	 * @param dataId
	 * @param vProcessName
	 * @return
	 */
	public WorkflowRecordTaskInfo findWorkflowRecordTaskInfo(String dataId,String vProcessName);
	/**
	 * ͨ������õ���Ӧ��������ʷ����ͨ������ʵ���õ���Ӧ��������ʷ
	 * @param historyEntity
	 * @param processInstanceId
	 * @return
	 */
	public List findAllWorkflowHistoryMessage(String historyEntity,Long processInstanceId);
	/**
	 * �õ���Ӧ���û���Ϣ��ȡ���û���Ϣ�еļ����ؼ��ֶ�
	 * @param userId
	 * @return
	 */
	public String findUserInfoMessageById(Long userId);
	
	/**
	 * ��װHTML�ʼ�����
	 * @Methods Name htmlContent
	 * @Create In 2009-7-17 By guangsa
	 * @param order
	 * @param opl
	 * @return String
	 */
	public String htmlContent(String nodeName,String pageUrl,String applyType,String dataId,String reqClass,String goStartState,Long taskId,String creator,String vDesc,List auditHis,String hurryFlag ,boolean browsePerson);
	
	/**
	 * ��װHTML�ʼ����� ITILר��
	 * @Methods Name htmlContent
	 * @Create In 2009-11-27 By gaowen
	 * @param order
	 * @param opl
	 * @return String
	 */
	public String htmlContent(String nodeName,String pageUrl,String applyType,String dataId,String reqClass,String goStartState,Long taskId,UserInfo creatorMeg,String vDesc,List auditHis,String hurryFlag ,boolean browsePerson,UserInfo userInfo);
	/**
	 * ͨ����ɫ��ID��������Ӧ���û���
	 * @Methods Name getUserNameByRoleId
	 * @Create In 2009-7-17 By guangsa
	 * @param roleId
	 * @return String
	 */
	public List getUserNameByRoleId(Role role);
	/**
	 * ͨ���û�����������Ӧ�Ĵ����¼
	 * @Methods Name getUserNameByRoleId
	 * @Create In 2009-7-17 By guangsa
	 * @param roleId
	 * @return String
	 */
	public List getTaskProxyObject(String userName);
	/**
	 * ��������ID�ͽڵ�ID�ҵ���Ӧ���ʼ����õ�Ԫ
	 * @param virtualDefinitionId
	 * @param nodeId
	 * @return
	 */
	public ConfigUnitMail findConfigUnitMailById(Long virtualDefinitionId,Long nodeId);
	
	 /**
	 * ͨ����ʵ����ID���õ���Ӧ�Ĺ���������ʵ��
	 * @param processId
	 * @return
	 */
	public WorkflowRecordTaskInfo findWorkflowRecordByProcessId(Long processId);
	/**
	 * ����ʵ�屣����Ӧ������ʷ
	 * @Methods Name saveWorkflowTaskInfoByEntity
	 * @Create In Mar 9, 2010 By debby
	 * @param recordTask void
	 */
	public void saveWorkflowTaskInfoByEntity(WorkflowRecordTaskInfo recordTask);
	
	

}
