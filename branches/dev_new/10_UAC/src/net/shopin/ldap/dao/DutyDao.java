package net.shopin.ldap.dao;

import java.util.List;

import org.springframework.ldap.core.ContextMapper;

import net.shopin.ldap.entity.Duty;

/**
 * 职务Dao接口
 * @author wchao
 *
 */
public interface DutyDao {
	
	/**
	 * 新建职务<br>
	 * 职务编号生成需要根据编号生成规则,在当前职务下最大的加1
	 * @param duty
	 */
	void create(Duty duty);
	
	/**
	 * 修改职务，尽可以修改职务名称
	 * @param duty
	 */
	void update(Duty duty);
	
	/**
	 * 物理删除职务，如果存在关联需要提前进行用户等数据迁移
	 * @param duty
	 */
	void remove(Duty duty);
	
	/**
	 * 逻辑删除职务
	 * @param dutyRDN
	 */
	void deleteByRDN(String dutyRDN);
	
	/**
	 * 根据职务编号查询职务信息
	 * @param dutyRDN
	 * @return
	 */
	Duty findByRDN(String dutyRDN);
	
	/**
	 * 查询职务列表，每次展开一级
	 * @param parentRDN
	 * @return
	 */
	List<Duty> findSubDutysByParentRDN(String parentRDN);
	
	/**
	 * 查询职务列表
	 * 从职务名称过滤
	 * @param parentNo
	 * @return
	 */
	List<Duty> findDutysByParam(String param);
	
	/**
	 * 
	 * @return
	 */
	ContextMapper getContextMapper();
}
