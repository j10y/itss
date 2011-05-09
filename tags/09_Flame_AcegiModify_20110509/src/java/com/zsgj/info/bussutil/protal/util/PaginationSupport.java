package com.zsgj.info.bussutil.protal.util;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.zsgj.info.framework.util.json.CollectionUtil;
import com.zsgj.info.framework.util.json.JSONUtil;

public class PaginationSupport {
	public final static int PAGESIZE = 15;

	private int pageSize = PAGESIZE;

	private List items;

	private int totalCount;

	private int[] indexes = new int[0];

	private int startIndex = 0;

	public PaginationSupport(List items) {
		setItems(items);
		setPageSize(items.size());
		setTotalCount(items.size());
		setStartIndex(0);
	}

	public PaginationSupport(List items, int totalCount) {
		setPageSize(PAGESIZE);
		setTotalCount(totalCount);
		setItems(items);
		setStartIndex(0);
	}

	public PaginationSupport(List items, int totalCount, int startIndex) {
		setPageSize(PAGESIZE);
		setTotalCount(totalCount);
		setItems(items);
		setStartIndex(startIndex);
	}

	public PaginationSupport(List items, int totalCount, int pageSize,
			int startIndex) {
		setPageSize(pageSize);
		setTotalCount(totalCount);
		setItems(items);
		setStartIndex(startIndex);
	}

	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		if (totalCount > 0) {
			this.totalCount = totalCount;
			int count = totalCount / pageSize;
			if (totalCount % pageSize > 0) {
				count++;
			}
			indexes = new int[count];
			for (int i = 0; i < count; i++) {
				indexes[i] = pageSize * i;
			}
		} else {
			this.totalCount = 0;
		}
	}

	public int[] getIndexes() {
		return indexes;
	}

	public void setIndexes(int[] indexes) {
		this.indexes = indexes;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		if (totalCount <= 0) {
			this.startIndex = 0;
		} else if (startIndex >= totalCount) {
			this.startIndex = indexes[indexes.length - 1];
		} else if (startIndex < 0) {
			this.startIndex = 0;
		} else {
			this.startIndex = indexes[startIndex / pageSize];
		}
	}

	public int getNextIndex() {
		int nextIndex = getStartIndex() + pageSize;
		if (nextIndex >= totalCount) {
			return getStartIndex();
		} else {
			return nextIndex;
		}
	}

	public int getPreviousIndex() {
		int previousIndex = getStartIndex() - pageSize;
		if (previousIndex < 0) {
			return 0;
		} else {
			return previousIndex;
		}
	}

	public List transItems() {
		return CollectionUtil.getCollectionUtil().transList(this.getItems());
	}

	/**
	 * 将分页像中的数据转化为标准的JSON分页结构
	 * 
	 * @return
	 */
	public String json() {
		if (CollectionUtils.isNotEmpty(this.getItems())) {
			return JSONUtil.pageJson(this.getTotalCount(), CollectionUtil
					.getCollectionUtil().list2Json(this.transItems()));
		} else {
			return null;
		}
	}

}
