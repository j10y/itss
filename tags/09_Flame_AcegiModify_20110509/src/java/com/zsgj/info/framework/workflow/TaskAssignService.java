package com.zsgj.info.framework.workflow;

import java.util.List;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.workflow.entity.ConfigUnitRole;
import com.zsgj.info.framework.workflow.entity.ConfigUnitRoleTable;
import com.zsgj.info.framework.workflow.entity.DefinitionPreAssign;
import com.zsgj.info.framework.workflow.entity.TaskPreAssign;
import com.zsgj.info.framework.workflow.entity.WorkflowRole;
import com.zsgj.info.framework.workflow.entity.WorkflowRoleMaping;
import com.zsgj.info.framework.workflow.info.NodeInfo;
/**
 * ����Ԥָ�ɽӿڣ���Ҫ��ά������Ԥָ����Ϣ����Աί�д���������Ϣ
 * һ�����̶���ֻ��Ӧһ�����ţ������Ա��¼����ž���ʹ��
 * һ�����������ڵ�ֻ��Ӧһ�����̽�ɫ�������̽�ɫ������ʵ�ʽ�ɫ�о���Ķ�Ӧ��ϵ
 * @Class Name TaskAssignService
 * @Author yang
 * @Create In Dec 8, 2008
 */
public interface TaskAssignService {
		
	/**
	 *  �г�����Ԥָ�ɵ������б�
	 * @Methods Name listPreAssignTask
	 * @Create In 2008-9-15 By sa
	 * @param definiName
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page listDefinitionPreAssign(String definiName, int pageNo, int pageSize);
	
	/**
	 * �������̶����Ԥָ�ɣ����ظ����ڵ��ָ����Ϣ
	 * ����һ��
	 * @Methods Name addDefinitionPreAssign
	 * @Create In Dec 8, 2008 By yang
	 * @param departmentCode
	 * @param departmentName
	 * @param definitionName
	 * @return 
	 * @ReturnType List<DefinitionPreAssign>
	 */
	List<DefinitionPreAssign> addDefinitionPreAssign(String definitionName,String definitionDesc,String departmentCode,List<NodeInfo> nodes);
	
	/**
	 * ɾ�����̶����Ԥָ�ɣ�ɾ�������벿�ŵĹ�ϵ�����������ڵ��ָ����Ϣ
	 * ɾ������һ���б�
	 * @Methods Name removeDefinitionPreAssign
	 * @Create In Dec 8, 2008 By yang
	 * @param definitionName 
	 * @ReturnType void
	 */
	void removeDefinitionPreAssign(String definitionName);
	
	/**
	 * �޸����̶�����������
	 * �޸�һ��
	 * @Methods Name updateDefinitionPreAssignDepert
	 * @Create In Dec 8, 2008 By yang
	 * @param definitionName
	 * @param departmentCode
	 * @param departmentName
	 * @return 
	 * @ReturnType DefinitionPreAssign
	 */
	int updateDefinitionPreAssignDepart(String definitionName,String departmentCode,String departmentName);	
	
	//begin add peixf
	/**
	 * 5������һ����Ա���������̽�ɫ�����в��ŵ�
	 */
	List<WorkflowRole> findWorkflowRoleByUserInfo(UserInfo userInfo);
	
	/**
	 * 6������ĳ���ŵ�һ�����̽�ɫ��������Ա
	 */
	List<UserInfo> findUserInfoByWorkflowRoleAndDepartment(WorkflowRole workFlowRole,Department dept);
	
	/**
	 * 7������һ�����ŵ��������̽�ɫ
	 */
	List<WorkflowRole> findWorkflowRoleByDepartment(Department dept);
	
	WorkflowRoleMaping findWorkflowRoleMapingById(String wfrmId);
	
	WorkflowRoleMaping saveWorkflowRoleMaping(WorkflowRoleMaping wfrm);
	
	List<WorkflowRole> findAllWorkflowRoles();
	
	void removeWorkflowRoleMapingById(String[] wfrmIds);
	
	//end
	public List<TaskPreAssign> listPreAssignTask(String definiName);

	public Page listPreAssignTask(String definiName, int pageNo, int pageSize);
	/**
	 * ͨ���ڵ����ͺ����������ҵ���Ӧ�����ý�ɫ
	 * @Methods Name findUnitRoleByNodeTypeAndProDesc
	 * @Create In Feb 25, 2009 By guangsa
	 * @param processName
	 * @param nodeDesc
	 * @return ConfigUnitRole
	 */
	public ConfigUnitRole findUnitRoleByNodeTypeAndProDesc(String processName ,String nodeDesc,String virtualDefinitionId,Long nodeId);
	/**
	 * ͨ�����õ�Ԫ��ɫ�ҵ���Ӧ�Ľ�ɫ
	 * @Methods Name findRoleTableByConfigUnitRole
	 * @Create In Feb 25, 2009 By guangsa
	 * @param unitRole
	 */
	public List<ConfigUnitRoleTable> findRoleTableByConfigUnitRole(ConfigUnitRole unitRole);
	
	//add by guangsa for counterSignAssign in 20090722 begin
	
	//add by guangsa for counterSignAssign in 20090722 end

}
