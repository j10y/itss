package com.xp.commonpart.dao;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.xp.commonpart.Page;

public interface BaseDao {
	/**
	 * 保存实体
	 * @param obj
	 * @param clazz
	 * @param key
	 * @return
	 */
	public Object save(Object obj,Class clazz,String key);
	/**
	 * 删除实体
	 * @param obj
	 */
	public void remove(Object obj);
	/**
	 * 通过参数查找实体 
	 * @param clazz
	 * @param propName
	 * @param propValue
	 * @return
	 */
	public List findObjectByPar(Class clazz,String propName, Object propValue);
	
	/**
	 * 出入参数,和排序的名称
	 * @param clazz
	 * @param propName
	 * @param propValue
	 * @param orderName
	 * @param ascoOrdesc
	 * @return
	 */
	public List findObjectByParOrder(Class clazz,String propName, Object propValue,String orderName,String ascoOrdesc);
	
	/**
	 * 通过传多个参数来查询相应数据
	 * @param clazz
	 * @param propNames
	 * @param propValues
	 * @return
	 */
	public List findObjectByPars(Class clazz,String[] propNames, Object[] propValues);
	
	/**
	 * 通过传多个参数来模糊查询相应数据
	 * @param clazz
	 * @param propNames
	 * @param propValues
	 * @return
	 */
	public List findObjectByLikeParsOrder(Class clazz,String[] propNames, Object[] propValues,String[] orderNames,String[] ascoOrdescs);
	
	public List findObjectByParsOrder(Class clazz,String[] propNames, Object[] propValues,String orderName,String ascoOrdesc);
	
	/**
	 * 查询
	 * @param clazz
	 * @param proMap
	 * @param orderMap
	 * @return
	 */
	public List findObjectListByParamAndOrder(Class clazz,LinkedHashMap proMap,LinkedHashMap orderMap);
	/**
	 *获取唯一的主键值
	 */
	public Long getUniqueId();
	
	public Page findObjectPageByParamAndOrder(Class clazz,
			LinkedHashMap proMap, LinkedHashMap orderMap,int pageNo, int pageSize);
	/**
	 * 分页复合查询,通过传多个参数来查询相应数据
	 * @param clazz
	 * @param proMap
	 * @param proMultMap
	 * @param orderMap
	 * @param pageNo
	 * @param pageSize
	 * @author tongjp
	 * @return
	 */
	public Page findObjectPageByMultParamAndOrder(Class clazz,
			LinkedHashMap proMap,LinkedHashMap proMultMap, LinkedHashMap orderMap,int pageNo, int pageSize) ;
	
	public Object findObjectById(Class clazz,String key,Object id);
}
