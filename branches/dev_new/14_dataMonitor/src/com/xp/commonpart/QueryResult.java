package com.xp.commonpart;

import java.util.List;

public class QueryResult<T> {
	/** 每页显示的分页数据 */
	private List<T> resultlist;
	/** 总记录数*/
	private long totalrecord;
	
	public List<T> getResultlist() {
		return resultlist;
	}
	public void setResultlist(List<T> resultlist) {
		this.resultlist = resultlist;
	}
	public long getTotalrecord() {
		return totalrecord;
	}
	public void setTotalrecord(long totalrecord) {
		this.totalrecord = totalrecord;
	}
}