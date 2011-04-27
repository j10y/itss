/**
 * @Probject Name: 10_InfoFramework_B2B
 * @Path: com.digitalchina.ibmb2b.system.serviceDepartmentService.java
 * @Create By zhangpeng
 * @Create In 2008-7-18 ����05:05:03
 * TODO
 */
package com.zsgj.info.framework.security.service;

import java.util.List;
import java.util.Map;

import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.workflow.entity.WorkflowRole;

/**
 * ������ط���
 * @Class Name DepartmentService
 * @Author zhangpeng
 * @Create In 2008-7-18
 */
public interface DepartmentService {
	List findRootDept(String deptCode);
	/**
	 * ���Ҳ���
	 * @Methods Name findRootDepartments
	 * @Create In Apr 20, 2009 By Administrator
	 * @return List
	 */
	List findRootDepartments(String departmentCode);
	/**
	 * ��ȡָ��Code�Ĳ�����Ϣ
	 * @Methods Name findDepartmentById
	 * @Create In 2008-7-18 By zhangpeng
	 * @param id
	 * @return Department
	 */
	Department findDepartmentById(Long departCode);
	
	/**
	 * ��ȡָ��id�Ĳ�����Ϣ
	 * @Methods Name findDepartmentById
	 * @Create In 2008-7-18 By zhangpeng
	 * @param id
	 * @return Department
	 */
	Department findDepartmentByRealId(Long id);
	
	/**
	 * ��ȡָ��id�Ĳ�����Ϣ
	 * @Methods Name findDepartmentById
	 * @Create In 2008-7-18 By zhangpeng
	 * @param id
	 * @return Department
	 */
	Department findDepartmentByName(String name);
	
	/**
	 * ��ȡ��ҵ����������в���
	 * @Methods Name findDeptAll
	 * @Create In 2008-8-31 By sa
	 * @return List
	 */
	List findDeptAll();
	
	
	/**
	 * ͨ���ϼ����ű�Ż�ȡ�������Ӳ��ţ��ϼ����ű�ſ���ʹ����Դ�ļ����ֶ�����
	 * String rootDeptCode = PropertiesUtil.getProperties("system.rootdept.deptcode");
	 * @Methods Name findDeptAll
	 * @Create In 2008-8-31 By sa
	 * @return List
	 */
	List findDeptByParentCode(String deptCode);
	
	
	/**
	 * �ṩ���Ż�ȡ�������¼����ţ�����Ա����������
	 * @Methods Name findChildDeptByParent
	 * @Create In 2008-11-7 By sa
	 * @param dept
	 * @return List
	 */
	List findChildDeptByParent(Department dept);
	
	/**
	 * 2���б�ĳ���ŵ�������Ա
	 * @Methods Name findUserInfoByDepartment
	 * @Create In 2008-12-9 By sa
	 * @param dept
	 * @return List<UserInfo>
	 */
	List<UserInfo> findUserInfoByDepartment(Department dept);
	
	/**
	 * 3������һ�����ŵ������ϼ�����,�����������򣬵�һ��Ϊ�������룬���Ϊ����
	 * @Methods Name findDepartmentParents
	 * @Create In 2008-12-9 By sa
	 * @param dept
	 * @return List<Department>
	 */
	List<Department> findDepartmentParents(Department dept);
	
	/**
	 * 4������һ����Ա����������
	 * @Methods Name findDepartmentByUserInfo
	 * @Create In 2008-12-9 By sa
	 * @param userInfo
	 * @return Department
	 */
	Department findDepartmentByUserInfo(UserInfo userInfo);
	
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
	
	/**
	 * 8������һ�����ŵ������¼����ţ���״�ṹMapǶ�ף�departmentCode����ֵ
	 * @Methods Name findDepartmentChildren
	 * @Create In 2008-12-10 By sa
	 * @param dept
	 * @return Map<String,Map>
	 */
	Map<String,Map> findDepartmentChildren(Department dept);
	
	/**
	 * ���ݲ�������ģ�����Ҳ���
	 * @Methods Name findDepartmentByDepName
	 * @Create In Mar 30, 2009 By chuanyu ou
	 * @return Map
	 */
	Map findDepartmentByDepName(String depName, String orderBy, boolean isAsc, int pageNo, int pageSize);

	/**
	 * ����Ӳ���
	 * @Methods Name addChildDepartment
	 * @Create In Jun 14, 2009 By Administrator
	 * @param parentDeptId
	 * @param childDeptName 
	 * @return void
	 */
	void addChildDepartment(Long parentDeptId, String childDeptName);
	
	/**
	 * ɾ����ǰѡ�в���
	 * @Methods Name deleteCurrentDepartment
	 * @Create In Jun 14, 2009 By Administrator
	 * @param parentDeptId
	 * @param childDeptName
	 * @param childDeptCode void
	 */
	void deleteCurrentDepartment(Long departmentId);
	
	/**
	 * �޸��Ӳ���
	 * @Methods Name deleteChildDepartment
	 * @Create In Jun 14, 2009 By Administrator
	 * @param deptCode
	 * @param departmentName
	 * @return void
	 */
	void modifyCurrentDepartment(Long deptCode, String departmentName);	
}
