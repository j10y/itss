package com.digitalchina.itil.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.digitalchina.info.framework.orm.BaseDao;
import com.digitalchina.itil.dao.JfreeChartDao;
import com.digitalchina.itil.jfreeChart.entity.StatisticsPicture;

public class JfreeChartDaoImpl extends BaseDao implements JfreeChartDao{

	public List selectJfreeChart(StatisticsPicture statisticsPicture) {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData meta = null;
		List list=new ArrayList();
		try {
			conn = getJdbcTemplate().getDataSource().getConnection();
			stmt=conn.createStatement();
			String sql=statisticsPicture.getSqlString();
	   		rs=stmt.executeQuery(sql);
//	   		int i=rs.getMetaData().getColumnCount();
//	   		List ls=new ArrayList();
//	   		for(int j=1;j<=i;j++){
//	   			String ln=rs.getMetaData().getColumnName(j);
//	   			ls.add(ln);
//	   		}
	   		if(statisticsPicture.getTjPictureType().equals("1")){
		   		String itemName=statisticsPicture.getItemName().getColumnName();
		   		while(rs.next()){
		   			String[] values =new String[2];
		   			values[0]=rs.getString(itemName);
		   			values[1]=rs.getInt("num")+"";
		   			list.add(values);
		   		}
	   		}else if(statisticsPicture.getTjPictureType().equals("2")||statisticsPicture.getTjPictureType().equals("3")
	   				||statisticsPicture.getTjPictureType().equals("4")){
	   				String itemName="";
	   				if(statisticsPicture.getItemName()!=null){
	   					itemName=statisticsPicture.getItemName().getColumnName();
	   				}
	   				
	   				String lableTickName="";
	   				if(statisticsPicture.getLableTickName()!=null){
	   					lableTickName=statisticsPicture.getLableTickName().getColumnName();
	   				}
	   			while(rs.next()){
		   			String[] values =new String[3];
		   			values[0]=rs.getInt("num")+"";
		   			values[1]=rs.getString(itemName);
		   			values[2]=rs.getString(lableTickName);
		   			list.add(values);
		   		}
	   		}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			//JdbcUtil.close(rs, stmt, conn);
		}
		return list;
	}

	public List selectRequire() {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData meta = null;
		List list=new ArrayList();
		try {
			conn = getJdbcTemplate().getDataSource().getConnection();
			stmt=conn.createStatement();
			String sql="SELECT COUNT(flm_Requirement.id) as 'num', MAX(Department.DepartName) as 'dpName'";
				   sql+=" FROM flm_Requirement INNER JOIN";
				   sql+=" Department ON flm_Requirement.departCode = Department.DepartCode";
				   sql+=" GROUP BY flm_Requirement.departCode ";
				   sql+=" Order BY COUNT(flm_Requirement.id) ";
	   		rs=stmt.executeQuery(sql);
	   		while(rs.next()){
	   			String[] values =new String[2];
	   			values[0]=rs.getString("dpName");
	   			values[1]=rs.getInt("num")+"";
	   			list.add(values);
	   		}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			//JdbcUtil.close(rs, stmt, conn);
		}
		return list;
	}
	
}
