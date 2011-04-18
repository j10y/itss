package com.digitalchina.itil.actor.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.digitalchina.info.framework.exception.DaoException;
import com.digitalchina.itil.actor.dao.UserInfoSynDao;

public class UserInfoSynDaoImpl extends JdbcDaoSupport implements UserInfoSynDao{

	public void updateUserInfo(List<Hashtable> users) {
		Map<String,Long> platForms = new HashMap<String,Long>();
		Map<String,Long> userTypes = new HashMap<String,Long>();
		Map<String,Long> personScopes = new HashMap<String,Long>();
		Connection msConnection=null;
		Statement msStatement=null;
		PreparedStatement msPS=null;
		String msSelectSql=null;
		try{
			msConnection=getConnection();
			msConnection.setAutoCommit(false);
			msStatement = msConnection.createStatement();
			
			msSelectSql = "select id,keyword from sys_PlatForm";
			ResultSet msRS = msConnection.prepareStatement(msSelectSql).executeQuery();
			for(int i=1;msRS.next();i++){
				platForms.put(msRS.getString(2), Long.valueOf(msRS.getString(1)));
			}
			msRS.close();
			
			msSelectSql = "select id,code from sys_UserType";
			msRS = msConnection.prepareStatement(msSelectSql).executeQuery();
			for(int i=1;msRS.next();i++){
				userTypes.put(msRS.getString(2), Long.valueOf(msRS.getString(1)));
			}
			msRS.close();
			msSelectSql = "update sUserInfos set enabled=0 where specialUser!=1";
			msConnection.prepareStatement(msSelectSql).execute();
			
			msSelectSql = "select id,personnelScopeCode from sys_PersonnelScope";
			msRS = msConnection.prepareStatement(msSelectSql).executeQuery();
			for(int i=1;msRS.next();i++){
				personScopes.put(msRS.getString(2), Long.valueOf(msRS.getString(1)));
			}
			msRS.close();
			for(Hashtable user : users){
				String userName = (String) user.get("uid");
				if(userName==null){
					continue;
				}
				msSelectSql = "select username from sUserInfos where username=?";
				msPS=msConnection.prepareStatement(msSelectSql);
				msPS.setString(1,userName);
				msPS.executeQuery();
				msRS=msPS.executeQuery();
				String msName=null;
				if(msRS.next()){
					msName=msRS.getString(1);
				}
				msRS.close();
				StringBuilder msSql=new StringBuilder();
				if(msName==null){
					msSql.append("insert into sUserInfos");
					msSql.append(" (userName," +
							"password," +
							"departCode," +
							"costCenterCode," +
							"realName," +
							"enabled," +
							"isLock," +
							"email," +
							"employeeCode," +
							"itcode," +
							"titleCode," +
							"userType," +
							"personnelScope," +
							"costCenterName," +
							"postName," +
							"postCode," +
							"platform)");
					msSql.append(" values('"+userName+"','000000',"
							+(user.get("departmentNumber")==null?"0":user.get("departmentNumber"))+",'"
							+user.get("dcCostCenterCode")+"','"
							+user.get("cn")+"',1,0,'"
							+userName+"@digitalchina.com','"
							+user.get("employeeNumber")+"','"
							+userName.toUpperCase()+"',"
							+user.get("dcTitleCode")+","
							+userTypes.get(user.get("employeeType"))+","
							+personScopes.get(user.get("dcPersonalSubAreaCode"))+",'"
							+user.get("dcCostCenterName")+"','"
							+user.get("dcPosition")+"','"
							+user.get("title")+"',"
							+platForms.get(user.get("dcFlatCode"))+")");
				}else{
					msSql.append("update sUserInfos set ");
					msSql.append("userName='"+userName+"'," +
							"departCode=" +(user.get("departmentNumber")==null?"0":user.get("departmentNumber"))+"," +
							"costCenterCode='" +user.get("dcCostCenterCode")+"',"+
							"realName='" + user.get("cn") +"',"+
							"enabled=1," +
							"email='" +userName+"@digitalchina.com',"+
							"employeeCode='" + user.get("employeeNumber")+"',"+
							"itcode='" + user.get("uid").toString().toUpperCase()+"',"+
							"titleCode=" +user.get("dcTitleCode")+","+
							"userType=" +userTypes.get(user.get("employeeType"))+","+
							"personnelScope=" +personScopes.get(user.get("dcPersonalSubAreaCode"))+","+
							"costCenterName='" +user.get("dcCostCenterName")+"',"+
							"postName='" +user.get("dcPosition")+"',"+
							"postCode='" +user.get("title")+"',"+
							"platform="+platForms.get(user.get("dcFlatCode"))+
							" WHERE username='"+msName+"'");
				}
				//System.out.print(msSql);
				msStatement.executeUpdate(msSql.toString());
				msConnection.commit();
			}
		}catch (Exception e) {
			e.printStackTrace();
			try {msConnection.rollback();} catch (SQLException e1) {e.printStackTrace();}
			throw new DaoException(e.getMessage());
		}finally{
			if(msConnection!=null)try{msConnection.close();}catch(Exception e){e.printStackTrace();}
			if(msPS!=null)try {msPS.close();} catch (SQLException e) {e.printStackTrace();}
			if(msStatement!=null)try {msStatement.close();} catch (SQLException e) {e.printStackTrace();}
			System.out.println("========================更新人员信息数据完毕========================");
		}
		
	}

