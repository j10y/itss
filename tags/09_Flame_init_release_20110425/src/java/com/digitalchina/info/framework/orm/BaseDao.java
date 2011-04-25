package com.digitalchina.info.framework.orm;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;

import com.digitalchina.info.framework.exception.DaoException;



/**
 * BaseDao即Dao基类，所有的Dao接口实现类在实现自身Dao接口的同时还需继承该Dao基类，以获得
 * 数据访问的能力。<p>通过组合复用一个DaoSupport来获得基于Hibernate数据服务的统一API，即
 * DaoSupport的接口则是提供持久化所需的基本方法。<p>
 * BasicDao可以使用hibernate或spring提供的方法，但是不是直接使用，而是通过调用DaoSupport的实现类
 * 来使用。
 * <p>
 * 使用BaseDao有如下优点：<br>
 * 1. 实现了服务层与hibernate/Spring的解耦。<br>
 * 2. 同时也简化编程接口，降低使用hibernate/Spring的难度。<br>
 * 3. 由于必须通过daoSupport中定义的方法访问数据库，增强了代码的可读性。<br>
 * <p>
 * 考虑系统老代码中有大量的JDBC访问数据库的代码，而新功能又需要访问部分来代码。为了访问原有模块中的功能，
 * 又需要使用spring管理的数据源，因此注入JdbcTemplate来使用spring对JDBC的封装。
 * <p> 
 * 如果要使用纯JDBC API访问数据库，可以使用getConnection()方法获得数据库连接，与基于Spring的JDBC编程不同之处是
 * 需要每次使用后关闭数据库连接。
 * @Class Name BaseDao
 * @Author xiaofeng
 * @Create In 2007-10-16

 */
public abstract class BaseDao {
    
    private DaoSupport daoSupport = null;
    private JdbcTemplate jdbcTemplate = null;

    /**
     * 获取daoSupport
     * 2007-10-25 By xiaofeng
     * @return DaoSupport
     */
    public DaoSupport getDaoSupport() {
        return daoSupport;
    }

   /**
    * 设置daoSupport
    * @Methods Name setDaoSupport
    * @Create In 2007-11-12 By peixf
    * @param daoSupport void
    */
    public void setDaoSupport(DaoSupport daoSupport) {
        this.daoSupport = daoSupport;
    }

   /**
   * 获取JdbcTemplate
   * @Methods Name getJdbcTemplate
   * @Create In 2007-11-12 By peixf
   * @return JdbcTemplate
   */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	/**
     * 设置JdbcTemplate
     * 2007-10-25 By xiaofeng
     * @return void
     */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	/**
	 * 获取数据库连接,给部分模块需要使用jdbc进行数据访问使用，此连接会源于的是架构管理的连接池，以保证最佳性能。
	 * 但注意使用后连接的断开。
	 */
	public Connection getConnection(){
		Connection conn = null;
		try {
			conn =  this.getJdbcTemplate().getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("BaseDao获取数据库连接发生异常");
		}
		return conn;
	}
	
	/**
	 * 关闭连接
	 * @Methods Name close
	 * @Create In 2007-12-17 By yang
	 * @param conn
	 * @return Connection
	 */
	public void close(Connection conn){
		if(conn==null) {
			return;
		}
		else {
			try {
				if(!conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DaoException("BaseDao关闭数据库连接发生异常");
			}
		}
	}

//	public Session getHbnSession(){
//		return null;
//	}
}
