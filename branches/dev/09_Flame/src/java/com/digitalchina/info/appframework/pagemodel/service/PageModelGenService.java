package com.digitalchina.info.appframework.pagemodel.service;

import com.digitalchina.info.appframework.pagemodel.entity.PageModel;
import com.digitalchina.info.appframework.pagemodel.servlet.PageParameter;

public interface PageModelGenService {
	
	/**
	 * 用pageModel的名称生成js或jsp文件
	 * @Methods Name generatePageModelCode
	 * @Create In 2008-12-19 By sa
	 * @param pageModelName
	 * @return String
	 */
	String generatePageModelCode(PageParameter para,Integer fileType,String pagePathUrl);
	
	/**
	 * 为panel的代码生成获取PageModel，并初始化下面所有的子数据
	 * @Methods Name findPageModelForGen
	 * @Create In 2008-12-22 By sa
	 * @param pageModelName
	 * @return PageModel
	 */
	PageModel findPageModelForGen(String pageModelName);
	/**
	 * 为展开代码而作的服务
	 * @Methods Name generatePageModelCodeForExpand
	 * @Create In May 13, 2009 By Administrator
	 * @param para
	 * @param fileType
	 * @param pagePathUrl
	 * @return String
	 */
	String generatePageModelCodeForExpand(PageParameter para,Integer fileType,String pagePathUrl);
}
