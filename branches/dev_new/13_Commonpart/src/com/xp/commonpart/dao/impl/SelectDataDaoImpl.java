package com.xp.commonpart.dao.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.xp.commonpart.dao.SelectDataDao;

public class SelectDataDaoImpl extends JdbcDaoSupport implements SelectDataDao{

	public List selectData(String sql) {
		// TODO Auto-generated method stub
		List list=this.getJdbcTemplate().queryForList(sql);
		return list;
	}

	public boolean deletebyid(String sql) {
		// TODO Auto-generated method stub
		try {
			this.getJdbcTemplate().execute(sql);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String[] getColumnName(String sql) {
		// TODO Auto-generated method stub
		SqlRowSet sqlset=this.getJdbcTemplate().queryForRowSet(sql);
		//SqlRowSet sqlset=(SqlRowSet) this.getJdbcTemplate().query(sql,new SqlRowSetResultSetExtractor());
		String[] columnNames=sqlset.getMetaData().getColumnNames();
		return columnNames;
	}

	public int selectDataRowNum(String sql) {
		// TODO Auto-generated method stub
		int i=this.getJdbcTemplate().queryForInt("select count(*) from ("+sql+") tablecount");
		return i;
	}

	public void insertRealTable(String sql) {
		// TODO Auto-generated method stub
		this.getJdbcTemplate().update(sql);
	}
}
