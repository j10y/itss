/*    */ package com.xpsoft.oa.service.system.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.system.DepartmentDao;
/*    */ import com.xpsoft.oa.model.system.Department;
/*    */ import com.xpsoft.oa.service.system.DepartmentService;
/*    */ import java.util.List;
/*    */ 
/*    */ public class DepartmentServiceImpl extends BaseServiceImpl<Department>
/*    */   implements DepartmentService
/*    */ {
/*    */   private DepartmentDao dao;
/*    */ 
/*    */   public DepartmentServiceImpl(DepartmentDao dao)
/*    */   {
/* 19 */     super(dao);
/* 20 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public List<Department> findByParentId(Long parentId) {
/* 24 */     return this.dao.findByParentId(parentId);
/*    */   }
/*    */ 
/*    */   public List<Department> findByDepName(String depName) {
/* 28 */     return this.dao.findByDepName(depName);
/*    */   }
/*    */ 
/*    */   public List<Department> findByPath(String path) {
/* 32 */     return this.dao.findByPath(path);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.system.impl.DepartmentServiceImpl
 * JD-Core Version:    0.6.0
 */