	/**
	 * 更新平台信息
	 * @Methods Name updatePlatform
	 * @Create In Jun 22, 2010 By lee
	 * @param personnelScopeMap void
	 */
	public void updatePlatform(Map<String,String> platformMap){
		Connection msConnection=null;
		Statement msStatement=null;
		PreparedStatement msPS=null;
		Set<String> platformKey = platformMap.keySet();
		String msSelectSql="select name from sys_PlatForm where keyword =?";
		try{
			msConnection=getConnection();
			msConnection.setAutoCommit(false);
			msStatement=msConnection.createStatement();
			msPS=msConnection.prepareStatement(msSelectSql);
			for(String keyword : platformKey){
				String name = platformMap.get(keyword);
				msPS.setString(1,keyword);
				ResultSet msRS=msPS.executeQuery();
				String msName=null;
				if(msRS.next()){
					msName=msRS.getString(1);
				}
				msRS.close();
				StringBuilder msSql=new StringBuilder();
				if(msName==null){
					msSql.append("insert into sys_PlatForm");
					msSql.append(" (keyword,name)");
					msSql.append(" values('"+keyword+"','"+name+"')");
					msStatement.executeUpdate(msSql.toString());
					msConnection.commit();
				}
				else if(!msName.equals(name)){
					msSql.append("update sys_PlatForm");
					msSql.append(" set name ='"+name+"'");
					msSql.append(" where keyword ='"+keyword+"'");
					msStatement.executeUpdate(msSql.toString());
					msConnection.commit();
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			try {msConnection.rollback();} catch (SQLException e1) {e.printStackTrace();}
			throw new DaoException(e.getMessage());
		}finally{
			if(msConnection!=null)try{msConnection.close();}catch(Exception e){e.printStackTrace();}
			if(msPS!=null)try {msPS.close();} catch (SQLException e) {e.printStackTrace();}
			if(msStatement!=null)try {msStatement.close();} catch (SQLException e) {e.printStackTrace();}
			System.out.println("========================更新平台表数据完毕========================");
		}
	}
	/**
	 * 更新人事子范围表
	 * @Methods Name updatePersonnelScope
	 * @Create In Jun 22, 2010 By lee
	 * @param personnelScopeMap void
	 */
	public void updatePersonnelScope(Map<String,String> personnelScopeMap){
		Connection msConnection=null;
		Statement msStatement=null;
		PreparedStatement msPS=null;
		Set<String> personnelScopeKey = personnelScopeMap.keySet();
		String msSelectSql="select name from sys_PersonnelScope where personnelScopeCode =?";
		try{
			msConnection=getConnection();
			msConnection.setAutoCommit(false);
			msStatement=msConnection.createStatement();
			msPS=msConnection.prepareStatement(msSelectSql);
			for(String psCode : personnelScopeKey){
				String psName = personnelScopeMap.get(psCode);
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
					msSql.append(" values('"+psCode+"','"+psName+"')");
					msStatement.executeUpdate(msSql.toString());
					msConnection.commit();
				}
				else if(!msName.equals(personnelScopeMap.get(psCode))){
					msSql.append("update sys_PersonnelScope");
					msSql.append(" set name ='"+psName+"'");
					msSql.append(" where personnelScopeCode ='"+psCode+"'");
					msStatement.executeUpdate(msSql.toString());
					msConnection.commit();
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			try {msConnection.rollback();} catch (SQLException e1) {e.printStackTrace();}
			throw new DaoException(e.getMessage());
		}finally{
			if(msConnection!=null)try{msConnection.close();}catch(Exception e){e.printStackTrace();}
			if(msPS!=null)try {msPS.close();} catch (SQLException e) {e.printStackTrace();}
			if(msStatement!=null)try {msStatement.close();} catch (SQLException e) {e.printStackTrace();}
			System.out.println("========================更新人事子范围数据完毕========================");
		}
	}
	/**
	 * 更新人员类型码表
	 * @Methods Name updateUserType
	 * @Create In Jun 22, 2010 By lee
	 * @param userTypes void
	 */
	public void updateUserType(Set<String> userTypes){
		Map<String,String> userTypesMap = new HashMap();
		for(String userTypeStr : userTypes){
			String[] typestr = userTypeStr.split("-");
			userTypesMap.put(typestr[0], typestr[1]);
		}
		Set<String> userTypesCode = userTypesMap.keySet();
		Connection msConnection=null;
		Statement msStatement=null;
		PreparedStatement msPS=null;
		String msSelectSql="select name from sys_UserType where code =?";
		try{
			msConnection=getConnection();
			msConnection.setAutoCommit(false);
			msStatement=msConnection.createStatement();
			msPS=msConnection.prepareStatement(msSelectSql);
			for(String userTypeCode : userTypesCode){
				String userTypeName = userTypesMap.get(userTypeCode);
				msPS.setString(1,userTypeCode);
				ResultSet msRS=msPS.executeQuery();
				String msName=null;
				if(msRS.next()){
					msName=msRS.getString(1);
				}
				msRS.close();
				StringBuilder msSql=new StringBuilder();
				if(msName==null){
					msSql.append("insert into sys_UserType");
					msSql.append(" (code,name)");
					msSql.append(" values('"+userTypeCode+"','"+userTypeName+"')");
					msStatement.executeUpdate(msSql.toString());
					msConnection.commit();
				}
				else if(!msName.equals(userTypeName)){
					msSql.append("update sys_UserType");
					msSql.append(" set name ='"+userTypeName+"'");
					msSql.append(" where code ='"+userTypeCode+"'");
					msStatement.executeUpdate(msSql.toString());
					msConnection.commit();
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			try {msConnection.rollback();} catch (SQLException e1) {e.printStackTrace();}
			throw new DaoException(e.getMessage());
		}finally{
			if(msConnection!=null)try{msConnection.close();}catch(Exception e){e.printStackTrace();}
			if(msPS!=null)try {msPS.close();} catch (SQLException e) {e.printStackTrace();}
			if(msStatement!=null)try {msStatement.close();} catch (SQLException e) {e.printStackTrace();}
			System.out.println("========================更新人员类型表数据完毕========================");
		}
	}

	public void updateDeptment(List<Hashtable> deptments) {
		Connection msConnection=null;
		Statement msStatement=null;
		PreparedStatement msPS=null;
		String msSelectSql=null;
		try{
			msConnection=getConnection();
			msConnection.setAutoCommit(false);
			msStatement = msConnection.createStatement();
			msSelectSql = "update Department set EndDate='2000/1/1'";
			msConnection.prepareStatement(msSelectSql).execute();
			
			for(Hashtable dept : deptments){
				String dpetCode = (String) dept.get("dpetCode");
				if(dpetCode==null){
					continue;
				}
				msSelectSql = "select DepartCode from Department where DepartCode=?";
				msPS=msConnection.prepareStatement(msSelectSql);
				msPS.setString(1,dpetCode);
				ResultSet msRS = msPS.executeQuery();
				msRS=msPS.executeQuery();
				String msCode=null;
				if(msRS.next()){
					msCode=msRS.getString(1);
				}
				msRS.close();
				StringBuilder msSql=new StringBuilder();
				if(msCode==null){
					msSql.append("insert into Department");
					msSql.append(" (DepartCode,DepartName,ParentCode,EndDate)");
					msSql.append(" values("+dept.get("dpetCode")+",'"+dept.get("deptName")+"',"+dept.get("parentCode")+",'9999/12/31')");
				}else{
					msSql.append("update Department set ");
					msSql.append("DepartName='" +dept.get("deptName")+"'," +
							"ParentCode=" +dept.get("parentCode")+"," +
							"EndDate='9999/12/31'"+
							" WHERE DepartCode="+dpetCode+"");
				}
				//System.out.println(msSql);
				msStatement.executeUpdate(msSql.toString());
				msConnection.commit();
			}
		}catch (Exception e) {
			e.printStackTrace();
			try {msConnection.rollback();} catch (SQLException e1) {e.printStackTrace();}
			throw new DaoException(e.getMessage());
		}finally{
			if(msConnection!=null)try{msConnection.close();}catch(Exception e){e.printStackTrace();}
			if(msPS!=null)try {msPS.close();} catch (SQLException e) {e.printStackTrace();}
			if(msStatement!=null)try {msStatement.close();} catch (SQLException e) {e.printStackTrace();}
			System.out.println("========================更新部门信息数据完毕========================");
		}
		
	}
}
