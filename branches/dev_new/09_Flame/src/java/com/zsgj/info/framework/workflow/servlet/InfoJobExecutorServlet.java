package com.zsgj.info.framework.workflow.servlet;

import javax.servlet.ServletException;

import org.hibernate.SessionFactory;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.job.executor.JobExecutorServlet;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.workflow.base.JbpmConfig;

public class InfoJobExecutorServlet extends JobExecutorServlet {

	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;
	private static JbpmConfiguration jbpmConfiguration; 
	
	static{
		//此处可修改数据源之外的其他配置项，放进特定的配置文件里，参考jbpm.cfg.xml
		String resource = "jbpm.cfg.xml";
		jbpmConfiguration = JbpmConfiguration.getInstance(resource);
	}
	
	/**
	 * 设置spring的数据源
	 */
	public void init() throws ServletException { 		
		JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
		String sfname = JbpmConfig.SPRING_SESSION_FACTORY_NAME;
		SessionFactory springSessionFactory = (SessionFactory)ContextHolder.getBean(sfname);
		jbpmContext.setSessionFactory(springSessionFactory);
		jbpmConfiguration.startJobExecutor(); 
	} 
}
