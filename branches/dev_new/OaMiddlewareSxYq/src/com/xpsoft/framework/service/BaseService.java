package com.xpsoft.framework.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.xpsoft.framework.dao.BaseObject;
import com.xpsoft.framework.dao.Dao;
import com.xpsoft.framework.dao.support.Page;
import com.xpsoft.framework.entity.Sequence;
import com.xpsoft.framework.util.PropertiesUtil;


/**
 * 业务操作的基础类 - 实现一个类的基本操作和基本的增删改查。
 * 此类可以被所有的服务实现类继承，以获取常用的服务方法。
 * 如果某些CommonDao经常被各个服务类使用，可以考虑将这些通用的CommonDao实现注入到 CommonService中，然后具体的子服务继承此CommonServiceImpl。
 * @Class Name BaseService
 * @Author likang
 * @Create In Jul 22, 2010
 */
public class BaseService implements Service {
	
	/**
	 * 服务层日志记录器
	 */
	protected final Log logger = LogFactory.getLog(BaseService.class);

	/**
	 * 服务层依赖的DAO接口
	 */
	protected Dao dao;
	

	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	/**
	 * 分页查询，不指定查询条件
	 * @Methods Name findAllByPage
	 * @Create In Jul 22, 2010 By likang
	 * @param clazz    要查询的实体
	 * @param orderBy  排序字段
	 * @param isAsc    是否升序
	 * @param pageNo   页码
	 * @param pageSize 页大小
	 * @return Page	   分页对象
	 */
	public Page findAllByPageWithNoCondition(Class clazz, String orderBy,
			Boolean isAsc, int pageNo, int pageSize) throws Exception{
		Map map = new HashMap();
		map.put("start", (pageNo-1)*pageSize);
		map.put("end", pageNo*pageSize);
		if (orderBy != null && !orderBy.trim().equals("")) {
			map.put("orderBy", orderBy);
		}
		if (isAsc != null) {
			if (isAsc) {
				map.put("asc", "asc");
			} else {
				map.put("asc", "desc");
			}
		}
		Integer totalRows = dao.selectTotal(clazz);
		Page page = new Page(totalRows,pageNo,pageSize);
		List list = dao.getObjectsByParam(clazz, map, "selectPage");
		page.setData(list);
		return page;
	}

	/**
	 * 根据实体ID查找对象
	 * @Methods Name getObjectById
	 * @Create In Jul 22, 2010 By likang
	 * @param clazz
	 * @param id
	 * @return Object
	 */
	public Object getObjectById(Class clazz, Object id) throws Exception {
		return dao.getObjectById(clazz, id);
	}

	/**
	 * 获取指定类的所有持久化对象
	 * @author debby
	 * @param clazz
	 * @return List
	 * @throws Exception 
	 */
	public List getObjects(Class clazz) throws Exception{
		return dao.getObjects(clazz);
	}

	/**
	 * 物理删除持久化状态
	 * @Methods Name remove
	 * @Create In Aug 3, 2010 By likang
	 * @param o
	 * @throws Exception void
	 */
	public void remove(Object o) throws Exception {
		dao.removeObject(o.getClass(), ((BaseObject)o).getId());
	}

	/**
	 * 提供对象id物理删除持久化对象
	 * @Methods Name removeObject
	 * @Create In Aug 3, 2010 By likang
	 * @param clazz
	 * @param id
	 * @throws Exception void
	 */
	public void removeObject(Class clazz, Object id) throws Exception {
		dao.removeObject(clazz, id);
	}

	/**
     * 逻辑删除持久化对象
     * 默认删除标记为deleteFlag
     * @Methods Name removeObjectByFlag
     * @Create In Jul 22, 2010 By debby
     * @param clazz
     * @param id void
     */
	public void removeObjectByFlag(Class clazz, Object id)
			throws Exception {
		dao.removeObjectByFlag(clazz, id);
		
	}

	/**
     * 物理批量删除
     * @Methods Name removeObjectByIds
     * @Create In Jul 22, 2010 By likang
     * @param clazz
     * @param value id的逗号分隔 如 1,2,3
     */
	public void removeObjectByIds(Class clazz, String values)
			throws Exception {
		dao.removeObjectByIds(clazz, values);
		
	}

	/**
	 * 保存持久化或游离状态的对象
	 * @param o
	 * @return Object
	 */
	public Object save(Object o) throws Exception{
		return dao.save(o);
	}

	/**
	 * 更新实体对象
	 * @Methods Name update
	 * @Create In Jul 22, 2010 By debby
	 * @param o void
	 */
	public void update(Object o) throws Exception {
		dao.update(o);
	}
	
	/**
     * 分页查询，指定查询条件
     * @Methods Name findAllByPage
     * @Create In Jul 22, 2010 By likang
     * @param clazz    要查询的实体
     * @param map	   需要传入参数的map  例如 map.put("属性名",值);
     * @param orderBy  排序字段
     * @param isAsc    是否升序
     * @param pageNo   页码
     * @param pageSize 页大小
     * @return Page	   分页对象
     */
	public Page findAllByPage(Class clazz, Map map,  String orderBy,
			Boolean isAsc, int pageNo, int pageSize) throws Exception {
		map.put("start", (pageNo-1)*pageSize);
		map.put("end", pageNo*pageSize);
		if (orderBy != null && !orderBy.trim().equals("")) {
			map.put("orderBy", orderBy);
		}
		if (isAsc != null) {
			if (isAsc) {
				map.put("asc", "asc");
			} else {
				map.put("asc", "desc");
			}
		}
		Integer totalRows = dao.selectTotalByParam(clazz,map);
		Page page = new Page(totalRows,pageNo,pageSize);
		List list = dao.getObjectsByParam(clazz, map, "selectPageByParam");
		page.setData(list);
		return page;
	}
	
