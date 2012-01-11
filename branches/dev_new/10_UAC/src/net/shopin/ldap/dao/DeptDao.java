package net.shopin.ldap.dao;

import java.util.List;

import net.shopin.ldap.entity.Department;
import net.shopin.ldap.entity.User;

import org.springframework.ldap.core.ContextMapper;

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
	 * @param deptDN
	 */
	void deleteByDN(String deptDN);
	
	/**
	 * 根据部门编号查询部门信息
	 * @param deptDN
	 * @return
	 */
	Department findByDN(String deptDN);
	
	/**
	 * 查询部门列表，每次展开一级
	 * @param parentDN
	 * @return
	 */
	List<Department> findSubDeptsByParentDN(String parentDN);
	
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
