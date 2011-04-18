package com.digitalchina.info.framework.dao;

import java.util.List;

import org.springframework.jdbc.support.KeyHolder;

public interface JdbcDao {
	
	/**
	 * 根据SQL建表
	 * @param sql
	 */
	public void createTableBySQL(String sql);

	/**
	 * 统计firstName相同的总数
	 * @param firstName
	 * @return
	 */
	public int findCountOfBaseObjectsByFirstName(String firstName);

	/**
	 * 插入记录并返回自动生成的主键Id
	 * @param ps
	 * @return
	 */
	public KeyHolder insertBaseObject(final BaseObject actor);

	/**
	 * 用SimpleJdbcInsert插入一条记录:mysql测试成功
	 */
	public long inserOneBaseObject(BaseObject actor);

	/**
	 * 插入/更新/删除数据
	 * @param sql有参数语句
	 * @param obj参数值数组
	 */
	public int operateBaseObject(String sql, Object[] obj);

	/**
	 * 根据SQL查询记录总数
	 * 
	 * @param sql
	 * @return
	 */
	public int findRowCountBySQL(String sql);

	/**
	 * 根据Id查找指定对象
	 * 
	 * @param id
	 * @return
	 */
	public BaseObject findBaseObjectById(long id);

	/**
	 * 根据Id查找指定对象
	 * 
	 * @param id
	 * @return
	 */
	public BaseObject findBaseObjectByIdSimple(long id);

	/**
	 * 返回所有对象
	 * 
	 * @return
	 */
	public List findAllBaseObjects();


}