	/**
	 * 按条件不分页获取指定类的所有持久化对象
	 * @Methods Name getObjectsByParam
	 * @Create In Aug 3, 2010 By likang
	 * @param clazz
	 * @param map	  需要传入参数的map  例如 map.put("属性名",值);
	 * @param orderBy 排序字段
	 * @param isAsc   是否升序
	 * @return
	 * @throws Exception List
	 */
	public List getObjectsByParam(Class clazz, Map map, String orderBy,
			Boolean isAsc) throws Exception {
		if (orderBy != null && !orderBy.trim().equals("")) {
			map.put("orderBy", orderBy);
		}
		if (isAsc != null) {
			if (isAsc) {
				map.put("asc", "asc");
			} else {
				map.put("asc", "desc");
			}
		}
		List list = dao.getObjectsByParamNoPage(clazz, map);
		return list;
	
	}
	
	/**
     * 批量逻辑删除持久化对象
     * 默认删除标记为deleteFlag 需要每个实体有该属性
     * @Methods Name removeObjectsByFlag
     * @Create In Aug 3, 2010 By likang
     * @param clazz
     * @param values id的逗号分隔 如 1,2,3
     * @throws Exception void
     */
	public void removeObjectsByFlag(Class clazz, String values)
			throws Exception {
		// TODO Auto-generated method stub
		dao.removeObjectsByFlag(clazz, values);
		
	}

	
	/**
	 * 分页查询，指定查询条件,返回结果不带总页数
	 * @Methods Name findPageListByParam
	 * @Create In Aug 18, 2010 By debby
	 * @param clazz
	 * @param map
	 * @param orderBy
	 * @param isAsc
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception List
	 */
	public List findPageListByParam(Class clazz, Map map,  String orderBy,
			Boolean isAsc, int pageNo, int pageSize)throws Exception {
		map.put("start", (pageNo-1)*pageSize);
		//查11条 为了分页判断
		map.put("end", pageNo*pageSize+1);
		if (orderBy != null && !orderBy.trim().equals("")) {
			map.put("orderBy", orderBy);
		}
		if (isAsc != null) {
			if (isAsc) {
				map.put("asc", "asc");
			} else {
				map.put("asc", "desc");
			}
		}
		List list = dao.getObjectsByParam(clazz, map, "selectPageByParam");
		return list;
	}
	
	/**
	 * 根据查询语句名字，按条件不分页获取指定类的所有持久化对象
	 * @Methods Name getObjectsByQueryName
	 * @Create In Jan 21, 2011 By likang
	 * @param clazz
	 * @param queryName	sqlMap中查询语句名称
	 * @param map	  需要传入参数的map  例如 map.put("属性名",值);
	 * @param orderBy 排序字段
	 * @param isAsc   是否升序
	 * @return
	 * @throws Exception List
	 */
	public List getObjectsByQueryName(Class clazz,String queryName,Map map, String orderBy, Boolean isAsc)throws Exception{
		if (orderBy != null && !orderBy.trim().equals("")) {
			map.put("orderBy", orderBy);
		}
		if (isAsc != null) {
			if (isAsc) {
				map.put("asc", "asc");
			} else {
				map.put("asc", "desc");
			}
		}
		List list = dao.getObjectsByQueryName(clazz,queryName,map);
		return list;
	}
	/**
	 * 根据参数，通过查询语句的名字查询全部总数
	 * @Methods Name selectTotalQueryName
	 * @Create In Jan 22, 2011 By likang
	 * @param clazz
	 * @param queryName
	 * @param map
	 * @return Integer
	 */
	public Integer selectTotalByQueryName(Class clazz, String queryName, Map map) {
		// TODO Auto-generated method stub
		return dao.selectTotalByQueryName(clazz,queryName,map);
	}
	
	/**
	 * 分页查询辅助方法 封装成page对象(用于一个SqlMap中有同时有多个分页查询情况下自定义查询语句id)
	 * @Methods Name queryByPage
	 * @Create In Jan 22, 2011 By likang
	 * @param clazz	要查询的实体
	 * @param paramPageQueryName	条件分页查询在sqlMap中的id
	 * @param paramTotalQueryName	条件总数查询在sqlMap中的id
	 * @param queryMap				条件Map
	 * @param pageNo				当前页码
	 * @param pageSize				分页长度
	 * @return Page
	 */
	public Page pageQueryBySqlName(Class clazz, String paramPageQueryName,
			String paramTotalQueryName, Map queryMap, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		queryMap.put("start", (pageNo-1)*pageSize);
		queryMap.put("end", pageNo*pageSize);
		//分页查数据
		List list = new ArrayList();
		try {
			list = dao.getObjectsByQueryName(clazz, paramPageQueryName, queryMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//条件查总数
		Integer totalRows = dao.selectTotalByQueryName(clazz, paramTotalQueryName, queryMap);
		//分页对象
		Page page = new Page(totalRows,pageNo,pageSize);
		page.setData(list);
		return page;
	}
}
