package com.zsgj.info.appframework.metadata.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;

import com.zsgj.info.appframework.metadata.ColumnQueryService;
import com.zsgj.info.framework.dao.support.Page;

/**
 * 字段查询服务的默认实现类。
 * 此实现类将注入给“元数据管理器MetaDataManagerImpl”以获取框架的通用查询功能。
 * @Class Name ColumnQueryServiceImpl
 * @Author peixf
 * @Create In 2008-7-23
 */
public class ColumnQueryServiceImpl extends ColumnQueryService {

	
	public Criteria before(Class entityClazz, Map params, int pageNo, int pageSize, String orderProp, boolean isAsc) {

		return super.before(entityClazz, params, pageNo, pageSize, orderProp, isAsc);
	}

	
	public void middle(Criteria criteria, Map extParams) {

		super.middle(criteria, extParams);
	}

	
	public void middle(Criteria criteria) {

		super.middle(criteria);
	}

	
	public Page query(Class clazz, Map params, int pageNo, int pageSize, String orderProp, boolean isAsc) {

		return super.query(clazz, params, pageNo, pageSize, orderProp, isAsc);
	}

	
	public Page query(Class clazz, Map params, Map extParams, int pageNo, int pageSize, String orderProp, boolean isAsc) {

		return super.query(clazz, params, extParams, pageNo, pageSize, orderProp, isAsc);
				
	}



	/*public Page query2(Class clazz, Map<String, Object> params, Map<String, Object> extParams, 
				int pageNo, int pageSize, String orderProp, boolean isAsc) {
		
		return super.query2(clazz, params, extParams, pageNo, pageSize, orderProp, isAsc);
		
	}
*/

//	public Page end(Criteria criteria, Map params, int pageNo, int pageSize) {
//		// TODO Auto-generated method stub
//		return super.end(criteria, params, pageNo, pageSize);
//	}

	
	
}
