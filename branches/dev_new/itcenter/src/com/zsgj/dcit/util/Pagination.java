package com.zsgj.dcit.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pagination<T> implements Serializable {
	
	private static final long serialVersionUID = 603031536401774250L;
	
	private int pageNum;
	private int pageSize = 10;
	private int firstResult;
	private int pageCount;
	private List<T> data;

	private Pagination() {
		data = new ArrayList<T>();
	}

	public Pagination(int pageNum) {
		this();
		this.pageNum = pageNum;
		setFirstResult(pageNum);

	}

	public Pagination(int pageSize, int pageNum) {
		this(pageNum);
		this.pageSize = pageSize;
		setFirstResult(pageNum);
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(int pageNum) {
		this.firstResult = (pageNum - 1) * pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		if (pageCount == 0) {
			this.pageCount = 1;
		} else if (pageCount % pageSize != 0) {
			this.pageCount = pageCount / pageSize + 1;
		} else {
			this.pageCount = pageCount / pageSize;
		}
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
	public static int getFirstResult(int pageNum,int pageSize){
			return 	(pageNum - 1) * pageSize;
	}
	
}
