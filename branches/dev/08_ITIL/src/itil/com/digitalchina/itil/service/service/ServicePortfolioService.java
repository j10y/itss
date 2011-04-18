package com.digitalchina.itil.service.service;

import com.digitalchina.itil.service.entity.ServicePortfolio;
/**
 * ������Ϸ���
 * @Class Name ServicePortfolioServcie
 * @Author lee
 * @Create In 2009-1-15
 */
public interface ServicePortfolioService {
	/**
	 * ͨ��ID��ȡ�������
	 * @Methods Name findServicePortfolioById
	 * @Create In 2009-1-15 By lee
	 * @param id
	 * @return ServicePortfolio
	 */
	ServicePortfolio findServicePortfolioById(String id);
	
}
