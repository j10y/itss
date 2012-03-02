package net.shopin.alipay.util;


import java.sql.*;

public class JDBCUtil {

    static{
        String oracleDriverClassName = "oracle.jdbc.driver.OracleDriver";
        String sqlServerDriverClassName = "net.sourceforge.jtds.jdbc.Driver";
        String mySqlDriverClassName = "com.mysql.jdbc.Driver";

        try {
            Class.forName(oracleDriverClassName);
//            Class.forName(mySqlDriverClassName);
//            Class.forName(sqlServerDriverClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getUrl(String ip, String port, String sid, int dbType){
        
        String url = "";

        switch(dbType){
            case 0 : url= "jdbc:oracle:thin:@" + ip + ":" + port + ":" + sid ; break;
            case 1 : url= "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + sid ; break;
            case 2 : url= "jdbc:mysql://" + ip + ":" + port + "/" + sid ; break;
            case 3 : url= "" ; break;
            default : url= "" ; break;

        }

        return url;
    }

    public static Connection getConnection(String url, String username, String pwd){
	   Connection con = null;
	   try{
          con = DriverManager.getConnection(
			    url,username,pwd);
	   }catch(Exception e){
	      return null;
	   }
	   return con;
	}

    public static void close(ResultSet rs, Statement stmt,Connection con){
	      try{
		     if(rs!=null) rs.close();
		  }catch(Exception ex){
		     ex.printStackTrace();
		  }
		  try{
		     if(stmt!=null)stmt.close();
		  }catch(Exception ex){
		     ex.printStackTrace();
		  }
	      try{
		     if(con!=null)con.close();
		  }catch(Exception ex){
		     ex.printStackTrace();
		  }
	}

    public static void printRs(ResultSet rs){

        if(rs==null) return;
        ResultSetMetaData meta = null;
        try {
            meta = rs.getMetaData();
            int cols = meta.getColumnCount();
            StringBuffer sb = new StringBuffer();
            while(rs.next()){
                for(int i=1;i<=cols;i++){
                    sb.append(meta.getColumnName(i)+"=");
                    sb.append(rs.getString(i)+"  ");
                }
                sb.append("\n");
            }
            System.out.println(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
