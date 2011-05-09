package com.zsgj.info.framework.security.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;

import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.security.entity.Platform;
import com.zsgj.info.framework.security.entity.Province;
import com.zsgj.info.framework.security.entity.Region;
import com.zsgj.info.framework.security.service.PlatformService;

public class PlatformServiceImpl extends BaseDao implements PlatformService{

	/* (non-Javadoc)
	 * @see com.digitalchina.info.framework.security.service.PlatformService#findRegionByName(java.lang.String, java.lang.String, boolean, int, int)
	 */
	public Map findProvinceByName(String name, String orderBy, boolean isAsc,
			int pageNo, int pageSize) {
	   Map<String,Object> resultMap =  new HashMap();
		
		Criteria criteria = super.getCriteria(Province.class);
		if(name!=null&&!"".equals(name)){
			criteria.add(Expression.like("name", "%"+name+"%"));
		}
		List queryList = criteria.setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize).list();
		
		criteria.setFirstResult(0);
		long total =  ((Integer) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		resultMap.put("queryList", queryList);
		resultMap.put("total", total);
		return resultMap;
	}
	
	public Map findRegionByName(String name, String orderBy, boolean isAsc,
			int pageNo, int pageSize) {
	   Map<String,Object> resultMap =  new HashMap();
		
		Criteria criteria = super.getCriteria(Region.class);
		if(name!=null&&!"".equals(name)){
			criteria.add(Expression.like("name", "%"+name+"%"));
		}
		List queryList = criteria.setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize).list();
		
		criteria.setFirstResult(0);
		long total =  ((Integer) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		resultMap.put("queryList", queryList);
		resultMap.put("total", total);
		return resultMap;
	}

	public Map findPlatformByName(String name, String orderBy, boolean isAsc,
			int pageNo, int pageSize) {
		Map<String,Object> resultMap =  new HashMap();
		
		Criteria criteria = super.getCriteria(Platform.class);
		if(name!=null&&!"".equals(name)){
			criteria.add(Expression.like("name", "%"+name+"%"));
		}
		List queryList = criteria.setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize).list();
		
		criteria.setFirstResult(0);
		long total =  ((Integer) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		resultMap.put("queryList", queryList);
		resultMap.put("total", total);
		return resultMap;
	}

	public Platform findPlatformById(Long id) {
		Criteria criteria = super.getCriteria(Platform.class);
		criteria.add(Expression.eq("id", id));
		return (Platform) criteria.uniqueResult();
	}

}
