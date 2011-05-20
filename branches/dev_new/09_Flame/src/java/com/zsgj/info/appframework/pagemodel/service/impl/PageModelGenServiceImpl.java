package com.zsgj.info.appframework.pagemodel.service.impl;

import java.util.Set;

import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.appframework.pagemodel.PageManager;
import com.zsgj.info.appframework.pagemodel.entity.PageModel;
import com.zsgj.info.appframework.pagemodel.entity.PageModelPanel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelRelation;
import com.zsgj.info.appframework.pagemodel.service.PageModelGenService;
import com.zsgj.info.appframework.pagemodel.service.PageModelService;
import com.zsgj.info.appframework.pagemodel.service.PagePanelColumnService;
import com.zsgj.info.appframework.pagemodel.service.PagePanelService;
import com.zsgj.info.appframework.pagemodel.servlet.PageParameter;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.asm.render.FreemarkerRender;

@SuppressWarnings("unused")
public class PageModelGenServiceImpl extends BaseDao implements PageModelGenService {

	private PageManager pageManager;
	private MetaDataManager metaDataManager;
	private Service service;
	private PageModelService pageModelService ;
	private PagePanelService pagePanelService ;
	private PagePanelColumnService pagePanelColumnService ;
	//private ConfigItemService configItemService ;
	
	public static String TEMPLATE_PAGE_PANEL = "com/zsgj/info/appframework/pagemodel/ftl/pagePanel.ftl";
	
	public void setPageModelService(PageModelService pageModelService) {
		this.pageModelService = pageModelService;
	}

	public PageModel findPageModelForGen(String pageModelName) {
		// TODO Auto-generated method stub
		return null;
	}

	public String generatePageModelCode(PageParameter para,Integer fileType,String pagePathUrl) {	
	  String path=para.getPagePath();
	  String fileName= path.substring((path.lastIndexOf("/")+1));
	  String jsName= fileName.substring(0, (fileName.lastIndexOf(".")));
	  String filePath= path.substring(1, (path.lastIndexOf("/")));
	  PageParameter pap=new PageParameter();
	     pap.setFilePath(filePath+"/"+jsName);
	  String filepath=  filePath.replace("/", "\\");
	  FreemarkerRender render = new FreemarkerRender();
	   String rootPath=pagePathUrl+filepath+"\\";
	  //"D:\\jboss-4.2.2.GA\\server\\default\\.\\deploy\\itil.war\\user\\configDemo\\";
		 if(fileType==1){
			String TemplatePath="com/zsgj/info/appframework/pagemodel/ftl/listPanel.ftl";
			    render.renderPage(para, TemplatePath,rootPath+jsName+".js");
			String TemplatePath2="com/zsgj/info/appframework/pagemodel/ftl/listPaneljsp.ftl";
			    render.renderPage(pap, TemplatePath2,rootPath+fileName);
		 }
		 else if(fileType==2){
			String TemplatePath="com/zsgj/info/appframework/pagemodel/ftl/pagePanel.ftl";
			    render.renderPage(para, TemplatePath,rootPath+jsName+".js");
			String TemplatePath2="com/zsgj/info/appframework/pagemodel/ftl/pagePaneljsp.ftl";
			    render.renderPage(pap, TemplatePath2,rootPath+fileName);
			
		 }
		return null;
	}
	/**
	 * 新增展开字段生成
	 */
	public String generatePageModelCodeForExpand(PageParameter para,Integer fileType,String pagePathUrl) {	
		  String path=para.getPagePath();
		  String fileName= path.substring((path.lastIndexOf("/")+1));
		  String jsName= fileName.substring(0, (fileName.lastIndexOf(".")));
		  String filePath= path.substring(1, (path.lastIndexOf("/")));
		  PageParameter pap=new PageParameter();
		     pap.setFilePath(filePath+"/"+jsName);
		  String filepath=  filePath.replace("/", "\\");
		  FreemarkerRender render = new FreemarkerRender();
		   String rootPath=pagePathUrl+filepath+"\\";
		  //"D:\\jboss-4.2.2.GA\\server\\default\\.\\deploy\\itil.war\\user\\configDemo\\";
			 if(fileType==1){
				String TemplatePath="com/zsgj/info/appframework/pagemodel/ftl/expandlistPanel.ftl";
				    render.renderPage(para, TemplatePath,rootPath+jsName+".js");
				String TemplatePath2="com/zsgj/info/appframework/pagemodel/ftl/expandlistPaneljsp.ftl";
				    render.renderPage(pap, TemplatePath2,rootPath+fileName);
			 }
			 else if(fileType==2){
				String TemplatePath="com/zsgj/info/appframework/pagemodel/ftl/expandPagePanel.ftl";
				    render.renderPage(para, TemplatePath,rootPath+jsName+".js");
				String TemplatePath2="com/zsgj/info/appframework/pagemodel/ftl/expandPagePaneljsp.ftl";
				    render.renderPage(pap, TemplatePath2,rootPath+fileName);
				
			 }
			return null;
		}

	
	private String genPagePanelJson(PageModelPanel pmp){
		String json = "";
		PagePanel panel = pmp.getPagePanel();
		String divFloat = pmp.getDivFloat();
		json+="{";
		json += "\"panelname\":\""+panel.getName()+"\",";
		json += "\"title\":\""+panel.getTitle()+"\",";
		json += "\"panelTableName\":\""+panel.getSystemMainTable().getTableCnName()+"\",";
		json += "\"xtype\":\""+panel.getXtype().getName()+"\",";
		json += "\"divFloat\":\""+divFloat+"\",";
		json += "\"order\":\""+pmp.getOrder()+"\",";
		json += "\"clazz\":\""+panel.getSystemMainTable().getClassName()+"\"";
		Set<PagePanelRelation> childpprs = panel.getChildPagePanels();
		if(!childpprs.isEmpty()){
			json += ",\"childPagePanels\":[";
			for(PagePanelRelation ppr : childpprs){
				json+= this.genPagePanelJson(ppr);
				json+=",";
			}
			json = json.substring(0, json.length()-1);
			json += "]";
		}
		json+="}";
		return json;
	}
	
