/*    */ package com.htsoft.oa.dao.personal.impl;
/*    */ 
/*    */ import com.htsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.htsoft.oa.dao.personal.DutySystemDao;
/*    */ import com.htsoft.oa.model.personal.DutySystem;
/*    */ import java.sql.SQLException;
/*    */ import java.util.List;
/*    */ import org.hibernate.HibernateException;
/*    */ import org.hibernate.Query;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.orm.hibernate3.HibernateCallback;
/*    */ import org.springframework.orm.hibernate3.HibernateTemplate;
/*    */ 
/*    */ public class DutySystemDaoImpl extends BaseDaoImpl<DutySystem>
/*    */   implements DutySystemDao
/*    */ {
/*    */   public DutySystemDaoImpl()
/*    */   {
/* 21 */     super(DutySystem.class);
/*    */   }
/*    */ 
/*    */   public void updateForNotDefult()
/*    */   {
/* 28 */     String hql = "update DutySystem ds set ds.isDefault=? where ds.isDefault=?";
/* 29 */     getHibernateTemplate().execute(new HibernateCallback()
/*    */     {
/*    */       public Object doInHibernate(Session session) throws HibernateException, SQLException {
/* 32 */         Query query = session.createQuery("update DutySystem ds set ds.isDefault=? where ds.isDefault=?");
/* 33 */         query.setShort(0, DutySystem.NOT_DEFAULT.shortValue());
/* 34 */         query.setShort(1, DutySystem.DEFAULT.shortValue());
/* 35 */         query.executeUpdate();
/* 36 */         return null;
/*    */       }
/*    */     });
/*    */   }
/*    */ 
/*    */   public List<DutySystem> getDefaultDutySystem()
/*    */   {
/* 45 */     String hql = "from DutySystem ds where ds.isDefault=? ";
/* 46 */     return findByHql(hql, new Object[] { DutySystem.DEFAULT });
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.dao.personal.impl.DutySystemDaoImpl
 * JD-Core Version:    0.6.0
 */