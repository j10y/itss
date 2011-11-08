package com.xp.commonpart.service;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xp.commonpart.Page;
/**
 * 基于hibernate的基础方法接口,包括简单的增删查改
 * @author tongjp
 *
 */
public interface BaseService {
	/**
	 * 保存实体,保存前会通过对象的指定id,检查对象是否是新增或修改,如果新增,会根据规则生成一个唯一只id附给该对象
	 * @param obj 对象
	 * @param clazz 所属类
	 * @param key 该对象的id,
	 * @author tongjp
	 * @return
	 */
	public Object save(Object obj,Class clazz,String key);
	/**
	 * 删除实体
	 * @param obj
	 * @author tongjp
	 */
	public void remove(Object obj);
	
	public Object findObjectById(Class clazz,String key,Object id);
	
	/**
	 * 通过参数查找实体,该方法只支持一个参数的查询 
	 * @param clazz,对象的类
	 * @param propName,查询的参数名
	 * @param propValue,查询的值
	 * @author tongjp
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
	 * @author tongjp
	 * @return
	 */
	public List findObjectByParOrder(Class clazz,String propName, Object propValue,String orderName,String ascoOrdesc);
	
	/**
	 * 通过传多个参数来查询相应数据,可输入多个参数,按照参数名和值的顺序分别放到数组中
	 * @param clazz
	 * @param propNames
	 * @param propValues
	 * @author tongjp
	 * @return
	 */
	public List findObjectByPars(Class clazz,String[] propNames, Object[] propValues);
	
	/**
	 * 通过传多个参数来模糊查询相应数据,可输入多个参数,按照参数名和值的顺序分别放到数组中
	 * @param clazz
	 * @param propNames
	 * @param propValues
	 * @author tongjp
	 * @return
	 */
	public List findObjectByLikeParsOrder(Class clazz,String[] propNames, Object[] propValues,String[] orderNames,String[] ascoOrdescs);
	/**
	 * 通过传多个参数来查询相应数据,可输入多个参数,按照参数名和值的顺序分别放到数组中,但是只支持一个排序参数
	 * @param clazz
	 * @param propNames
	 * @param propValues
	 * @param orderName
	 * @param ascoOrdesc
	 * @author tongjp
	 * @return
	 */
	public List findObjectByParsOrder(Class clazz,String[] propNames, Object[] propValues,String orderName,String ascoOrdesc);
	
	/**
	 * 通过传多个参数来查询相应数据,
	 * @param clazz
	 * @param proMap(属性名,值)
	 * @param orderMap(排序属性,desc or asc)
	 * @author tongjp
	 * @return
	 */
	public List findObjectListByParamAndOrder(Class clazz,LinkedHashMap proMap,LinkedHashMap orderMap);
	/**
	 *获取唯一值
	 *@author tongjp
	 *@return
	 */
	public Long getUniqueId();
	/**
	 * 分页查询,通过传多个参数来查询相应数据
	 * @param clazz
	 * @param proMap(属性名,值)
	 * @param orderMap(排序属性,desc or asc)
	 * @param request
	 * @author tongjp
	 * @return
	 */
	public Page findObjectPageByParamAndOrder(Class clazz,
			LinkedHashMap proMap, LinkedHashMap orderMap,HttpServletRequest request) ;
	
	/**
	 * 分页复合查询,通过传多个参数来查询相应数据
	 * @param clazz
	 * @param proMap(属性名,值)
	 * @param proMultMap(属性名,值)需要模糊查询的参数
	 * @param orderMap(排序属性,desc or asc)
	 * @param request
	 * @author tongjp
	 * @return
	 */
	public Page findObjectPageByMultParamAndOrder(Class clazz,
			LinkedHashMap proMap,LinkedHashMap proMultMap, LinkedHashMap orderMap,HttpServletRequest request) ;
	/**
	 * 保存系统日志
	 * @param request
	 * @param operateDetail操作内容
	 * @author tongjp
	 */
	public void saveSysTemUserLog(HttpServletRequest request,String operateDetail);
	
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
	
	/**
	 * 导出数据，返回连接
	 * @param titles
	 * @param proNames
	 * @param dataList
	 * @param exportType
	 * @author tongjp
	 * @return
	 */
	public String exportData(String fileRootPath, String sheetName,
			String filePrefix,String[] titles,String[] proNames,List dataList,String exportType);
	
	/**
	 * 导出数据，返回连接
	 * @param fileRootPath
	 * @param sheetName
	 * @param filePrefix
	 * @param titles
	 * @param proNames
	 * @param dataList
	 * @param listValueMap那些下拉列表的数据
	 * @param exportType
	 * @return
	 */
	public String exportDataForlist(String fileRootPath, String sheetName,
			String filePrefix,String[] titles, String[] proNames, List dataList,String[] propertytype,Map listValueMap,
			String exportType,Map imagepath);
}
