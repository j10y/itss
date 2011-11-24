package net.shopin.ldap.dao;

import java.util.List;

import org.springframework.ldap.core.ContextMapper;

import net.shopin.ldap.entity.UserGroup;

/**
 * 用户组Dao接口
 * @author wchao
 *
 */
public interface GroupDao {
	
	/**
	 * 新建用户组<br>
	 * 用户组编号生成需要根据编号生成规则,在当前用户组下最大的加1
	 * @param userGroup
	 */
	void create(UserGroup userGroup);
	
	/**
	 * 修改用户组，尽可以修改用户组名称
	 * @param userGroup
	 */
	void update(UserGroup userGroup);
	
	/**
	 * 物理删除用户组，如果存在关联需要提前进行用户等数据迁移
	 * @param userGroup
	 */
	@Deprecated
	void remove(UserGroup userGroup);
	
	/**
	 * 逻辑删除用户组
	 * @param deptRDN
	 */
	void deleteByRDN(String deptRDN);
	
	/**
	 * 根据用户组编号查询用户组信息
	 * @param deptRDN
	 * @return
	 */
	UserGroup findByRDN(String deptRDN);
	
	/**
	 * 查询用户组列表，每次展开一级
	 * @param parentRDN
	 * @return
	 */
	List<UserGroup> findSubDeptsByParentRDN(String parentRDN);
	
	/**
	 * 查询用户组列表
	 * 从deptNo和deptName过滤
	 * @param parentNo
	 * @return
	 */
	List<UserGroup> findDeptsByParam(String param);
	
	/**
	 * 
	 * @return
	 */
	ContextMapper getContextMapper();
}
