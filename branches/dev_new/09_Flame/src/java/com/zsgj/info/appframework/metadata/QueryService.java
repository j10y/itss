package com.zsgj.info.appframework.metadata;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;

import com.zsgj.info.framework.dao.support.Page;

/**
 * 查询服务接口，定义基于定制框架的通用查询接口。
 * 因QueryService为MetaDataManager之外的框架API接口
 * 注意如果查询服务无法满足您的业务场景，请重新实现QueryService接口。具体办法：
 * <pre>
 *   仿照ColumnQueryServiceImpl类，写一个类继承ColumnQueryService抽象类，覆盖其
 *   middle方法，如只需直接修改查询条件，覆盖middle(Criteria criteria)。
 *   如果要根据特殊的参数组合来决定，请覆盖middle(Criteria criteria, Map extParams)方法.
 * </pre>
 * 
 * @Class Name QueryService
 * @Author peixf
 * @Create In 2008-5-30
 */
public interface QueryService {

	/**
	 * 对查询的特殊限制，由前端查询参数决定，子类覆盖此方法。
	 * @param criteria
	 * @param extParams
	 */
	void middle(Criteria criteria, Map extParams);
	
	/**
	 * 对查询的特殊限制，不由前端查询参数决定，子类覆盖此方法。
	 * 如解决方案有法人体的限制
	 * @param criteria
	 */
	void middle(Criteria criteria);

	/**
	 * 查询功能实际调用的查询方法。底层先调用before，
	 * 进而middle(Criteria criteria)和end方法。
	 * @param clazz
	 * @param params 基于定制框架构造好的查询参数, 其key可能为systemQueryColumn或string
	 * @param pageNo
	 * @param pageSize
	 * @param orderProp
	 * @param isAsc
	 * @return Page
	 */
	Page query(Class clazz, Map<Object,Object> params, int pageNo, int pageSize, String orderProp, boolean isAsc);
	
	
	
	/**
	 * 查询功能实际调用的查询方法。底层先调用before，
	 * 进而middle(Criteria criteria,Map extParams)和end方法。
	 * @param clazz
	 * @param params 基于定制框架构造好的查询参数, 其key可能为systemQueryColumn或string
	 * @param extParams 对于部分特殊功能，超乎框架可控范围，增加此参数，子类对其进行特殊处理
	 * @param pageNo
	 * @param pageSize
	 * @param orderProp
	 * @param isAsc
	 * @return Page
	 */
	Page query(Class clazz, Map<Object,Object> params, Map extParams, 
					int pageNo, int pageSize, String orderProp, boolean isAsc);
	
	
	/**
	 * 使用参数键值对进行查询, param格式：属性名作为key，属性值作为value.
	 * 此方法可以完成参数类型自动转化. <br>底层先调用before，
	 * 进而middle(Criteria criteria,Map extParams)和end方法.
	 * @Methods Name queryByParams
	 * @Create In 2008-10-17 By sa
	 * @param clazz
	 * @param params
	 * @param extParams
	 * @param pageNo
	 * @param pageSize
	 * @param orderProp
	 * @param isAsc
	 * @return Page
	 */
	Page queryByParams(Class clazz, Map<String,Object> params, Map<String,Object> extParams, 
				int pageNo, int pageSize, String orderProp, boolean isAsc);
	
	/**
	 * 使用参数键值对进行查询, param格式：属性名作为key，属性值作为value.
	 * 此方法可以完成参数类型自动转化. <br>底层先调用before，
	 * 进而middle(Criteria criteria,Map extParams)和end方法.
	 * @Methods Name queryByParams
	 * @Create In 2009-1-12 By sa
	 * @param clazz
	 * @param params
	 * @param extParams
	 * @param orderProp
	 * @param isAsc
	 * @return List
	 */
	List queryByParams(Class clazz, Map<String,Object> params, Map<String, Object> extParams, 
			String orderProp, boolean isAsc);
	
	/**
	 * 为树形结构查询结果构造查询数据，需要按照某个属性进行名称显示，单击
	 * 此名称旁边的加好显示其下所有的子数据。
	 * @Methods Name queryForTree
	 * @Create In 2009-1-12 By sa
	 * @param clazz
	 * @param params
	 * @param extParams
	 * @param pageNo
	 * @param pageSize
	 * @param orderProp
	 * @param isAsc
	 * @return Page
	 */
	Page queryForTree(Class clazz, Map<String,Object> params, Map<String,Object> extParams, 
				int pageNo, int pageSize, String orderProp, boolean isAsc);
	
	
	/**
	 * 使用查询参数构造基于数据定制框架的Criteria
	 * @param entityClazz
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @param orderProp
	 * @param isAsc
	 * @return Criteria 返回值将会传递给middle(Criteria criteria)方法
	 */
	Criteria before(Class entityClazz, Map params, int pageNo, int pageSize, String orderProp, boolean isAsc);
	
	/**
	 * 结束查询，返回分页器，其中包含分页信息及最终的查询结果
	 * @param criteria
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page end(Criteria criteria, int pageNo, int pageSize);
	
	
	Page end(Criteria criteria, Map params, int pageNo, int pageSize);
	/**
	 * 查询功能实际调用的查询方法。底层先调用before，
	 * 进而middle(Criteria criteria,Map extParams)和end方法。
	 * @Methods Name queryByParamsForUser
	 * @Create In Aug 18, 2009 By lee
	 * @param clazz
	 * @param queryParams
	 * @param object
	 * @param pageNo
	 * @param pageSize
	 * @param orderProp
	 * @param isAsc
	 * @param propertyName
	 * @return Page
	 */
	Page queryByParamsForUser(Class clazz, Map<String, Object> queryParams,
			Object object, int pageNo, int pageSize, String orderProp,
			boolean isAsc, String propertyName);

}
