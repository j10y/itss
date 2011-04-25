package com.digitalchina.info.framework.workflow.base;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.db.GraphSession;
import org.jbpm.db.TaskMgmtSession;
import org.jbpm.identity.hibernate.IdentitySession;

import com.digitalchina.info.framework.context.ContextHolder;

public class JbpmContextFactory {
//	static Log log = LogFactory.getLog(JbpmContextFactory.class);
//	static JbpmContext jbpmContext = null; 
	private static IdentitySession identitySession;
	public static final ThreadLocal<JbpmContext> jbpmContext_ThreadLocal = new ThreadLocal<JbpmContext>();
	public static SessionFactory sessionFactory = (SessionFactory)ContextHolder.getBean("pmcSessionFactory");
	public static Session springSession = null;
	
	//与框架共用sessionFactory
	public static JbpmContext getJbpmContext() {			
		JbpmContext jbpmContext = (JbpmContext)jbpmContext_ThreadLocal.get();
		if (jbpmContext == null||!jbpmContext.getSession().isOpen()){
			jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
			String sfname = JbpmConfig.SPRING_SESSION_FACTORY_NAME;
			SessionFactory springSessionFactory = (SessionFactory)ContextHolder.getBean(sfname);
			jbpmContext.setSessionFactory(springSessionFactory);
//			jbpmContext.setSession(springSessionFactory.openSession());
//			jbpmContext.setActorId(JbpmUtils.getLoginActorId());
			//System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
			jbpmContext_ThreadLocal.set(jbpmContext);
		}
		return jbpmContext;
	}

	
	//保存数据并关闭上下文
	public static void closeJbpmContext(){
		JbpmContext jbpmContext = (JbpmContext)jbpmContext_ThreadLocal.get();		
		if (jbpmContext != null){
			if(jbpmContext.getSession()!=null&&jbpmContext.getSession().isOpen()) {	
				//System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
//				jbpmContext.getSession().clear();
//				jbpmContext.getSession().disconnect();
				//jbpmContext.getSession().close();							
			}
			jbpmContext.close();
			//String state = jbpmContext.getSessionFactory().getCurrentSession().isOpen()+"";
			//System.out.println("after Context close: session is open: "+state);
		}  
		jbpmContext = null;
		jbpmContext_ThreadLocal.set(null);		
	}
	
	
	//用特定用户Id打开JbpmContext，考虑到安全认证问题，应该采用这种方式
	public static JbpmContext getJbpmContext(String actorId) {			
		JbpmContext jbpmContext = getJbpmContext();
		jbpmContext.setActorId(actorId);
		return jbpmContext;
	}
	
	public static GraphSession getGraphSession()
	{
		return getJbpmContext().getGraphSession();
	}

	public static TaskMgmtSession getTaskMgmtSession()
	{
		return getJbpmContext().getTaskMgmtSession();
	}

	public static IdentitySession getIdentitySession()
	{
		if (identitySession == null)
			identitySession = new IdentitySession(getJbpmContext().getSession());
		return identitySession;
	}
	
	public static void main(String[] argv) {
		getJbpmContext();
		closeJbpmContext();
	}
	
	
//	//与框架共用sessionFactory
//	public static JbpmContext getJbpmContext() {			
//		JbpmContext jbpmContext = (JbpmContext)jbpmContext_ThreadLocal.get();
//		if (jbpmContext == null||!jbpmContext.getSession().isOpen()){
//			jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
//			String sfname = JbpmConfig.SPRING_SESSION_FACTORY_NAME;
//			SessionFactory springSessionFactory = (SessionFactory)ContextHolder.getBean(sfname);
////			Session session = springSessionFactory.getCurrentSession();
////			//if(session==null||!session.isOpen()) {
////				session = springSessionFactory.openSession();
////			//}
////			jbpmContext.setSession(session);
//		
////			springSession = springSessionFactory.openSession();
////			System.out.println("begin: springSession is open:"+springSession.isOpen());
//			
//			jbpmContext.setSessionFactory(springSessionFactory);
//			jbpmContext.setActorId(JbpmUtils.getLoginActorId());
//			
//			jbpmContext_ThreadLocal.set(jbpmContext);
//		} 
//		return jbpmContext;
//	}
//	
//	
//	//保存数据并关闭上下文
//	public static void closeJbpmContext(){
//		JbpmContext jbpmContext = (JbpmContext)jbpmContext_ThreadLocal.get();		
//		if (jbpmContext != null){
//			jbpmContext.close();
////			String state = jbpmContext.getSession().isOpen()+"";
////			System.out.println("session state is open:["+state +"] after JbpmContext closed.");
//		} 
//		jbpmContext = null;
//		jbpmContext_ThreadLocal.set(null);
//		
////		System.out.println("end: springSession is open:"+springSession.isOpen());
//	}
}
