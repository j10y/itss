package net.shopin.alipay.service;



import java.util.List;


public abstract interface BaseService<T> extends GenericService<T, Long>
{
	public List findDataList(String sql);
	
	public boolean removeDatabySql(String sql); 
}
