/*    */ package com.xpsoft.oa.dao.system.impl;
/*    */ 
/*    */ import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.system.FunUrlDao;
import com.xpsoft.oa.model.system.FunUrl;
/*    */ 
/*    */ public class FunUrlDaoImpl extends BaseDaoImpl<FunUrl>
/*    */   implements FunUrlDao
/*    */ {
/*    */   public FunUrlDaoImpl()
/*    */   {
/* 13 */     super(FunUrl.class);
/*    */   }
/*    */ 
/*    */   public FunUrl getByPathFunId(String path, Long funId)
/*    */   {
/* 21 */     String hql = "from FunUrl fu where fu.urlPath=? and fu.appFunction.functionId=? ";
/* 22 */     return (FunUrl)findUnique(hql, new Object[] { path, funId });
/*    */   }

			public Set<String> getAdminDataSource() {
				final Set<String> urlSet = new TreeSet();
				getHibernateTemplate().execute(new HibernateCallback() {
					public Object doInHibernate(Session session)throws HibernateException, SQLException {
						/* 48 */String hql = "from FunUrl";
						/* 49 */Query query = session.createQuery(hql);
						/* 50 */List<FunUrl> fuList = query.list();
			
						/* 52 */for (FunUrl fu : fuList) {
									urlSet.add(fu.getUrlPath());
								}
						return null;
					}
				});
				return urlSet;
			}


/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.system.impl.FunUrlDaoImpl
 * JD-Core Version:    0.6.0
 */