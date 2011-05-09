package com.zsgj.info.appframework.metadata;

import java.util.List;
import java.util.Map;

import com.zsgj.info.appframework.metadata.entity.UserTableQueryColumn;
import com.zsgj.info.appframework.metadata.entity.UserTableSetting;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.UserInfo;

/**
 * 系统定制数据管理器，简化前端对定制框架API的使用。 
 * 保证数据定制框架的开发与前端开发分离。
 * 
 * 后续开发中如果遇到特殊的功能，可以用自定义子类，继承本MetaDataManager
 * 接口的默认实现类MetaDataManagerImpl，覆盖相关的方法。
 * query方法使用的是QueryService中的query方法，如业务特殊需要修改查询，
 * 可以直接使用QueryService，QueryService
 * @Class Name MetaDataManager
 * @Author peixf
 * @Create In 2008-6-30
 */
public interface MetaDataManager {
	
	/**
	 * 使用Map<String,String>类型的查询参数queryParams的生成查询字段。
	 * 前端使用HttpUtil.requestParam2Map方法将request中的参数封装以Map<String,String>
	 * 返回，此map作为参数传入此方法，最终生成基于系统查询框架的查询条件,以便
	 * 交给Page query(Class clazz, Map queryParams ...)方法的第2个参数使用。
	 * @Methods Name genQueryParamValues
	 * @Create In 2008-6-30 By peixf
	 * @param clazz
	 * @param requestParams 请求参数的Map形式
	 * @return Map<Object,Object> 系统查询字段，对象
	 */
	Map<Object,Object> genQueryParams(Class clazz, Map<String,String> requestParams);
	
	/**
	 * 框架2期使用，重新暴露出来此方法
	 * @Methods Name genPropParams
	 * @Create In 2008-12-21 By sa
	 * @param clazz
	 * @param requestParams
	 * @return Map<String,Object>
	 */
	Map<String, Object> genPropParams(Class clazz, Map<String, Object> requestParams);
	//Map<String, Object> genPropParams(Class clazz, Map<String, Object> requestParams);
	
	/**
	 * 使用属性名称，属性值的键值对进行查询。此方法省去生成查询字段的步骤，
	 * 只要queryParams参数的key与类的属性名称一致，将作为查询条件进行查询，
	 * 对于日期和货币可以使用区域查询。对于字符串和整数同样可以将参数放到数组
	 * 里进行多值IN查询
	 * @Methods Name query
	 * @Create In 2008-10-16 By sa
	 * @param clazz
	 * @param queryParams
	 * @param pageNo
	 * @param pageSize
	 * @param orderProp
	 * @param isAsc
	 * @return Page
	 */
	Page query(Class clazz, Map<String,Object> queryParams, 
			int pageNo, int pageSize, String orderProp, boolean isAsc);
	
	List query(Class clazz, Map<String,Object> queryParams, String orderProp, boolean isAsc);

	
	/**
	 * 使用主查询参数和扩展查询参数进行综合查询，底层调用QueryService的query方法。
	 * 如果没有扩展参数传递null参数。此方法的queryParams参数必须是genQueryParams方法的返回值
	 * 方法返回分页器，调用page.list()或page.getData即可获取数据
	 * @Methods Name query
	 * @Create In 2008-6-30 By peixf
	 * @param clazz
	 * @param queryParams 基于系统查询框架的查询条件
	 * @param extParams 扩展查询参数
	 * @param pageNo 起始分页号码
	 * @param pageSize 页大小
	 * @param orderProp 排序属性名
	 * @param isAsc 是否升序
	 * @return Page 
	 */
	Page query(Class clazz, Map<Object,Object> queryParams, Map extParams, 
					int pageNo, int pageSize, String orderProp, boolean isAsc);
	
