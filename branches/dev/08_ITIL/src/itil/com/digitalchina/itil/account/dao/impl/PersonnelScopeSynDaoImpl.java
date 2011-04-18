package com.digitalchina.itil.account.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.digitalchina.info.framework.exception.DaoException;
import com.digitalchina.itil.account.dao.PersonnelScopeSynDao;
import com.digitalchina.itil.account.service.DataBaseConnection;

public class PersonnelScopeSynDaoImpl extends JdbcDaoSupport implements PersonnelScopeSynDao {
	
	public void saveOrUpdatePersonnelScope() throws DaoException {
		Connection oracleConnection=null;
		Connection msConnection=null;
		Statement oracleStatement=null;
		ResultSet oracleRS=null;
		Statement msStatement=null;
		PreparedStatement msPS=null;
		String oracleSql = "select RSZFWDM, RSZFWMC from DCDWS.MB_RLZY_RSZFW";
		String msSelectSql="select name from sys_PersonnelScope where personnelScopeCode =?";
		try {
			oracleConnection=DataBaseConnection.getOracleConnection();
			msConnection=getConnection();
			msConnection.setAutoCommit(false);
			oracleStatement=oracleConnection.createStatement();
			oracleRS=oracleStatement.executeQuery(oracleSql);
			msStatement=msConnection.createStatement();
			msPS=msConnection.prepareStatement(msSelectSql);
			for(int i=1;oracleRS.next();i++){
				String psCode = oracleRS.getString("RSZFWDM");
				String oracleName=oracleRS.getString("RSZFWMC");
				msPS.setString(1,psCode);
				ResultSet msRS=msPS.executeQuery();
				String msName=null;
				if(msRS.next()){
					msName=msRS.getString(1);
				}
				msRS.close();
				StringBuilder msSql=new StringBuilder();
				if(msName==null){
					msSql.append("insert into sys_PersonnelScope");
					msSql.append(" (personnelScopeCode,name)");
					msSql.append(" values('"+psCode+"','"+oracleName+"')");
					msStatement.executeUpdate(msSql.toString());
					msConnection.commit();
				}
				else if(!msName.equals(oracleName)){
					msSql.append("update sys_PersonnelScope");
					msSql.append(" set name ='"+oracleName+"'");
					msSql.append(" where personnelScopeCode ='"+psCode+"'");
					msStatement.executeUpdate(msSql.toString());
					msConnection.commit();
				}
			}
			System.out.println("同步MB_RLZY_RSZFW完成 ");
		} catch (Exception e) {
			e.printStackTrace();
			try {msConnection.rollback();} catch (SQLException e1) {e.printStackTrace();}
			throw new DaoException(e.getMessage());
		}finally{
			if(oracleConnection!=null)try{oracleConnection.close();}catch(Exception e){e.printStackTrace();}
			if(msConnection!=null)try{msConnection.close();}catch(Exception e){e.printStackTrace();}
			if(oracleRS!=null)try {oracleRS.close();} catch (SQLException e) {e.printStackTrace();}
			if(oracleStatement!=null)try {oracleStatement.close();} catch (SQLException e) {e.printStackTrace();}
			if(msPS!=null)try {msPS.close();} catch (SQLException e) {e.printStackTrace();}
			if(msStatement!=null)try {msStatement.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
	}

	public void updateUserInfo() throws DaoException {
		Connection oracleConnection=null;
		Connection msConnection=null;
		Statement oracleStatement=null;
		ResultSet oracleRS=null;
		PreparedStatement msPS=null;
		String oracleSql="select RYDM, RSZFWDM from DCDWS.MX_RLZY_RSBDZZFP where to_date(JSRQ,'YYYYMMDD')> SYSDATE";
		StringBuilder msSql=new StringBuilder();
		msSql.append("update sUserInfos set personnelScope = (");
		msSql.append(" select id from sys_PersonnelScope");
		msSql.append(" where personnelScopeCode = ? )");
		msSql.append(" where employeeCode = ?");
		try {
			oracleConnection=DataBaseConnection.getOracleConnection();
			msConnection=getConnection();
			msConnection.setAutoCommit(false);
			oracleStatement=oracleConnection.createStatement();
			oracleRS=oracleStatement.executeQuery(oracleSql);
			msPS=msConnection.prepareStatement(msSql.toString());
			for(int i=1;oracleRS.next();i++){
				String psCode = oracleRS.getString("RSZFWDM");
				String employeeCode = oracleRS.getString("RYDM");
				msPS.setString(1,psCode);
				msPS.setString(2, employeeCode);
				msPS.addBatch();
				if(i%200==0){
					msPS.executeBatch();
					msConnection.commit();
				}
			}
			msPS.executeBatch();
			msConnection.commit();
			System.out.println("同步MX_RLZY_RSBDZZFP完成 ");
		} catch (Exception e) {
			e.printStackTrace();
			try {msConnection.rollback();} catch (SQLException e1) {e.printStackTrace();}
			throw new DaoException(e.getMessage());
		}finally{
			if(oracleConnection!=null)try{oracleConnection.close();}catch(Exception e){e.printStackTrace();}
			if(msConnection!=null)try{msConnection.close();}catch(Exception e){e.printStackTrace();}
			if(oracleRS!=null)try {oracleRS.close();} catch (SQLException e) {e.printStackTrace();}
			if(oracleStatement!=null)try {oracleStatement.close();} catch (SQLException e) {e.printStackTrace();}
			if(msPS!=null)try {msPS.close();} catch (SQLException e) {e.printStackTrace();}
		}
	}
}
