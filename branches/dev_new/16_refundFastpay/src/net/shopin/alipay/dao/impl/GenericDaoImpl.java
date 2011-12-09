package net.shopin.alipay.dao.impl;

import java.io.Serializable;

import net.shopin.alipay.dao.GenericDao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;




public abstract class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {
	protected Log logger = LogFactory.getLog(GenericDaoImpl.class);
	protected JdbcTemplate jdbcTemplate;
	protected Class persistType;
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public Class getPersistType() {
		return persistType;
	}
	public void setPersistType(Class persistType) {
		this.persistType = persistType;
	}
	public GenericDaoImpl(Class persistType) {
		super();
		this.persistType = persistType;
	}
	
}