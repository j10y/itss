package net.shopin.ldap.dao;

import java.util.List;

import net.shopin.ldap.entity.Role;
import net.shopin.ldap.entity.System;

import org.springframework.ldap.core.ContextMapper;

/**
 * 系统Dao接口
 * @author wchao
 *
 */
public interface SystemDao {
	
	/**
	 * 新建系统<br>
	 * 系统编号生成需要根据编号生成规则,在当前系统下最大的加1
	 * @param system
	 */
	void create(System system);
	
	/**
	 * 修改系统，尽可以修改系统名称
	 * @param system
	 */
	void update(System system);
	
	/**
	 * 物理删除系统，如果存在关联需要提前进行用户等数据迁移
	 * @param system
	 */
	void remove(System system);
	
	/**
	 * 逻辑删除系统
	 * @param systemDN
	 */
	void deleteByDN(String systemDN);
	
	/**
	 * 根据系统编号查询系统信息
	 * @param systemDN
	 * @return
	 */
	System findByDN(String systemDN);
	
	/**
	 * 查询系统列表，每次展开一级
	 * @param parentDN
	 * @return
	 */
	List<System> findSubSystemsByParentDN(String parentDN);
	
	/**
	 * 查询系统列表
	 * 从系统名称过滤
	 * @param parentNo
	 * @return
	 */
	List<System> findSystemsByParam(String param);
	
	/**
	 * 
	 * @return
	 */
	ContextMapper getContextMapper();
	
	public List<Role> listMembers(String rolepDN);
	
	public List<System> findSystemsByRoleDN(String roleDN);
}
