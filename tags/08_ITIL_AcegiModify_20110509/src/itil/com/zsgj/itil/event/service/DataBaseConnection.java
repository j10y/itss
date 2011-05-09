package com.zsgj.itil.event.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.zsgj.info.framework.util.PropertiesUtil;
public class DataBaseConnection {
	private static Connection conforOracle=null;
	private static Connection conforSQLServer=null;
	/**
	 * ��ȡOracle����
	 * @return Connection
	 */
	public static final Connection getOracleConnection(){
		
	      String url = PropertiesUtil.getProperties("jdbc.cc.url");//"jdbc:oracle:thin:@10.1.180.33:1521:DWS";
	      String user = PropertiesUtil.getProperties("jdbc.cc.username"); //"FINANCE";
	      String password = PropertiesUtil.getProperties("jdbc.cc.password"); //"FINANCE1234"; 
		  try
		    {
			  Class.forName("oracle.jdbc.driver.OracleDriver");
		    }
		   catch(Exception e)
		   {
			  System.out.println("Error001:Oracle����û���ҵ�..");
		   }
		   try {
			   conforOracle =DriverManager.getConnection(url,user,password);
			  System.out.println("Oracle���ӳɹ�");
		   }catch (SQLException e) {
			  System.out.println("Error002:�û������������,Ҳ������url����");
		   }
		   return conforOracle;
		   }
	/**
	 * ��ȡSqlServer����
	 * @return
	 */
	public static final Connection getSQLServerConnection(){
//		 String url="jdbc:microsoft:sqlserver://10.1.153.189:1433;databaseName=BizCadreSell";
		 String url="jdbc:microsoft:sqlserver://172.16.1.165:1433;databaseName=BizCadreSell";
	     String user="sa";
	     String password="its7888"; 
		  try
		    {
			  Class.forName( "com.microsoft.jdbc.sqlserver.SQLServerDriver" ); 
		    }
		   catch(Exception e)
		   {
			   System.out.println("Error001:SQLServer����û���ҵ�..");
		   }
		   try {
			   conforSQLServer =DriverManager.getConnection(url,user,password);
			System.out.println("SQLServer���ӳɹ�");
		   }catch (SQLException e) {
			System.out.println("Error002:�û������������,Ҳ������url����");
		   }
		   return conforSQLServer;
		   }
	/**
	 * �ر�����
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
	public static void main(String[] args){
		Connection con =null;
		con = getOracleConnection();
		closeConnection(null,null,con);
		getSQLServerConnection();
		closeConnection(null,null,con);
	}
}
