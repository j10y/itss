package net.shopin.alipay.dao.impl;

import java.util.List;

import net.shopin.alipay.dao.BaseDao;
import net.shopin.alipay.dao.impl.GenericDaoImpl;

import org.springframework.dao.DataAccessException;

/**
 * 
 * @author csx
 * 
 * @param <T>
 *            ��@SuppressWarnings("unchecked") �����࣬��������Ϊlong���͡�����ֱ�Ӽ̳и��࣬������Ϊ�������ͣ�
 *            ��Ҫֱ�Ӽ̳�GenericDaoImpl
 */
@SuppressWarnings("unchecked")
public abstract class BaseDaoImpl<T> extends GenericDaoImpl<T, Long> implements
		BaseDao<T> {

	public BaseDaoImpl(Class persistType) {
		super(persistType);
	}

	public List findDataList(String sql) {
		List list = this.jdbcTemplate.queryForList(sql);
		return list;
	}

	public boolean removeDatabySql(String sql) {
		try {
			this.jdbcTemplate.execute(sql);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean updatebySql(String sql) {
		// TODO Auto-generated method stub
		try {
			this.jdbcTemplate.update(sql);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