	/**
	 * 使用主查询参数和扩展查询参数进行综合查询，底层调用QueryService的query方法。
	 * 自动获取后台角色权限数据（查看全部还是查看自己）
	 * 如果没有扩展参数传递null参数。此方法的queryParams参数必须是genQueryParams方法的返回值
	 * 方法返回分页器，调用page.list()或page.getData即可获取数据
	 * @Methods Name queryForUser
	 * @Create In 2009-8-18 By lee
	 * @param clazz
	 * @param queryParams 基于系统查询框架的查询条件
	 * @param pageNo 起始分页号码
	 * @param pageSize 页大小
	 * @param orderProp 排序属性名
	 * @param isAsc 是否升序
	 * @param propertyName 过滤属性名
	 * @return Page 
	 */
	Page queryForUser(Class clazz, Map<String,Object> queryParams, int pageNo, int pageSize, String orderProp, boolean isAsc, String propertyName);
	
	
	/**
	 * 添加界面，初始化字段的关联列表，已Map形式返回关联数据。
	 * @Methods Name getEntityDataForAdd
	 * @Create In 2008-6-30 By peixf
	 * @param smt
	 * @return Map<String,Object> 对象属性的名称，对象属性值
	 */
	Map<String, Object> getEntityDataForAdd(Class clazz);
	
	/**
	 * 获取输入表单所有输入项的关联数据。
	 * 编辑界面，加工主对象，初始化页面关联列表数据，已Map形式返回结果。
	 * 前端固定传递ID参数名为 id。
	 * 考虑扩展故使用Map requestParams参数可以将方法定义为：
	 * Map<String,Object> getEntityDataForEdit(Class clazz, Map requestParams);
	 * @Methods Name getEntityDataForEdit
	 * @Create In 2008-6-30 By peixf
	 * @param object
	 * @param smt
	 * @return Map<String,Object> 对象属性的名称，对象属性值
	 */
	Map<String,Object> getEntityDataForEdit(Class clazz, String objectId);
	
	
	/**
	 * 获取输入表单所有输入项的关联数据，与getEntityDataForEdit(Class clazz, String objectId)
	 * 的区别是传入对象。
	 * @Methods Name getEntityDataForEdit
	 * @Create In 2008-9-5 By sa
	 * @param object
	 * @return Map<String,Object>
	 */
	Map<String,Object> getEntityDataForEdit(Object object);
	
	/**
	 * 获取输入表单所有输入项的关联数据
	 * @Methods Name getFormDataForEdit
	 * @Create In May 14, 2009 By sa
	 * @param object
	 * @return Map<String,Object>
	 */
	Map<String,Object> getFormDataForEdit(Object object);
	
	/**
	 * 获取输入表单所有输入项的关联数据
	 * @Methods Name getFormDataForEdit
	 * @Create In May 14, 2009 By sa
	 * @param object
	 * @return Map<String,Object>
	 */
	Map<String,Object> getFormDataForEdit(Object object, String tableName);
	
	/**
	 * 获取输入表单所有输入项的关联数据
	 * @Methods Name getFormDataForEdit
	 * @Create In May 14, 2009 By sa
	 * @param object
	 * @return Map<String,Object>
	 */
	Map<String,Object> getFormDataForEdit(Class clazz, String objectId);
	
	/**
	 * 获取表单页面的预览数据
	 * @Methods Name getEntityDataForLook
	 * @Create In Mar 11, 2009 By Administrator
	 * @param object
	 * @return Map<String,Object>
	 */
	Map<String,Object> getEntityDataForLook(Object object);
	
	
	Map<String,Object> getEntityDataForLook(Class clazz, String id);
	
	
	/**
	 * 此方法重载getEntityDataForEdit(Object object)方法提供给2期框架使用, 将对象带上前缀和关联数据返回
	 * @Methods Name getEntityDataForEdit
	 * @Create In 2008-12-10 By sa
	 * @param object
	 * @param tableName
	 * @return Map<String,Object>
	 */
	Map<String,Object> getEntityDataForEdit(Object object, String tableName);
	
	
	/**
	 * 2期框架预览数据使用的方法
	 * @Methods Name getEntityDataForLook
	 * @Create In 2009-3-13 By sa
	 * @param object
	 * @param tableName
	 * @return Map<String,Object>
	 */
	Map<String,Object> getEntityDataForLook(Object object, String tableName);

	/**
	 * 将列表查询结果已map的形式返回，包括扩展字段的数据。
	 * 目前对应metaDataUtil的getEntityDataForList2方法
	 * @Methods Name getEntityMapDataForList
	 * @Create In 2008-6-30 By peixf
	 * @param mainList
	 * @param smt
	 * @return List<Map<String,Object>>
	 */
	List<Map<String, Object>> getEntityMapDataForList(Class clazz, List mainList);
	
