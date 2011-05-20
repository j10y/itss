package com.zsgj.itil.service.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.util.BeanUtil;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.itil.service.entity.ServiceCatalogue;
import com.zsgj.itil.service.service.ServiceCatalogueService;

public class ServiceCatalogueAction extends BaseAction{
	
	private ServiceCatalogueService servcieCatalogueService=(ServiceCatalogueService)getBean("servcieCatalogueService");
	
	//保存服务目录
	public ServiceCatalogue save(HttpServletRequest request){
		Class clazz=null;
		try {
			clazz = Class.forName("com.zsgj.itil.service.entity.ServiceCatalogue");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Object obj=BeanUtil.getEncodeObject(request, clazz);
		ServiceCatalogue serviceCatalogue = servcieCatalogueService.save((ServiceCatalogue) obj);
		return serviceCatalogue;
	}
	
//	public String list(){
//		HttpServletRequest request = super.getRequest();
//		Map params = super.getRequestParams();
//		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
//		
//		Page page = servcieCatalogueService.findServiceCatalogue(params, pageNo, pageSize, "name", true);
//		
//		return page.list();
//	}
	
	public String remove() throws Exception {
		String scIds = super.getRequest().getParameter("dataId");
		servcieCatalogueService.removeServiceCatalogue(scIds);
		
		return null;
	}
	
}
