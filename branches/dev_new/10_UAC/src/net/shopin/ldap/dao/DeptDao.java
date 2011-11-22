package net.shopin.ldap.dao;

import java.util.List;

import org.springframework.ldap.core.ContextMapper;

import net.shopin.ldap.entity.Department;

/**
 * 部门Dao接口
 * @author wchao
 *
 */
public interface DeptDao {
	
	/**
	 * 新建部门<br>
	 * 部门编号生成需要根据编号生成规则,在当前部门下最大的加1
	 * @param department
	 */
	void create(Department department);
	
	/**
	 * 修改部门，尽可以修改部门名称
	 * @param department
	 */
	void update(Department department);
	
	/**
	 * 物理删除部门，如果存在关联需要提前进行用户等数据迁移
	 * @param department
	 */
	@Deprecated
	void remove(Department department);
	
	/**
	 * 逻辑删除部门
	 * @param deptRDN
	 */
	void deleteByRDN(String deptRDN);
	
	/**
	 * 根据部门编号查询部门信息
	 * @param deptRDN
	 * @return
	 */
	Department findByRDN(String deptRDN);
	
	/**
	 * 查询部门列表，每次展开一级
	 * @param parentRDN
	 * @return
	 */
	List<Department> findSubDeptsByParentRDN(String parentRDN);
	
	/**
	 * 查询部门列表
	 * 从deptNo和deptName过滤
	 * @param parentNo
	 * @return
	 */
	List<Department> findDeptsByParam(String param);
	
	/**
	 * 
	 * @return
	 */
	ContextMapper getContextMapper();
}
