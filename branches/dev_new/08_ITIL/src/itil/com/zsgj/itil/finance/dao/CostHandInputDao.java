package com.zsgj.itil.finance.dao;

import java.util.Map;

import com.zsgj.info.framework.dao.Dao;
import com.zsgj.info.framework.dao.support.Page;

public interface CostHandInputDao extends Dao{
	/**
	 * 分页查询所有系统中正式的配置项
	 * @Methods Name selectConfigItem
	 * @Create In Oct 12, 2010 By liaogs1
	 * @Return Page
	 */
	public Page selectConfigItem(int pageNo, int pageSize,String value);
	/**
	 * 分页查询所有系统中正式的配置项
	 * @Methods Name selectServiceItem
	 * @Create In Oct 12, 2010 By liaogs1
	 * @Return Page
	 */
	public Page selectServiceItem(int pageNo, int pageSize,String value);
	/**
	 * 分页查询所有费用类型
	 * @Methods Name selectServiceItem
	 * @Create In Oct 12, 2010 By liaogs1
	 * @Return Page
	 */
	public Page selectFinanceCostType(int pageNo, int pageSize,String value);
	/**
	 * 分页查询所有用户
	 * @Methods Name selectServiceItem
	 * @Create In Oct 12, 2010 By liaogs1
	 * @Return Page
	 */
	public Page selectReimbursement(int pageNo, int pageSize,String value);
	
	/**
	 * 分页查询所有成本中心数据
	 * @Methods Name selectFinanceCostCenter
	 * @Create In Oct 12, 2010 By liaogs1
	 * @Return Page
	 */
	public Page selectFinanceCostCenter(int pageNo, int pageSize,String value);
	
	/**
	 * 根据对象的属性和属性值查找对象
	 * @Methods Name findObjectByProperty
	 * @Create In Oct 14, 2010 By Liaos1
	 * @param s : 对象的全路径，用于构造Class
	 * @param id ： 对象的主键id
	 * @param propertyName ：对象的属性
	 * @param propertyValue ： 对象的属性值
	 * @return Object ： 返回该对象
	 */
	public Object findObjectByProperty(String s,Long id,String propertyName,Object propertyValue);
	
	/**
	 * 查询列表
	 * @Methods Name selectList
	 * @Create In Oct 14, 2010 By liaogs1
	 * @param clazz : 对象的全路径，用于构造Class
	 * @param map ： 条件
	 * @param pageNo ： 页码
	 * @param pageSize ：页大小
	 * @return Page
	 */
	public Page selectList(Map<String,String> map,int pageNo, int pageSize);
}
