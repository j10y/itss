package com.digitalchina.info.framework.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * �����ظ����ݵü��Ϲ���
 * @Class Name UniqueList
 * @Author peixf
 * @Create In 2008-6-13
 * @param <T>
 */
public class UniqueList<T>{

	private List<T> uniqueList = new ArrayList<T>();
	
	public UniqueList(){}
	
	public UniqueList(Collection<T> list){
		for(T item: list){
			if(!uniqueList.contains(item)){
				uniqueList.add(item);
			}
		}
	}
	
	public void add(T object){
		if(!uniqueList.contains(object)){
			uniqueList.add(object);
		}
	}
	
	public List<T> getUniqueList(){
		return this.uniqueList;
	}
}
