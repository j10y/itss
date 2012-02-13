package com.xpsoft.core.dao.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.xpsoft.core.dao.DanpinBaseDao;
import com.xpsoft.core.web.paging.PagingBean;

/**
 * 
 * @author csx
 *
 * @param <T> 基@SuppressWarnings("unchecked")
础表类，对于主键为long类型　，则直接继承该类，若主键为其他类型，
 * 需要直接继承GenericDaoImpl
 */
@SuppressWarnings("unchecked")
public abstract class DanpinBaseDaoImpl<T> extends DanpinGenericDaoImpl<T, Long> implements DanpinBaseDao<T>{

	public DanpinBaseDaoImpl(Class persistType) {
		super(persistType);
	}
	public List findDataList(String sql) {
		List list=this.jdbcTemplate.queryForList(sql);
		return list;
	}
	
	 public boolean removeDatabySql(String sql){
		 try {
			this.jdbcTemplate.execute(sql);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 return false;
		}
		 return true;
	 } 
	 
	 public boolean updateDatabySql(String sql){
		 try {
			this.jdbcTemplate.update(sql);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 return false;
		}
		 return true;
	 } 
}
