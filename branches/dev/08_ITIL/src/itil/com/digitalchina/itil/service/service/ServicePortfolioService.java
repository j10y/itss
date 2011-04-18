package com.digitalchina.itil.service.service;

import com.digitalchina.itil.service.entity.ServicePortfolio;
/**
 * 服务组合服务
 * @Class Name ServicePortfolioServcie
 * @Author lee
 * @Create In 2009-1-15
 */
public interface ServicePortfolioService {
	/**
	 * 通过ID获取服务组合
	 * @Methods Name findServicePortfolioById
	 * @Create In 2009-1-15 By lee
	 * @param id
	 * @return ServicePortfolio
	 */
	ServicePortfolio findServicePortfolioById(String id);
	
}
