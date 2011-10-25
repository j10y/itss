/*     */ package com.xpsoft.oa.dao.document.impl;
/*     */ 
/*     */ import com.xpsoft.core.dao.impl.BaseDaoImpl;
/*     */ import com.xpsoft.core.web.paging.PagingBean;
/*     */ import com.xpsoft.oa.dao.document.DocumentDao;
/*     */ import com.xpsoft.oa.model.document.Document;
/*     */ import com.xpsoft.oa.model.system.AppRole;
/*     */ import com.xpsoft.oa.model.system.AppUser;
/*     */ import com.xpsoft.oa.model.system.Department;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class DocumentDaoImpl extends BaseDaoImpl<Document>
/*     */   implements DocumentDao
/*     */ {
/*     */   public DocumentDaoImpl()
/*     */   {
/*  24 */     super(Document.class);
/*     */   }
/*     */ 
/*     */   public List<Document> findByIsShared(Document document, Date from, Date to, Long userId, ArrayList<Long> roleIds, Long depId, PagingBean pb)
/*     */   {
/*  30 */     ArrayList list = new ArrayList();
/*  31 */     StringBuffer buff = new StringBuffer();
/*  32 */     if (roleIds.contains(AppRole.SUPER_ROLEID)) {
/*  33 */       buff.append("select vo from Document vo where vo.isShared=1");
/*     */     } else {
/*  35 */       buff.append("select vo from Document vo where vo.isShared=1 and ( 0=1 ");
/*  36 */       if (depId != null) {
/*  37 */         buff.append(" or vo.sharedDepIds like ? ");
/*  38 */         list.add("%," + depId + ",%");
/*     */       }
/*  40 */       if (roleIds != null) {
/*  41 */         for (Long roleId : roleIds) {
/*  42 */           buff.append(" or vo.sharedRoleIds like ?");
/*  43 */           list.add("%," + roleId + ",%");
/*     */         }
/*     */       }
/*  46 */       if (userId != null) {
/*  47 */         buff.append(" or vo.sharedUserIds like ?");
/*  48 */         list.add("%," + userId + ",%");
/*     */       }
/*  50 */       buff.append(")");
/*     */     }
/*  52 */     if (document != null) {
/*  53 */       if (StringUtils.isNotEmpty(document.getDocName())) {
/*  54 */         buff.append(" and vo.docName like ?");
/*  55 */         list.add("%" + document.getDocName() + "%");
/*     */       }
/*  57 */       if (StringUtils.isNotEmpty(document.getFullname())) {
/*  58 */         buff.append(" and vo.fullname=?");
/*  59 */         list.add("," + document.getFullname() + ",");
/*     */       }
/*     */     }
/*  62 */     if (to != null) {
/*  63 */       buff.append(" and vo.createtime <= ?");
/*  64 */       list.add(to);
/*     */     }
/*  66 */     if (from != null) {
/*  67 */       buff.append(" and vo.createtime >= ?");
/*  68 */       list.add(from);
/*     */     }
/*  70 */     buff.append(" order by vo desc");
/*  71 */     return findByHql(buff.toString(), list.toArray(), pb);
/*     */   }
/*     */ 
/*     */   public List<Document> findByPublic(String path, Document document, Date from, Date to, AppUser appUser, PagingBean pb)
/*     */   {
/*  81 */     StringBuffer sb = new StringBuffer();
/*  82 */     List list = new ArrayList();
/*  83 */     if (!appUser.getRights().contains("__ALL")) {
/*  84 */       sb.append("select doc from Document doc,DocFolder docF,DocPrivilege pr where doc.docFolder=docF and pr.docFolder=docF");
/*  85 */       Set roles = appUser.getRoles();
/*  86 */       StringBuffer buff = new StringBuffer();
/*  87 */       if (roles != null) {
/*  88 */         Iterator it = roles.iterator();
/*  89 */         while (it.hasNext()) {
/*  90 */           Long roleId = ((AppRole)it.next()).getRoleId();
/*  91 */           buff.append(roleId.toString() + ',');
/*     */         }
/*  93 */         if (roles.size() > 0) {
/*  94 */           buff.deleteCharAt(buff.length() - 1);
/*     */         }
/*     */       }
/*  97 */       sb.append(" and pr.rights>0 and ((pr.udrId=? and pr.flag=1)");
/*  98 */       Integer userId = Integer.valueOf(Integer.parseInt(appUser.getUserId().toString()));
/*  99 */       list.add(userId);
/* 100 */       if (appUser.getDepartment() != null) {
/* 101 */         Integer depId = Integer.valueOf(Integer.parseInt(appUser.getDepartment().getDepId().toString()));
/* 102 */         sb.append(" or (pr.udrId=? and pr.flag=2)");
/* 103 */         list.add(depId);
/*     */       }
/* 105 */       if ((buff.toString() != null) && (buff.length() > 0)) {
/* 106 */         sb.append(" or (pr.udrId in (" + buff.toString() + ") and pr.flag=3)");
/*     */       }
/* 108 */       sb.append(")");
/*     */     } else {
/* 110 */       sb.append("select doc from Document doc,DocFolder docF where doc.docFolder=docF and docF.isShared=1");
/*     */     }
/* 112 */     if (path != null) {
/* 113 */       sb.append(" and docF.path like ?");
/* 114 */       list.add(path + "%");
/*     */     }
/* 116 */     if ((document != null) && 
/* 117 */       (document.getDocName() != null)) {
/* 118 */       sb.append(" and doc.docName like ?");
/* 119 */       list.add("%" + document.getDocName() + "%");
/*     */     }
/*     */ 
/* 122 */     if (to != null) {
/* 123 */       sb.append(" and doc.createtime <= ?");
/* 124 */       list.add(to);
/*     */     }
/* 126 */     if (from != null) {
/* 127 */       sb.append(" and doc.createtime >= ?");
/* 128 */       list.add(from);
/*     */     }
/* 130 */     sb.append(" group by doc order by doc.docName asc,doc.createtime asc");
/* 131 */     List docList = findByHql(sb.toString(), list.toArray(), pb);
/* 132 */     return docList;
/*     */   }
/*     */ 
/*     */   public List<Document> findByPersonal(Long userId, Document document, Date from, Date to, String path, PagingBean pb)
/*     */   {
/* 137 */     StringBuffer sb = new StringBuffer();
/* 138 */     ArrayList list = new ArrayList();
/* 139 */     sb.append("select doc from Document doc,DocFolder docFolder where doc.docFolder=docFolder and docFolder.appUser.userId is not Null");
/* 140 */     if (path != null) {
/* 141 */       sb.append(" and docFolder.path like ?");
/* 142 */       list.add(path + "%");
/*     */     }
/* 144 */     if (userId != null) {
/* 145 */       sb.append(" and doc.appUser.userId=?");
/* 146 */       list.add(userId);
/*     */     }
/* 148 */     if ((document != null) && 
/* 149 */       (document.getDocName() != null)) {
/* 150 */       sb.append(" and doc.docName like ?");
/* 151 */       list.add("%" + document.getDocName() + "%");
/*     */     }
/*     */ 
/* 154 */     if (to != null) {
/* 155 */       sb.append(" and vo.createtime <= ?");
/* 156 */       list.add(to);
/*     */     }
/* 158 */     if (from != null) {
/* 159 */       sb.append(" and vo.createtime >= ?");
/* 160 */       list.add(from);
/*     */     }
/* 162 */     sb.append(" group by doc");
/* 163 */     return findByHql(sb.toString(), list.toArray(), pb);
/*     */   }
/*     */ 
/*     */   public List<Document> findByFolder(String path)
/*     */   {
/* 168 */     String hql = "select doc from Document doc where doc.docFolder.path like ?";
/* 169 */     List list = new ArrayList();
/* 170 */     list.add(path + "%");
/* 171 */     return findByHql(hql, list.toArray());
/*     */   }
/*     */ 
/*     */   public List<Document> searchDocument(AppUser appUser, String content, boolean isHaveData, PagingBean pb)
/*     */   {
/* 177 */     StringBuffer buff = new StringBuffer(
/* 178 */       "select doc from Document doc,DocFolder docF ");
/* 179 */     if (isHaveData) {
/* 180 */       buff.append(" ,DocPrivilege pr");
/*     */     }
/* 182 */     buff.append(" where ");
/* 183 */     Set roles = appUser.getRoles();
/* 184 */     List list = new ArrayList();
/* 185 */     StringBuffer sb = new StringBuffer();
/* 186 */     Iterator it = roles.iterator();
/* 187 */     if (roles.size() > 0) {
/* 188 */       while (it.hasNext()) {
/* 189 */         Long roleId = ((AppRole)it.next()).getRoleId();
/* 190 */         sb.append(roleId.toString() + ',');
/*     */       }
/* 192 */       if (roles.size() > 0) {
/* 193 */         sb.deleteCharAt(sb.length() - 1);
/*     */       }
/*     */     }
/* 196 */     buff.append(" ((doc.isShared=1 ");
/* 197 */     if (!appUser.getRights().contains("__ALL")) {
/* 198 */       buff.append(" and (0=1");
/* 199 */       if (appUser.getDepartment() != null) {
/* 200 */         buff.append(" or doc.sharedDepIds like ? ");
/* 201 */         list.add("%," + appUser.getDepartment().getDepId() + ",%");
/*     */       }
/* 203 */       while (it.hasNext()) {
/* 204 */         Long roleId = ((AppRole)it.next()).getRoleId();
/* 205 */         buff.append(" or doc.sharedRoleIds like ?");
/* 206 */         list.add("%," + roleId.toString() + ",%");
/*     */       }
/* 208 */       if (appUser.getUserId() != null) {
/* 209 */         buff.append(" or doc.sharedUserIds like ?");
/* 210 */         list.add("%," + appUser.getUserId() + ",%");
/*     */       }
/* 212 */       buff.append(")");
/*     */     }
/* 214 */     buff.append(") or (doc.isShared=0 and doc.docFolder=docF and docF.appUser.userId is not Null and doc.appUser.userId=? )");
/* 215 */     list.add(appUser.getUserId());
/* 216 */     buff.append(" or (doc.docFolder=docF and docF.isShared=1");
/* 217 */     if ((isHaveData) && 
/* 218 */       (!appUser.getRights().contains("__ALL"))) {
/* 219 */       buff.append(" and pr.docFolder=docF");
/* 220 */       buff.append(" and pr.rights>0 and ((pr.udrId=? and pr.flag=1)");
/* 221 */       Integer userId = Integer.valueOf(Integer.parseInt(appUser.getUserId()
/* 222 */         .toString()));
/* 223 */       list.add(userId);
/* 224 */       if (appUser.getDepartment() != null) {
/* 225 */         Integer depId = Integer.valueOf(Integer.parseInt(appUser.getDepartment()
/* 226 */           .getDepId().toString()));
/* 227 */         buff.append(" or (pr.udrId=? and pr.flag=2)");
/* 228 */         list.add(depId);
/*     */       }
/* 230 */       if ((sb.toString() != null) && (sb.length() > 1)) {
/* 231 */         buff.append(" or (pr.udrId in (" + sb.toString() + 
/* 232 */           ") and pr.flag=3)");
/*     */       }
/* 234 */       buff.append(")");
/*     */     }
/*     */ 
/* 237 */     buff.append(")");
/*     */ 
/* 239 */     buff.append(")");
/* 240 */     if (StringUtils.isNotEmpty(content)) {
/* 241 */       buff.append(" and (doc.content like ? or doc.docName like ?)");
/* 242 */       list.add("%" + content + "%");
/* 243 */       list.add("%" + content + "%");
/*     */     }
/* 245 */     buff.append(" group by doc order by doc desc");
/* 246 */     return findByHql(buff.toString(), list.toArray(), pb);
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.document.impl.DocumentDaoImpl
 * JD-Core Version:    0.6.0
 */