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
 * 流程预指派接口，主要是维护定义预指派信息和人员委托代理审批信息
 * 一个流程定义只对应一个部门，但可以被下级部门具体使用
 * 一个流程审批节点只对应一个流程角色，但流程角色可以与实际角色有具体的对应关系
 * @Class Name TaskAssignService
 * @Author yang
 * @Create In Dec 8, 2008
 */
public interface TaskAssignService {
		
	/**
	 *  列出所有预指派的任务列表
	 * @Methods Name listPreAssignTask
	 * @Create In 2008-9-15 By sa
	 * @param definiName
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page listDefinitionPreAssign(String definiName, int pageNo, int pageSize);
	
	/**
	 * 新增流程定义的预指派，返回各个节点的指派信息
	 * 增加一批
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
	 * 删除流程定义的预指派，删除流程与部门的关系和所有其他节点的指派信息
	 * 删除的是一个列表
	 * @Methods Name removeDefinitionPreAssign
	 * @Create In Dec 8, 2008 By yang
	 * @param definitionName 
	 * @ReturnType void
	 */
	void removeDefinitionPreAssign(String definitionName);
	
	/**
	 * 修改流程定义所属部门
	 * 修改一批
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
	 * 5、返回一个人员的所有流程角色，所有部门的
	 */
	List<WorkflowRole> findWorkflowRoleByUserInfo(UserInfo userInfo);
	
	/**
	 * 6、返回某部门的一个流程角色的所有人员
	 */
	List<UserInfo> findUserInfoByWorkflowRoleAndDepartment(WorkflowRole workFlowRole,Department dept);
	
	/**
	 * 7、返回一个部门的所有流程角色
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
	 * 通过节点类型和流程描述找到相应的配置角色
	 * @Methods Name findUnitRoleByNodeTypeAndProDesc
	 * @Create In Feb 25, 2009 By guangsa
	 * @param processName
	 * @param nodeDesc
	 * @return ConfigUnitRole
	 */
	public ConfigUnitRole findUnitRoleByNodeTypeAndProDesc(String processName ,String nodeDesc,String virtualDefinitionId,Long nodeId);
	/**
	 * 通过配置单元角色找到相应的角色
	 * @Methods Name findRoleTableByConfigUnitRole
	 * @Create In Feb 25, 2009 By guangsa
	 * @param unitRole
	 */
	public List<ConfigUnitRoleTable> findRoleTableByConfigUnitRole(ConfigUnitRole unitRole);
	
	//add by guangsa for counterSignAssign in 20090722 begin
	
	//add by guangsa for counterSignAssign in 20090722 end

}
