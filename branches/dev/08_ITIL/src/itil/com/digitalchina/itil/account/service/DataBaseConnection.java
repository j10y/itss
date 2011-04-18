package com.digitalchina.itil.account.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.exception.DaoException;
import com.digitalchina.info.framework.util.PropertiesUtil;

public class DataBaseConnection {
	private static Connection conforOracle=null;
	/**
	 * ��ȡOracle����
	 * 
	 * @return Connection
	 */
	public static final Connection getOracleConnection(){
		
	      String url = PropertiesUtil.getProperties("jdbc.dws.url","jdbc:oracle:thin:@10.1.180.33:1521:DWS");
	      String user = PropertiesUtil.getProperties("jdbc.dws.username","FINANCE");
	      String password = PropertiesUtil.getProperties("jdbc.dws.password","FINANCE1234"); 
		  try
		    {
			  Class.forName("oracle.jdbc.driver.OracleDriver");
		    }
		   catch(Exception e)
		   {
			  System.out.println("Error001:Oracle����û���ҵ�..");
		   }
		   try {
			   // System.out.println("�û�����"+user+"--���룺"+password+"--url:"+url);
			   conforOracle =DriverManager.getConnection(url,user,password);
			  System.out.println("Oracle���ӳɹ�");
		   }catch (SQLException e) {
			  System.out.println("Error002:�û������������,Ҳ������url����,����DWS���ݿ�ʧ�ܣ�");
		   }
		   return conforOracle;
		   }
	/**
	 * �ر�����
	 * 
	 * @param resultset
	 * @param obj
	 * @param connection
	 */
	public static final void closeConnection(ResultSet resultset, Object obj,Connection connection) {
		   if (resultset != null)
		    try {
		     resultset.close();
		     resultset = null;
		    } catch (SQLException sqlexception) {
		    }
		   if (obj != null && (obj instanceof Statement))
		    try {
		     ((Statement) obj).close();
		     obj = null;
		    } catch (SQLException sqlexception1) {
		    }
		   if (connection != null)
		    try {
		     connection.close();
		     connection = null;
		    } catch (SQLException sqlexception2) {
		    }
		  }
	public static final Connection getMySqlConnection(){
	      String driverClassName = PropertiesUtil.getProperties("jdbc.www.driverClassName","com.mysql.jdbc.Driver");
	      String url = PropertiesUtil.getProperties("jdbc.www.url","jdbc:mysql://10.1.180.22:3306/proxymgmt");
	      String user = PropertiesUtil.getProperties("jdbc.www.username","admin");
	      String password = PropertiesUtil.getProperties("jdbc.www.password","its7888"); 
			Connection con = null;
			try {
				Class.forName(driverClassName);
				con = DriverManager.getConnection(url, user, password);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
			return con;
	}
	public static void main(String[] args){}
}
