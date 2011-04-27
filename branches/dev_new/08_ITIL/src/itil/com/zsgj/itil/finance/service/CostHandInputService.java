package com.zsgj.itil.finance.service;

import java.util.Map;
import net.sf.json.JSONObject;

public interface CostHandInputService {
	/**
	 * 分页查询所有系统中正式的配置项
	 * @Methods Name findConfigItem
	 * @Create In Oct 15, 2010 By liaogs1
	 * @param start
	 * @param pageSize
	 * @param item ：查询项的标识 1表示配置项;2表示服务项
	 * @param propertyValue : 用于页面数据的自动填充
	 * @return String : json 串
	 */
	public String findItem(int start, int pageSize,String item,String propertyValue);
	
	/**
	 * 分页查询所有费用类型
	 * @Methods Name findCostType
	 * @Create In Oct 15, 2010 By liaogs1
	 * @param start
	 * @param pageSize
	 * @param propertyValue
	 * @return String
	 */
	public String findCostType(int start, int pageSize,String propertyValue);
	
	/**
	 * 分页查询所有用户
	 * @Methods Name findReimbursement
	 * @Create In Oct 15, 2010 By liaogs1
	 * @param start
	 * @param pageSize
	 * @param propertyValue
	 * @return String
	 */
	public String findReimbursement(int start, int pageSize,String propertyValue);

	/**
	 * 分页查询所有成本中心数据
	 * @Methods Name findFinanceCostCenter
	 * @Create In Oct 15, 2010 By liaogs1
	 * @param start
	 * @param pageSize
	 * @param propertyValue
	 * @return String
	 */
	public String findFinanceCostCenter(int start, int pageSize,String propertyValue);
	
	/**
	 * 保存财务成本明细
	 * @Methods Name saveFinanceCostSchedules
	 * @Create In Oct 14, 2010 By liaogs1
	 * @param jo
	 * @return boolean
	 */
	public boolean saveFinanceCostSchedules(JSONObject jo);
	
	/**
	 * 查询列表
	 * @Methods Name findList
	 * @Create In Oct 15, 2010 By liaogs1
	 * @param map
	 * @param start
	 * @param pageSize
	 * @return String
	 */
	public String findList(Map<String,String> map,int start, int pageSize);
	
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
}
