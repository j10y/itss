/*    */ package com.xpsoft.oa.dao.system.impl;
/*    */ 
/*    */ import com.xpsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.xpsoft.core.web.paging.PagingBean;
/*    */ import com.xpsoft.oa.dao.system.DepartmentDao;
/*    */ import com.xpsoft.oa.model.system.Department;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class DepartmentDaoImpl extends BaseDaoImpl<Department>
/*    */   implements DepartmentDao
/*    */ {
/*    */   public DepartmentDaoImpl()
/*    */   {
/* 19 */     super(Department.class);
/*    */   }
/*    */ 
/*    */   public List<Department> findByParentId(Long parentId)
/*    */   {
/* 24 */     String hql = "from Department d where d.parentId=?";
/* 25 */     Object[] params = { parentId };
/* 26 */     return findByHql("from Department d where d.parentId=? order by d.sort", params);
/*    */   }
/*    */ 
/*    */   public List<Department> findByVo(Department department, PagingBean pb)
/*    */   {
/* 31 */     ArrayList paramList = new ArrayList();
/* 32 */     String hql = "from Department vo where 1=1";
/* 33 */     if (StringUtils.isNotEmpty(department.getDepName())) {
/* 34 */       hql = hql + " and vo.depName like ?";
/* 35 */       paramList.add("%" + department.getDepName() + "%");
/*    */     }
/* 37 */     if (StringUtils.isNotEmpty(department.getDepDesc())) {
/* 38 */       hql = hql + " and vo.depDesc=?";
/* 39 */       paramList.add("%" + department.getDepDesc() + "%");
/*    */     }
/* 41 */     return findByHql(hql, paramList.toArray(), pb);
/*    */   }
/*    */ 
/*    */   public List<Department> findByDepName(String depName)
/*    */   {
/* 46 */     String hql = "from Department vo where vo.depName=?";
/* 47 */     String[] param = { depName };
/* 48 */     return findByHql(hql, param);
/*    */   }
/*    */ 
/*    */   public List<Department> findByPath(String path)
/*    */   {
/* 53 */     String hql = "from Department vo where vo.path like ?";
/* 54 */     String[] param = { path + "%" };
/* 55 */     return findByHql(hql, param);
/*    */   }
/*    */ }