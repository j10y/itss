 package com.htsoft.oa.action.communicate;
 
 import com.google.gson.Gson;
 import com.htsoft.core.command.QueryFilter;
 import com.htsoft.core.util.ContextUtil;
 import com.htsoft.core.web.action.BaseAction;
 import com.htsoft.oa.model.communicate.PhoneGroup;
 import com.htsoft.oa.model.system.AppUser;
 import com.htsoft.oa.service.communicate.PhoneBookService;
 import com.htsoft.oa.service.communicate.PhoneGroupService;
 import java.util.List;
 import javax.annotation.Resource;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.commons.lang.StringUtils;
 
 public class PhoneGroupAction extends BaseAction
 {
 
   @Resource
   private PhoneGroupService phoneGroupService;
   private PhoneGroup phoneGroup;
 
   @Resource
   private PhoneBookService phoneBookService;
   private Long groupId;
 
   public Long getGroupId()
   {
/*  32 */     return this.groupId;
   }
 
   public void setGroupId(Long groupId) {
/*  36 */     this.groupId = groupId;
   }
 
   public PhoneGroup getPhoneGroup() {
/*  40 */     return this.phoneGroup;
   }
 
   public void setPhoneGroup(PhoneGroup phoneGroup) {
/*  44 */     this.phoneGroup = phoneGroup;
   }
 
   public String list()
   {
/*  51 */     List<PhoneGroup> list = this.phoneGroupService.getAll(ContextUtil.getCurrentUserId());
/*  52 */     String method = getRequest().getParameter("method");
/*  53 */     StringBuffer buff = new StringBuffer();
/*  54 */     int i = 0;
/*  55 */     if (StringUtils.isNotEmpty(method)) {
/*  56 */       buff.append("[");
     } else {
/*  58 */       i++;
/*  59 */       buff.append("[{id:'0',text:'联系人分组',expanded:true,children:[");
     }
/*  61 */     for (PhoneGroup pg : list) {
/*  62 */       buff.append("{id:'" + pg.getGroupId() + "',text:'" + pg.getGroupName() + "',leaf:true},");
     }
/*  64 */     if (!list.isEmpty()) {
/*  65 */       buff.deleteCharAt(buff.length() - 1);
     }
/*  67 */     if (i == 0)
/*  68 */       buff.append("]");
     else {
/*  70 */       buff.append("]}]");
     }
/*  72 */     this.jsonString = buff.toString();
/*  73 */     return "success";
   }
 
   public String multiDel()
   {
/*  81 */     String[] ids = getRequest().getParameterValues("ids");
/*  82 */     if (ids != null) {
/*  83 */       for (String id : ids) {
/*  84 */         Long groupId = new Long(id);
/*  85 */         PhoneGroup phoneGroup = (PhoneGroup)this.phoneGroupService.get(groupId);
/*  86 */         this.phoneGroupService.remove(groupId);
/*  87 */         List<PhoneGroup> list = this.phoneGroupService.findBySnDown(phoneGroup.getSn(), phoneGroup.getAppUser().getUserId());
/*  88 */         for (PhoneGroup pg : list) {
/*  89 */           pg.setSn(Integer.valueOf(pg.getSn().intValue() - 1));
/*  90 */           this.phoneGroupService.save(pg);
         }
       }
     }
 
/*  95 */     this.jsonString = "{success:true}";
 
/*  97 */     return "success";
   }
 
   public String count() {
/* 101 */     QueryFilter filter = new QueryFilter(getRequest());
/* 102 */     List pbList = this.phoneBookService.getAll(filter);
/* 103 */     setJsonString("{success:true,count:" + pbList.size() + "}");
/* 104 */     return "success";
   }
 
   public String get()
   {
/* 112 */     PhoneGroup phoneGroup = (PhoneGroup)this.phoneGroupService.get(this.groupId);
/* 113 */     Gson gson = new Gson();
 
/* 115 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 116 */     sb.append(gson.toJson(phoneGroup));
/* 117 */     sb.append("}");
/* 118 */     setJsonString(sb.toString());
 
/* 120 */     return "success";
   }
 
   public String save()
   {
/* 126 */     AppUser appUser = ContextUtil.getCurrentUser();
/* 127 */     Integer sn = this.phoneGroupService.findLastSn(appUser.getUserId());
/* 128 */     if (sn == null) sn = Integer.valueOf(0);
/* 129 */     this.phoneGroup.setAppUser(appUser);
/* 130 */     this.phoneGroup.setSn(Integer.valueOf(sn.intValue() + 1));
/* 131 */     this.phoneGroupService.save(this.phoneGroup);
/* 132 */     setJsonString("{success:true}");
/* 133 */     return "success";
   }
 
   public String move() {
/* 137 */     String strOpt = getRequest().getParameter("optId");
/* 138 */     String strGroupId = getRequest().getParameter("groupId");
/* 139 */     Long userId = ContextUtil.getCurrentUserId();
/* 140 */     if (StringUtils.isNotEmpty(strGroupId)) {
/* 141 */       Integer opt = Integer.valueOf(Integer.parseInt(strOpt));
/* 142 */       Long groupId = Long.valueOf(Long.parseLong(strGroupId));
/* 143 */       this.phoneGroup = ((PhoneGroup)this.phoneGroupService.get(groupId));
/* 144 */       Integer sn = this.phoneGroup.getSn();
/* 145 */       if ((opt.intValue() == 1) && 
/* 146 */         (sn.intValue() > 1)) {
/* 147 */         PhoneGroup pg = this.phoneGroupService.findBySn(Integer.valueOf(sn.intValue() - 1), userId);
/* 148 */         pg.setSn(sn);
/* 149 */         this.phoneGroupService.save(pg);
/* 150 */         this.phoneGroup.setSn(Integer.valueOf(sn.intValue() - 1));
/* 151 */         this.phoneGroupService.save(this.phoneGroup);
       }
 
/* 154 */       if ((opt.intValue() == 2) && 
/* 155 */         (sn.intValue() < this.phoneGroupService.findLastSn(userId).intValue())) {
/* 156 */         PhoneGroup pg = this.phoneGroupService.findBySn(Integer.valueOf(sn.intValue() + 1), userId);
/* 157 */         pg.setSn(sn);
/* 158 */         this.phoneGroup.setSn(Integer.valueOf(sn.intValue() + 1));
/* 159 */         this.phoneGroupService.save(pg);
/* 160 */         this.phoneGroupService.save(this.phoneGroup);
       }
 
/* 163 */       if ((opt.intValue() == 3) && 
/* 164 */         (sn.intValue() > 1)) {
/* 165 */         List<PhoneGroup> list = this.phoneGroupService.findBySnUp(sn, userId);
/* 166 */         for (PhoneGroup pg : list) {
/* 167 */           pg.setSn(Integer.valueOf(pg.getSn().intValue() + 1));
/* 168 */           this.phoneGroupService.save(pg);
         }
/* 170 */         this.phoneGroup.setSn(Integer.valueOf(1));
/* 171 */         this.phoneGroupService.save(this.phoneGroup);
       }
 
/* 174 */       if ((opt.intValue() == 4) && 
/* 175 */         (sn.intValue() < this.phoneGroupService.findLastSn(userId).intValue())) {
/* 176 */         List<PhoneGroup> list = this.phoneGroupService.findBySnDown(sn, userId);
/* 177 */         for (PhoneGroup pg : list) {
/* 178 */           pg.setSn(Integer.valueOf(pg.getSn().intValue() - 1));
/* 179 */           this.phoneGroupService.save(pg);
         }
/* 181 */         this.phoneGroup.setSn(Integer.valueOf(this.phoneGroupService.findLastSn(userId).intValue() + 1));
/* 182 */         this.phoneGroupService.save(this.phoneGroup);
       }
 
/* 185 */       setJsonString("{success:true}");
     } else {
/* 187 */       setJsonString("{success:false}");
     }
/* 189 */     return "success";
   }
 }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.action.communicate.PhoneGroupAction
 * JD-Core Version:    0.6.0
 */