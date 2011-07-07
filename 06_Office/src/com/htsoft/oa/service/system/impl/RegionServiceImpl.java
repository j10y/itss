/*    */ package com.htsoft.oa.service.system.impl;
/*    */ 
/*    */ import com.htsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.htsoft.oa.dao.system.RegionDao;
/*    */ import com.htsoft.oa.model.system.Region;
/*    */ import com.htsoft.oa.service.system.RegionService;
/*    */ import java.util.List;
/*    */ 
/*    */ public class RegionServiceImpl extends BaseServiceImpl<Region>
/*    */   implements RegionService
/*    */ {
/*    */   private RegionDao dao;
/*    */ 
/*    */   public RegionServiceImpl(RegionDao dao)
/*    */   {
/* 17 */     super(dao);
/* 18 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public List<Region> getProvince()
/*    */   {
/* 23 */     return this.dao.getProvince();
/*    */   }
/*    */ 
/*    */   public List<Region> getCity(Long regionId)
/*    */   {
/* 28 */     return this.dao.getCity(regionId);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.service.system.impl.RegionServiceImpl
 * JD-Core Version:    0.6.0
 */