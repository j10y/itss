package com.digitalchina.info.framework.orm.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.digitalchina.info.framework.orm.BaseDao;
import com.digitalchina.info.framework.orm.JdbcMetaDataAware;

public class JdbcMetaDataAwareImpl extends BaseDao implements JdbcMetaDataAware {

	public ResultSetMetaData getResultSetMetaData(String tableName) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData meta = null;
		try {
			conn = getJdbcTemplate().getDataSource().getConnection();
			stmt=conn.createStatement();
	   		rs=stmt.executeQuery("select * from "+tableName);
	   		meta = rs.getMetaData();
	   		/*for(int i=1;i<=meta.getColumnCount();i++){
				l_cols.add(meta.getColumnName(i));
				System.out.println("meta.getColumnLabel("+i+"): "+meta.getColumnLabel(i));
	   		}*/
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			//JdbcUtil.close(rs, stmt, conn);
		}
		return meta;
	}

}
