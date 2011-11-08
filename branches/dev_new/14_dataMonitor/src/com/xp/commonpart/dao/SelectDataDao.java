package com.xp.commonpart.dao;

import java.util.List;

public interface SelectDataDao {
	public List selectData(String sql);
	
	public boolean deletebyid(String sql);
	
	public String[] getColumnName(String sql);
	
	public int selectDataRowNum(String sql);
	
	public void insertRealTable(String sql);
}
