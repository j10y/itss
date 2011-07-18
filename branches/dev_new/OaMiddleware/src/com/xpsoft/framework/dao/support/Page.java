package com.xpsoft.framework.dao.support;

import java.util.List;

/**
 * 分页器
 * @Class Name Page
 * @Author debby
 * @Create In Jul 23, 2010
 */
 
public class Page {
	 private int totalRows = 0; // 记录总数   

	 private int totalPages = 0; // 总页数   

	 private int pageSize = 10; // 每页显示数据条数，默认为10条记录   

	 private int currentPage = 1; // 当前正在显示的页面   

	 private int prePage;// 前面一页   

	 private int nextPage;// 后面一页   

	 private boolean hasPrevious = false; // 是否有上一页   

	 private boolean hasNext = false; // 是否有下一页   

	 private List pageList = null;// 每次请求后从数据库取得的将要显示数据   

	 public Page(int totalRows, int totalPages, int pageSize, int currentPage,   
	         int prePage, int nextPage, boolean hasPrevious, boolean hasNext,   
	         List pageList) {   
	     super();   
	     this.totalRows = totalRows;   
	     this.totalPages = totalPages;   
	     this.pageSize = pageSize;   
	     this.currentPage = currentPage;   
	     this.prePage = prePage;   
	     this.nextPage = nextPage;   
	     this.hasPrevious = hasPrevious;   
	     this.hasNext = hasNext;   
	     this.pageList = pageList;   
	 }   
	 
	 /**
	  * 根据参数的总数默认显示第一页
	  * @param totalRows
	  * @author debby
	  * @Create In Jul 23, 2010
	  */
	 public  Page(int  totalRows) {   
		 this.totalRows = totalRows;   
		 totalPages=totalRows / pageSize;   
		 int  mod=totalRows % pageSize;   
		 if (mod > 0 )  {   
			 totalPages ++ ;   
		 }    
		 currentPage=1 ;   
		 refresh();
	 }   
	 
	 /**
	  * 根据总数和当前页构造分页器
	  * @author debby
	  * @param totalRows
	  * @param currentPage
	  * @Create In Jul 23, 2010
	  */
	 public  Page(int  totalRows,int currentPage) {   
		 this.totalRows = totalRows;   
		 totalPages=totalRows / pageSize;   
		 int  mod=totalRows % pageSize;   
		 if (mod > 0 )  {   
			 totalPages ++ ;   
		 }    
		 this.currentPage = currentPage;
		 refresh();
	 }   
	 
	 /**
	  * 根据总数、当前页和大小构造分页器
	  * @author debby
	  * @param totalRows
	  * @param currentPage
	  * @param pageSize
	  * @Create In Jul 23, 2010
	  */
	 public  Page(int  totalRows,int currentPage,int pageSize) {   
		 this.pageSize = pageSize;
		 this.totalRows = totalRows;   
		 totalPages=totalRows / pageSize;   
		 int  mod=totalRows % pageSize;   
		 if (mod > 0 )  {   
			 totalPages ++ ;   
		 }    
		 this.currentPage = currentPage;
		 refresh();
	 }   
	 
	 public void setData(List list){
		 this.pageList = list;
	 }
	 
	 public List getData(){
		 return pageList;
	 }
	 
	 public Page() {   
	 }   

	 public List getPageList() {   
	     return pageList;   
	 }   

	 public void setPageList(List pageList) {   
	     this.pageList = pageList;   
	 }   

	 public int getCurrentPage() {   
	     return currentPage;   
	 }   

	 public void setCurrentPage(int currentPage) {   
	     this.currentPage = currentPage;   
	     refresh();   
	 }   

	 public int getPageSize() {   
	     return pageSize;   
	 }   

	 public void setPageSize(int pageSize) {   
	     this.pageSize = pageSize;   
	     refresh();   
	 }   

	 public int getTotalPages() {   
	     return totalPages;   
	 }   
	    
	 public int getTotalRows() {   
	     return totalRows;   
	 }   

	 public void setTotalRows(int totalRows) {   
	     this.totalRows = totalRows;   
	     refresh();   
	 }   

	  // 跳到第一页   
	  public void first() {   
	      currentPage = 1;   
	      this.setHasPrevious(false);   
	      refresh();   

	  }   

	  // 取得上一页（重新设定当前页面即可）   
	  public void previous() {   
	      currentPage--;   
	      refresh();   
	  }   

	  // 取得下一页   
	  public void next() {   
	      if (currentPage < totalPages) {   
	          currentPage++;   
	      }   
	      refresh();   
	  }   

	  // 跳到最后一页   
	  public void last() {   
	      currentPage = totalPages;   
	      this.setHasNext(false);   
	      refresh();   
	  }   

	  public boolean isHasNext() {   
	      return hasNext;   
	  }   

	  public void setHasNext(boolean hasNext) {   
	      this.hasNext = hasNext;   
	  }   

	  public boolean isHasPrevious() {   
	      return hasPrevious;   
	  }   

	  public void setHasPrevious(boolean hasPrevious) {   
	      this.hasPrevious = hasPrevious;   

	  }   

	  /**
	   * 根据传入的总页数和当前页初始化参数
	   * @Methods Name refresh
	   * @Create In Jul 23, 2010 By debby void
	   */
	  public void refresh() {   

	      if (totalPages <= 1) {   

	          hasPrevious = false;   

	          hasNext = false;
	          
	          prePage = currentPage;
	          
	          nextPage = currentPage;

	      } else if (currentPage == 1) {   

	          hasPrevious = false;   

	          hasNext = true;   

	          prePage = currentPage;
	          
	          nextPage = currentPage + 1;

	      } else if (currentPage == totalPages) {   

	          hasPrevious = true;   

	          hasNext = false;   

	          prePage = currentPage - 1;
	          
	          nextPage = currentPage;

	      } else {   

	          hasPrevious = true;   

	          hasNext = true;   

	          prePage = currentPage - 1;
	          
	          nextPage = currentPage + 1;

	      }   

	  }   

	  public int getPrePage() {   
	      return prePage;   
	  }   

	  public void setPrePage(int prePage) {   
	      this.prePage = prePage;   
	  }   

	  public int getNextPage() {   
	      return nextPage;   
	  }   

	  public void setNextPage(int nextPage) {   
	      this.nextPage = nextPage;   
	  }   

	
}