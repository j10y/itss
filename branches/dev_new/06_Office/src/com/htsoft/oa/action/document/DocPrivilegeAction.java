 package com.htsoft.oa.action.document;
 
 import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.web.action.BaseAction;
import com.htsoft.oa.model.document.DocPrivilege;
import com.htsoft.oa.model.system.AppRole;
import com.htsoft.oa.model.system.AppUser;
import com.htsoft.oa.model.system.Department;
import com.htsoft.oa.service.document.DocPrivilegeService;
import com.htsoft.oa.service.system.AppRoleService;
import com.htsoft.oa.service.system.AppUserService;
import com.htsoft.oa.service.system.DepartmentService;
 
 public class DocPrivilegeAction extends BaseAction
 {
 
   @Resource
   private DocPrivilegeService docPrivilegeService;
   private DocPrivilege docPrivilege;
 
   @Resource
   private AppRoleService appRoleService;
 
   @Resource
   private AppUserService appUserService;
 
   @Resource
   private DepartmentService departmentService;
   private Long privilegeId;
 
   public Long getPrivilegeId()
   {
/*  49 */     return this.privilegeId;
   }
 
   public void setPrivilegeId(Long privilegeId) {
/*  53 */     this.privilegeId = privilegeId;
   }
 
   public DocPrivilege getDocPrivilege() {
/*  57 */     return this.docPrivilege;
   }
 
   public void setDocPrivilege(DocPrivilege docPrivilege) {
/*  61 */     this.docPrivilege = docPrivilege;
   }
 
   public String list()
   {
/*  68 */     QueryFilter filter = new QueryFilter(getRequest());
/*  69 */     List<DocPrivilege> list = this.docPrivilegeService.getAll(filter);
/*  70 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  71 */       .append(filter.getPagingBean().getTotalItems()).append(",result:[");
/*  72 */     for (DocPrivilege privilege : list) {
/*  73 */       Integer rights = privilege.getRights();
/*  74 */       String right = Integer.toBinaryString(rights.intValue());
/*  75 */       Integer read = null;
/*  76 */       Integer update = null;
/*  77 */       Integer delete = null;
/*  78 */       char[] cc = right.toCharArray();
/*  79 */       if ((cc.length == 1) && (cc[0] == '1')) {
/*  80 */         read = Integer.valueOf(1);
       }
/*  82 */       if (cc.length == 2) {
/*  83 */         if (cc[0] == '1') {
/*  84 */           update = Integer.valueOf(1);
         }
/*  86 */         if (cc[1] == '1') {
/*  87 */           read = Integer.valueOf(1);
         }
       }
/*  90 */       if (cc.length == 3) {
/*  91 */         if (cc[0] == '1') {
/*  92 */           delete = Integer.valueOf(1);
         }
/*  94 */         if (cc[1] == '1') {
/*  95 */           update = Integer.valueOf(1);
         }
/*  97 */         if (cc[2] == '1') {
/*  98 */           read = Integer.valueOf(1);
         }
       }
/* 101 */       buff.append("{'privilegeId':" + privilege.getPrivilegeId() + ",'udrId':" + privilege.getUdrId() + ",'udrName':'" + privilege.getUdrName() + "','folderName':'" + privilege.getDocFolder().getFolderName() + "','flag':" + privilege.getFlag() + ",'rightR':" + read + ",'rightU':" + update + ",'rightD':" + delete + "},");
     }
/* 103 */     if (list.size() > 0) {
/* 104 */       buff.deleteCharAt(buff.length() - 1);
     }
/* 106 */     buff.append("]}");
 
/* 108 */     this.jsonString = buff.toString();
/* 109 */     return "success";
   }
 
   public String multiDel()
   {
/* 117 */     String[] ids = getRequest().getParameterValues("ids");
/* 118 */     if (ids != null) {
/* 119 */       for (String id : ids) {
/* 120 */         this.docPrivilegeService.remove(new Long(id));
       }
     }
/* 123 */     this.jsonString = "{success:true}";
/* 124 */     return "success";
   }
 
   public String get()
   {
/* 132 */     DocPrivilege docPrivilege = (DocPrivilege)this.docPrivilegeService.get(this.privilegeId);
/* 133 */     Gson gson = new Gson();
 
/* 135 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 136 */     sb.append(gson.toJson(docPrivilege));
/* 137 */     sb.append("}");
/* 138 */     setJsonString(sb.toString());
/* 139 */     return "success";
   }
 
   public String change()
   {
/* 146 */     String strPrivilegeId = getRequest().getParameter("privilegeId");
/* 147 */     String strField = getRequest().getParameter("field");
/* 148 */     String strFieldValue = getRequest().getParameter("fieldValue");
/* 149 */     if (StringUtils.isNotEmpty(strPrivilegeId)) {
/* 150 */       this.docPrivilege = ((DocPrivilege)this.docPrivilegeService.get(Long.valueOf(Long.parseLong(strPrivilegeId))));
/* 151 */       Integer in = this.docPrivilege.getRights();
/* 152 */       if (in.intValue() > 0) {
/* 153 */         String str = Integer.toBinaryString(in.intValue());
/* 154 */         StringBuffer buff = new StringBuffer(str);
/* 155 */         if (buff.length() == 1) {
/* 156 */           buff.insert(0, "00");
         }
/* 158 */         if (buff.length() == 2) {
/* 159 */           buff.insert(0, "0");
         }
/* 161 */         if (buff.length() <= 0) {
/* 162 */           buff.insert(0, "000");
         }
/* 164 */         String rights = "";
/* 165 */         if ("rightR".equals(strField)) {
/* 166 */           StringBuffer newBuff = new StringBuffer();
/* 167 */           if ("true".equals(strFieldValue))
/* 168 */             newBuff.append(buff.deleteCharAt(2).toString()).append("1");
           else {
/* 170 */             newBuff.append(buff.deleteCharAt(2).toString()).append("0");
           }
/* 172 */           rights = newBuff.toString();
         }
 
/* 175 */         if ("rightU".equals(strField)) {
/* 176 */           StringBuffer newBuff = new StringBuffer();
/* 177 */           if ("true".equals(strFieldValue))
/* 178 */             newBuff.append(buff.charAt(0)).append("1").append(buff.charAt(2));
           else {
/* 180 */             newBuff.append(buff.charAt(0)).append("0").append(buff.charAt(2));
           }
/* 182 */           rights = newBuff.toString();
         }
 
/* 185 */         if ("rightD".equals(strField)) {
/* 186 */           StringBuffer newBuff = new StringBuffer();
/* 187 */           if ("true".equals(strFieldValue))
/* 188 */             newBuff.append("1").append(buff.deleteCharAt(0).toString());
           else {
/* 190 */             newBuff.append("0").append(buff.deleteCharAt(0).toString());
           }
/* 192 */           rights = newBuff.toString();
         }
/* 194 */         Integer right = Integer.valueOf(Integer.parseInt(rights, 2));
/* 195 */         this.docPrivilege.setRights(right);
/* 196 */         this.docPrivilegeService.save(this.docPrivilege);
/* 197 */         setJsonString("{success:true}");
       }
     } else {
/* 200 */       setJsonString("{success:false}");
     }
/* 202 */     return "success";
   }
 
   public String save()
   {
/* 210 */     this.docPrivilegeService.save(this.docPrivilege);
/* 211 */     setJsonString("{success:true}");
/* 212 */     return "success";
   }
 
   public String add()
   {
/* 223 */     String strFolderId = getRequest().getParameter("folderId");
/* 224 */     String strRoleIds = getRequest().getParameter("roleIds");
/* 225 */     String strUserIds = getRequest().getParameter("userIds");
/* 226 */     String strDepIds = getRequest().getParameter("depIds");
/* 227 */     String strRightR = getRequest().getParameter("rightR");
/* 228 */     String strRightU = getRequest().getParameter("rightU");
/* 229 */     String strRightD = getRequest().getParameter("rightD");
/* 230 */     StringBuffer buff = new StringBuffer();
 
/* 232 */     if (StringUtils.isNotEmpty(strRightD))
/* 233 */       buff.append("1");
     else {
/* 235 */       buff.append("0");
     }
/* 237 */     if (StringUtils.isNotEmpty(strRightU))
/* 238 */       buff.append("1");
     else {
/* 240 */       buff.append("0");
     }
/* 242 */     if (StringUtils.isNotEmpty(strRightR))
/* 243 */       buff.append("1");
     else {
/* 245 */       buff.append("0");
     }
/* 247 */     Integer rights = Integer.valueOf(Integer.parseInt(buff.toString(), 2));
/* 248 */     if (StringUtils.isNotEmpty(strFolderId)) {
/* 249 */       Long folderId = Long.valueOf(Long.parseLong(strFolderId));
/* 250 */       if (StringUtils.isNotEmpty(strRoleIds)) {
/* 251 */         String[] roles = strRoleIds.split(",");
/* 252 */         if (roles.length > 0) {
/* 253 */           for (int i = 0; i < roles.length; i++) {
/* 254 */             DocPrivilege docp = new DocPrivilege();
/* 255 */             docp.setFolderId(folderId);
/* 256 */             docp.setFlag(Short.valueOf((short) 3));
/* 257 */             Integer roleId = Integer.valueOf(Integer.parseInt(roles[i]));
/* 258 */             AppRole appRole = (AppRole)this.appRoleService.get(Long.valueOf(roleId.longValue()));
/* 259 */             docp.setUdrId(roleId);
/* 260 */             docp.setUdrName(appRole.getName());
/* 261 */             docp.setRights(rights);
/* 262 */             docp.setFdFlag(Short.valueOf((short) 0));
/* 263 */             this.docPrivilegeService.save(docp);
           }
         }
       }
/* 267 */       if (StringUtils.isNotEmpty(strUserIds)) {
/* 268 */         String[] userIds = strUserIds.split(",");
/* 269 */         if (userIds.length > 0) {
/* 270 */           for (int i = 0; i < userIds.length; i++) {
/* 271 */             DocPrivilege docp = new DocPrivilege();
/* 272 */             docp.setFolderId(folderId);
/* 273 */             docp.setFlag(Short.valueOf((short) 1));
/* 274 */             Integer userId = Integer.valueOf(Integer.parseInt(userIds[i]));
/* 275 */             AppUser appUser = (AppUser)this.appUserService.get(Long.valueOf(userId.longValue()));
/* 276 */             docp.setUdrId(userId);
/* 277 */             docp.setUdrName(appUser.getFullname());
/* 278 */             docp.setRights(rights);
/* 279 */             docp.setFdFlag(Short.valueOf((short) 0));
/* 280 */             this.docPrivilegeService.save(docp);
           }
         }
       }
/* 284 */       if (StringUtils.isNotEmpty(strDepIds)) {
/* 285 */         String[] depIds = strDepIds.split(",");
/* 286 */         if (depIds.length > 0) {
/* 287 */           for (int i = 0; i < depIds.length; i++) {
/* 288 */             DocPrivilege docp = new DocPrivilege();
/* 289 */             docp.setFolderId(folderId);
/* 290 */             docp.setFlag(Short.valueOf((short) 2));
/* 291 */             Integer depId = Integer.valueOf(Integer.parseInt(depIds[i]));
/* 292 */             Department department = (Department)this.departmentService.get(Long.valueOf(depId.longValue()));
/* 293 */             docp.setUdrId(depId);
/* 294 */             docp.setUdrName(department.getDepName());
/* 295 */             docp.setRights(rights);
/* 296 */             docp.setFdFlag(Short.valueOf((short) 0));
/* 297 */             this.docPrivilegeService.save(docp);
           }
         }
       }
     }
/* 302 */     setJsonString("{success:true}");
/* 303 */     return "success";
   }
 }
