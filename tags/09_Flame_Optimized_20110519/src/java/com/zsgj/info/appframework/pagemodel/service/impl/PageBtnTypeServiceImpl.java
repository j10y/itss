package com.zsgj.info.appframework.pagemodel.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;

import com.zsgj.info.appframework.pagemodel.entity.PageBtnType;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.appframework.pagemodel.service.PageBtnTypeService;
import com.zsgj.info.framework.dao.BaseDao;

public class PageBtnTypeServiceImpl extends BaseDao implements PageBtnTypeService {

	public PageBtnType findPageBtnTypeByName(String keyName) {
		PageBtnType pageBtnType = null;
		Criteria c = super.getCriteria(PageBtnType.class);
		c.add(Restrictions.eq("name", keyName));
		List list = c.list(); //·ÀÖ¹ÓÐÖØ¸´¼ÇÂ¼
		if(!list.isEmpty()){
			pageBtnType = (PageBtnType) list.iterator().next();
		}
		return pageBtnType;
	}

}
