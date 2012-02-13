/*     */ package com.xpsoft.core.jbpm;
/*     */ 
/*     */ import com.xpsoft.core.util.AppUtil;
/*     */ import com.xpsoft.oa.model.system.AppUser;
/*     */ import com.xpsoft.oa.service.system.AppRoleService;
/*     */ import com.xpsoft.oa.service.system.AppUserService;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.jbpm.api.identity.Group;
/*     */ import org.jbpm.api.identity.User;
/*     */ import org.jbpm.pvm.internal.identity.spi.IdentitySession;
/*     */ 
/*     */ public class UserSession
/*     */   implements IdentitySession
/*     */ {
/*  22 */   private AppUserService appUserService = (AppUserService)AppUtil.getBean("appUserService");
/*     */ 
/*  24 */   private AppRoleService appRoleService = (AppRoleService)AppUtil.getBean("appRoleService");
/*     */ 
/*     */   public String createGroup(String arg0, String arg1, String arg2)
/*     */   {
/*  30 */     return null;
/*     */   }
/*     */ 
/*     */   public void createMembership(String arg0, String arg1, String arg2)
/*     */   {
/*     */   }
/*     */ 
/*     */   public String createUser(String arg0, String arg1, String arg2, String arg3)
/*     */   {
/*  42 */     return null;
/*     */   }
/*     */ 
/*     */   public void deleteGroup(String arg0)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void deleteMembership(String arg0, String arg1, String arg2)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void deleteUser(String arg0)
/*     */   {
/*     */   }
/*     */ 
/*     */   public Group findGroupById(String groupId)
/*     */   {
/*  67 */     return (Group)this.appRoleService.get(new Long(groupId));
/*     */   }
/*     */ 
/*     */   public List<Group> findGroupsByUser(String userId)
/*     */   {
/*  72 */     AppUser user = (AppUser)this.appUserService.get(new Long(userId));
/*  73 */     List list = new ArrayList();
/*  74 */     Iterator it = user.getRoles().iterator();
/*  75 */     while (it.hasNext()) {
/*  76 */       list.add((Group)it.next());
/*     */     }
/*  78 */     return list;
/*     */   }
/*     */ 
/*     */   public List<Group> findGroupsByUserAndGroupType(String userId, String groupType)
/*     */   {
/*  83 */     return findGroupsByUser(userId);
/*     */   }
/*     */ 
/*     */   public User findUserById(String userId)
/*     */   {
/*  88 */     return (User)this.appUserService.get(new Long(userId));
/*     */   }
/*     */ 
/*     */   public List<User> findUsers()
/*     */   {
/*  93 */     List<AppUser> userList = this.appUserService.getAll();
/*  94 */     List list = new ArrayList();
/*  95 */     for (User user : userList) {
/*  96 */       list.add(user);
/*     */     }
/*  98 */     return list;
/*     */   }
/*     */ 
/*     */   public List<User> findUsersByGroup(String groupId)
/*     */   {
/* 103 */     List<AppUser> userList = this.appUserService.findByRoleId(new Long(groupId));
/* 104 */     List list = new ArrayList();
/* 105 */     for (User user : userList) {
/* 106 */       list.add(user);
/*     */     }
/* 108 */     return list;
/*     */   }
/*     */ 
/*     */   public List<User> findUsersById(String[] userIds)
/*     */   {
/* 114 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.core.jbpm.UserSession
 * JD-Core Version:    0.6.0
 */