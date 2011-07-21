/*    */ package com.xpsoft.oa.service.admin.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.core.util.AppUtil;
/*    */ import com.xpsoft.oa.dao.admin.OfficeGoodsDao;
/*    */ import com.xpsoft.oa.dao.system.AppUserDao;
/*    */ import com.xpsoft.oa.model.admin.OfficeGoods;
/*    */ import com.xpsoft.oa.model.info.ShortMessage;
/*    */ import com.xpsoft.oa.model.system.AppUser;
/*    */ import com.xpsoft.oa.service.admin.OfficeGoodsService;
/*    */ import com.xpsoft.oa.service.info.ShortMessageService;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Resource;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ 
/*    */ public class OfficeGoodsServiceImpl extends BaseServiceImpl<OfficeGoods>
/*    */   implements OfficeGoodsService
/*    */ {
/* 27 */   private static Log logger = LogFactory.getLog(OfficeGoodsServiceImpl.class);
/*    */   private OfficeGoodsDao dao;
/*    */ 
/*    */   @Resource
/*    */   private AppUserDao appUserDao;
/*    */ 
/*    */   @Resource
/*    */   private ShortMessageService shortMessageService;
/*    */ 
/*    */   public OfficeGoodsServiceImpl(OfficeGoodsDao dao)
/*    */   {
/* 35 */     super(dao);
/* 36 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public void sendWarmMessage()
/*    */   {
/* 41 */     List<OfficeGoods> list = this.dao.findByWarm();
/* 42 */     if (list.size() > 0) {
/* 43 */       StringBuffer sb = new StringBuffer("办公用品：");
/* 44 */       for (OfficeGoods goods : list) {
/* 45 */         if (goods.getIsWarning().shortValue() == 1)
/* 46 */           sb.append(goods.getGoodsName() + "已经低于警报库存量" + goods.getWarnCounts() + "了.");
/*    */         else {
/* 48 */           sb.append(goods.getGoodsName() + "已经没有库存了.");
/*    */         }
/*    */       }
/* 51 */       sb.append("请尽快购买相应的用品");
/* 52 */       Map map = AppUtil.getSysConfig();
/* 53 */       String username = (String)map.get("goodsStockUser");
/* 54 */       if (StringUtils.isNotEmpty(username)) {
/* 55 */         AppUser user = this.appUserDao.findByUserName(username);
/* 56 */         if (user != null) {
/* 57 */           this.shortMessageService.save(AppUser.SYSTEM_USER, user.getUserId().toString(), sb.toString(), ShortMessage.MSG_TYPE_SYS);
/* 58 */           logger.info("messages had sent to the manager!" + user.getUsername());
/*    */         } else {
/* 60 */           logger.info("can not find the user in the system.");
/*    */         }
/*    */       } else {
/* 63 */         logger.info("can not find the name in the map.");
/*    */       }
/* 65 */       logger.info(sb.toString());
/*    */     } else {
/* 67 */       logger.info("没有产品要补仓.");
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.admin.impl.OfficeGoodsServiceImpl
 * JD-Core Version:    0.6.0
 */