	/**
	 * 将列表查询结果已map的形式返回，包括扩展字段的数据。2期新增方法。
	 * @param clazz
	 * @param mainList
	 * @param tableName
	 * @return
	 */
	List<Map<String, Object>> getEntityMapDataForList(Class clazz, List mainList, String tableName);
	/**
	 * 有时并不是可以在前端使用数据前就初始化所有的实体关联数据，尤其是关联属性又关联属性的情况，
	 * 为解决此问题，并可以充分发挥Hibernate延迟加载问题，故提供此方法。
	 * 将列表查询结果已实体形式返回，扩展字段的数据会自动封装到对象BaseObject的extendProps属性里。
	 * @Methods Name getEntityDataForList
	 * @Create In 2008-7-22 By peixf
	 * @param clazz
	 * @param mainList
	 * @return List<Object>
	 */
	List<Object> getEntityDataForList(Class clazz, List mainList);
	
	/**
	 * 导出数据到excel文件，返回最终生成的导出文件名称，如product_0998484843.xls，
	 * 将此文件名称发送至页面，使用应用前缀+fileRootPath+product_0998484843.xls即可下载文件<br>
	 * @Methods Name outPutDatas
	 * @Create In 2008-5-21 By peixf
	 * @param clazz 类型
	 * @param fileRootPath 导出文件相对于应用根目录的相对路径，如output/product/
	 * @param sheetName 导出Excel文件的sheet名称
	 * @param filePrefix 导出Excel文件名称前缀，底层实现以此前缀加时间戳来命名，如生成的product_0998484843.xls的前缀为product
	 * @param mainList 要导出的数据
	 * @return String 生成的导出文件名称
	 */
	String exportData(Class clazz, List mainList, String fileRootPath, String sheetName, String filePrefix);
	
	/**
	 * 保存实体数据，将请求参数置入Map，传递给该方法。
	 * @Methods Name saveEntityData
	 * @Create In 2008-7-22 By peixf
	 * @param clazz
	 * @param requestParams void
	 */
	Object saveEntityData(Class clazz, Map requestParams);
	
	/**
	 * 物理删除实体数据，级联删除扩展数据。
	 * @Methods Name removeEntityData
	 * @Create In 2008-7-22 By peixf
	 * @param clazz
	 * @param id void
	 */
	void removeEntityData(Class clazz, String objectId);
	
	/**
	 * 逻辑生成，删除后的数据移入历史表里
	 * @Methods Name removeEntityData
	 * @Create In 2008-7-22 By peixf
	 * @param clazz
	 * @param objectId void
	 */
	void removeEntityData(Class clazz, Class eventClazz, String objectId);
	
	/**
	 * 获取用户可见的所有列表字段，即列表页面显示哪些字段。
	 * @Methods Name getUserColumnForList
	 * @Create In 2008-7-28 By sa
	 * @param clazz
	 * @return List<UserTableSetting>
	 */
	List<UserTableSetting> getUserColumnForList(Class clazz);
	
	/**
	 * 获取表单输入界面用户的所有字段设置，即输入和修改页面（通常对应一个JSP页面）显示哪些字段。
	 * @Methods Name getUserColumnForEdit
	 * @Create In 2008-7-28 By peixf
	 * @param clazz
	 * @return List<UserTableSetting>
	 */
	List<UserTableSetting> getUserColumnForEdit(Class clazz);
	
	/**
	 * 获取用户可见的查询字段，即在列表页面顶部要显示哪些字段作为查询条件。
	 * 注意查询条件的表单元素如果为下拉列表，则需要初始化其数据，故如列表页面顶部需要有查询功能，
	 * 则另需要下面方法，初始化查询字段关联数据
	 * @Methods Name getUserColumnForQuery
	 * @Create In 2008-7-28 By peixf
	 * @param clazz
	 * @return List<UserTableQueryColumn>
	 */
	List<UserTableQueryColumn> getUserColumnForQuery(Class clazz);
	
	/**
	 * 获取用户可见查询字段的所有关联数据
	 * @Methods Name getUserColumnDataForQuery
	 * @Create In 2008-7-28 By peixf
	 * @param clazz
	 * @return Map<String,Object>
	 */
	Map<String,Object> getUserColumnDataForQuery(Class clazz);
	
	/**
	 * 保存实体数据，将请求参数置入Map，传递给该方法,传入用户信息，不从UserContext里取。
	 * @param clazz
	 * @author tongjp
	 * @param requestParams
	 * @param user
	 * @return
	 */
	Object saveEntityDataForUser(Class clazz, Map requestParams,UserInfo user);
}
