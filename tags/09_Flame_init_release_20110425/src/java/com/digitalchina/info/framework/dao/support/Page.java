package com.digitalchina.info.framework.dao.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.digitalchina.info.framework.util.json.CollectionUtil;
import com.digitalchina.info.framework.util.json.JSONUtil;


/**
 * ��ҳ����HibernateGenericDao�еķ�ҳ��ѯ�������ش�Page��
 * �ṩ��ȡ�������ݣ���¼��������ҳ���ȷ�����
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
	 * ��ҳ�����췽��
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
	 * ��ȡ�ܼ�¼��
	 * @Methods Name getTotalCount
	 * @Create In 2008-10-6 By sa
	 * @return Long
	 */
	public Long getTotalCount() {
		return this.totalCount;
	}

	/**
	 * ��ȡ�ܷ�ҳ��
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
	 * ��ȡÿҳ��ʾ�ļ�¼��
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
	 * ��ȡ���صļ�������
	 * @Methods Name list
	 * @Create In 2008-10-6 By sa
	 * @return List
	 */
	public List list() {
		return (List) data;
	}
	
	/**
	 * ��ȡ���صļ�������
	 * @Methods Name getData
	 * @Create In 2008-10-6 By sa
	 * @return List
	 */
	public List getData(){
		return (List) data;
	}

	/**
	 * �˷���������Ĺ���ʹ��
	 * @Methods Name setResult
	 * @Create In 2008-5-21 By peixf
	 * @param list void
	 */
	public void setResult(List list){
		this.data = list;
	}

	/**
	 * ��ȡ��ǰ��ʾ��ҳ��
	 * @Methods Name getCurrentPageNo
	 * @Create In 2008-10-6 By sa
	 * @return Long
	 */
	public Long getCurrentPageNo() {
		return start / pageSize + 1;
	}

	/**
	 * �ж��Ƿ�����һҳ
	 * @Methods Name hasNextPage
	 * @Create In 2008-10-6 By sa
	 * @return boolean
	 */
	public boolean hasNextPage() {
		return this.getCurrentPageNo() < this.getTotalPageCount() - 1;
	}

	/**
	 * �ж��Ƿ���ǰһҳ
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
	 * ת��page�е�list����Ϊmap��ʽ, ����portal�����¼Ӵ˷���
	 * @Methods Name transItems
	 * @Create In 2008-10-23 By sa
	 * @return List
	 */
	public List transItems() {
		return CollectionUtil.getCollectionUtil().transList(this.getData());
	}
	/**
	 * ����ҳ���е�����ת��Ϊ��׼��JSON��ҳ�ṹ, ����portal�����¼Ӵ˷���
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