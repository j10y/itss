/*    */ package com.htsoft.core.service.impl;
/*    */ 
/*    */ import com.htsoft.core.command.QueryFilter;
/*    */ import com.htsoft.core.dao.GenericDao;
/*    */ import com.htsoft.core.service.GenericService;
/*    */ import com.htsoft.core.web.paging.PagingBean;
/*    */ import java.io.Serializable;
/*    */ import java.util.List;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ 
/*    */ public class GenericServiceImpl<T, PK extends Serializable>
/*    */   implements GenericService<T, PK>
/*    */ {
/* 19 */   protected Log logger = LogFactory.getLog(GenericServiceImpl.class);
/*    */ 
/* 21 */   protected GenericDao<T, Serializable> dao = null;
/*    */ 
/*    */   public void setDao(GenericDao dao) {
/* 24 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public GenericServiceImpl(GenericDao dao) {
/* 28 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public T get(PK id) {
/* 32 */     return this.dao.get(id);
/*    */   }
/*    */ 
/*    */   public T save(T entity) {
/* 36 */     return this.dao.save(entity);
/*    */   }
/*    */ 
/*    */   public T merge(T entity) {
/* 40 */     return this.dao.merge(entity);
/*    */   }
/*    */ 
/*    */   public void evict(T entity) {
/* 44 */     this.dao.evict(entity);
/*    */   }
/*    */ 
/*    */   public List<T> getAll() {
/* 48 */     return this.dao.getAll();
/*    */   }
/*    */ 
/*    */   public List<T> getAll(PagingBean pb) {
/* 52 */     return this.dao.getAll(pb);
/*    */   }
/*    */ 
/*    */   public List<T> getAll(QueryFilter filter) {
/* 56 */     return this.dao.getAll(filter);
/*    */   }
/*    */ 
/*    */   public void remove(PK id) {
/* 60 */     this.dao.remove(id);
/*    */   }
/*    */ 
/*    */   public void remove(T entity) {
/* 64 */     this.dao.remove(entity);
/*    */   }
/*    */ 
/*    */   public void flush()
/*    */   {
/* 69 */     this.dao.flush();
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.core.service.impl.GenericServiceImpl
 * JD-Core Version:    0.6.0
 */