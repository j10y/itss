 package com.xpsoft.oa.dao.info.impl;
 
 import com.xpsoft.core.dao.impl.BaseDaoImpl;
 import com.xpsoft.oa.dao.info.AppTipsDao;
 import com.xpsoft.oa.model.info.AppTips;
 
 public class AppTipsDaoImpl extends BaseDaoImpl<AppTips>
   implements AppTipsDao
 {
   public AppTipsDaoImpl()
   {
     super(AppTips.class);
   }
 }
