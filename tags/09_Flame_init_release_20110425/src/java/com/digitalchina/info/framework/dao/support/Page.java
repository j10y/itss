package com.digitalchina.info.framework.dao.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.digitalchina.info.framework.util.json.CollectionUtil;
import com.digitalchina.info.framework.util.json.JSONUtil;


/**
 * 分页器，HibernateGenericDao中的分页查询方法返回此Page。
 * 提供获取集合数据，记录总数，分页数等方法。
 * <br>
 * @author xiaofeng
 */
@SuppressWarnings("serial")
public class Page implements Serializable {

	private static int DEFAULT_PAGE_SIZE = 20;

	private int pageSize = DEFAULT_PAGE_SIZE; 

	private long start; 

	private Object data; 

	private long totalCount; 

	public Page() {
		this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList());
	}

	/**
	 * 分页器构造方法
	 * @param start
	 * @param totalSize
	 * @param pageSize
	 * @param data
	 */
	public Page(long start, long totalSize, int pageSize, Object data) {
		this.pageSize = pageSize;
		this.start = start;
		this.totalCount = totalSize;
		this.data = data;
	}

	/**
	 * 获取总记录数
	 * @Methods Name getTotalCount
	 * @Create In 2008-10-6 By sa
	 * @return Long
	 */
	public Long getTotalCount() {
		return this.totalCount;
	}

	/**
	 * 获取总分页数
	 * @Methods Name getTotalPageCount
	 * @Create In 2008-10-6 By sa
	 * @return Long
	 */
	public Long getTotalPageCount() {
		if (totalCount % pageSize == 0)
			return totalCount / pageSize;
		else
			return totalCount / pageSize + 1;
	}

	/**
	 * 获取每页显示的记录数
	 * @Methods Name getPageSize
	 * @Create In 2008-10-6 By sa
	 * @return int
	 */
	public int getPageSize() {
		return pageSize;
	}

	public Object getResult() {
		return data;
	}
	
	/**
	 * 获取返回的集合数据
	 * @Methods Name list
	 * @Create In 2008-10-6 By sa
	 * @return List
	 */
	public List list() {
		return (List) data;
	}
	
	/**
	 * 获取返回的集合数据
	 * @Methods Name getData
	 * @Create In 2008-10-6 By sa
	 * @return List
	 */
	public List getData(){
		return (List) data;
	}

	/**
	 * 此方法给特殊的功能使用
	 * @Methods Name setResult
	 * @Create In 2008-5-21 By peixf
	 * @param list void
	 */
	public void setResult(List list){
		this.data = list;
	}

	/**
	 * 获取当前显示的页码
	 * @Methods Name getCurrentPageNo
	 * @Create In 2008-10-6 By sa
	 * @return Long
	 */
	public Long getCurrentPageNo() {
		return start / pageSize + 1;
	}

	/**
	 * 判断是否有下一页
	 * @Methods Name hasNextPage
	 * @Create In 2008-10-6 By sa
	 * @return boolean
	 */
	public boolean hasNextPage() {
		return this.getCurrentPageNo() < this.getTotalPageCount() - 1;
	}

	/**
	 * 判断是否有前一页
	 * @Methods Name hasPreviousPage
	 * @Create In 2008-10-6 By sa
	 * @return boolean
	 */
	public boolean hasPreviousPage() {
		return this.getCurrentPageNo() > 1;
	}

	
	protected static int getStartOfPage(int pageNo) {
		return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
	}

	public static int getStartOfPage(int pageNo, int pageSize) {
		return (pageNo - 1) * pageSize;
	}
	
	/**
	 * 转换page中的list数据为map格式, 增加portal功能新加此方法
	 * @Methods Name transItems
	 * @Create In 2008-10-23 By sa
	 * @return List
	 */
	public List transItems() {
		return CollectionUtil.getCollectionUtil().transList(this.getData());
	}
	/**
	 * 将分页像中的数据转化为标准的JSON分页结构, 增加portal功能新加此方法
	 * @return
	 */
	public String json() {
		if (CollectionUtils.isNotEmpty(this.getData())) {
			return JSONUtil.pageJson(this.getTotalCount(), CollectionUtil
					.getCollectionUtil().list2Json(this.transItems()));
		} else {
			return null;
		}
	}
}