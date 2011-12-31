/*    */ package com.xpsoft.oa.dao.archive.impl;
/*    */ 
/*    */ import com.xpsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.xpsoft.oa.dao.archive.ArchRecUserDao;
/*    */ import com.xpsoft.oa.model.archive.ArchRecUser;
import com.xpsoft.oa.model.system.Department;

import java.util.ArrayList;
import java.util.List;
/*    */ 
/*    */ public class ArchRecUserDaoImpl extends BaseDaoImpl<ArchRecUser>
/*    */   implements ArchRecUserDao
/*    */ {
/*    */   public ArchRecUserDaoImpl()
/*    */   {
/* 15 */     super(ArchRecUser.class);
/*    */   }
/*    */ 
/*    */   public List findDepAll()
/*    */   {
			List list=new ArrayList();
/* 20 */    String hql = "select ar,dp from ArchRecUser ar right join ar.department dp where dp.parentId=0";
			List listp=findByHql(hql);
			for(int i = 0; i < listp.size(); i++){
				Department dep = (Department) ((Object[]) listp.get(i))[1];
				list.add(listp.get(i));
				List clist=findChildDep(dep.getDepId());
				if(clist!=null&&clist.size()>0){
					list.addAll(clist);
				}
			}	
/* 21 */     return list;
/*    */   }	
			private List findChildDep(Long depId){
				List list=new ArrayList();
				String hql = "select ar,dp from ArchRecUser ar right join ar.department dp where dp.parentId="+depId;
				List listp=findByHql(hql);
				for(int i = 0; i < listp.size(); i++){
					Department dep = (Department) ((Object[]) listp.get(i))[1];
					list.add(listp.get(i));
					List clist=findChildDep(dep.getDepId());
					if(clist!=null&&clist.size()>0){
						list.addAll(clist);
					}
				}	
				return list;
			}
/*    */   public ArchRecUser getByDepId(Long depId)
/*    */   {
/* 26 */     String hql = "from ArchRecUser ar where ar.department.depId =?";
/* 27 */     List list = findByHql(hql, new Object[] { depId });
/* 28 */     if (list.size() > 0) {
/* 29 */       return (ArchRecUser)list.get(0);
/*    */     }
/* 31 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.archive.impl.ArchRecUserDaoImpl
 * JD-Core Version:    0.6.0
 */