	private String genPagePanelJson(PagePanelRelation pmp){
		String json = "";
		PagePanel panel = pmp.getPagePanel();
		//String divFloat = pmp.getDivFloat();
		json+="{";
		json += "\"panelname\":\""+panel.getName()+"\",";
		json += "\"title\":\""+panel.getTitle()+"\",";
		json += "\"panelTableName\":\""+panel.getSystemMainTable().getTableCnName()+"\",";
		json += "\"xtype\":\""+panel.getXtype().getName()+"\",";
		json += "\"order\":\""+pmp.getOrder()+"\",";
		json += "\"clazz\":\""+panel.getSystemMainTable().getClassName()+"\"";
		Set<PagePanelRelation> childpprs = panel.getChildPagePanels();
		if(!childpprs.isEmpty()){
			json += ",\"childPagePanels\":[";
			for(PagePanelRelation ppr : childpprs){
				json+= this.genPagePanelJson(ppr);
				json+=",";
			}
			json = json.substring(0, json.length()-1);
			json += "]";
		}
		json+="}";
		return json;
	}
	

	
	public void setPageManager(PageManager pageManager) {
		this.pageManager = pageManager;
	}
	
	public void setMetaDataManager(MetaDataManager metaDataManager) {
		this.metaDataManager = metaDataManager;
	}
	
	public void setService(Service service) {
		this.service = service;
	}
	
	public void setPagePanelService(PagePanelService pagePanelService) {
		this.pagePanelService = pagePanelService;
	}
	
	public void setPagePanelColumnService(
			PagePanelColumnService pagePanelColumnService) {
		this.pagePanelColumnService = pagePanelColumnService;
	}
	
//	public void setConfigItemService(ConfigItemService configItemService) {
//		this.configItemService = configItemService;
//	}
}
