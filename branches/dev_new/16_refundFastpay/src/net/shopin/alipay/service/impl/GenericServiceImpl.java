package net.shopin.alipay.service.impl;



import java.io.Serializable;

import net.shopin.alipay.dao.GenericDao;
import net.shopin.alipay.service.GenericService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GenericServiceImpl<T, PK extends Serializable> implements
		GenericService<T, PK> {
	/* 19 */protected Log logger = LogFactory.getLog(GenericServiceImpl.class);

	/* 21 */protected GenericDao<T, Serializable> dao = null;

	public void setDao(GenericDao dao) {
		/* 24 */this.dao = dao;
	}

	public GenericServiceImpl(GenericDao dao) {
		/* 28 */this.dao = dao;
	}
}
