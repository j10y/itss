package com.xpsoft.oa.dao.customer.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.customer.CustomerDao;
import com.xpsoft.oa.model.customer.Customer;
import java.sql.SQLException;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class CustomerDaoImpl extends BaseDaoImpl<Customer> implements
		CustomerDao {
	public CustomerDaoImpl() {
		/* 20 */super(Customer.class);
	}

	public boolean checkCustomerNo(String customerNo) {
		final String hql = "select count(*) from Customer c where c.customerNo = ?";
		final String custNo = customerNo;
		Long count = (Long) getHibernateTemplate().execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						/* 31 */Query query = session
								.createQuery(hql);
						/* 32 */query.setString(0, custNo);
						/* 33 */return query.uniqueResult();
					}
				});
		/* 36 */return count.longValue() == 0L;
	}
}
