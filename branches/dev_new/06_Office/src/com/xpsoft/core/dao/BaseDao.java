package com.xpsoft.core.dao;

import java.util.List;

/**
 * 大部分Dao仅需要继承该接口即可
 * @author csx
 *
 * @param <T>
 */
public interface BaseDao<T> extends GenericDao<T,Long> {
	
}
