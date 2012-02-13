package com.xpsoft.core.service;

import java.util.List;

public abstract interface DanpinBaseService<T> extends DanpinGenericService<T, Long>
{
	public List findDataList(String sql);
	
	public boolean removeDatabySql(String sql); 
	
	public boolean updateDatabySql(String sql);
}
