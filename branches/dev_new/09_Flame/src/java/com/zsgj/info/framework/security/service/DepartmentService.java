/**
 * @Probject Name: 10_InfoFramework_B2B
 * @Path: com.digitalchina.ibmb2b.system.serviceDepartmentService.java
 * @Create By zhangpeng
 * @Create In 2008-7-18 下午05:05:03
 * TODO
 */
package com.zsgj.info.framework.security.service;

import java.util.List;
import java.util.Map;

import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.workflow.entity.WorkflowRole;

/**
 * 部门相关服务
 * @Class Name DepartmentService
 * @Author zhangpeng
 * @Create In 2008-7-18
 */
public interface DepartmentService {
	List findRootDept(String deptCode);
	/**
	 * 查找部门
	 * @Methods Name findRootDepartments
	 * @Create In Apr 20, 2009 By Administrator
	 * @return List
	 */
	List findRootDepartments(String departmentCode);
	/**
	 * 获取指定Code的部门信息
	 * @Methods Name findDepartmentById
	 * @Create In 2008-7-18 By zhangpeng
	 * @param id
	 * @return Department
	 */
	Department findDepartmentById(Long departCode);
	
	/**
	 * 获取指定id的部门信息
	 * @Methods Name findDepartmentById
	 * @Create In 2008-7-18 By zhangpeng
	 * @param id
	 * @return Department
	 */
	Department findDepartmentByRealId(Long id);
	
	/**
	 * 获取指定id的部门信息
	 * @Methods Name findDepartmentById
	 * @Create In 2008-7-18 By zhangpeng
	 * @param id
	 * @return Department
	 */
	Department findDepartmentByName(String name);
	
	/**
	 * 获取事业部级别的所有部门
	 * @Methods Name findDeptAll
	 * @Create In 2008-8-31 By sa
	 * @return List
	 */
	List findDeptAll();
	
	
	/**
	 * 通过上级部门编号获取其所有子部门，上级部门编号可以使用资源文件或手动传入
	 * String rootDeptCode = PropertiesUtil.getProperties("system.rootdept.deptcode");
	 * @Methods Name findDeptAll
	 * @Create In 2008-8-31 By sa
	 * @return List
	 */
	List findDeptByParentCode(String deptCode);
	
	
	/**
	 * 提供部门获取其所有下级部门，即人员的所属部门
	 * @Methods Name findChildDeptByParent
	 * @Create In 2008-11-7 By sa
	 * @param dept
	 * @return List
	 */
	List findChildDeptByParent(Department dept);
	
	/**
	 * 2、列表某部门的所有人员
	 * @Methods Name findUserInfoByDepartment
	 * @Create In 2008-12-9 By sa
	 * @param dept
	 * @return List<UserInfo>
	 */
	List<UserInfo> findUserInfoByDepartment(Department dept);
	
	/**
	 * 3、返回一个部门的所有上级部门,从上至下排序，第一个为神州数码，最后为自身
	 * @Methods Name findDepartmentParents
	 * @Create In 2008-12-9 By sa
	 * @param dept
	 * @return List<Department>
	 */
	List<Department> findDepartmentParents(Department dept);
	
	/**
	 * 4、返回一个人员的所属部门
	 * @Methods Name findDepartmentByUserInfo
	 * @Create In 2008-12-9 By sa
	 * @param userInfo
	 * @return Department
	 */
	Department findDepartmentByUserInfo(UserInfo userInfo);
	
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
	
	/**
	 * 8、返回一个部门的所有下级部门，树状结构Map嵌套，departmentCode做键值
	 * @Methods Name findDepartmentChildren
	 * @Create In 2008-12-10 By sa
	 * @param dept
	 * @return Map<String,Map>
	 */
	Map<String,Map> findDepartmentChildren(Department dept);
	
	/**
	 * 根据部门名称模糊查找部门
	 * @Methods Name findDepartmentByDepName
	 * @Create In Mar 30, 2009 By chuanyu ou
	 * @return Map
	 */
	Map findDepartmentByDepName(String depName, String orderBy, boolean isAsc, int pageNo, int pageSize);

	/**
	 * 添加子部门
	 * @Methods Name addChildDepartment
	 * @Create In Jun 14, 2009 By Administrator
	 * @param parentDeptId
	 * @param childDeptName 
	 * @return void
	 */
	void addChildDepartment(Long parentDeptId, String childDeptName);
	
	/**
	 * 删除当前选中部门
	 * @Methods Name deleteCurrentDepartment
	 * @Create In Jun 14, 2009 By Administrator
	 * @param parentDeptId
	 * @param childDeptName
	 * @param childDeptCode void
	 */
	void deleteCurrentDepartment(Long departmentId);
	
	/**
	 * 修改子部门
	 * @Methods Name deleteChildDepartment
	 * @Create In Jun 14, 2009 By Administrator
	 * @param deptCode
	 * @param departmentName
	 * @return void
	 */
	void modifyCurrentDepartment(Long deptCode, String departmentName);	
}
