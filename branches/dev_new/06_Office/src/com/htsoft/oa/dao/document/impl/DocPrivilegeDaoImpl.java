 package com.htsoft.oa.dao.document.impl;
 
 import com.htsoft.core.dao.impl.BaseDaoImpl;
 import com.htsoft.core.web.paging.PagingBean;
 import com.htsoft.oa.dao.document.DocPrivilegeDao;
 import com.htsoft.oa.model.document.DocPrivilege;
 import com.htsoft.oa.model.system.AppRole;
 import com.htsoft.oa.model.system.AppUser;
 import com.htsoft.oa.model.system.Department;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Set;
 
 public class DocPrivilegeDaoImpl extends BaseDaoImpl<DocPrivilege>
   implements DocPrivilegeDao
 {
   public DocPrivilegeDaoImpl()
   {
/*  21 */     super(DocPrivilege.class);
   }
 
   public List<DocPrivilege> getAll(DocPrivilege docPrivilege, Long folderId, PagingBean pb)
   {
/*  26 */     StringBuffer hql = new StringBuffer("from DocPrivilege vo where 1=1");
/*  27 */     ArrayList list = new ArrayList();
/*  28 */     if (folderId != null) {
/*  29 */       hql.append(" and vo.docFolder.folderId=?");
/*  30 */       list.add(folderId);
     }
/*  32 */     if (docPrivilege != null) {
/*  33 */       if (docPrivilege.getUdrName() != null) {
/*  34 */         hql.append(" and vo.udrName=?");
/*  35 */         list.add(docPrivilege.getUdrName());
       }
/*  37 */       if (docPrivilege.getFlag() != null) {
/*  38 */         hql.append(" and vo.flag=?");
/*  39 */         list.add(docPrivilege.getFlag());
       }
     }
/*  42 */     return findByHql(hql.toString(), list.toArray(), pb);
   }
 
   public List<DocPrivilege> getByPublic(DocPrivilege docPrivilege, Long urdId)
   {
/*  47 */     StringBuffer sb = new StringBuffer("from DocPrivilege vo where 1=1");
/*  48 */     return findByHql(sb.toString());
   }
 
   public List<Integer> getRightsByFolder(AppUser user, Long folderId)
   {
/*  53 */     List rights = new ArrayList();
/*  54 */     List list = new ArrayList();
/*  55 */     StringBuffer buff = new StringBuffer("from DocPrivilege vo where vo.docFolder.folderId=?");
/*  56 */     list.add(folderId);
/*  57 */     buff.append(" and (");
/*  58 */     if (user != null) {
/*  59 */       buff.append("(vo.udrId=? and vo.flag=1)");
/*  60 */       list.add(Integer.valueOf(Integer.parseInt(user.getUserId().toString())));
     }
/*  62 */     if (user.getDepartment() != null) {
/*  63 */       buff.append(" or (vo.udrId=? and vo.flag=2)");
/*  64 */       list.add(Integer.valueOf(Integer.parseInt(user.getDepartment().getDepId().toString())));
     }
     Iterator it;
/*  66 */     if ((user.getRoles() != null) && (user.getRoles().size() > 0)) {
/*  67 */       Set roles = user.getRoles();
/*  68 */       StringBuffer sb = new StringBuffer();
/*  69 */       it = roles.iterator();
/*  70 */       while (it.hasNext()) {
/*  71 */         sb.append(((AppRole)it.next()).getRoleId() + ",");
       }
/*  73 */       if (roles.size() > 0) {
/*  74 */         sb.deleteCharAt(sb.length() - 1);
       }
/*  76 */       if (sb != null) {
/*  77 */         buff.append(" or (vo.udrId in (" + sb + ") and vo.flag=3)");
       }
     }
/*  80 */     buff.append(" )");
/*  81 */     List<DocPrivilege> docPr = findByHql(buff.toString(), list.toArray());
/*  82 */     if (docPr != null) {
/*  83 */       for (DocPrivilege doc : docPr) {
/*  84 */         rights.add(doc.getRights());
       }
     }
/*  87 */     return rights;
   }
 
   public Integer getRightsByDocument(AppUser user, Long docId)
   {
/*  92 */     List list = new ArrayList();
/*  93 */     StringBuffer buff = new StringBuffer("select pr from Document doc,DocFolder docF,DocPrivilege pr where doc.docFolder=docF and pr.docFolder=docF and pr.rights>0 and doc.docId=?");
/*  94 */     list.add(docId);
/*  95 */     buff.append(" and (");
/*  96 */     if (user != null) {
/*  97 */       buff.append("(pr.udrId=? and pr.flag=1)");
/*  98 */       list.add(Integer.valueOf(Integer.parseInt(user.getUserId().toString())));
     }
/* 100 */     if (user.getDepartment() != null) {
/* 101 */       buff.append(" or (pr.udrId=? and pr.flag=2)");
/* 102 */       list.add(Integer.valueOf(Integer.parseInt(user.getDepartment().getDepId().toString())));
     }
/* 104 */     if ((user.getRoles() != null) && (user.getRoles().size() > 0)) {
/* 105 */       Set roles = user.getRoles();
/* 106 */       StringBuffer sb = new StringBuffer();
/* 107 */       Iterator it = roles.iterator();
/* 108 */       while (it.hasNext()) {
/* 109 */         sb.append(((AppRole)it.next()).getRoleId() + ",");
       }
/* 111 */       if (roles.size() > 0) {
/* 112 */         sb.deleteCharAt(sb.length() - 1);
       }
/* 114 */       if (sb != null) {
/* 115 */         buff.append(" or (pr.udrId in (" + sb + ") and pr.flag=3)");
       }
     }
/* 118 */     buff.append(" )");
/* 119 */     List<DocPrivilege> docPr = findByHql(buff.toString(), list.toArray());
/* 120 */     Integer right = Integer.valueOf(0);
/* 121 */     if (docPr != null) {
/* 122 */       for (DocPrivilege doc : docPr) {
/* 123 */         right = Integer.valueOf(right.intValue() | doc.getRights().intValue());
       }
     }
/* 126 */     return right;
   }
 
   public Integer countPrivilege()
   {
/* 131 */     String hql = "from DocPrivilege pr";
/* 132 */     List list = findByHql(hql);
/* 133 */     return Integer.valueOf(list.size());
   }
 }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.dao.document.impl.DocPrivilegeDaoImpl
 * JD-Core Version:    0.6.0
 */