package com.digitalchina.info.appframework.pagemodel.service;

import com.digitalchina.info.appframework.pagemodel.entity.PageModel;
import com.digitalchina.info.appframework.pagemodel.servlet.PageParameter;

public interface PageModelGenService {
	
	/**
	 * ��pageModel����������js��jsp�ļ�
	 * @Methods Name generatePageModelCode
	 * @Create In 2008-12-19 By sa
	 * @param pageModelName
	 * @return String
	 */
	String generatePageModelCode(PageParameter para,Integer fileType,String pagePathUrl);
	
	/**
	 * Ϊpanel�Ĵ������ɻ�ȡPageModel������ʼ���������е�������
	 * @Methods Name findPageModelForGen
	 * @Create In 2008-12-22 By sa
	 * @param pageModelName
	 * @return PageModel
	 */
	PageModel findPageModelForGen(String pageModelName);
	/**
	 * Ϊչ����������ķ���
	 * @Methods Name generatePageModelCodeForExpand
	 * @Create In May 13, 2009 By Administrator
	 * @param para
	 * @param fileType
	 * @param pagePathUrl
	 * @return String
	 */
	String generatePageModelCodeForExpand(PageParameter para,Integer fileType,String pagePathUrl);
}
