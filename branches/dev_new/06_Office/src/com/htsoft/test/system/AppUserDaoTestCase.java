/*    */ package com.htsoft.test.system;
/*    */ 
/*    */ import com.htsoft.oa.dao.system.AppUserDao;
/*    */ import com.htsoft.oa.dao.system.DepartmentDao;
/*    */ import com.htsoft.oa.model.system.AppRole;
/*    */ import com.htsoft.oa.model.system.AppUser;
/*    */ import com.htsoft.oa.model.system.Department;
/*    */ import com.htsoft.test.BaseTestCase;
/*    */ import java.io.PrintStream;
/*    */ import java.util.Date;
/*    */ import java.util.Iterator;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class AppUserDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private AppUserDao appUserDao;
/*    */ 
/*    */   @Resource
/*    */   private DepartmentDao departmentDao;
/*    */ 
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 29 */     AppUser appUser = new AppUser();
/*    */ 
/* 31 */     appUser = (AppUser)this.appUserDao.get(Long.valueOf(4L));
/* 32 */     System.out.println(appUser.getAddress());
/* 33 */     Set set = appUser.getRoles();
/* 34 */     Iterator it = set.iterator();
/*    */ 
/* 36 */     AppRole role = (AppRole)it.next();
/* 37 */     System.out.println(role.getRoleName());
/*    */   }
/*    */ 
/*    */   public void addDep()
/*    */   {
/* 44 */     Department dep = new Department();
/* 45 */     dep.setDepName("Root Dep");
/* 46 */     dep.setDepLevel(Integer.valueOf(1));
/*    */ 
/* 48 */     this.departmentDao.save(dep);
/*    */   }
/*    */   @Test
/*    */   public void bacthAdd() {
/* 53 */     Department dep = (Department)this.departmentDao.get(Long.valueOf(1L));
/* 54 */     for (int i = 101; i < 102; i++) {
/* 55 */       AppUser au = new AppUser();
/*    */ 
/* 57 */       au.setTitle(Short.valueOf((short) 1));
/* 58 */       au.setUsername("user" + i);
/* 59 */       au.setPassword("1");
/* 60 */       au.setFullname("李海" + i);
/* 61 */       au.setAddress("testAddress");
/* 62 */       au.setEducation("test");
/* 63 */       au.setEmail("user" + i + "@htsoft.com");
/* 64 */       au.setAccessionTime(new Date());
/* 65 */       au.setPhoto("photo");
/* 66 */       au.setZip("00003");
/* 67 */       au.setStatus(Short.valueOf((short) 1));
/* 68 */       au.setFax("020-003034034");
/* 69 */       au.setPosition("UserManager");
/*    */ 
/* 72 */       this.appUserDao.save(au);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.test.system.AppUserDaoTestCase
 * JD-Core Version:    0.6.0
 */