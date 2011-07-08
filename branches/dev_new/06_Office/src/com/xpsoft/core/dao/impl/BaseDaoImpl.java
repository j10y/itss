package com.xpsoft.core.dao.impl;

import java.util.List;

import com.xpsoft.core.dao.BaseDao;
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
public abstract class BaseDaoImpl<T> extends GenericDaoImpl<T, Long> implements BaseDao<T>{

	public BaseDaoImpl(Class persistType) {
		super(persistType);
	}
	
}
