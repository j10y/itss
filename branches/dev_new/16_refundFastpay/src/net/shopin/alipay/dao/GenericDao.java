package net.shopin.alipay.dao;

import java.io.Serializable;
import java.util.List;

public abstract interface GenericDao<T, PK extends Serializable>{
	  public abstract List findDataList(String sql);
	  
	  public abstract boolean removeDatabySql(String sql); 
	  
	  public abstract boolean updatebySql(String sql); 
}