package com.digitalchina.info.framework.orm;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;

import com.digitalchina.info.framework.exception.DaoException;



/**
 * BaseDao��Dao���࣬���е�Dao�ӿ�ʵ������ʵ������Dao�ӿڵ�ͬʱ����̳и�Dao���࣬�Ի��
 * ���ݷ��ʵ�������<p>ͨ����ϸ���һ��DaoSupport����û���Hibernate���ݷ����ͳһAPI����
 * DaoSupport�Ľӿ������ṩ�־û�����Ļ���������<p>
 * BasicDao����ʹ��hibernate��spring�ṩ�ķ��������ǲ���ֱ��ʹ�ã�����ͨ������DaoSupport��ʵ����
 * ��ʹ�á�
 * <p>
 * ʹ��BaseDao�������ŵ㣺<br>
 * 1. ʵ���˷������hibernate/Spring�Ľ��<br>
 * 2. ͬʱҲ�򻯱�̽ӿڣ�����ʹ��hibernate/Spring���Ѷȡ�<br>
 * 3. ���ڱ���ͨ��daoSupport�ж���ķ����������ݿ⣬��ǿ�˴���Ŀɶ��ԡ�<br>
 * <p>
 * ����ϵͳ�ϴ������д�����JDBC�������ݿ�Ĵ��룬���¹�������Ҫ���ʲ��������롣Ϊ�˷���ԭ��ģ���еĹ��ܣ�
 * ����Ҫʹ��spring���������Դ�����ע��JdbcTemplate��ʹ��spring��JDBC�ķ�װ��
 * <p> 
 * ���Ҫʹ�ô�JDBC API�������ݿ⣬����ʹ��getConnection()����������ݿ����ӣ������Spring��JDBC��̲�֮ͬ����
 * ��Ҫÿ��ʹ�ú�ر����ݿ����ӡ�
 * @Class Name BaseDao
 * @Author xiaofeng
 * @Create In 2007-10-16

 */
public abstract class BaseDao {
    
    private DaoSupport daoSupport = null;
    private JdbcTemplate jdbcTemplate = null;

    /**
     * ��ȡdaoSupport
     * 2007-10-25 By xiaofeng
     * @return DaoSupport
     */
    public DaoSupport getDaoSupport() {
        return daoSupport;
    }

   /**
    * ����daoSupport
    * @Methods Name setDaoSupport
    * @Create In 2007-11-12 By peixf
    * @param daoSupport void
    */
    public void setDaoSupport(DaoSupport daoSupport) {
        this.daoSupport = daoSupport;
    }

   /**
   * ��ȡJdbcTemplate
   * @Methods Name getJdbcTemplate
   * @Create In 2007-11-12 By peixf
   * @return JdbcTemplate
   */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	/**
     * ����JdbcTemplate
     * 2007-10-25 By xiaofeng
     * @return void
     */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	/**
	 * ��ȡ���ݿ�����,������ģ����Ҫʹ��jdbc�������ݷ���ʹ�ã������ӻ�Դ�ڵ��Ǽܹ���������ӳأ��Ա�֤������ܡ�
	 * ��ע��ʹ�ú����ӵĶϿ���
	 */
	public Connection getConnection(){
		Connection conn = null;
		try {
			conn =  this.getJdbcTemplate().getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("BaseDao��ȡ���ݿ����ӷ����쳣");
		}
		return conn;
	}
	
	/**
	 * �ر�����
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
				throw new DaoException("BaseDao�ر����ݿ����ӷ����쳣");
			}
		}
	}

//	public Session getHbnSession(){
//		return null;
//	}
}
