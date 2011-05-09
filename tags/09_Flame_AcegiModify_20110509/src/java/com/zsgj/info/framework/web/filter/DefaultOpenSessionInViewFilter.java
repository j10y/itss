package com.zsgj.info.framework.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.support.OpenSessionInViewFilter;

public class DefaultOpenSessionInViewFilter extends OpenSessionInViewFilter {
	
	private final Log logger = LogFactory.getLog(getClass());
	
	protected void closeSession(Session session, SessionFactory sessionFactory) {
		//System.out.println("------------------------- closeSession------------------------- ");
		super.closeSession(session, sessionFactory);
	}

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		//System.out.println("### doFilterInternal ###");
		super.doFilterInternal(request, response, filterChain);
	}

	protected FlushMode getFlushMode() {
		//System.out.println("----- getFlushMode: "+super.getFlushMode()+" -----");
		return super.getFlushMode();
	}

	protected Session getSession(SessionFactory sessionFactory) throws DataAccessResourceFailureException {
		Session session = super.getSession(sessionFactory);
		//System.out.println("-------------------------  getSession ------------------------- ");
		return session;
	}

	protected String getSessionFactoryBeanName() {
		//System.out.println("----- getSessionFactoryBeanName: "+super.getSessionFactoryBeanName()+" -----");
		return super.getSessionFactoryBeanName();
	}

	protected boolean isSingleSession() {
		// TODO Auto-generated method stub
		return super.isSingleSession();
	}

	protected SessionFactory lookupSessionFactory() {
		// TODO Auto-generated method stub
		return super.lookupSessionFactory();
	}

	protected SessionFactory lookupSessionFactory(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return super.lookupSessionFactory(request);
	}

	public void setFlushMode(FlushMode flushMode) {
		// TODO Auto-generated method stub
		super.setFlushMode(flushMode);
	}

	public void setSessionFactoryBeanName(String sessionFactoryBeanName) {
		// TODO Auto-generated method stub
		super.setSessionFactoryBeanName(sessionFactoryBeanName);
	}

	public void setSingleSession(boolean singleSession) {
		// TODO Auto-generated method stub
		super.setSingleSession(singleSession);
	}

	protected String getAlreadyFilteredAttributeName() {
		// TODO Auto-generated method stub
		return super.getAlreadyFilteredAttributeName();
	}

	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		// TODO Auto-generated method stub
		return super.shouldNotFilter(request);
	}

	public void afterPropertiesSet() throws ServletException {
		System.out.println("----- afterPropertiesSet -----");
		super.afterPropertiesSet();
	}

	public void destroy() {
		System.out.println("-------------------------  destroy PmcOpenSessionInViewFilter -------------------------");
		super.destroy();
	}

	protected void initBeanWrapper(BeanWrapper bw) throws BeansException {
		System.out.println("BeanWrapper³õÊ¼»¯Íê±Ï...");
		super.initBeanWrapper(bw);
	}

	protected void initFilterBean() throws ServletException {
		super.initFilterBean();
	}

}
