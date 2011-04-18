package com.digitalchina.info.appframework.pagemodel.autocreat.servlet;

import java.util.List;
/**
 * 按钮初始化
 */
import com.digitalchina.info.appframework.pagemodel.entity.PageModel;
import com.digitalchina.info.appframework.pagemodel.entity.PageModelBtn;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelBtn;
import com.digitalchina.info.appframework.pagemodel.service.PageModelService;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.appframework.pagemodel.autocreat.servlet.ButtonParameter;
/**
 * 按钮初始化
 * @Class Name CoderForButton
 * @Author sujs
 * @Create In May 12, 2009
 */
public class CoderForButton {
	public static String encode(List<PageModelBtn> pageModelBtn){
		String json="";
		if(!pageModelBtn.isEmpty()){
			for(PageModelBtn pmb:pageModelBtn){
				if(pmb.getIsDisplay()==1&&pmb.getMethod()!=null){
					ButtonParameter button=new ButtonParameter();
					button.setButtonLink(pmb.getLink());
					button.setButtonMainTable(pmb.getPageModel().getSystemMainTable().getTableName());
					button.setButtonName(pmb.getBtnName());
					button.setButtonType(pmb.getMethod());
				    button.setContainer(pmb.getPageModel().getName());
					button.setImageUrl(pmb.getImageUrl());
					button.setNextPageModel((pmb.getNextPageModel()==null?"":pmb.getNextPageModel().getName()));
				    if(pmb.getMethod().endsWith("addByPage")){
				    	json+=ButtonGather.jumpPage(button)+",new Ext.Toolbar.Separator(),";
				    }else if(pmb.getMethod().endsWith("addByWindow")){
				    	json+=ButtonGather.callBack(button)+",new Ext.Toolbar.Separator(),";
                    }else if(pmb.getMethod().endsWith("search")){
                    	json+=ButtonGather.query(button)+",new Ext.Toolbar.Separator(),";
                    }else if(pmb.getMethod().endsWith("reset")){
                    	json+=ButtonGather.reset(button)+",new Ext.Toolbar.Separator(),";
                    }else if(pmb.getMethod().endsWith("resetPanel")){
                    	json+=ButtonGather.resetPanel(button)+",new Ext.Toolbar.Separator(),";
                    }else if(pmb.getMethod().endsWith("modifyByPage")){
                    	json+=ButtonGather.modify(button)+",new Ext.Toolbar.Separator(),";
                    }else if(pmb.getMethod().endsWith("goBack")){
                    	json+=ButtonGather.callBack(button)+",new Ext.Toolbar.Separator(),";
                    }
				}
			}
			if (json.endsWith(",")) {
				json = "[" + json.substring(0, json.length() - 1) + "]";
			}
		}
		return json;
	}
	public static String encodeforPanel(List<PagePanelBtn> pagePanelBtn){
		String json="";
		if(!pagePanelBtn.isEmpty()){
			for(PagePanelBtn pmb:pagePanelBtn){
				if(pmb.getIsDisplay()==null||pmb.getIsDisplay()==1&&pmb.getMethod()!=null){
					ButtonParameter button=new ButtonParameter();
					button.setButtonLink(pmb.getLink());
					//button.setButtonMainTable(pmb.getPageModel().getSystemMainTable().getTableName());
					button.setButtonName(pmb.getBtnName());
					button.setButtonType(pmb.getMethod());
				    button.setContainer(pmb.getName());
					button.setImageUrl(pmb.getImageUrl());
					button.setNextPageModel((pmb.getNextPageModel()==null?"":pmb.getNextPageModel().getName()));
				    if(pmb.getMethod().endsWith("addByPage")){
				    	json+=ButtonGather.jumpPage(button)+", new Ext.Toolbar.Separator(),";
				    }else if(pmb.getMethod().endsWith("addByWindow")){
				    	json+=ButtonGather.callBack(button)+", new Ext.Toolbar.Separator(),";
                    }else if(pmb.getMethod().endsWith("search")){
                    	json+=ButtonGather.query(button)+",new Ext.Toolbar.Separator(),";
                    }else if(pmb.getMethod().endsWith("reset")){
                    	json+=ButtonGather.reset(button)+",new Ext.Toolbar.Separator(),";
                    }else if(pmb.getMethod().endsWith("resetPanel")){
                    	json+=ButtonGather.resetPanel(button)+",new Ext.Toolbar.Separator(),";
                    }else if(pmb.getMethod().endsWith("modifyByPage")){
                    	json+=ButtonGather.modify(button)+",new Ext.Toolbar.Separator(),";
                    }else if(pmb.getMethod().endsWith("goBack")){
                    	json+=ButtonGather.callBack(button)+",new Ext.Toolbar.Separator(),";
                    }else if(pmb.getMethod().endsWith("saveForModel")){
                    	json+=ButtonGather.createSaveForModel(button)+",new Ext.Toolbar.Separator(),";
                    }else if(pmb.getMethod().endsWith("saveForFormPanel")){
                    	json+=ButtonGather.callBack(button)+",new Ext.Toolbar.Separator(),";
                    }
				}
			}
			if (json.endsWith(",")) {
				json = "[" + json.substring(0, json.length() - 1) + "]";
			}
		}
		return json;
	}
}
