 package com.xpsoft.core.web.paging;
 
 public class PagingBean
 {
   public static final String PAGING_BEAN = "_paging_bean";
/* 14 */   public static Integer DEFAULT_PAGE_SIZE = Integer.valueOf(25);
   public static final int SHOW_PAGES = 6;
   public Integer start;
   private Integer pageSize;
   private Integer totalItems;
 
   public PagingBean(int start, int limit)
   {
/* 32 */     this.pageSize = Integer.valueOf(limit);
/* 33 */     this.start = Integer.valueOf(start);
   }
 
   public Integer getPageSize() {
/* 37 */     return this.pageSize;
   }
 
   public void setPageSize(int pageSize) {
/* 41 */     this.pageSize = Integer.valueOf(pageSize);
   }
 
   public int getTotalItems() {
/* 45 */     return this.totalItems.intValue();
   }
 
   public Integer getStart() {
/* 49 */     return this.start;
   }
 
   public void setStart(Integer start) {
/* 53 */     this.start = start;
   }
 
   public void setTotalItems(Integer totalItems) {
/* 57 */     this.totalItems = totalItems;
   }
 
   public void setTotalItems(int totalItems) {
/* 61 */     this.totalItems = Integer.valueOf(totalItems);
   }
 
   public int getFirstResult()
   {
/* 66 */     return this.start.intValue();
   }
 }
