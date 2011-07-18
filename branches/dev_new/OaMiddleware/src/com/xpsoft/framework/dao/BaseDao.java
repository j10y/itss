package com.xpsoft.framework.dao;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * 实现Dao接口 为基础数据提供数据库访问方法
 * @Class Name BaseDao
 * @Author likang
 * @Create In Jul 19, 2010
 */
public class BaseDao<T> extends SqlMapClientDaoSupport implements Dao<T>{



	/**
	 * 根据实体class查找全部对象
	 * @Methods Name removeObject
	 * @Create In Jul 22, 2010 By debby
	 * @param class 查询实体的class
	 * @return void
	 */
	public List getObjects(Class clazz) throws Exception{
		return this.getSqlMapClientTemplate().queryForList(clazz.getName()+".selectAll");
	}

	/**
	 * 根据实体的id删除
	 * @Methods Name removeObject
	 * @Create In Jul 22, 2010 By debby
	 * @param class 删除实体的class
	 * @param Serializable 实体Id
	 * @return void
	 */
	public void removeObject(Class clazz, Object id) throws Exception{
		this.getSqlMapClientTemplate().delete(clazz.getName()+".delete",id);
		
	}
	
	/**
	 * 实体对象的通用保存方法
	 * 通过sqlmapconfig中的insert来保存实体
	 * @Methods Name save
	 * @Create In Jul 22, 2010 By debby
	 * @param o 实体对象
	 * @return Object
	 */
	public Object save(Object o) throws Exception{
		this.getSqlMapClientTemplate().insert(o.getClass().getName()+".insert",o);
		return o;
	}
	
	/**
	 * 根据实体的id逻辑删除
	 * @Methods Name removeObjectByFlag
	 * @Create In Jul 22, 2010 By likang
	 * @param class 删除实体的class
	 * @param Serializable 实体Id
	 * @return void
	 */
	public void removeObjectByFlag(Class clazz, Object id) throws Exception{
		this.getSqlMapClientTemplate().update(clazz.getName()+".deleteByLogic",id);
	}

	/**
	 * 带参数查询全部结果的方法，
	 * 所有参数根据配置文件的查询sql组成map对象
	 * querySql参数为查询sql的id
	 * 可以根据传入的参数进行查询结果的排序
	 * @Methods Name getObjectsByParam
	 * @Create In Jul 22, 2010 By debby
	 * @param clazz
	 * @param map
	 * @param querySql
	 * @return List
	 */
	public List getObjectsByParam(Class clazz, Map map, String querySql) throws Exception{
		return this.getSqlMapClientTemplate().queryForList(clazz.getName()+"."+querySql,map);
	}

	/**
	 * 根据实体ID查找对象
	 * @Methods Name getObjectById
	 * @Create In Jul 22, 2010 By likang
	 * @param clazz
	 * @param id
	 * @return Object
	 */
	public Object getObjectById(Class clazz, Object id) throws Exception{
		return this.getSqlMapClientTemplate().queryForObject(clazz.getName()+".selectById",id);
	}

	/**
     * 根据ids物理删除 例如 1,2,3
     * @Methods Name removeObjectByIds
     * @Create In Aug 3, 2010 By likang
     * @param clazz
     * @param value
     * @throws Exception void
     */
	public void removeObjectByIds(Class clazz, String value) throws Exception{
		this.getSqlMapClientTemplate().delete(clazz.getName()+".deleteByIds",value);
	}

	/**
	 * 更新实体对象
	 * @Methods Name update
	 * @Create In Jul 22, 2010 By debby
	 * @param o void
	 */
	public void update(Object o) throws Exception{
		this.getSqlMapClientTemplate().update(o.getClass().getName()+".update",o);
	}

	/**
	 * 直接查询全部总数
	 * @Methods Name selectTotal
	 * @Create In Jul 23, 2010 By debby
	 * @param clazz
	 * @return int
	 */
	public Integer selectTotal(Class clazz) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(clazz.getName()+".selectTotal");
	}

	/**
	 * 根据参数查询全部总数
	 * @Methods Name selectTotalByParam
	 * @Create In Jul 23, 2010 By debby
	 * @param clazz
	 * @param map
	 * @return int
	 */
	public Integer selectTotalByParam(Class clazz, Map map) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(clazz.getName()+".selectTotalByParam",map);
	}
	
	/**
	 * 按条件不分页获取指定类的所有持久化对象
	 * @Methods Name getObjectsByParamNoPage
	 * @Create In Aug 3, 2010 By likang
	 * @param clazz
	 * @param map	  需要传入参数的map  例如 map.put("属性名",值);
	 * @return
	 * @throws Exception List
	 */
	public List getObjectsByParamNoPage(Class clazz, Map map) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().queryForList(clazz.getName()+".selectObjectsByParamNoPage",map);
	}
	
	/**
     * 批量逻辑删除持久化对象
     * 默认删除标记为deleteFlag 需要每个实体有该属性
     * @Methods Name removeObjectsByFlag
     * @Create In Aug 3, 2010 By likang
     * @param clazz
     * @param values id的逗号分隔 如 1,2,3
     * @throws Exception void
     */
	public void removeObjectsByFlag(Class clazz, String values) {
		// TODO Auto-generated method stub
		this.getSqlMapClientTemplate().update(clazz.getName()+".deleteObjectsByLogic",values);
	}
	
	/**
	 * 根据查询语句名字，按条件不分页获取指定类的所有持久化对象
	 * @Methods Name getObjectsByQueryName
	 * @Create In Jan 21, 2011 By likang
	 * @param clazz
	 * @param queryName
	 * @param map
	 * @return List
	 */
	public List getObjectsByQueryName(Class clazz, String queryName, Map map) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().queryForList(clazz.getName()+"." + queryName,map);
	}
	
	/**
	 * 根据参数，通过查询语句的名字查询全部总数
	 * @Methods Name selectTotalByQueryName
	 * @Create In Jan 22, 2011 By likang
	 * @param clazz
	 * @param queryName
	 * @param map
	 * @return Integer
	 */
	public Integer selectTotalByQueryName(Class clazz, String queryName, Map map) {
		// TODO Auto-generated method stub
		return (Integer) this.getSqlMapClientTemplate().queryForObject(clazz.getName()+"."+queryName,map);
	}
}
